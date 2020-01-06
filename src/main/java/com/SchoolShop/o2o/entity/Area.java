package com.SchoolShop.o2o.entity;
import java.util.Date;
public class Area {
	//为什么用Integer 因为如果用int，则为null时，会自动赋值0
	private  Integer areaId;//ID
	private String areaName;//名称
	private Integer priority;//权重
	private Date createTime;//创建时间
	private Date lastEditTime;//更新时间
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
}
