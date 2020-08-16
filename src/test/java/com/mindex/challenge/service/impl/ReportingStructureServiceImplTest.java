package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {
	
	private String employeeUrl;
	private String reportingStructureEmployeeIdUrl;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;
	
	@Before
	public void Setup() {
		employeeUrl = "http://localhost:" + port + "employee";
		reportingStructureEmployeeIdUrl = "http://localhost:" + port + "reporting-structure/{id}";
	}
	
	@Test
	public void testRead() {
		Employee testEmployee1 = new Employee();
		testEmployee1.setFirstName("John");
		testEmployee1.setLastName("Smith");
		testEmployee1.setDepartment("Accounting");
		testEmployee1.setPosition("Developer");
		
		Employee testEmployee2 = new Employee();
		testEmployee2.setFirstName("Rachel");
		testEmployee2.setLastName("Jos");
		testEmployee2.setDepartment("Engineering");
		testEmployee2.setPosition("Developer");
		
		Employee testEmployee3 = new Employee();
		testEmployee3.setFirstName("Max");
		testEmployee3.setLastName("Willis");
		testEmployee3.setDepartment("Banking");
		testEmployee3.setPosition("Developer");
		
		Employee createdEmployee2 = restTemplate.postForEntity(employeeUrl, testEmployee2, Employee.class).getBody();
		Employee createdEmployee3 = restTemplate.postForEntity(employeeUrl, testEmployee3, Employee.class).getBody();
		
		List<Employee> directReports1 = new ArrayList<>();
		directReports1.add(createdEmployee2);
		directReports1.add(createdEmployee3);
		
		testEmployee1.setDirectReports(directReports1);
		
		
		Employee createdEmployee1 = restTemplate.postForEntity(employeeUrl, testEmployee1, Employee.class).getBody();
		
		ReportingStructure testReportingStructure = new ReportingStructure();
		testReportingStructure.setEmployee(createdEmployee1);
		testReportingStructure.setNumberOfReports(2);
		
		ReportingStructure createdReportingStructure = restTemplate.getForEntity(reportingStructureEmployeeIdUrl, ReportingStructure.class,
				createdEmployee1.getEmployeeId()).getBody();
		
		assertNotNull(createdReportingStructure.getEmployee());
		assertReportingStructureEquivalence(testReportingStructure, createdReportingStructure);
		
	}
	
	private static void assertReportingStructureEquivalence(ReportingStructure expected, ReportingStructure actual) {
		assertEquals(expected.getEmployee().getEmployeeId(), actual.getEmployee().getEmployeeId());
		assertEquals(expected.getEmployee().getFirstName(), actual.getEmployee().getFirstName());
		assertEquals(expected.getEmployee().getLastName(), actual.getEmployee().getLastName());
		assertEquals(expected.getEmployee().getPosition(), actual.getEmployee().getPosition());
		assertEquals(expected.getEmployee().getDirectReports().get(0).getEmployeeId(), 
				actual.getEmployee().getDirectReports().get(0).getEmployeeId());
		assertEquals(expected.getEmployee().getDirectReports().get(1).getEmployeeId(), 
				actual.getEmployee().getDirectReports().get(1).getEmployeeId());
		assertEquals(expected.getNumberOfReports(), actual.getNumberOfReports());
	}
}
