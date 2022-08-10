package mx.com.luis.service;



import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import mx.com.luis.dao.UsuarioDAO;
import mx.com.luis.domain.Usuario;
import mx.com.luis.domain.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Lo que vamos a hacer es convertir está Clase en una Clase de servicio y está
// Clase la va a utilizar Spring Security, por lo tanto el nombre de este bean
// debe ser tal cuál lo vamos a escribir con el nombre de: "userDetailsService".
// No podemos utilizar otro nombre ya que esto en automático lo  va a utilizar Spring,
// Si cambiamos el nombre estonces ya no lo va a reconocer y tenemos que agregar
// más configuraciones para que pueda reconocer está Clase.        
@Service("userDetailsService")
@Slf4j// Escribimos la Clase de lommbok para mandar datos a consola.
// Está clase debe de implementar la interface de UserDetailService para que
// pueda funcionar también la seguridad de Spring cuando estamos trabajando
// con está implementación de JPA e implementamos todos sus métodos.
public class UsuarioService implements UserDetailsService{

    // Inyectamos la Clase de UsuarioDAO y las importamos.
    @Autowired
    private UsuarioDAO usuarioDAO;
    
    // va a obtener el usuario (El Objeto de usuario) filtrado por un username
    // Así que cambiamos el parametro a username.
    // Recordemos que los métodos de nuestras Clases de Servicio deben de ser 
    // Transaccionales. Pero en este caso como será de solo lectura será de readOnly
    // Importamos de: org.springframework.transaction.annotation.Transactional;
    // Recuerda ponerla despues de @Override
    @Override
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Agregamos la implementación a este método.
        // Se va a apoyar de la clase UsuarioDAO, así que vamos a inyectarla.
        // Con esta clase de UsuarioDAO es que vamos a interactuar tanto de usuario
        // como de roles.
        // Importamos la Clase de Usuario.
        Usuario usuario = usuarioDAO.findByUsername(username);
        
        // Y vamos a revisar si el Objeto de Usuario es igual a null entonces
        // arrojaremos una exepción.
        // Ahora si tenemos éxito encontrando el Usuario entonces vamos a 
        // recuperar los roles asociados a este usuario.
        if(usuario == null){
            throw new UsernameNotFoundException(username);
        }
        
        // Así que vamosa definir una variable para recuperar los roles.
        // va a ser de tipo ArrayList() y usaremos el tipo de <GrantedAuthority>
        // ya que ese es el tipo que necesita Spring para poder manejar los roles.
        // No podemos utilizar directamente nuestra Clase de rol.
        var roles = new ArrayList<GrantedAuthority>();
        
        // Ahora lo que vamos a hacer es recorrer cada uno de los roles
        // con nuestra variable del usuario del Objeto Usuario que hemos encontrado.
        // y solicitamos todos los roles. Recordamos que ese atributo de roles
        // lo hemos definido en nuestra Clase de Usuario. Y debido a que agregamos
        // la anotación de @Data entonces en automático se creo el método de
        // gerRoles() tanto el método getRoles y setRoles se crearon para este
        // atributo.
        // Importamos la Clase de Rol.
        for(Rol rol: usuario.getRoles()){
            // Y vamos a envolver cada uno de los roles que hemos encontrado
            // en un nuevo Objeto de tipo SimpleGrantedAuthority y para poder
            // crear el Objeto simplemente le pasamos el nombre del Rol que
            // estamos recuperando.
            roles.add(new SimpleGrantedAuthority(rol.getNombre()));
        }
        
        // Por último esté metodo lo que debe de regresar es un Objeto de tipo
        // UserDetails con todos los elementos que ya hemos recuperado, así
        // que haremos lo siguiente:
        // Esta Clase de User() es una Clase de Spring, no es una Clase que 
        // nosotros hayamos definido, así que importamos está Clase del
        // paquete de org.springframework.security.core.userdetails.UserDetails;
        // Y ahora vamos a construir un Objeto de tipo usuario.
        // Recuperamos el nombre, la password y los roles debe ser de la variable
        // que implemento cada uno de los Objetos de GrantedAuthority con
        // cada rol que hemos recuperado.
        return new User(usuario.getUsername(), usuario.getPassword(), roles);
        
        // Así que con esto ya estamos creando un nuevo Objeto de tipo User
        // pero esté Objeto es de tipo Spring el cuál se utiliza en Spring
        // Security de manera automática, por ello es que recuperamos y le
        // pasamos los valores de username, password y los roles asociados.
        
        // Es todo lo que necesitamos en este método para que cargue la 
        // implementación correctamente según el usuario que hemos proporcionado
        // por eso el método se llama: loadUserByUsername() y regresa un Objeto
        // de tipo UserDetails.
        
        // Así que esto todo lo que vamos a realizar para poder implementar 
        // correctamente el Servicio de Usuario que vamos a utilizar 
        // posteriormente.
        
        // Vamos a configurar ahora modificando la Clase de SecurityConfig
        // con la cuál ya no vamos a crear Objetos en memoria directamente,
        // si no que los vamos a sustituir para poderlos obtener de la 
        // Base de Datos.
        
    }
    
}
