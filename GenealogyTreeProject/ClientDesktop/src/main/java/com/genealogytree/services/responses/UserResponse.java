package com.genealogytree.services.responses;

import com.genealogytree.domain.beans.User;

public class UserResponse extends ServerResponse {

	User user;

	public UserResponse() {
		this(null);
	}

	public UserResponse(User u) {
		super(ResponseStatus.OK);
		this.user = u;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
