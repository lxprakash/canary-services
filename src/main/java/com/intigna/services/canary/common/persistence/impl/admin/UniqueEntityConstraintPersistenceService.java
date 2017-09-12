/**
 * 
 */
package com.intigna.services.canary.common.persistence.impl.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.datacode2.microservices.commons.CommonServiceUtils;
import com.datacode2.microservices.commons.data.ConstraintError;
import com.intigna.services.canary.common.persistence.admin.IUniqueEntityConstraintPersistenceService;

/**
 * Method is used to support entity level validation. It is called by the @UniqueEntityContraint annotation class. 
 * @author Chris Lynn
 *
 */
public class UniqueEntityConstraintPersistenceService implements IUniqueEntityConstraintPersistenceService
{
	@PersistenceContext
	public EntityManager entityManager;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<ConstraintError> entityExists(Object objInstance, Class<?> clazz, String nameProperty, String nameMessage, String secondProperty, String secondPropertyMessage)
	{
		List<ConstraintError> constraintErrors = new ArrayList<>();
		List<Object> resultList = new ArrayList<>();
		Object o = null; 
		UUID thatId = null;
		
		try
		{
			UUID thisId        = (UUID) PropertyUtils.getProperty(objInstance, "id");
			String name        = BeanUtils.getProperty(objInstance, nameProperty);
			String secondField = BeanUtils.getProperty(objInstance, secondProperty);
			
			resultList = checkUniqueValue(clazz, nameProperty, name);
			 
			if(!resultList.isEmpty()) //For Updation
			{
				o = resultList.get(0);
				thatId = (UUID) PropertyUtils.getProperty(o, "id");
				
				if ( thisId != null && thisId.equals(thatId))
				{
					if(secondField != null && !secondField.isEmpty())
					{
						resultList = checkUniqueValue(clazz, secondProperty, secondField);
						
						if(!resultList.isEmpty())
						{
							o = resultList.get(0);
							thatId = (UUID) PropertyUtils.getProperty(o, "id");
							
							if (thisId.equals(thatId))
							{
								//Do Update Operation
							}
							else
							{
								constraintErrors.add(new ConstraintError(secondProperty, secondPropertyMessage));
							}
						}
					}
				}
				else
				{
					constraintErrors.add(new ConstraintError(nameProperty, nameMessage));
				}
			}
			else //For New Creation
			{
				if(secondField != null && !secondField.isEmpty())
				{
					resultList = checkUniqueValue(clazz, secondProperty, secondField);
					
					if(!resultList.isEmpty())
					{
						o = resultList.get(0);
						thatId = (UUID) PropertyUtils.getProperty(o, "id");
						
						if ( thisId != null && thisId.equals(thatId))
						{
							//Do Create Operation
						}
						else
						{
							constraintErrors.add(new ConstraintError(secondProperty, secondPropertyMessage));
						}
					}
				}
			}			
		}
		catch (Exception e)
		{
			throw new UnsupportedOperationException(e);
		}
		return constraintErrors;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Object> checkUniqueValue(Class<?> clazz, String property, String value)
	{
		List<Object> resultList = new ArrayList<>();
		
		try
		{
			entityManager.setFlushMode(FlushModeType.COMMIT);
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<?> cq = builder.createQuery(clazz);
			Root rootEntry = cq.from(clazz);
			
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(builder.equal(rootEntry.get("tenantId"), CommonServiceUtils.getTenantId()));
			predicates.add(builder.equal(builder.lower(rootEntry.get(property)), value.toLowerCase())); // need to add BeanUtils to get the property from the object.
			
			cq.select(rootEntry).where(predicates.toArray(new Predicate[]{}));
			resultList = (List<Object>) entityManager.createQuery(cq).setMaxResults(1).getResultList();
		}
		catch (Exception e)
		{
			throw new UnsupportedOperationException(e);
		}
		finally
		{
			entityManager.setFlushMode(FlushModeType.AUTO);  // reset to standard behavior
		}
		return resultList;
	}
}