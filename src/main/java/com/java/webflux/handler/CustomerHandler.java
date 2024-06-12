package com.java.webflux.handler;

import com.java.webflux.dao.CustomerDao;
import com.java.webflux.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class CustomerHandler {

    @Autowired
    private CustomerDao dao;

    private final WebClient webClient;

    public CustomerHandler(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8082").build();
    }
    public Mono<ServerResponse> loadCustomers(ServerRequest request) {
        Flux<Customer> customerFlux = dao.getCustomersList();
        return ServerResponse.ok().body(customerFlux, Customer.class);
    }

    public Mono<ServerResponse> findCustomer(ServerRequest request) {
        int customerId = Integer.valueOf(request.pathVariable("input"));
        Mono<Customer> customerMono = dao.getCustomersList().filter(c -> c.getId() == customerId).next();
        return ServerResponse.ok().body(customerMono, Customer.class);
    }

    public Mono<ServerResponse> saveCustomer(ServerRequest request) {
        Mono<Customer> customerMono = request.bodyToMono(Customer.class);
        Mono<String> saveResponse = customerMono.map(dto -> dto.getId() + ":" + dto.getName());
        return ServerResponse.ok().body(saveResponse, Customer.class);
    }

    public Mono<ServerResponse> getUsersFromGolang(ServerRequest request) {
        return webClient.get()
                .uri("/phone?number=0711555001")
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(res -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(res))
                .onErrorResume(e -> {
                    e.printStackTrace();
                    return ServerResponse.status(500).bodyValue("Error retrieving user data");
                });
    }
}
