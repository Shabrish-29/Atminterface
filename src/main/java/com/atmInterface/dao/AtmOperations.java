package com.atmInterface.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.atmInterface.exception.InvalidAmountException;



public class AtmOperations {
	
	public static boolean enter(long accountnumber , String pin) throws ClassNotFoundException, SQLException
	{
		try
		{
		Connection connection= MysqlDBConnection.dbconnect();
		PreparedStatement statement=connection.prepareStatement("select * from atmdb where accountnumber=?");
		statement.setLong(1,  accountnumber);
		ResultSet result=statement.executeQuery();
		if(result.next())
		{
		
			if(result.getString("pin").equals(pin))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("Something went wrong!! "); 
		}
		catch(SQLException e)
		{
			System.out.println("Pin is incorrect!! "); 
		}
		return false;
		
		
	}
	
	public static double balanceCheck(long pinNumber) throws ClassNotFoundException, SQLException
	{  
		double balance;
		try
		{
		Connection connection= MysqlDBConnection.dbconnect();
		PreparedStatement statement=connection.prepareStatement("select * from atmdb where pin=?");		
		statement.setLong(1, pinNumber);
		ResultSet result=statement.executeQuery();
		result.next();
		balance=result.getDouble("balance");
		return balance;
		
		}
		catch(SQLException e)
		{
			System.out.println("Wrong password!!");
		}
		catch(Exception e)
		{
			System.out.println("Something went wrong!!");
		}
		return 0;
	}

	
	public static double withdraw(long pinNumber, double withdrawalAmount) throws ClassNotFoundException, SQLException, InvalidAmountException
	{
		Connection connection= MysqlDBConnection.dbconnect();
		PreparedStatement statement=connection.prepareStatement("select * from atmdb where pin=?");
		statement.setLong(1, pinNumber);
		
		ResultSet result=statement.executeQuery();
		result.next();
		double availableBalance=result.getDouble("balance");
		
		if(withdrawalAmount<availableBalance)
		{
		   double remainingBalance=availableBalance-withdrawalAmount;
		   statement=connection.prepareStatement("update atmdb set balance=? where pin=?");
		   statement.setDouble(1, new Double(remainingBalance));
		   statement.setLong(2, pinNumber);
		   
		   if(statement.executeUpdate()>0)
		   {
			   return remainingBalance;  
		   }
		   else
		   {
			   return 0;
		   }
		}
		else
		{
			throw new InvalidAmountException("Invalid Withdrawal amount!!");
		}
	}



	public static double deposit(long pinNumber, double depositAmount) throws ClassNotFoundException, SQLException
	{
		Connection connection= MysqlDBConnection.dbconnect();
		PreparedStatement statement=connection.prepareStatement("select * from atmdb where pin=?");
		statement.setLong(1, pinNumber);
		
		ResultSet result=statement.executeQuery();
		result.next();
		double avilableBalance=result.getDouble("balance");
		double newBalance=avilableBalance+depositAmount;
	
		statement=connection.prepareStatement("update atmdb set balance=? where pin=?");
		statement.setDouble(1, new Double(newBalance));
		statement.setLong(2, pinNumber);
		   
		   if(statement.executeUpdate()>0)
		   {
			   return newBalance;  
		   }
		   else
		   {
			   return 0;
	       }
	
   }
	
	public static ResultSet checkAccountInfo(long pinNumber) throws ClassNotFoundException, SQLException
	{
		Connection connection= MysqlDBConnection.dbconnect();
		PreparedStatement statement=connection.prepareStatement("select * from atmdb where pin=?");
		statement.setLong(1, pinNumber);
		
		ResultSet result=statement.executeQuery();
		if(result.next())
		{
			return result;
		}
		else
		{
			return null;
		}
	}

	
	
	

}