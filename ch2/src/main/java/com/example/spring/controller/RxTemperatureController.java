package com.example.spring.controller;

import com.example.spring.Emitter.RxSeeEmitter;
import com.example.spring.component.RxTemperatureSensor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RxTemperatureController {

    private final RxTemperatureSensor temperatureSensor;

    public RxTemperatureController(RxTemperatureSensor temperatureSensor) {
        this.temperatureSensor = temperatureSensor;
    }

    @RequestMapping(
            value = "rxtemperature-stream",
            method = RequestMethod.GET)
    public SseEmitter events(HttpServletRequest request) {
        RxSeeEmitter emitter = new RxSeeEmitter();

        temperatureSensor.temperatureStream()
                    .subscribe(emitter.getSubscriber());

         return emitter;
    }

}
