package com.aegisql.ordinal.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GenericTree {

    public static final int[] SIMPLE_COLLECTION_PATH = new int[]{0};
    public static final int[] SIMPLE_MAP_KEY_PATH = new int[]{0};
    public static final int[] SIMPLE_MAP_VAL_PATH = new int[]{1};

    private final String name;
    private  final Class aClass;

    private final boolean isParametrized;
    private final List<GenericTree> genericSubTrees = new ArrayList<>();

    public static GenericTree getGeneticInfo(Class cls, String fieldName) {
        try {
            Field field = cls.getField(fieldName);
            return new GenericTree(field);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public GenericTree(Field field) {
        this(field.getGenericType());
    }

    public GenericTree(Type type) {
        name = type.getTypeName();
        Class aClass = null;
        if(type instanceof ParameterizedType) {
            this.isParametrized = true;
            ParameterizedType parameterizedType = (ParameterizedType) type;
            try {
                aClass = Class.forName(parameterizedType.getRawType().getTypeName());
            } catch (ClassNotFoundException e) {
            }
            collectGenericType((ParameterizedType) type, this);
        } else {
            this.isParametrized = false;
            try {
                aClass = Class.forName(name);
            } catch (ClassNotFoundException e) {
            }
        }
        this.aClass = aClass;
    }

    private void collectGenericType(ParameterizedType parameterizedType, GenericTree parent) {
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            for (Type typeArgument : typeArguments) {
                GenericTree genericTree = new GenericTree(typeArgument);
                genericSubTrees.add(genericTree);
            }
    }

    public String getName() {
        return name;
    }

    public Class getaClass() {
        return aClass;
    }

    public List<GenericTree> getGenericSubTrees() {
        return genericSubTrees;
    }

    public GenericTree getGenericTree(int pos) {
        return getGenericSubTrees().get(pos);
    }

    public boolean isParametrized() {
        return isParametrized;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder(aClass == null ? name : aClass.getCanonicalName());

        if(genericSubTrees.size() > 0) {
            sb.append('<');
            for(var subtree:genericSubTrees) {
                sb.append(subtree.toString()).append(',');
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
            sb.append('>');
        }

        return sb.toString();
    }

    public void deepTest(Object o, int... path) {
        if(o==null) {
            return;
        }
        if(path == null || path.length == 0 || path[0] == -1) {
            test(aClass,o);
            return;
        }
        int pos = path[0];
            getGenericTree(pos).deepTest(o,shift(path));
    }

    private void test(Class cls, Object val) {
        if(val != null) {
            var valClass = val.getClass();
            if( ! cls.isAssignableFrom(valClass)) {
                throw new RuntimeException("Class "+valClass+" is not assignable from "+cls);
            }
        }
    }

    protected static int[] shift(int[] array) {
        if(array == null || array.length==0) {
            throw new IllegalArgumentException("Array is null or empty");
        }
        int[] newArray = new int[array.length-1];
        System.arraycopy(array, 1, newArray, 0, array.length - 1);
        return newArray;
    }

}

