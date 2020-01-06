package com.SchoolShop.o2o.entity;
import java.util.Date;
public class LocalAuth {
	private Long lacalAuthId;
	private String userName;
	private String password;
	private Date createTime;
	private Date lastEditTime;
	private PersonInfo personInfo;
	public Long getLacalAuthId() {
		return lacalAuthId;
	}
	public void setLacalAuthId(Long lacalAuthId) {
		this.lacalAuthId = lacalAuthId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public PersonInfo getPersonInfo() {
		return personInfo;
	}
	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}
}

