package com.wavemaker.commons.functions;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 9/8/19
 */
public class LoggablePredicate<T> implements Predicate<T> {

    private final Predicate<T> delegate;

    private final Consumer<T> accept;
    private final Consumer<T> reject;

    public LoggablePredicate(
            final Predicate<T> delegate, final Consumer<T> accept, final Consumer<T> reject) {
        this.delegate = delegate;
        this.accept = accept;
        this.reject = reject;
    }

    public LoggablePredicate(final Predicate<T> delegate, final Consumer<T> reject) {
        this(delegate, t -> {
        }, reject);
    }

    @Override
    public boolean test(final T value) {
        if (delegate.test(value)) {
            accept.accept(value);
            return true;
        } else {
            reject.accept(value);
            return false;
        }
    }
}
