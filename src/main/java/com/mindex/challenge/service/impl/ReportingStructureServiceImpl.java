package com.mindex.challenge.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {
	
	private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);
	
	@Autowired
	private RestTemplate restTemplate;
	 
	private String url = "http://localhost:";
	
	 
	@Override
	public ReportingStructure read(String id, String port) {
		LOG.debug("Creating reporting structure with employee id [{}]", id);
		Employee employee = restTemplate.getForEntity(url + port + "/employee/{id}", Employee.class, id).getBody();
		
		int numberOfReports = 0;
		if(employee.getDirectReports() != null) {
		  numberOfReports = getNumberOfReports(employee.getEmployeeId(),numberOfReports,port);	
		}
		
		ReportingStructure reportingStructure = new ReportingStructure();
		reportingStructure.setEmployee(employee);
		reportingStructure.setNumberOfReports(numberOfReports);
		
		return reportingStructure;
		
	}
	
	//this method must be private as it is an implementation detail
	private int getNumberOfReports(String id, int numberOfReports,String port) {
		
		Employee employee = restTemplate.getForEntity(url + port + "/employee/{id}", Employee.class, id).getBody();
		
		 if(employee.getDirectReports() == null) return numberOfReports;
		
			for(Employee reportingEmployee: employee.getDirectReports()) {
				numberOfReports++;
				numberOfReports = getNumberOfReports(reportingEmployee.getEmployeeId(), numberOfReports, port);
			}
		return numberOfReports;
	}


}
