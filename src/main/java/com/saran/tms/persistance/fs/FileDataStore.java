package com.saran.tms.persistance.fs;

import com.saran.tms.persistance.DataStore;
import com.saran.tms.persistance.DataStoreConfig;
import com.saran.tms.persistance.StorageStrategy;

public class FileDataStore implements DataStore {
	private FileConfig fileConfig;
	private StorageStrategy fileStrategy;
	
	public FileDataStore(DataStoreConfig dsConfig) {
		this.fileConfig = (FileConfig) dsConfig;
		this.fileStrategy = FileStrategies.overwriteStrategy;
	}
	
	@Override
	public void setStrategy(StorageStrategy fStrategy) {
		if(fStrategy instanceof FileStrategy) {			
			this.fileStrategy = fStrategy;
		}
	}

	@Override
	public Object write(Object... args) {
		Object[] arguments = prependObjects(new Object[] {fileConfig.getFolderPath(), fileConfig.getFileName()}, args);
		fileStrategy.write(arguments);
		return null;
	}

	@Override
	public Object read(Object... args) {
		Object[] arguments = prependObjects(new Object[] {fileConfig.getFolderPath(), fileConfig.getFileName()}, args);
		return fileStrategy.read(arguments);
	}

	@Override
	public Object update(Object... args) {
		Object[] arguments = prependObjects(new Object[] {fileConfig.getFolderPath(), fileConfig.getFileName()}, args);
		fileStrategy.update(arguments);
		return null;
	}

	@Override
	public Object delete(Object... args) {
		Object[] arguments = prependObjects(new Object[] {fileConfig.getFolderPath(), fileConfig.getFileName()}, args);
		fileStrategy.delete(arguments);
		return null;
	}
	
	
}
