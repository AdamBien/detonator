#!/bin/sh
mvn clean package
native-image -jar target/detonator.jar