#!/usr/bin/env bash

set -e

mvn clean package -DskipTests=true;

find . -name *chathub*.jar -exec du -h {} \;
