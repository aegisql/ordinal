package com.aegisql.ordinal.reflection;

public interface TypeInfo<Т> {
    Class<Т> getType();

    GenericTree getGenericTree();

    default void test(Object val) {
        if(val != null) {
            var valClass = val.getClass();
            if(!getType().isAssignableFrom(valClass)) {
                throw new RuntimeException("Class "+valClass+" is not assignable from "+getType());
            }
        }
    }
}
