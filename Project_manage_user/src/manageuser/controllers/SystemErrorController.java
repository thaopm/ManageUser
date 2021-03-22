/**
 * Copyright(C), 2019 Luvina Software Company
 * SystemErrorController.java, Jul 27, 2019, Phạm Minh Thảo
 */
package manageuser.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manageuser.utils.Constant;
import manageuser.utils.MessageErrorProperties;

/**
 * Lớp Servlet thực hiện chức năng hiển thị System Error
 * 
 * @author PhamMinhThao
 */
public class SystemErrorController extends HttpServlet {
	/**
	 * Tham số đánh dấu việc đọc và ghi servlet cùng 1 phiên bản
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Phương thức doGet thực hiện lấy về thông báo lỗi và hiển thị lên màn hình
	 * System Error
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Lấy về mã thông báo lỗi
			String keyMess = request.getParameter(Constant.ERRORS);
			String errorMess = Constant.EMPTY_STRING;
			// Nếu trên request không lưu mã thông báo lỗi, gán thông báo lỗi mặc định là
			// ER015 (Hệ thống phát sinh lỗi)
			if (keyMess == null) {
				errorMess = MessageErrorProperties.getMessage(Constant.ER015);
				// Nếu có, lấy về mã lỗi và đọc nội dung từ file message_error_ja
			} else {
				errorMess = MessageErrorProperties.getMessage(keyMess);
			}
			// Lấy về lỗi được truyền và set lên request
			request.setAttribute(Constant.ERRORS, errorMess);
			// Hiển thị lên màn hình System Error
			RequestDispatcher dispatcher = request.getRequestDispatcher(Constant.JSP_SYSTEM_ERROR);
			dispatcher.forward(request, response);
		} catch (Exception e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
		}
	}

}
