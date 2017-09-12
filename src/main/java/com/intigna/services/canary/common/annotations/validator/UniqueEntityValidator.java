/**
 * 
 */
package com.intigna.services.canary.common.annotations.validator;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.datacode2.microservices.commons.data.ConstraintError;
import com.intigna.services.canary.common.annotations.UniqueEntityConstraint;
import com.intigna.services.canary.common.persistence.admin.IUniqueEntityConstraintPersistenceService;
import com.intigna.services.canary.spring.CanaryServicesBeanFactory;

/**
 * @author Chris Lynn
 *
 */
public class UniqueEntityValidator implements ConstraintValidator<UniqueEntityConstraint, Object>
{
	private IUniqueEntityConstraintPersistenceService service;
	private Class<?> entityClass;
	private String nameProperty;
	private String nameMessage;
	private String secondProperty;
	private String secondPropertyMessage;
	 
	@Override
	public void initialize(UniqueEntityConstraint unique)
	{
	    this.entityClass = unique.entityClass();
	    this.nameProperty = unique.nameProperty();
	    this.nameMessage = unique.nameMessage();
	    this.secondProperty = unique.secondProperty();
	    this.secondPropertyMessage = unique.secondPropertyMessage();
	    this.service = (IUniqueEntityConstraintPersistenceService) CanaryServicesBeanFactory.getBean("uniqueEntityConstraintPersistenceServiceProxy");
    }

	@Override
	public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext)
	{
		boolean b = true;
	    
	    List<ConstraintError> errors = this.service.entityExists(o, entityClass, nameProperty, nameMessage, secondProperty, secondPropertyMessage);
	    
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