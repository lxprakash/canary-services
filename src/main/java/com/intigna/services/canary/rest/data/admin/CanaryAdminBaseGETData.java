/**
 * 
 */
package com.intigna.services.canary.rest.data.admin;

import org.springframework.util.ObjectUtils;

import com.datacode2.microservices.commons.CommonServiceUtils;
import com.datacode2.microservices.commons.rest.data.BaseGETDataUUID;
import com.intigna.services.canary.persistence.entity.admin.CanaryAdminBaseEntity;

/**
 * @author Asarudeen
 *
 */
public class CanaryAdminBaseGETData extends BaseGETDataUUID
{
	private String name;
	private String description;
	private Boolean status;
	
	public CanaryAdminBaseGETData()
	{}
	
	public CanaryAdminBaseGETData(CanaryAdminBaseEntity entity)
	{
		if(!ObjectUtils.isEmpty(entity))
		{
			this.setId(entity.getId());
			this.setName(entity.getName());
			this.setDescription(entity.getDescription());
			this.setStatus(entity.getStatus());
			this.setDateCreated(CommonServiceUtils.newDateGETData(entity.getDateCreated(), null));
		}
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Boolean getStatus()
	{
		return status;
	}

	public void setStatus(Boolean status)
	{
		this.status = status;
	}
}