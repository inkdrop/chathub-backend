# Chathub

Java backend for Chathub project

## Setting up the Project

**Dependencies**

* Java 8
* PostgreSQL 9.3 or superior
* RabbitMQ
* Redis (3.x)

**Installation**

1. Install Maven if needed
2. Clone the project
3. Create a database called `chatroom` on Postgres

## Running
```
mvn spring-boot:run
```

## Usage*
The following endpoints are fully funcitonal by now

```
Due to latest changes on API, this is part of documentation is pending
```

Private messages are not fully implemented yet.  
* All API calls require the header `Auth-Token` with the token given on by application when calling `/auth/github`