package com.java.webflux.controller.HTTP2;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.webflux.response.PhoneResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
public class Phone2Controller {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public Phone2Controller(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.baseUrl("http://mock-lookup:8082").build();
        this.objectMapper = objectMapper;
    }


    @GetMapping("/phone/2/{id}")
    public Mono<ResponseEntity<PhoneResponse>> getDefaultPhone(@PathVariable String id) {
        return webClient.get()
                .uri("/phone?number={id}", id)
                .retrieve()
                .bodyToMono(String.class)
                .map(body -> body.replace("\r", ""))
                .flatMap(sanitizedBody -> {
                    try {
                        return Mono.just(ResponseEntity.ok(objectMapper.readValue(sanitizedBody, PhoneResponse.class)));
                    } catch (JsonProcessingException e) {
                        return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error parsing phone response", e));
                    }
                })
                .onErrorResume(WebClientResponseException.class, e -> Mono.just(ResponseEntity.status(e.getStatusCode()).body(null)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
