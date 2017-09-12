package com.intigna.services.canary.healthcheck.dao;

import com.intigna.services.canary.persistence.em.utils.PersistenceServiceUtils;

public class HealthCheckDAOImpl
{
	public Boolean dbHealthCheck()
	{
		return PersistenceServiceUtils.getHealthCheckPersistenceService().dbHealthCheck();
	}
}