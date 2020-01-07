package com.imooc.myo2o.enums;

public enum AwardStateEnum {
	OFFLINE(-1, "非法奖品"), SUCCESS(0, "操作成功"), INNER_ERROR(-1001, "操作失败"), EMPTY(
			-1002, "奖品信息为空");

	private int state;

	private String stateInfo;

	private AwardStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public static AwardStateEnum stateOf(int index) {
		for (AwardStateEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}

}
