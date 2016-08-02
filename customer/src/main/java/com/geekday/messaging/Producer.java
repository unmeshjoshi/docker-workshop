package com.geekday.messaging;

import org.zeromq.ZMQ;

public class Producer {

    private final static Producer instance = new Producer();
    private final ZMQ.Socket publisherSocket;

    public Producer() {
        ZMQ.Context ctx = ZMQ.context(4);
        publisherSocket = ctx.socket(ZMQ.PUB);
        publisherSocket.bind("tcp://localhost:5555");
    }

    public static Producer getInstance() {
        return instance;
    }

    public void publish(String topic, String message) {
        publisherSocket.sendMore(topic);
        publisherSocket.send(message);
    }
}
