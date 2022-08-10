package mx.com.luis.dao;

import org.springframework.data.repository.CrudRepository;
import mx.com.luis.domain.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Eduardo
 * 
 */
// Para crear nuestra Clase de repositorio anteriormente haciamos lo siguiente
// sobre este tipo de clases erá que agregabamos la anotación de: @Repository
// sin embargo, está anotación se agregaba sobre la implmentación, es decir, 
// sobre la clase concreta, pero por otro lado antes teniamos que crear una
// interface y posteriormente una implementación, al día se hoy esto ya se ha 
// simplificado y vamos a ver estás nuevas prácticas, ya no agregaremos esa
// anotación, si no que nuestra interface va a extender de manera directa
// de otra interface llamada CrudRepository<> y la ventaja es que cualquier tipo
// de Clase Dao vamos a poder crearla utilizando esté mismo repositorio, así
// que ya no vamos a estar agregando los Clásicos métodos de insertar, listar
// actualizar, eliminar, etc, a cada una de nuestras clases, esto ya no va a ser
// necesario. Si no que está Clase de CrudRepository<> es precisamente el
// trabajo que va a realizar.
// Así que debido a que va a ser un tipo generico que vamos a poder utilizar en 
// cualquiera de nuestras Clases de tipo Dao, entonces necesitamos especifícar
// cual es el tipo de Identidad que va a manejar, así que indicamos el tipo
// en esté caso serán de tipo Persona, y posteriormente necesitamos indicar
// dependiendo del tipo que estemos utilizando, la llave primaria que estemos
// trabajando, en este caso el tipo de la llave primaria es de tipo long.

//public interface PersonaDao extends CrudRepository<Persona, Long>{
public interface PersonaDAO extends JpaRepository<Persona, Long>{
    
    // Una de las grandes ventajas de realizar está nueva práctica es que ya no
    // es necesario crear una implementación, normalmente lo que tendríamos que 
    // hacer anteriormente es crear una clase que implemente está interface, en
    // este caso SpringBoot ya va a crear una implementación por Defautl, así 
    // que ya no va a ser necesario crear una implementación por nuestra cuenta
    // y si damos ctrl + clic sobre está interfaz podemos observar los clásicos
    // métodos de save, saveAll, findById, existById, findAll, findAllById,
    // count, deleteById, delete, etc...
    // Estos son los más comunes cuando estamos trabajando con nuestras clases
    // de Identidad, ejemplo el de findAll nos va a regresar toda la lista
    // de objetos de tipo persona, y ese es el método que vamos a utilizar 
    // a continuación.
    
    // Así que simplemente con extender de está Clase CrudRepository entonces
    // ya podemos empezar a trabajar con nuestra Clase PersonaDao
    
    // Ahora como siguiente paso necesitamos inyectar está interface PersonaDao
    // dentro de nuestro controlador así que vamos a nuestro controlador,
    
    // Cabe destacar que aquí podemos agregar métodos personalizados ya que
    // obviamente, puede ser que estos métodos no sean suficientes para 
    // trabajar con nuestro objeto de tipo persona, por ejemplo para ejecutar
    // un Query personalizado para que regrese todos los objetos de tipo persona
    // ordenados por el apellido (por poner un ejemplo).
    // Entonces esté tipo de métodos los definimos aquí.
    
    // Pero al final la campiaremos por JpaRepository porque tiene una que otra mejora.
    // Aunque se puede usar cualquiera de las 2.
}
