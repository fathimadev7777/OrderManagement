package com.zepto.order_management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection 
{
	public static void main(String[] args) throws SQLException 
	{
		
	createCon();
	}
	
	public static  Connection createCon() throws SQLException
	{
	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/walmart", "root", "@Fathima9008");
	return con;
	}
}
