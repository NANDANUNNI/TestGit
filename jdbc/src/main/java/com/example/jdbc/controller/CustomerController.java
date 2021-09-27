package com.example.jdbc.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.jdbc.model.Customer;
import com.example.jdbc.service.CustomerService;



@RestController
public class CustomerController {

	@Autowired
    public CustomerService service;

    @GetMapping("/customers")
    public List<Customer> customerInformation() throws SQLException {
    	
        List<Customer> customers = service.findAllCustomers(); 
        
        return customers;        
    }
    
    @GetMapping("/customer/{custNo}")
    public Optional<Customer> oneCustomer(@PathVariable(value = "custNo") int custNo) throws SQLException  {
    	
    	Customer customer = service.findCustomerByNo(custNo);
    	System.out.println(customer);
    	return Optional.of(customer);  
    }
    
    @PostMapping("/customer/add")
    public Customer createCustomer(@RequestBody Customer customer) throws SQLException{
    	
    	return service.saveCustomer(customer);
    }
    
    @DeleteMapping("/customer/{custNo}")
    public ResponseEntity<HttpStatus>deleteOneCustomer(@PathVariable(value = "custNo") int custNo) throws DataAccessException, SQLException{
    	
    	return service.deleteCustomer(custNo);
    	
    }
    @PutMapping("/customer/{custNo}")
    public Customer editCustomer(@PathVariable(value = "custNo") int custNo ,@RequestBody Customer customer) throws SQLException  {
    	return service.updateCustomer(custNo , customer);
    }
    
}
