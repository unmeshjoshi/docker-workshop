Services
========

We have three services:
- customerweb
- accountservice
- customerservice

They are dependent on each other to function properly.
Compiling Services
=======================
```bash
$ cd customerweb
$ ./gradlew  clean build shadowJar copyDockerFile
$ cd ../account
$ ./gradlew  clean build shadowJar copyDockerFile
$ cd ../customer
$ ./gradlew  clean build shadowJar copyDockerFile
```


Building Service Images
=======================

We assume that you have run `gradle build` to compile each service. Each service image needs to be built using their corresponding `Dockerfile`. Here's how you do it:

```bash
$ cd customerweb
$ docker -t customerweb-image ./build
$ cd ../account
$ docker -t account-service-image ./build
$ cd ../customer
$ docker -t customer-service-image ./build
```

Creating docker network
=======================

The services need to be run on a separate, isolated docker network. Let's create it:

```bash
$ docker network create --driver bridge isolated_nw
```

Running the services
====================

Since we have each docker image in hand, we can now run the services. Note that each service is supplied the isolated network, so that they can talk to each other.

```bash
$ docker run -d --network isolated_nw --name customerweb -p 8085:8085 customerweb-image
$ docker run -d --network isolated_nw --name accountservice -p 8080:8080 account-service-image
$ docker run -d --network isolated_nw --name customerservice -p 8082:8082 customer-service-image
```

Testing if the services are up
==============================


Check if the services are running:

```bash
$ docker ps
```

We can now call them and see if they work:

```bash
$ curl --data "name=John&address=USA" -i http://localhost:8082/customer
$ curl -i http://localhost:8085/summary
```
