package com.EmployeeData;


import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.EmployeeData.controller.EmployeeController;
import com.EmployeeData.model.Employee;
import com.EmployeeData.repository.EmployeeRepository;
import com.EmployeeData.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(EmployeeController.class)
@AutoConfigureMockMvc(addFilters = false)
class EmployeeControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService employeeService;
	
	@MockBean
	private EmployeeRepository employeeRepository;
	
	@Test
	public void saveEmployee() throws Exception {
		
		Employee employee = new Employee();
		employee.setId(1);
		employee.setFirstName("Jayant");
		employee.setLastName("Singh");
		employee.setEmail("Jayant@gmail.com");
		employee.setDepartmentName("HR");
		employee.setMobile("8976578934");
		employee.setAge(23);
		
		employeeService.saveEmployee(employee);
		System.out.println(employee.getId());
		
		Mockito.when(employeeService.saveEmployee(employee)).thenReturn(employee);
		
		Mockito.verify(employeeService).saveEmployee(employee);
		
		String url = "http://localhost:8080/api/employees";
		
		mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(employee)))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void getAllEmployees() throws Exception {
		List<Employee> empList = Arrays.asList(new Employee(1, "Jayant", "Singh", "Jayant@gmail.com", "HR", "8976578934", 23));
		
		Mockito.when(employeeService.getAllEmployees()).thenReturn(empList);
		
		String url = "http://localhost:8080/api/employees";
		
		mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
		       .andExpect(status().isOk())
		       .andExpect(jsonPath("$[0].firstName").value("Jayant"));
	}
	
	@Test
	public void getEmployeeById() throws Exception {
		
		Employee employee = new Employee(1, "Jayant", "Singh", "Jayant@gmail.com", "HR", "8976578934", 23);
		
		Mockito.when(employeeService.getEmployeeById(1)).thenReturn(employee);
		
		
		
		mockMvc.perform(get("http://localhost:8080/api/employees/{id}",1).contentType(MediaType.APPLICATION_JSON))
	       .andExpect(status().isOk())
	       .andExpect(jsonPath("$.firstName").value("Jayant"));
	}
	
//	@Test
//	public void deleteEmployee() throws Exception {
//		
//		Employee employee = new Employee(1, "Jayant", "Singh", "Jayant@gmail.com", "HR", "8976578934", 23);
//		
//		doNothing().when(employeeService).deleteEmployee(1);
//        mockMvc.perform(delete("http://localhost:8080/api/employees").andExpect(status().isOk();
//	}
}
