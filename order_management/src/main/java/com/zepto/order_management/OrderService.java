package com.zepto.order_management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zepto.exceptions.CustomerNotFound;
import com.zepto.exceptions.ProductNotFoundException;

public class OrderService 
{
	
	public void orderPlacing(int customerId,int productId,int quantity,int amount) throws SQLException, CustomerNotFound, ProductNotFoundException
	{
	String query1="select * from customers where id=?";
	String query2="select * from products where productId=?";
	
	//validating customers
	try(Connection con=DBConnection.createCon();)
	{
		con.setAutoCommit(false);
		
		try(PreparedStatement ps1=con.prepareStatement(query1))
		{
			ps1.setInt(1, customerId);
			
			try(ResultSet rs=ps1.executeQuery();)
			{
				boolean found=false;
				while(rs.next())
				{
					found=true;
					rs.getInt("id");
					System.out.println("Customer Exist");
				}
				
				if(!found)
				{
					throw new CustomerNotFound("Customer does Not exist");
				}
			}
		
			//validating product
			try(PreparedStatement ps2=con.prepareStatement(query2))
			{
				ps2.setInt(1, productId);
				
				try(ResultSet rs=ps2.executeQuery();)
				{
					boolean found=false;
					while(rs.next())
					{
						found=true;
						rs.getInt("productId");
						System.out.println("Product Found");
						
					}
					
					if(!found)
					{
						throw new ProductNotFoundException("Product Not Found");
					}
				}
			}
			
			//checking availible stock of the product
			String query3="select stock_Quantity from products where productId=?";
			int availibleStock=0;
			
			try(PreparedStatement ps3=con.prepareStatement(query3))
			{
				ps3.setInt(1, productId);
				
				try(ResultSet rs=ps3.executeQuery();)
				{
					
					while(rs.next())
					{
						
						availibleStock=rs.getInt("stock_Quantity");
						
					}
				
					
					if(quantity>availibleStock)
					{
						throw new IllegalArgumentException("Limited Stock Only --->  "+availibleStock);
					}
					
					
				}
			
			}
			
			
			//Reducing the product Stock
			String query4="update  products set stock_Quantity=stock_Quantity-? where productId=?";
			
			try(PreparedStatement ps4=con.prepareStatement(query4);)
			{
				ps4.setInt(1, quantity);
				ps4.setInt(2, productId);
				
				int rows=ps4.executeUpdate();
				
				if(rows>0)
				{
					System.out.println("Stock Updated Sucessfully ");
					System.out.println("Availible Stock : "+(availibleStock-quantity));
				}
				else
				{
					System.out.println("Stock not Updated");
				}
			}
			
			//Inserting the order
			String query5="INSERT INTO orders ( customerId, productId, Amount) values (?,?,?)";
		
			try(PreparedStatement ps5=con.prepareStatement(query5))
			{
				
				ps5.setInt(1, customerId);
				ps5.setInt(2, productId);
				ps5.setInt(3, amount);
				
				int rows=ps5.executeUpdate();
				if(rows>0)
				{
				System.out.println("Order Succesfully Added");
				}
				else
				{
					System.out.println("Order Failed");
				}
				
				
			}
			con.commit();
			System.out.println("Succesfully aPlaced Order");
			}
			
			
			catch (CustomerNotFound | ProductNotFoundException| IllegalArgumentException |SQLException e)
			{
				System.out.println(e.getMessage());
				con.rollback();
				throw e; //throw e sends the exception back to the calling method.
			
			}
		}
	
	}

}

