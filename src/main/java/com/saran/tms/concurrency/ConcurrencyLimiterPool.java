package com.saran.tms.concurrency;

import java.util.HashMap;
import java.util.Map;

public class ConcurrencyLimiterPool {

	private Map<Integer, ConcurrencyLimiter> concurrencyLimiterPool;
		
	public ConcurrencyLimiterPool() {
		concurrencyLimiterPool = new HashMap<>();
	}
		
	public synchronized ConcurrencyLimiter getOrCreate(int maxLimit, int hashedKey) {
		if(!concurrencyLimiterPool.containsKey(hashedKey)) {
			concurrencyLimiterPool.put(hashedKey, new ConcurrencyLimiter(maxLimit));
		}
		return concurrencyLimiterPool.get(hashedKey);
	}
	
	public void cleanUp() {
		for(Map.Entry<Integer, ConcurrencyLimiter> concurrencyLimiterEntry : concurrencyLimiterPool.entrySet()) {
			ConcurrencyLimiter concurrencyLimiter = concurrencyLimiterEntry.getValue();
			if(concurrencyLimiter.getSize() == 0) {
				concurrencyLimiterPool.remove(concurrencyLimiterEntry.getKey());
			}
		}
	}
}
