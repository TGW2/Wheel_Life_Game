#!/usr/bin/env sh
set -eu

mvn -q -DskipTests package
java -jar target/project-g6h9c-1.0.0.jar
