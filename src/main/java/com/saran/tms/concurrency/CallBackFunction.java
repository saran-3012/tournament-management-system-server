package com.saran.tms.concurrency;

import com.saran.tms.exceptions.ResponseException;

@FunctionalInterface
public interface CallBackFunction {
	public Object callBack(Object values[]) throws ResponseException;
}

