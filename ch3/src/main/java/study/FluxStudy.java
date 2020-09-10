package study;

import org.apache.tomcat.jni.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Optional;

public class FluxStudy {

    public static void main(String[] args) {

        Flux<String> stream1 = Flux.just("Hello", "World");
        Flux<Integer> stream2 = Flux.fromArray(new Integer[]{1, 2, 3});
        Flux<Integer> stream3 = Flux.fromIterable(Arrays.asList(9, 8, 7));

        Flux<Integer> stream4 = Flux.range(2010, 9);
        // 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018

        Mono<String> stream5 = Mono.just("One");
        Mono<String> stream6 = Mono.justOrEmpty(null);
        Mono<String> stream7 = Mono.justOrEmpty(Optional.empty());

        // HTTP 요청이나 DB쿼리와 같은 비동기 작업
        // Mono<String> stream8 = Mono.fromCallable(() -> httpRequest());

        Flux<String> empty = Flux.empty();
        Flux<String> never = Flux.never();
        Mono<String> error = Mono.error(new RuntimeException("Unknown id"));

        Flux.just("A", "B", "C")
                .subscribe(
                        data -> System.out.println("onNext: " + data),
                        err -> {},
                        () -> System.out.println("onComplete")
                );

        /*
        *
        * onNext: A
        * onNext: B
        * onNext: C
        * onComplete
        *
         */


        Flux.range(1, 100)
                .subscribe(
                        data -> System.out.println("on Next: " + data),
                        err -> {  },
                        () -> System.out.println("onComplete"),
                        subscription -> {
                            subscription.request(4);
                            subscription.cancel();
                        }
                );

        /*
        *
        * on Next: 1
        * on Next: 2
        * on Next: 3
        * on Next: 4
        *
         */





    }


}
