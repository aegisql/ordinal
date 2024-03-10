package com.aegisql.ordinal.reflection;

import java.lang.annotation.*;

@Repeatable(ContainerizeValues.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Containerize {
    String value();
}
