package com.aegisql.ordinal;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

public final class OrdinalHolder <T> extends AbstractOrdinal<OrdinalHolder<T>>{

    private final T value;

    public static <T> OrdinalHolder<T>[] wrap(T first, T... values) {
        OrdinalHolder<T> ordinalHolder = new OrdinalHolder<>(first, values);
        return ordinalHolder.values();
    }

    public OrdinalHolder(T first, T... values) {
        super();
        Objects.requireNonNull(first,"Ordinal Holder requires not null elements");
        this.value = first;
        this.ordinal = 0;
        if(values != null) {
            this.values = new OrdinalHolder[values.length+1];
            this.values[0] = this;
            for (int i = 0,j=1; i < values.length; i++,j++) {
                this.values[j] = new OrdinalHolder<>(j,values[i],this.values);
            }
        } else {
            this.values = new OrdinalHolder[]{this};
        }
    }

    private OrdinalHolder(int ordinal, T value, OrdinalHolder<T>[] values) {
        this.ordinal = ordinal;
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
        return ordinal == that.ordinal && maxOrdinal() == that.maxOrdinal() && Objects.equals(value, that.value) && thisCollection.equals(thatCollection);
    }

    @Override
    public int hashCode() {
        return ordinal+1;
    }
}

