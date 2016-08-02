package com.geekday.accounting.web;

import com.geekday.accounting.gateway.Customer;

public class Summary {

    Customer customer;

    public Summary() {
    }

    public Summary(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
