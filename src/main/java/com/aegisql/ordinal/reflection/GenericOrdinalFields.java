package com.aegisql.ordinal.reflection;

import com.aegisql.ordinal.Ordinal;

public abstract class GenericOrdinalFields <T> extends OrdinalFields<GenericOrdinalFields<T>> implements TypeInfo<T> {
    @Override
    public Class<T> getType() {
        return this.getParameterTree().getGenericClass();
    }
}
