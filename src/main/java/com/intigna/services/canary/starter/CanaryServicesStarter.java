/**
 * 
 */
package com.intigna.services.canary.starter;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import com.intigna.services.canary.rest.CanaryServicesApplication;

/**
 * Embedded Jetty Bootstraper to Start the Services.
 * @author Chris Lynn
 *
 */
public class CanaryServicesStarter
{
	public static void main(String[] args) throws Exception
	{
		try
		{
			ServletHolder servletHolder = new ServletHolder(new HttpServletDispatcher());
			servletHolder.setInitParameter("javax.ws.rs.Application", CanaryServicesApplication.class.getCanonicalName());
			
			WebAppContext webAppContext = new WebAppContext();
			webAppContext.setResourceBase("src/main/webapp");
	        webAppContext.setDescriptor("/WEB-INF/web.xml");
	        webAppContext.setContextPath("/canary");
	        webAppContext.addServlet(servletHolder, "/*");
	        webAppContext.setParentLoaderPriority(true);

	        Server server = new Server(8080);
	        server.setHandler(webAppContext);
	        server.start();
	        server.join();
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
}