package com.saran.tms.config;

import java.util.Collections;
import java.util.Set;

import com.saran.tms.enums.Constraints;

public class ColumnConfig {
	private String columnName;
	private String fieldName;
	private Class<?> dataType;
	private Set<Constraints> constraints;
	
	public ColumnConfig(String columnName, String fieldName, Class<?> dataType, Set<Constraints> constraints) {
		this.columnName = columnName;
		this.fieldName = fieldName;
		this.dataType = dataType;
		this.constraints = Collections.unmodifiableSet(constraints);
	}

	public String getColumnName() {
		return columnName;
	}

	public String getFieldName() {
		return fieldName;
	}
	
	public Class<?> getDataType(){
		return dataType;
	}

	public Set<Constraints> getConstraints() {
		return constraints;
	}

	@Override
	public String toString() {
		return "ColumnConfig [columnName=" + columnName + ", fieldName=" + fieldName + ", dataType=" + dataType
				+ ", constraints=" + constraints + "]";
	}
	
}
