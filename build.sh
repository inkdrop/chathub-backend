#!/usr/bin/env bash

set -e

mvn clean package docker:build -DskipTests -DpushImage;

find . -name *chathub*.jar -exec du -h {} \;

mvn clean;
