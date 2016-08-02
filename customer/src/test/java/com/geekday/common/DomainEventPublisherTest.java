package com.geekday.common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DomainEventPublisherTest {

    @Test
    public void shouldPublishEvents() throws InterruptedException {
        //create this before creating sub socket
        DomainEventPublisher domainEventPublisher = DomainEventPublisher.getInstance();
        DomainEventSubcriber domainEventSubcriber = new DomainEventSubcriber("CustomerCreated");

        Thread.sleep(2000);//

        DomainEvent event = new DomainEvent("CustomerCreated", "name, address");
        domainEventPublisher.publish(event);



        DomainEvent receivedEvent = domainEventSubcriber.receive();

        assertEquals("name, address", receivedEvent.getCsv());

    }

}