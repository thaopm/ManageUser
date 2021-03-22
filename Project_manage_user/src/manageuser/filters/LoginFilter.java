/**
 * Copyright(C), 2019 Luvina Software Company
 * LogInFilter.java, Aug 5, 2019, Phạm Minh Thảo
 */
package manageuser.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * Filter check login trước khi cho phép người dùng truy cập vào một URL
 * 
 * @author PhamMinhThao
 */
public class LoginFilter implements Filter {

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {

	}

	/**
	 * Phương thức doFilter kiểm tra login và chuyển hướng URL dựa trên trạng thái
	 * login và URL được gọi đến
	 * 
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		try {
			HttpSession session = request.getSession();
			// Lấy servlet path từ request
			String servletPath = request.getServletPath();
			String loginPath = "/" + Constant.LOGIN_URL;

			// Kiểm tra login
			boolean loggedIn = Common.checkLogIn(session);
			// Nếu servletPath khớp với đường dẫn đến url login hoặc đường dẫn của trang ADM001.jsp 
			if (servletPath.equals(loginPath) || servletPath.equals(Constant.JSP_ADM001)) {
				// Nếu đã đăng nhập
				if (loggedIn) {
					// Chuyển hướng đến màn hình List User
					response.sendRedirect(Constant.LISTUSER_URL);
				} else {
					// Nếu chưa đăng nhập, cho qua, hiển thị màn hình ADM001
					chain.doFilter(req, res);
				}
				// Nếu servletPath không khớp với đường dẫn đến màn hình login
			} else {
				// Nếu đã đăng nhập, cho qua
				if (loggedIn) {
					chain.doFilter(req, res);
				} else {
					// Nếu chưa đăng nhập, chuyển hướng tới màn hình Login
					response.sendRedirect(Constant.LOGIN_URL);
				}
			}

			// Nếu có lỗi, in ra thông báo và hiển thị màn hình System Error
		} catch (Exception e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			response.sendRedirect(Constant.SYSTEM_ERROR_URL + Constant.ER015);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

	}

}
