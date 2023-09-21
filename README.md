[![CircleCI](https://dl.circleci.com/status-badge/img/gh/m-remis/user-posts-management-service/tree/main.svg?style=svg&circle-token=be10159a23b14433d8f05f28bd11d770041576dd)](https://dl.circleci.com/status-badge/redirect/gh/m-remis/user-posts-management-service/tree/main)

## User Posts Management Service

#### Simple Spring Boot demo application that provides REST APIs for managing user posts.

All functional requirements are covered by unit tests.

Functional requirements:

- Add post - need to validate userID using external API
- Post view
    - based on id or userId
    - if the post is not found in the system, it needs to be searched using an external API and saved (valid only for
      searching using the post id)
- Post removal
- Editing the post - the ability to change the title and body


External API can be found here:  https://jsonplaceholder.typicode.com/

Apis used:

https://jsonplaceholder.typicode.com/posts

https://jsonplaceholder.typicode.com/users

The format of the post is as follows:

- id: integer
- userId: integer
- title: string
- body: string

This project uses:

* Java 17
* Maven
* Spring Boot 3.0.5 framework
* In memory H2 database
* Embedded Apache Tomcat is replaced by JBoss Undertow (no real reason to be honest)
* SpringDoc OpenAPI UI
* Hibernate

### Make sure to have installed

[Git](https://git-scm.com/downloads)

[JDK 17 or later](https://adoptium.net)

[Maven 3.8.8 or later](https://maven.apache.org/download.cgi)

### DB Structure

This project uses simple in memory H2 database with initial SQL script for schema creation, 
data is removed from DB on each application startup for simplicity.

[SQL Script](src/main/resources/schema.sql)

### Build & Test:

```
mvn clean install
```

### How to start:

```
java -jar user-posts-management-service\target\user-posts-management-service-0.0.1-SNAPSHOT.jar
```

### Swagger UI:

This project uses OpenAPI 3.0.3 for documentation

Swagger UI can be found under:

```
{server-url}/api/user-posts-service/swagger-ui/index.html
```

#### For local testing, click [here](http://localhost:8080/api/user-posts-service/swagger-ui/index.html) after running the application to redirect to Swagger UI

![swagger](docs/img.png)

### YAML API Specification:

specification can be downloaded under:

```
{server-url}/api/user-posts-service/v3/api-docs.yaml
```

#### For local testing, click [here](http://localhost:8080/api/user-posts-service/v3/api-docs.yaml) after running the application to download the file