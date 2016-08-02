package com.geekday.common;


public class DomainEvent {
    private String type;
    private String json;

    public DomainEvent(String type, String json) {
        this.type = type;
        this.json = json;
    }

    public String getType() {
        return type;
    }

    public String getCsv() {
        return json;
    }

    @Override
    public String toString() {
        return "DomainEvent{" +
                "type='" + type + '\'' +
                ", json='" + json + '\'' +
                '}';
    }
}
