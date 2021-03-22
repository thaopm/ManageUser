/**
 * Copyright(C), 2019 Luvina Software Company
 * CharacterEncodingFilter.java, Aug 10, 2019, Phạm Minh Thảo
 */
package manageuser.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import manageuser.utils.Constant;

/**
 * Filter cài đặt kiểu dữ liệu UTF-8 cho các Controller
 * 
 * @author PhamMinhThao
 */
@WebFilter(urlPatterns = { "*.do" })
public class CharacterEncodingFilter implements Filter {

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {

	}

	/**
	 * Set UTF-8 cho các Servlet
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// Ép kiểu request về HttpServletRequest
		HttpServletRequest req = (HttpServletRequest) request;
		// Set định dạng encode UTF-8 cho request
		req.setCharacterEncoding(Constant.UTF8_ENCODING);
		// cho qua
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

	}

}
