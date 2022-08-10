package mx.com.luis.web;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 *
 * @author Eduardo
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Bean
    public LocaleResolver localeResolver(){
        
        var slr = new SessionLocaleResolver();
        slr.setDefaultLocale(new Locale("es"));
        
        return slr;
        
    }
    
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        
        var lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registro){
        registro.addInterceptor(localeChangeInterceptor());
    }
    
    // Lección 9: Seguridad con Spring Framework -------------------------------
    // Agregaremos una configuración para que nos permita acceder a /
    // Haremos el mapping de la url del path por dedault /
    // Por lo que este método nos servirá para mapear paths que no necesariamente
    // pasan por el controlador.
    @Override
    public void addViewControllers(ViewControllerRegistry registro){
        // Ahora vamos a registrar algunas de las vistas que vamos
        // a utilizar en nuestra aplicación. y le ponemos un nombre a esa vista.
        registro.addViewController("/").setViewName("index");
        // También agregamos el mapeo de la vista del logging
        registro.addViewController("/login");
        // Mapeamos la página de error de 403 que agregamos en templates.errores.
        registro.addViewController("/errores/403").setViewName("/errores/403");
        
    }
    // También registraremos una página de login personalizada para que en dado
    // caso de que no hayamos hecho loging, entonces Spring Security nos muestre
    // la página de logging. Por lo que crearemos un nuevo HTML llamado login
    // en templates.
    
    // -------------------------------------------------------------------------
}

// Lección 8 Manejo de Mensajes e Internacionalización con Spring --------------

//Lo primero que vamos a hacer en nuestra Clase de WebConfig debemos de 
//registrarla como un bean para ello debemos de colocarle la anotación de 
//@Configuration, si no, nada funcionará.

// Ahora para manejar el concepto de internacionalización
//es configurar un Listener, el cuál ya viene creado en Spring pero debemos de
//configurarlo así que para ello creamos esta nueva Clase.
//Está Clase va a implementar una interface de WebMvcConfigurer, ya que vamos a
//implementar varios métodos de esta interface, la importamos de:
//org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//Si entramos a esta interface con Ctrl + Clic podemos observar que tenemos
//varios métodos, dentro de estos métodos tenemos por ejemplo el concepto de 
//addInterceptors para agregar un interceptor, el cuál vamos a necesitar para
//manejar el concepto de internacionalización y poder manejar varios idiomas.

//Pero debemos definir varios elementos en primer lugar, así que lo primero que
//vamos a definir es un bean conocido como LocaleResolver, así que vamos a crear
//un nuevo objeto de está Clase.
//Cuando ya estamos trabajando con las últimas versiones es muy sencillo declarar
//esté tipo de beans.
//Simplemente para crear un bean agregamos la anotación de @Bean y vamos a 
//definir un método que lo que va a hacer es crear una instancia del Objeto que
//nos intereza y en automático al haber agregado está anotación de @Bean entonces
//lo va a agregar al contenedor de Spring. Posteriormente definimos el método
//que va a crear neustro objeto que nos intereza lo llamaremos: LocaleResolver y 
//el método también se llamara: localeResolver. También importamos la anotación
//de @Bean de: org.springframework.context.annotation.Bean;
//Y también la de LocaleResoler, lo exportamos de:
//org.springframework.web.servlet.LocaleResolver;

//Ahora lo que haremos será crear un nuevo Objeto de tipo LocaleResolver y al
//anotar este método con la anotación @Bean entonces cuando se mande a llamar
//este método en automático va a agregar este @Bean al contexto de Spring,
//así que vamos a poder utilizarlo como cualquier otro bean compartido en 
//nuestro contexto de Spring en el contenedor de Spring.

//Así que agregamos el siguiente código:
//        var slr = new SessionLocaleResolver();
//Definimos una variable de tipo: SessionLocaleResolver y creamos un nuevo Objeto
//de este tipo, estás ya son Clases de la Api de Spring, simplemente las estamos
//utilizando para poder configurar correctamente el manejo de múltiples idiomas,
//importamos está clase del paquete de:
//org.springframework.web.servlet.i18n.SessionLocaleResolver;
//Este paquete se llama i18n ya que hace referencia a la palabra
//internacionalization pero como es bastante larga, entre la i y la n existe 18
//caracteres más de está palabra, por eso este concepto también se le conoce
//como i18n.

//Posteriormente con esta variable mandamos a llamar al método setDefaultLocale
//y posteriormente podemos especificar el idioma con el que queremos que se 
//maneje, en este caso vamos a crear un nuevo Objeto de tipo Locale() e
//importamos está Clase de: java.util.Locale;
//Y podemos especificar en este Constructor el lenguaje que queremos manejar por
//default, podría ser el por ejemplo el lenguaje en Español: "es" o también
//por ejemplo el lenguaje en Inglés: "en"

