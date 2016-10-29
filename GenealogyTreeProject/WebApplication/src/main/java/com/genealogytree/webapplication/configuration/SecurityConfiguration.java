package com.genealogytree.webapplication.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	private static String REALM="GenealogyTree";
	
	@Autowired
	DataSource dataSource;
	
	@Autowired 
	GT_UserDetailsService userDetailsService;
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
	  auth.userDetailsService(userDetailsService);
	}

	
	  @Override
	    protected void configure(HttpSecurity http) throws Exception {
	    http.csrf().disable()
	    .authorizeRequests()
	    .antMatchers("/").permitAll()
	    .antMatchers("/user/add").permitAll()
	    .antMatchers("/user/**").authenticated()
	    .and().httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint())
	    .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	    
	     
	  }
	  
	    @Bean
	    public GT_AuthenticationEntryPoint getBasicAuthEntryPoint(){
	        return new GT_AuthenticationEntryPoint();
	    }
	     
	    /* To allow Pre-flight [OPTIONS] request from browser */
	    @Override
	    public void configure(WebSecurity web) throws Exception {
	        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/");
	    }

}
