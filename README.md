# Drone service using Java11, Spring Boot and H2 DB

RESTful API to simulate Drone Service. 

## Requirements
While implementing your solution **please take care of the following requirements**:
#### Functional requirements
- There is no need for UI;
- Prevent the drone from being loaded with more weight that it can carry;
- Prevent the drone from being in LOADING state if the battery level is **below 25%**;
- Introduce a periodic task to check drones battery levels and create history/audit event
log for this

#### Non-functional requirements
- Input/output data must be in JSON format;
- Your project must be build-able and runnable;
- Your project must have a README file with build/run/test instructions (use DB that can
be run locally, e.g. in-memory, via container);
- Required data must be preloaded in the database.
- Unit tests are optional but advisable (if you have time);
- Advice: Show us how you work through your commit history.
- Programming Language: Java



## Getting Started

1. Checkout the project from GitHub

```
git clone https://github.com/olutayopeter/drone-service

```
2. Enable Lombok support on your IDE

Refer to the following link for instructions:

```
https://projectlombok.org/setup/eclipse

```
3. Open IDE of your choice and Import as existing maven project in your workspace

```
- Import existing maven project
- Run mvn clean install
- If using STS, Run As Spring Boot App

```
4. Default port for the api is 8080


### Prerequisites

* Java 11
* Intellij,Spring Tool Suite 4 or similar IDE
* [Maven](https://maven.apache.org/) - Dependency Management

### Maven Dependencies

```
spring-boot-starter-actuator
spring-boot-starter-data-jpa
spring-boot-starter-security
spring-boot-starter-web
spring-boot-devtools
h2 - Inmemory database
lombok - to reduce boilerplate code
springfox-swagger2
springfox-swagger-ui
spring-boot-starter-test
spring-security-test

```

## Swagger

Please find the Rest API documentation in the below url

```
http://localhost:8080/swagger-ui.html

```

## H2 In-Memory Database

Make sure to use jdbc:h2:mem:testdb as your jdbc url. If you intend to you use custom database name, please
define datasource properties in application-dev.properties
username: sa
password: password

```
http://localhost:8080/h2-console/


## USER LOGIN

The data.sql is populated with user login. User login details:
username: blusalt
password: blusalt

```
http://localhost:8080/h2-console/

```


## Authors

* **Olutayo Adelodun**

## Postman collection
I have included postman collection of the test conducted
link: https://api.postman.com/collections/3227660-deff6f9f-fe40-4aff-9022-fcadb395f907?access_key=PMAT-01H4JMWJP1PGEZ2CD9HKRP239D

