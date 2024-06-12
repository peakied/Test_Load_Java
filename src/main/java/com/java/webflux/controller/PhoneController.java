package com.java.webflux.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.webflux.response.PhoneResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class PhoneController {
    private WebClient.Builder webClientBuilder;

    @GetMapping("/phone/{id}")
    public Mono<ResponseEntity<PhoneResponse>> getDefaultPhone(@PathVariable String id) {
        String url = String.format("http://localhost:8081/phone?number=%s", id);

        return webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .map(responseBody -> {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        String sanitizedResponse = responseBody.replaceAll("\r", "");
                        PhoneResponse response = mapper.readValue(sanitizedResponse, PhoneResponse.class);
                        return ResponseEntity.ok(response);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return ResponseEntity.status(500).body(null);
                    }
                });
    }
}
