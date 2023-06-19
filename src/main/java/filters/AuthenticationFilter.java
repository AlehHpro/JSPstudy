package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

// "/*" - means that this authentication filter will be executed for every servlets url-pattern in application.
@WebFilter("/*")
public class AuthenticationFilter implements Filter{

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
			throws IOException, ServletException {
		// Pre-processing
		HttpServletRequest request = (HttpServletRequest)arg0;
		if(request.getRequestURI().startsWith("/HPlusSample/orderHistory") || request.getRequestURI().startsWith("/HPlusSample/getProfileDetails")) {
			HttpSession session = request.getSession();
			if(session.getAttribute("username") == null) {
				request.getRequestDispatcher("/html/login.jsp").forward(request, arg1);
			}
		}
		
		chain.doFilter(request, arg1);
		// Post-processing
	}

}
