package com.atmInterface;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;


import org.junit.jupiter.api.Test;

import com.atmInterface.dao.AtmOperations;
import com.atmInterface.exception.InvalidAmountException;

public class WithdrawModuleTest {
	
	@Test
	public void testCase1() throws ClassNotFoundException, SQLException, InvalidAmountException
	{
		
		assertEquals(5000, AtmOperations.withdraw(1562, 50000));
	
		
	}
}

	