package com.intigna.services.canary.persistence.entity.admin;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.datacode2.microservices.commons.dbconverter.IsActiveStatusJPAConverter;
import com.datacode2.microservices.commons.persistence.entity.MultiTenant;

@MappedSuperclass
public abstract class CanaryAdminBaseEntity extends MultiTenant
{
	@Column(name = "NAME_TEXT")
	@NotNull(message = "{common.admin.name.notnull}")
	@Size(min = 1, max = 255, message = "{common.admin.name.size}")
	private String name;
	
	@Column(name = "DESCRIPTION")
	@Size(max = 1024, message = "{common.admin.description.size}")
	private String description;
	
	@Column(name = "STATUS")
	@Convert(converter=IsActiveStatusJPAConverter.class)
	private Boolean status;
	
	@Override
	public String getEntityDisplayName()
	{
		return this.name;
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