package com.inkdrop.security.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.inkdrop.domain.repositories.UserRepository;

@WebFilter(urlPatterns={"/v1/message/*","/v1/private_message", "/v1/room/*"})
public class TokenAuthenticationFilter implements Filter {

	@Autowired
	UserRepository userRepository;

	private Logger log = LogManager.getLogger(TokenAuthenticationFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String backendToken = httpRequest.getHeader("Auth-Token");
		HttpServletResponse servletResponse = (HttpServletResponse) response;

		if(backendToken == null){
			log.info("Token is null");
			servletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid token");
			return;
		}
		if(userRepository.findByBackendAccessToken(backendToken) == null){
			log.info("Invalid token: "+backendToken);
			servletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid token");
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {}
}
