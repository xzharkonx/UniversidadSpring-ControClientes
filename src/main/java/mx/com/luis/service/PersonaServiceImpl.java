package mx.com.luis.service;

import java.util.List;
import mx.com.luis.dao.PersonaDAO;
import mx.com.luis.domain.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Eduardo
 */

// Implementamos la interfaz de servicio: PersonaService e implementamos
// todos sus métodos que están definidos en la interface que creamos.
// Para que Spring pueda reconocer está Clase como una Clase de Servicio
// vamos a colocarle la anotación de Service. Esto es muy importante ya que si
// no agregamos está anotación entonces no vamos a poder inyectar está Clase
// como una implementación de la Interface Persona Service dentro del 
// controlador de Spring.
// Si le damos Ctrl + Clic a la anotación podemos observar que a su vez está 
// anotación utiliza la anotación de @Component, así que con esto es suficiente
// para que Spring pueda reconocer está Clase como parte del  contenedor de 
// Spring y posteriormente la podamos inyectar en el controlador.

// Transacciones ---------------------------------------------------------------

// Pero debido a que estamos trabajando en la capa de servicio tenemos 
// que agregar un tema más en la capa de Servicio tenemos que agregar
// un tema mas.
// Cuando estamos denr de la capa Dao se maneja el concepto de transacciones,
// esto quiere decir que cualquier operación con la Base de Datos en dado
// caso de error se va a hacer un "Rollback" u en caso de que todo sea 
// exitoso se hace un "Commit", pero cuando estamos trabajando con nuestras
// clases de Servicio podríamos estar utilizando varios objetos de tipo DAO
// desde una misma Clase de Servicio por lo tanto podríamos estar realizando 
// varias operaciones sobre distintas tablas sobre la base de datos, por lo 
// tanto también estos métodos debemos de marcarlos como transaccionales ya que
// en dado caso de error entonces se tiene que hacer un Rollback y no guardar
// ninguna información en ninguna de las tablas afectadas y en dado caso de que
// todo sea exitoso entonces se debe de hacer un Commit de toda la transacción
// guardando toda la información en todas las tablas afectadas. 
// Así que dependiendo del tipo de método es el tipo de Transacción que vamos a 
// realizar.

@Service
public class PersonaServiceImpl implements PersonaService{

// Así que con esto ya tenemos definida nuestra Clase de tipo service y ahora
// vamos a inyectar una instancia de nuestra clase de tipo PersonaDao.
    
    // De igual manera que en el Controlador, le tenemos que colocar la
    // anotación de @Autowired
    @Autowired
    private PersonaDAO personaDao;

    // Por lo que ahora nuestro controlador ya no va a utilizar directamente la
    // Capa de Datos (la Clase de PersonaDao) si no que ahora va a utilizar
    // la capa de servicio PersonaServiceImpl, que realmente va a utilizar
    // la interface PersonaService. Eso lo veremos posteriormente en el 
    // Controlador.
    
    // Así que ahora vamos a conectar nuestra Capa de Servicio con nuestra 
    // Capa de Datos utilizando esta variable PersonaDao.
    // Así que agregamos la implementación de estos métodos.
    
    // Transaciones continuación -----------------------------------------------
    // Por ejemplo, en este método de listar Personas únicamente leemos
    // información de la Base de Datos, así que como únicamente vamos a agregar
    // información, agregamos la anotación de @Transactional e importamos esta
    // anotación pero debemos de tener cuidado!.debemos importarla de 
    // org.springframework.transaction.annotation.Transactional y
    // no de Javax.transaction.Transactional, de lo contrario vamos a tener 
    // errores para manejar el concepto de transacciones cuando estemos 
    // trabajando con Spring.
    // Y posteriormente debido a que este método solamente va a leer información
    // entones podemos utilizar el atributo de readOnly = true y le asignamos
    // el valor de verdadero ya que únicamente vamos a leer información de la 
    // base de datos.
    
    @Transactional(readOnly = true)
    @Override
    public List<Persona> listarPersonas() {
        // Aquí nos dará error por que el método de findAll() nos regresara
        // todos los objetos de la Base de Datos pero en tipo Object, por lo
        // que haremos un Casteo de tipo List<Persona> para que lo podamos 
        // convertir a una lista.
       return (List<Persona>) personaDao.findAll();
    }

    // Transacciones continuación ----------------------------------------------
    // Pero en el caso de las Tansacciones al guardar debemos  de indicar
    // que va a ser de tipo Transaccional sin ningún otro parametro ya que en
    // este caso guardar si va a modificar la información en la base de datos
    // por lo tanto si debe de hacer "Commit o Rollback", ya que en el caso de
    // listar personas no es necesario hacer "Commit o Rollback".
    @Transactional
    @Override
    public void guardar(Persona persona) {
        personaDao.save(persona);
    }
    
    // Transacciones continuación ----------------------------------------------
    // Aquí también solo agregamos la anotación para que nos permita hacer
    // un "Commit o Rollback" por que si se están modificando los datos en la DB
    
    @Transactional
    @Override
    public void eliminar(Persona persona) {
        personaDao.delete(persona);
    }

    // Transacciones continuación ----------------------------------------------
    // Aquí como solamente es un Select, agregamos el atributo de readOnly = true
    // ya que este método solamente va a consultar información de la base de 
    // datos. No la va a modificar.
    @Transactional(readOnly = true)
    @Override
    public Persona encontrarPersona(Persona persona) {
        // Finalmene para poder recuperar un objeto de tipo persona
        // llamaos al método findById y le pasamos el objeto de tipo Persona
        // que estamos recibiendo y posteriormente getId_persona().
        // Así que con este código lo que estamos haciendo es buscar el objeto
        // de tipo persona con la llave proporcionada. 
        
        // y lo retornamos pero lo que podemos observar que el método 
        // findById lo que nos regresa es un tipo Optional (Optional<Persona)
        // y este tipo básicamente lo que podemos hacer es decidir que hacemos
        // en dado caso de que el valor regresado sea de tipo null.
        // Así que de manera opcional podemos hacer lo siguiente
        // Podemos regresar el objeto encontrado o también tenemos otras opciones
        // por ejemplo regresar el valor de null en dado caso de que no
        // entcontremos el objeto que estamos buscando o por ejemplo también 
        // lanzar una excepcion, para ello, al final del método colocamos un .
        // para ver que opciones nos manda y ahí selecionamos .orElse() y le
        // pasaremos null: .orElse(null)
        
       return personaDao.findById(persona.getId_persona()).orElse(null);
       
       // Así que en dado caso de que este método no encuentre el objeto que
       // estamos buscando no va a fallar, no va a mandar error si no que
       // va a regresar el valor de null.
       
       // Así que con esto ya hemos implementado todos los métodos de nuestro
       // servicio. Para leer, insertar, eliminar o buscar una persona por Id.
       
       // Una vez agregado el concepto de Transacciones a nuestros métodos
       // Ahora sí que con esto ya tenemos toda la implementación de 
       // PersonaServiceImpl.
       
       // Así que ahora vamos a nuestro Controlador a agregar estos cambios.
    }
    
}
