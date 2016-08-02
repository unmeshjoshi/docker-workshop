package com.geekday.accounting.gateway;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class HttpService {

    final static Logger logger = LoggerFactory.getLogger(HttpService.class);

    static public InputStream get(final String resourceUri)
            throws IllegalStateException, Exception {


        HttpResponse response = getHttpResponse(resourceUri);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            throw new RuntimeException("Unable to fetch resource (status "
                    + statusCode + "): " + resourceUri
                    + " [correlationId: " +  "]");
        }

        logger.debug("Got response from '"
                + resourceUri + "': " + statusCode
                + " [correlationId:" + "]");
        return response.getEntity().getContent();
    }

    public static HttpResponse getHttpResponse(String resourceUri) throws IOException {
        return Request.Get(resourceUri)
                .connectTimeout(5000)
                .execute()
                .returnResponse();
    }

}
