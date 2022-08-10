package mx.com.luis.dao;
import mx.com.luis.domain.Usuario;


import org.springframework.data.jpa.repository.JpaRepository;

//La haremos de forma similar a como lo hicimos con la Clase PersonaDAO pero 
//en este caso utilizaremos la Clase de JpaRepository, podemos utilizar la de
//PersonaDAO pero está tiene unas que otras mejoras esta Clase.  
// Importamos  la Clase de Usuario de: mx.com.luis.domain.Usuario;
public interface UsuarioDAO extends JpaRepository<Usuario, Long>{
    // Para empezar a configurar la seguridad que va a utilizar Spring con
    // nuestra Clase de UsuarioDAO necesitamos definir un método llamado:
    // findByUsername()
    // Este lo anotaremos tal cual lo estamos visualizando ya que es parte de
    // la seguridad de Spring, esto lo solicita el Framework de Spring Security.
    // Posteriormente definimos un parametro, el de username.
    // Entonces en automático Spring va a recuperar un Objeto de tipo usuario
    // filtrado por el username.
    Usuario  findByUsername(String username);
    
    // Como siguiente paso vamos a definir el servicio de está Clase.
}
