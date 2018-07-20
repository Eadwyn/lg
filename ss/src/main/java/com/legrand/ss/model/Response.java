package com.legrand.ss.model;

import java.io.Serializable;

public class Response implements Serializable {

	private static final long serialVersionUID = 3263510932374672682L;
	/** 结果成功标志 */
	private boolean success = false;
	/** 提示信息 */
	private String message;
	/** 数据 */
	private Object data;

	public Response() {
	}

	/**
	 * 构造方法
	 * 
	 * @param resultType
	 *            结果成功标志
	 */
	public Response(boolean success) {
		this.success = success;
	}

	/**
	 * 构造方法
	 * 
	 * @param success
	 *            结果成功标志
	 * @param message
	 *            提示信息
	 */
	public Response(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	/**
	 * 构造方法。<b>请注意与构造方法 public ResponseData(boolean success, String message) 的冲突</b>
	 * 
	 * @param resultType
	 *            结果成功标志
	 * @param data
	 *            数据
	 */
	public Response(boolean success, Object data) {
		this.success = success;
		this.data = data;
	}

	/**
	 * 构造方法
	 * 
	 * @param success
	 *            结果成功标志
	 * @param message
	 *            提示信息
	 * @param data
	 *            结果类型
	 */
	public Response(boolean success, String message, Object data) {
		this.success = success;
		this.message = message;
		this.data = data;
	}

	/**
	 * 获取 结果成功标志
	 * 
	 * @return 结果成功标志
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * 设置 结果成功标志
	 * 
	 * @param success
	 *            结果成功标志
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * 获取 提示信息
	 * 
	 * @return 提示信息
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 设置 提示信息
	 * 
	 * @param message
	 *            提示信息
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 获取 数据
	 * 
	 * @return 数据
	 */
	public Object getData() {
		return data;
	}

	/**
	 * 设置 数据
	 * 
	 * @param data
	 *            数据
	 */
	public void setData(Object data) {
		this.data = data;
	}
}

