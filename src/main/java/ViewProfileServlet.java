import java.io.IOException;

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
		
		// Store all information in request object.
		req.setAttribute("user", user);
		
		// Forward control to profile.jsp
		req.getRequestDispatcher("/html/profile.jsp").forward(req, resp);
		
	}
	
}
