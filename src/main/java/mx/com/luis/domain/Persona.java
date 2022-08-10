/*
 * Recordemos que está clase debe de estar dentro del paquete Principal
 * donde se enecuentra la Clase de HolaMundoSpringApplication, de lo contrario
 * no va a encontrar está Clase, ya que las Clases de Spring las va a 
 * reconocer siempre y cuando esté dentro del mismo paquete o dentro de
 * subpaquetes de esté mismo paquete.
 */
package mx.com.luis.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

/**
 *
 * @author Eduardo
 */

// Podemos utilizar el proyecto lombok (recordemos que lo hemos agregado en
// la configuración de nuestras dependencias),nos ayudará mucho para la creación
// de nuestro codigo restante (Getters, Setters, Constructor, hashCode(), equals()
// y toString()) y no tener que hacerlo manualmente.
// Solamente agregamos la anotación de @Data y podemos observar en la ventanita
// izquierda inferior de nuestro navegador como las marca ahora.

// Simplemente con esa anotación podemos dejar muy limpio el código y de manera
// automática la anotación del proyecto de lombok genera todo nuestro codigo
// para que sea un JavaBean.
// el estándar y no de Hibernate, Hibernate ya no lo vamos a utilizar de manera
@Data

// Ahora vamos a convertir está clase de Persona en una Clase Identidad.
// Esto lo podemos ver más a detalle en la Sección de Hibernate.
// Con esto ya estamos convirtiendo la Clase en Identidad e importamos de
// la anotación javax.persistence.Entity ya que vamos a utilizar JPA, es decir,
// independiente si no que todo va a ser a partir de JPA y la implementación
// de JPA es Hibernate (como lo pusimos en la explicación en el archivo
// application properties.
@Entity

// Vamos a agregar otra anotación (no es necesario agregarla) pero cuando estamos
// trabajando con otros sistemas operativos pueden existir errores al realizar
// el mapeo de la tabla. Si observamos el nombre de la tabla se escribio en 
// minúsculas. Pero el nombre de está clase se escribio con una mayúscula y que
// los querys tengan errores, así que para este tipo de problemas podemos
// agregar lo siguiente, agremaos dentro de la anotación la variable name
// y la igualamos al nombre de la tabla que tengamos en la DB.
@Table(name = "persona")

// Como siguiente paso vamos a implementar la interfaz Serializable
public class Persona implements Serializable {// en ocaciones con este simple hecho de que no corresponden puede provocar que
    
    // Agregamos el siguiente atributo, así con esto la Clase Identidad Persona
    // ya esta implementando la interface de Serializable.
    // Es para poder convertir el objeto a bytes por que se envia por la red o 
    // entre servidores, para poderlo transmitir entonces se convierte a bytes, 
    // para ello se necesita serializar.
    private static final long serialVersionUID = 1L;
    
    // Lo que nos falta agregar es el mapeo de la llave primaria, hay que revizar
    // la tabla y en las columnas tenemos además de los atributos que ya hemos
    // mapeado tenemos como extra el id_persona, así que vamos a agregarlo.
    // Le indicamos la anoación @Id para indicar que este es el Id de la tabla.
    @Id
    // Y agregamos la forma de como agregar está llave primaria.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_persona;
    
    // Con esto ya tenemos lista nuesta Clase Identidad para Mapear a la DB.
    
    // Con esta antación decimos que está cadena ya no puede tener valor vació
    // También existe la de @NotNull pero si estamos trabajando con cadenas
    // entonces vamos a trabajar la anotación de @NotEmpty ya que esta anocación
    // valida que no sea vacia, en cambio la anotación de @NotNull únicamente
    // valida que no sea null pero no valida cadena vacia es decir: "" o ''.
    @NotEmpty
    private String nombre;
    @NotEmpty
    private String apellido;
    
    // Para el email podemos agrega una validación extra con la anotación
    // @Email y la importamos del paquete javax.validation.constraints.Email
    @NotEmpty
    @Email
    private String email;
    // Dejamos sin anotar este campo de telefono para observar como podemos
    // dejar vacia esta información y que no arroje ningún error.
    private String telefono;
    // Ahora para seguir con la validación hay que modificar la vista para ello
    // vamos a la vista de modificar.html ya que aquí es donde está el formulario.
    
    
}

// Ahora ya podemos utilizarla en nuestros controladores.

// Ahora el siguiente paso es crear nuestras Clases de tipo DAO (Data 
// Access Object) las cuales nos van a permitir conectarnos hacia la base de
// datos para ejecutar los querys (editar, modificar, listar o eliminar) a un
// registro en este caso de la Tabla de Persona.

// Resumen: Con esto ya con esto hemos trabajado en la capa de datos, en la cuál
// hemos creado una Clase Identidad y en la base de datos nos estamos conectando
// utilizando JPA bajo la implementación de Hibernate y nos estamos conectando
// a la base de datos de MySQL. 
// Ahora para que podamos recuperar información desde la vista (todavía no
// implementaremos la capa de negocio) pero vamos a conectarnos a la capa de
// datos desde la presentación. Así que para podernos conectar desde la vista
// y obtener la información vamos a utilizar lo que se conoce como reposiorios
// de Spring. 
// Anteriormente solamente con utiizar la anotación repository era suficiente
// para crear este tipo de Clases de tipo DAO (Data Access Object) y aunque se
// puede seguir utilizando este tipo de código normalmente era muy repetitivo.
// Así que al día de hoy utilizando SpringData y el concepto de repositorios
// podemos ahorrarnos bastante código. 
    
// Ahora será necesario crear nuestra interfaz de tipo DAO de esta clase
// en un nuevo paquete llamado mx.com.luis.dao.

// Sección 6 Validaciones con Spring--------------------------------------------
// Vamos a hacer algunas modificaciones sobre esta clase para agregar  el
// el concepto de validaciones, lo que vamos a hacer es agregar algunas 
// anoaciones para agregar el tema de validaciones, por ejemplo el campo de 
// nombre ya no puede ser nulo.
// Para agregar las validaciones y que reconozca las anotaciónes como
// @NotEmpty (no dejar campo vació) tenemos que agregar la librería de 
// import javax.validation.constraints.NotEmpty;
// Y Si no la reconoce entonces tenemos que importar a nuestro archivo pom.xml
// la dependencia de <artifactId>spring-boot-starter-validation</artifactId>
// ir a ver ese archivo.

    // Para el email podemos agrega una validación extra con la anotación
    // @Email y la importamos del paquete javax.validation.constraints.Email
    // Tenemos que reiniciar el servidor y hacer un Clean and Build
//------------------------------------------------------------------------------
// Seción 8 Manejo de Mensajes e Internacionalización con Spring ---------------
// Para poder personalizar los mensajes de validación que hemos agregado
// a nuestra página en el formulario de nuestra aplicación (cuando nos de un 
// error al ingresar los datos, para que se muestren en nuestro idioma o en 
// otros correctamente).
// Podemos utilizar los textos de @NotEmpty y posteriormente asociar el campo
// que queremos modificar, pero lo más importante es manejar el texto de @NotEmpty
// o el de @Email, así que estas validaciones también las vamos a utilizar para
// definir nuestras llaves/variables en el archivo messages.properties y así 
// modificar los mensajes por ejemplo:
//    NotEmpty.persona.nombre = Indicamos el mensaje
// Debe de iniciar de esa forma, con el nombre de la validación que hemos
// agregado (la validación que queremos personalizar.
// Y así con cada uno de los campos debemos continuar.
        