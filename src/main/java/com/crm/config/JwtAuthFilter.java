package com.crm.config;

import java.io.IOException;
import java.util.Date;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	
	private final Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);

	@Autowired
	private com.crm.helper.JwtHelperUtil JwtHelperUtil;
	
	@Autowired
	private com.crm.services.customUserDetailsService customUserDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {
		
		String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/swagger-ui") || requestURI.startsWith("/v3/api-docs")) {
               filterChain.doFilter(request, response);
               return;
        }

	    String requestTokenHeader = request.getHeader("Authorization");
	    System.out.println("JwtAuthFilter.java:::No: "+request.getRequestURI() +"  :::: " + request.getContextPath() + " :::::;Token is Validated" + requestTokenHeader);
	    log.info("JwtAuthFilter.java:: endpoint: "+request.getRequestURI() +"  :::: " + request.getContextPath() + " :::::;Token is :: "+SecurityContextHolder.getContext().getAuthentication()+"  ::::Validated" + requestTokenHeader);
	    String username = null;
	    String jwtToken = null;
String endpoint=extractEndpoint(request);
logger.info("EndPoint::::::::::::::::::::::: "+endpoint);
	    if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
	        jwtToken = requestTokenHeader.substring(7);
	        try {
	            if (!JwtHelperUtil.isTokenExpired(jwtToken)) {
	                Date expirationDate = JwtHelperUtil.extractExpiration(jwtToken);
	                System.out.print("Expiration Date::::::::::::::::::::: " + expirationDate);

	                username = this.JwtHelperUtil.extractUsername(jwtToken);
	                System.out.println("JwtAuthFilter.java:::::::::::  " + username);
	                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	                    UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);

	                    if (this.JwtHelperUtil.validateToken(jwtToken, userDetails)) {
	                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	                	    filterChain.doFilter(request, response);

	                    } else {
	                        System.out.print("Token is not valid");
	                        sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Token is not valid");
	                        return;
	                    }
	                } else {
	                    System.out.print("Token is not Validated");
	                    sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Token is not validated");
	                    return;
	                }
	            }
	        } catch (ExpiredJwtException ex) {
	            ex.printStackTrace();
	            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Token is expired");
	            return;
	        }
	    }

	    
	    if (endpoint.equalsIgnoreCase("/token") || endpoint.equalsIgnoreCase("/get-Signup") || endpoint.equalsIgnoreCase("/verified-OTP") || endpoint.equalsIgnoreCase("/create-passwordSelf") || endpoint.equalsIgnoreCase("/PayoutBulkRaised")) {
            filterChain.doFilter(request, response);
		} /*
			 * else if(requestTokenHeader==null && !endpoint.equalsIgnoreCase("/token") ||
			 * !endpoint.equalsIgnoreCase("/get-Signup") ||
			 * !endpoint.equalsIgnoreCase("/verified-OTP") ||
			 * !endpoint.equalsIgnoreCase("/create-passwordSelf") ||
			 * !endpoint.equalsIgnoreCase("/forgetPassword")){ sendErrorResponse(response,
			 * HttpStatus.UNAUTHORIZED, "Token is not validated"); return; }
			 */
	    // Continue the filter chain
	}

	private void sendErrorResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
	    response.setStatus(status.value());
	    response.setContentType("application/json");
	    response.getWriter().write("{\"error\": \"" + message + "\"}");
	}

	 private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request1) {

			// create a UsernamePasswordAuthenticationToken with null values.
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					null, null, null);
			// After setting the Authentication in the context, we specify
			// that the current user is authenticated. So it passes the
			// Spring Security Configurations successfully.
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			// Set the claims so that in controller we will be using it to create
			// new JWT
			request1.setAttribute("claims", ex.getClaims());

		}
	
	 
	 private String extractEndpoint(HttpServletRequest request) {
		    String contextPath = request.getContextPath();
		    String requestURI = request.getRequestURI();
		    
		    // Remove the context path from the request URI to get the endpoint
		    String endpoint = requestURI.substring(contextPath.length());
		    
		    return endpoint;
		}
	

	
	
}