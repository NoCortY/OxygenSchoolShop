package cn.objectspace.schoolshop.enums;

public enum UserProductMapStateEnum {
	SUCCESS(1, "操作成功"), INNER_ERROR(-1001, "操作失败"), NULL_USERPRODUCT_ID(-1002,
			"UserProductId为空"), NULL_USERPRODUCT_INFO(-1003, "传入了空的信息");

	private int state;

	private String stateInfo;

	private UserProductMapStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public static UserProductMapStateEnum stateOf(int index) {
		for (UserProductMapStateEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}
}
