package com.saran.tms.database;

import java.util.List;

import com.saran.tms.enums.JoinTypes;
import com.saran.tms.enums.Operators;
import com.saran.tms.enums.TableNames;
import com.saran.tms.exceptions.DataBaseException;

public class JoinEntry implements QueryEntry {
	protected TableNames tableName1;
	protected String columnName1;
	protected TableNames tableName2;
	protected String columnName2;
	protected JoinTypes joinType;
	
	protected Operators logicalOperator;
	protected ConditionEntry conditionEntry;
	
	protected short type;
	
	public JoinEntry(TableNames tableName1, String columnName1, TableNames tableName2, String columnName2) {
		this.type = 0;
		
		this.tableName1 = tableName1;
		this.columnName1 = columnName1;
		this.tableName2 = tableName2;
		this.columnName2 = columnName2;
		this.joinType = JoinTypes.JOIN;
	}
	
	public JoinEntry(TableNames tableName1, String columnName1, TableNames tableName2, String columnName2, JoinTypes joinType) {
		this.type = 0;
		
		this.tableName1 = tableName1;
		this.columnName1 = columnName1;
		this.tableName2 = tableName2;
		this.columnName2 = columnName2;
		this.joinType = joinType;
	}
	
	public JoinEntry(Operators logicalOperator, ConditionEntry conditionEntry) throws DataBaseException {
		this.type = 1;
		if(logicalOperator != Operators.AND && logicalOperator != Operators.OR) {
			throw new DataBaseException("Invalid logical operator");
		}
		this.logicalOperator = logicalOperator;
		this.conditionEntry = conditionEntry;
	}
	
	public String toQueryString() throws DataBaseException {
		StringBuilder queryStringBuilder = new StringBuilder();
		switch(type) {
			case 0:
				queryStringBuilder.append(joinType.getJoinType())
								  .append(' ')
								  .append(tableName2.getTableName())
								  .append(' ').append("ON").append(' ')
								  .append(tableName1.getTableName())
								  .append('.')
								  .append(columnName1)
								  .append(' ').append('=').append(' ')
								  .append(tableName2.getTableName())
								  .append('.')
								  .append(columnName2)
								  .append(' ');
				
				break;
			case 1:
				String conditionString = conditionEntry.toQueryString();
				if(conditionString.length() > 0) {
					queryStringBuilder.append(logicalOperator.getOperator())
									  .append(' ')
									  .append(conditionString);
				}
				break;
			default:;
		}
		
		return queryStringBuilder.toString();
	}
	
	public List<Object> getConditionValues() {
		return conditionEntry.getConditionValues();
	}
	
}
