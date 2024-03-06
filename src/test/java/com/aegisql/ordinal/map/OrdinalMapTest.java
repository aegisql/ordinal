package com.aegisql.ordinal.map;

import com.aegisql.ordinal.OrdinalHolder;
import org.junit.jupiter.api.Test;

import static com.aegisql.ordinal.Ordinals.*;
import static org.junit.jupiter.api.Assertions.*;

class OrdinalMapTest {

    @Test
    public void basicOrdinalMapTest() {

        var map = new OrdinalMap<OrdinalHolder<String>,Integer>(DAYS);
        assertTrue(map.isEmpty());
        map.put(MONDAY,10);
        assertEquals(1,map.size());
        map.put(TUESDAY,20);
        assertEquals(2,map.size());
        map.put(WEDNESDAY,30);
        assertEquals(3,map.size());
        map.put(THURSDAY,40);
        assertEquals(4,map.size());
        map.put(FRIDAY,50);
        assertEquals(5,map.size());
        map.put(SATURDAY,60);
        assertEquals(6,map.size());
        map.put(SUNDAY,70);
        assertEquals(7,map.size());

        assertEquals(10,map.get(MONDAY));
        assertEquals(20,map.get(TUESDAY));
        assertEquals(30,map.get(WEDNESDAY));
        assertEquals(40,map.get(THURSDAY));
        assertEquals(50,map.get(FRIDAY));
        assertEquals(60,map.get(SATURDAY));
        assertEquals(70,map.get(SUNDAY));

        assertEquals(30,map.put(WEDNESDAY,300));
        assertEquals(300,map.get(WEDNESDAY));

        assertEquals(300,map.remove(WEDNESDAY));
        assertEquals(6,map.size());
        assertFalse(map.isEmpty());
        System.out.println(map);
        map.clear();
        assertTrue(map.isEmpty());
        assertEquals(0,map.size());
        System.out.println(map);
    }

}