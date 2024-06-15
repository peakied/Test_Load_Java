package com.java.webflux.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.webflux.response.PhoneResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PhoneHandler {

    @Autowired
    private List<WebClient> clients;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebClient webClient;

    public Mono<ServerResponse> getUsersFromGolang(ServerRequest request) {
        return callGolangService(request);
    }

    public Mono<ServerResponse> getUsersFromGolangStream(ServerRequest request) {
        return callGolangService(request);
    }

    public Mono<ServerResponse> getUsersFromGolangLoadBalancer(ServerRequest request) {
        return callGolangService(request, true);
    }

    private Mono<ServerResponse> callGolangService(ServerRequest request) {
        return callGolangService(request, false);
    }

    private Mono<ServerResponse> callGolangService(ServerRequest request, Boolean client) {
        WebClient usedClient = (!client) ? webClient : selectWebClient();

        return usedClient.get()
                .uri(uriBuilder -> uriBuilder.path("/phone")
                        .queryParam("number", request.pathVariable("id"))
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .map(string -> string.replace("\r", ""))
                .flatMap(this::parseAndRespond)
                .onErrorResume(WebClientResponseException.class, e -> ServerResponse.status(e.getStatusCode())
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(e.getResponseBodyAsString()))
                .subscribeOn(Schedulers.boundedElastic());

    }

    private Mono<ServerResponse> parseAndRespond(String responseBody) {
        try {
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(objectMapper.readValue(responseBody, PhoneResponse.class));
        } catch (JsonProcessingException e) {
            return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error parsing phone response", e));
        }
    }

    private WebClient selectWebClient() {
        int index = ThreadLocalRandom.current().nextInt(clients.size());
        return clients.get(index);
    }

}
