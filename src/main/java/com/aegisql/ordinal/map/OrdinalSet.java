package com.aegisql.ordinal.map;

import java.util.*;

import com.aegisql.ordinal.Ordinal;

public class OrdinalSet <K extends Ordinal<K>> implements Set<K> {

    private final OrdinalMap<K,Integer> map;

    public OrdinalSet(K[] elements) {
        this(new OrdinalMap<>(elements));
    }

    public OrdinalSet(Collection<K> elements) {
        this(new OrdinalMap<>(elements));
    }

    public OrdinalSet(K instance) {
        this(new OrdinalMap<>(instance));
    }

    private OrdinalSet(OrdinalMap<K, Integer> map) {
        this.map = map;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public Iterator<K> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public Object[] toArray() {
        return map.keySet().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return map.keySet().toArray(a);
    }

    @Override
    public boolean add(K k) {
        return map.put(k, k.ordinal()) == null;
    }

    @Override
    public boolean remove(Object o) {
        return map.remove(o) != null;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return map.keySet().containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends K> c) {
        boolean changed = false;
        for(K o:c) {
            if( ! add(o)) {
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean changed = false;
        for(Object o:c) {
            if( ! contains(o)) {
                remove(o);
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean changed = false;
        for(Object o:c) {
            if(remove(o)) {
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public void clear() {
        map.clear();
    }
}

