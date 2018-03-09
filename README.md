# Gauge-Java

[![Build status](https://ci.appveyor.com/api/projects/status/b3xfe7d6fftbq6gf?svg=true)](https://ci.appveyor.com/project/getgauge/gauge-java)
[![Build Status](https://travis-ci.org/getgauge/gauge-java.svg?branch=master)](https://travis-ci.org/getgauge/gauge-java)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.thoughtworks.gauge/gauge-java/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.thoughtworks.gauge/gauge-java)

|Gauge-Java Nightly|Maven-Gauge-Java Nightly|
|----------|----------------|
|[ ![Download Nightly](https://api.bintray.com/packages/gauge/gauge-java/Nightly/images/download.svg) ](https://bintray.com/gauge/gauge-java/Nightly/_latestVersion)| [ ![Download Maven Nightly](https://api.bintray.com/packages/gauge/maven-gauge-java/Nightly/images/download.svg) ](https://bintray.com/gauge/maven-gauge-java/Nightly/_latestVersion)|

This project adds java [language plugin](https://docs.gauge.org/plugins.html#language-reporting-plugins) for [gauge](http://getgauge.io).

The plugin is authored in [Java](https://en.wikipedia.org/wiki/Java_(programming_language)). It contains a launcher component (gauge-java.go) written in golang which is used to start the plugin from [gauge](https://github.com/getgauge/gauge).

## Getting started

### Pre-requisite

- [Install Gauge](https://docs.gauge.org/installing.html#installation)

### Installation

```
gauge install java
```

### Create a gauge-java project

```
gauge init java
```

### Run tests

```
gauge run specs
```

### Alternate Installation options

#### Install specific version
* Installing specific version
```
gauge install java --version 0.6.5
```

#### Offline installation
* Download the plugin from [Releases](https://github.com/getgauge/gauge-java/releases)
```
gauge install java --file gauge-java-0.6.5-windows.x86_64.zip
```

#### Nightly installation

To install java nightly, download the latest nightly from [here](https://bintray.com/gauge/gauge-java/Nightly).

Once you have the downloaded nightly gauge-java-version.nightly-yyyy-mm-dd.zip, install using:

    gauge install java -f gauge-java-version.nightly-yyyy-mm-dd.zip

#### Build from source

##### Requirements
* [Golang](http://golang.org/)
* [Java](https://www.java.com/en/download/index.jsp)
* [Maven](https://maven.apache.org/)
* [Gauge](http://getgauge.io)

##### Compiling

````
go run make.go
````

For cross platform compilation (launcher)

````
go run make.go --all-platforms
````

##### Installing

After compilation
````
go run make.go --install
````

Installing to a CUSTOM_LOCATION

````
go run make.go --install --plugin-prefix CUSTOM_LOCATION
````

##### Creating distributable

Note: Run after compiling

````
go run make.go --distro
````

For distributable across platforms os, windows and linux for bith x86 and x86_64

````
go run make.go --distro --all-platforms
````

New distribution details need to be updated in the java-install.json file in  [gauge plugin repository](https://github.com/getgauge/gauge-repository) for a new verison update.

## License

This program is dual-licensed under:
1. the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version;
or
2. the Eclipse Public License v1.0. You can redistribute it and/or modify it under the terms of either license.

## Copyright

Copyright 2016 ThoughtWorks, Inc.

## Acknowledgements

This project is supported by [YourKit Java Profiler](https://www.yourkit.com/java/profiler/index.jsp). YourKit supports open source projects with its full-featured Java Profiler, which is used by Gauge Team.

![Yourkit](https://www.yourkit.com/images/yklogo.png)
