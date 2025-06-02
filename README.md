# API MCSV Technical Test

Este proyecto es una arquitectura basada en microservicios construida con Spring Boot. Cada microservicio está contenido
en su propia carpeta y se ejecuta de forma independiente.

## 📦 Estructura del proyecto

```
api-mcsv-technical-test/
├── microservice-auth       → Microservicio de autenticación
├── microservice-config     → Servidor de configuración centralizada
├── microservice-eureka     → Servidor Eureka para descubrimiento de servicios
├── microservice-general    → Microservicio principal (dominio general)
├── microservice-gateway    → API Gateway para enrutamiento de solicitudes
```

## 🔧 Requisitos Previos

Asegúrate de tener instalados los siguientes requisitos en tu máquina:

- [Java 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [Base de datos MySQL](https://www.mysql.com/downloads/)

## 🚀 Instalación y Configuración

Sigue estos pasos para instalar y configurar el proyecto:

### 1. **Clonar el repositorio:**

    git clone https://github.com/riquelmip/api-mcsv-technical-test.git

### 2. **Dirígete a la ruta donde clonaste el repo:**

    cd api-mcsv-technical-test

### 3. **Crea la base de datos MySQL:**

      microservices-technical-test 

Puedes utilizar una herramienta como MySQL Workbench o la línea de comandos para crear la base de datos.
Luego, importa el archivo SQL llamado: microservices-technical-test.sql

### 4. **Configura las propiedades de la base de datos:**

Ingresa al microservicio de configuración `microservice-config` en la ruta:
`api-mcsv-technical-test\microservice-config\src\main\resources\configurations`

Dentro encontrarás varios archivos de configuración, cada uno pertenece a un microservicio. Debes abrir los archivos:

         - microservice-config/src/main/resources/configurations/microservice-auth.properties
         - microservice-config/src/main/resources/configurations/microservice-general.properties

Dentro de cada uno de esos dos archivos, configura las propiedades `spring.datasource.url`, `spring.datasource.username`
y `spring.datasource.password` con los datos de tu base de datos. De la siguiente manera:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/microservices-technical-test
spring.datasource.username=username
spring.datasource.password=password
```
### 5. **Levantar los microservicios:**

🔁 **Orden recomendado para ejecutar los servicios:**
```bash
   ⚠️ Cada microservicio debe ser ejecutado desde su propia carpeta raíz.
   1. microservice-config (Servidor de configuración)
   2. microservice-eureka (Servidor de descubrimiento)
   3. microservice-gateway (Enrutador API Gateway)
   4. microservice-auth (Autenticación)
   5. microservice-general (Servicio de dominio principal)
```
📌 **Compilar todo el proyecto**
```bash
   mvn clean install
```
Esto compilará todos los subproyectos (microservicios).

▶️ **Ejecución individual de los microservicios**

Cada servicio puede iniciarse desde su propia carpeta:
```bash
   # 1. Servidor de configuración
   cd microservice-config
   mvn spring-boot:run
   
   # 2. Servidor Eureka
   cd ../microservice-eureka
   mvn spring-boot:run
   
   # 3. Gateway
   cd ../microservice-gateway
   mvn spring-boot:run
   
   # 4. Servicio de autenticación
   cd ../microservice-auth
   mvn spring-boot:run
   
   # 5. Servicio general
   cd ../microservice-general
   mvn spring-boot:run
```
### 6. 🌐 Accesos importantes
Eureka Dashboard: http://localhost:8761

API Gateway (por defecto): http://localhost:8080

Los endpoints de los microservicios se enrutan a través del API Gateway.

### 7. 📩 Notas adicionales
Asegúrate de que los puertos no estén siendo usados por otros procesos.