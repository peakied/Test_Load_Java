package com.java.webflux.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.webflux.response.PhoneResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import protos.Lookup;
import protos.LookupServiceGrpc;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PhoneHandler {

    private final ObjectMapper objectMapper;
    private final LookupServiceGrpc.LookupServiceStub asyncStub;
    private final AtomicInteger counter = new AtomicInteger(0);

    public PhoneHandler(ObjectMapper objectMapper) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("mock-lookup", 8083)
                .usePlaintext()
                .build();
        this.objectMapper = objectMapper;
        asyncStub = LookupServiceGrpc.newStub(channel);
    }

    public Mono<ServerResponse> getUsersFromGolang(ServerRequest request) {
        return callGolangService(request, null);
    }

    public Mono<ServerResponse> getUsersFromGolangGrpc(ServerRequest request) {
        Lookup.LookupReq grpcRequest = Lookup.LookupReq.newBuilder().setPhoneNumber(request.pathVariable("id")).build();

        return Mono.create(sink -> asyncStub.lookup(grpcRequest, new StreamObserver<>() {
            @Override
            public void onNext(Lookup.LookupRes value) {
                sink.success(value);
            }

            @Override
            public void onError(Throwable t) {
                sink.error(t);
            }

            @Override
            public void onCompleted() {
            }
        })).map(Object::toString)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

    private Mono<ServerResponse> callGolangService(ServerRequest request, String path) {
        WebClient usedClient = (path == null)?
                WebClient.builder().baseUrl("http://mock-lookup:"+request.pathVariable("path")).build() :
                WebClient.builder().baseUrl("http://mock-lookup:"+path).build();

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

    public Mono<ServerResponse> getUsersFromGolangLoadBalancer(ServerRequest request) {
//        http1
//        return switch (getNextServerIndex()) {
//            case 0 -> callGolangService(request, "8081");
//            case 1 -> callGolangService(request, "8084");
//            case 2 -> callGolangService(request, "8085");
//            case 3 -> callGolangService(request, "8086");
//            case 4 -> callGolangService(request, "8087");
//            default -> Mono.empty();
//        };

        return switch (getNextServerIndex()) {
            case 0 -> callGolangService(request, "8082");
            case 1 -> callGolangService(request, "8088");
            case 2 -> callGolangService(request, "8089");
            case 3 -> callGolangService(request, "8090");
            case 4 -> callGolangService(request, "8091");
            default -> Mono.empty();
        };
    }

    private int getNextServerIndex() {
        return counter.getAndUpdate(i -> (i + 1) % 5);
    }
}
