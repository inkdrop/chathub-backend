#!/usr/bin/env bash

set -eu

mvn clean package -DskipTests;

docker build . -t luizkowalski/chathub:qa;
docker push luizkowalski/chathub:qa;

mvn clean;
