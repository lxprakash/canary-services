/**
 * 
 */
package com.intigna.services.canary.rest.impl.manifest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datacode2.microservices.commons.rest.ObjectJsonParser;
import com.datacode2.microservices.commons.rest.data.healthcheck.ServiceManifest;
import com.intigna.services.canary.rest.api.manifest.INavelService;


/**
 * Defines the implementation of the Commission REST services.
 * @author adevoe
 * 
 */
public class NavelServices 
implements INavelService 
{
	private final static Logger logger = LoggerFactory.getLogger(NavelServices.class);

	@Override
	public ServiceManifest getCurrentManifest() throws Exception {
		ServiceManifest m = ServiceManifest.getTheDefaultServiceManifest();
		return m;
	}

	 
}