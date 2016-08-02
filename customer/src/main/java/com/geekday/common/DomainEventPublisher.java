package com.geekday.common;

import com.geekday.messaging.Producer;

public class DomainEventPublisher { //we need only one publisher.
    private static DomainEventPublisher instance = new DomainEventPublisher();
    private DomainEventPublisher(){}

    private Producer producer = Producer.getInstance();

    public static DomainEventPublisher start() {
        return getInstance();
    }

    public static DomainEventPublisher getInstance() {
        return instance;
    }

    public void publish(DomainEvent event) {

        producer.publish(event.getType(), event.getCsv());
    }
}
