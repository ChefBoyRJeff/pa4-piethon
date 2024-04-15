package edu.psu.ist.analyzer.utils;

/**
 * A result either holds value ({@link Ok}) or an error ({@link Err}).
 *
 * @param <T> the type of the value.
 * @param <E> the type of the error.
 */
public sealed interface Result<T, E> {

    // factory methods:
    static <T, E> Result<T, E> ok(T t) {
        return new Ok<>(t);
    }

    static <T, E> Result<T, E> err(E e) {
        return new Err<>(e);
    }

    // instance methods:

    /**
     * Returns the error value stored for {@code this} result instance.
     *
     * @throws IllegalArgumentException if {@code this} isn't an instance of
     *                                  {@link Err}.
     */
    default E getError() {
        if (this instanceof Err<T, E> v) {
            return v.e;
        }
        throw new IllegalArgumentException("precondition violation");
    }

    /**
     * Returns the success value stored for {@code this} result instance.
     *
     * @throws IllegalArgumentException if this isn't an instance of {@link Ok}.
     */
    default T get() {
        if (this instanceof Ok<T, E> v) {
            return v.t;
        }
        throw new IllegalArgumentException("precondition violation");
       /* jdk22 approach:
        return switch (this) {
            case Ok(var v) -> v;
            case Err(_)    -> throw new IllegalArgumentException
                                                ("precondition violation");
        };*/
    }

    default boolean isOk() {
        return this instanceof Result.Ok<T, E>;
    }

    default boolean isError() {
        return this instanceof Result.Err<T, E>;
    }

    // actual implementations:

    record Ok<T, E>(T t) implements Result<T, E> {
    }

    record Err<T, E>(E e) implements Result<T, E> {
    }
}
