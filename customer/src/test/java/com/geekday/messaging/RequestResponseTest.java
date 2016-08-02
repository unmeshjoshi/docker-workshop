package com.geekday.messaging;

import org.junit.Test;
import org.zeromq.ZMQ;

import static org.junit.Assert.assertEquals;

public class RequestResponseTest {

    @Test
    public void shouldSendAndReceiveMessages() throws InterruptedException {
        ZMQ.Context ctx = ZMQ.context(1);
        ZMQ.Socket responseSocket = ctx.socket(ZMQ.REP);
        responseSocket.bind("tcp://*:5556");

        ZMQ.Socket requestSocket = ctx.socket(ZMQ.REQ);
        requestSocket.connect("tcp://*:5556");

        requestSocket.send("Hello World!");

        byte[] request = responseSocket.recv();

        responseSocket.send(new String(request) + " from server");//echo request;

        assertEquals("Hello World! from server", new String(requestSocket.recv()));
    }

}
