package com.intigna.services.canary.test.rest;

import static org.junit.Assert.assertTrue;

import java.net.URL;

import javax.ws.rs.Produces;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.intigna.services.canary.rest.impl.CanaryLoggerServices;

@RunWith(Arquillian.class)
public class CanaryLoggerServicesTest {
	
	@Deployment(testable=false)
	public static WebArchive createTestArchive() {
		return ShrinkWrap.create(WebArchive.class, "CanaryLoggerServicesTest.war")
				.addClass(CanaryLoggerServicesTest.class)
				.addClass(CanaryLoggerServices.class)
				.addAsWebInfResource("web.xml");
	}
	
	@ArquillianResource
	private URL baseURL;
	
	@Test
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public void should_getLogInfo() throws Exception {
		Builder request = ResteasyClientBuilder.newClient().target(baseURL.toString() + "logger/log/info/CanaryLoggerServices/SampleMessage")
				.request();
		Response response = request.get();
		System.out.println("status: " + response.getStatus());
		assertTrue(response != null);
		System.out.println("response: " + response.readEntity(String.class));
		Assert.assertEquals("Status is wrong", 200, response.getStatus());
	}

}
