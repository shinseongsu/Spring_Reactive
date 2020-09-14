package com.spring.example.service;

import com.spring.example.vo.StockItem;
import reactor.core.publisher.Flux;

public interface StocksService {

    Flux<StockItem> stream();
}