//Y por último lo que vamos a hacer es retornar el Objeto slr;
//Ya que al final esté método regresa un tipo LocaleResolver ya que esto es una 
//interface, podemos verlo al hacer Ctrl + Clic en la clase de LocaleResolver
//Pero la implementación es SessionLocaleResolver y podemos hacer Ctrl + Clic
//en ella, y esta hereda a su vez de AbstractLocaleContextResolver que al
//hacer de nuevo Ctrl + Clic en está vemos que implementa LocaleContextResolver
//y si nuevamente entramos con Ctrl + Clic a está interface finalmente vemos que
//extiende o hereda de LocaleResolver, esto no es tan importante entenderlo a 
//detalle, con que sepamos como funciona esté codigo no es necesario entender
//a detalle cada una de estás Clases o Interfaces.        
//Simplemente con esto ya lo empezamos a utilizar de manera correcta.

//Así que estamos definiendo está variable LocaleResolver, estamos especificando
//cuál va a ser nuestro idioma por default que vamos a empezar a trabajar y esto
//se va a asociar con nuestro archivo de propiedades que hemos agregado en
//other Sources, src, default package y en nuestro archivo de messages.properties
//este archivo será nuestro archivo base pero a partir de este archivo también
//podemos crar archivos de propiedades en otros lenguajes, esto es lo que haremos
//a continuación. Pero primero terminaremos de configurar este archivo.

//Así que como siguiente paso necesitamos configurar un Interceptor para que
//podamos cambiar de idioma de manera dinámica. Así que agregamos otro bean
//definimos la anotación de @Bean, definimos un nuevo método publico como 
//LocaleChangeInterceptor, está es ahora la interface que vamos a utilizar y el
//método se llamara localeChangeInterceptor para que podamos modificar
//dinamicamente el lenguaje (de habla y lectura) con el que estamos trabajando
//e importamos de esta Clase del paquete de:
//org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
//Y vamos a especificar cuál va a ser el parametro para cambiar el lenguaje,
//así que definimos una variable llamada "lci" y creamos un nuevo objeto de este
//tipo: new LocaleChangeInterceptor(); en este caso es una Clase en concreto 
//y no una interfaz.
//Una vez que ya hayamos definido está variable especificamos cuál va a ser el
//parámetro con el método setParamName() y especificamos cuál va a ser el 
//parámetro para cambiar el lenguaje, en este caso le pasamos "lang" y cuando
//adjuntemos esta cadena en nuestras urls podemos indicar cuál sera el lenguaje
//que vamos a utilizar utilizando la sintaxis de internacionalización en este
//caso según el país que vayamos a utilizar.

//Si no estamos familiarizados con este manejo de internacionalización para
//especificar cuál es el país y lenguaje que se quiere utilizar podemos buscar
//una lista de elementos de internacionalización, algunas disponibles en:
//https://medium.com/i18n-and-l10n-resources-for-developers/internationalization-with-java-locale-3119ed1bab0a
//https://www.roseindia.net/tutorials/i18n/locales-list.shtml
//https://www.w3.org/International/O-charset-lang.html

// Finalmente retornamos el objeto lci

//Por último registramos este interceptor
//Para ello sobreescribimos agregando la anotación @Override y 
//el método de addInterceptors() y le pasamos
//un objeto llamado InterceptorRegistry el cuál importaremos de:
//org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//ahora a esa variable de registro utilizamos el método de addInterceptor
// para registrar nuestro interceptor y le pasamos el método que creamos de
//localeChangeInterceptor()

//Hasta aquí ya hemos configurado cada uno de los elementos que necesitamos
//para que se pueda modificar de manera dinámica el lenguaje que vamos a
//utilizar en nuestra aplicación.

//Ahora copiaremos nuestro archivo de messages.properties y lo copiaremos 2
//veces para que maneje el idioma Español e Inglés pero le cambiaremos los 
//nombres a:
//messages_en.properties (Inglés)
//messages_es.properties (Español)
        
//Al hacer Clic Derecho y en open podemos ver como se nos habre una ventana
//mostrandonos la Clave y Valor y con diferentes opciones        
//Ahora solo debemos de cambiar el lenguaje que tenemos en el archivo _en
//cambiando sus valores a inglés. O podemos ir directamente a ese archivo 
//y editarlo.

// Por último en nuestro paquete de templates.layaout en plantilla
// en la parte del footer agregaremos un div con un link de thymeleaf con el
// cuál le vamos a pasar el parametro de lenguaje.






        