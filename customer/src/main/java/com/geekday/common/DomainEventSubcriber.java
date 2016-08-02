package com.geekday.common;

import com.geekday.messaging.Consumer;

public class DomainEventSubcriber {

    private Consumer consumer;
    private String topic;

    public DomainEventSubcriber(String topic) {
        this.topic = topic;
        this.consumer = new Consumer(topic);
    }

    public DomainEvent receive() {
        String content = consumer.readMessage();
        return new DomainEvent(topic, content);
    }

}
