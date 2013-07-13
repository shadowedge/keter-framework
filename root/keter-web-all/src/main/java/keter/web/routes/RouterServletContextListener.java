package keter.web.routes;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.jsp.JspApplicationContext;
import javax.servlet.jsp.JspFactory;

import keter.web.routes.RoutesELResolver;



/**
 * Servlet context listener that registers custom RoutesELResolver class as
 * ELResolver in order to the route formatting work.
 * 
 * @author dawid
 */
public class RouterServletContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// pass
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		JspApplicationContext jspContext = JspFactory.getDefaultFactory()
				.getJspApplicationContext(context);
		jspContext.addELResolver(new RoutesELResolver());
	}
}
