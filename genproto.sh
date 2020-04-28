#!/bin/sh

# *----------------------------------------------------------------
# *  Copyright (c) ThoughtWorks, Inc.
# *  Licensed under the Apache License, Version 2.0
# *  See LICENSE.txt in the project root for license information.
# *----------------------------------------------------------------*/

#Using protoc version 2.6.1
for filename in gauge-proto/*.proto; do
  newName="$filename-bkp"
  sed 's/option java_package = "com.thoughtworks.gauge";/option java_package = "gauge.messages";/' "$filename" > "$newName"
  rm "$filename"
  cp "$newName" "$filename"
  rm "$newName"
done
mvn protobuf:compile-custom protobuf:compile


cp  target/generated-sources/protobuf/java/gauge/messages/Messages.java src/main/java/gauge/messages
cp  target/generated-sources/protobuf/java/gauge/messages/Spec.java src/main/java/gauge/messages
cp  target/generated-sources/protobuf/java/gauge/messages/Services.java src/main/java/gauge/messages

cp  target/generated-sources/protobuf/grpc-java/gauge/messages/RunnerGrpc.java src/main/java/gauge/messages

cd gauge-proto && git checkout . && cd ..
