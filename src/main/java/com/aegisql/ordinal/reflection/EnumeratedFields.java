package com.aegisql.ordinal.reflection;

import com.aegisql.ordinal.Ordinal;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class EnumeratedFields<T extends EnumeratedFields<T>> implements Ordinal<T>, Serializable {

    protected int ordinal;
    protected int maxOrdinal;

    protected String name;

    protected GenericTree genericTree;

    protected EnumeratedFields(String name) {
        this();
        this.name = name;
    }

    protected EnumeratedFields() {
        List<T> values = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<GenericTree> genericTreeCollectors = new ArrayList<>();
        int expected = 0;
        int actual = 1;//count this
        for (Field field : getClass().getFields()) {
            try {
                Class<?> fieldType = field.getType();
                if(fieldType != this.getClass()) {
                    continue;
                }
                expected++;
                names.add(field.getName());
                genericTreeCollectors.add(new GenericTree(field.getGenericType()));
                Object val = field.get(null);
                if(val == null) {
                    continue;
                }
                actual++;
                values.add((T) val);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        if(expected == actual) {
            values.add((T) this); //add this as last initiated
            T[] array  = (T[]) Array.newInstance(values.get(0).getClass(),values.size());
            for (int j = 0; j < values.size(); j++) {
                array[j] = values.get(j);
            }
            for (int i = 0; i < values.size(); i++) {
                T val = values.get(i);
                val.maxOrdinal = actual-1;
                val.ordinal = i;
                val.values = array;
                val.genericTree = genericTreeCollectors.get(i);
                if(val.name == null) {
                    val.name = names.get(i); //default name, if not set
                }
            }
        }

    }

    protected T[] values;

    @Override
    public T[] values() {
        T[] copy = Arrays.copyOf(values, values.length);
        return copy;
    }

    @Override
    public int ordinal() {
        return ordinal;
    }

    @Override
    public int maxOrdinal() {
        return maxOrdinal;
    }

    public GenericTree getGenericTree() {
        return genericTree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnumeratedFields<?> that = (EnumeratedFields<?>) o;
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

    public static <T extends EnumeratedFields<? extends T>> T[] values(Class<? extends T> forClass) {
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
                return ((T)val).values;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public static <T extends EnumeratedFields<? extends T>> T valueOf(Class<? extends T> forClass, String name) {
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

    public static <T extends EnumeratedFields<? extends T>> T valueOf(Class<? extends T> forClass, int pos) {
        return values(forClass)[pos];
    }

}
