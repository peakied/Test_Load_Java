package com.java.webflux.controller.HTTP2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.webflux.response.PhoneResponse;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@RestController
public class CustomWebClient2Controller {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public CustomWebClient2Controller(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;

        // Customize the connection provider with desired configurations
        ConnectionProvider connectionProvider = ConnectionProvider.builder("custom")
                .maxConnections(2000) // Maximum number of connections
                .pendingAcquireMaxCount(5000) // Maximum number of pending acquire requests
                .pendingAcquireTimeout(Duration.ofMillis(5000)) // Timeout for pending acquires
                .build();

        HttpClient httpClient = HttpClient.create(connectionProvider)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000) // Connection timeout
                .responseTimeout(Duration.ofMillis(5000)) // Response timeout
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

        this.webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("http://localhost:8082")
                .build();
    }

    @GetMapping("/phoneWeb/2/{id}")
    public Mono<ResponseEntity<PhoneResponse>> getPhoneWeb(@PathVariable String id) {
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
