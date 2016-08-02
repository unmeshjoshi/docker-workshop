#!/bin/bash

echo "Getting dependencies..."

docker_compose_version="1.7.1"
docker_compose_url="https://github.com/docker/compose/releases/download/${docker_compose_version}/docker-compose-`uname -s`-`uname -m`"
cache_dir="/var/workshop-cache"

sudo apt-get update
sudo apt-get install -y git software-properties-common python-software-properties

# install JDK 8
sudo bash -c "echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | debconf-set-selections && \
add-apt-repository -y ppa:webupd8team/java && \
apt-get update && \
apt-get install -y oracle-java8-installer"

# install and cache docker-compose
if [[ ! -f /usr/local/bin/docker-compose ]]; then
  if [[ ! -f ${cache_dir}/docker-compose ]]; then
    curl -L ${docker_compose_url} > ${cache_dir}/docker-compose
  fi
  sudo cp ${cache_dir}/docker-compose /usr/local/bin/docker-compose
  sudo chmod a+x /usr/local/bin/docker-compose
fi
