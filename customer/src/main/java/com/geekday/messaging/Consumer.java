package com.geekday.messaging;

import org.zeromq.ZMQ;

public class Consumer {
    private final ZMQ.Context ctx;
    private final ZMQ.Socket subscriberSocket;

    public Consumer(String topic) {
        ctx = ZMQ.context(4);
        subscriberSocket = ctx.socket(ZMQ.SUB);
        subscriberSocket.connect("tcp://localhost:5555");
        subscriberSocket.subscribe(topic.getBytes());
    }

    public String readMessage() {
        String topic =  subscriberSocket.recvStr();
        String message = subscriberSocket.recvStr();
        return message;
    }
}
