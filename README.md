# Client Manager
Aplicación desarrollada con el fin de manejar clientes de una empresa y sus sucursales.

## Requerimientos
### Tecnología a emplear en el Desarrollo Web 

El proyecto se basa en un pequeño servicio web que utiliza las siguientes tecnologías: 
* Java 1.8 
* Spring MVC con Spring Boot  
* Base de datos H2 (en memoria) 
* Maven 

### Consideraciones adicionales para el desarrollo del aplicativo 
* Respetar el copyright, evitando tomar activos (imágenes, archivos, códigos o fragmentos de estos) contenidos en el documento para los que no poseen permiso de utilizar. 
* Desarrollar el proyecto usando GIT como control de versiones. 

### Diseño de la solución desde la perspectiva del software 
Debe tener en cuenta las siguientes convenciones mientras trabaja en este ejercicio: 

* Todas las entidades deben tener un ID con el tipo Long y date_created con el tipo ZonedDateTime. 
* La arquitectura del servicio web está construida con los siguientes componentes: 
    * DataTransferObjects: objetos que se utilizan para la comunicación externa a través de la API. 
    * Controller: implementa la lógica de procesamiento del servicio web, el análisis de parámetros y la validación de entradas y salidas. 
    * Service: implementa la lógica de negocios y maneja el acceso a los DataAccessObjects. 
    * DataAccessObjects: interfaz para la base de datos. Inserta, actualiza, elimina y lee objetos de la base de datos. 
    * DomainObjects: objetos funcionales que pueden persistir en la base de datos. 
    * TestDrivenDevelopment es una buena opción, pero depende de usted cómo está probando su código. 

### Especificaciones funcionales 
La empresa de Automóviles XXXXX, desea tener un sistema de información que le permita llevar el control de sus clientes. Actualmente la empresa posee una red de concesionarios a nivel nacional, alrededor de 15. Pero requiere tener un sistema que le permite gestionar la información de sus clientes y segmentar el mismo por concesionario. 

