package com.aegisql.ordinal.reflection;

import java.util.List;
import java.util.Map;
import java.util.Set;

public final class TestOrdinals2<T> extends GenericOrdinalFields<T> {

    public final static TestOrdinals2<String> strField = new TestOrdinals2<>();
    public final static TestOrdinals2<Integer> intField = new TestOrdinals2<>();

    public final static TestOrdinals2<TestOrdinals2<?>> typedOrdinalsField = new TestOrdinals2<>();

    @Containerize("one")
    @Containerize("two")
    public final static TestOrdinals2<List<Map<String, Set<String>>>> deepMapField = new TestOrdinals2<>();

}
