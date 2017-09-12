/**
 * 
 */
package com.intigna.services.canary.common.persistence.admin;

import java.util.List;

import com.datacode2.microservices.commons.data.ConstraintError;

/**
 * @author Chris Lynn
 *
 */
public interface IUniqueEntityConstraintPersistenceService
{
	/**
	 * Checks whether or not a given value exists for a given field
	 * 
	 * @param objInstance
	 * @param clazz
	 * @param nameProperty
	 * @param nameMessage
	 * @param secondProperty
	 * @param secondPropertyMessage
	 * @return
	 * @throws UnsupportedOperationException
	 */
	List<ConstraintError> entityExists(Object objInstance, Class<?> clazz, String nameProperty, String nameMessage, String secondProperty, String secondPropertyMessage);
	
	/**
	 * Checks whether or not a given value exists for a given field in Database.
	 * 
	 * @param clazz
	 * @param property
	 * @param value
	 * @return
	 * @throws UnsupportedOperationException
	 */
	List<Object> checkUniqueValue(Class<?> clazz, String property, String value);
}