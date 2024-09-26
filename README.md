# CUSPID API GENERATOR

### Overview

This Maven plugin aims to simplify the development process by automatically generating service classes, controller
classes, service implementations, and repository interfaces based on your entity classes. It acts like an administration
page for your application, providing a structured way to manage your data and business logic.

By automating the generation of these components, developers can focus more on implementing business logic while
minimizing boilerplate code.

### Features

1. [x] Automatic Code Generation: Generate service, controller, and repository layers from entity definitions.
2. [x] Modular Architecture: Extend or modify generated code to fit specific requirements.
3. [x] Administration Functionality: Simplifies the management of application data through generated administrative
   interfaces.
4. [x] Easy Integration: Seamlessly integrates with existing Spring Boot applications.

### Prerequisites

    Java 17 or higher
    Maven 3.5 or higher
    Spring Boot 3.x or higher
    An IDE (e.g., IntelliJ IDEA, Eclipse)

### Getting Started

#### 1. Define Your Entities

Create your entity classes in the src/main/java/com/example/entities directory. For example:

``` java
package com.example.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String name;

private String email;

    // Getters and Setters
}
```

#### 2. Configure the Plugin in Your Project

Add the plugin to your Maven project's pom.xml:

```xml

<build>
    <plugins>
        <plugin>
            <groupId>org.cuspid</groupId>
            <artifactId>cuspid-api-generator</artifactId>
            <version>1.0.0-SNAPSHOT</version>
            <executions>
                <execution>
                    <goals>
                        <goal>generate</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

#### 3. Run the Code Generation

Run the plugin goal to generate the necessary service, controller, service implementation, and repository classes based
on your entities:

```shell
mvn com.example:entity-service-generator-plugin:generate
```

#### 4. Review the Generated Code

Check the src/main/java/com/example/generated directory for the generated classes. You can now customize these classes
to implement your business logic.

#### 5. Configure Your Application

Add any necessary configuration to your application.properties file. Ensure that you have the necessary dependencies for
Spring Data JPA and your database setup.

Furthermore, you must include this new package in the component scan and use @EnableJpaRepositories to ensure that the
context will be created correctly.

#### 6. Add SpringDoc Dependency (Optional)

If you want to have a UI to access the generated controllers, add the SpringDoc dependency to your pom.xml:

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-ui</artifactId>
    <version>1.6.14</version> <!-- Check for the latest version -->
</dependency>
```

#### 7. Run Your Application

Start your Spring Boot application using:

```shell
mvn spring-boot:run
```

#### Example Usage

Once your application is running, you can test the generated controllers and services using Postman or your browser.

Endpoint Example :

*  GET /api/users - Retrieve all users.
*  POST /api/users - Create a new user.

#### Contributing

Contributions are welcome!
Please open an issue or submit a pull request for any improvements or features you'd like to
see.

#### License

This project is licensed under the MIT License. See the LICENSE file for details.

#### Acknowledgments

* Spring Boot for the framework.
* Maven for project management and build automation.