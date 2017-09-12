package com.intigna.services.canary.rest.interceptors;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datacode2.microservices.commons.constants.IFilterPriorities;
import com.datacode2.microservices.commons.keycloak.KeycloakConfiguration;
import com.datacode2.microservices.commons.rest.data.RequestDetails;
import com.datacode2.microservices.commons.utils.SecurityFilterUtils;
import com.intigna.services.canary.spring.CanaryServicesBeanFactory;

/**
 * Interceptor method checks if the REST call is authenticated or not.
 * @author Chris Lynn
 *
 */
@Provider
@PreMatching
@Priority(IFilterPriorities.AUTHENTICATION)
public class SecurityFilter implements ContainerRequestFilter
{
	private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException
	{
		logger.info("Security Filter :- Inside Filter.");
		
		RequestDetails requestDetails = ResteasyProviderFactory.popContextData(RequestDetails.class);
		KeycloakConfiguration config  = CanaryServicesBeanFactory.getInstance().getKeycloakConfiguration();
		SecurityFilterUtils.validateKeycloakConfig(requestContext, config, requestDetails);
	}
}