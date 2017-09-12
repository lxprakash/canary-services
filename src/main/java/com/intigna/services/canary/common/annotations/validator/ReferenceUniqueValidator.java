/**
 * 
 */
package com.intigna.services.canary.common.annotations.validator;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.datacode2.microservices.commons.data.ConstraintError;
import com.intigna.services.canary.common.annotations.ReferenceUniqueConstraint;
import com.intigna.services.canary.common.persistence.admin.IReferenceUniqueConstraintPersistenceService;
import com.intigna.services.canary.spring.CanaryServicesBeanFactory;

/**
 * Reference Unique Entity Validator, To Check uniqueness for combination of name and reference fields of each Entity.
 * @author Chris Lynn
 *
 */
public class ReferenceUniqueValidator implements ConstraintValidator<ReferenceUniqueConstraint, Object>
{
	private IReferenceUniqueConstraintPersistenceService service;
	private Class<?> entityClass;
	private String message;
	private String nameProperty;
	private String referenceProperty;
	
	@Override
	public void initialize(ReferenceUniqueConstraint unique)
	{
	    this.entityClass = unique.entityClass();
	    this.message = unique.message();
		this.nameProperty = unique.nameProperty();
		this.referenceProperty = unique.referenceProperty();
		this.service = (IReferenceUniqueConstraintPersistenceService) CanaryServicesBeanFactory.getBean("referenceUniqueConstraintPersistenceServiceProxy");
    }

	@Override
	public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext)
	{
		boolean b = true;
	    
	    List<ConstraintError> errors = this.service.entityExists(o, entityClass, message, nameProperty, referenceProperty);
	    
	    if(!errors.isEmpty())
	    {
	    	b = false;
	    	constraintValidatorContext.disableDefaultConstraintViolation();
	    	
	    	for(ConstraintError error : errors)
	    	{
	    		constraintValidatorContext.buildConstraintViolationWithTemplate(error.getErrorMessage()).addPropertyNode(error.getFieldName()).addConstraintViolation();
    		}
        }
	    return b;
    }
}