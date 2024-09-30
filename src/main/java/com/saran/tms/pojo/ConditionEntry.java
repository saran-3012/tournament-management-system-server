package com.saran.tms.pojo;

import java.util.List;

import com.saran.tms.enums.Operators;

public class ConditionEntry{

	private List<Operators> prefixOperators;
	private String columnName;
	private List<Operators> suffixOperators;
	private Object value;
	
	public ConditionEntry() {
		super();
	}

	public ConditionEntry(List<Operators> prefixOperators, String columnName, 
			List<Operators> suffixOperators, Object value) {
		this.columnName = columnName;
		this.value = value;
		this.prefixOperators = prefixOperators;
		this.suffixOperators = suffixOperators;
	}
	
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	public List<Operators> getPrefixOperators() {
		return prefixOperators;
	}
	public void setPrefixOperators(List<Operators> prefixOperators) {
		this.prefixOperators = prefixOperators;
	}

	public List<Operators> getSuffixOperators() {
		return suffixOperators;
	}
	public void setSuffixOperators(List<Operators> suffixOperators) {
		this.suffixOperators = suffixOperators;
	}
	
	
}
