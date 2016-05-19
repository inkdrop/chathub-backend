# Chathub

Java backend for Chathub project

## Setting up the Project

**Dependencies**

* Java 8
* PostgreSQL 9.5 or superior
* RabbitMQ
* Redis (3.x)

**Installation**

1. Install Maven if needed
2. Clone the project
3. Create a database called `chatroom` on PostgreSQL

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
