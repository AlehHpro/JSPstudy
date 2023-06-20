import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Product;
import dao.ApplicationDao;

@WebServlet("/addProducts")
public class ProductsServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get the HTTPSession object
		HttpSession session = req.getSession();
		
		// Create a cart as ArrayList for the user
		List<String> cart = (ArrayList<String>)session.getAttribute("cart");
		// If user is here for the first time we create new cart
		if(cart == null) {
			cart = new ArrayList<>();
		}
		
		// Add the selected product to the cart
		if(req.getParameter("product") != null) {
			cart.add(req.getParameter("product"));
		} 
		// Set the list to the session object as an attribute 
		session.setAttribute("cart", cart);
		
		// Get search criteria from search servlet
		String search = (String)session.getAttribute("search");
		Connection connection = (Connection)getServletContext().getAttribute("dbconnection");
		
		// Get the search results from DAO
		ApplicationDao dao = new ApplicationDao();
		List<Product> products = dao.searchProducts(search, connection);
		
		// Set the search results in request scope
		req.setAttribute("products", products);
		
		// Forward to searchResults.jsp
		req.getRequestDispatcher("/html/searchResults.jsp").forward(req, resp);
		
	}
	
}
