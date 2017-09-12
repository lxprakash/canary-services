/**
 * 
 */
package com.intigna.services.canary.test.utils;

import java.util.UUID;

import org.jboss.resteasy.spi.ResteasyProviderFactory;

import com.datacode2.microservices.commons.rest.data.RequestDetails;

/**
 * To Load and Unload Tenant from Resteasy Provider Factory.
 * @author Chris Lynn
 *
 */
public final class UnitTestUtils
{
	/**
	 * Load Tenant Id in Resteasy Provider Factory.
	 */
	public static void loadTenant()
	{
		RequestDetails requestDetails = new RequestDetails();
		requestDetails.setTenantId(UUID.randomUUID());
		ResteasyProviderFactory.pushContext(RequestDetails.class, requestDetails);
	}
	
	/**
	 * Clears Resteasy Provider Factory.
	 */
	public static void clearTenant()
	{
		ResteasyProviderFactory.clearContextData();
	}
}