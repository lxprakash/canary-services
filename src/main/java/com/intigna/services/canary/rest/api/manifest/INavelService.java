
package com.intigna.services.canary.rest.api.manifest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.cache.NoCache;

import com.intigna.services.canary.rest.data.ServiceManifest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 * It is used to define the REST Services Stub for TemplateInstance.
 * @author adevoe
 *
 */
@Path("/hc1")
@Api(value = "/hc1")
public interface INavelService
{
	 
	@GET
	@Path("/xx")
	@NoCache
	@ApiOperation(value = "Fetches based the id.", response = ServiceManifest.class)
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Invalid id, no data updated or invalid data."),
        @ApiResponse(code = 500, message = "System error")
    })
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public abstract ServiceManifest getCurrentManifest() throws Exception;

}
	
 