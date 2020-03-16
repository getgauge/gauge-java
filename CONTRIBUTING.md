## Contributing to Gauge

Contributions to Gauge are welcome and appreciated. Please read this document to understand the process for contributing.

## Gauge Core v/s Plugins

Gauge Core is a project that has features that would reflect across all Gauge use cases. These features are typically agnostic of the user's choice of implementation language. 

Plugins are meant to do something specific. These could be adding support for a new language, or have a new report etc. 

So, depending on where you see your contribution fit, please focus on the respective repository.

## Contribution process

Please read about the Contribution Process [here](https://github.com/getgauge/gauge/blob/master/CONTRIBUTING.md), if you are happy please sign the [Contributor's License Agreement](https://gauge-bot.herokuapp.com/cla/). 

## How can I contribute

Contributions can be of many forms:

- Open an issue, or participate in an existing one. 
- Write some code, and send us a pull request.
- Enhance the documentation
- Participate in design discussions on Google Groups

If you need help in getting started with contribution, feel free to reach out on the [Google Groups](https://groups.google.com/forum/#!forum/getgauge) or [Gitter](https://gitter.im/getgauge/chat).


## Bump up gauge-java version

* Update the version in `java.json` file.

Ex:
```diff
     "id": "java",
-    "version": "0.7.5",
+    "version": "0.7.6",
```

* Update the productVersion property in `pom.xml`.

Ex:


* Update the productVersion property in `pom.xml`.

Ex:

```diff
     <properties>
         <maven.build.timestamp.format>yyyy-MM-dd</maven.build.timestamp.format>
-        <projectVersion>0.7.5</projectVersion>
+        <projectVersion>0.7.6</projectVersion>
         <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
```
