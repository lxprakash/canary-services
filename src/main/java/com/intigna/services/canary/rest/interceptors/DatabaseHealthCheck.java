/**
 * 
 */
package com.intigna.services.canary.rest.interceptors;

import com.codahale.metrics.health.HealthCheck;
import com.datacode2.microservices.commons.message.CommonErrorCodeAndMessage;
import com.intigna.services.canary.healthcheck.dao.HealthCheckDAOImpl;
import com.intigna.services.canary.spring.CanaryServicesBeanFactory;

/**
 * To run Health Check on Database.
 * @author Chris Lynn
 *
 */
public class DatabaseHealthCheck extends HealthCheck
{
	@Override
	protected Result check() throws Exception
	{
		try
		{
			HealthCheckDAOImpl dao = CanaryServicesBeanFactory.getInstance().getHealthCheckDAOImplProxy();
			dao.dbHealthCheck();
		}
		catch(Exception exception)
		{
			return Result.unhealthy(exception);
		}
		return Result.healthy(CommonErrorCodeAndMessage.INFO_001.getErrorMessage());
	}
}