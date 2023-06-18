import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ApplicationDao;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Dispatch the request to login.jsp resource (sole objective is to reach the login.jsp)
		RequestDispatcher dispatcher = req.getRequestDispatcher("/html/login.jsp");
		// Forwarding control to login.jsp file
		dispatcher.forward(req, resp);
		// 2-nd. option. Combines the response of LoginServlet and the response of login.jsp and renders it to the client.
		//dispatcher.include(req, resp);
	}

	// Using URL rewriting.
	// Store user information and forward control to home.jsp.
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get the username from the login form.
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		// Call DAO for validation logic.
		ApplicationDao dao = new ApplicationDao();
		boolean isValidUser = dao.validateUser(username, password);
		
		// Check if user is invalid and set up an error message.
		if(isValidUser) {
			// Set up the HTTP session.
		HttpSession session = req.getSession();
		
		// Set the username as an attribute.
		session.setAttribute("username", username);
		
		// Forward to home.jsp.
		req.getRequestDispatcher("/html/home.jsp").forward(req, resp);
		
		} else {
			String errorMessage = "Invalid credentials, please login again!";
			req.setAttribute("error", errorMessage);
			req.getRequestDispatcher("/html/login.jsp").forward(req, resp);
		} 
	
	}
	
	
	
}
