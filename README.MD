# API TEST
## David Portocarrero Caicedo

### Start
Requiere una base de datos MySQL en la dirección `localhost:3306`.

* Tipo de servidor: MariaDB
* Usuario: root@localhost
* Apache/2.4.43 (Win64) OpenSSL/1.1.1g PHP/7.4.7
* Versión del cliente de base de datos: libmysql - mysqlnd 7.4.7
* utf8_bin
---
### Especificaciones de la base de datos
* Nombre de la base de datos: `java_spring_boot_test`
* Nombre de la tabla `usuarios`

Estructura de la tabla

```json
{
  "id": int(11),
  "nombre": varchar(120),
  "apellido": varchar(120),
  "email": varchar(255),
  "telefono": varchar(120),
  "password": varchar(120)
}

```
---
### Archivos claves

Guía a partir del curso: [Curso de Java Fullstack Completo (springboot, hibernate, JWT, API Rest)](https://www.youtube.com/watch?v=7vHzVN0EiQc).

Queries de consulta y borrado por medio de cliente JS.:
`src/main/resources/static/js/demo/datatables-demo.js`

Librería Front-end del curso: ``

---
### API SPECIFICATION
Dominio raíz: `localhost:8080`

`/api`

`/api/user/{id}`: GET | *response: User*

`/api/user`: POST | *body: User*

`/api/edit`: PUT #incompleto

`/api/delete/{id}`: DELETE | *response: String*

`/api/search/{id}`: GET | *response: List<User'>*

`/api/search`: GET | *response: User*


`/auth`

`/auth/login`: POST | *body: {email, password}* | *response: Token + User*
