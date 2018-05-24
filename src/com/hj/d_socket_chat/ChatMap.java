package com.hj.d_socket_chat;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ChatMap<K, V> {
	public Map<K, V> map = Collections.synchronizedMap(new HashMap<>());
	
	//根据value删除指定项
	public synchronized void removeByValue(Object value) {
		for (Object key : map.keySet()) {
			if (map.get(key) == value) {
				map.remove(key);
				break;
			}
		}
	}
	
	//获取由所有value组成的Set集合
	public synchronized Set<V> valueSet() {
		Set<V> result = new HashSet<V>();
		map.forEach((key, value) -> result.add(value));
		return result;
	}
	
	//根据value查找key
	public synchronized K getKeyByValue(V value) {
		for (K key : map.keySet()) {
			if (map.get(key) == value || map.get(key).equals(value)) {
				return key;
			}
		}
		return null;
	}
	
	//put
	public synchronized V put(K key, V value) {
		for (V v : valueSet()) {
			if (v.equals(value) && v.hashCode() == value.hashCode()) {
				throw new RuntimeException("ChatMap实例中不允许有重复元素");
			}
		}
		return map.put(key, value);
	}
}
