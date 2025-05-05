# Built with
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [MySQL](https://www.mysql.com/)
- [Apache Maven](https://maven.apache.org/)
- [JUnit 5](https://junit.org/junit5/)
- [Mockito](https://site.mockito.org/)
- [Lombok](https://projectlombok.org/)
- [MapStruct](https://mapstruct.org/)
- [Swagger](https://swagger.io/)
- [Spring Doc Open API](https://springdoc.org/)
- [JaCoCo](https://www.eclemma.org/jacoco/)

# How to run
## Prerequisites
Clone the repository to your local machine.

## Run locally
1. Install the dependencies
    - [Java](https://www.oracle.com/java/technologies/downloads/#java21)
    - [MySQL](https://www.mysql.com/) (Version: 8.0 or higher)
        - Create the user and database in MySQL as per `application.properties`
      ```properties
         spring.datasource.url=jdbc:mysql://localhost:3306/emsp-account
         spring.datasource.username=root
         spring.datasource.password=1qazxsw2
      ```
2. Import the project in your IDE(e.g. IntelliJ IDEA) and Run the application
3. The documentation with Swagger UI is available at the following URL.
   ```
   http://localhost:8080/swagger-ui/index.html
   ```


## Run on Cloud Platforms
Deploy the application on cloud platforms, e.g. AWS.
There is a GitHub Actions workflow in .github/workflows/aws/aws_ecs.yml. 
(it was verified that it works on AWS, but my AWS account was closed for some reasons)

# Design

1. Layers

   1.1 Core Layers

    * Controller Layer
    * Service Layer
    * Repository Layer
    * DTO Layer
    * Model Layer
    * Mapper Layer

   1.2 Other Layers/Packages

    * Configuration Layer
    * Exception Layer
    * Utility Layer

2. Database Schema
   
   The following database schema file was exported from the auto-generated database. 
```
   src/resources/schema/db.sql
```

3. Modeling and Validation
    * Using PO(Persistence Object) and DTO(Data Transfer Object) to separate the data layer and service layer.
    * Using [MapStruct](https://mapstruct.org/) to convert PO to DTO and vice versa.
    * Using Lombok to reduce the boilerplate code.
    * Using Hibernate Validator to validate the request data.

4. Testing

   4.1 Unit Tests and Integration Tests
    * Using JUnit 5, Mockito and Spring Boot Test to write the unit tests and integration tests.
    * Using Jacoco to generate test reports.
   
   4.2 Testing APIs with Postman
    * You can import the file postman.json to your own Postman to test