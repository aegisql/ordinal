package com.aegisql.ordinal.reflection;

public class TypedOrdinals<T> extends OrdinalFields<TypedOrdinals<T>> {

    public final static TypedOrdinals<String> strField = new TypedOrdinals<>();
    public final static TypedOrdinals<Integer> intField = new TypedOrdinals<>();

    public final static TypedOrdinals<TypedOrdinals<?>> typedOrdinalsField = new TypedOrdinals<>();

}
