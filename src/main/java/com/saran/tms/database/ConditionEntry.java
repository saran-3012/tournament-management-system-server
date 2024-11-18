package com.saran.tms.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.saran.tms.enums.Operators;
import com.saran.tms.enums.TableNames;
import com.saran.tms.exceptions.DataBaseException;

public class ConditionEntry implements QueryEntry {
	
	protected Operators logicalOperator;
	protected TableNames tableName;
	protected String columnName;
	protected List<Operators> relationalOperators; 
	protected Object columnValue;
	protected ConditionEntry[] nestedConditions;
	protected boolean negate;
	protected boolean optional;
	
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
	

	public ConditionEntry(String columnName, List<Operators> relationalOperators) {
		this.type = 1;
		
		this.columnName = columnName;
		this.relationalOperators = relationalOperators;
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
	
	public ConditionEntry(TableNames tableName, String columnName, List<Operators> relationalOperators) {
		this.type = 3;
		
		this.tableName = tableName;
		this.columnName = columnName;
		this.relationalOperators = relationalOperators;
	}
	
	
	public ConditionEntry(Operators logicalOperator, ConditionEntry ...nestedConditions) throws DataBaseException {
		this.type = 4;
		if(logicalOperator != Operators.AND && logicalOperator != Operators.OR) {
			throw new DataBaseException("Invalid logical operator");
		}
		this.logicalOperator = logicalOperator;
		this.nestedConditions = nestedConditions;
	}
	
	public ConditionEntry(Operators logicalOperator, List<ConditionEntry> nestedConditions) throws DataBaseException {
		this(logicalOperator, (ConditionEntry[]) nestedConditions.toArray());
	}
	
	public ConditionEntry negate() {
		this.negate = true;
		return this;
	}
	
	public ConditionEntry negate(boolean negate) {
		this.negate = negate;
		return this;
	}
	
	public ConditionEntry optional() {
		this.optional = true;
		return this;
	}
	
	public ConditionEntry optional(boolean optional) {
		this.optional = optional;
		return this;
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
	
	protected String joinConditions() throws DataBaseException {
		if(nestedConditions == null || nestedConditions.length == 0) {
			return "";
		}
		
		StringBuilder conditionString = new StringBuilder();
		
		boolean isFirstValue = true;
		for(final ConditionEntry nestedCondition : nestedConditions) {
			String nestedConditionString = nestedCondition.toQueryString();
			if(nestedConditionString.length() > 0) {
				if(!isFirstValue) {
					conditionString.append(logicalOperator.getOperator());
				}
				else {
					isFirstValue = false;
				}
				conditionString.append(nestedConditionString);
				conditionValues.addAll(nestedCondition.getConditionValues());
			}
		}
		return conditionString.toString();
	}
	
	public String toQueryString() throws DataBaseException {
		StringBuilder queryStringBuilder = new StringBuilder(negate? "NOT " : "");
		conditionValues = new ArrayList<>();
		switch(type) {
			case 0:
				if(columnName == null || relationalOperators == null || relationalOperators.isEmpty() || columnValue == null) {
					if(optional) {
						return "";
					}
					else {
						throw new DataBaseException("Syntax Error : Not valid condition");
					}
				}
				queryStringBuilder.append(columnName)
									 .append(joinOperators())
									 .append('?')
									 .append(' ');
				
				conditionValues.add(columnValue);
				break;
			case 1:
				if(columnName == null || relationalOperators == null || relationalOperators.isEmpty()) {
					if(optional) {
						return "";
					}
					else {
						throw new DataBaseException("Syntax Error : Not valid condition");
					}
				}
				queryStringBuilder.append(columnName)
									 .append(joinOperators())
									 .append(' ');
				
				break; 
			case 2:
				if(tableName == null || columnName == null || relationalOperators == null || relationalOperators.isEmpty() || columnValue == null) {
					if(optional) {
						return "";
					}
					else {
						throw new DataBaseException("Syntax Error : Not valid condition");
					}
				}
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
				if(tableName == null || columnName == null || relationalOperators == null || relationalOperators.isEmpty()) {
					if(optional) {
						return "";
					}
					else {
						throw new DataBaseException("Syntax Error : Not valid condition");
					}
				}
				queryStringBuilder.append(tableName.getTableName())
									 .append(columnName)
									 .append(joinOperators())
									 .append(' ');
				break;
			case 4:
				if(nestedConditions == null || nestedConditions.length == 0) {
					if(optional) {
						return "";
					}
					else {
						throw new DataBaseException("Syntax Error : Not valid condition");
					}
				}
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
