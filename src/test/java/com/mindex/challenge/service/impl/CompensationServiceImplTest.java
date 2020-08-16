package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {
	
	private String employeeUrl;
	private String compensationEmployeeIdUrl;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Before
	public void setup() {
		employeeUrl = "http://localhost:" + port + "/employee";
		compensationEmployeeIdUrl = "http://localhost:" + port + "/compensation/{id}";
	}
	
	@Test
	public void testCreateRead() {
		
		Employee testEmployee = new Employee();
		testEmployee.setFirstName("Rachel");
		testEmployee.setLastName("Harris");
		testEmployee.setDepartment("Engineering");
		testEmployee.setPosition("Developer");
		
		Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();
		
		//testing create
		Compensation testCompensation = new Compensation();
		testCompensation.setSalary("$25000");
		testCompensation.setEffectiveDate(new Date());
		
		Compensation createdCompensation = restTemplate.postForEntity(compensationEmployeeIdUrl, testCompensation, 
				Compensation.class, createdEmployee.getEmployeeId()).getBody();
		
		assertNotNull(createdCompensation.getEmployee());
		
		testCompensation.setEmployee(createdEmployee);
		assertCompensationEquivalence(testCompensation, createdCompensation);
		
		//testing read
		Compensation readCompensation = restTemplate.getForEntity(compensationEmployeeIdUrl, Compensation.class, 
				createdEmployee.getEmployeeId()).getBody();
		
		assertCompensationEquivalence(createdCompensation, readCompensation);
	}

	private void assertCompensationEquivalence(Compensation expected, Compensation actual) {
		assertEquals(expected.getEmployee().getEmployeeId(), actual.getEmployee().getEmployeeId());
		assertEquals(expected.getEmployee().getFirstName(), actual.getEmployee().getFirstName());
		assertEquals(expected.getEmployee().getLastName(), actual.getEmployee().getLastName());
		assertEquals(expected.getEmployee().getPosition(), actual.getEmployee().getPosition());
		assertEquals(expected.getSalary(), actual.getSalary());
		assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
	}
	

}
