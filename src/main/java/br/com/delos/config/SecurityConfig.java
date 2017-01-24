package br.com.delos.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import br.com.delos.security.RestUnauthorizedEntryPoint;
import br.com.delos.security.UserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan("br.com.delos.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger log = Logger.getLogger(SecurityConfig.class);

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
    private AuthenticationSuccessHandler restAuthenticationSuccessHandler;
	
	@Autowired
    private AuthenticationFailureHandler restAuthenticationFailureHandler;
	
	@Autowired
    private RestUnauthorizedEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler restAccessDeniedHandler;
	   
	public SecurityConfig() {
		log.info("::::Inicialização do Security Config::::");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().withUser("agm").password("agm").roles("ADMIN");
//	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/template/**", "/index.jsp", "/login.xhtml");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.headers().disable()
			.csrf().disable()
			.authorizeRequests()
				.antMatchers("/resources/**").permitAll()
				.antMatchers("/template/**").permitAll()
				.antMatchers("/pages/**").permitAll()
				.antMatchers("/pages/configuration/**").hasAuthority("ADMIN")
//				.anyRequest().authenticated()
				.and()
			.exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .accessDeniedHandler(restAccessDeniedHandler)
                .and()
			.formLogin()
				.loginPage("/login.faces")
				.loginProcessingUrl("/j_spring_security_check")
//                .successHandler(restAuthenticationSuccessHandler)
//                .failureHandler(restAuthenticationFailureHandler)
                .failureUrl("/login.faces?erro=true")
                .usernameParameter("j_username")
                .passwordParameter("j_password") 
                .defaultSuccessUrl("/pages/index.faces")
                .permitAll()
                .and()
			.logout()
                .logoutUrl("/")
//                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                .logoutSuccessUrl("/login.faces" )
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and();
	}
	
}
