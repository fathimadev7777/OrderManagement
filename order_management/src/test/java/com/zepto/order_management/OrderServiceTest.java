package com.zepto.order_management;

//import  org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import com.zepto.exceptions.CustomerNotFoundException;
import com.zepto.exceptions.ProductNotFoundException;
public class OrderServiceTest
{
    OrderService orderService=new OrderService();

    @Test
void testCreateConnection() throws SQLException {

    Connection con = DBConnection.createCon();

    assertNotNull(con, "Database connection should not be null");

    assertTrue(con.isValid(5), "Database connection should be valid");

    assertFalse(con.isClosed(), "Database connection should be open");

    con.close();
}



    @Test
    void testingtOrderPlacingSuccess() throws Exception
     {

        assertDoesNotThrow(() ->
            orderService.orderPlacing(1, 3, 2, 2000)
        );
    }

    @Test
    void testingCustomerNotFound()
    {
        assertThrows(CustomerNotFoundException.class,()->orderService.orderPlacing(97687, 1, 2, 2000));

    }

  
    @Test
    void testingProductNotFound() {

        assertThrows(ProductNotFoundException.class, () ->
            orderService.orderPlacing(1, 123, 2, 2000)
        );
    }

    //  Insufficient stock
    @Test
    void testInsufficientStock() {

        assertThrows(IllegalArgumentException.class, () ->
            orderService.orderPlacing(1, 3, 100000, 2000)
        );
    }
     //  Verifying stock after successful order
    @Test
    void testStockReducedAfterOrder() throws Exception {

        int productId = 3;
        int quantity = 2;

        int beforeStockWas = getStock(productId);

        orderService.orderPlacing(1, productId=3, quantity=2, 2000);

        int afterStockIs = getStock(productId);

        assertEquals(beforeStockWas - quantity, afterStockIs);
    }

     // Verify stock is unchanged when customer is invalid
    @Test
    void testNoStockChangeWhenCustomerNotFound() throws Exception {

        int productId = 1;

        int beforeStock = getStock(productId);

        assertThrows(CustomerNotFoundException.class, () ->
            orderService.orderPlacing(999, productId, 2, 2000)
        );

        int afterStock = getStock(productId);

        assertEquals(beforeStock, afterStock);
    }
    
     
    private int getStock(int productId) throws SQLException {

        String query =
            "SELECT stock_Quantity FROM products WHERE productId=?";

        try (Connection con = DBConnection.createCon();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt("stock_Quantity");
                }
            }
        }

        throw new SQLException("Product not found");

}
    }