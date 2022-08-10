
package mx.com.luis.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Data // Anotación de loombok para generar los gettes, setters y demás de forma automática.
@Table(name="usuario") // Opcional, para el nombre de la tabla y no tener problemas.
public class Usuario implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    
    @NotEmpty // Validación para que no sea vacio ni nulo.
    private String username;
    

    @NotEmpty // Validación para que no sea vacio ni nulo.
    private String password;
    
    // Finalmente vamos a hacer el mapeo de la Clase de usuario y la Clase de rol
    // Así que un usuario puede tener múltiples roles.
    // Por lo tanto utilizaremos la anotación de Uno a Muchos: @OneToMany
    //@OneToMany
    // Posteriormente indicamos la columna que se va a relacionar entre la tabla
    // de usuario y la tabla de rol, por lo tanto, la columna que relaciona
    // a la tabla de rol con la tabla de usuario es la columna de id_usuario.
    // Por lo tanto agregamos la anotación de: @JoinColumn(name="nombre_columna").
    //@JoinColumn(name="id_usuario")
    // Posteriormente definimos un atributo que va a ser una lista de objetos:
    // List<> de tipo Rol: <Rol> | List<Rol> y se va a llamar roles. 
    // Para poder recuperar los roles asociados a un usuario.
    
    @OneToMany
    @JoinColumn(name="id_usuario")
    List<Rol> roles;
    
    // Con esto ya tenemos toda la información que necesitamos para poder
    // trabajar con nuestras Clases de usuario y rol.
    
    // Posteriormente crearemos nuestros DAO.
}
