package com.spring.example.service;

import com.spring.example.vo.Profile;
import reactor.core.publisher.Mono;

public interface ProfileService {

    Mono<Profile> getByUser(String name);

}
