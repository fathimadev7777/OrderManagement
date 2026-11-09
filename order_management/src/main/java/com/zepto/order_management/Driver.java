package com.zepto.order_management;

import java.sql.SQLException;

import com.zepto.exceptions.CustomerNotFoundException;
import com.zepto.exceptions.ProductNotFoundException;

public class Driver 
{
	public static void main(String[] args) throws SQLException, CustomerNotFoundException, ProductNotFoundException {
		
	
	OrderService orderService=new OrderService();
	
	try
	{
		orderService.orderPlacing(4, 10, 1, 8500);
	}
	catch (CustomerNotFoundException |ProductNotFoundException | IllegalArgumentException| SQLException e) 
	{
		System.out.println(e.getMessage());
	}
	

}
}