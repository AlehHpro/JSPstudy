import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Product;
import beans.User;
import dao.ApplicationDao;

@WebServlet("/registerUser")
public class RegisterUserServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Collect all form data.
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String firstName = req.getParameter("fname");
		String lastName = req.getParameter("lname");
		String activity = req.getParameter("activity");
		int age = Integer.parseInt(req.getParameter("age"));
		
		// Fill it up in a User bean.
		User user = new User(username, password, firstName, lastName, age, activity);
		
		// Call DAO layer and save the user object to DB.
		ApplicationDao dao = new ApplicationDao();
		int rows = dao.registerUser(user);
		
		// Prepare an information message for user about the success or failure of the operation.
		String infoMessage = null;
		if(rows == 0) {
			infoMessage = "Sorry, an error occured!";
		} else {
			infoMessage = "User was registered successfully!";
		}
		
		// Write the message back to the page in client browser.
		String page = getHTMLString(req.getServletContext().getRealPath("/html/register.html"), infoMessage);
		resp.getWriter().write(page);
		
	}
	
	public String getHTMLString(String filePath, String message) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		String line = "";
		StringBuffer buffer = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		
		reader.close();
		String page = buffer.toString();
		
		page = MessageFormat.format(page, message);
		
		return page;
	}
	
	// Method sole objective is to get us to the register.html file.
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String page = getHTMLString(req.getServletContext().getRealPath("/html/register.html"), "");
		resp.getWriter().write(page);
	}
	
	
}
