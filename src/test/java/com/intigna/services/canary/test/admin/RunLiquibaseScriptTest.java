/**
 * 
 */
package com.intigna.services.canary.test.admin;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.intigna.services.canary.persistence.liquibase.LiquibaseConfiguration;

/**
 * It will Run All Liquibase Scripts.
 * @author Chris Lynn
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RunLiquibaseScriptTest
{
	@Test
	public void runLiquibaseScript()
	{
		System.out.println("Started Running Liquibase Scripts.");
		
		LiquibaseConfiguration.configureLiquibase();
	}
}