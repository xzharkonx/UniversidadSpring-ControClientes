package mx.com.luis.web;

import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import mx.com.luis.domain.Persona;
import mx.com.luis.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Eduardo
 */

// Sección 3.
// Básicamente lo que vamos a hacer en este tema es recuperar la información
// de manera dinámica directamente de una base de datos utilizando
// MySQL

// Así que los siguientes cambios que haremos será que en nuestro 
// controlador en lugar de que utilice la información de manera estática
// en las clases de tipo Persona que creamos y lo compartimos creando un
// arreglo de objetos de tipo persona, en lugar de hacer esto manualmente
// lo vamos a recuperar de una base de datos.

// Esto será simple con Spring en la última versión.
// Lo primero que vamos a hacer es modificar nuestro archivo POM.xml
// para agregar las librerías que nos hacen falta en la carpeta de
// Project Files.

// Recuerda haber hecho un Clean and Build del Proyecto.

// Ahora trabajaremos en esta nueva Clase.
// Iremos a modificar nuestra Clase Identidad (Persona.java)

@Controller
@Slf4j // Anotación de loombok para ver datos por consola.
public class ControladorWeb {
    
    //    @Autowired
    //    private PersonaDao personaDao;
   
    // Ahora en lugar de inyectar un objeto de tipo PersonaDao que es la capa
    // de datos ahora lo que vamos a inyectar es nuestra capa de servicio, 
    // nuestra capa lógica.
    @Autowired
    private PersonaService personaService;
    // Básicamente con esto en lugar de inyectar una Clase concreta, podemos 
    // observar que no estamos inyectando la Clase de PersonaServiceImpl si no,
    // que Spring lo que hace es que en automático va a buscar por un lado el
    // tipo que es la interface pero va a buscar una instancia de la
    // implementación que se ha marcado con la anotación de @Service.
    // Así que de está manera Spring o hace muy sencillo, inyectar una
    // instancia de la Clase PersonaServiceImpl.
    // Sin embargo, si en un futuro queremos cambiar la implementación
    // como estamos utilizando la interface no hay ningún problema, podríamos
    // cambiar simplemente la implementación y el código de nuestro controlador
    // quedaría exactamente igual. 
    
    // Así que en este momento ya estamos desacoplando cada una de las capas, 
    // anto la capa de presentación en el controlador, estamos inyectando la
    // capa de servicio y en la capa de servicio desacoplamos también la capa
    // de datos haciendo exactamente lo mismo inyectando un tipo PersonaDao y en
    // automático Spring va a crear una implementación de está interface.
    // Así que por ello no fuen necesario en este caso inyectar ni crear una
    // clase concreta de PersonaDao, todo se hizo de forma automática al 
    // estender de la Interfaz CrudRepository.
    
    // Con esto ya tenemos todo nuestro flujo completo y podemos probarlo.
    
    // Podemos obtener el Usuario que se ha logueado con @AuthenticationPrincipal
    // Importamos de: org.springframework.security.core.annotation.AuthenticationPrincipal;
    // y pasandole al Objeto User user como argumento.
    // Importamos de: org.springframework.security.core.userdetails.User;
    @GetMapping("/")
    public String inicio(Model model, @AuthenticationPrincipal User user){
        
        log.info("Ejecutando el Controlador Spring MVC");
        log.info("Usuario que hizo login:" + user);
        // Posteriormente vamos a recuperar estos datos de la Base de Datos.
        // Debimos haber convertido nuestro modelo Persona a Identidad y
        // luego debimos de haber creado nuestra dependencia de la clase 
        // PersonaDao, ahora haremos lo siguiente.
        
        // Definimos un variable personaDao de tipo PersonaDao.
        // Y en Spring para poder inyectar cualquier dependencia /cualquier
        // otro objeto que sea administrado por el contenedor, basta con
        // con colocarle la anotación @Autowired y la importamos.
        // Esto es muy similar a la versión Java EE (Empresarial), esto es muy
        // similar a la anotación de Inject, así que básicamente lo que hacemos
        // es inyectar la Interface tipo PersonaDao y esta Interface al 
        // extender de la Interface CrudRepository entonces ya tenemos todos los
        // métodos para poder trabajar con nuestro objeto de tipo persona.
        // Importamos nuestra Clase de PersonaDao.
        
        // Y ahora para poder recuperar los objetos de tipo persona basta con
        // hacer lo siguiente:
        // Declaramos una variable y posteriormente le asignamos el valor 
        // de nuestra variable inyectada y está contendrá lo métodos ya
        // ya mencionados en la Interfaz.
        
        // Ya no tuvimos que hacer ninguna implementación esto lo va a hacer
        // en automático SpringBoot al levantar nuestra aplicación.
        // En esto momento lo que se crea es la implementación de nuestra 
        // PersonaDao.
        
        // var personas = personaDao.findAll();
        // Y aquí vamos a modificar la variable que estamos utilizando, ahora
        // será la de personaService y en vez de mandar a mandar a llamar al
        // método findAll vamos a mandar a llamar al método de listar personas.
        // Esto nos va a regresar la lista de objetos de tipo persona.
        var personas = personaService.listarPersonas();
        
        // Por último volvemos a compartir dentro del modelo nuestro listado
        // de objetos de tipo Persona
        model.addAttribute("personas", personas);    
        
        // Por último configuraremos la vista para resivir estos datos.
        
        // Sección 5
        // Ahora iremos al index3
        return "index3";

    }
    
