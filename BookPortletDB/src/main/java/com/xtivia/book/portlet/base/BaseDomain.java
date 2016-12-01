package com.xtivia.book.portlet.base;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dtran
 * A base domain class
 */
public abstract class BaseDomain implements Serializable {

	private static final long serialVersionUID = -3077151134540465540L;
	private Boolean entityStatus;
	private Integer createdBy;
	private Date createdDate;
	private Integer lastUpdatedBy;
	private Date lastUpdatedDate;
	
	public Boolean getEntityStatus() {
		return entityStatus;
	}
	public void setEntityStatus(Boolean entityStatus) {
		this.entityStatus = entityStatus;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	@Override
	public String toString() {
		return "BaseDomain [entityStatus=" + entityStatus + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", lastUpdatedBy=" + lastUpdatedBy + ", lastUpdatedDate=" + lastUpdatedDate + "]";
	}
}
