package com.aegisql.ordinal.reflection;

import com.aegisql.ordinal.AbstractOrdinal;
import com.aegisql.ordinal.Ordinal;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class OrdinalFields<T extends OrdinalFields<T>> extends AbstractOrdinal<T> implements Serializable {

    protected String name;
    protected GenericTree genericTree;

    protected OrdinalFields(String name) {
        this();
        this.name = name;
    }

    protected OrdinalFields() {
        List<StaticFieldInfo<T>> staticFieldInfos = new ArrayList<>();
        for (Field field : getClass().getFields()) {
                Class<?> fieldType = field.getType();
                if(fieldType != this.getClass()) {
                    continue;
                }
                staticFieldInfos.add(new StaticFieldInfo<T>((T) this,field));
        }
        T[] array  = (T[]) Array.newInstance(staticFieldInfos.getFirst().getValue().getClass(),staticFieldInfos.size());
        for(int i = 0; i < staticFieldInfos.size(); i++) {
            StaticFieldInfo<T> fieldInfo = staticFieldInfos.get(i);
            T value = fieldInfo.getValue();
            array[i] = fieldInfo.getValue();
            value.values = array;
            value.ordinal = i;
            if(value.genericTree == null) {
                value.genericTree = fieldInfo.getGenericTree();
            }
            if(value.name == null) {
                value.name = fieldInfo.getFieldName();
            }
        }

    }

    public GenericTree getGenericTree() {
        return genericTree;
    }

    public GenericTree getParameterTree() {
        return genericTree.getGenericTree(0);
    }

    public Class<T> getParameterType() {
        return getParameterTree().getGenericClass();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdinalFields<?> that = (OrdinalFields<?>) o;
        return ordinal == that.ordinal;
    }

    @Override
    public int hashCode() {
        return ordinal+1;//never return 0, which is reserved for null
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static <T extends OrdinalFields<? extends T>> T[] values(Class<? extends T> forClass) {
        for (Field field : forClass.getFields()) {
            try {
                Class<?> fieldType = field.getType();
                if(fieldType != forClass) {
                    continue;
                }
                Object val = field.get(null);
                if(val == null) {
                    continue;
                }
                return ((T)val).values();
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public static <T extends OrdinalFields<? extends T>> T valueOf(Class<? extends T> forClass, String name) {
        for (Field field : forClass.getFields()) {
            try {
                Class<?> fieldType = field.getType();
                if(fieldType != forClass) {
                    continue;
                }
                Object val = field.get(null);
                if(val == null) {
                    continue;
                }
                if(((T)val).name.equals(name)) {
                    return (T) val;
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public static <T extends OrdinalFields<? extends T>> T valueOf(Class<? extends T> forClass, int pos) {
        return values(forClass)[pos];
    }

}

