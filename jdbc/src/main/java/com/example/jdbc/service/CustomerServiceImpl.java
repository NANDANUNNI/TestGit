package com.example.jdbc.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.jdbc.dao.CustomerDAO;
import com.example.jdbc.model.Customer;



@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	CustomerDAO dao;
	
	@Override
	public List<Customer> findAllCustomers() throws SQLException {
		
		return dao.getAllCustomers();
	}


	@Override
	public Customer findCustomerByNo(int custNo) throws SQLException  {
		
		return dao.getCustomerByNo(custNo);
	}


	@Override
	public Customer saveCustomer(Customer customer) throws SQLException  {
	
		return dao.save(customer);
	}


	@Override
	public ResponseEntity<HttpStatus> deleteCustomer(int custNo) throws DataAccessException, SQLException  {
		
		return dao.delete(custNo);
	}


	@Override
	public Customer updateCustomer(int custNo, Customer customer) throws SQLException  {
		
		return dao.update(custNo , customer);
	}



	

}
