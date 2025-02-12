package com.saran.tms.adapters.cache;

import java.util.concurrent.TimeUnit;

public interface LRUCache<K, V> {
	public static enum CacheImpl{
		CaffeineCache
	}
	
	public static <K, V> LRUCache<K, V> getCache(CacheImpl cacheImpl, long maximumSize, long idleTimeout, TimeUnit timeunit){
		switch(cacheImpl) {
			case CaffeineCache:
				return new CaffeineCache<K, V>(maximumSize, idleTimeout, timeunit);
			default:
				return null;
		}
	}
	void put(K key, V value);
	V get(K key);
	void remove(K key);
	void cleanUp();
	boolean containsKey(K key);
}
