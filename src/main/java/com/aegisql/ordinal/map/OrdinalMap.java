package com.aegisql.ordinal.map;

import com.aegisql.ordinal.Ordinal;

import java.io.Serializable;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.stream.Collectors;

public class OrdinalMap <K extends Ordinal<K>, V> implements Map<K,V>, Cloneable, Serializable {

    private Entry<K,V>[] entry;

    private int size = 0;

    public OrdinalMap(K[] elements) {
        Objects.requireNonNull(elements,"OrdinalMap requires sample of elements");
        entry = new SimpleEntry[elements.length];
    }

    public OrdinalMap(Collection<K> elements) {
        Objects.requireNonNull(elements,"OrdinalMap requires sample of elements");
        entry = new SimpleEntry[elements.size()];
    }

    public OrdinalMap(Class<K> cls, int size) {
        entry = new SimpleEntry[size];
    }

    public OrdinalMap(K instance) {
        entry = new SimpleEntry[instance.maxOrdinal()+1];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return entry[((K)key).ordinal()] != null;
    }

    @Override
    public boolean containsValue(Object value) {
        for(Entry o:entry) {
            if(Objects.equals(o.getValue(),value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        var e = entry[((K) key).ordinal()];
        return e == null ? null : e.getValue();
    }

    @Override
    public V put(K key, V value) {
        int ord = ((K) key).ordinal();
        SimpleEntry<K,V> e = new SimpleEntry<>(key,value);
        var prevEntry = entry[ord];
        V prevValue = prevEntry == null ? null : prevEntry.getValue();
        entry[ord] = e;
        if(prevValue == null) {
            size++;
        }
        return prevValue;
    }

    @Override
    public V remove(Object key) {
        int ord = ((K) key).ordinal();
        var prevEntry = entry[ord];
        if(prevEntry != null) {
            entry[ord] = null;
            size--;
            return prevEntry.getValue();
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        m.forEach((k,v)->put(k,v));
    }

    @Override
    public void clear() {
        for (int i = 0; i < entry.length; i++) {
            entry[i] = null;
        }
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> kSet = entrySet().stream().map(Entry::getKey).collect(Collectors.toSet());
        return Collections.unmodifiableSet(kSet);
    }

    @Override
    public Collection<V> values() {
        List<V> vList = entrySet().stream().map(Entry::getValue).collect(Collectors.toList());
        return Collections.unmodifiableList(vList);
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K,V>> eSet = new LinkedHashSet<>();
        for(int i = 0; i < entry.length; i++) {
            if(entry[i] != null) {
                eSet.add(entry[i]);
            }
        }
        return Collections.unmodifiableSet(eSet);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        OrdinalMap copy = (OrdinalMap) super.clone();
        copy.entry = this.entry.clone();
        return copy;
    }

    @Override
    public String toString() {
        Iterator<Entry<K,V>> i = entrySet().iterator();
        if (! i.hasNext())
            return "{}";

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (;;) {
            Entry<K,V> e = i.next();
            K key = e.getKey();
            V value = e.getValue();
            sb.append(key   == this ? "(this Map)" : key);
            sb.append('=');
            sb.append(value == this ? "(this Map)" : value);
            if (! i.hasNext())
                return sb.append('}').toString();
            sb.append(',').append(' ');
        }
    }
}
