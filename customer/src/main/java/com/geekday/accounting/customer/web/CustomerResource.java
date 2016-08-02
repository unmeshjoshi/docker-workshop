package com.geekday.accounting.customer.web;


import com.geekday.accounting.customer.domain.Customer;
import com.geekday.accounting.customer.domain.CustomerRepository;
import com.geekday.accounting.customer.domain.CustomerService;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/")
public class CustomerResource {
    @POST
    @Path("customer")
    public Response createCustomer(@FormParam("name") String name, @FormParam("address") String address) {
        new CustomerService().newCustomer(name, address);
        return Response.created(URI.create("/profile/" + name)).build();
    }

    @GET
    @Path("profile/{customerName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Customer profile(@PathParam("customerName")String customerName) {
        return new CustomerRepository().get(customerName);
    }
}