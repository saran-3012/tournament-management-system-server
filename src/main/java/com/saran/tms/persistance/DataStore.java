package com.saran.tms.persistance;

public interface DataStore {
	public Object write(Object ...obj);
	public Object read(Object ...obj);
	public Object update(Object ...obj);
	public Object delete(Object ...obj);
	public void setStrategy(StorageStrategy storageStrategy);
	
	default Object[] prependObjects(Object vals[], Object[] arr) {
		final int m = vals.length;
		final int n = arr.length;
		Object[] res = new Object[m + n];
		int idx = 0;
		for(int i=0; i<m; i++) {
			res[idx++] = vals[i];
		}
		for(int i=0; i<n; i++) {
			res[idx++] = arr[i];
		}
		return res;
	}
}
