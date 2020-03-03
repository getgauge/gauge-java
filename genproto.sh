#!/bin/sh

#Copyright 2015 ThoughtWorks, Inc.

#This file is part of Gauge-Java.

# This program is free software.
#
# It is dual-licensed under:
# 1) the GNU General Public License as published by the Free Software Foundation,
# either version 3 of the License, or (at your option) any later version;
# or
# 2) the Eclipse Public License v1.0. You can redistribute it and/or modify it under the terms of either license.
#
# We would then provide copied of each license in a separate .txt file with the name of the license as the title of the file.

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