### Requerimientos a considerar para el desarrollo del aplicativo 
Debería poder iniciar la aplicación de ejemplo ejecutando com.consultec.MyServerApplicantTestApplication, que inicia un servidor web en el puerto 8080 (http://localhost:8080) y sirve SwaggerUI donde se puede inspeccionar y probar los endpoints desplegados. 
* Tarea #1 
    * Escriba un nuevo controller para mantener los datos de los clientes (CRUD). 
    * Entidad Customer: debe tener al menos las siguientes características: 
        * id, name, lastName, username, password, email, address, dateCreated(pattern = "dd/MM/yyyy"), status (Enum = ENABLED, DISABLED)     * Agregar datos de ejemplo a un archivo resources/data.sql 
* Tarea #2 
    * Imagine una interfaz de gestión que los empleados de la empresa de Automóviles utilizan internamente para buscar datos relacionados con los clientes. 
    * Para esta nueva funcionalidad de búsqueda, necesitamos un endpoint para buscar clientes. 
    * Debería ser posible buscar clientes por sus atributos (nombre de usuario, estado). o Implementar un nuevo endpoint para realizar búsquedas (o ampliar uno ya existente) o Deberá usar los atributos del cliente como parámetros de entrada o Retorna una lista de clientes (JSON) 
* Tarea #3 (opcional)
    * Esta tarea es _opcional_, implemente la seguridad. Asegure el API para que la autenticación sea requerida para acceder a ella.

## Tecnologías Utilizadas 
* Java 1.8 
* Spring MVC
* Spring Boot
* Spring Security
* Spring Security Oauth2
* Spring Data
* JPA
* Hibernate
* Swagger
* JWT
* H2 Database
* Lombok
* Maven

## Interpretación del problema
Básicamente se pensó en que se le deberá dar mantenimiento a una entidad cliente la cual pertenece a un concesionario (compañía o sucursales). Dichos mantenimientos están limitados por los permisos de los usuarios de l aplicación. Se asumió que los usuarios existían con sus roles predefinidos.

## Arquitectura
Se decidió hacer una arquitectura de microservicios y modulada.

El proyecto actualmente consta de 4 módulos:
* **entities**: contiene las entidades en común que se manejarán en toda la aplicación. Así como las operaciones en común que se hacen entre ellos como el mapeo de de las entidades a sus respectivos DTOs. Las validaciones y excepciones que rigen el correcto funcionamiento de las entidades.
* **core**: contiene la lógica de negocio. Los servicios y repositorios en el cual se operan las entidades con la base de datos donde se persiste la información. Este módulo se trabaja puramente con las entidades. Las validaciones de dichas operaciones, también se encuentran en es te lugar.
* **ws**: contiene las configuraciones y declaraciones de los servicios RestFul que permitirán la comunicación externa con clientes que quieran consumir los servicios. Aquí se configura la seguridad y se da uso a los servicios expuestos por el core para conectar las operaciones de los controladores con los repositorios.
* **ui**: contiene la estructura de las vistas utilizadas por el usuario. La **UI** y el **WS** están aisladas, de hecho corren en puertos diferentes. Pués la UI se maneja sola e independiente de la estructura de los servicios en el backend. Aquí se pueden ver las páginas y los controladores de cada que intervienen en el funcionamiento de cada una. Los servicios que hacen la llamada al **WS** para solicitar alguna operación con las entidades. Aquí también se pueden ver configuraciones de seguridad para las acciones que puede realizar un usuario en la **UI**.

Esta estructura permite el aislamiento de:
* La UI y los servicios Rest.
* Los controladores de los servicios rest y los servicios que comunican al repositorio. De esta manera el controlador es quién se encarga de manera remota como mapear los datos de manera correcta que el cliente necesita.
* Las entidades de todos los módulos. Al no depender de ningún módulo ésta permite la fácil importación de las entidades o DTO en los diferentes módulos, sin afectar de manera negativa la utilización de la misma.

Las mayorías de las funciones se desarrollaron para que sean unícas e independientes y puedan fácilmente ser reutilizadas. Ejemplo: el proyecto podría implementar una estructura de mappers para abstraer la forma en como se mapean los DTO con las entidades. Esta implementación se haría fácil ya que cada función que convierte un DTO en entidad y viceversa se encuentran separadas.

### Entities
* Las entidades extienden de **BaseEntityProperties**, el cual contiene las propiedades base de las entidades tales como:
    * ID: que es el identificador único de la entidad.
    * createdDate: que es la fecha en que se creó la entidad.
    * updatedDate: que es la última fecha en que se actualizó la entidad.
    * status: que es estado de la entidad.
* Los **dtos** se encuentran bajo el folder **/dto** y son básicamente las mismas entidades pero que no se persisten en la base de datos, no pueden afectar la base de datos.
* Las validaciones de las propiedades de los dtos están dentro del folder **/validations**. Valida las propiedades de un DTO antes de ser convertido en una entidad, en caso que se requiera.
* Las conversiones de una entidad a otra se encuentran bajo el folder de **/util**. También se manejan operaciones básicas con las entidades tales como: setear los estados, las fechas de creación y actualización se encuentra bajo este folder también.
* La definición de los estados y los roles se encuentran definidas bajo **/status** y **/role** respectivamente.
* La definición de las excepciones que puede generar cada entidad se encuentra en el folder **/exceptions**.

### Core
* Las excepciones que se generan por algún fallo en alguna operación entre las entidades y la base de datos, se alojan bajo el folder **/exceptions**.
* Todos los repositorios se encuentran en el folder **/repository**.
* Todos los servicios que sirven de puente de comunicación con los repositios, y abstracción de las operacioines de las entidades en la base de datos se encuentran en el folder **/service**.
* Todas las utilerías necesitadas para realizar correctamente las operaciones de persistir información en la base de datos se encuentran en el folder **/util**.

### WS
* Las configuraciones hechas en el momento de incio de la aplicación de Spring Boot se encuentran en el foldr **/configuration**. Aquí se puede ver la configuración de seguridad, protección de los recursos expuestos, la configuración del token JWT y el servicio de búsqueda de usuario  por su nombre de usuario con el fin ofrecerlo a authentication provider de spring.
* La configuración de como reaccionan los servicios rest ante una excepción para devolver un body personalizado se encuentra en el folder **/exception**. Cualquier otro file que necesite crearse con respecto al manejo de excepciones en el WS iría aquí.
* Todos los controladores que configuran los endpoints del WS se encuentran bajo el folder **/rest**.
* Todas las utilerías necesitadas para realizar correctamente el intercambio de información entre el cliente y el servidor se encuentran bajo el **/util**.
