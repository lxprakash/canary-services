/**
 * 
 */
package com.intigna.services.canary.rest.data.admin;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Asarudeen
 *
 */
public class CanaryAdminBasePOSTData
{
	@NotNull(message = "{common.admin.name.notnull}")
	@Size(min = 1, max = 255, message = "{common.admin.name.size}")
	private String name;
	
	@Size(max = 1024, message = "{common.admin.description.size}")
	private String description;
	
	private Boolean status;
	
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