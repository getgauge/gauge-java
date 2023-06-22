# Gauge-Java

[![Actions Status](https://github.com/getgauge/gauge-java/workflows/tests/badge.svg)](https://github.com/getgauge/gauge-java/actions)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.thoughtworks.gauge/gauge-java/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.thoughtworks.gauge/gauge-java)
[![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-v1.4%20adopted-ff69b4.svg)](CODE_OF_CONDUCT.md)

This project adds java [language plugin](https://gauge.org/plugins/) for [gauge](https://gauge.org/).

## Getting started

### Pre-requisite

- [Install Gauge](https://docs.gauge.org/getting_started/installing-gauge.html)
- [Java](https://www.java.com/en/download/) (Minimum required version is 11)

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
gauge install java --version 0.10.0
```

#### Offline installation
* Download the plugin from [Releases](https://github.com/getgauge/gauge-java/releases)
```
gauge install java --file gauge-java-0.10.0-windows.x86_64.zip
```

#### Build from source

The plugin is authored in [Java](https://en.wikipedia.org/wiki/Java_(programming_language)).
Gauge is authored in golang. These are independent processes talking to each other over TCP on port GAUGE_INTERNAL_PORT (env variable) using [Protobuf](https://github.com/getgauge/gauge-proto).

##### Additional Requirements
Apart from [Gauge](https://gauge.org/index.html) and [Java](https://www.java.com/en/download/index.jsp) you will need

* [Maven](https://maven.apache.org/)
* [JQ](https://stedolan.github.io/jq/) (for unix)
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


## Deployment

The deployments are managed via Github Actions. Whenever a PR is merged and contains label `ReleaseCandidate`, A deployment will occur with the current version and then the version will be bumped up.


## License

This program is licensed under:

Apache License, Version 2.0

## Copyright

Copyright ThoughtWorks, Inc.

## Acknowledgements

This project is supported by [YourKit Java Profiler](https://www.yourkit.com/java/profiler/index.jsp). YourKit supports open source projects with its full-featured Java Profiler, which is used by Gauge Team.

![Yourkit](https://www.yourkit.com/images/yklogo.png)
