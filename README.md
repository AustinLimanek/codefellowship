# Secure code fellowship

## Features

This application allows for user to sign up for an account using the Spring security framework. Users input information about their username, password, first name, last name, biography, and their date of birth. This information is stored into a database. The user will be directed to a webpage with basic styling and an ability to sign out.

## Running 

Use `./gradlew bootRun` to run the application. The user will need to modify the `codefellowship/src/main/resources/application.properties` file in order for the application to run properly. The application requires being wired up to a database.

    spring.datasource.url=jdbc:postgresql://localhost:5432/codefellowship
    spring.datasource.username=postgres
    spring.datasource.password=
    spring.jpa.hibernate.dll-auto=update
    spring.jpa.generate-ddl=true

The password must be up input appropriately to your system.

