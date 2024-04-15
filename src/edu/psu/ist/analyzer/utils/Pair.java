package edu.psu.ist.analyzer.utils;

/** An ordered pair ADT generic in its first and second component types. */
public record Pair<A, B>(A first, B second) {

    @Override public String toString() {
        return String.format("(%s, %s)", first, second);
    }
}
