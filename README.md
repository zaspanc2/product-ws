# Product Web Service

Spring Boot application used for product related operations. Supports JSON requests and responses.
The project uses Spring Boot 3.4.2 and Java 17.

## Contents

The project contains the code for product web service with unit and integration tests.
The following are also included inside the resources folder:

* OpenAPI specification file
* Postman collection
* Docker-compose file containing the basic configuration for running a postgres database and product web service using
  docker (note that product web service docker image is not included because it is too large and must be build first)

## Requirements

The product web service requires the following elements to operate properly:

* Postgres database connection

## Environment variables

The following environment variables are required:

* `PORT` - The number of a port where the web service should run; defaults to `8080`
* `DB_URL` - The jdbc url to the running postgres database; defaults to `jdbc:postgresql://localhost:5432/product`
* `DB_USERNAME` - The postgres database user username; defaults to `guest`
* `DB_PASSWORD` - The postgres database user password; defaults to `guest`
* `DLL_AUTO` - The hibernate dll property that is used to modify the database schema at startup; defaults to `none`. The
  following options are supported:
    * `none` - does nothing to the database schema; expected to be set most of the time
    * `create` - deletes all data and creates a new schema with tables; expected to be set only for the first startup
      and
      after changed to `none`
    * `create-drop` - deletes all data and creates a new schema with tables and then deletes all data when the
      application shuts down; expected to be set when doing testing

Check application.properties file for usage.

## Startup

### Database

The product web service required an active postgres database for operations.
Prepare an existing postgres database or start a new one using the provided docker compose file with
`docker-compose up postgres`.

### Product web service

The product web service can be run using IntelliJ or Docker.
For first startup set `DLL_AUTO` to `create` to prepare the database schema. Then set it to `none` to prevent any
further modifications.

#### Running using IntelliJ

1. Open the whole project using IntelliJ.
2. Create a new Spring Boot configuration.
3. Override above-mentioned environment variables if needed.
4. Set a Java 17 sdk.
5. Set the Spring Boot runner class to `com.fripop.product.ws.ProductWsApplication`.
6. Run the application.

#### Running using docker

1. Install Docker.
2. Open the whole project using IntelliJ.
3. Build a jar file using `mvn clean install -Dmaven.test.skip=true`.
4. Move to the product-ws folder and build the docker image `using docker build -t product-ws-local .`.
5. Copy `docker-compose.yaml` to a separate location or move to the resources folder.
6. Run the product web service and/or postgres database using `docker-compose up`.

## Usage

To use the product web service after it has started, generate a spring rest client with the provided openapi
specification and include it to another project or use the provided postman collection.

The full list of supported operations is displayed using swagger ui and can be viewed here after the web service is
started (make sure to set the correct port):
`http://localhost:8080/productws/swagger-ui/index.html`

### Postman

To use the web service using postman, first import the provided collection and then properly set the hostname variable.

## Testing

Project also contains unit and integration tests located in the test directory.