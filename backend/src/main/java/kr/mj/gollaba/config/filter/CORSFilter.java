package kr.mj.gollaba.config.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static kr.mj.gollaba.config.SecurityConfig.REFRESH_TOKEN_HEADER;

@Component
@Order(0)
public class CORSFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		response.setHeader("Access-Control-Allow-Origin", "*"); // 허용 domain 구분
		response.setHeader("Access-Control-Request-Method", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, Authorization, Cookie," + REFRESH_TOKEN_HEADER);
		response.setHeader("Content-Type", "application/json");
		response.setHeader("Accept", "application/json");
//		response.setHeader("Access-Control-Allow-Credentials", "false");

		filterChain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
