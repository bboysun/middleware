package com.darryl.middleware.common.enums;

/**
 * @Auther: Darryl
 * @Description: 返回结果的状态值的枚举
 * @Date: 2020/04/28
 */
public enum ResultEnum {
	SUCCESS("200","请求成功"),
	ERROR("500","系统内部错误"),
	NOT_FOUND("404","找不到页面"),
	FORBIDDEN("403","请求拒绝");

	private String code;

	private String msg;

	ResultEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}
