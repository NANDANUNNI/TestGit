package com.example.jdbc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.jdbc.controller.CustomerController;
import com.example.jdbc.model.Customer;
import com.example.jdbc.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTests {
	
	
	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper mapper;
	
	@MockBean
	CustomerService service;
	
	
	Customer INFO_1 = new Customer(1 , "Amar" , "India");
	Customer INFO_2 = new Customer(2 , "Korbin" , "USA");
 
	//Testing Get all
	
	@Test
	public void customerInformationTest() throws Exception {
		
		List<Customer> infoCustomers = new ArrayList<>(Arrays.asList(INFO_1, INFO_2));
		
		when(service.findAllCustomers()).thenReturn(infoCustomers);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/customers")
				.contentType(MediaType.APPLICATION_JSON))
		
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].custName", is("Amar")))
				.andExpect(jsonPath("$", hasSize(2)));
		
	}
	
	//Testing GET one
	
	@Test
	public void oneCustomerTest() throws Exception {

		when(service.findCustomerByNo(INFO_1.getCustNo())).thenReturn(INFO_1);

		mockMvc.perform(MockMvcRequestBuilders.get("/customer/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.custName", is("Amar")));
	}
	
	//Testing POST
	
	@Test
	public void createCustomerTest() throws Exception {

		Customer info = new Customer(3 ,"Albin" , "UK");

		when(service.saveCustomer(ArgumentMatchers.any())).thenReturn(info);

		
		String jsonString = mapper.writeValueAsString(info);

		mockMvc.perform(MockMvcRequestBuilders.post("/customer/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString).accept(MediaType.APPLICATION_JSON))

				.andExpect(status().isOk())
				.andExpect(jsonPath("$.custNo", Matchers.equalTo(3)))
				.andExpect(jsonPath("$.custName", is("Albin")));

	}
	
	//Testing DELETE
	
	@Test
	public void deleteOneCustomerTest() throws Exception {

		Customer info = new Customer(3 ,"Albin" , "UK");

		
		when(service.deleteCustomer(3)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

		String jsonString = mapper.writeValueAsString(info);

		mockMvc.perform(MockMvcRequestBuilders.delete("/customer/3")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString).accept(MediaType.APPLICATION_JSON))

				.andExpect(status().isOk());

	}
	
	//Testing PUT
	
	@Test
	public void editCustomerTest() throws Exception {

		Customer info = new Customer(3 ,"Albin" , "UK");
		
		
		when(service.updateCustomer(ArgumentMatchers.anyInt() , ArgumentMatchers.any())).thenReturn(info);

		
		String jsonString = mapper.writeValueAsString(info);

		mockMvc.perform(MockMvcRequestBuilders.put("/customer/3")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString).accept(MediaType.APPLICATION_JSON))

				.andExpect(status().isOk())
				.andExpect(jsonPath("$.custNo", Matchers.equalTo(3)))
				.andExpect(jsonPath("$.custName", is("Albin")));

	}
	
	

}
