package kr.mj.gollaba.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CORSFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
	  log.info("init Filter =====> ");
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
	HttpServletRequest request = (HttpServletRequest) servletRequest;
	HttpServletResponse response = (HttpServletResponse) servletResponse;
		
    response.setHeader("Access-Control-Allow-Origin", "*"); // 허용 domain 구분
	response.setHeader("Access-Control-Request-Method", "*");
	response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
	response.setHeader("Access-Control-Max-Age", "3600");
	response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, Authorization, Cookie, OpenApiKey");
    response.setHeader("Content-Type", "application/json");
    response.setHeader("Accept", "application/json");
//    response.setHeader("Access-Control-Allow-Credentials", "false");
    
    if ("OPTIONS".equals(request.getMethod())) {
        response.setStatus(HttpServletResponse.SC_OK);
    } else { 
        ReadableRequestWrapper wrapper = new ReadableRequestWrapper((HttpServletRequest) servletRequest);
        filterChain.doFilter(wrapper, response);
    }
  }

  @Override
  public void destroy() {
  }

}
