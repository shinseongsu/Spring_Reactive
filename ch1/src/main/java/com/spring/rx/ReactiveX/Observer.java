package com.spring.rx.ReactiveX;

public interface Observer<T> {
    void observe(T event);
}
