package com.spring.example.service;

import com.spring.example.vo.PasswordDTO;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.EXPECTATION_FAILED;

public class DefaultPasswordVerificationService implements PasswordVerificationService{

    private final WebClient webClient;

    public DefaultPasswordVerificationService(WebClient.Builder webClientBuilder) {
        webClient = webClientBuilder
                .baseUrl("http://localhost:8080")
                .build();
    }

    @Override
    public Mono<Void> check(String raw, String encoded) {
        return webClient
                .post()
                .uri("/password")
                .body(BodyInserters.fromPublisher(
                        Mono.just(new PasswordDTO(raw, encoded)),
                        PasswordDTO.class
                ))
                .exchange()
                .flatMap(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return Mono.empty();
                    }
                    else if (response.statusCode() == EXPECTATION_FAILED) {
                        return Mono.error(new BadCredentialsException("Invalid credentials"));
                    }
                    return Mono.error(new IllegalStateException());
                });
    }
}
