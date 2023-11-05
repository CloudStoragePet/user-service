# Identity Application

Identity Application is a Spring Boot-based microservice for managing user authentication and authorization.

## Server port: 8080

## Requirements

- Java version 17.0.7
- Spring Boot 3.1.3
- Maven 3.9.1
- Zipkin

## Technologies Used

1. Java 17
2. Spring Boot
3. Spring Security
4. JWT (JSON Web Tokens)
5. Zipkin

## Application Features

The Identity Application manages user authentication and authorization by providing the following features:

- User registration
- User login
- Token-based authentication
- Token management (e.g. expiration, validation)

## Endpoints

The Identity Application exposes the following RESTful API endpoints:

- `POST /api/v1/auth/signup`: Register a new user.
- `POST /api/v1/auth/login`: Login with existing user credentials and generate a JWT token.
- `GET /api/v1/validation/user`: Validate users token.
- `POST /api/v1/validation/admin`: Validate admins token.

## Running the Application with Docker

To run the Identity Application with Docker, follow these steps:

1. Ensure that Docker and Docker Compose are installed on your system.
2. Clone the repository and navigate to the project directory.
3. Run in `cmd`
   ```sh
      mvn clean install
   ```
4. Build the Docker image by running the following command. Make sure to replace the placeholders `<your_*>` with your
   actual values:

   ```sh
   docker build \
       --build-arg JWT_SECRET=<your_jwt_secret> \
       --build-arg JWT_LIFETIME=<your_jwt_lifetime> \
       --build-arg JWT_ISSUER=<your_jwt_issuer> \
       --build-arg DB_HOST=<your_db_host> \
       --build-arg DB_USER=<your_db_user> \
       --build-arg DB_PASSWORD=<your_db_password> \
       --build-arg EUREKA_URL=<eureka_url> \
       --build-arg MANAGEMENT_ZIPKIN_TRACING_ENDPOINT=<your_zipkin_endpoint> \
       -t user-service .
    ```
http://localhost:8080/swagger-ui/index.html