package com.intigna.services.canary.persistence.liquibase;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intigna.services.canary.spring.CanaryServicesBeanFactory;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

public final class LiquibaseConfiguration
{
	private static final Logger logger = LoggerFactory.getLogger(LiquibaseConfiguration.class);
	
	private LiquibaseConfiguration()
	{
		throw new IllegalStateException("Liquibase Configuration Utility class");
	}
	
	/**
	 * To Update Latest Changes in Database.
	 */
	public static void configureLiquibase()
	{
		try
		{
			logger.info("Started Configuring Liquibase.");
			
			// Get the Connection from the current datasource
			Connection conn = CanaryServicesBeanFactory.getDataSource().getConnection();
			
			// Establish the Liquibase database connection
			Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(conn));
			
			if(database.supportsSchemas())
			{
				// Set the Schema read from the properties file.
				System.setProperty("schemaName", CanaryServicesBeanFactory.getInstance().getServiceConfigurationData().getSchemaName());
				database.setDefaultSchemaName(CanaryServicesBeanFactory.getInstance().getServiceConfigurationData().getSchemaName());
			}
			
			// Define the file that is to be executed in Liquibase
			Liquibase liquibase = new Liquibase("liquibase/changelogs/releases/canary-services-services-changelog-master.xml", new ClassLoaderResourceAccessor(), database);
			
			// Execute the changelog
			liquibase.update(new Contexts(), new LabelExpression());
			
			logger.info("Completed Configuring Liquibase.");
		}
		catch (SQLException e)
		{
			logger.error("Error pulling the connection from the Spring Datasource: " + ExceptionUtils.getStackTrace(e));
			throw new RuntimeException("Error: " + e);
		}
		catch (DatabaseException e)
		{
			logger.error("Error with Liquibase Connecting to Database: " + ExceptionUtils.getStackTrace(e));
			throw new RuntimeException("Error: " + e);
		}
		catch (LiquibaseException e)
		{
			logger.error("Error Defining Changelog or Running Liquibase Update: " + ExceptionUtils.getStackTrace(e));
			throw new RuntimeException("Error: " + e);
		}
		catch (Exception e)
		{
			logger.error("Exception: " + ExceptionUtils.getStackTrace(e));
			throw new RuntimeException("Error: " + e);
		}
	}
}