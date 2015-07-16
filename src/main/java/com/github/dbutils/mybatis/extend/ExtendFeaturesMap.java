package com.github.dbutils.mybatis.extend;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 扩展字段ExtendFeatures专用的map
 * @param <K>
 * @param <V>
 * @project dal-common
 * @author tony.shen
 * @date 2012-5-27 下午7:41:21
 * Copyright (C) 2010-2012 www.xxx.com Inc. All rights reserved.
 */
public class ExtendFeaturesMap<K,V> implements Map<K,V> {

    private final Map<K,V> map;

    public ExtendFeaturesMap() {
        this(new HashMap<K, V>());
    }
    
    public ExtendFeaturesMap(Map<K, V> map) {
        this.map = map;
    }
    
    public Collection<V> values() {
        return map.values();
    }

    public int size() {
        return map.size();
    }

    public V remove(Object key) {
        return map.remove(key);
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    public V put(K key, V value) {
        return map.put(key, value);
    }

    public Set<K> keySet() {
        return map.keySet();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public V get(Object key) {
        return map.get(key);
    }

    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }

    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    public void clear() {
        map.clear();
    }
    
    public int getSize() {
        return size();
    }
    
    @Override
    public String toString() {
        return map.toString();
    }
}
