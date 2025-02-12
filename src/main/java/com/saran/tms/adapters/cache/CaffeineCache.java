package com.saran.tms.adapters.cache;

import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

public class CaffeineCache<K, V> implements LRUCache<K, V> {

	private Cache<K, V> cache;
	
	protected CaffeineCache(long maximumSize, long idleTimeout, TimeUnit timeunit) {
		cache = Caffeine.newBuilder()
						.maximumSize(maximumSize)
						.expireAfterAccess(idleTimeout, timeunit)
						.build();
	}
	
	@Override
	public void put(K key, V value) {
		cache.put(key, value);
	}

	@Override
	public V get(K key) {
		return cache.getIfPresent(key);
	}

	@Override
	public void remove(K key) {
		cache.invalidate(key);
	}

	@Override
	public void cleanUp() {
		cache.cleanUp();
	}

	@Override
	public boolean containsKey(K key) {
		return (get(key) != null);
	}
	
}
