/**
 * 
 */
package com.intigna.services.canary.common.annotations.validator;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.datacode2.microservices.commons.data.ConstraintError;
import com.intigna.services.canary.common.annotations.TwoReferenceUniqueConstraint;
import com.intigna.services.canary.common.persistence.admin.ITwoReferenceUniqueConstraintPersistenceService;
import com.intigna.services.canary.spring.CanaryServicesBeanFactory;

/**
 * Two Reference Unique Entity Validator, To Check uniqueness for combination of two reference fields of each Entity.
 * @author Chris Lynn
 *
 */
public class TwoReferenceUniqueValidator implements ConstraintValidator<TwoReferenceUniqueConstraint, Object>
{
	private ITwoReferenceUniqueConstraintPersistenceService service;
	private Class<?> entityClass;
	private String message;
	private String referencePropertyOne;
	private String referencePropertyTwo;
	
	@Override
	public void initialize(TwoReferenceUniqueConstraint unique)
	{
	    this.entityClass = unique.entityClass();
	    this.message = unique.message();
		this.referencePropertyOne = unique.referencePropertyOne();
		this.referencePropertyTwo = unique.referencePropertyTwo();
		this.service = (ITwoReferenceUniqueConstraintPersistenceService) CanaryServicesBeanFactory.getBean("twoReferenceUniqueConstraintPersistenceServiceProxy");
    }

	@Override
	public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext)
	{
		boolean b = true;
	    
	    List<ConstraintError> errors = this.service.entityExists(o, entityClass, message, referencePropertyOne, referencePropertyTwo);
	    
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