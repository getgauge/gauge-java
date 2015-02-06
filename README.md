
gauge-java
==========

This is the java [language plugin](http://getgauge.io/documentation/plugins/README.html) for [gauge](http://getgauge.io).

It contains a launcher component (gauge-java.go) written in golang which is used to start the plugin from gauge.

Requirements
-----------
* [Golang](http://golang.org/)
* [Java] (https://www.java.com/en/download/index.jsp)
* [Ant](https://ant.apache.org/)
* [Gauge](http://getgauge.io)


Compiling
---------

````
go run make.go
````

For cross platform compilation (launcher)

````
go run make.go --all-platforms
````

Installing
----------
After installing gauge

````
go run make.go --install
````

Installing to a CUSTOM_LOCATION

````
go run make.go --install --plugin-prefix CUSTOM_LOCATION
````

Creating distributable
----------------------

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

![GNU Public License version 3.0](http://www.gnu.org/graphics/gplv3-127x51.png)
Gauge-Java is released under [GNU Public License version 3.0](http://www.gnu.org/licenses/gpl-3.0.txt)

Copyright
---------

Copyright 2015 ThoughtWorks, Inc.
