# Kong Food

## Descrição
Kong Food é um aplicativo que permite que os clientes registrem seus pedidos e o cozinheiro gerencie os pedidos que precisam.
O nome foi apenas para nomear o projeto, não está relacionado ao filme King Kong ou ao jogo Donkey Kong. 
Este projeto é um desafio da Especialização em Arquitetura de Software da FIAP.

## Objetivos
O principal objetivo deste projeto é criar um aplicativo que permita aos clientes registrar seus pedidos e ao cozinheiro
para gerenciar os pedidos que precisam preparar. O aplicativo deve ser capaz de:
* Permitir que o cliente registre seu pedido
* Permitir que o cliente cancele seu pedido
* Permitir que o cliente veja o status do seu pedido
* Permitir que o cliente personalize seu pedido
* Permitir que o cozinheiro veja os pedidos que precisam preparar
* Permitir que os usuários gerenciem o status dos pedidos
* Gerenciar a disponibilidade de produtos

Para atingir esses objetivos, usamos DDD (Domain Driven Design) e Clean Architecture.

#### Contextos delimitados
Este projeto é dividido em 4 contextos delimitados:
* [Customer](src/main/kotlin/br/com/fiap/techchallenge/kongfood/domain/customer/README.md)
* [Order](src/main/kotlin/br/com/fiap/techchallenge/kongfood/domain/order/README.md)
* Product
* Payment

### Documentação de referência
Para mais informações, consulte as seguintes seções:
* [Tech Challenge](https://docs.google.com/document/d/1Ay-Ibw0kTPWWaD3e0yEtVvpCV3PH59aqYK10QD6ct5k/edit?usp=sharing)
* [Event Storm](https://miro.com/app/board/uXjVNa83QS0=/?share_link_id=481501726445)
* [Postman collection](src/main/resources/Tech_Challenge.postman_collection.json)
* [Swagger](http://localhost:8080/swagger-ui.html)
* [Swagger-PDF](https://drive.google.com/file/d/10parfh2E2axyWB63tnemb7sR7m7HaMCz/view?usp=sharing)

## Arquitetura
Este projeto é baseado na arquitetura limpa. A ideia principal é separar a lógica de negócios dos detalhes técnicos.
A lógica de negócios é o núcleo da aplicação, é a parte mais importante da aplicação. Os detalhes técnicos são os detalhes
que não são importantes para a lógica de negócios, como o banco de dados, o framework, o servidor web, etc. A lógica de negócios
é o centro da aplicação e os detalhes técnicos são os adaptadores que permitem que a aplicação se comunique com o mundo externo.
Outro conceito importante é o uso dos princípios SOLID

## Requisitos técnicos
* Java 17
* Gradle 7.5.1
* Docker 4.23.0 or higher

### Documentação de referência

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.4/gradle-plugin/reference/html/)
* [Kotlin Reference Documentation](https://kotlinlang.org/docs/reference/)
* [Clean Architecture](https://www.baeldung.com/spring-boot-clean-architecture)
* [Domain-Driven Design](https://en.wikipedia.org/wiki/Domain-driven_design)
* Docker
    * [Docker](https://docs.docker.com/)
    * [Docker Compose](https://docs.docker.com/compose/)

### Links Adicionais

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

### Diagrama de Arquitetura
![Arquitetura](/src/main/resources/arquitetura-infraestrutura-tech-challenge-fase-2-Page-2.jpg)