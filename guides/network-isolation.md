Network Isolation
=================

# Intro

In the previous section, we took our chroot'ed environment and isolated it's CPU using cgroups. We will now isolate it's network using Linux network namespaces.

# First steps

We can verify that our network in the chroot'ed environment is not isolated. We need to use two separate sessions: one for the chrooted environment, the other for the host:

```bash
chroot # nc -l -p 4444
host # netstat -nptel
Proto Recv-Q Send-Q Local Address           Foreign Address         State       User       Inode       PID/Program name
tcp6       0      0 :::4444                 :::*                    LISTEN      0          22176       3499/nc
host # nc localhost 4444
```

`nc` is a command that allows us to connect and listen to ports. The first command is used to listen to the port `4444`. Then, in the host, we use `netstat` to list out bound ports. We can see the `4444` port in the output. We can also connect to the bound port from host using `nc`. Bottomline is that, the network is not isolated and we are opening up ports on our container's host.

# Creating a network namespace

A network namespace is an isolated software network. Creating one is easy:

```bash
host # ip netns add myns
host # ip link list
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN mode DEFAULT group default 
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
2: eth0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP mode DEFAULT group default qlen 1000
    link/ether 08:00:27:04:89:54 brd ff:ff:ff:ff:ff:ff
```

The first command creates the network namespace. The second lists network interfaces. There are two devices in your network. `lo` is the loopback device. `eth0` is a real network interface card that possibly provides you internet. Let's check the devices that exist in our guest:

```bash
host # ip netns exec myns bash
guest # ip link list
1: lo: <LOOPBACK> mtu 65536 qdisc noop state DOWN group default 
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
```

There is only the loopback device in the namespace. The namespace is thus an isolated network.

# Creating virtual devices

You can create virtual network devices and wire them up in arbitrary ways. However, virtual network devices are always created in pairs. This is because your devices always need to be connected to some other device, or else they'll be useless.

```bash
host # ip link add veth0 type veth peer name veth1
host # ip link list
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN mode DEFAULT group default 
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
2: eth0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP mode DEFAULT group default qlen 1000
    link/ether 08:00:27:04:89:54 brd ff:ff:ff:ff:ff:ff
3: veth1: <BROADCAST,MULTICAST> mtu 1500 qdisc noop state DOWN mode DEFAULT group default qlen 1000
    link/ether 3e:85:6f:c8:80:bb brd ff:ff:ff:ff:ff:ff
4: veth0: <BROADCAST,MULTICAST> mtu 1500 qdisc noop state DOWN mode DEFAULT group default qlen 1000
    link/ether ce:37:c3:56:61:39 brd ff:ff:ff:ff:ff:ff
```

The two devices are created. We want to now place one of those devices in the network namespace that we created:

```bash
host # ip link set veth0 netns myns
host # ip netns exec myns bash
guest # ip link list
1: lo: <LOOPBACK> mtu 65536 qdisc noop state DOWN mode DEFAULT group default 
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
4: veth0: <BROADCAST,MULTICAST> mtu 1500 qdisc noop state DOWN mode DEFAULT group default qlen 1000
    link/ether ce:37:c3:56:61:39 brd ff:ff:ff:ff:ff:ff
```

We can now see the device `veth0` placed in our network namespace. They are currently turned off. Let's turn them on and assign them IPs.

```bash
host # ip link set veth1 up
host # ip addr add 192.168.1.1/24 dev veth1
host # ip netns exec myns bash
guest # ip link set veth0 up
guest # ip addr add 192.168.1.2/24 dev veth0
```

We can now try and bind to ports. Ports bound to in the guest should not affect the host.

```bash
guest # nc -l -p 4444
host # nc localhost 4444
host # 
```

Since the host and guest are connected by the device pair, you can actually establish connection between them. For this, you need to supply the IPs:

```bash
guest # nc -l -p 4444
host # nc 192.168.1.2 4444
````

# Exercise: Usage with chroot and cgroups

To build your own container, you need to bring chroot, cgroups CPU isolation and network isolation together. Here's how you can do that:

1. Create network namespace and execute a bash shell in the namespace
2. Add the PID of that bash shell to the `task` file of your cgroup
3. Open up a chrooted environment in the shell


