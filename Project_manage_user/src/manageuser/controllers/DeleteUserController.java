/**
 * Copyright(C), 2019 Luvina Software Company
 * DeleteUserController.java, Aug 13, 2019, Phạm Minh Thảo
 */
package manageuser.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Constant;

/**
 * Servlet Xử lí chức năng xóa user khi click vào nút xóa ở màn hình ADM005
 * 
 * @author PhamMinhThao
 */
public class DeleteUserController extends HttpServlet {

	/**
	 * Tham số đánh dấu việc đọc và ghi servlet cùng 1 phiên bản
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Phương thức doPost xử lý xóa user trong DB
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Khởi tạo đối tượng TblUserLogic
			TblUserLogic userLogic = new TblUserLogicImpl();

			// Lấy về userId trên request
			int userId = Integer.parseInt(request.getParameter(Constant.ATB_USER_ID));
			// Kiểm tra xem userId có phải là id của admin hay không
			if (!userLogic.checkAdminById(userId)) {
				// Nếu không phải admin, thực hiện xóa user
				userLogic.deleteUser(userId);
				// Nếu xóa thành công (không có lỗi) hiển thị màn hình thông báo
				// xóa user thành công
				response.sendRedirect(Constant.SUCCESS_URL + "?type=" + Constant.DELETE_SUCCESS);
			} else {
				// Nếu user là admin, hiển thị màn hình System Error với thông
				// báo không thể xóa admin
				response.sendRedirect(Constant.SYSTEM_ERROR_URL + "?error=" + Constant.ER020);
			}
			// Nếu có lỗi, in ra thông báo và hiển thị màn hình báo lỗi
		} catch (Exception e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + e.getMessage());
			response.sendRedirect(Constant.SYSTEM_ERROR_URL);
		}
	}
}
