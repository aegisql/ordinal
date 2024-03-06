package com.aegisql.ordinal;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

public final class OrdinalHolder <T> implements Ordinal<OrdinalHolder<T>>{

    private final int ordinal;
    private final int maxOrdinal;
    private final T value;
    private final OrdinalHolder<T>[] values;

    public static <T> OrdinalHolder<T>[] wrap(T first, T... values) {
        return new OrdinalHolder(first,values).values;
    }

    private OrdinalHolder(T first, T... values) {
        Objects.requireNonNull(first,"Ordinal Holder requires not null elements");
        this.value = first;
        this.ordinal = 0;
        if(values != null) {
            this.values = new OrdinalHolder[values.length+1];
            this.values[0] = this;
            this.maxOrdinal = values.length;
            for (int i = 0,j=1; i < values.length; i++,j++) {
                this.values[j] = new OrdinalHolder<>(j,this.maxOrdinal,values[i],this.values);
            }
        } else {
            this.maxOrdinal = 0;
            this.values = new OrdinalHolder[]{this};
        }
    }

    private OrdinalHolder(int ordinal, int maxOrdinal, T value, OrdinalHolder<T>[] values) {
        this.ordinal = ordinal;
        this.maxOrdinal = maxOrdinal;
        this.value = value;
        this.values = values;
    }

    public T getValue() {
        return value;
    }

    @Override
    public int ordinal() {
        return ordinal;
    }

    @Override
    public int maxOrdinal() {
        return maxOrdinal;
    }

    @Override
    public OrdinalHolder<T>[] values() {
        return values;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdinalHolder<?> that = (OrdinalHolder<?>) o;
        var thisCollection = this.valuesStream().map(OrdinalHolder::getValue).collect(Collectors.toList());
        var thatCollection = that.valuesStream().map(OrdinalHolder::getValue).collect(Collectors.toList());
        return ordinal == that.ordinal && maxOrdinal == that.maxOrdinal && Objects.equals(value, that.value) && thisCollection.equals(thatCollection);
    }

    @Override
    public int hashCode() {
        return ordinal+1;
    }
}

