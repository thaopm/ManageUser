/**
 * Copyright(C), 2019 Luvina Software Company
 * ShowInfoController.java, Jul 30, 2019, Phạm Minh Thảo
 */
package manageuser.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manageuser.entities.UserInfor;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * Servlet thực hiện hiển thị thông tin chi tiết của user lên màn hình ADM005
 * 
 * @author PhamMinhThao
 */
public class ShowUserInfoController extends HttpServlet {

	/**
	 * Tham số đánh dấu việc đọc và ghi servlet cùng 1 phiên bản
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Phương thức doGet thực hiện lấy thông tin user để hiển thị lên màn hình
	 * ADM005 khi di chuyển từ màn hình ADM002 sang và back về từ màn hình ADM003
	 * trường hợp Edit
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Khai báo TblUserLogic và UserInfor
			TblUserLogic userLogic = new TblUserLogicImpl();
			UserInfor userInfor = null;

			// Lấy về id user cần hiển thị thông tin, nếu id user được truyền lên request
			// không thỏa mãn kiểu integer, gán user id = 0
			int id = Common.parseInt(request.getParameter(Constant.ATB_USER_ID), Constant.DEFAULT_USER_ID);

			// Kiểm tra user id có tồn tại trong Db hay không
			if (userLogic.checkExistedUserId(id)) {
				// Lấy về user có id nhận được và hiển thị lên màn hình ADM005
				userInfor = userLogic.getUserInforByUserId(id);
				request.setAttribute(Constant.ATB_USER, userInfor);
				RequestDispatcher dispatcher = request.getRequestDispatcher(Constant.JSP_ADM005);
				dispatcher.forward(request, response);
				// Nếu user id không có trong DB, hiển thị màn hình thông báo
				// lỗi xóa user không tồn tại
			} else {
				response.sendRedirect(Constant.SYSTEM_ERROR_URL + "?error=" + Constant.ER013);
			}
			// Nếu có lỗi, in ra thông báo và hiển thị màn hình System Error
		} catch (Exception e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			response.sendRedirect(Constant.SYSTEM_ERROR_URL);
		}
	}

}
