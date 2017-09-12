/**
 * 
 */
package com.intigna.services.canary.rest.impl.healthchecks;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datacode2.microservices.commons.rest.api.IApplicationHealthCheckServices;
import com.datacode2.microservices.commons.rest.data.healthcheck.ServiceHealthCheckData;

/**
 * Defines the implementation of the Application Health Check REST Services.
 * @author Asarudeen
 *
 */
public class ApplicationHealthCheckServices implements IApplicationHealthCheckServices
{
	private static final Logger logger = LoggerFactory.getLogger(ApplicationHealthCheckServices.class);
	
	@Override
	public Response checkApplicationHealth() throws Exception
	{
		logger.info("ApplicationHealthCheckService :- checkApplicationHealth() Invoked.");
		
		ServiceHealthCheckData healthData = new ServiceHealthCheckData();
		
		//TODO: Add Health Check for all Dependent Applications.
		
		logger.info("ApplicationHealthCheckService :- checkApplicationHealth() Ended.");
		return Response.ok().status(Status.OK).entity(healthData).build();
	}
}