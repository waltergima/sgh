# SGH
Sistema de Gestão de Hospedagem para casas de apoio
Accommodation Management System for support houses

SGH (Sistema de Gestão de Hospedagem / Accommodation Management System) is system to manage support houses and their guests

## Getting Started

Follow the next steps in order to run this project on your local environment.

### Prerequisites

Install [Java JDK 11+](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).The JDK (Java Development Kit) is a development environment for building applications, applets, and components using the Java programming language.

The database used is [MySQL 8+](https://www.mysql.com/downloads).

To build the project ans install the dependencies you will need [Maven 3.6+](https://maven.apache.org/download.cgi).


### Installing

After installing the prerequisites, run the installation of the dependencies of the project. In the main folder of the project, run the following command

```
mvn clean install
```

Edit the file [application.properties](src/main/resources/application.properties) with the correct database configuration. In this file you can configure the property `jwt.secret` to a secret used to generate the token for your application.

To generate a build run the following command

```
mvn clean package
```
This will generate a target folder with a jar named `sgh-[version].jar`. For example: `sgh-1.0.0.jar`;

To start the server run the following command

```
java -jar sgh-[version].jar
```

The development server will start in the port 8080

## Deployment

You can use the jar generated in the previous step to deploy in your application server

## Built With

* [Spring Boot](https://spring.io/projects/spring-boot)
* [Hibernate](https://hibernate.org/) - Hibernate ORM enables developers to more easily write applications whose data outlives the application process. As an Object/Relational Mapping (ORM) framework, Hibernate is concerned with data persistence as it applies to relational databases (via JDBC).
## Authors

* **Elbio Caetano** - *Initial work* - [Elbio Caetano](https://github.com/elbiocaetano)

## License

This project is licensed under the Mozilla Public License 2.0 - see the [LICENSE](LICENSE) file for details

