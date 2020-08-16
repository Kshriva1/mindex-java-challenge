package com.mindex.challenge.data;

import java.util.Date;

public class CompensationParts {
	
	private String salary;
	private Date effectiveDate;
	
	public CompensationParts() {
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
}
