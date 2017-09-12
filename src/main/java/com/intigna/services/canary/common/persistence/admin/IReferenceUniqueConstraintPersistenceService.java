/**
 * 
 */
package com.intigna.services.canary.common.persistence.admin;

import java.util.List;

import com.datacode2.microservices.commons.data.ConstraintError;

/**
 * To Check uniqueness for combination of name and reference fields of each Entity.
 * @author Chris Lynn
 *
 */
public interface IReferenceUniqueConstraintPersistenceService
{
	/**
	 * Checks whether or not a given combination of value exists for a given field.
	 * 
	 * @param objInstance
	 * @param clazz
	 * @param message
	 * @param nameProperty
	 * @param referenceProperty
	 * @return
	 * @throws UnsupportedOperationException
	 */
	List<ConstraintError> entityExists(Object objInstance, Class<?> clazz, String message, String nameProperty, String referenceProperty);
	
	/**
	 * Checks whether or not a given combination of value exists for a given field in the Database.
	 * 
	 * @param clazz
	 * @param nameProperty
	 * @param namePropertyValue
	 * @param referenceProperty
	 * @param referencePropertyValue
	 * @return
	 * @throws UnsupportedOperationException
	 */
	List<Object> checkUniqueValue(Class<?> clazz, String nameProperty, String namePropertyValue, String referenceProperty, Object referencePropertyValue);
}