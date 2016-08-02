package com.geekday.accounting.customer.domain;

import com.geekday.common.DomainEvent;
import com.geekday.common.DomainEventPublisher;

public class CustomerService {
    public void newCustomer(String name, String address) {
        Customer customer = new Customer(name, address, "");

        saveCustomer(customer);
        publishEvent(customer);
    }

    private void publishEvent(Customer customer) {
        DomainEvent event = new DomainEvent("CustomerCreated", customer.getName() + "," + customer.getAddress());
        DomainEventPublisher.getInstance().publish(event);
    }

    private void saveCustomer(Customer customer) {
        CustomerRepository repository = new CustomerRepository();
        repository.save(customer);
    }

    public void linkAccountToCustomer(String customerName, String accountId) {
        CustomerRepository repository = new CustomerRepository();
        Customer customer = repository.get(customerName);
        customer.setAccountId(accountId);
        repository.update(customer);

    }
}
