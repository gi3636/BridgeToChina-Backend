package com.btchina.core.util;

import org.springframework.lang.Nullable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 获取用户登录的账号信息
 * <p>
 * 该类与网关服务耦合，所获取的用户信息是由 gateway-service 解析而来的
 */
public class AuthHelper {

	public static final String USER_ID_HEADER = "user-id";
	public static final String USERNAME_HEADER = "username";

	/**
	 * 获取当前用户的id
	 *
	 * @return 当前用户id，当用户未登录时为null
	 */
	@Nullable
	public static Long getCurrentUserId() {
		// 获取当前线程的请求
		ServletRequestAttributes attribute = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attribute == null) {
			return null;
		}
		// 获取请求中的用户信息
		String header = attribute.getRequest().getHeader(USER_ID_HEADER);
		Long userId;
		try {
			userId = Long.valueOf(header);
		} catch (NumberFormatException ignored) {
			// 解析失败
			userId = null;
		}
		return userId;
	}


	/**
	 * 获取当前用户的id
	 *
	 * @return 当前用户id，当用户未登录时为null
	 */
	@Nullable
	public static String getCurrentUsername() {
		// 获取当前线程的请求
		ServletRequestAttributes attribute = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attribute == null) {
			return null;
		}
		// 获取请求中的用户信息
		return attribute.getRequest().getHeader(USERNAME_HEADER);
	}

	/**
	 * 获取当前用户的id
	 *
	 * @return 当前用户id
	 */
	public static Long getUserId() {
		return getCurrentUserId();
	}

	/**
	 * 获取当前用户的id
	 *
	 * @return 当前用户id
	 */
	public static String getUsername() {
		return getCurrentUsername();
	}

}