    // Sección 5 ---------------------------------------------------------------
    // Ahora agregaremos el path /agregar 
    // Con una petición de tipo get
    @GetMapping("/agregar")
    // Lo que vamos a hacer es crear un nuevo objeto de tipo persona,
    // esto lo podemos hacer dentro del método o podemos decirle a Spring que
    // nos inyecte un nuevo objeto de tipo persona, para que nos inyecte
    // un Objeto de tipo persona simplemente lo definimos como argumento más
    // en nuestro método, y entonces lo que va a hacer Spring es buscar un
    // Objeto de tipo persona en la fábrica de Spring, si no lo encuentra
    // entonces va a crear un nuevo objeto y  nos lo va a inyectar de 
    // manera automática en esté método, así de sencillo va a ser crear un nuevo
    // objeto cuando estemos trabajando con Spring
    public String agregar(Persona persona){
        // Si nos sale error importamos import mx.com.luis.domain.Persona;
        
        // Así que dentro de este método ya no será necesario utilizar el
        // operador new, esto lo va a hacer en automático Spring y lo va a
        // inyectar de manera automática.
        
        // Una vez que ya tenemos definido nuestro de tipo persona, este objeto
        // en automático se comparte en el alcance de request dentro de la 
        // fábrica de Spring.
        
        // Así que el siguiente paso va a ser simplemente redirigir hacia la
        // vista, en este caso la vista se va a llamar modificar. Se va a llamar
        // modificar ya que va a realizar los 2 casos de uso, tanto de agregar
        // como de modificar un objeto de tipo persona, va a realizar las 2
        // funciones.
        
        // Así que dentro de templates vamos a agregar la vista de modificar
        // Creamos un archivo modificar.html
        
        return "modificar";
    }
    
    // Aquí vamos a agregar el path para guardar los datos
    // Como el form es de tipo post utilizaremos la anotación de @PostMapping
    @PostMapping("/guardar")
    // Vamos a resivir el objeto del formulario, para resivirlo simplemente
    // Spring lo va a inyectar de forma automática al definirlo como argumento
    // Entonces Spring buscara el objeto que se lleno en el formulario, lo
    // recupera y lo va a inyectar directamente al momento de definir la
    // variable como argumento.
    
    // Recordemos que en el método de agregar como era la primera vez que esté
    // Objeto estaba en memoria, entonces crea un nuevo objeto, pero
    // si este objeto ya se encuentra en memoria, entonces simplemente va a 
    // pasar la instancia que ya se encuentra creada en memoria con cada
    // uno de los valores que se haya llenado desde el formulario.
    public String guardar(@Valid Persona persona, Errors errores){
        // Preguntamos si hay errores, si los hay retornamos a la vista de 
        // modificar
        if (errores.hasErrors()){
            return "modificar";
        }
        
        // Así que en este momento ya tenemos todo nuestro objeto Persona lleno
        // con cada uno de los valores (excepto el id_persona) ya que este 
        // es un valor automático.
        // Y apoyandonos en nuestro objeto de personaService llamamos al 
        // método de guardar, le pasamos el objeto persona y con esto
        // es suficiente para que se inserte la información en la base de datos.
        personaService.guardar(persona);
        // Por último hacemos un return y nos redireccionamos a la página de
        // inicio.
        // Así que para hacer este redireccionamiento utilizamos la palabra 
        // redirect:  y posteriormente el path al cual queremos redireccionar.
        // Si solo colocamos el path / nos dará error.
        return "redirect:/";
        // Con esto ya tenemos todo el flujo para agregar un nuevo Objeto
        // de tipo persona.
    }
    
