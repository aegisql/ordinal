package com.aegisql.ordinal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrdinalHolderTest {

    @Test
    public void testOrdinalHolder() {
        var oh = OrdinalHolder.wrap("MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY","SUNDAY");
        var mon = oh[0];
        var tue = oh[1];
        var wed = oh[2];
        var thu = oh[3];
        var fri = oh[4];
        var sat = oh[5];
        var sun = oh[6];
        System.out.println(mon.valuesCollection());
        assertEquals("MONDAY",mon.getValue());
        assertEquals("TUESDAY",tue.getValue());
        assertEquals("WEDNESDAY",wed.getValue());
        assertEquals("THURSDAY",thu.getValue());
        assertEquals("FRIDAY",fri.getValue());
        assertEquals("SATURDAY",sat.getValue());
        assertEquals("SUNDAY",sun.getValue());

        assertEquals(-1,mon.compareTo(tue));
        assertEquals(-1,tue.compareTo(wed));
        assertEquals(-1,wed.compareTo(thu));
        assertEquals(-1,thu.compareTo(fri));
        assertEquals(-1,fri.compareTo(sat));
        assertEquals(-1,sat.compareTo(sun));
        assertEquals(6,sun.compareTo(mon));

        assertEquals(6,mon.maxOrdinal());
        assertEquals(6,tue.maxOrdinal());
        assertEquals(6,wed.maxOrdinal());
        assertEquals(6,thu.maxOrdinal());
        assertEquals(6,fri.maxOrdinal());
        assertEquals(6,sat.maxOrdinal());
        assertEquals(6,sun.maxOrdinal());

        assertNotEquals(mon,tue);
        assertNotEquals(tue,wed);


        var oh2 = OrdinalHolder.wrap("MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY","SUNDAY");
        assertEquals(mon,oh2[0]);
        var oh3 = OrdinalHolder.wrap("MONDAY","tuesday","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY","SUNDAY");
        assertNotEquals(mon,oh3[0]);

        assertEquals(mon.next(),tue);
        assertEquals(wed.prev(),tue);
        assertThrows(IndexOutOfBoundsException.class,mon::prev);
        assertThrows(IndexOutOfBoundsException.class,sun::next);

        for(var day:mon) {
            System.out.println("day="+day);
        }

    }

}