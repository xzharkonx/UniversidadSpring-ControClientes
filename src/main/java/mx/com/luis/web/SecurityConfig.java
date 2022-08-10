package mx.com.luis.web;

// Clase para personalizar los usuarios que vamos a utilizar para hacer logging
// en nuestra aplicación.        

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// Se quitará esta importación porque ya no usamos usuarios esáticos.
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // Anotación para la seguridad web.
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    
    // Quitaremos todo esté metodo para ya no carcar los usuarios desde
    // un contexto estático.
    /**
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
      // Aquí agregaremos nuevos usuarios creados en memoria y se desactivará
      // el default.
      auth.inMemoryAuthentication()
              .withUser("admin")
                .password("{noop}admin1234") // {noop} sirve para indicar que no sea necesario encriptar.
                .roles("ADMIN","USER")
              .and() // Y agregamos otro usuario
              .withUser("user")
                .password("{noop}zelda1234")
                .roles("USER")
              ;
              // Ahora ya podremos acceder con estás credenciales.
              
    }
    **/
    
    // Cargaremos los usuarios desde la base de datos.
    
    // Vamos a empezar a inyectar el servicio de usuarios que hemos creado
    @Autowired
    private UserDetailsService userDetailsService;
    
    // Ahora definimos el tipo de encriptación que vamos a utilizar.
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    // Ahora agregaremos otro método para indicar que vamos a utilizar la 
    // implementación de userDeailService, pero además le vamosa pasar un 
    // argumento de formá automática a este método.
    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception{
        build.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    
    
    // Con el siguiente método restringiremos las URLS de nuestra applicación
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        // De manera muy similar a los usuarios podemos construir las URLs a las
        // cuales queremos restringir.
        // Aquí agregaremos los paths que restringiremos.
        // Con doble asterisco indicaremos que todo lo que
        // este despues de ese path estára restringido.
        // Luego especificaremos los Roles que pueden acceder a estos paths.
        // Luego indicaremos quien puede acceder al path raíz /.
        // También indicaremos la configuración de login.
        http.authorizeRequests()                            
                .antMatchers("/editar/**", "/agregar/**", "/eliminar/**")
                    .hasRole("ADMIN")
                .antMatchers("/")
                    .hasAnyRole("ADMIN", "USER") // Como serán varios se llama hasAnyRole
                .and() // Aquí indicaremos cuál será la página de login
                    .formLogin() // Para el login ya personalizado
                    .loginPage("/login") // Indicamos el login/path (html de templates).
                .and() // Le inidicamos cuál será nuestra página de error.
                    .exceptionHandling().accessDeniedPage("/errores/403") // Especificamos cuál será la página.
                ;
                }
    // Debido a que el path / (inicio) ya está restringido, entonces ya no podemos
    // acceder directamente a está pagina, así que agregaremos una configuración
    // en el archivo de WebConfig de esté paquete.
    // Luego de estás configuraciones y de agregar una págian de login ya podemos
    // acceder.
}

// Lección 9: Seguridad con Spring Framework -----------------------------------

// Esta clase heredará de WebSecurityConfigurerAdapter para poder configurar
// los usuarios que vamos a utilizar.

// utilizaremos el método de configure para agregar más usuarios y personalizar
// los usuarios que vamos a personalizar para hacer logging en nuestra apliación
// Posteriormente vamos a agregar otro método también se llamará configure pero
// recibirá un parámetro de http security y lo utilizaremos para restringir las
// urls de nuestra aplicación web, ya que vamos a restringir a los usuarios y
// a las páginas que pueden visualizar según el rol que se les haya asignado.