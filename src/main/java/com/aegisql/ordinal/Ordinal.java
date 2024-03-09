package com.aegisql.ordinal;

import java.util.*;
import java.util.stream.Stream;

public interface Ordinal<T extends Ordinal<T>>  extends Comparable<Ordinal<T>> {
    int ordinal();
    int maxOrdinal();

    T[] values();

    default int compareTo(Ordinal<T> other) {
        Objects.requireNonNull(other, "Cannot compare to NULL ordinal");
        if (this.getClass() != other.getClass()) {
            throw new ClassCastException("Cannot compare order of instances of different types. Expected:" + getClass().getCanonicalName() + " Actual:" + other.getClass().getCanonicalName());
        }
        return this.ordinal() - other.ordinal();
    }

    default Collection<T> valuesCollection() {
        return Collections.unmodifiableList(List.of(values()));
    }

    default Stream<T> valuesStream() {
        return List.of(values()).stream();
    }

    default T next() {
        if(ordinal() < maxOrdinal()) {
            return values()[ordinal()+1];
        } else {
            return null;
        }
    }

    default T prev() {
        if(ordinal() > 0) {
            return values()[ordinal()-1];
        } else {
            return null;
        }
    }

    default T valueAt(int pos) {
        return values()[pos];
    }

}
