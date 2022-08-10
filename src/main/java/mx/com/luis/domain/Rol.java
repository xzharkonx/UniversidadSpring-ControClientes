// Crearemos esta Clase para nuestros Roles
// Creamos primero esta clase ya que esta no va a tener dependencia con
// ninguna otra Clase, en cambio la Clase de Usuario va a tener una relación
// Con la tabla de rol.
package mx.com.luis.domain;

// Importamos estás clases de javax porque usaremos el standard de JPA.
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import lombok.Data; 

@Entity
@Data // Anotación de loombok para generar los gettes, setters y demás de forma automática.
@Table(name="rol") // Opcional, para el nombre de la tabla y no tener problemas.
public class Rol implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id // Indicamos que será la llave primaria.
    // Tipo de generación. Ya que especificamos en MySQL que será de tipo
    // autoincrementable, la dejamos de está forma para hacer este mapeo.
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long idRol;
    
    @NotEmpty // Validación para que no sea vacio ni nulo.
    private String nombre;
    
    // Esto es todo por está clase ya que el mapero de idUsuario realmente
    // no lo vamos a utilizar, ya que en este lado de la relación del rol hacia
    // usuarios no vamos a hacer el mapeo de idUsuario, esto lo vamos a hacer
    // de la Clase de Usuario hacia Rol.
    
    
}
