package tacos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder encoder() { 
		return new StandardPasswordEncoder("53cr3t"); //TODO solve deprecation
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception { http
		.authorizeRequests()
		.antMatchers("/design", "/orders").hasRole("USER")
		.antMatchers("/", "/**").access("permitAll")
		.and().formLogin().loginPage("/login").defaultSuccessUrl("/design")
		.and().logout().logoutSuccessUrl("/")
		.and().csrf().ignoringAntMatchers("/h2-console/**")
		.and().headers().frameOptions().sameOrigin();
	}

/*

auth.inMemoryAuthentication().withUser("gabka").password("{noop}fufka").authorities("ROLE_USER")
			.and().withUser("woody").password("bullseye").authorities("ROLE_USER");
			
In spring-security-core:5.0.0.RC1, the default PasswordEncoder is built as a DelegatingPasswordEncoder. When you store the users in memory, you are providing the passwords in plain text and when trying to retrieve the encoder from the DelegatingPasswordEncoder to validate the password it can't find one that matches the way in which these passwords were stored.

Use this way to create users instead.

User.withDefaultPasswordEncoder().username("user").password("user").roles("USER").build(); 
You can also simply prefix {noop} to your passwords in order for the DelegatingPasswordEncoder use the NoOpPasswordEncoder to validate these passwords. Notice that NoOpPasswordEncoder is deprecated though, as it is not a good practice to store passwords in plain text.

User.withUsername("user").password("{noop}user").roles("USER").build();
For more information, check this post.

https://spring.io/blog/2017/11/01/spring-security-5-0-0-rc1-released#password-encoding
*/
}
