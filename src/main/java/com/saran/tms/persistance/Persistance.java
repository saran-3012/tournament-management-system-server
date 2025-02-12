package com.saran.tms.persistance;

import com.saran.tms.persistance.fs.FileDataStore;
import com.saran.tms.persistance.redis.RedisDataStore;

public class Persistance {
	
	public static enum DataStores{
		REDIS(),
		FS();
		private DataStores() {}
	}
	
	private DataStore ds;
	
	private Persistance(DataStores dsType, DataStoreConfig dsConfig) {
		switch(dsType) {
			case REDIS:
				ds = new RedisDataStore(dsConfig);
				break;
			case FS:
				ds = new FileDataStore(dsConfig);
				break;
			default:
				System.out.println("Invalid Data Store option");
		}
	}
	
	public static Persistance getPersistance(DataStores dsType, DataStoreConfig dsConfig) {
		return new Persistance(dsType, dsConfig);
	}
	
	public void write(Object ...args) {
		ds.write(args);
	}
	
	public Object read(Object ...args) {
		return ds.read(args);
	}
	
	public void update(Object ...args) {
		ds.update(args);
	}
	
	public void delete(Object ...args) {
		ds.delete(args);
	}
	
	public Persistance setStrategy(StorageStrategy storageStrategy) {
		ds.setStrategy(storageStrategy);
		return this;
	}
}
