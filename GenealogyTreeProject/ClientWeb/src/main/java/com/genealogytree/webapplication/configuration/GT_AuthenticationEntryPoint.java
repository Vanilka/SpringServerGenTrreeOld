package com.genealogytree.webapplication.configuration;

import java.io.IOException;
import java.io.PrintWriter;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genealogytree.ExceptionManager.config.Causes;
import com.genealogytree.ExceptionManager.exception.ExceptionBean;
 
@Configuration
public class GT_AuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
	   @Override
	    public void commence(final HttpServletRequest request, 
	            final HttpServletResponse response, 
	            final AuthenticationException authException) throws IOException, ServletException {
	        //Authentication failed, send error response.
	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName() + "");
	        ObjectMapper mapper = new ObjectMapper();
	        PrintWriter writer = response.getWriter();
	        writer.println(mapper.writeValueAsString(new ExceptionBean(Causes.UNAUTHORIZED)));
	    }
	     
	    @Override
	    public void afterPropertiesSet() throws Exception {
	        setRealmName("GenealogyTree");
	        super.afterPropertiesSet();
	    }
	}



