package com.saran.tms.persistance.fs;

import com.saran.tms.persistance.DataStoreConfig;

public class FileConfig implements DataStoreConfig{
	private String folderPath;
	private String fileName;
	
	public FileConfig(String _folderPath) {
		this.folderPath = _folderPath;
	}
	
	public FileConfig(String _folderPath, String _fileName) {
		this.folderPath = _folderPath;
		this.fileName = _fileName;
	}
	
	public FileConfig setFolderPath(String _folderPath) {
		this.folderPath = _folderPath;
		return this;
	}
	
	public FileConfig setFileName(String _fileName) {
		this.fileName = _fileName;
		return this;
	}

	public String getFolderPath() {
		return folderPath;
	}
	
	public String getFileName() {
		return fileName;
	}

}
