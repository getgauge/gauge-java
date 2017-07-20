Gauge-java
==========

This is the java [language plugin](http://getgauge.io/documentation/user/current/test_code/java/java.html) for [gauge](http://getgauge.io).
[![Build status](https://ci.appveyor.com/api/projects/status/vhluq2mpktp87usw?svg=true)](https://ci.appveyor.com/project/sriv/gauge-java)
 [![Build Status](https://travis-ci.org/getgauge/gauge-java.svg?branch=master)](https://travis-ci.org/getgauge/gauge-java)
 [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.thoughtworks.gauge/gauge-java/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.thoughtworks.gauge/gauge-java)

|Gauge-Java Nightly|Maven-Gauge-Java Nightly|
|----------|----------------|
|[ ![Download Nightly](https://api.bintray.com/packages/gauge/gauge-java/Nightly/images/download.svg) ](https://bintray.com/gauge/gauge-java/Nightly/_latestVersion)| [ ![Download Maven Nightly](https://api.bintray.com/packages/gauge/maven-gauge-java/Nightly/images/download.svg) ](https://bintray.com/gauge/maven-gauge-java/Nightly/_latestVersion)| 

It contains a launcher component (gauge-java.go) written in golang which is used to start the plugin from gauge.

Install through Gauge
---------------------
````
gauge --install java
````

* Installing specific version
```
gauge --install java --plugin-version 0.0.7
```

### Offline installation
* Download the plugin from [Releases](https://github.com/getgauge/gauge-java/releases)
```
gauge --install java --file gauge-java-0.0.7-windows.x86_64.zip
```

# Build from source

###Requirements
* [Golang](http://golang.org/)
* [Java] (https://www.java.com/en/download/index.jsp)
* [Maven](https://maven.apache.org/)
* [Gauge](http://getgauge.io)

### Compiling

````
go run make.go
````

For cross platform compilation (launcher)

````
go run make.go --all-platforms
````

###Installing

After compilation
````
go run make.go --install
````

Installing to a CUSTOM_LOCATION

````
go run make.go --install --plugin-prefix CUSTOM_LOCATION
````

###Creating distributable

Note: Run after compiling

````
go run make.go --distro
````

For distributable across platforms os, windows and linux for bith x86 and x86_64

````
go run make.go --distro --all-platforms
````

New distribution details need to be updated in the java-install.json file in  [gauge plugin repository](https://github.com/getgauge/gauge-repository) for a new verison update.

License
-------

This program is dual-licensed under:
1. the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version;
or
2. the Eclipse Public License v1.0. You can redistribute it and/or modify it under the terms of either license.

Copyright
---------

Copyright 2016 ThoughtWorks, Inc.

Acknowledgement
---------------

This project is supported by [YourKit Java Profiler](https://www.yourkit.com/java/profiler/index.jsp). YourKit supports open source projects with its full-featured Java Profiler, which is used by Gauge Team.

![Yourkit](https://www.yourkit.com/images/yklogo.png)
