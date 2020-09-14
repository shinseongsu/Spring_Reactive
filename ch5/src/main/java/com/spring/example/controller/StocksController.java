package com.spring.example.controller;

import com.spring.example.service.StocksService;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
public class StocksController {

    private Map<String, StocksService> stringStocksServiceMap;

    @GetMapping("/sse/stocks")
    public Flux<ServerSentEvent<?>> streamStocks() {

        return Flux
                .fromIterable(stringStocksServiceMap.values())
                .flatMap(StocksService::stream)
                .<ServerSentEvent<?>>map(item ->
                        ServerSentEvent
                            .builder(item)
                            .event("StockItem")
                            .id(item.getId())
                            .build()
                )
                .startWith(
                        ServerSentEvent
                                .builder()
                                .event("Stocks")
                                .data(stringStocksServiceMap.keySet())
                                .build()
                );

    }


}
