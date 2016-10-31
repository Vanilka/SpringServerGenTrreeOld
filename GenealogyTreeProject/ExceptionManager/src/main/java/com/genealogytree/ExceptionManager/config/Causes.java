package com.genealogytree.ExceptionManager.config;

public enum Causes {
	ANOTHER_CAUSE(0,"Another Error"),
	USER_ALREADY_EXIST(300,"Sorry, but user with this login exist already");
	
	private int code;
	private String cause;
	
	private Causes(int number, String cause) {
		this.cause = cause;
		this.code = number;
	}
	
	@Override
	public String toString() {
		return this.cause;
	}
	
	public int getCode() {
		return this.code;
	}
}
