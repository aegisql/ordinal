package com.aegisql.ordinal.map;

import org.junit.jupiter.api.Test;

import static com.aegisql.ordinal.Ordinals.*;
import static org.junit.jupiter.api.Assertions.*;

class OrdinalSetTest {

    @Test
    public void basicOrdinalSetTest() {
        var set = new OrdinalSet<>(DAYS);
        assertTrue(set.isEmpty());
        assertEquals(0,set.size());

        set.add(MONDAY);
        assertFalse(set.isEmpty());
        assertEquals(1,set.size());

        set.clear();
        assertTrue(set.isEmpty());
        assertEquals(0,set.size());

    }

}