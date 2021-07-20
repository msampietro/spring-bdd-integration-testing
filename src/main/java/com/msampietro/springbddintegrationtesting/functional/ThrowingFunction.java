package com.msampietro.springbddintegrationtesting.functional;

import java.util.function.Function;

@FunctionalInterface
public interface ThrowingFunction<T, R, E extends Exception> {
    static <T, R> Function<T, R> throwingFunction(
            ThrowingFunction<T, R, Exception> throwingFunction) {

        return i -> {
            try {
                return throwingFunction.apply(i);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    R apply(T t) throws E;
}
