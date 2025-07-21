# UNS Microservices
This Gradle-powered monorepo hosts a number of Spring Boot Dockerised microservices, and a few commons libraries, that constitute the platform for a Unified Namespace-powered enterprise (e.g. Industrial Internet of Things -- IIoT, or any enterprise) that is digitally transforming/transformed. Continuous Integration is performed by GitHub Actions.

To build and install the libraries only (not the services) to local maven run
```shell
$ ./gradlew clean publishToMavenLocal
```


To build the entire monorepo run
```shell
$ ./gradlew --stop
$ ./gradlew clean build --refresh-dependencies
```


To build a spring boot app as a JAR run
```shell
$ ./gradlew clean bootJar
```


To run a specific spring boot app run
```shell
$ java -jar services/service1/build/libs/app.jar
```


To run a particular service (e.g. service1) using Docker run:
```shell
$ cd workspaces/my-project/services/service1
$ docker-compose up --build
```
