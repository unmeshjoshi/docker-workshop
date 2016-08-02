package com.geekday.accounting.customer;

import com.geekday.accounting.customer.domain.Customer;
import com.geekday.accounting.customer.domain.CustomerRepository;
import com.geekday.common.DomainEvent;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

    @Test
    public void shouldStoreCustomerEvents() {
        CustomerRepository repository = new CustomerRepository();
        String eventId = repository.save(new DomainEvent("CustomerCreated", "name,address"));
        assertNotNull(eventId);
    }

    @Test
    public void shouldGetLatestViewOfCustomerFromEvents() {
        CustomerRepository repository = new CustomerRepository();
        String eventId1 = repository.save(new DomainEvent("CustomerCreated", "name,address"));
        String eventId2 = repository.save(new DomainEvent("CustomerCreated", "name1,address1"));
        String eventId3 = repository.save(new DomainEvent("CustomerUpdated", "name,address3"));
        String eventId4 = repository.save(new DomainEvent("CustomerCreated", "name2,address2"));

        Customer customer = repository.getLatest("name");
        assertEquals("name", customer.getName());
        assertEquals("address3", customer.getAddress());

    }
}