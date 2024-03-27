package com.aegisql.ordinal.multimethod;

import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

class Function2Test {

    public String sum(int x, int y) {
        return "Summa "+x+"+"+y+"="+(x+y);
    }

    public String mul(int x, int y) {
        return "Prod "+x+"*"+y+"="+(x*y);
    }

    public String pow(int x, int y) {
        return "Pow "+x+"^"+y+"="+Math.pow(x,y);
    }

    @Test
    public void function2Test() {

        BiFunction<Integer, Integer, String> dispatch = Function2.dispatch(
                (x, y) -> x % 3,
                this::sum,
                this::mul,
                this::pow
        );

        System.out.println(dispatch.apply(5,5));
        System.out.println(dispatch.apply(6,6));
        System.out.println(dispatch.apply(7,7));

    }

    @Test
    public void function2PredTest() {

        BiFunction<Integer, Integer, String> dispatch = Function2.dispatch(
                (x, y) -> x % 2 == 0 ? true:false,
                this::sum,
                this::mul
        );

        System.out.println(dispatch.apply(5,5));
        System.out.println(dispatch.apply(6,6));
        System.out.println(dispatch.apply(7,7));

    }

}