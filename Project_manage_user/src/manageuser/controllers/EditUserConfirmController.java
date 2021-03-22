/**
 * Copyright(C), 2019 Luvina Software Company
 * EditUserConfirmController.java, Aug 11, 2019, Phạm Minh Thảo
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
import manageuser.logics.TblDetailUserJapanLogic;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.TblDetailUserJapanLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Constant;

/**
 * Servlet Xử lí chức năng Edit user ở màn hình ADM004
 * 
 * @author PhamMinhThao
 */
public class EditUserConfirmController extends HttpServlet {

	/**
	 * Tham số đánh dấu việc đọc và ghi servlet cùng 1 phiên bản
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Phương thức doGet lấy thông tin user gửi từ màn hình ADM003 để hiển thị lên
	 * màn hình ADM004
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Lấy về session
			HttpSession session = request.getSession();
			// Lấy về thuộc tính đánh dấu request được gửi từ ADM003
			String fromADM003 = (String) session.getAttribute(Constant.FROM_ADM003);
			// Trường hợp không có thông báo request được gửi từ ADM003 (truy
			// cập trang bằng cách nhập URL), hiển thị thông báo lỗi
			if (fromADM003 == null) {
				response.sendRedirect(Constant.SYSTEM_ERROR_URL);
				// Nếu userInfor = null, gọi đến màn hình System Error và gửi
				// thông báo user không tồn tại
			} else {
				// Lấy về key đánh dấu cho user
				String key = request.getParameter(Constant.KEY);
				// Lấy user thông qua key
				UserInfor userInfor = (UserInfor) session.getAttribute(key);
				// Set user và key lên request để gửi sang trang jsp
				request.setAttribute(Constant.ATB_USER, userInfor);
				request.setAttribute(Constant.KEY, key);
				// Xóa thuộc tính đánh dấu request được gửi từ trang ADM003 để người dùng không
				// thể mở lại trang bằng cách truy cập theo URL
				session.removeAttribute(Constant.FROM_ADM003);
				// Gửi request đến trang jsp ADM004
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
	 * Phương thức doPost xử lí khi người dùng click nút OK trên màn hình ADM004
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Lấy về session
			HttpSession session = request.getSession();
			// Khởi tạo đối tượng TblUserLogic, TblDetailUserJapanLogic
			TblUserLogic userLogic = new TblUserLogicImpl();
			TblDetailUserJapanLogic detailUserJapanLogic = new TblDetailUserJapanLogicImpl();

			// Lấy về key đánh dấu cho user
			String key = request.getParameter(Constant.KEY);
			// Lấy user thông qua key
			UserInfor userInfor = (UserInfor) session.getAttribute(key);
			// Xóa đối tượng userInfor cần thêm khỏi session
			session.removeAttribute(key);

			// Nếu user không tồn tại trong DB, hiển thị màn hình System Error với thông báo
			// lỗi biên tập user không tồn tại
			if (!userLogic.checkExistedUserId(userInfor.getUserId())) {
				response.sendRedirect(Constant.SYSTEM_ERROR_URL + "?error=" + Constant.ER013);
			} else {
				// Kiểm tra email đã tồn tại trong DB chưa, nếu đã tồn tại,
				// hiển thị màn hình System Error với thông báo chương trình có lỗi
				if (userLogic.checkExistedEmail(userInfor.getUserId(), userInfor.getEmail())) {
					response.sendRedirect(Constant.SYSTEM_ERROR_URL);
				} else {
					// Kiểm tra user có thông tin năng lực tiếng Nhật trong DB
					// hay không
					boolean haveJapaneseLevel = detailUserJapanLogic.checkExistedJapaneseLevel(userInfor.getUserId());
					// Gán thuộc tính có thông tin năng lực tiếng Nhật hay không
					// cho đối tượng userInfor
					userInfor.setHaveJapaneseLevel(haveJapaneseLevel);
					// Thực hiện chỉnh sửa thông tin user
					userLogic.editUser(userInfor);
					// Nếu chỉnh sửa thành công, hiển thị màn hình 006 với thông
					// báo edit thành công, nếu có lỗi, thực thi khối lệnh trong catch
					response.sendRedirect(Constant.SUCCESS_URL + "?type=" + Constant.EDIT_SUCCESS);
				}
			}
			// Nếu có lỗi, in ra và di chuyển tới màn hình System Error
		} catch (Exception e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			response.sendRedirect(Constant.SYSTEM_ERROR_URL);
		}
	}

}
