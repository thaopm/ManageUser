/**
 * Copyright(C), 2019 Luvina Software Company
 * LoginController.java, Jul 22, 2019, Phạm Minh Thảo
 */
package manageuser.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.utils.Constant;
import manageuser.validates.ValidateUser;

/**
 * Lớp Servlet thực hiện chức năng login cho màn hình ADM001
 * 
 * @author PhamMinhThao
 */
public class LoginController extends HttpServlet {

	/**
	 * Tham số đánh dấu việc đọc và ghi servlet cùng 1 phiên bản
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Phương thức doPost thực hiện lấy và kiểm tra thông tin đăng nhập của người
	 * dùng, nếu hợp lệ cho phép log in và hiển thị màn hình ADM002, nếu không, hiển
	 * thị danh sách lỗi và giữ lại login name trên màn hình
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Khởi tạo đối tượng ValidateUser
			ValidateUser validateUser = new ValidateUser();
			// Lấy về login name người dùng nhập
			String loginName = request.getParameter(Constant.LOGIN_NAME);
			// Lấy về pass người dùng nhập
			String password = request.getParameter(Constant.PASSWORD);
			// Lấy về danh sách lỗi từ ValidateUser
			List<String> listError = validateUser.validateLogin(loginName, password);
			if (listError.isEmpty()) {
				// Lấy về session và lưu login name lên session
				HttpSession session = request.getSession();
				session.setAttribute(Constant.LOGIN_NAME, loginName);
				// sendRedirect tới ListUser.do
				response.sendRedirect(Constant.LISTUSER_URL);
			} else {
				// set danh sách lỗi và tên đăng nhập lên attribute cho
				// request
				request.setAttribute(Constant.LIST_ERROR, listError);
				request.setAttribute(Constant.LOGIN_NAME, loginName);
				// tạo đối tượng RequestDispatcher gửi đến ADM001
				RequestDispatcher req = request.getRequestDispatcher(Constant.JSP_ADM001);
				// Forward request
				req.forward(request, response);
			}
			// Nếu có lỗi, in ra và hiển thị màn hình System Error
		} catch (Exception e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			response.sendRedirect(Constant.SYSTEM_ERROR_URL);
		}
	}

	/**
	 * Phương thức doGet gọi đến trang jsp ADM001 để hiển thị lên màn hình
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Hiển thị lên màn hình ADM001
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(Constant.JSP_ADM001);
			dispatcher.forward(request, response);
			// Nếu có lỗi, in ra và hiển thị màn hình System Error
		} catch (Exception e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			response.sendRedirect(Constant.SYSTEM_ERROR_URL);
		}
	}
}
