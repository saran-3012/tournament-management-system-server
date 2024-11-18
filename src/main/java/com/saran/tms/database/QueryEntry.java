package com.saran.tms.database;

import com.saran.tms.exceptions.DataBaseException;

public interface QueryEntry {
	public String toQueryString() throws DataBaseException;
}
