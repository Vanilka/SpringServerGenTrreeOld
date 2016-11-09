package com.genealogytree.services.responses;

public abstract class ServerResponse {
	public static enum ResponseStatus {
		OK, FAIL;
	}

	private ResponseStatus status;

	ServerResponse(ResponseStatus status) {
		this.status = status;
	}

	public ResponseStatus getStatus() {
		return status;
	}

	public void setStatus(ResponseStatus status) {
		this.status = status;
	}

}
