// Para poder encriptar las contraseñas de nuestros usuarios lo haremos
// de la siguiente forma, aunque la mejor forma de hacerlo es creando un flujo
// que nos permita crear los usuarios y al capturar la contraseña la encripte
// y la guarde en la base de datos.

// Creamos un método para encriptar nuestra password y la encriptamos.
// Así que para encriptarla utilizaremos la Clase BCryptPasswordEncoder
// Ejecutando el main haciendo Clic derecho y run file de este archivo.
// Puede tardar un poco.
// Luego veremos en la consola la encriptación la cuál copiaremos y pegaremos
// en la base de datos de la tabla de usuarios.
package mx.com.luis.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncriptarPassword {
    
    public static void main(String[] args){
        var password = "admin1234";
        var passwordUser = "zelda1234";
        // Podemos crear las passwors que querramos y cada vez que las 
        // ejecutemos de nuevo serán diferentes.
        System.out.println("password Admin:" + password);
        System.out.println("password encriptado Admin:" + encriptarPassword(password));
        
        System.out.println("password User:" + passwordUser);
        System.out.println("password encriptado User:" + encriptarPassword(passwordUser));
    }
    
    public static String encriptarPassword(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
            
}
