package com.sinosoft.cms.base.enums;

/**
 * StatusEnum类型枚举
 */
public enum StatusEnum {
	/**
	 * 禁用
	 */
	DISABLE(0, "false"),
	/**
	 * 启用
	 */
	ENABLE(1, "true"),

	;

	private int status;
	private String name;

	private StatusEnum(int status, String name) {
		this.status = status;
		this.name = name;
	}

	public int getValue() {
		return this.status;
	}

	public String getName() {
		return this.name;
	}

	public static StatusEnum valueOf(int status) {
		StatusEnum ret = null;
		for (StatusEnum stt : StatusEnum.values()) {
			if (stt.getValue() == status) {
				ret = stt;
				break;
			}
		}
		return ret;
	}
}
