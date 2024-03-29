package com.aegisql.ordinal.map;

import com.aegisql.ordinal.Ordinal;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class OrdinalMap <K extends Ordinal<K>, V> implements Map<K,V>, Cloneable, Serializable {

    public class OrdinalEntry<K  extends Ordinal<K>,V> implements Ordinal<K>, Entry<K,V> {

        private final K key;
        private V value;

        public OrdinalEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public int ordinal() {
            return key.ordinal();
        }

        @Override
        public int maxOrdinal() {
            return key.maxOrdinal();
        }

        @Override
        public K[] values() {
            return null;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V old = this.value;
            this.value = value;
            return old;
        }

        @Override
        public String toString() {
            return "OrdinalEntry{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }

    private class UnmodifiableOrdinalSet<T extends Ordinal<T>> implements Set<T> {

        private final T[] elements;
        private final int size;

        public UnmodifiableOrdinalSet(T[] elements) {
            this.elements = elements;
            int size = 0;
            for(int i = 0; i < elements.length; i++) {
                T element = elements[i];
                if(element != null) {
                    size++;
                }
            }
            this.size = size;
        }

        @Override
        public int size() {
            return this.size;
        }

        @Override
        public boolean isEmpty() {
            return size==0;
        }

        @Override
        public boolean contains(Object o) {
            K key = (K) o;
            return elements[key.ordinal()] != null;
        }

        @Override
        public Iterator<T> iterator() {

            return new Iterator<T>() {

                int pos = 0;

                @Override
                public boolean hasNext() {
                    for(int i = pos; i < elements.length; i++) {
                        if(elements[i] != null) {
                            pos = i;
                            return true;
                        }
                    }
                    return false;
                }

                @Override
                public T next() {
                    for(;pos < elements.length; pos++) {
                        if(elements[pos] != null) {
                            return elements[pos++];
                        }
                    }
                    throw new NoSuchElementException("You've reached the end of the iterator");
                }
            };
        }

        @Override
        public Object[] toArray() {
            Object[] array = new Object[size];
            int i = 0;
            for(T t:elements) {
                if(t==null) continue;
                array[i++] = t;
            }
            return array;
        }

        @Override
        public <T1> T1[] toArray(T1[] a) {
            T1[] array = (T1[]) Array.newInstance(a.getClass().getComponentType(), size);
            int i = 0;
            for(T t:elements) {
                if(t==null) continue;
                T1 element = (T1) t;
                array[i++] = element;
            }
            return array;
        }

        @Override
        public boolean add(T t) {
            throw new UnsupportedOperationException("UnmodifiableOrdinalSet does not support add(t)");
        }

        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException("UnmodifiableOrdinalSet does not support remove(o)");
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            for(Object o:c) {
                if(o==null) return false;
                if(o instanceof Ordinal) {
                    Ordinal ordinal = (Ordinal) o;
                    if(ordinal.maxOrdinal() >= elements.length) return false;
                    if(elements[ordinal.ordinal()] == null) return false;
                    return elements[ordinal.ordinal()].equals(ordinal);
                } else {
                    return false;
                }
            }
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends T> c) {
            throw new UnsupportedOperationException("UnmodifiableOrdinalSet does not support addAll(a)");
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException("UnmodifiableOrdinalSet does not support retainAll(c)");
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException("UnmodifiableOrdinalSet does not support removeAll(c)");
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException("UnmodifiableOrdinalSet does not support clear()");
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("{");
            for(int i = 0; i < elements.length; i++) {
                if(elements[i]!=null) {
                    sb.append(elements[i]).append(",");
                }
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
            sb.append("}");
            return sb.toString();
        }
    }

    private OrdinalEntry<K,V>[] entry;

    private int size = 0;

    private final Class<K> keyType;

    private final K[] keys;

    public OrdinalMap(K[] elements) {
        Objects.requireNonNull(elements,"OrdinalMap requires sample of elements");
        entry = new OrdinalEntry[elements.length];
        keyType = (Class<K>) elements[0].getClass();
        this.keys = elements;
    }

    public OrdinalMap(Collection<K> elements) {
        Objects.requireNonNull(elements,"OrdinalMap requires sample of elements");
        entry = new OrdinalEntry[elements.size()];
        keyType = (Class<K>) elements.iterator().next().getClass();
        this.keys = (K[]) Array.newInstance(keyType,elements.size());
        var iterator = elements.iterator();
        for(int i = 0; i < elements.size(); i++) {
            keys[i] = iterator.next();
        }
    }

    public OrdinalMap(K instance) {
        entry = new OrdinalEntry[instance.maxOrdinal()+1];
        keyType = (Class<K>) instance.getClass();
        this.keys = instance.values();
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
        OrdinalEntry<K,V> e = new OrdinalEntry<>(key,value);
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
        K[] keys = (K[]) Array.newInstance(keyType,entry.length);
        for(int i = 0; i < entry.length; i++) {
            Entry<K, V> kvEntry = entry[i];
            if(kvEntry != null) {
                keys[i] = kvEntry.getKey();
            } else {
                keys[i] = null;
            }
        }
        Set<K> set = new UnmodifiableOrdinalSet<>(keys);
        return Collections.unmodifiableSet(set);
    }

    @Override
    public Collection<V> values() {
        List<V> vList = entrySet().stream().map(Entry::getValue).collect(Collectors.toList());
        return Collections.unmodifiableList(vList);
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        OrdinalEntry<K, V>[] clone = entry.clone();
        var set = new UnmodifiableOrdinalSet<>((Ordinal[])clone);
        return set;
    }

    public K[] getKeys() {
        return keys;
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
