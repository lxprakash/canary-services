/**
 * 
 */
package com.intigna.services.canary.persistence.em.utils;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datacode2.microservices.commons.persistence.constants.ICommonPersistenceConstants;
import com.intigna.services.canary.healthcheck.persistence.HealthCheckPersistenceService;
import com.intigna.services.canary.persistence.em.admin.CanaryAdminBaseEntityPersistenceService;
import com.intigna.services.canary.spring.CanaryServicesBeanFactory;

/**
 * A Common Utility Class to serve all Persistence Service Classes.
 * @author Chris Lynn
 *
 */
public final class PersistenceServiceUtils
{
	private static final Logger logger = LoggerFactory.getLogger(PersistenceServiceUtils.class);
	
	private PersistenceServiceUtils()
	{
		throw new IllegalStateException("Persistence Service Utility class");

	}
	
	/**
	 * To Get Hints based on Graph Name.
	 * @param entityManager
	 * @param graphName
	 * @return
	 */
	public static Map<String, Object> getHints(EntityManager entityManager, String graphName)
	{
		logger.info("Invoked getHints() Method");
		
		EntityGraph<?> graph = entityManager.getEntityGraph(graphName);
		
		Map<String, Object> hints = new HashMap<>();
		hints.put(ICommonPersistenceConstants.LOAD_GRAPH, graph);
		
		return hints;
	}
	
	/**
	 * To get Health Check Persistence Service Instance.
	 * @return
	 * @throws Exception
	 */
	public static HealthCheckPersistenceService getHealthCheckPersistenceService()
	{
		return CanaryServicesBeanFactory.getInstance().getHealthCheckPersistenceServiceProxy();
	}
	
	/**
	 * To get Canary Administration Base Entity Persistence Service Instance.
	 * @return
	 * @throws Exception
	 */
	public static CanaryAdminBaseEntityPersistenceService getCanaryAdminBaseEntityPersistenceService()
	{
		return CanaryServicesBeanFactory.getInstance().getCanaryAdminBaseEntityPersistenceServiceProxy();
	}
}