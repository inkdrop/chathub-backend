#!/usr/bin/env bash

set -e

mvn clean test -Dspring.profiles.active=test
