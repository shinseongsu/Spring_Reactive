package study;

import reactor.core.publisher.Flux;

import java.time.Instant;
import java.util.Comparator;

public class FluxStudy2 {

    public static void main(String[] args) {

        Flux.range(2018, 5)
                .timestamp()
                .index()
                .subscribe(e -> System.out.println("index: " + e.getT1()
                        + ", ts: " + Instant.ofEpochMilli(e.getT2().getT1())
                        + ", value: " + e.getT2().getT2() ));

        /*
            index: 0, ts: 2020-09-10T05:55:30.673Z, value: 2018
            index: 1, ts: 2020-09-10T05:55:30.688Z, value: 2019
            index: 2, ts: 2020-09-10T05:55:30.688Z, value: 2020
            index: 3, ts: 2020-09-10T05:55:30.688Z, value: 2021
            index: 4, ts: 2020-09-10T05:55:30.688Z, value: 2022
         */

        Flux.just(1, 6, 2, 8, 3, 1, 5, 1)
                    .collectSortedList(Comparator.reverseOrder())
                    .subscribe(System.out::println);
        // [8, 6, 5, 3, 2, 1, 1, 1]



    }

}
