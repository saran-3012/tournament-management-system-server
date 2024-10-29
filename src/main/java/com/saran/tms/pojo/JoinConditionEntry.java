package com.saran.tms.pojo;


import com.saran.tms.enums.Operators;
import com.saran.tms.enums.TableNames;

public class JoinConditionEntry extends JoinEntry {
	private static final int type = 1;
	
	private TableNames conditionTableName;
	private String conditionColumnName;
	private Operators prefixOperator;
	private Operators suffixOperator;
	private Object value;
	
	
	public JoinConditionEntry() {
		super();
	}

	public JoinConditionEntry(Operators prefixOperator, TableNames conditionTableName, String conditionColumnName, 
			Operators suffixOperator, Object value) {
		super();
		this.conditionTableName = conditionTableName;
		this.conditionColumnName = conditionColumnName;
		this.prefixOperator = prefixOperator;
		this.suffixOperator = suffixOperator;
		this.value = value;
	}

	public TableNames getConditionTableName() {
		return conditionTableName;
	}

	public void setConditionTableName(TableNames conditionTableName) {
		this.conditionTableName = conditionTableName;
	}

	public String getConditionColumnName() {
		return conditionColumnName;
	}

	public void setConditionColumnName(String conditionColumnName) {
		this.conditionColumnName = conditionColumnName;
	}

	public Operators getPrefixOperator() {
		return prefixOperator;
	}

	public void setPrefixOperator(Operators prefixOperator) {
		this.prefixOperator = prefixOperator;
	}

	public Operators getSuffixOperator() {
		return suffixOperator;
	}

	public void setSuffixOperator(Operators suffixOperator) {
		this.suffixOperator = suffixOperator;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public int getType() {
		return type;
	}

	@Override
	public String toString() {
		return "JoinConditionEntry [conditionTableName=" + conditionTableName + ", conditionColumnName="
				+ conditionColumnName + ", prefixOperator=" + prefixOperator + ", suffixOperator=" + suffixOperator
				+ ", value=" + value + "]";
	}
	
}
