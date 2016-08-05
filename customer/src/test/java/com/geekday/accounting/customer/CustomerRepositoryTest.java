package com.geekday.accounting.customer;

import com.geekday.accounting.customer.domain.Customer;
import com.geekday.accounting.customer.domain.CustomerRepository;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerRepositoryTest {

    @BeforeClass
    public static void init() {
        CustomerRepository.initialize();
    }

    @Test
    public void shouldSaveCustomer() throws Exception {
        CustomerRepository repository = new CustomerRepository();
        repository.save(new Customer("name", "address", ""));
        Customer c = repository.get("name");
        assertEquals("name", c.getName());
        assertEquals("address", c.getAddress());
    }
}