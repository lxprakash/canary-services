/**
 * 
 */
package com.intigna.services.canary.rest.impl.utils;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.intigna.services.canary.persistence.entity.admin.CanaryAdminBaseEntity;
import com.intigna.services.canary.rest.data.admin.CanaryAdminBaseGETData;

/**
 * A Common Utility Class to serve all Rest API Service Classes.
 * @author Chris Lynn
 *
 */
public final class RestAPIUtils
{
	private RestAPIUtils()
	{
		throw new IllegalStateException("Rest API Utility class");
	}
	
	/**
	 * To build Response for an Request.
	 * @param entity
	 * @param mediaType
	 * @param status
	 * @return
	 */
	public static Response buildResponse(Object entity, String mediaType, Status status)
    {
		return Response.status(status).entity(entity).type(mediaType).build();
    }
	
	/**
	 * To copy data from Entity to POJO class.
	 * @param copyFrom
	 * @return
	 * @throws Exception
	 */
	public static CanaryAdminBaseGETData copyIntoCanaryAdminBaseGETData(CanaryAdminBaseEntity copyFrom)
	{
		return new CanaryAdminBaseGETData(copyFrom);
	}
}