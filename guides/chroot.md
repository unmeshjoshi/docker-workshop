Chroot Lab
==========

# Intro

To learn about isolation primitives, we start off with a common tool used for isolating the filesystem. chroot is a small tool that allows you to **ch**ange the **root** directory for running or isolating a program.

# First steps

To try out the tool quickly, issue the following commands:

```bash
$ mkdir jail
$ echo "Hello World" > jail/myfile.txt
$ sudo chroot jail/
chroot: /bin/bash: No such file or directory
```

The error tells us that it is not able to locate `/bin/bash` which is the default program that it tries to execute. We are also able to supply the program that we want it to execute. Try the following:

```bash
$ sudo chroot jail/ ls -l
chroot: ls: No such file or directory
```
When we run the above command, chroot designates `jail/` as the root directory. Since there is only one file in this isolated filesystem (/myfile.txt), the command `ls` won't work (`ls` resides at `/bin/ls`).

# Busybox

[busybox] is a very small executable that is the swiss-army knife of UNIX utilities. We can get it working in our chroot'ed environment in order to explore it.

We'll need to re-create the filesystem necessary for `busybox` to work.

```bash  
$ cp --parents /bin/busybox jail/
$ sudo chroot jail/ busybox sh
chroot: failed to run command 'busybox': Not a directory
```

It's failed again. The reason is, `busybox` depends on other system libraries in order to work. We can find a list of libraries that it needs using the `otool -L` command in macOS or `ldd` in linux. These libraries in turn depend on other libraries to work. We can simplify our task by copying entire library directories itself (for Ubuntu, you can copy `/lib/x86_64-linux-gnu` and `/lib64`):

```bash
$ cp -r --parents /lib/x86_64-linux-gnu jail/
$ cp -r /lib64 jail/
$ sudo chroot jail/ busybox sh

BusyBox v1.21.1 (Ubuntu 1:1.21.0-1ubuntu1) built-in shell (ash)
Enter 'help' for a list of built-in commands.

/ # ls -l
total 12
drwxr-xr-x    2 1000     1000          4096 Jul 26 01:57 bin
drwxr-xr-x    3 1000     1000          4096 Jul 26 01:53 lib
drwxr-xr-x    2 1000     1000          4096 Jul 26 01:53 lib64
-rw-rw-r--    1 1000     1000             0 Jul 26 01:46 myfile.txt
```

It worked! The output shows us the files in the `jail` directory. Since `jail` is now `/`, we can try out absolute paths:

```bash
/ # cat /myfile.txt
Hello World
```


[busybox]: https://busybox.net/