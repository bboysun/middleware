package com.darryl.middleware.common.res;

import com.darryl.middleware.common.enums.ResultEnum;

import java.io.Serializable;

/**
 * @Auther: Darryl
 * @Description: 返回给前端的response
 * @Date: 2020/04/28
 */
public class Response<T> implements Serializable {

	private String code;

	private String msg;

	private T data;

	public Response(String code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public Response(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Response(ResultEnum resultEnum) {
		this.code = resultEnum.getCode();
		this.msg = resultEnum.getMsg();
	}

	public Response(ResultEnum resultEnum, T data) {
		this.code = resultEnum.getCode();
		this.msg = resultEnum.getMsg();
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
