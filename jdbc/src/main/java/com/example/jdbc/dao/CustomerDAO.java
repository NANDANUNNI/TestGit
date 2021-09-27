package com.example.jdbc.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.jdbc.model.Customer;


public interface CustomerDAO {
	List<Customer> getAllCustomers() throws SQLException  ;

	Customer getCustomerByNo(int custNo) throws SQLException;

	Customer save(Customer customer) throws SQLException;

	ResponseEntity<HttpStatus> delete(int custNo) throws DataAccessException, SQLException;

	Customer update(int custNo, Customer customer) throws SQLException;
	
	Connection getDBConnection() throws SQLException;

}