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
import com.intigna.services.canary.common.persistence.admin.IReferenceUniqueConstraintPersistenceService;

/**
 * To Check uniqueness for combination of name and reference fields of each Entity.
 * @author Chris Lynn
 *
 */
public class ReferenceUniqueConstraintPersistenceService implements IReferenceUniqueConstraintPersistenceService
{
	@PersistenceContext
	public EntityManager entityManager;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<ConstraintError> entityExists(Object objInstance, Class<?> clazz, String message, String nameProperty, String referenceProperty)
	{
		List<ConstraintError> constraintErrors = new ArrayList<>();
		List<Object> resultList = new ArrayList<>();
		Object o = null; 
		UUID thatId = null;
		
		try
		{
			UUID thisId                   = (UUID) PropertyUtils.getProperty(objInstance, "id");
			String namePropertyValue      = BeanUtils.getProperty(objInstance, nameProperty);
			Object referencePropertyValue = PropertyUtils.getProperty(objInstance, referenceProperty);
			
			resultList = checkUniqueValue(clazz, nameProperty, namePropertyValue, referenceProperty, referencePropertyValue);
			
			if(!resultList.isEmpty())
			{
				o = resultList.get(0);
				thatId = (UUID) PropertyUtils.getProperty(o, "id");
				
				if(thisId != null && thisId.equals(thatId))
				{
					//Do Update Operation
				}
				else
				{
					constraintErrors.add(new ConstraintError(nameProperty, message));
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
	public List<Object> checkUniqueValue(Class<?> clazz, String nameProperty, String namePropertyValue, String referenceProperty, Object referencePropertyValue)
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
			predicates.add(builder.equal(rootEntry.get(referenceProperty), referencePropertyValue));
			predicates.add(builder.equal(builder.lower(rootEntry.<String>get(nameProperty)), namePropertyValue.toLowerCase()));

			cq.select(rootEntry).where(predicates.toArray(new Predicate[]{}));
			resultList = (List<Object>) entityManager.createQuery(cq).setMaxResults(1).getResultList();
		}
		catch (Exception e)
		{
			throw new UnsupportedOperationException(e);
		}
		finally
		{
			entityManager.setFlushMode(FlushModeType.AUTO);
		}
		return resultList;
	}
}