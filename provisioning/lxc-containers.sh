#!/bin/bash

sudo apt-get update

# Install lxc package
sudo apt-get install -y lxc

# Pulls the ubuntu template - This take a while, hence included in the provisioning
sudo lxc-create -t ubuntu -n demo-container
