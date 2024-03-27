package com.aegisql.ordinal.reflection;

import java.util.List;
import java.util.Map;
import java.util.Set;

public final class TestOrdinals<T> extends GenericOrdinalFields<T> {

    public final static TestOrdinals<String> strField = new TestOrdinals<>();
    public final static TestOrdinals<Integer> intField = new TestOrdinals<>();

    public final static TestOrdinals<TestOrdinals<?>> typedOrdinalsField = new TestOrdinals<>();

    @Containerize("one")
    @Containerize("two")
    public final static TestOrdinals<List<Map<String, Set<String>>>> deepMapField = new TestOrdinals<>();

}
