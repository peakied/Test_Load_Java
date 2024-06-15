package com.java.webflux.controller.gRPC;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import protos.Lookup;
import protos.LookupServiceGrpc;
import reactor.core.publisher.Mono;

@RestController
public class PhoneGrpcController {
    private final LookupServiceGrpc.LookupServiceStub asyncStub;

    public PhoneGrpcController() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8083)
                .usePlaintext()
                .build();
        asyncStub = LookupServiceGrpc.newStub(channel);
    }
    @GetMapping("/phone/grpc/{id}")
    public Mono<Lookup.LookupRes> getLookupData(@PathVariable("id") String id) {
        Lookup.LookupReq request = Lookup.LookupReq.newBuilder().setPhoneNumber(id).build();

        return Mono.create(sink -> {
            asyncStub.lookup(request, new StreamObserver<Lookup.LookupRes>() {
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
            });
        });
    }
}


