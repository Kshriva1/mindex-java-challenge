package com.mindex.challenge.service;

import java.util.Date;

import com.mindex.challenge.data.Compensation;

public interface CompensationService {
	
	Compensation create(String id, String salary, Date effectiveDate, String port);
	Compensation read(String id, String port);
}
