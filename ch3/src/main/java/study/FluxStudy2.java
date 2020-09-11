package study;

import reactor.core.publisher.Flux;

import java.time.Instant;
import java.util.Arrays;
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

        Flux.just(3, 5, 7, 9, 11, 15, 16, 17)
                .any(e -> e % 2 == 0)
                .subscribe(hasEvens -> System.out.println("Has evens: " + hasEvens));
        // Has evens: true

        Flux.range(1, 5)
                .reduce(0, (acc, elem) -> acc + elem)
                .subscribe(result -> System.out.println("Result: " + result));
        // Result: 15

        Flux.range(1, 5)
                    .scan(0, (acc, elem) -> acc + elem)
                .subscribe(result -> System.out.println("Result = " + result));
        /*
        *
        *    Result = 0
        *    Result = 1
        *    Result = 3
        *    Result = 6
        *    Result = 10
        *    Result = 15
        *
         */

        int bucketSize = 5;
        Flux.range(1, 500)
                .index()
                .scan(
                        new int[bucketSize],
                        (acc, elem) -> {
                            acc[(int)(elem.getT1() % bucketSize)] = elem.getT2();
                            return acc;
                        })
                .skip(bucketSize)
                .map(array -> Arrays.stream(array).sum() * 1.0 /bucketSize)
                .subscribe(av -> System.out.println("Running average: " + av));

        /*
        *    Running average: 3.0
        *    Running average: 4.0
        *    Running average: 5.0
        *    Running average: 6.0
        *    Running average: 7.0
        *    Running average: 8.0
        *    Running average: 9.0
        *    Running average: 10.0
        *    ...
        */


        Flux.just(1, 2, 3)
                .thenMany(Flux.just(4, 5))
                .subscribe(e -> System.out.println("onNext : " + e));
        // onNext : 4
        // onNext : 5


        Flux.concat(
                Flux.range(1, 3),
                Flux.range(4, 2),
                Flux.range(6, 5)
        ).subscribe(e -> System.out.println("onNext: " + e));

        // onNext: 1
        //onNext: 2
        //onNext: 3
        //onNext: 4
        //onNext: 5
        //onNext: 6
        //onNext: 7
        //onNext: 8
        //onNext: 9
        //onNext: 10

        Flux.range(1, 13)
                .buffer(4)
                .subscribe(e -> System.out.println("onNext: " + e));
        /*
          onNext: [1, 2, 3, 4]
          onNext: [5, 6, 7, 8]
          onNext: [9, 10, 11, 12]
          onNext: [13]
         */

        


    }

}
