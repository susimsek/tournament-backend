# Tournament Backend Kotlin & Spring Boot GraphQL 

Tournament Backend GraphQL API using MongoDB

<img src="https://github.com/susimsek/tournament-backend/blob/master/images/introduction.png" alt="Spring Boot Kotlin Graphql Tournament GraphQL API" width="100%" height="100%"/> 


## Playground

> You can access the playground ui from the following url.

http://localhost:9091/playground


## Testing

The backend provides a [GraphQL Playground](https://github.com/prisma-labs/graphql-playground) to test things out. The playground is located at http://localhost:9090/api/playground by default.

The graphql server is located at http://localhost:9091/graphql by default.

Authentication happens by passing a [JWT token](https://jwt.io/) as a header by using the `Authorization: Bearer <token>` header.

A JWT token can be obtained by calling the `login` mutation.

# Development

Before you can build this project, you must install and configure the following dependencies on your machine.

## Prerequisites for Backend

* Java 17
* Kotlin
* Maven 3.x
* MongoDB

### Run the app

You can run the spring boot app by typing the following command

```sh
mvn spring-boot:run
```

# Docker

You can also fully dockerize your application and all the services that it depends on. To achieve this, first build a docker image of your app.


### Image Build

The docker image of application can be built as follows:

```sh
mvn -Pjib verify jib:dockerBuild
```

## Deployment with Docker Compose

You can start a tournament api (accessible on http://localhost:9091/playground) with

```sh
docker-compose -f deploy/docker/docker-compose.yaml up -d
```

# Used Technologies

* Java 17
* Kotlin
* Docker
* Mongodb
* Graphql
* Spring Boot