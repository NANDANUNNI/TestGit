package com.example.jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.example.jdbc.model.Customer;
import com.example.jdbc.model.CustomerMapper;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

//	 @Autowired
//	 private DataSource dataSource;

	private Connection connection;
	private PreparedStatement statement;
	private ResultSet resultSet;

	private final String SQL1 = "select * from customer";
	private final String SQL2 = "select * from customer where custNo = ?";
	private final String SQL3 = "insert into customer(custNo , custName , country) values( ? , ? , ? )";
	private final String SQL4 = "delete from customer where custNo = ?";
	private final String SQL5 = "update customer set custName = ? , country = ? where custNo = ?";

	@Override
	public Connection getDBConnection() throws SQLException {
		try {
			Connection conn =DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "1211") ;
			return conn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

//		return dataSource.getConnection();
	}

	@Override
	public List<Customer> getAllCustomers() throws SQLException {

		CustomerMapper customerMapper = new CustomerMapper();

		List<Customer> customers = null;

		try {
			connection = getDBConnection();
			statement = connection.prepareStatement(SQL1);
			resultSet = statement.executeQuery();
			System.out.println(resultSet);
			customers = new ArrayList<>();
			while (resultSet.next()) {
				customers.add(customerMapper.mapRow(resultSet, 3));

			}
		} finally {
			try {
				connection.close();
				statement.close();
				resultSet.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

		return customers;

	}

	@Override
	public Customer getCustomerByNo(int custNo) throws SQLException {

		Customer customer = null;
		CustomerMapper customerMapper = new CustomerMapper();

		try {
			connection = getDBConnection();
			statement = connection.prepareStatement(SQL2);
			statement.setInt(1, custNo);

			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				customer = customerMapper.mapRow(resultSet, 3);
			}
			
		} finally {

			try {
				connection.close();
				statement.close();
				resultSet.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}

		}

		return customer;

	}

	@Override
	public Customer save(Customer customer) throws SQLException {

		try {
			connection = getDBConnection();
			statement = connection.prepareStatement(SQL3);
			statement.setInt(1, customer.getCustNo());
			statement.setString(2, customer.getCustName());
			statement.setString(3, customer.getCountry());
			statement.executeUpdate();
		} finally {
			try {
				connection.close();
				statement.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

		return getCustomerByNo(customer.getCustNo());
	}

	@Override
	public ResponseEntity<HttpStatus> delete(int custNo) throws DataAccessException, SQLException {

		
		if (getCustomerByNo(custNo) != null) {
			try {
				connection = getDBConnection();
				statement = connection.prepareStatement(SQL4);
				statement.setInt(1, custNo);
				statement.executeUpdate();
			} finally {
				try {
					connection.close();
					statement.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}

			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Customer update(int custNo, Customer customer) throws SQLException {

		try {
			connection = getDBConnection();
			statement = connection.prepareStatement(SQL5);
			statement.setString(1, customer.getCustName());
			statement.setString(2, customer.getCountry());
			statement.setInt(3, custNo);
			statement.executeUpdate();
		} finally {
			try {
				connection.close();
				statement.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

		return getCustomerByNo(custNo);
	}

}
