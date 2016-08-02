package com.geekday.accounting.web;


import com.geekday.accounting.gateway.Customer;
import com.geekday.accounting.gateway.CustomerService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class CustomerResource {

    @GET
    @Path("summary")
    @Produces(MediaType.APPLICATION_JSON)
    public Summary summary() {

        try {
            String customerName = "John";
            Customer customer = new CustomerService().getCustomer(customerName);

            return new Summary(customer);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}