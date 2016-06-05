package com.inkdrop.config.web;

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

import lombok.extern.slf4j.Slf4j;

@WebFilter(urlPatterns={"/**", "/*", "*"})
@Slf4j
public class SimpleCORSFilter implements Filter {
	
	@Override
   public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		log.info("Allowing CORS");
       HttpServletResponse response = (HttpServletResponse) servletResponse;
       HttpServletRequest req = (HttpServletRequest) servletRequest;
       response.setHeader("Access-Control-Allow-Origin", "*");
       response.setHeader("Access-Control-Allow-Credentials", "true");
       response.setHeader("Access-Control-Allow-Methods", "POST, GET, HEAD, OPTIONS");
       response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Auth-Token");
       
       filterChain.doFilter(req, response);
   }

	@Override
	public void init(FilterConfig filterConfig) {
		//
	}

	@Override
	public void destroy() {
		//
	}

}

