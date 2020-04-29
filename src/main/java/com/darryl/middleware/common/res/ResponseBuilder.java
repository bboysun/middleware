package com.darryl.middleware.common.res;

import com.darryl.middleware.common.enums.ResultEnum;

/**
 * @Auther: Darryl
 * @Description: 统一返回前端的response的实体
 * @Date: 2020/04/28
 */
public class ResponseBuilder {

	/**
	 * 返回成功，带有返回数据bean，200
	 * @param data 返回的数据
	 * @param <T> 返回数据的类型
	 * @return 返回response实体
	 */
	public static <T> Response buildSeccuess(T data) {
		return new Response(ResultEnum.SUCCESS, data);
	}

	/**
	 * 返回成功，不带有数据， 200
	 * @return 返回response实体
	 */
	public static Response buildSeccuess() {
		return new Response(ResultEnum.SUCCESS);
	}

	/**
	 * 返回失败，500
	 * @return 返回response实体
	 */
	public static Response buildError() {
		return new Response(ResultEnum.ERROR);
	}

	/**
	 * 返回404
	 * @return 返回response实体
	 */
	public static Response buildNotFound() {
		return new Response(ResultEnum.NOT_FOUND);
	}

	/**
	 * 返回拒绝信息，带有拒绝的bean数据
	 * @param data 返回的数据
	 * @param <T> 返回数据的类型
	 * @return 返回response实体
	 */
	public static <T> Response buildForbidden(T data) {
		return new Response(ResultEnum.FORBIDDEN, data);
	}
}
