package com.saran.tms.concurrency;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;


public class ConcurrencyLimiter {
	
	private final AtomicInteger currentThreads;
	private final ReentrantLock processLock = new ReentrantLock(true);
	private final int maxSize;
	
	public ConcurrencyLimiter(int maxSize) {
		this.maxSize = maxSize;
		currentThreads = new AtomicInteger(0);
	}
	
	public Object executeCallBack(CallBackFunction callBack, Object ...values) throws Exception {
		if(currentThreads.get() >= maxSize ) {
			throw new IllegalStateException("Maximum number of waiting tasks reached");
		}
		currentThreads.incrementAndGet();
		
		processLock.lock();
		try {
			return callBack.callBack(values);
		}
		catch(Exception e) {
			throw e;
		}
		finally {			
			currentThreads.decrementAndGet();
			processLock.unlock();
		}
	}
	
	public int getSize() {
		return currentThreads.get();
	}
}
