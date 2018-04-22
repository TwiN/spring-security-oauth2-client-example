package org.twinnation.springsecurityoauth2clientexample.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ExampleController {
	
	@GetMapping("/")
	public String index() {
		OAuth2User user = getCurrentUser();
		return "Hello " + user.getAttributes().get("name") + ". Your email is " + user.getAttributes().get("email")
			  + " and your profile picture is <img src='"+user.getAttributes().get("picture")+"' /> <br />"
			  + "<a href='/logout'>logout</a>";
	}
	
	
	public OAuth2User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return ((OAuth2AuthenticationToken)auth).getPrincipal();
	}
	
}
