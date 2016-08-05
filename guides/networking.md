Networking Lab
==========

# Intro

Creating a network with docker.

# Looking at aufs layers

To try out the tool quickly, issue the following commands:

```bash
$ vagrant ssh
$ cd /vagrant/
$ docker network create --driver bridge isolated_nw
$ docker run -d --network isolated_nw --name customerweb -p 8085:8085 customerweb-image
$ docker run -d --network isolated_nw --name customerservice -p 8082:8082 customer-service-image
$ curl --data "name=John&address=USA" -i http://localhost:8082/customer
$ curl -i http://localhost:8085/summary
```
