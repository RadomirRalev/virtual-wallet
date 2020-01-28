package com.example.demo.helpers;

import com.example.demo.models.transaction.Deposit;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class ApiCommunication {
    private static final String URI = "http://localhost:8081/payment";
    private static final String HEADER_NAME = "x-api-key";
    private static final String HEADER_VALUE = "apiKey";

    public static void communicateWithApi(Deposit deposit) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HEADER_NAME, HEADER_VALUE);
        HttpEntity<Deposit> entityRequest = new HttpEntity<>(deposit, headers);
        restTemplate.exchange(URI, HttpMethod.POST, entityRequest, Integer.class);
    }
}
