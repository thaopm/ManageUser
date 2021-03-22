/**
 * Copyright(C) 2019 Luvina Software Company
 * AddUserConfirmController.java, Aug 1, 2019, Phạm Minh Thảo
 */
package manageuser.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.entities.UserInfor;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Constant;

/**
 * Servlet xử lý chức năng xác nhận add user cho màn hình ADM004
 * 
 * @author PhamMinhThao
 */
public class AddUserConfirmController extends HttpServlet {

	/**
	 * Tham số đánh dấu việc đọc và ghi servlet cùng 1 phiên bản
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Phương thức doGet xử lý việc nhận user gửi từ màn hình ADM003 và hiển thị lên
	 * ADM004 nếu thông tin user có tồn tại trên session, ngược lại, hiển thị màn
	 * hình System Error
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		try {
			// Lấy về thuộc tính đánh dấu request được gửi từ ADM003
			String fromADM003 = (String) session.getAttribute(Constant.FROM_ADM003);
			// Trường hợp không có thông báo request được gửi từ ADM003 (TH truy
			// cập trang bằng cách nhập URL), hiển thị thông báo lỗi
			if (fromADM003 == null) {
				response.sendRedirect(Constant.SYSTEM_ERROR_URL);
			} else {
				// Lấy về key đánh dấu user trên session
				String key = request.getParameter(Constant.KEY);
				// Lấy user thông qua key đánh dấu user
				UserInfor userInfor = (UserInfor) session.getAttribute(key);
				// Set user và key lên request để hiển thị sang trang jsp
				request.setAttribute(Constant.ATB_USER, userInfor);
				request.setAttribute(Constant.KEY, key);
				// Xóa thuộc tính đánh dấu request được gửi từ trang ADM003 để người dùng không
				// thể mở lại trang bằng cách truy cập theo URL
				session.removeAttribute(Constant.FROM_ADM003);
				// Hiển thị trang jsp ADM004
				RequestDispatcher dispatcher = request.getRequestDispatcher(Constant.JSP_ADM004);
				dispatcher.forward(request, response);
			}
			// Nếu có lỗi, in ra lỗi và hiển thị trang System Error
		} catch (Exception e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			response.sendRedirect(Constant.SYSTEM_ERROR_URL);
		}
	}

	/**
	 * Phương thức doPost lấy về đối tượng userInfor lưu trên session và xử lý việc
	 * click nút OK để thêm mới user trên màn hình ADM004. Nếu thêm mới thành công,
	 * hiển thị màn hình ADM006 với thông báo Thêm mới thành công, ngược lại, hiển
	 * thị màn hình System Error
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// Lấy về session
			HttpSession session = request.getSession();
			// Khởi tạo đối tượng TblUserLogic, MstGroupLogic, MstJapanLogic
			TblUserLogic userLogic = new TblUserLogicImpl();

			// Lấy về key đánh dấu cho user
			String key = request.getParameter(Constant.KEY);
			// Lấy user thông qua key
			UserInfor userInfor = (UserInfor) session.getAttribute(key);
			// Xóa key đánh dấu đối tượng userInfor cần thêm khỏi session
			session.removeAttribute(key);

			// Kiểm tra login name và email được thêm mới đã tồn tại trong DB chưa, nếu đã
			// tồn tại, hiển thị màn hình System Error
			if (userLogic.checkExistedLoginName(userInfor.getLoginName())
					|| userLogic.checkExistedEmail(userInfor.getUserId(), userInfor.getEmail())) {
				response.sendRedirect(Constant.SYSTEM_ERROR_URL);
			} else {
				// Nếu cả login name và email đều chưa tồn tại, thực hiện thêm mới user
				userLogic.createUser(userInfor);
				// Nếu thêm mới thành công, hiển thị màn hình 006 với thông
				// báo thêm mới thành công, nếu có lỗi, thực thi khối lệnh trong catch
				response.sendRedirect(Constant.SUCCESS_URL + "?type=" + Constant.INSERT_SUCCESS);
			}
			// Nếu có lỗi, in ra lỗi và hiển thị màn hình System Error
		} catch (Exception e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			response.sendRedirect(Constant.SYSTEM_ERROR_URL);
		}
	}
}
