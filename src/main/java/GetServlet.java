import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Configuration of WebServlet using annotation.
 * @author Aleh_Hutyrchyk
 *
 */
// Way to define init parameters through annotations.
//@WebServlet(urlPatterns = "/getServlet",
//			initParams = @WebInitParam(name = "URL", value = "http://www.weatherservice.com"))
public class GetServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ServletConfig API is used to pass config. info. to a particular servlet.
		// In this case - registration to a web(weather) service url. URL is embedded into init parameter in web.xml file.
		ServletConfig config = getServletConfig();
		System.out.println(config.getInitParameter("URL"));
		ServletContext context = getServletContext();
		System.out.println(context.getInitParameter("dbURL"));
		
		
		String value = req.getParameter("name");
		String htmlResponse = "<html><h3>Welcome to servlets!!!</h3></html>";
		PrintWriter writer = resp.getWriter();
		writer.write(htmlResponse + " " + value);
		
	}

	
}
