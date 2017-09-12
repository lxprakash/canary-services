package com.intigna.services.canary.healthcheck.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class HealthCheckPersistenceService
{
	@PersistenceContext
	public EntityManager entityManager;
	
	@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public Boolean dbHealthCheck()
	{
		entityManager.createNativeQuery("SELECT 1").getSingleResult();
		return true;
	}
}