    // Para editar crearemos el siguiente Path, como es un simple link, la
    // solicitud será de tipo get
    // Indicamos el path y posteriormente la variable que vamos a utilizar
    // en este caso id_persona. Este atributo debe ser exactamente igual al
    // atributo de la Clase que queremos que se inicialice, de lo contrario
    // no se podrá inicializar correctamente el objeto de tipo persona, esto
    // lo vamos a ver posteriormente.
    @GetMapping("/editar/{id_persona}")
    // Simplemente al definir un objeto de tipo persona, Spring lo que va a 
    // hacer es buscar este Objeto, si ya existe entonces lo va a inyectar
    // y además el parametro que estamos recibiendo lo va a asociar con este
    // Objeto de tipo persona, así que de manera automática va a inicializar
    // este Objeto de tipo Persona con el valor de id_persona.
    // Así que a no es necesario inicializar este Objeto ni hacer set de este
    // parametro que estamos recibiendo.
    // Con esto es suficiente para recibir la información de este objeto persona
    // y posteriormente recibimos la variable de Modelo para poderla compartir
    // para la siguiente petición.
    public String editar(Persona persona, Model model){
        // Ahora como ese objeto ya tiene el id que queremos buscar en la DB
        // entonces vamos a apoyarnos de nuestro objeto personaService para 
        // buscar ese objeto con el método encontrarPersona y le pasamos 
        // el Objeto de tipo Persona.
        // Así este Objeto ya puede buscarse en la base de datos.
        // Este método de encontrarPersona regresa un Objeto de tipo Persona, lo
        // volvemos a asignar a la misma variable para no crear otra.
        // Así que con esto ya tenemos nuestro Objeto completo con cada uno
        // de los valores de los atributos recuperados de la base de datos
        persona = personaService.encontrarPersona(persona);
        // Finalmente compartimos este objeto utilizando nuestra variable de model
        // y posteriormente utilizamos el método addAtribute para pasarlo a la 
        // vista
        model.addAttribute("persona",persona);
        // Por último hacemos return a la vista de modificar.
        // Por ello es que esta vista sirve para los 2 casos tanto para agregar
        // como para modificar un Objeto ya que la información que se recupera
        // de esté Objeto es exactamente la misma.
        // Si encuenta un Objeto nuevo, entonces simplemente se va a llenar
        // cada uno de los valores con el formulario del Objeto nuevo.
        // Pero si encentra un Objeto el cuál ya ha sido inicializado, en este
        // caso se agrego al modelo, ya se encuentra en la fabrica de Spring
        // entonces esa vista recupera el Objeto que ya está compartido
        // en la fabrica de Spring entonces llena cada uno de los valors
        // del formulario debido a que se está asociando los elementos de
        // tipo input con cada uno de los campos del Objeto.
        return "modificar";
        // Podemos ver que a hacer clic en editar nos lleva a la vista
        // de modificar y nos trae develta el Objeto que queremos editar
        // pero ahora hay que configurar en la vista que ese objeto se va a
        // modificar.
    }
    
    // Este será similar al método de eliminar.
    // @GetMapping("/eliminar/{id_persona}")
    // Pero lo haremos con QueryParam en vez de hacerlo como arriba con PathVariable
    // para ello haremos lo siguiente, simplemente será el path de /eliminar
    // Y como este path ya incluye un parametro llamado id_persona y al reconocer
    // que es un atributo de nuestro Objeto de tipo Persona que ya se encuentra
    // en el contenedor de Spring entonces le asigna este valor de manera
    // automática. Así que este Objeto lo inyecta Spring y además hace un set
    // del valor del id_persona que estamos recibiendo como parametro en este 
    // link. Así que es todo lo que tenemos que agregar.
    @GetMapping("/eliminar")
    public String eliminar(Persona persona){
        personaService.eliminar(persona);
        return "redirect:/";
        // Con esto es suficiente para eliminar un registro de la base de datos.
    }
    
    
    // Sección 6 Validaciones con Spring ---------------------------------------
    // Ahora tenemos que modificar en nuestro Controlador, ya que al momento de
    // mandar a llamar nuestro método de guardar (el path de /guardar) 
    // entonces debemos de recuperar los errores, así que ir a ver como esta
    // colocada está parte ahí.
    // Entonces en el método de guardar vamos a indicar que esté Objeto de 
    // tipo Persona se va a validar, para ello en el parametro vamos a 
    // anteponer la anotación de @Valid e importamos la anotación de 
    // javax.validation.Valid (Recuerda haber añadido al pom.xml la dependencia
    // de <artifactId>spring-boot-starter-validation</artifactId>)
   
    // Y en dado caso de que tengamos errores entonces tenemos que pasar también
    // el parametro de "Errors errores" e importamos la anotación de
    // org.springframework.validation.Errors
    
    // En caso de que tengamos errores vamos a preguntar con un if si hemos
    // recibido algun error con el método hasErrors(), en cuyo caso lo que 
    // vamos a hacer es retornar a la vista de "modificar" y en caso de no
    // tener errores vamos a proceder con lo que ya tenia ese método.
    
    // Estos 2 atributos deben de estar juntos, la validación y el manejo de
    // errores.
    
    // Podemos incluir más atributos por ejemplo con  "Model model" en dado caso
    // de que queramos agregarlos, al agregarlos podemos ver si existe en la
    // vista y manejar una lógica ahí.
    
    // Con esto tenemos lista nuestra validación.
}
