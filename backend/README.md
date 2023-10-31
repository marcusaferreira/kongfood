# Kong Food
for portuguese version, click [here](README-pt-BR.md)
## Description
Kong Food is an application that allows customers to registry their orders and the cook to manage the orders that need 
to prepare. The name was just to name the project, it is not related to the movie King Kong or the game Donkey Kong. 
This project is a challenge from FIAP Specialization in Software Architecture.

## Goals
The main goal of this project is to create an application that allows the customers to registry their orders and the cook
to manage the orders that need to prepare. The application should be able to:
* Allow the customer to registry his order
* Allow the customer to cancel his order
* Allow the customer to see his order status
* Allow the customer to personalize his order
* Allow the cook to see the orders that need to prepare
* Allow the users to manage the orders status
* Manage the products disponibility

To achieve these goals we used DDD (Domain Driven Design) and Hexagonal Architecture.

#### Bounded contexts
This project is divided into 3 bounded contexts:
* [Customer](src/main/kotlin/br/com/fiap/techchallenge/kongfood/domain/customer/README.md)
* [Order](src/main/kotlin/br/com/fiap/techchallenge/kongfood/domain/order/README.md)

### Reference Documentation
For further reference, please consider the following sections:
* [Tech Challenge](https://docs.google.com/document/d/1Ay-Ibw0kTPWWaD3e0yEtVvpCV3PH59aqYK10QD6ct5k/edit?usp=sharing)
* [Event Storm](https://miro.com/app/board/uXjVNa83QS0=/?share_link_id=481501726445)
* [Postman collection](https://drive.google.com/file/d/10q7YHOSL5zUNMQB1gxtugwNzTbwYKy_r/view?usp=sharing)
* [Swagger](http://localhost:8080/swagger-ui.html)
* [Swagger-PDF](https://drive.google.com/file/d/10parfh2E2axyWB63tnemb7sR7m7HaMCz/view?usp=sharing)

## Architecture
This project is based on the hexagonal architecture. The main idea is to separate the business logic from the technical
details. The business logic is the core of the application it is the most important part of the application. The
technical details are the details that are not important for the business logic, like the database, the framework, the
web server, etc. The business logic is the center of the application and the technical details are the adapters that
allow the application to communicate with the external world.

## Code structure
The code is divided into 3 main packages:
* [domain](src/main/kotlin/br/com/fiap/techchallenge/kongfood/domain/README.md)
* [application](src/main/kotlin/br/com/fiap/techchallenge/kongfood/application)
* [infrastructure](src/main/kotlin/br/com/fiap/techchallenge/kongfood/infrastructure)

## Technical Requirements
* Java 17
* Gradle 7.5.1
* Docker 4.23.0 or higher

### Technical Reference Documentation
For further reference, please consider the following sections:
* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.4/gradle-plugin/reference/html/)
* [Kotlin Reference Documentation](https://kotlinlang.org/docs/reference/)
* [Organizing Layers Using Hexagonal Architecture, DDD and Spring](https://www.baeldung.com/hexagonal-architecture-ddd-spring)
* [Hexagonal Architecture](https://en.wikipedia.org/wiki/Hexagonal_architecture_(software))
* [Domain-Driven Design](https://en.wikipedia.org/wiki/Domain-driven_design)
* Docker
  * [Docker](https://docs.docker.com/)
  * [Docker Compose](https://docs.docker.com/compose/)

### Additional Links
These additional references should also help you:
* [MongoDB](https://www.mongodb.com/)
* [Spring Data MongoDB - Reference Documentation](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#reference)
* [Docker Buildkit](https://docs.docker.com/build/buildkit/#getting-started)

