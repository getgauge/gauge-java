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

#!/bin/sh

#Using protoc version 2.6.1
cd gauge-proto
protoc --java_out=../src/main/java/ spec.proto
protoc --java_out=../src/main/java/ messages.proto
protoc --java_out=../src/main/java/ api.proto
protoc --java_out=../src/main/java/ lsp.proto