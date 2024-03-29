package com.aegisql.ordinal;

import com.aegisql.ordinal.map.OrdinalMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrdinalNumberTest {


    @Test
    public void ordinalNumbersTest() {

        OrdinalNumber on = new OrdinalNumber(10);

        assertEquals(-1,on.compareTo(on.next()));

        int i = 0;
        for(OrdinalNumber n: on) {
            assertEquals(i++,n.intValue());
            System.out.println(i+" of "+(on.maxOrdinal()+1));
        }

        OrdinalMap<OrdinalNumber,String> m = new OrdinalMap<>(on);

        m.put(on,"One");

        System.out.println(m);

    }

}