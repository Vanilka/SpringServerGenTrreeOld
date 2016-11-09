package com.genealogytree.services;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.genealogytree.application.GenealogyTreeContext;
import com.genealogytree.application.ScreenManager;
import com.genealogytree.domain.beans.User;
import com.genealogytree.exception.ExceptionBean;
import com.genealogytree.services.responses.ExceptionResponse;
import com.genealogytree.services.responses.ServerResponse;
import com.genealogytree.services.responses.UserResponse;

public class GTUserService {

	private GenealogyTreeContext context;

	public ServerResponse registerUser(String email, String login, String password) {
		ServerResponse result = null;
		try {
		User registerUser = new User(email, login, password);
		Response registerResponse = this.context.getMainTarget().path("user").path("add")
				.request(MediaType.APPLICATION_JSON).post(Entity.json(registerUser));

		if (registerResponse.getStatus() != 200) {
			result = new ExceptionResponse((registerResponse.readEntity(ExceptionBean.class)));
		} else {
			User user = registerResponse.readEntity(User.class);
			result = new UserResponse(user);
		}
		} catch (Exception e){
			result = new ExceptionResponse(new ExceptionBean());
		}
		return result;
	}

	public ServerResponse connect(String login, String password) {
		ServerResponse result = null;
		try {
			User loginuser = new User(null, login, password);
			Response response = this.context.getMainTarget().path("user").path("login")
					.request(MediaType.APPLICATION_JSON).post(Entity.json(loginuser));
			if (response.getStatus() != 200) {
				System.out.println("Nie Udalo SIE !");
				System.out.println(response.readEntity(String.class));
				result = new ExceptionResponse((response.readEntity(ExceptionBean.class)));
				
			} else {
				User LoggedUser = response.readEntity(User.class);
				this.context.setConnectedUser(LoggedUser);
				result = new UserResponse(LoggedUser);
			}
			
		} catch (Exception e) {
			result = new ExceptionResponse(new ExceptionBean());
		}
		
		return result;
	}

	public void setContext(GenealogyTreeContext context) {
		this.context = context;
	}

}
