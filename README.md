# Simple Crud app with Spring Webflux

This template is for a complete example of simple crud web application which uses reactive programming with spring boot and spring webflux.
* spring webflux
* spring security
* postgresql
* flyway
* integration test with flyway and testcontainer

### Getting Started

For further reference, please consider the following sections:

* Install Java 21
* ```docker-compose up -d postgres```
* ``` mvn spring-boot:run```

### Project Features
This project template includes the following features:

- **Crud Operations**: CRUD (Create, Read, Update, Delete)
- **UseCase classes**: Extracted the small unit of responsibility for use case to minimise the scope of the component and increase readability, re-usability and testability
- **Command Query Segregation**: Controller/Service have each command and query specific class
- **Dependency Inversion**: Higher level components should not depend on lower level components. To eliminate dependencies, we invert the dependencies with interfaces.
- **No blocking calls throughout the api call**: To make use of reactive programming, never use blocking calls. 
- **Security with JwtToken**: All endpoints except health check endpoint should be secured with JWT token
- **Unit test**: Test all classes in the scope of a single component, test rest controller with mockmvc
- **Integration test**: Use testcontainer for postgresql for integration test
- **Test data migration and cleanup mechanism**: Custom made way to clean up and migrate test data into test database (similar to [flyway-test-extensions](https://github.com/flyway/flyway-test-extensions)

