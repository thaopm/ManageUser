/**
 * Copyright(C), 2019 Luvina Software Company
 * EditUserInputController.java, Aug 10, 2019, Phạm Minh Thảo
 */
package manageuser.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.entities.UserInfor;
import manageuser.logics.MstGroupLogic;
import manageuser.logics.MstJapanLogic;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.MstJapanLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;
import manageuser.validates.ValidateUser;

/**
 * Servlet Xử lí chức năng Edit user ở màn hình ADM003
 * 
 * @author PhamMinhThao
 */
public class EditUserInputController extends HttpServlet {

	/**
	 * Tham số đánh dấu việc đọc và ghi servlet cùng 1 phiên bản
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Phương thức doGet xử lý việc nhận user gửi từ màn hình ADM005 và hiển thị lên
	 * ADM005 nếu user có tồn tại trong DB, ngược lại, hiển thị màn hình System
	 * Error với thông báo user không tồn tại
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Lấy giá trị userInfo để hiển thị lên màn hình ADM003
			UserInfor userInfor = getDefaultValue(request);
			// Nếu user tồn tại
			if (userInfor != null) {
				// Thực hiện thiết lập giá trị cho select box
				Common.setDataLogic(request);
				// Set giá trị user lên request và gửi sang trang JSP ADM003
				request.setAttribute(Constant.ATB_USER, userInfor);
				RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(Constant.JSP_ADM003);
				dispatcher.forward(request, response);
				// Nếu user = null (không tồn tại) hiển thị màn hình System
				// Error với mã lỗi ER013 (User không tồn tại)
			} else {
				response.sendRedirect(Constant.SYSTEM_ERROR_URL + "?error=" + Constant.ER013);
			}
			// Nếu có lỗi, in ra và di chuyển tới màn hình System Error
		} catch (Exception e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + e.getMessage());
			response.sendRedirect(Constant.SYSTEM_ERROR_URL);
		}
	}

	/**
	 * Lấy giá trị của user từ request
	 * 
	 * @param request
	 * @return Đối tượng UserInfor chứa giá trị để hiển thị lên ADM003
	 * @throws SQLException           Ngoại lệ ném ra khi có lỗi thao tác với CSDL
	 * @throws ClassNotFoundException Ngoại lệ ném ra khi không tìm thấy Class
	 */
	private UserInfor getDefaultValue(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		// Khởi tạo đối tượng userInfor để trả về
		UserInfor userInfor = null;
		// Lấy về kiểu gọi đến Controller
		String action = request.getParameter(Constant.ACTION);

		TblUserLogic userLogic = new TblUserLogicImpl();
		// Kiểm tra action gọi đến màn hình ADM003
		switch (action) {
		// Nếu action = edit (Click chỉnh sửa ở màn hình ADM005)
		case Constant.EDIT:
			// Lấy về user id lưu trên request
			int userId = Common.parseInt(request.getParameter(Constant.ATB_USER_ID), Constant.DEFAULT_USER_ID);
			// Kiểm tra user có tồn tại trong DB hay không
			if (userLogic.checkExistedUserId(userId)) {
				// Nếu có tồn tại, thực hiện lấy về thông tin user trong DB
				userInfor = userLogic.getUserInforByUserId(userId);
				// Nếu user chưa có thông tin năng lực tiếng Nhật, gán giá
				// trị cho start date và end date để hiển thị lên select box
				if (Constant.DEFAULT_CODE_LEVEL.equals(userInfor.getCodeLevel())) {

					// Lấy về năm, tháng, ngày hiện tại
					int currentYear = Common.getYearNow();
					int currentMonth = Common.getMonthNow();
					int currentDay = Common.getDayNow();

					// Tạo start date và end date rồi gán cho user
					String startDate = Common.convertToString(currentYear, currentMonth, currentDay);
					String endDate = Common.convertToString(currentYear + 1, currentMonth, currentDay);
					userInfor.setStartDate(startDate);
					userInfor.setEndDate(endDate);
				}
			}
			break;
		// Nếu action = back (Click back ở màn hình ADM004)
		case Constant.BACK:
			// Lấy về session
			HttpSession session = request.getSession();
			// Lấy key đánh dấu user từ request
			String key = request.getParameter(Constant.KEY);
			// Lấy userInfor thông qua key
			userInfor = (UserInfor) session.getAttribute(key);
			// Xóa user đánh dấu bởi key vừa được get trên session
			session.removeAttribute(key);
			break;

		// Nếu action = confirm (Lấy về thông tin người dùng nhập khi click
		// nút Xác nhận)
		case Constant.CONFIRM:
			// Khởi tạo đối tượng userInfor
			userInfor = new UserInfor();
			// Lấy về user id lưu trên request
			userId = Common.parseInt(request.getParameter(Constant.ATB_USER_ID), Constant.DEFAULT_USER_ID);
			// Lấy về thông tin groupId, fullname, tên kana, email, tel, codeLevel, total từ
			// request

			// Lấy về năm, tháng, ngày của birthday và khởi tạo birthday
			int year = Integer.parseInt(request.getParameter(Constant.YEAR_BIRTHDAY));
			int month = Integer.parseInt(request.getParameter(Constant.MONTH_BIRTHDAY));
			int day = Integer.parseInt(request.getParameter(Constant.DAY_BIRTHDAY));
			String birthday = Common.convertToString(year, month, day);

			// Lấy về năm, tháng, ngày của start date và khởi tạo start date
			year = Integer.parseInt(request.getParameter(Constant.YEAR_START_DATE));
			month = Integer.parseInt(request.getParameter(Constant.MONTH_START_DATE));
			day = Integer.parseInt(request.getParameter(Constant.DAY_START_DATE));
			String startDate = Common.convertToString(year, month, day);

			// Lấy về năm, tháng, ngày của end date và khởi tạo end date
			year = Integer.parseInt(request.getParameter(Constant.YEAR_END_DATE));
			month = Integer.parseInt(request.getParameter(Constant.MONTH_END_DATE));
			day = Integer.parseInt(request.getParameter(Constant.DAY_END_DATE));
			String endDate = Common.convertToString(year, month, day);
			
			// Lấy về thông tin groupId, fullname, tên kana, email, tel, codeLevel, total từ
			// request và gán các thuộc tính cho đối tượng userInfor
			userInfor.setUserId(userId);
			userInfor.setLoginName(Common.getStringFromRequest(request.getParameter(Constant.LOGIN_NAME)));
			userInfor.setGroupId(Integer.parseInt(request.getParameter(Constant.ATB_GROUP_ID)));
			userInfor.setFullName(Common.getStringFromRequest(request.getParameter(Constant.ATB_NAME)));
			userInfor.setFullNameKana(Common.getStringFromRequest(request.getParameter(Constant.NAME_KATAKANA)));
			userInfor.setBirthday(birthday);
			userInfor.setEmail(Common.getStringFromRequest(request.getParameter(Constant.EMAIL)));
			userInfor.setTel(Common.getStringFromRequest(request.getParameter(Constant.TEL)));
			userInfor.setCodeLevel(request.getParameter(Constant.CODE_LEVEL));
			userInfor.setTotal(Common.getStringFromRequest(request.getParameter(Constant.TOTAL)));
			userInfor.setStartDate(startDate);
			userInfor.setEndDate(endDate);
			break;
		default:
			break;
		}
		return userInfor;
	}

