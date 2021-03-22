/**
 * Copyright(C), 2019 Luvina Software Company
 * SuccessController.java, Aug 5, 2019, Phạm Minh Thảo
 */
package manageuser.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import manageuser.utils.Constant;
import manageuser.utils.MessageProperties;

/**
 * Servlet xử lý hiển thị thông báo thành công sau khi thêm mới/chỉnh sửa/xóa
 * user
 * 
 * @author PhamMinhThao
 */
public class SuccessController extends HttpServlet {

	/**
	 * Tham số đánh dấu việc đọc và ghi servlet cùng 1 phiên bản
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Phương thức doGet hiển thị thông báo thành công sau khi thêm mới/chỉnh
	 * sửa/xóa user và hiển thị lên màn hình ADM006
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Lấy về kiểu gọi đến trang ADM006
			String type = request.getParameter("type");
			String mess = Constant.EMPTY_STRING;
			// Kiểm tra thông báo được gửi đến,
			// Nếu là insert, gán thông báo thêm mới user thành công
			if (Constant.INSERT_SUCCESS.equals(type)) {
				mess = MessageProperties.getMessage(Constant.MSG001);
				// Nếu là edit, gán thông báo chỉnh sửa user thành công
			} else if (Constant.EDIT_SUCCESS.equals(type)) {
				mess = MessageProperties.getMessage(Constant.MSG002);
				// Nếu là delete, gán thông báo xóa user thành công
			} else if (Constant.DELETE_SUCCESS.equals(type)) {
				mess = MessageProperties.getMessage(Constant.MSG003);
			}
			// Set thông báo lên request và hiển thị sang jsp ADM006
			request.setAttribute(Constant.MESSAGE, mess);
			RequestDispatcher dispatcher = request.getRequestDispatcher(Constant.JSP_ADM006);
			dispatcher.forward(request, response);
			// Nếu có lỗi, in ra thông báo và hiển thị màn hình System Error
		} catch (Exception e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			response.sendRedirect(Constant.SYSTEM_ERROR_URL);
		}
	}
}
