package study;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ReactorStudy {

    public static void main(String[] args) {

        ThreadLocal<Map<Object, Object>> threadLocal =
                new ThreadLocal<>();

        threadLocal.set(new HashMap<>());

        Flux
                .range(0, 10)
                .doOnNext(k ->
                        threadLocal
                                    .get()
                                    .put(k, new Random(k).nextGaussian())
                )
                .publishOn(Schedulers.parallel())
                .map(k -> threadLocal.get().get(k))
                .blockLast();


        // 수정 하기
        Flux.range(0, 10)
                .flatMap(k ->
                        Mono.subscriberContext()
                            .doOnNext(context -> {
                                Map<Object, Object> map = context.get("randoms");
                                map.put(k, new Random(k).nextGaussian());
                            })
                            .thenReturn(k)
                )
                .publishOn(Schedulers.parallel())
                .flatMap(k ->
                        Mono.subscriberContext()
                            .map(context -> {
                                Map<Object, Object> map = context.get("randoms");
                                return map.get(k);
                            })
                )
                .subscriberContext(context ->
                        context.put("randoms", new HashMap())
                )
                .blockLast();

    }

}