	/**
	 * Phương thức doPost xử lý việc click button xác nhận trên màn hình ADM003.
	 * Phương thức này kiểm tra thông tin người dùng nhập, nếu hợp lệ, cho phép hiển
	 * thị màn hình ADM004, nếu không, hiển thị danh sách lỗi chỉ ra các hạng mục
	 * không hợp lệ lên màn hình ADM003
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Khởi tạo session
			HttpSession session = request.getSession();
			// Khởi tạo TblUserLogic
			TblUserLogic userLogic = new TblUserLogicImpl();
			// Khởi tạo đối tượng user info lấy được từ request
			UserInfor userInfor = getDefaultValue(request);

			if (userLogic.checkExistedUserId(userInfor.getUserId())) {

				// Khởi tạo validateUser
				ValidateUser validateUser = new ValidateUser();
				// Kiểm tra user lấy được từ request
				List<String> listError = validateUser.validateUserInfor(userInfor);

				// Nếu danh sách có lỗi
				if (!listError.isEmpty()) {
					// Trả về đối tượng user info
					request.setAttribute(Constant.ATB_USER, userInfor);
					// Trả về danh sách lỗi
					request.setAttribute(Constant.LIST_ERROR, listError);
					// Thực hiện set giá trị cho các hạng mục selectbox ở màn
					// hình ADM003
					Common.setDataLogic(request);
					// Hiển thị lên màn hình ADM003
					RequestDispatcher dispatcher = request.getServletContext()
							.getRequestDispatcher(Constant.JSP_ADM003);
					dispatcher.forward(request, response);
					// Nếu danh sách lỗi rỗng
				} else {

					// Lấy group name và gán cho userInfo
					MstGroupLogic mstGroupLogic = new MstGroupLogicImpl();
					String groupName = mstGroupLogic.getMstGroupById(userInfor.getGroupId()).getGroupName();
					userInfor.setGroupName(groupName);

					// Nếu code level của user khác giá trị mặc định (người dùng
					// có chọn code level) Lấy về name level và gán giá trị cho
					// user
					if (!Constant.DEFAULT_CODE_LEVEL.equals(userInfor.getCodeLevel())) {
						MstJapanLogic mstJapanLogic = new MstJapanLogicImpl();
						String levelName = mstJapanLogic.getMstJapanByCodeLevel(userInfor.getCodeLevel())
								.getNameLevel();
						userInfor.setNameLevel(levelName);
					}

					/**
					 * Tạo key đánh dấu user trên session để khi click back từ màn hình ADM004, màn
					 * hình ADM003 vẫn hiển thị đúng thông tin vừa nhập (phòng trường hợp mở 2 tab,
					 * nếu không đánh dấu 2 user bằng 2 key khác nhau, khi click back ở ADM004, màn
					 * hình ADM003 sẽ hiển thị thông tin được nhập ở tab gần nhất)
					 */
					String key = Common.createKey();
					session.setAttribute(key, userInfor);
					// Gán thuộc tính đánh dấu request được gửi từ trang ADM003 để tránh trường hợp
					// truy cập ADM004 không qua ADM003 mà qua URL
					session.setAttribute(Constant.FROM_ADM003, Constant.FROM_ADM003);
					response.sendRedirect(Constant.EDIT_CONFIRM_URL + "?key=" + key);
				}
				// Nếu user = null (không tồn tại user) hiển thị màn hình System
				// Error với mã lỗi ER013 (User không tồn tại)
			} else {
				response.sendRedirect(Constant.SYSTEM_ERROR_URL + "?error=ER013");
			}
			// Nếu có lỗi, in ra thông báo và hiển thị màn hình System Error
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			response.sendRedirect(Constant.SYSTEM_ERROR_URL);
		}
	}

}
