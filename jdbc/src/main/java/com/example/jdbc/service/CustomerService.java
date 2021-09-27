package com.example.jdbc.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.jdbc.model.Customer;


public interface CustomerService {

	List<Customer> findAllCustomers() throws SQLException ;

	Customer findCustomerByNo(int custNo) throws SQLException ;

	Customer saveCustomer(Customer customer) throws SQLException ;

	ResponseEntity<HttpStatus> deleteCustomer(int custNo) throws DataAccessException, SQLException;

	Customer updateCustomer(int custNo, Customer customer) throws SQLException;

}