package com.aegisql.ordinal.reflection;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

public class StaticFieldInfo<T> {

    private final Class<T> fieldType;
    private final String fieldName;
    private final GenericTree genericTree;
    private final T value;
    private final boolean isReady;

    private final Containerize[] containerize;

    public StaticFieldInfo(T value, Field field) {
        Objects.requireNonNull(value,"StaticFieldInfo requires non null value");
        Objects.requireNonNull(field,"StaticFieldInfo requires a field instance");
        fieldType = (Class<T>) field.getType();
        if(fieldType != value.getClass()) {
            throw new RuntimeException("Field and value types must match. Value type "+value.getClass()+" expected field type "+fieldType);
        }
        fieldName = field.getName();
        ContainerizeValues containerizeValues = field.getAnnotation(ContainerizeValues.class);
        if(containerizeValues != null) {
            this.containerize = containerizeValues.value();
        } else {
            this.containerize = new Containerize[]{};
        }
        genericTree = new GenericTree(field);
        try {
            T fieldValue = (T) field.get(null);
            if(fieldValue != null) {
                this.value = fieldValue;
                this.isReady = true;
            } else {
                this.value = value;
                this.isReady = false;
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Class<T> getFieldType() {
        return fieldType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public GenericTree getGenericTree() {
        return genericTree;
    }

    public T getValue() {
        return value;
    }

    public boolean isReady() {
        return isReady;
    }

    public Containerize[] getContainerize() {
        return containerize;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StaticFieldInfo{");
        sb.append("fieldName='").append(fieldName).append('\'');
        sb.append(", fieldType=").append(fieldType);
        sb.append(", genericTree=").append(genericTree);
        sb.append(", ready=").append(isReady);
        sb.append(", containerize=").append(Arrays.toString(containerize));
        sb.append('}');
        return sb.toString();
    }

}
