package com.crm.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
	  
	private final Logger log = LoggerFactory.getLogger(WebConfig.class);
	
	  @Override 
	  public void addCorsMappings(CorsRegistry registry) 
	  {
		  log.info("WebConfig changes for CORS ");
		  registry.addMapping("/**");
	  }

}
