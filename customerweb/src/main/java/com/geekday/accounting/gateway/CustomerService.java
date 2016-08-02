package com.geekday.accounting.gateway;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;

public class CustomerService {

    final static Logger logger = LoggerFactory.getLogger(CustomerService.class);

    public Customer getCustomer(String name) throws Exception {
        try {
            InputStream content = HttpService.get(
                    "http://customerservice:8082/profile/" + name);

            return parseJson(content);

        } catch (Exception e) {
            throw new RuntimeException("Exception while fetching customer information " + name, e);

        }
    }

    private Customer parseJson(InputStream json) {
        JsonElement jsonElement = new JsonParser().parse(new InputStreamReader(json));
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String accountId = null;
        if (jsonObject.get("accountId") != null) {
            accountId = jsonObject.get("accountId").getAsString();
        }
        return new Customer(jsonObject.get("name").getAsString(), jsonObject.get("address").getAsString(), accountId);
    }
}
