package com.example.jdbc.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CustomerMapper implements RowMapper<Customer> {

	@Override
	public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {


		Customer customer = new Customer();
		
		customer.setCustNo(rs.getInt("custNo"));
		customer.setCustName(rs.getString("custName"));
		customer.setCountry(rs.getString("country"));
		
		return customer;
		
	}
	
	
	

}
