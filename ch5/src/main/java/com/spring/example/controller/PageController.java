package com.spring.example.controller;

import com.spring.example.vo.Song;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class PageController {

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/play-list-view")
    public Mono<String> getPlaylist(final Model model) {

        final Flux<Song> playlistStream = new Flux<Song>() {
            @Override
            public void subscribe(CoreSubscriber<? super Song> actual) {

            }
        };

        return playlistStream
                    .collectList()
                    .doOnNext(list -> model.addAttribute("playList", list))
                    .then( Mono.just("freemarker/play-list-view"));
    }

}
