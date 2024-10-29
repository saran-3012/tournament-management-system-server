package com.saran.tms.models;

public class AggregateModel implements Model {
	
	private Long count;
	private Long sum;
	private Long min;
	private Long max;
	private Double avg;
	
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public Long getSum() {
		return sum;
	}
	public void setSum(Long sum) {
		this.sum = sum;
	}
	public Long getMin() {
		return min;
	}
	public void setMin(Long min) {
		this.min = min;
	}
	public Long getMax() {
		return max;
	}
	public void setMax(Long max) {
		this.max = max;
	}
	public Double getAvg() {
		return avg;
	}
	public void setAvg(Double avg) {
		this.avg = avg;
	}
	@Override
	public String toString() {
		return "AggregateModel [count=" + count + ", sum=" + sum + ", min=" + min + ", max=" + max + ", avg=" + avg
				+ "]";
	}
	
}
