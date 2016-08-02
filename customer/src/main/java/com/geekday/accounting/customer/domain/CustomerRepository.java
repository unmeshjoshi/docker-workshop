package com.geekday.accounting.customer.domain;


import com.geekday.common.DomainEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomerRepository {

    public static void initialize() {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.execute("create table customer(name varchar(50), address varchar(50), accountId varchar(50))");

            statement.execute("create table eventstore(eventId varchar(50), eventType varchar(50), eventData  CLOB(1K))");
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:hsqldb:mem:customer", "sa", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Customer customer) {
        Connection connection = getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("insert into customer (name, address) values(?, ?)");
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getAddress());
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Customer get(String name) {
        Connection connection = getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("select * from customer where name = ?");
            ps.setString(1, name);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return new Customer(resultSet.getString("name"), resultSet.getString("address"), resultSet.getString("accountId"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void update(Customer customer) {
        Connection connection = getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("update customer set accountId = ? where name=?");
            ps.setString(1, customer.getAccountId());
            ps.setString(2, customer.getName());
            ps.executeUpdate();

            System.out.println("Updated customer " + customer.getName() + " with accountId " + customer.getAccountId());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public String save(DomainEvent event) {
        Connection connection = getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("insert into eventstore values(?,?,?)");
            String eventId = new Random().nextInt() + "";

            ps.setString(1, eventId);
            ps.setString(2, event.getType());
            ps.setString(3, event.getCsv());
            ps.executeUpdate();

            System.out.println("Inserting event " + event);

            return eventId;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public Customer getLatest(String name) {
        List<Customer> allCustomers = getAllCustomers();
        Customer result = null;
        for (Customer customer : allCustomers) {
            if(customer.getName().equals(name)) {
                 result = customer;
            }
        }

        if (result == null) {
            throw new RuntimeException("Customer not found");
        }

        return result;
    }

    private List<Customer> getAllCustomers() {
        List<Customer> customerList = new ArrayList();

        Connection connection = getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("select * from eventstore where eventType = 'CustomerCreated' or eventType = 'CustomerUpdated'");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String customerCsv = resultSet.getString("eventData");
                String[] customerAttributes = customerCsv.split(",");
                String accountId = "";
                if (customerAttributes.length == 3) {
                    accountId = customerAttributes[2];
                }

                customerList.add(new Customer(customerAttributes[0], customerAttributes[1], accountId));

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return customerList;
    }
}
