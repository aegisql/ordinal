package com.aegisql.ordinal;

import java.util.*;
import java.util.stream.Stream;

public interface Ordinal<T extends Ordinal<T>>  extends Comparable<Ordinal<T>>, Iterable<T> {
    int ordinal();
    default int maxOrdinal() {
        return values().length-1;
    }

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
            throw new IndexOutOfBoundsException("You've reached max ordinal: "+maxOrdinal());
        }
    }

    default T prev() {
        if(ordinal() > 0) {
            return values()[ordinal()-1];
        } else {
            throw new IndexOutOfBoundsException("You've reached min ordinal");
        }
    }

    default T valueAt(int pos) {
        return values()[pos];
    }

    @Override
    default Iterator<T> iterator() {

        T thisOrdinal = (T)this;

        return new Iterator<T>() {
            T ordinal = null;
            @Override
            public boolean hasNext() {
                return ordinal == null ? thisOrdinal.ordinal() < thisOrdinal.maxOrdinal() : ordinal.ordinal() < ordinal.maxOrdinal();
            }

            @Override
            public T next() {
                if(ordinal == null) {
                    ordinal = thisOrdinal;
                } else {
                    ordinal = ordinal.next();
                }
                return ordinal;
            }
        };
    }

}
