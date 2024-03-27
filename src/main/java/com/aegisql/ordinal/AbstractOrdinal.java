package com.aegisql.ordinal;

import java.util.Arrays;

public abstract class AbstractOrdinal<T extends AbstractOrdinal<T>> implements Ordinal<T> {

    protected int ordinal;
    protected T[] values;

    protected AbstractOrdinal() {
    }

    @Override
    public int ordinal() {
        return ordinal;
    }

    @Override
    public T[] values() {
        return values.clone();
    }

    @Override
    public String toString() {
        return Arrays.toString(values);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        T ordinal = (T) o;
        return this.ordinal == ordinal.ordinal();
    }

    @Override
    public int hashCode() {
        return ordinal+1;
    }
}

