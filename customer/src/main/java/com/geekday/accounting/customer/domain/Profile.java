package com.geekday.accounting.customer.domain;

public class Profile {
    String name = "test";
    String email = "test@test.com";

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
