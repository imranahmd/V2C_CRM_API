package com.crm.config;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCORSFilter implements Filter {

	
	private final Logger log = LoggerFactory.getLogger(SimpleCORSFilter.class);
	
	public SimpleCORSFilter() {
	    log.info("SimpleCORSFilter init");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
	
	    HttpServletRequest request = (HttpServletRequest) req;
	    HttpServletResponse response = (HttpServletResponse) res;
	    log.info("Origin request received :" + request.getHeader("Origin"));
	    response.setHeader("Access-Control-Allow-Origin", "*");//request.getHeader("Origin"));
	    response.setHeader("Access-Control-Allow-Credentials", "true");
	    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
	    response.setHeader("Access-Control-Max-Age", "3600");
	    //response.setHeader("Access-Control-Expose-Headers", "Authorization");
	    //response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me ,Authorization,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,x-request-src");//Authorization
	    response.setHeader("Access-Control-Allow-Headers", "*");//Authorization
	    log.info("Origin request received Access-Control-Allow-Origin:" + response.getHeader("Access-Control-Allow-Origin"));
	    
	    chain.doFilter(req, res);
	    
	}
	
		
	/*@Override
	public void init(FilterConfig filterConfig) {
		
	}
	
	@Override
	public void destroy() {
	}*/

}
