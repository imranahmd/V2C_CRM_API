package com.crm;
import org.apache.catalina.util.ServerInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
@EnableAsync
public class CrmPortalApplication{

	
	  private static final Logger LOGGER = LogManager.getLogger(CrmPortalApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(CrmPortalApplication.class, args);
		 System.out.println("Tomcat Version: " + ServerInfo.getServerNumber());
		
		
	}
	
	
	
	
	
}	
