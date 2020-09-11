package com.spring.example.repository;

import com.spring.example.vo.Order;
import reactor.core.publisher.Mono;

public interface OrderRepository {

    Mono<Order> findById(String id);

    Mono<Order> save(Order order);

    Mono<Void> deleteById(String id);

}
