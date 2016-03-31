# Chathub

Java backend for Chathub project

## Setting up the Project

**Dependencies**

* Java 8
* PostgreSQL 9.3 or superior
* RabbitMQ (for calling v1 messages API)

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

| Endpoint                          | Param             | Body                                 | Return                                                       |
| --------------------------------- | ----------------- | ------------------------------------ | ------------------------------------------------------------ |
| `/auth/github?token=X`            | token  from gitub |                                      | chathub user                                                 |
| `/v1/rooms/{name}`                | room's name       |                                      | room information                                             |
| `/v1/rooms`                       |                   |                                      | user's rooms                                                 |
| `/v1/rooms/{name}/join`           | room's name       |                                      |                                                              |
| `/v1/rooms/{name}/leave`          | room's name       |                                      | leave a room                                                 |
| `/v1/rooms/{name}/messages`       | room's name       |                                      |                                                              |
| `/v1/rooms/{room}/messages/new`   | room's name       | `{ "content": "message content" }`   | send a new message                                           |
| `/v2/rooms/{room}/messages/new`   | room's name       | `{ "content": "message content" }`   | send a new message using the v2 api (don't require RabbitMQ) |

Private messages are not fully implemented yet.  
* All API calls require the header `Auth-Token` with the token given on by application when calling `/auth/github`