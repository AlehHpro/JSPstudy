import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Order;
import dao.ApplicationDao;

/*
 * Servlet for JSTL Demo.
 */
@WebServlet("/orderHistory")
public class OrderHistory extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get username from session.
		String username = (String) req.getSession().getAttribute("username");

		// Call dao and get order history.
		ApplicationDao dao = new ApplicationDao();
		List<Order> orders = dao.getOrders(username);

		// Set order data in request.
		req.setAttribute("orders", orders);

		// Forward to home.jsp.
		req.getRequestDispatcher("/html/home.jsp").forward(req, resp);

	}

}
