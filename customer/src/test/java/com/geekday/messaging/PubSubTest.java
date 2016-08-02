package com.geekday.messaging;

import org.junit.Test;
import org.zeromq.ZMQ;

import static org.junit.Assert.assertEquals;

public class PubSubTest {

    @Test
    public void shouldBeAbleToPublishAndSubscribeToTopic() throws InterruptedException {

        ZMQ.Context ctx = ZMQ.context(4);
        ZMQ.Socket publisherSocket = ctx.socket(ZMQ.PUB);
        publisherSocket.bind("tcp://*:5557");

        ZMQ.Context subCtx = ZMQ.context(4);

        ZMQ.Socket subscriberSocket = subCtx.socket(ZMQ.SUB);
        subscriberSocket.connect("tcp://*:5557");
        subscriberSocket.subscribe("topic1".getBytes());


        Thread.sleep(2000);//


        publisherSocket.sendMore("topic2");
        publisherSocket.send("Hello World 2!");

        publisherSocket.sendMore("topic1");
        publisherSocket.send("Hello World 1!");

        String topic = subscriberSocket.recvStr();
        String message = subscriberSocket.recvStr();

        assertEquals("Hello World 1!", message);
    }
}