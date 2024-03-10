package com.aegisql.ordinal.reflection;

import java.util.List;
import java.util.Map;

public class TypedOrdinals<T> extends OrdinalFields<TypedOrdinals<T>> {

    public final static TypedOrdinals<String> strField = new TypedOrdinals<>();
    public final static TypedOrdinals<Integer> intField = new TypedOrdinals<>();

    public final static TypedOrdinals<TypedOrdinals<?>> typedOrdinalsField = new TypedOrdinals<>();

    @Containerize("one")
    @Containerize("two")
    public final static TypedOrdinals<Map<String, List<String>>> deepMapField = new TypedOrdinals<>();

}
