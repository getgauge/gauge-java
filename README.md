# Gauge-Java

[![Actions Status](https://github.com/getgauge/gauge-java/workflows/tests/badge.svg)](https://github.com/getgauge/gauge-java/actions)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.thoughtworks.gauge/gauge-java/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.thoughtworks.gauge/gauge-java)
[![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-v1.4%20adopted-ff69b4.svg)](CODE_OF_CONDUCT.md)

This project adds java [language plugin](https://docs.gauge.org/plugins.html#language-reporting-plugins) for [gauge](http://getgauge.io).

## Getting started

### Pre-requisite

- [Install Gauge](https://docs.gauge.org/installing.html#installation)
- [Java](https://www.java.com/en/download/)

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

#### Build from source

The plugin is authored in [Java](https://en.wikipedia.org/wiki/Java_(programming_language)).
Gauge is authored in golang. These are independent processes talking to each other over TCP on port GAUGE_INTERNAL_PORT (env variable) using [Protobuf](https://github.com/getgauge/gauge-proto).

##### Additional Requirements
Apart from [Gauge](https://gauge.org/index.html) and [Java](https://www.java.com/en/download/index.jsp) you will need

* [Maven](https://maven.apache.org/)
* [JQ](https://stedolan.github.io/jq/) (for unix)
##### Compiling

````
./build.sh | .\build.ps1 build
````

##### Installing

After compilation

````
./build.sh | .\build.ps1 forceinstall
````

##### Creating distributable

Note: Run after compiling

````
./build.sh | .\build.ps1 package
````


## Deployment

Only contributors with push access can create a deployment.

The deployment process is managed via Github Actions.

Follow these steps to deploy gauge-java.

* Create a Personal Access Token in Github with `repo:public_repo` scope (skip this step if you already have a PAT).
* Run `GITHUB_TOKEN={Your token} sh release.sh` in `gauge-java` dir. This will trigger a deployment workflow on Github Actions. This workflow creates a release draft with all required assets and information.
* Visit to the release draft, analyze and update the contents (remove unnecessary entries, mention Contributors, remove dependabot PR entries).
* Publish the draft release.
* Once the draft is published it will trigger another workflow on Github Actions, which will perform all the Post release tasks, In case of gauge-java it will upload the `gauge-java` maven artifact to maven central.
* Once it's done please update the new release information in [gauge-repository](https://github.com/getgauge/gauge-repository/blob/master/java-install.json)

* That's it. Now the release can be announced on the required community platforms (chat, google group etc.)


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
