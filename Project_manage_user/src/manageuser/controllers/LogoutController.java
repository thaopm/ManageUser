/**
 * Copyright(C), 2019 Luvina Software Company
 * LogoutController.java, Jul 22, 2019, Phạm Minh Thảo
 */
package manageuser.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.utils.Constant;

/**
 * Lớp Servlet thực hiện chức năng logout cho link logout
 * 
 * @author PhamMinhThao
 */
public class LogoutController extends HttpServlet {

	/**
	 * Tham số đánh dấu việc đọc và ghi servlet cùng 1 phiên bản
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Phương thức doGet thực hiện hiện chức năng logout khi người dùng click link
	 * logout
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Lấy về session
			HttpSession session = request.getSession();
			// Vô hiệu hóa session
			session.invalidate();
			// Trở về trang login
			response.sendRedirect(Constant.LOGIN_URL);
		} catch (Exception e) {
			// Bắt ngoại lệ và in ra lỗi
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			response.sendRedirect(Constant.SYSTEM_ERROR_URL);
		}
	}

}
