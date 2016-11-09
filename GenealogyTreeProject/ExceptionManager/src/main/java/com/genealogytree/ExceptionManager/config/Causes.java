package com.genealogytree.ExceptionManager.config;

public enum Causes {
	ANOTHER_CAUSE(0,"Another Error"),
	USER_ALREADY_EXIST(300,"Sorry, but user with this login exist already"),
	NOT_FOUND_USER(904, "User not found"),
	USER_INISTANCE_WITHOUT_ID(901, "User Id must be provided"),
	UNAUTHORIZED(401, "Required full authorization");
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
