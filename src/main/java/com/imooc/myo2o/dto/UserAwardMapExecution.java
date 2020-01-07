package com.imooc.myo2o.dto;

import java.util.List;

import com.imooc.myo2o.entity.UserAwardMap;
import com.imooc.myo2o.enums.UserAwardMapStateEnum;

public class UserAwardMapExecution {
	// 结果状态
	private int state;

	// 状态标识
	private String stateInfo;

	// 授权数
	private Integer count;

	// 操作的UserAwardMap
	private UserAwardMap userAwardMap;

	// 授权列表（查询专用）
	private List<UserAwardMap> userAwardMapList;

	public UserAwardMapExecution() {
	}

	// 失败的构造器
	public UserAwardMapExecution(UserAwardMapStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// 成功的构造器
	public UserAwardMapExecution(UserAwardMapStateEnum stateEnum,
			UserAwardMap userAwardMap) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.userAwardMap = userAwardMap;
	}

	// 成功的构造器
	public UserAwardMapExecution(UserAwardMapStateEnum stateEnum,
			List<UserAwardMap> userAwardMapList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.userAwardMapList = userAwardMapList;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public UserAwardMap getUserAwardMap() {
		return userAwardMap;
	}

	public void setUserAwardMap(UserAwardMap userAwardMap) {
		this.userAwardMap = userAwardMap;
	}

	public List<UserAwardMap> getUserAwardMapList() {
		return userAwardMapList;
	}

	public void setUserAwardMapList(List<UserAwardMap> userAwardMapList) {
		this.userAwardMapList = userAwardMapList;
	}

}
