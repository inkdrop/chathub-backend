#!/usr/bin/env bash

#siege -d 1 -c 50 -t60S -H 'Content-Type: application/json' -H 'Auth-Token: 4&yKhXHFkzZaI0NjaQo)4Ql7W' 'http://127.0.0.1:8080/v1/rooms/16587783/messages/new POST {"content": "message"}'
siege -d 1 -c 50 -t10S -H 'Content-Type: application/json' -H 'Auth-Token: 4&yKhXHFkzZaI0NjaQo)4Ql7W' 'http://127.0.0.1:8080/v1/rooms/16587783/messages/new POST {"content": "message"}'
