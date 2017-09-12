package com.intigna.services.canary.test.admin;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * To run all the Test Classes.
 * @author Chris Lynn
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ 
	RunLiquibaseScriptTest.class
})
@Ignore
public class AllTests
{}