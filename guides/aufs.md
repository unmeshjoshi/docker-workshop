AUFS Lab
==========

# Intro

Uderstand aufs and Copy on write behaviour.

# Looking at aufs layers

To try out the tool quickly, issue the following commands:

```bash
$ vagrant ssh
$ cd /vagrant/aufsdemo
$ docker build . -t aufstest-image
$ sudo find /var/lib/docker -name 'testfile.txt'
$ docker run -t --name aufstest-container -i aufstest-image
$ Edit testfile.txt
$ sudo find /var/lib/docker -name 'testfile.txt'
```
