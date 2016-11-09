package com.genealogytree.exception;

public class ExceptionBean {
	private String cause;
	private int code;
	
	public ExceptionBean() {
		this(0, null);
	}
	public ExceptionBean(int code, String cause) {
		this.code = code;
		this.cause = cause;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
}
