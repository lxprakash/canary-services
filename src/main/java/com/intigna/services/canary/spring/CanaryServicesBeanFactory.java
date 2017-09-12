package com.intigna.services.canary.spring;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.datacode2.microservices.commons.data.SecretData;
import com.datacode2.microservices.commons.data.ServiceEndpoint;
import com.datacode2.microservices.commons.keycloak.KeycloakConfiguration;
import com.datacode2.microservices.commons.persistence.entity.data.ServiceConfigurationData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intigna.services.canary.healthcheck.dao.HealthCheckDAOImpl;
import com.intigna.services.canary.healthcheck.persistence.HealthCheckPersistenceService;
import com.intigna.services.canary.persistence.em.admin.CanaryAdminBaseEntityPersistenceService;

/**
 * Canary Services Bean Factory is used to initialize the spring application context and to provide a helper method for spring bean instantiation.
 * @author mhills
 *
 */
public class CanaryServicesBeanFactory
{
	/**
	 * spring-context.xml file needs to be in the application classpath.
	 */
	private static final String S_CONFIGFILES = "application/spring/canary-services-services-context.xml";

	private static ApplicationContext beanFactory = null;
	private static CanaryServicesBeanFactory fac = null;
	private static Map<String, ServiceEndpoint> mapOfServiceEndpoints = new HashMap<>();
	
	public static CanaryServicesBeanFactory getInstance()
	{
		if (fac == null )
		{
			fac = new CanaryServicesBeanFactory();
			
			try
			{
				beanFactory = new ClassPathXmlApplicationContext(S_CONFIGFILES);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return fac;
	}
	
	/**
	 * Instantiate an object based on a supplied bean name. The bean name corresponds to an entry in the spring-context.xml file.
	 * @param beanName
	 * @return The spring object. If no bean is found a NULL is returned.
	 */
	public static Object getBean(String beanName)
	{
		if ( beanFactory == null )
		{
			getInstance();
		}
		return beanFactory.getBean(beanName);
	}

	/**
	 * Method will lookup a message from the property files defined within the Spring configuration.
	 * @param sK The lookup key value.
	 * @param args An array of objects that are to be inserted into a message.
	 * @param locale - The Locale of the message. If null, points to US locale.
	 * @return The message assigned to sK.
	 */
	public static String getMessage(String sK, Object[] args, Locale locale)
	{
		String msg = null;
		
		if (beanFactory == null)
		{
			getInstance();
		}
		
		if (locale == null)
			locale = Locale.US;
		
		msg = beanFactory.getMessage(sK, args, locale);
		return msg;
	}
	
	//Liquibase Configuration Data Source
	public static DataSource getDataSource()
	{
		return (DataSource) getBean("dataSource");
	}
	
	//Keycloak Client Configuration Declarations
	public KeycloakConfiguration getKeycloakConfiguration()
	{
		return (KeycloakConfiguration) getBean("keycloakConfigurationProxy");
	}
	
	//Service Configuration Data Declarations
	public ServiceConfigurationData getServiceConfigurationData()
	{
		return (ServiceConfigurationData) getBean("serviceConfigurationDataProxy");
	}
	
	//Service Calls
	public ServiceEndpoint getClientServiceEndpoint(String serviceName) throws Exception
	{
		ServiceEndpoint endpoint;
		if(!mapOfServiceEndpoints.containsKey(serviceName))
		{
			SecretData secret = (SecretData) getBean(serviceName);
			ObjectMapper mapper = new ObjectMapper();
			endpoint = mapper.readValue(secret.getSecretData(), ServiceEndpoint.class);
			mapOfServiceEndpoints.put(serviceName, endpoint);
		}
		
		endpoint = mapOfServiceEndpoints.get(serviceName);
		return endpoint ;
	}
	
	//Persistence Declarations
	public HealthCheckPersistenceService getHealthCheckPersistenceServiceProxy()
	{
		return (HealthCheckPersistenceService) getBean("healthCheckPersistenceServiceProxy");
	}
	
	public CanaryAdminBaseEntityPersistenceService getCanaryAdminBaseEntityPersistenceServiceProxy()
	{
		return (CanaryAdminBaseEntityPersistenceService) getBean("adminBaseEntityPersistenceServiceProxy");
	}
	
	//DAO Declarations
	public HealthCheckDAOImpl getHealthCheckDAOImplProxy()
	{
		return (HealthCheckDAOImpl) getBean("healthCheckDAOProxy");
	}
}