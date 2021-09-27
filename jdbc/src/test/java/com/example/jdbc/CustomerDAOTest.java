package com.example.jdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.example.jdbc.dao.CustomerDAOImpl;
import com.example.jdbc.model.Customer;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ DriverManager.class, CustomerDAOImpl.class })
public class CustomerDAOTest {

	CustomerDAOImpl customerDAO = new CustomerDAOImpl();
	
	

	@Before
	public void setup() throws SQLException {

		Connection connection = mock(Connection.class);
		mockStatic(DriverManager.class);

		PowerMockito.when(DriverManager.getConnection(anyString(), anyString(), anyString())).thenReturn(connection);

		assertEquals(connection, customerDAO.getDBConnection());

	}

	@Test
	void testGetDBConnection() throws SQLException {
		customerDAO.getDBConnection();
	}

	@Test
	void testGetAllCustomers() throws SQLException {
		customerDAO.getAllCustomers();
	}

	@Test
	void testGetCustomerByNo() throws SQLException {
		customerDAO.getCustomerByNo(0);
	}

	@Test
	void testDelete() throws SQLException {
		customerDAO.delete(9);
	}
	@Test()
	void testSave() throws SQLException {
		Customer customer =new Customer(9, "fdsfs" , "dffsa");

//		Connection c = mock(Connection.class);
//		PreparedStatement preparedStatement = mock(PreparedStatement.class);
//		
//		
//		
//		CustomerDAOImpl cus = spy(customerDAO);
//		doReturn(c).when(cus).getDBConnection();
//		
//		
//		doReturn(preparedStatement).when(c).prepareStatement("fdsagag");
//		doReturn(1).when(preparedStatement).executeUpdate();
//
//		doReturn(customer).when(cus).getCustomerByNo(0);
		assertEquals(customer.getCustNo(), customerDAO.save(customer).getCustNo());
	}

	

	@Test
	void testUpdate() throws SQLException {
		Customer customer = new Customer();
		customerDAO.update(ArgumentMatchers.anyInt(), customer);
	}

}
