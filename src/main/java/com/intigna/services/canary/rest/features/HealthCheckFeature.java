/**
 * 
 */
package com.intigna.services.canary.rest.features;

import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import com.codahale.metrics.health.HealthCheckRegistry;
import com.datacode2.microservices.commons.spring.MicroserviceBeanFactory;
import com.intigna.services.canary.rest.interceptors.DatabaseHealthCheck;

/**
 * Health Check feature is used to register Database Health Check.
 * @author Chris Lynn
 *
 */
@Provider
@ConstrainedTo(RuntimeType.SERVER)
public class HealthCheckFeature implements DynamicFeature
{
	private final HealthCheckRegistry healthChecks = (HealthCheckRegistry) MicroserviceBeanFactory.getBean("healthCheckRegistry");
	
	@Override
	public void configure(ResourceInfo resourceInfo, FeatureContext context)
	{
		healthChecks.register("Database", new DatabaseHealthCheck());
	}
}