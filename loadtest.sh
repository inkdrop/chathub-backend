#!/usr/bin/env bash

siege -d 1 -c 50 -t60S -H 'Content-Type: application/json' -H 'Auth-Token: qyz3kHZFuXRw8nm14uYLjLNoQ' 'http://127.0.0.1:8080/v1/rooms/16587783/messages/new POST {"content": "message"}'
