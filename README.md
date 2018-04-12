# spring-security-oauth2-client-example


## Custom OAuth2UserService 

Your service would look like that. It's the easiest way to implement a custom OAuth2 UserService.

```java
@Service
public class MyCustomOAuth2UserService extends DefaultOAuth2UserService {
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User user = super.loadUser(userRequest);
		Map<String, Object> attributes = user.getAttributes();
		Set<GrantedAuthority> authorities = new HashSet<>(user.getAuthorities());
		
		// At this point, you would load your data (e.g. from database) and modify the authorities as you wish
		// For the sake of testing, we'll just add the role 'ADMIN' to the user
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		
		return new DefaultOAuth2User(authorities, attributes, "sub");
	}
	
}
```

And the security configuration

```java
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
```


## Note

For Google, it's actually possible to just use the two following properties:

```properties
spring.security.oauth2.client.registration.google.client-id=
spring.security.oauth2.client.registration.google.client-secret=
```

But for the sake of the example, I'll be inserting all properties.
