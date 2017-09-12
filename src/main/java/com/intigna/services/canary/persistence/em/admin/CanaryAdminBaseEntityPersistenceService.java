package com.intigna.services.canary.persistence.em.admin;

import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.LobHelper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.datacode2.microservices.commons.CommonServiceUtils;
import com.datacode2.microservices.commons.persistence.constants.ICommonPersistenceConstants;
import com.datacode2.microservices.commons.persistence.entity.DCBaseEntity;
import com.datacode2.microservices.commons.persistence.entity.data.PageData;
import com.intigna.services.canary.persistence.em.utils.PersistenceServiceUtils;

public class CanaryAdminBaseEntityPersistenceService
{
	@PersistenceContext
	public EntityManager entityManager;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void add(DCBaseEntity entity)
	{
		DCBaseEntity dbEntity = entityManager.merge(entity);
		entity.setId(dbEntity.getId());
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(DCBaseEntity entity)
	{
		entityManager.merge(entity);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(DCBaseEntity entity)
	{
		entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public DCBaseEntity getById(UUID id, String graphName, Class entityClass)
	{
		if(!StringUtils.isEmpty(graphName))
			return (DCBaseEntity) entityManager.find(entityClass, id, PersistenceServiceUtils.getHints(entityManager, graphName));
		else
			return (DCBaseEntity) entityManager.find(entityClass, id);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public PageData findAll(String name, Boolean status, String graphName, Class entityClass, Integer pageNumber, Integer pageSize, Boolean isPaging)
	{
		PageData pageData = new PageData();
		List entityClassList = null;
		Long count;
		Integer totalPages = 0;

		//Actual Query
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery cq = builder.createQuery(entityClass);
		Root rootEntry = cq.from(entityClass);
		
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(rootEntry.get("tenantId"), CommonServiceUtils.getTenantId()));
		
		if(!StringUtils.isEmpty(name))
		{
			predicates.add(builder.equal(builder.lower(rootEntry.<String>get("name")), name.toLowerCase()));
		}
		
		if(status != null)
		{
			predicates.add(builder.equal(rootEntry.get("status"), status));
		}
		
		cq.select(rootEntry).where(predicates.toArray(new Predicate[]{})).orderBy(builder.asc(rootEntry.get("name")));
		TypedQuery query = entityManager.createQuery(cq);
		
		if(!StringUtils.isEmpty(graphName))
			query.setHint(ICommonPersistenceConstants.LOAD_GRAPH, entityManager.getEntityGraph(graphName));
		
		if(BooleanUtils.isTrue(isPaging))
		{
			entityClassList = query.setFirstResult((pageNumber - 1) * pageSize).setMaxResults(pageSize).getResultList();
			
			//Get Total Count
			CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
			countQuery.select(builder.count(countQuery.from(entityClass)));
			countQuery.where(predicates.toArray(new Predicate[]{}));
			count = entityManager.createQuery(countQuery).getSingleResult();
			totalPages = (int) Math.ceil((double) count.intValue()/pageSize);
		}
		else
		{
			entityClassList = query.getResultList();
			count = (long) entityClassList.size();
			totalPages = 1;
		}
		
		pageData.setTotalEntities(count);
		pageData.setResultLst(entityClassList);
		pageData.setPageTotal(totalPages);
		return pageData;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Blob getBlob(InputStream fileInputStream, Long fileSize) throws Exception
	{
		org.hibernate.Session session = (org.hibernate.Session) entityManager.getDelegate();
		LobHelper lobHelper = session.getLobHelper();
		return lobHelper.createBlob(fileInputStream, fileSize);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public byte[] getInputStreamAsBytes(Blob blob) throws Exception
	{
		return IOUtils.toByteArray(blob.getBinaryStream());
	}
}