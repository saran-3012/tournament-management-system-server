package com.saran.tms.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.saran.tms.enums.Operators;
import com.saran.tms.enums.TableNames;

public class ConditionEntry implements QueryEntry {
	
	protected Operators logicalOperator;
	protected TableNames tableName;
	protected String columnName;
	protected List<Operators> relationalOperators; 
	protected Object columnValue;
	protected List<ConditionEntry> nestedConditions;
	protected boolean negate;
	
	protected short type;
	
	protected List<Object> conditionValues;
	
	public ConditionEntry(String columnName, Operators relationalOperator, Object columnValue) {
		this.type = 0;
		
		this.columnName = columnName;
		this.relationalOperators = Arrays.asList(relationalOperator);
		this.columnValue = columnValue;
	}
	
	public ConditionEntry(String columnName, List<Operators> relationalOperators, Object columnValue) {
		this.type = 0;
		
		this.columnName = columnName;
		this.relationalOperators = relationalOperators;
		this.columnValue = columnValue;
	}
	
	public ConditionEntry(String columnName, Operators relationalOperator, Object columnValue, boolean negate) {
		this.type = 0;
		
		this.columnName = columnName;
		this.relationalOperators = Arrays.asList(relationalOperator);
		this.columnValue = columnValue;
		this.negate = negate;
	}
	
	public ConditionEntry(String columnName, List<Operators> relationalOperators, Object columnValue, boolean negate) {
		this.type = 0;
		
		this.columnName = columnName;
		this.relationalOperators = relationalOperators;
		this.columnValue = columnValue;
		this.negate = negate;
	}
	

	public ConditionEntry(String columnName, List<Operators> relationalOperators) {
		this.type = 1;
		
		this.columnName = columnName;
		this.relationalOperators = relationalOperators;
	}
	
	public ConditionEntry(String columnName, List<Operators> relationalOperators, boolean negate) {
		this.type = 1;
		
		this.columnName = columnName;
		this.relationalOperators = relationalOperators;
		this.negate = negate;
	}
	
	
	public ConditionEntry(TableNames tableName, String columnName, Operators relationalOperator, Object columnValue) {
		this.type = 2;
		
		this.tableName = tableName;
		this.columnName = columnName;
		this.relationalOperators = Arrays.asList(relationalOperator);
		this.columnValue = columnValue;
	}
	
	public ConditionEntry(TableNames tableName, String columnName, List<Operators> relationalOperators, Object columnValue) {
		this.type = 2;
		
		this.tableName = tableName;
		this.columnName = columnName;
		this.relationalOperators = relationalOperators;
		this.columnValue = columnValue;
	}
	
	public ConditionEntry(TableNames tableName, String columnName, Operators relationalOperator, Object columnValue, boolean negate) {
		this.type = 2;
		
		this.tableName = tableName;
		this.columnName = columnName;
		this.relationalOperators = Arrays.asList(relationalOperator);
		this.columnValue = columnValue;
		this.negate = negate;
	}
	
	public ConditionEntry(TableNames tableName, String columnName, List<Operators> relationalOperators, Object columnValue, boolean negate) {
		this.type = 2;
		
		this.tableName = tableName;
		this.columnName = columnName;
		this.relationalOperators = relationalOperators;
		this.columnValue = columnValue;
		this.negate = negate;
	}
	
	
	public ConditionEntry(TableNames tableName, String columnName, List<Operators> relationalOperators) {
		this.type = 3;
		
		this.tableName = tableName;
		this.columnName = columnName;
		this.relationalOperators = relationalOperators;
	}
	
	public ConditionEntry(TableNames tableName, String columnName, List<Operators> relationalOperators, boolean negate) {
		this.type = 3;
		
		this.tableName = tableName;
		this.columnName = columnName;
		this.relationalOperators = relationalOperators;
		this.negate = negate;
	}
	
	
	public ConditionEntry(Operators logicalOperator, List<ConditionEntry> nestedConditions) {
		this.type = 4;
		
		this.logicalOperator = logicalOperator;
		this.nestedConditions = nestedConditions;
	}
	
	public ConditionEntry(Operators logicalOperator, List<ConditionEntry> nestedConditions, boolean negate) {
		this.type = 4;
		
		this.logicalOperator = logicalOperator;
		this.nestedConditions = nestedConditions;
		this.negate = negate;
	}
	
	protected String joinOperators() {
		if(relationalOperators == null || relationalOperators.isEmpty()) {
			return "";
		}
		int n = relationalOperators.size();
		StringBuilder operatorString = new StringBuilder(relationalOperators.get(0).getOperator());
		for(int i=0; i<n; i++) {
			operatorString.append(' ').append(relationalOperators.get(i).getOperator());
		}
		return operatorString.toString();
	}
	
	protected String joinConditions() {
		if(nestedConditions == null || nestedConditions.isEmpty()) {
			return "";
		}
		int n = nestedConditions.size();
		StringBuilder conditionString = new StringBuilder(nestedConditions.get(0).toQueryString());
		conditionValues.addAll(nestedConditions.get(0).getConditionValues());
		for(int i=1; i<n; i++) {
			conditionString.append(logicalOperator.getOperator()).append(nestedConditions.get(i).toQueryString());
			conditionValues.addAll(nestedConditions.get(i).getConditionValues());
		}
		return conditionString.toString();
	}
	
	public String toQueryString() {
		StringBuilder queryStringBuilder = new StringBuilder(negate? "NOT " : "");
		conditionValues = new ArrayList<>();
		switch(type) {
			case 0:
				queryStringBuilder.append(columnName)
									 .append(joinOperators())
									 .append('?')
									 .append(' ');
				
				conditionValues.add(columnValue);
				break;
			case 1:
				queryStringBuilder.append(columnName)
									 .append(joinOperators())
									 .append(' ');
				
				break; 
			case 2:
				queryStringBuilder.append(tableName.getTableName())
									 .append('.')
									 .append(columnName)
									 .append(' ')
									 .append(joinOperators())
									 .append('?')
									 .append(' ');
				
				conditionValues.add(columnValue);
				break;
			case 3:
				queryStringBuilder.append(tableName.getTableName())
									 .append(columnName)
									 .append(joinOperators())
									 .append(' ');
				break;
			case 4:
				queryStringBuilder.append('(')
									 .append(' ')
									 .append(joinConditions())
									 .append(' ')
									 .append(')')
									 .append(' ');
				break;
				
			default:;
		}
		
		return queryStringBuilder.toString();
	}
	
	public List<Object> getConditionValues() {
		return conditionValues;
	}
	
}
