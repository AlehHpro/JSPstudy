package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import beans.Product;
import beans.User;

/**
 * Method searchProducts returns the list of products.
 * @author Aleh_Hutyrchyk
 *
 */
public class ApplicationDao {
	public List<Product> searchProducts(String searchString) {
		// Products are encapsulated into Products bean to stick with OOP. 
		Product product = null;
		List<Product> products = new ArrayList<>();

		try {
			// Get connection from DBConnection class.
			Connection connection = DBConnection.getConnectionDatabase();
			// Query for searching products.
			String sql = "select * from products where product_name like '%" + searchString + "%'";
			
			Statement statement = connection.createStatement();
			// Put all rows into ResultSet.
			ResultSet set = statement.executeQuery(sql);
			
			// Each row of the result set is added to the product object which is added to the products list.
			while (set.next()) {
				product = new Product();
				product.setProductId(set.getInt("product_id"));
				product.setProductImgPath(set.getString("image_path"));
				product.setProductName(set.getString("product_name"));
				products.add(product);
			}
		} 
		catch (SQLException e) {
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
	
}
