#!/usr/bin/env bash

set -e

mvn clean package -DskipTests;

docker build . -t luizkowalski/chathub:qa;
docker push luizkowalski/chathub:qa;

mvn clean;
