package com.geekday.accounting.customer.domain;

import com.geekday.common.DomainEvent;
import com.geekday.common.DomainEventSubcriber;

/**
 * Created by unmesh on 16/7/16.
 */
public class AccountSubscriber {

    public void start() {
        new Thread(()-> {
            while(true) {
                try {

                    DomainEvent customerEvent = new DomainEventSubcriber("AccountCreated").receive();
                    String accountCsv = customerEvent.getCsv();
                    String[] accountAttributes = accountCsv.split(",");
                    String accountId = accountAttributes[0];
                    String customerName = accountAttributes[1];

                    System.out.println("Got account event " + accountCsv);

                    new CustomerService().linkAccountToCustomer(customerName, accountId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
