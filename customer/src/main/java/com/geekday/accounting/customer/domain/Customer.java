package com.geekday.accounting.customer.domain;

public class Customer {
    private String name;
    private String address;
    private String accountId;

    public Customer(String name, String address, String accountId) {
        this.name = name;
        this.address = address;
        this.accountId = accountId;
    }

    public Customer() {
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
