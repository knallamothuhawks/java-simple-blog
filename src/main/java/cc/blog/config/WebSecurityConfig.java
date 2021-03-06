package cc.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.httpBasic();
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/members").hasRole("USER")
			.antMatchers(HttpMethod.GET, "/member/**").hasRole("USER")
			.antMatchers(HttpMethod.PUT, "/member").hasRole("USER")
			.antMatchers(HttpMethod.POST, "/member/**").hasRole("USER")
			.antMatchers(HttpMethod.DELETE, "/member/**").hasRole("USER")
			.antMatchers(HttpMethod.GET, "/posts").hasRole("USER")
			.antMatchers(HttpMethod.GET, "/post/**").hasRole("USER")
			.antMatchers(HttpMethod.PUT, "/post").hasRole("USER")
			.antMatchers(HttpMethod.POST, "/post/**").hasRole("USER")
			.antMatchers(HttpMethod.DELETE, "/post/**").hasRole("USER")
			.anyRequest().permitAll();
	}
}
