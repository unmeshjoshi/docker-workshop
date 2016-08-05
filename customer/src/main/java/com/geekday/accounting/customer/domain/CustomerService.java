package com.geekday.accounting.customer.domain;

public class CustomerService {
    public void newCustomer(String name, String address) {
        Customer customer = new Customer(name, address, "");

        saveCustomer(customer);
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
