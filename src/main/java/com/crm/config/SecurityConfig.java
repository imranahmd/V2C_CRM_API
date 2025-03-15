package com.crm.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
//import org.springframework.security.web.header.HeaderWriter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enable CORS
				.csrf(csrf -> csrf
						.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())) // Enable CSRF protection
//				.headers(headers -> headers
//								.frameOptions(frameOptions -> frameOptions.deny())  // Set X-Frame-Options to DENY
//								//.xssProtection(xss -> xss.block(true))  // Enable X-XSS-Protection
//								.contentSecurityPolicy(csp -> csp.policyDirectives("default-src 'self'; script-src 'self' https://pg.payfi.co.in; style-src 'self' https://pg.payfi.co.in;"))  // Set CSP
//								.referrerPolicy(referrer -> referrer.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.NO_REFERRER))  // Set Referrer-Policy
//				)
				.securityMatcher("/crm/**")
				.authorizeHttpRequests(authorizeRequests -> authorizeRequests
								.requestMatchers(HttpMethod.POST, "/forgetPassword").permitAll()
								.requestMatchers(HttpMethod.POST, "/reset-password").permitAll()
								.requestMatchers(HttpMethod.POST, "/get-Signup").permitAll()
								.requestMatchers(HttpMethod.POST, "/verified-OTP").permitAll()
								.requestMatchers(HttpMethod.GET, "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/v3/api-docs.yaml").permitAll()
								.anyRequest().authenticated() // All other requests must be authenticated
				)
				.httpBasic(withDefaults())
				.sessionManagement(session -> session
								.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Set session management
				);

        return http.build();
    }
	
	 

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("https://pg.payfi.co.in"); // Replace with your domain
        configuration.addAllowedOrigin("http://localhost:4200"); // Replace with your domain
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("content-security-policy"); 
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply configuration to all endpoints
        return source;
    }
	@Bean
	PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

//	@Bean
//	public AuthenticationManager authenticationManager() throws Exception {
//		return super.authenticationManager();
//
//	}

	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = 
            http.getSharedObject(AuthenticationManagerBuilder.class);
        // Configure your UserDetailsService and PasswordEncoder here
        return authenticationManagerBuilder.build();
    }


	@Bean
	JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
    
    
}