#!/usr/bin/env bash

set -e

mvn clean package -DskipTests;

find . -name *chathub*.jar -exec du -h {} \;
