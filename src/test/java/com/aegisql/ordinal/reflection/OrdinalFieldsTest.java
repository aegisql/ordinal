package com.aegisql.ordinal.reflection;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.aegisql.ordinal.reflection.TestOrdinals.*;
import static org.junit.jupiter.api.Assertions.*;

class OrdinalFieldsTest {

    @Test
    public void typedOrdinalsTest() {

        TestOrdinals[] values = OrdinalFields.values(TestOrdinals.class);
        assertNotNull(values);
        assertEquals(4,values.length);


        Class<String> strFieldType = strField.getType();
        Class<Integer> intFieldType = intField.getType();

        assertEquals(String.class,strField.getType());
        assertEquals(Integer.class,intField.getType());

        assertEquals(strField,values[0]);
        assertEquals(intField,values[1]);
        assertEquals(typedOrdinalsField,values[2]);

        assertTrue(Arrays.equals(values,strField.values()));

        assertEquals(0,strField.ordinal());
        assertEquals(1,intField.ordinal());
        assertEquals(2,typedOrdinalsField.ordinal());

        assertEquals(3,intField.maxOrdinal());

        assertEquals("strField",strField.getName());
        assertEquals("intField",intField.getName());
        assertEquals("typedOrdinalsField",typedOrdinalsField.getName());

        assertEquals(String.class,strField.getParameterType());
        assertEquals(Integer.class,intField.getParameterType());
        assertEquals(TestOrdinals.class,typedOrdinalsField.getParameterType());

        System.out.println(strField+" "+strField.getGenericTree());
        System.out.println(intField+" "+intField.getGenericTree());
        System.out.println(typedOrdinalsField+" "+typedOrdinalsField.getGenericTree());
    }
}