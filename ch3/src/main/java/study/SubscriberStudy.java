package study;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

public class SubscriberStudy {

    public static void main(String[] args) {

        Subscriber<String> subscriber = new Subscriber<String>() {

            volatile Subscription subscription;

            public void onSubscribe(Subscription s) {
                subscription = s;
                System.out.println("initial request for 1 element");
                subscription.request(1);
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext: " + s);
                System.out.println("requesting 1 more element");

                subscription.request(1);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("onError: " + t.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }

        };

        Flux<String> stream = Flux.just("Hello", "world", "!");
        stream.subscribe(subscriber);

    }


    /*
        initial request for 1 element
        onNext: Hello
        requesting 1 more element
        onNext: world
        requesting 1 more element
        onNext: !
        requesting 1 more element
        onComplete
     */

}
