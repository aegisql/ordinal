package com.aegisql.ordinal.multimethod;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Function3Test {

    public String sum(int x, int y, int z) {
        return "Summa "+x+"+"+y+"+"+z+"="+(x+y+z);
    }

    public String mul(int x, int y, int z) {
        return "Prod "+x+"*"+y+"*"+z+"="+(x*y*z);
    }

    public String pow(int x, int y, int z) {
        return "Pow "+z+"*("+x+"^"+y+")="+z*Math.pow(x,y);
    }


    @Test
    void testDispatch1() {
        var dispatch = Function3.dispatch((x, y, z) -> (x + y + z) % 3,
                this::sum,
                this::mul,
                this::pow
        );

        System.out.println(dispatch.apply(1,2,3));
        System.out.println(dispatch.apply(1,2,4));
        System.out.println(dispatch.apply(1,2,5));
    }

    @Test
    void testDispatch2() {
        var dispatch = Function3.dispatch((x, y, z) -> (x + y + z) % 3==0,
                this::sum,
                this::mul
        );

        System.out.println(dispatch.apply(1,2,3));
        System.out.println(dispatch.apply(1,2,4));
    }

}