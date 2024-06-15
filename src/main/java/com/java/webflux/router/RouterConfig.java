package com.java.webflux.router;

import com.java.webflux.handler.PhoneHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {
    @Autowired
    private PhoneHandler phoneHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .GET("/{path}/router/phone/{id}",phoneHandler ::getUsersFromGolang)
                .GET("/router/phone/load/{id}",phoneHandler ::getUsersFromGolangLoadBalancer)
                .GET("/router/phone/gprc/{id}",phoneHandler ::getUsersFromGolangGrpc)
                .build();
    }
}
