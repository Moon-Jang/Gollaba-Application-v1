package kr.mj.gollaba.config.filter;

import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpMediaTypeNotSupportedException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
public class ReadableRequestFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		if ("OPTIONS".equals(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
			return;
		}

		if (request.getContentType() == null || request.getContentType().startsWith(MediaType.MULTIPART_FORM_DATA_VALUE)) {
			filterChain.doFilter(request, response);
			return;
		}

		ReadableRequestWrapper wrapper = new ReadableRequestWrapper((HttpServletRequest) servletRequest);
		filterChain.doFilter(wrapper, response);
	}

	@Override
	public void destroy() {
	}

}
