/**
 * 
 */
package com.intigna.services.canary.rest.impl.manifest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datacode2.microservices.commons.rest.ObjectJsonParser;
import com.intigna.services.canary.rest.api.manifest.INavelService;
import com.intigna.services.canary.rest.data.ServiceManifest;


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
		ServiceManifest m = ServiceManifest.getTheServiceManifest("canary-service");
		String json = ObjectJsonParser.objectToJsonParserPrettyPrint(m);
		System.out.println("Manifest: " + json);
		return m;
	}

	 
}