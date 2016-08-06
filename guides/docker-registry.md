Intro
=====

Docker registry can be used to push and pull images from. It is basically a library of docker images. You can use registry to deploy and share images in your environment.

Running the registry
====================

Execute the following command to run a local registry:

```bash
$ docker run -d -p 5000:5000 --name registry registry:2
```

The registry is now running on port 5000.

Pushing an image into the registry
==================================

We need to tag images with a prefix that specifies the registry they will end up in. Let's tag our customer service to point to the local registry:

```bash
$ docker tag customer-service-image localhost:5000/customerservice
```

Now let's push it.

```bash
$ docker push localhost:5000/customerservice
```

Pulling images
==============

We can pull back our image:

```bash
$ docker pull localhost:5000/customerservice
```

Cleaning up
===========

```bash
docker stop registry && docker rm -v registry
```
