package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Statement;

import beans.Order;
import beans.Product;
import beans.User;

/**
 * Method searchProducts returns the list of products.
 * 
 * @author Aleh_Hutyrchyk
 *
 */
public class ApplicationDao {
	public List<Product> searchProducts(String searchString, Connection connection) {
		// Products are encapsulated into Products bean to stick with OOP.
		Product product = null;
		List<Product> products = new ArrayList<>();

		try {
			// Get connection from DBConnection class.
			//Connection connection = DBConnection.getConnectionDatabase();
			// Query for searching products.
			String sql = "select * from products where product_name like '%" + searchString + "%'";

			Statement statement = connection.createStatement();
			// Put all rows into ResultSet.
			ResultSet set = statement.executeQuery(sql);

			// Each row of the result set is added to the product object which is added to
			// the products list.
			while (set.next()) {
				product = new Product();
				product.setProductId(set.getInt("product_id"));
				product.setProductImgPath(set.getString("image_path"));
				product.setProductName(set.getString("product_name"));
				products.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}

	public int registerUser(User user) {
		int rowsAffected = 0;

		try {
			// Get the connection for the DB.
			Connection connection = DBConnection.getConnectionDatabase();

			// Write the insert query.
			String insertQuery = "insert into users values(?,?,?,?,?,?)";

			// Set parameters with PreparedStatement.
			java.sql.PreparedStatement statement = connection.prepareStatement(insertQuery);
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getFirstName());
			statement.setString(4, user.getLastName());
			statement.setInt(5, user.getAge());
			statement.setString(6, user.getActivity());

			// Execute the statement.
			rowsAffected = statement.executeUpdate();

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return rowsAffected;
	}

	// User validation in a servlet
	public boolean validateUser(String username, String password) {
		boolean isValidUser = false;

		try {
			// Get the connection for the DB
			Connection connection = DBConnection.getConnectionDatabase();

			// Write the select query
			String sql = "select * from users where username = ? and password = ?";

			// Set parameters with PreparedStatement
			java.sql.PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, password);

			// Execute the statement and check whether the user exists
			ResultSet set = statement.executeQuery();
			while (set.next()) {
				isValidUser = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return isValidUser;
	}
	
	// Method to get user profile details.
	public User getProfileDetails(String username){
		User user = null;
		try {
		// Get connection to DB.
		Connection connection = DBConnection.getConnectionDatabase();
		
		// Write select query to get profile details.
		String sql = "select * from users where username = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, username);
		
		// Execute query, get resultset and return user info.
		ResultSet set = statement.executeQuery();
		while(set.next()) {
			System.out.println("FOUND USER!!!!!");
			user = new User();
			user.setUsername(set.getString("username"));
			user.setFirstName("first_name");
			user.setLastName("last_name");
			user.setActivity("activity");
			user.setAge(set.getInt("age"));
		}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	// Getting order details for the user (for JSTL demo).
	public List<Order> getOrders(String username){
		Order order = null;
		List<Order> orders = new ArrayList<>();
		try {
			// Get connection to DB.
			Connection connection = new DBConnection().getConnectionDatabase();
			
			// Write select query to get order details.
			String sql = "select * from orders where user_name = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			
			// Execute query, get resultset and return User info.
			ResultSet set = statement.executeQuery();
			while(set.next()) {
				
				order = new Order();
				order.setOrderId(set.getInt("orderId"));
				order.setProductName(set.getString("product_name"));
				order.setProductImgPath(set.getString("image_path"));
				order.setOrderDate(new Date(set.getDate("order_date").getTime()));
				order.setUsername(set.getString("user_name"));
				orders.add(order);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return orders;
	}

}
