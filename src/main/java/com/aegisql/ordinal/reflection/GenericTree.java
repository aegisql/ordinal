package com.aegisql.ordinal.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GenericTree {

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

}

