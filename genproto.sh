#!/bin/sh

#Using protoc version 2.5.0
cd gauge-proto
protoc --java_out=../src/main/java/ spec.proto
protoc --java_out=../src/main/java/ messages.proto
protoc --java_out=../src/main/java/ api.proto

