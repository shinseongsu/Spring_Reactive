package com.spring.rx.ReactiveX;

public interface RxObserver<T> {
    void onNext(T next);
    void onComplete();
    void onError(Exception e);
}