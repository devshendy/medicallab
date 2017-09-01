package medicallab.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		
	@Autowired private UserDetailsService userService;
	@Autowired private BCryptPasswordEncoder bcryptPasswordEncoder;
	@Autowired private AccessDeniedHandler accessDeniedHandler;

	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {		
		auth
			.userDetailsService(userService)
			.passwordEncoder(bcryptPasswordEncoder);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/js/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/js/**", "/css/**", "/images/**").permitAll()
				.antMatchers("/login**").permitAll()
				.antMatchers("/patients/medical-profile/**", "/patients/api/**", "/patients/**/profile-image.png").permitAll()
				
				.antMatchers("/patients/**", "/requests/**", "/test/**").hasAnyRole(new String[] {"ADMIN", "OFFICER", "DOCTOR"})
				.antMatchers("/patients/new/**", "/patients/edit/**").hasRole("OFFICER")
				.antMatchers("/users/**").hasRole("ADMIN")
				
				.anyRequest().authenticated()
				.and()
			.exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler)
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
			.logout()
				.deleteCookies("JSESSIONID")
				.permitAll()
				.and()
			.sessionManagement()
				.maximumSessions(1).maxSessionsPreventsLogin(false)
					.expiredUrl("/login?expired")
					.sessionRegistry(getSessionRegistry())
			;
		
	}
	
	@Bean("sessionRegistry")
	public SessionRegistry getSessionRegistry() {
		return new SessionRegistryImpl();
	}
	
	@Bean("bcryptPasswordEncoder")
	public BCryptPasswordEncoder getBcryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
