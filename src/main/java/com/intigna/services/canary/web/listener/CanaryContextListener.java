package com.intigna.services.canary.web.listener;

import java.util.TimeZone;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datacode2.microservices.commons.constants.ICommonServiceConstants;
import com.datacode2.microservices.commons.web.listener.CommonContextListener;
import com.intigna.services.canary.persistence.liquibase.LiquibaseConfiguration;

@WebListener
public class CanaryContextListener 
extends CommonContextListener implements ServletContextListener
{
	private static final Logger logger = LoggerFactory.getLogger(CanaryContextListener.class);
	
	
	
	
	@Override
	public void contextInitialized(ServletContextEvent sce)
	{
		logger.info("Canary Context Listener Initialized.");
		
		super.contextInitialized(sce);
		
		//To Configure, Execute, and Update Latest Changes in Liquibase.
		LiquibaseConfiguration.configureLiquibase();

		// Move this to common?
		//To Set Default TimeZone to UTC.
		TimeZone.setDefault(TimeZone.getTimeZone(ICommonServiceConstants.TIMEZONE));
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce)
	{
		logger.info("Canary Context Listener Destroyed.");
	}
}