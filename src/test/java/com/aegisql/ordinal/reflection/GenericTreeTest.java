package com.aegisql.ordinal.reflection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.TypeVariable;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GenericTreeTest {

    public class OuterClass<T> {
        public class InnerClass<S> {
            T outerValue;
            S innerValue;
        }
    }

    public Integer number = 100;

    public List<Integer> listOfInteger = new ArrayList<>();
    public Map<String,Integer> mapOfIntegers = new HashMap<>();

    public Map<String,List<Integer>> mapOfListOfIntegers = new HashMap<>();

    public OuterClass<String> outer = new OuterClass<>();

    public OuterClass<String>.InnerClass<Integer> inner = outer.new InnerClass<>();

    @Test
    public void zeroLevelTest() {
        var gt = GenericTree.getGeneticInfo(getClass(),"number");
        assertFalse(gt.isParametrized());
        System.out.println(gt);
    }

    @Test
    public void singleLevelGenericTreeTest() {
        var gt = GenericTree.getGeneticInfo(getClass(),"listOfInteger");
        assertTrue(gt.isParametrized());
        var sub = gt.getGenericTree(0);
        assertFalse(sub.isParametrized());
        System.out.println(gt);
        System.out.println(sub);
    }

    @Test
    public void doubleLevelGenericTreeTest() {
        var gt = GenericTree.getGeneticInfo(getClass(),"mapOfIntegers");
        var sub1 = gt.getGenericTree(0);
        var sub2 = gt.getGenericTree(1);
        System.out.println(gt);
        System.out.println(sub1);
        System.out.println(sub2);
    }

    @Test
    public void threeLevelGenericTreeTest() {
        var gt = GenericTree.getGeneticInfo(getClass(),"mapOfListOfIntegers");
        var sub1 = gt.getGenericTree(0);
        var sub2 = gt.getGenericTree(1);
        var sub21 = gt.getGenericTree(1).getGenericTree(0);
        System.out.println(gt);
        System.out.println(sub1);
        System.out.println(sub2);
        System.out.println(sub21);

        assertEquals(2,gt.getGenericSubTrees().size());
    }

    @Test
    public void understandOuterInnerTest() {
        var gtOuter = GenericTree.getGeneticInfo(getClass(),"outer");
        var gtInner = GenericTree.getGeneticInfo(getClass(),"inner");

        System.out.println(gtOuter);
        System.out.println(gtInner);
    }

}