/**
 * 
 */
package com.intigna.services.canary.common.persistence.admin;

import java.util.List;

import com.datacode2.microservices.commons.data.ConstraintError;

/**
 * To Check uniqueness for combination two reference fields of each Entity.
 * @author Chris Lynn
 *
 */
public interface ITwoReferenceUniqueConstraintPersistenceService
{
	/**
	 * Checks whether or not a given combination of value exists for a given field.
	 * 
	 * @param objInstance
	 * @param clazz
	 * @param message
	 * @param referencePropertyOne
	 * @param referencePropertyTwo
	 * @return
	 * @throws UnsupportedOperationException
	 */
	List<ConstraintError> entityExists(Object objInstance, Class<?> clazz, String message, String referencePropertyOne, String referencePropertyTwo);
	
	/**
	 * Checks whether or not a given combination of value exists for a given field in the Database.
	 * 
	 * @param clazz
	 * @param referencePropertyOne
	 * @param referencePropertyOneValue
	 * @param referencePropertyTwo
	 * @param referencePropertyTwoValue
	 * @return
	 * @throws UnsupportedOperationException
	 */
	List<Object> checkUniqueValue(Class<?> clazz, String referencePropertyOne, Object referencePropertyOneValue, String referencePropertyTwo, Object referencePropertyTwoValue);
}