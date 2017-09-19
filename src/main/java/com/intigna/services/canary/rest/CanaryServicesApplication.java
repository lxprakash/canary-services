package com.intigna.services.canary.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.datacode2.microservices.commons.CommonServiceUtils;
import com.intigna.services.canary.rest.features.HealthCheckFeature;
import com.intigna.services.canary.rest.impl.CanaryLoggerServices;
import com.intigna.services.canary.rest.impl.healthchecks.ApplicationHealthCheckServices;

@ApplicationPath("/canary")
public class CanaryServicesApplication extends Application
{
	private Set<Object> singletons = new HashSet<>();

	@Override
	public Set<Class<?>> getClasses()
	{
		HashSet<Class<?>> set = new HashSet<>();
		
		set.addAll(CommonServiceUtils.getGlobalApplicationClasses());
		
		//Interceptor / Provider / Feature / Exception Mapper Classes.
	 	//set.add(SecurityFilter.class);
		set.add(HealthCheckFeature.class);
		set.add(ApplicationHealthCheckServices.class);
		
		//Rest Impl Service Classes.
		set.add(CanaryLoggerServices.class);
		
		return set;
	}

	@Override
	public Set<Object> getSingletons()
	{
		return this.singletons;
	}
}