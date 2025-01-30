# Store Tool

## Overview
Store Tool is a Spring Boot application designed to manage products and users in a store. It provides RESTful APIs for adding, retrieving, and modifying products, as well as user authentication and authorization.
You can play with it by using browser or Postman.

### Installation
1. Clone the repository:
    ```sh
    git clone https://github.com/songocu/Store.git
    cd Store/
    ```

2. Build the project:
    ```sh
    mvn clean install
    ```` 
   
3. Start application in this way with security disabled to access H2 database console:
    ```sh
    mvn spring-boot:run -Dspring-boot.run.profiles=security-disabled
    ```
   
4. Open your browser and go to `http://localhost:8000/h2-console` to access the H2 database console. Use the following settings:
    - JDBC URL: `jdbc:h2:mem:storedb`
    - User Name: `admin`
    - Password: `admin`
    - Click on the `Connect` button

5. Open your browser and go to `http://localhost:8000/api/products/home` to see a message that the application is running.
   
### Security
You can disable security to have access to database H2 console
```sh
mvn spring-boot:run -Dspring-boot.run.profiles=security-disabled
```

You can enable security to have access to the application
```sh
mvn spring-boot:run -Dspring-boot.run.profiles=security-enabled
```

If you enable security, you can use these users to login:
- admin:admin
- user:user

\*\*⚠️ Security can be improved by using JWT token and OAUTH2.\*\*

## End points for Products
Take care that the used port is 8000 not 8080

Accesible to all users:
- GET localhost:8000/api/products/home -> show a message to show that the application is running

Accessible to user and admin:
- GET localhost:8000/api/products/info/{id} -> get product by id
- GET localhost:8000/api/products/info/count/{category} -> get number of product for a category. for example "fruits"
- GET localhost:8000/api/products/info/sales/this-month -> get number of sales for this month

Accessible to admin only:
- POST localhost:8000/api/products/modify -> add a product to the database. Provide a product in a JSON format
- PUT localhost:8000/api/products/modify/{id}/price -> modify price of a product by id. Provide as a parameter the new price

## Initialisation

When application is starting it is initialized with some products using liquibase.
You can see them in the file resources/db/changelog/changes/db.changelog-1.0.xml

2 users are added in the file resources/db/changelog/changes/db.changelog-2.0.xml


## Technologies Used
- Java
- Spring Boot
- Spring Security
- JPA/Hibernate
- Maven
- Logback (for logging)
- H2 (in-memory database)
- Mockito (for testing)

### Prerequisites
- Java 17 or higher
- Maven 3.6.0 or higher