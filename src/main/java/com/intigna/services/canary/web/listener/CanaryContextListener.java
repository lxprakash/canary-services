package com.intigna.services.canary.web.listener;

import java.io.IOException;
import java.io.InputStream;
import java.util.TimeZone;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.core.appender.rolling.action.CommonsCompressAction;
import org.hsqldb.lib.StringInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datacode2.microservices.commons.constants.ICommonServiceConstants;
import com.datacode2.microservices.commons.rest.ObjectJsonParser;
import com.intigna.services.canary.persistence.liquibase.LiquibaseConfiguration;
import com.intigna.services.canary.rest.data.ServiceManifest;

@WebListener
public class CanaryContextListener implements ServletContextListener
{
	private static final Logger logger = LoggerFactory.getLogger(CanaryContextListener.class);
	
	
	
	
	@Override
	public void contextInitialized(ServletContextEvent sce)
	{
		logger.info("Canary Context Listener Initialized.");
		
		//To Configure, Execute, and Update Latest Changes in Liquibase.
	//	LiquibaseConfiguration.configureLiquibase();
		
		//To Set Default TimeZone to UTC.
		TimeZone.setDefault(TimeZone.getTimeZone(ICommonServiceConstants.TIMEZONE));
		
		ServletContext application = sce.getServletContext();		
		InputStream inputStream = application.getResourceAsStream("/META-INF/MANIFEST.MF");
		if (inputStream != null) {					
		try {
			Manifest manifest = new Manifest(inputStream);
			ServiceManifest m = ServiceManifest.populate("canary-service", manifest);
			String json = ObjectJsonParser.objectToJsonParserPrettyPrint(m);
			System.out.println("Manifest: " + json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			IOUtils.closeQuietly(inputStream);
		}
		}
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce)
	{
		logger.info("Canary Context Listener Destroyed.");
	}
}