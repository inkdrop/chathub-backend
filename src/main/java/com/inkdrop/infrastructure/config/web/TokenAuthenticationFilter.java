package com.inkdrop.infrastructure.config.web;

import com.inkdrop.infrastructure.repositories.UserRepository;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@WebFilter(urlPatterns = {"/v1/*", "/v1/**"})
@Slf4j
public class TokenAuthenticationFilter implements Filter {

  @Autowired
  UserRepository userRepository;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    if (httpRequest.getHeader("Access-Control-Request-Method") != null && "OPTIONS"
        .equals(httpRequest.getMethod())) {
      chain.doFilter(httpRequest, response);
      return;
    }

    String backendToken = httpRequest.getHeader("Auth-Token");

    if (backendToken == null) {
      log.info("Token is null");
      httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "No token given");
      return;
    }

    if (userRepository.findByBackendAccessToken(backendToken) == null) {
      httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid token");
      return;
    }

    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {
  }
}
