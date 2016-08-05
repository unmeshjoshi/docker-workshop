Customer-service Lab
==========

# Intro

Build image for customer service.

# Building docker image for customer service

To try out the tool quickly, issue the following commands:

```bash
$ vagrant ssh
$ cd /vagrant/customer
$ ./gradlew  clean build shadowJar copyDockerFile
$ docker build -t customer-service-image ./build/
```

# Running docker image for customer service
docker run --name customerservice -p 8082:8082 customer-service-image
curl --data "name=John&address=USA" -i http://localhost:8082/customer
