package org.twinnation.springsecurityoauth2clientexample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.twinnation.springsecurityoauth2clientexample.service.MyCustomOAuth2UserService;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableOAuth2Client
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private MyCustomOAuth2UserService userService;
	
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.httpBasic()
			.and().csrf().disable()
			.authorizeRequests()
				.anyRequest()
					.authenticated()
				.and()
					.oauth2Login()
						.userInfoEndpoint()
							.userService(userService);
	}
	
}
