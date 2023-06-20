import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.User;
import dao.ApplicationDao;

// Session information is fetched here for verification (from the LoginServlet which should give us the appropriate username for the session)
@WebServlet("/getProfileDetails")
public class ViewProfileServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get the username from the session
		System.out.println("Username in profile servlet :" + req.getSession().getAttribute("username"));
		String username = (String)req.getSession().getAttribute("username");
		
		// Call DAO and get profile details.
		ApplicationDao dao = new ApplicationDao();
		User user = dao.getProfileDetails(username);
		
		// For "weightSummary" table in profile.jsp. (Creating new collection object).
		Map<String, Double> weightSummary = new HashMap<>();
		weightSummary.put("January", 67.9);
		weightSummary.put("Febuary", 65.9);
		weightSummary.put("March", 64.8);
		
		// Store all information in request object.
		req.setAttribute("user", user);
		req.setAttribute("weightSummary", weightSummary);
		
		// Forward control to profile.jsp
		req.getRequestDispatcher("/html/profile.jsp").forward(req, resp);
		
	}
	
}
