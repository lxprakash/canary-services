
package com.intigna.services.canary.rest.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.cache.NoCache;

import com.datacode2.microservices.commons.rest.data.logging.LoggingGETData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 * It is used to define the REST Services Stub for TemplateInstance.
 * @author adevoe
 *
 */
@Path("/logger")
@Api(value = "/logger")
public interface ICanaryLoggerServices
{
	 
	@GET
	@Path("/log/{level}/{category}/{message}")
	@NoCache
	@ApiOperation(value = "Logs based .", response = LoggingGETData.class)
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Invalid id, no data updated or invalid data."),
        @ApiResponse(code = 500, message = "System error")
    })
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public abstract LoggingGETData logInfo(
			@ApiParam(name = "level", 
			value = "Logging Info - Can be 'info','error', or 'debug' ", 
			  required = true) 
			@PathParam("level") String level, 	
			
			@ApiParam(name = "category", 
				value = "Logging category, emulates class name  ",
				example = "com.intigna.services.someservice", 
				required = true) 	
			@PathParam("category")
			String category, 
			
			@ApiParam(name = "message", 
			value = "The message you want to send to the logging server. ",
			example = "OutOfMemoryError", 
			required = true) 	
			@PathParam("message")
			String message,
			
			@QueryParam("exc")
			@ApiParam(name = "exc", 
			value = "non-null and the logger will call log.info(message, new Exception(exceptionMessage)); ",
			required = false) 
			String exceptionMessage
		) throws Exception;

	@GET
	@Path("/extra/{category}/{message}")
	@NoCache
	@ApiOperation(value = "Logs based .", response = LoggingGETData.class)
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Invalid id, no data updated or invalid data."),
        @ApiResponse(code = 500, message = "System error")
    })
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	LoggingGETData addExtraInfo(
			@ApiParam(name = "category", 
				value = "Logging category, emulates class name  ",
				example = "com.intigna.services.someservice", 
				required = true) 	
			@PathParam("category")
			String category, 
			
			@ApiParam(name = "message", 
			value = "The message you want to send to the logging server. ",
			example = "OutOfMemoryError", 
			required = true) 	
			@PathParam("message")
			String message,
			
			@QueryParam("count")
			@ApiParam(name = "count", 
			value = "non-null and the ; ",
			defaultValue = "1",
			required = false) 
			String countStr) throws Exception;

}
	
 