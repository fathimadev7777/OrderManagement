                                     Order Management System – JDBC
                                     
This project implements an Order Management System using Java and JDBC with MySQL database integration.
The application manages the order placement process by validating customers and products, checking product availability, reducing stock quantities, and inserting order details into the database.

 Key Features
- Customer Validation: Checks whether the customer exists before placing an order.
- Product Validation: Verifies product availability in the database.
- Stock Validation: Checks whether sufficient stock is available for the requested quantity.
- Inventory Management: Automatically updates product stock after successful order placement.
- Order Management: Inserts order details into the orders table.
- Transaction Management: Uses JDBC transactions with `setAutoCommit(false)`, `commit()`, and `rollback()` to maintain database consistency.
- Exception Handling: Handles customer not found, product not found, insufficient stock, and SQL exceptions.

Technologies i have  Used in this small project
- Java
- JDBC
- MySQL
- SQL
- Maven

Concepts Practiced
- PreparedStatement
- ResultSet
- JDBC Transaction Management
- Commit and Rollback
- Try-with-resources
- Custom Exceptions
- Database Operations (SELECT, UPDATE, INSERT)

  Preparing this for upcomming my new project based on e-commerce 
