package com.java.webflux.router;

import com.java.webflux.handler.CustomerHandler;
import com.java.webflux.handler.CustomerStreamHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Autowired
    private CustomerHandler handler;

    @Autowired
    private CustomerStreamHandler handlerStream;

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .GET("/router/customers", handler::loadCustomers)
                .GET("/router/customers/stream", handlerStream::getCustomer)
                .GET("router/customers/{input}", handler::findCustomer)
                .POST("/router/customers/save", handler::saveCustomer)
                .GET("/router/user", handler::getUsersFromGolang)
                .build();
    }
}
