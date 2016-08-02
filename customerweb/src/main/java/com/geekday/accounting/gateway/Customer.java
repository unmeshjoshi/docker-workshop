package com.geekday.accounting.gateway;

public class Customer {
    private String name;
    private String address;
    private String accountId;

    public Customer() {
    }

    public Customer(String name, String address, String accountId) {
        this.name = name;
        this.address = address;
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}

