package com.java.webflux;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoFluxTest {

    @Test
    public void testMono() {
        Mono<?> mono = Mono.just("Hello")
                .then(Mono.error(new RuntimeException("Exception occurred")))
                .log();
        mono.subscribe(System.out::println, (e) -> System.out.println("Error: " + e));
    }

    @Test
    public void testFlux() {
        Flux<String> flux = Flux.just("Spring", "Spring Boot", "Hibernate", "microservice")
                .concatWithValues("AWS")
                .concatWith(Flux.error(new RuntimeException("Exception occurred in Flux")))
                .concatWithValues("cloud")
                .log();
        flux.subscribe(System.out::println);
    }
}
