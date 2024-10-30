package com.saran.tms.concurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ConcurrencyLimiterFactory {

	private static final Map<String, ConcurrencyLimiterPool> concurrencyLimiterPoolStore = new HashMap<>();
	
	public static void initializeConcurrencyLimiterPool(String key) {
		concurrencyLimiterPoolStore.put(key, new ConcurrencyLimiterPool());
	}
	
	public static ConcurrencyLimiter getConcurrencyLimiter(String key, int maxLimit, Object ...hashValues) {
		ConcurrencyLimiterPool concurrencyLimiterPool = concurrencyLimiterPoolStore.get(key);
		int hashedKey = computeHash(maxLimit, hashValues);
		return concurrencyLimiterPool.getOrCreate(maxLimit, hashedKey);
	}
	
	private static int computeHash(int maxLimit, Object hashValues[]) {
		int hashResult = maxLimit;
		for(Object hashValue : hashValues) {
			hashResult = Objects.hash(hashResult, hashValue);
		}
		return hashResult;
	}
	
	public static void cleanUp() {
		for(ConcurrencyLimiterPool concurrencyLimiterPool : concurrencyLimiterPoolStore.values()) {
			concurrencyLimiterPool.cleanUp();
		}
	}
}
