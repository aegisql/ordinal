package com.aegisql.ordinal.reflection;

import org.junit.jupiter.api.Test;

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
        var gt = GenericTree.getGenericInfo(getClass(),"number");
        assertFalse(gt.isParametrized());
        System.out.println(gt);

        gt.deepTest(1);

        assertThrows(RuntimeException.class,()->{gt.deepTest("ONE");});

    }

    @Test
    public void singleLevelGenericTreeTest() {
        var gt = GenericTree.getGenericInfo(getClass(),"listOfInteger");
        assertTrue(gt.isParametrized());
        var sub = gt.getGenericTree(0);
        assertFalse(sub.isParametrized());
        System.out.println(gt);
        System.out.println(sub);

        gt.deepTest(new LinkedList<>(),GenericTree.ROOT_PATH);
        gt.deepTest(1,GenericTree.SIMPLE_COLLECTION_PATH);

        assertThrows(RuntimeException.class,()->{gt.deepTest("ONE",GenericTree.SIMPLE_COLLECTION_PATH);});

    }

    @Test
    public void doubleLevelGenericTreeTest() {
        var gt = GenericTree.getGenericInfo(getClass(),"mapOfIntegers");
        var sub1 = gt.getGenericTree(0);
        var sub2 = gt.getGenericTree(1);
        System.out.println(gt);
        System.out.println(sub1);
        System.out.println(sub2);

        gt.deepTest(new HashMap<>());

        gt.deepTest("one",GenericTree.SIMPLE_MAP_KEY_PATH);
        gt.deepTest(1,GenericTree.SIMPLE_MAP_VAL_PATH);

        assertThrows(RuntimeException.class,()->{gt.deepTest(1,GenericTree.SIMPLE_MAP_KEY_PATH);});
        assertThrows(RuntimeException.class,()->{gt.deepTest("ONE",GenericTree.SIMPLE_MAP_VAL_PATH);});

    }

    @Test
    public void threeLevelGenericTreeTest() {
        var gt = GenericTree.getGenericInfo(getClass(),"mapOfListOfIntegers");
        var sub1 = gt.getGenericTree(0);
        var sub2 = gt.getGenericTree(1);
        var sub21 = gt.getGenericTree(1).getGenericTree(0);
        System.out.println(gt);
        System.out.println(sub1);
        System.out.println(sub2);
        System.out.println(sub21);

        assertEquals(2,gt.getGenericSubTrees().size());

        gt.deepTest(new HashMap<>());

        gt.deepTest("one",0);
        gt.deepTest(new LinkedList<>(),1);

        gt.deepTest(1,1,0);


    }

    @Test
    public void understandOuterInnerTest() {
        var gtOuter = GenericTree.getGenericInfo(getClass(),"outer");
        var gtInner = GenericTree.getGenericInfo(getClass(),"inner");

        System.out.println(gtOuter);
        System.out.println(gtInner);
    }

}