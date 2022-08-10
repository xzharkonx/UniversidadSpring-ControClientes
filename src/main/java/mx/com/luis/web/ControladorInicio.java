/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// Se movera al paquete .web para trabajar de otra manera.
// Cambiamos de mx.com.luis a mx.com.luis.web, luego le damos clic en el foquito
// de error y damos clic en Move Class to correct folder, esto nos creará un
// paquete con ese nombre.

// Ahora dejaremos esto de lado y trabajaremos en ControladorWeb.java

package mx.com.luis.web;

import java.util.ArrayList;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import mx.com.luis.domain.Persona;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Eduardo
 */

// En lugar de que sea un controlador Rest lo vamos a convertir a un
// controlador de tipo Spring MVC.
// @RestController
// Así que vamos a utilizar la anotación de Controller.
// La configuración es muy similar, así que vamos a seguir utilizando la
// anotación GetMapping para procesar la petición Get e indicamos el path /.
@Controller
@Slf4j // Anotación de loombok para ver datos por consola.
public class ControladorInicio {
    
    // Agregamos una variable para desplegar la información que hemos
    // colocado desde nuestro archivo applicacition.properties
    // Le colocamos la anotación @Value() (no importamos de la libreria lombok)
    // utilizamos la importación se SpringFramework
    // Dentro le pasamos la llave que le hemos colocado
    // Lo que hace es buscar ese valor en el archivo applicaction propertioes
    // y va a tomar ese valor y lo va a inyectar a esa variable.
    @Value("${index.saludoProperties}")
    private String saludoProperties;

    // Trabajaremos con la forma más simple al día de hoy para compartir
    // atributos para desplegarla en las páginas, recibiremos el Objeto Model
    @GetMapping("/estatica")
    public String inicio(Model model){
        // Con esté metodo podremos ver información en la consola, 
        // la que sea que le pasemos.
        // También incluye los métodos de: info(), warn(), error(), etc.
        log.info("Ejecutando el Controlador Spring MVC");
        // log.debug("Más detalle del controlador, desde el archivo Properties");
        
        // Ahora el String que va a regresar, en lugar de simplemente una cadena
        // que se muestre en el navegador, ahora simplemente va a regresar el
        // nombre de la página que se va a desplegar en el navegador.
        // ahora podemos despleguar una página por ejemplo "index" pero puede 
        // ser cualquier nombre.
        
        // ENVIANDO INFORMACIÖN A NUESTRO HTML CON THYMELEAF
        
        // Definimos una variable local y ponemos un mensaje.
        var mensaje = "Hola Mundo con Thymeleaf";
        // Este nos pedira un mapa, pero para ello seleccionamos la opción de 
        // String y Obj, la llave será el String "mensaje" y el valor será
        // nuestra variable mensaje.
        // Dentro de nuestro HTML la vamos a llamar.
        model.addAttribute("mensaje", mensaje);
        
        // Saludo desde el archivo applicaciton.properties
        model.addAttribute("saludoProperties", saludoProperties);
        
        // UTILIZANDO UN JAVABEAN DE UNA CLASE.
        var persona = new Persona();
        persona.setNombre("Luis");
        persona.setApellido("Garcia");
        persona.setEmail("xzharkonx@gmail.com");
        persona.setTelefono("7122302540");
        model.addAttribute("persona", persona);
        
        // UTILIZANDO OTRO JAVABEAN PARA CREAR UNA LISTA Y RECORRERLA EN EL FRONT
        var persona2 = new Persona();
        persona2.setNombre("Eduardo");
        persona2.setApellido("Mercado");
        persona2.setEmail("zharkon1.com");
        persona2.setTelefono("7122302540");
        model.addAttribute("persona2", persona2);
        
        // Creamos un ArrayList de tipo Persona
        // Podemos utilizar "var" para la inferencia de tipos.  
        // La inferencia de tipos es una característica predominante de los 
        // lenguajes de programación. La inferencia de tipos asigna 
        // automáticamente un tipo de datos a una función sin necesidad de que 
        // el programador lo escriba.
        var personas = new ArrayList();
        personas.add(persona);
        personas.add(persona2);
        model.addAttribute("personas", personas);
        
        // También para agregar la misma lista de arriba podemos agregar el
        // siguiente código:
        // var listaPersonas = Arrays.asList(persona,persona2);
        // Creamos una lista vacía para comprobar si está vacía con if 
        // utilizando thymeleaf.
        var listaPersonas = Arrays.asList();
        model.addAttribute("listaPersonas", listaPersonas);
        
        // Ahora podemos ir a nuestra vista (index) y recorrer la lista de personas.
        
        return "index2";
        
        // INFORMACIÖN ADICIONAL IMPORTANTE.
        
        // La tecnología de Thymeleaf por default utiliza archivos de tipo
        // HTML, en lugar de archivos como puede ser JSP.
        
        // una de las ventajas de utilizar este tipo de controladores y la 
        // anotación de Controller es que aunque estamos utilizando la
        // tecnología de los Servelets por detrás, ya no es necesario
        // extender de ninguna otra clase. (Como en su momento donde extendiamos
        // de la Clase HttpServerlet.
        
        // Para crear nuestro HTML hay que crearlo en Other Sources, luego
        // abrir src/main/resources y dentro tenemos la carpeta de
        // static y templates.
        
        // Dentro de la carpeta Static podemos añadir recursos como los que 
        // pueden ser imágenes, archivos de tipo CSS o JavaScript.
        
        // En la carpeta de Templates vamos a agregar precisamente las páginas
        // en este caso de la tecnología de Thymeleaf.
        // Podemos ponerla directamente dentro de ese Paquete/Carpeta o crear
        // SubPaquetes/SubCarpetas.
        
        // El nombre de nuestro HTML debe ser el mismo que el de la cadena
        // que vamos a retornar.
        
        // Pdemos observar que con Spring es muy simple declarar está variable
        // de tipo Model, cuando trabajamos con Spring se aplica el concepto
        // de inyección de dependencias así que debido a que nuestra clase de
        // controller ya es una clase que está disponible en la fábrica de
        // Spring entonces dentro de los métodos de tipo mapping que estamos
        // declarando podemos recibir diferentes argumentos simplemente con 
        // declararlos, no es necesario agregar ninguna otra anotación, ya que
        // simplemente con que esté método (inicio) este anotado con Getmapping
        // es suficiente para que podamos recibir otro tipo de parametros
        // en nuestro método, pero podemos recibir otros argumentos (además de 
        // Model) y en distinto orden, esto es parte de la flexibilidad de 
        // Spring.
        
        // Spring aplica uno de los principios de Holliwood, es decir, no me
        // llames, yo te voy a llamar.
        // Esto quiere decir que nosotros no vamos a instanciar estás clases
        // si no que la fábrica de Spring se va a encargar de crear estás
        // instancias y nos va a regresar una instancia de manera automática
        // para que nostros podamos utilizar estás clases, en esté caso
        // el argumento model.
        
    }
}
