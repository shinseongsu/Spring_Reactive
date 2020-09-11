package com.spring.example.Handler;

import com.spring.example.repository.OrderRepository;
import com.spring.example.vo.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class OrderHandler {

    private final OrderRepository orderRepository;

    public OrderHandler(OrderRepository repository) {
        orderRepository = repository;
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return request
                .bodyToMono(Order.class)
                .flatMap(orderRepository::save)
                .flatMap(o ->
                        ServerResponse.created(URI.create("/orders/" + o.getId()))
                                .build()
                );
    }

    public Mono<ServerResponse> get(ServerRequest request) {
        return orderRepository
                .findById(request.pathVariable("id"))
                .flatMap(order ->
                        ServerResponse
                                .ok()
                                .syncBody(order)
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> list(ServerRequest request) {
        return null;
    }

}
