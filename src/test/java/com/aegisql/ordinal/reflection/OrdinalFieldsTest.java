package com.aegisql.ordinal.reflection;

import org.junit.jupiter.api.Test;

import static com.aegisql.ordinal.reflection.TypedOrdinals.*;
import static org.junit.jupiter.api.Assertions.*;

class OrdinalFieldsTest {

    @Test
    public void typedOrdinalsTest() {

        System.out.println(strField+" "+strField.getGenericTree());
        System.out.println(intField+" "+intField.getGenericTree());
        System.out.println(typedOrdinalsField+" "+typedOrdinalsField.getGenericTree());
    }
}