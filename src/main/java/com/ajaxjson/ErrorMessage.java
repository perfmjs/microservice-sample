package com.ajaxjson;

/**
 * 返回给前台页面的json消息中的错误相关的信息
 *
 */
public class ErrorMessage {

	public final static int NOMAL_ERROR_CODE = 0;  //0表示普通的错误或者可以忽略的错误
	public final static int NOLOGIN_ERROR_CODE = 999;  //未登录提示
	
	private int code = 0; //0表示一般的错误
	private String message = "";

	public ErrorMessage(String message) {
		this.code = 0;
		this.message = message;
	}
	
	public ErrorMessage(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public static void main(String[] args) {
	}

}
