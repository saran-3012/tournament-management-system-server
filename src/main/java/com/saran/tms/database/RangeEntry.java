package com.saran.tms.database;

import java.util.ArrayList;
import java.util.List;

public class RangeEntry implements QueryEntry {
	protected Integer limit;
	protected Integer offset;
	
	protected List<Object> rangeValues;
	
	public RangeEntry() {}
	
	public RangeEntry(Integer limit) {
		this.limit = limit;
	}
	
	public RangeEntry(Integer limit, Integer offset) {
		this.limit = limit;
		this.offset = offset;
	}
	
	public RangeEntry setLimit(Integer limit) {
		this.limit = limit;
		return this;
	}
	
	public RangeEntry setOffset(Integer offset) {
		this.offset = offset;
		return this;
	}
	
	public String toQueryString() {
		StringBuilder queryStringBuilder = new StringBuilder();
		if(limit == null && offset == null) {
			return "";
		}
		rangeValues = new ArrayList<>();
		if(limit != null) {
			queryStringBuilder.append("LIMIT")
							  .append(' ')
							  .append('?')
							  .append(' ');
			rangeValues.add(limit);
			
		}
		if(offset != null) {
			queryStringBuilder.append("OFFSET")
							  .append(' ')
							  .append('?')
							  .append(' ');
			rangeValues.add(offset);
		}
		
		return queryStringBuilder.toString();
	}
	
	public List<Object> getRangeValues() {
		return rangeValues;
	}
}
