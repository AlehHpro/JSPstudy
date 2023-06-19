package listeners;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import dao.DBConnection;

@WebListener
public class ApplicationListener implements ServletContextListener{

	// sce - servlet context event object.
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("I'm in contextDestroyed method ");
		Connection connection = (Connection)sce.getServletContext().getAttribute("dbconnection");
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// Obtain the DB connection object.
		System.out.println("I'm in contextInitialized method ");
		Connection connection = DBConnection.getConnectionDatabase();
		
		// Store the connection object in ServletContext as an attribute.
		sce.getServletContext().setAttribute("dbconnection", connection);
		
	}
	

}
