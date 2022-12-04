# Notes importing system

## Stack
- [JDK 18](http://jdk.java.net/18/)
- Maven
- Spring Boot 3.0
- Spring Data JPA (Hibernate)
- Spring WebFlux
- Lombok
- Jackson
- JUnit 5
- PostgreSQL (15.1)


## Project setup
For correct operation, you need to configure the following options in the file `src/main/resources/application.yaml`:
```yaml
app:
  old-system-url: "http://localhost:8080" # source of import (do not change if using build-in old sysetm imitation)
  run-import-after-start: false # change if you want to run the import immediately after the application starts

server:
  port: 8088 # Netty webclient port (change if port is busy)

spring:
  datasource: # enter your details
    url: "jdbc:postgresql://localhost:5432/cleverdev"
    username: "user"
    password: "password"
  sql:
    init:
      mode: never # can be changed to "always" if you want to initialize an empty database automatically
```


## Run
To start the main import system execute in root directory:
```shell
mvn spring-boot:run
```
To run imitation of REST API of the old system execute in root directory (port 8080 must be free):
```shell
mvn spring-boot:run -f old-system-mock/pom.xml
```