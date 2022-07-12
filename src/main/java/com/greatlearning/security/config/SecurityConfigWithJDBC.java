package com.greatlearning.security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfigWithJDBC extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      String encryptedPassword = getEncoder().encode("root");
      String encryptedPassword2 = getEncoder().encode("chinna");
      auth.jdbcAuthentication().dataSource(dataSource).withDefaultSchema()
      .withUser("DB").password(encryptedPassword).roles("STORE_OWNER")
      .and()
      .withUser("pavani").password(encryptedPassword2).roles("STORE_Manager");
//		auth.inMemoryAuthentication()
//		.withUser("bts").password(getEncoder().encode("173")).roles("STORE_hero");
	}

	@Bean
	public PasswordEncoder getEncoder() {
		// return NoOpPasswordEncoder.getInstance();

		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/h2-console/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable().
		authorizeRequests()
		.antMatchers("/organicVeggies/makeAnnouncement").hasRole("STORE_OWNER")
		.antMatchers("/organicVeggies/checkInventory", "/organicVeggies/viewInventory", "/organicVeggies/doCheckout")
		.hasAnyRole("STORE_OWNER", "STORE_Manager","STORE_hero")
		.antMatchers("/").permitAll()
		.and().formLogin();
	}
}