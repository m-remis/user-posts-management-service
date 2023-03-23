# User Posts Management Service

This is user posts management microservice project. This microservice is responsible for:
* creating posts
* serving posts
* helping me to get a job


## DB Structure
This project uses in memory H2 database with initial SQL script for schema creation.
* [SQL Script](user-posts-management-service\src\main\resources)

## Build & Test
```
mvn clean install
```

How to start:
```
java -jar user-posts-management-service\target\user-posts-management-service-0.0.1-SNAPSHOT.jar
```