CPU Isolation
=============

# Intro

In the previous section, we isolated the program `busybox` such that it was only able to access a part of the filesystem. We can take this further and isolate the CPU usage of the program. This is done through a Linux kernel feature called cgroups (**C**ontrol **Groups**).

We make use of a nifty Linux program called `stress` that allows us to put a constant load on our CPUs. Let us copy over `stress` to our chrooted environment:

```bash
$ cp --parents /usr/bin/stress jail/
$ sudo chroot jail/ busybox sh
/ # stress -c 2
stress: info: [4148] dispatching hogs: 2 cpu, 0 io, 0 vm, 0 hdd
```

To check how much CPU `stress` is using, run `htop` on the host machine in a separate session. You will notice that both the CPUs are being utilized 100%.

# cgroups

cgroups are managed via the filesystem itself at `/sys/fs/cgroup`. Here's how you create a cgroup:

```bash
$ sudo mkdir /sys/fs/cgroup/cpuset/chroot
$ ls -l /sys/fs/cgroup/cpuset/chroot
total 0
-rw-r--r-- 1 root root 0 Aug  3 01:36 cgroup.clone_children
-rw-r--r-- 1 root root 0 Aug  3 01:36 cgroup.procs
-rw-r--r-- 1 root root 0 Aug  3 01:36 cpuset.cpu_exclusive
-rw-r--r-- 1 root root 0 Aug  3 01:36 cpuset.cpus
-r--r--r-- 1 root root 0 Aug  3 01:36 cpuset.effective_cpus
-r--r--r-- 1 root root 0 Aug  3 01:36 cpuset.effective_mems
-rw-r--r-- 1 root root 0 Aug  3 01:36 cpuset.mem_exclusive
-rw-r--r-- 1 root root 0 Aug  3 01:36 cpuset.mem_hardwall
-rw-r--r-- 1 root root 0 Aug  3 01:36 cpuset.memory_migrate
-r--r--r-- 1 root root 0 Aug  3 01:36 cpuset.memory_pressure
-rw-r--r-- 1 root root 0 Aug  3 01:36 cpuset.memory_spread_page
-rw-r--r-- 1 root root 0 Aug  3 01:36 cpuset.memory_spread_slab
-rw-r--r-- 1 root root 0 Aug  3 01:36 cpuset.mems
-rw-r--r-- 1 root root 0 Aug  3 01:36 cpuset.sched_load_balance
-rw-r--r-- 1 root root 0 Aug  3 01:36 cpuset.sched_relax_domain_level
-rw-r--r-- 1 root root 0 Aug  3 01:36 notify_on_release
-rw-r--r-- 1 root root 0 Aug  3 01:36 tasks
```

Some files have been automatically created in your new cgroup. We can now edit these files to customize our cgroup:

```bash
$ sudo su
# echo 0 > /sys/fs/cgroup/cpuset/chroot/cpuset.cpus
```

Here we assign the CPU `0` to our cgroup. This means that processes within our cgroup are only able to access CPU `0`. The final task is to add the `bash` process where `chroot` is running:

```bash
$ echo $$
3276
root      4061  0.0  0.1  64956  3920 pts/0    S    01:29   0:00 sudo chroot jail/ busybox sh
$ sudo su
# echo 3276 > /sys/fs/cgroup/cpuset/chroot/tasks
$ sudo chroot jail/ busybox sh
/ # stress -c 2
stress: info: [4148] dispatching hogs: 2 cpu, 0 io, 0 vm, 0 hdd
```

`htop` will confirm that the `stress` utility now only takes 100% of one CPU. The other CPU is free to do other minor tasks in the system.
