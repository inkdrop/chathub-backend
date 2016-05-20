# Chathub

Java backend for Chathub project

## Setting up the Project

**Dependencies**

* Java 8
* PostgreSQL 9.4 or superior
* RabbitMQ 3
* Redis

**Installation**

1. Clone the project
2. Create a database called `chatroom` on PostgreSQL

## Running
```
mvn spring-boot:run
```

or you can run with Docker with the following commands

```
./build.sh
docker-compose build
docker-compose up
```

## Usage*


Due to latest changes on API, this is part of documentation is pending


* All API calls require the header `Auth-Token` with the token given on by application when calling `/auth/github`
