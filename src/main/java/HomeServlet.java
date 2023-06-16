import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DBConnection;

// Purpose of this servlet is to forward the control to the index.html
// Purpose of creating this servlet is that we don't want to access our index.html as a static resource.
public class HomeServlet extends HttpServlet{
	
	// Lifecycle methods - init, service, destroy
	public Connection connection = null;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("in doGET method");
		
		// Forward the control to the index.html
		req.getRequestDispatcher("/html/index.html").forward(req, resp);
	}

	@Override
	public void destroy() {
		// Clean up activity - close DB connection object.
		System.out.println("in destroy method");
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init() throws ServletException {
		// Initialization activity - set up DB connection object.
		System.out.println("in init method");
		connection = DBConnection.getConnectionDatabase();
	}
	
	
}
