package com.aegisql.ordinal.reflection;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.aegisql.ordinal.reflection.TypedOrdinals.*;
import static org.junit.jupiter.api.Assertions.*;

class OrdinalFieldsTest {

    @Test
    public void typedOrdinalsTest() {

        TypedOrdinals[] values = OrdinalFields.values(TypedOrdinals.class);
        assertNotNull(values);
        assertEquals(3,values.length);

        assertEquals(strField,values[0]);
        assertEquals(intField,values[1]);
        assertEquals(typedOrdinalsField,values[2]);

        assertTrue(Arrays.equals(values,strField.values()));

        assertEquals(0,strField.ordinal());
        assertEquals(1,intField.ordinal());
        assertEquals(2,typedOrdinalsField.ordinal());

        assertEquals(2,intField.maxOrdinal());

        assertEquals("strField",strField.getName());
        assertEquals("intField",intField.getName());
        assertEquals("typedOrdinalsField",typedOrdinalsField.getName());

        assertEquals(String.class,strField.getParameterType());
        assertEquals(Integer.class,intField.getParameterType());
        assertEquals(TypedOrdinals.class,typedOrdinalsField.getParameterType());

        System.out.println(strField+" "+strField.getGenericTree());
        System.out.println(intField+" "+intField.getGenericTree());
        System.out.println(typedOrdinalsField+" "+typedOrdinalsField.getGenericTree());
    }
}