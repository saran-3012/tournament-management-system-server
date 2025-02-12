package com.saran.tms.persistance;

public interface StorageStrategy {
	public Object write(Object ...args);
	public Object read(Object ...args);
	public Object update(Object ...args);
	public Object delete(Object ...args);
}
