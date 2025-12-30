# EasyBytes Accounts Service

Servicio de cuentas desarrollado con Spring Boot para la gestión de cuentas de clientes.

## Información del Proyecto

- **Versión**: 0.0.1-SNAPSHOT
- **Framework**: Spring Boot 3.4.12
- **Java**: 21
- **Base de datos**: H2 (en memoria)
- **Validación**: Bean Validation (Jakarta Validation)
- **Mapeo**: MapStruct
- **Logging**: Logback con encoder Logstash

## Dependencias Principales

- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Validation
- Spring Boot Starter Actuator
- Exception Handling Spring Boot Starter (1.0.0-SNAPSHOT)
- H2 Database
- MapStruct (1.5.5.Final)
- Lombok

## Changelog

### Versión 0.0.26-SNAPSHOT

#### Modificado
- **pom.xml**: Actualizada la versión de `springdoc-openapi-starter-webmvc-ui` a `2.7.0` para resolver incompatibilidad con Spring Boot 3.4.x.

#### Problema Resuelto
- **Swagger UI**: Solucionado error 500 al acceder a `/swagger-ui/index.html` causado por `NoSuchMethodError: ControllerAdviceBean.<init>`.

### Versión 0.0.25-SNAPSHOT

#### Agregado
- **Soporte de Auditoría**: Se ha añadido soporte de auditoría a nivel de entidad JPA para registrar automáticamente la fecha de creación/modificación y el usuario.
- **`BaseEntity.java`**: Nueva clase base para entidades con campos de auditoría (`createdAt`, `createdBy`, `updatedAt`, `updatedBy`).
- **`AuditAwareImpl.java`**: Implementación de `AuditorAware` para obtener el auditor actual (actualmente "ACCOUNTS_MS").
- **`@EnableJpaAuditing`**: Anotación agregada en `AccountsApplication.java` para habilitar la auditoría JPA.

#### Modificado
- **`AccountServiceImpl.java`**: Refactorizado para eliminar la configuración manual de campos de auditoría, delegando esta responsabilidad a JPA Auditing.
- Las entidades `Customer` y `Account` ahora extienden `BaseEntity` para heredar las capacidades de auditoría.

### Versión 0.0.1-SNAPSHOT

#### Modificado
- **CustomerDto.java**: Agregados mensajes personalizados a las anotaciones de validación para `name`, `email` y `mobileNumber`
  - `@NotBlank(message = "Name is required")` para el campo name
  - `@Email(message = "Please provide a valid email address")` para el campo email
  - `@Pattern(regexp = "^\\d{10}$", message = "Mobile number must be exactly 10 digits")` para mobileNumber
  - `@NotBlank(message = "Mobile number is required")` para mobileNumber

#### Agregado
- **README.md**: Documentación completa del proyecto incluyendo API, configuración y ejemplos de uso

#### Problema Resuelto
- **Mensajes de validación**: Corregido el problema donde se mostraban mensajes predeterminados de Spring Framework en español en lugar de mensajes personalizados configurados

## Endpoints API

### Crear Cuenta
- **POST** `/api/accounts`
- **Body**: CustomerDto con información del cliente y cuenta
- **Respuesta**: ResponseDto con código de estado

### Obtener Cuenta
- **GET** `/api/accounts/{mobileNumber}`
- **Parámetro**: mobileNumber (10 dígitos numéricos)
- **Respuesta**: CustomerDto

### Actualizar Cuenta
- **PUT** `/api/accounts/update`
- **Body**: CustomerDto actualizado
- **Respuesta**: ResponseDto

### Eliminar Cuenta
- **DELETE** `/api/accounts/{mobileNumber}`
- **Parámetro**: mobileNumber (10 dígitos numéricos)
- **Respuesta**: ResponseDto

## Validaciones

### Validaciones en Path Variables
- Número móvil: Debe ser exactamente 10 dígitos numéricos

### Validaciones en Request Body (CustomerDto)
- **name**: Requerido, máximo 100 caracteres
- **email**: Requerido, formato de email válido, máximo 100 caracteres
- **mobileNumber**: Requerido, exactamente 10 dígitos numéricos, máximo 20 caracteres

## Configuración

### Base de Datos H2
- **URL**: `jdbc:h2:mem:testdb`
- **Consola H2**: Disponible en `http://localhost:8080/h2-console`
- **Usuario**: `sa`
- **Contraseña**: (vacía)

### Puerto del Servidor
- **Puerto**: 8080

### Configuración de Excepciones
```yaml
app:
  exception:
    include-stack-trace: false
    include-cause: true
    log-level: ERROR
    sensitive-fields:
      - password
      - ssn
      - creditCard
      - accountNumber
      - routingNumber
      - cvv
      - pin
      - email
    expose-error-codes: true
    base-error-uri: https://api.company.com/errors
```

## Ejecución

1. Clonar el repositorio
2. Ejecutar con Maven: `mvn spring-boot:run`
3. Acceder a la aplicación en `http://localhost:8080`

## Testing

Para probar las validaciones, enviar una petición POST a `/api/accounts` con datos inválidos:

```json
{
  "name": "",
  "email": "invalid-email",
  "mobileNumber": "123",
  "accountDto": {
    "accountType": "SAVINGS",
    "branchAddress": "123 Main St"
  }
}
```

Respuesta esperada con mensajes personalizados:
```json
{
  "errorCode": "VALIDATION_ERROR",
  "validationType": "SPRING_FRAMEWORK",
  "fieldErrors": {
    "name": "Name is required",
    "email": "Please provide a valid email address",
    "mobileNumber": "Mobile number must be exactly 10 digits"
  },
  "globalErrors": [],
  "errorCount": 3
}
