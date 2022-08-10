package mx.com.luis.service;

import java.util.List;
import mx.com.luis.domain.Persona;
/**
 *
 * @author Eduardo
 */


// Aqyí crearemos nuestra capa de servicios, vamos acrear una interface y una 
// implementación.
// Esta interface va a tener un contrato/los métodos que vamos a implementatar
// en nuestra clase que posteriormente vamos a crear.
public interface PersonaService {
 
    // Creamos un método público que va a regresar una lista de objetos de tipo
    // persona.
    public List<Persona> listarPersonas();
    
    // Agregamos otro método tipo void llamado guardar y es el objeto que se
    // va a aguardar.
    public void guardar(Persona persona);
    
    // Posteriormente creamos otro método que va a ser eliminar y resive un 
    // objeto de tipo persona a eliminar.
    public void eliminar(Persona persona);
    
    // También otro método que nos retornará un objeto de tipo persona para
    // buscarlo por el objeto que le hemos indicado.
    public Persona encontrarPersona(Persona persona);
    
    // De momento lo que vamos a hacer es completar nuestro codigo para poder
    // crear nuesta aplicación de tipo CRUD (Create, Read, Update y Delete).
    // Para poder ejecutar todas esas acciones desde la aplicación web.
    
    // Ahora lo que haremos será agregar nuestra implementación.
    // Por lo que ahora crearemos la Clase PersonaServiceImpl dentro de este
    // paquete.
}
