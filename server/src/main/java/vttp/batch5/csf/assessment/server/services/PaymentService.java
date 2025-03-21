package vttp.batch5.csf.assessment.server.services;

import java.io.StringReader;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.batch5.csf.assessment.server.exceptions.PaymentFailureException;
import vttp.batch5.csf.assessment.server.models.PaymentDetails;

@Service
public class PaymentService {

    public static final String PAYMENT_GATEWAY = "https://payment-service-production-a75a.up.railway.app/";
    public static final String PAYEE = "Maziyyah Najihah Mohd Jaafar";

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    RestTemplate restTemplate = new RestTemplate();

    public PaymentDetails makePayment(String orderId, double checkoutTotal, String username) {
        

        // build the request
        // payload

        JsonObject jsonObject = Json.createObjectBuilder()
                                    .add("order_id", orderId)
                                    .add("payer", username)
                                    .add("payee", PAYEE)
                                    .add("payment", checkoutTotal)
                                    .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
        headers.add("X-Authenticate", username);

        logger.info("payment service url {}", buildPaymentUrl());

        RequestEntity<String> req = RequestEntity
                                            .post(URI.create(buildPaymentUrl()))
                                            .headers(headers)
                                            .body(jsonObject.toString());
        try {
            ResponseEntity<String> response = restTemplate.exchange(req, String.class);
            logger.info("Response: Status={}, Body={}", response.getStatusCode(), response.getBody());
            String payload = response.getBody();

            // parse the response
            JsonReader reader = Json.createReader(new StringReader(payload));
            JsonObject root = reader.readObject();
            String payment_id = root.getString("payment_id");
            String order_id = root.getString("order_id");
            long timestamp = root.getJsonNumber("timestamp").longValue();

            PaymentDetails paymentDetails = new PaymentDetails(payment_id, order_id, timestamp);

            return paymentDetails;


        } catch (Exception e) {
            logger.error("Failed to send payment request. Error: {}", e.getMessage(), e);
            throw new PaymentFailureException("Failed to send payment request");


        }                     


    }

    public String buildPaymentUrl() {
        return UriComponentsBuilder.fromUriString(PAYMENT_GATEWAY)
                                    .pathSegment("api")
                                    .pathSegment("payment")
                                    .toUriString();
    }
    
}
