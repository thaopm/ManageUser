/**
 * Copyright(C), 2019 Luvina Software Company
 * AddUserInputController.java, Jul 31, 2019, Phạm Minh Thảo
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
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.MstJapanLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;
import manageuser.validates.ValidateUser;

/**
 * Lớp Servlet thực hiện chức năng thêm mới user cho màn hình ADM003
 * 
 * @author PhamMinhThao
 */
public class AddUserInputController extends HttpServlet {

	/**
	 * Tham số đánh dấu việc đọc và ghi servlet cùng 1 phiên bản
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Phương thức doGet thực hiện xử lý việc hiển thị màn hình ADM003 khi click
	 * thêm mới ở màn hình ADM002 hoặc back từ màn hình ADM004
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Thực hiện set giá trị cho các hạng mục selectbox ở màn hình
			// ADM003
			Common.setDataLogic(request);
			// Thực hiện lấy value của userInfor để hiển thị lên ADM003
			UserInfor userInfor = getDefaultValue(request);
			// Set giá trị user lên request và gửi sang trang JSP ADM003
			request.setAttribute(Constant.ATB_USER, userInfor);
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(Constant.JSP_ADM003);
			dispatcher.forward(request, response);
		} catch (Exception e) {
			// Nếu có lỗi, in ra thông báo và hiển thị màn hình System Error
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			response.sendRedirect(Constant.SYSTEM_ERROR_URL);
		}
	}

	/**
	 * Phương thức doPost thực hiện xử lí khi người dùng click button xác nhận trên
	 * màn hình ADM003
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Khởi tạo session
			HttpSession session = request.getSession();

			// Khởi tạo đối tượng user info với thông tin lấy được từ form
			UserInfor userInfor = getDefaultValue(request);

			// Kiểm tra thông tin user lấy từ request và lưu lỗi vào danh sách lỗi nếu có
			ValidateUser validateUser = new ValidateUser();
			List<String> listError = validateUser.validateUserInfor(userInfor);

			// Nếu danh sách có lỗi
			if (!listError.isEmpty()) {
				// Gán đối tượng user infor và danh sách lỗi lên request
				request.setAttribute(Constant.ATB_USER, userInfor);
				request.setAttribute(Constant.LIST_ERROR, listError);
				// Thực hiện set giá trị cho các hạng mục selectbox ở màn
				// hình ADM003
				Common.setDataLogic(request);
				// Hiển thị màn hình ADM003
				RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(Constant.JSP_ADM003);
				dispatcher.forward(request, response);
				// Nếu danh sách lỗi rỗng
			} else {
				// Lấy group name và gán cho userInfo
				MstGroupLogic mstGroupLogic = new MstGroupLogicImpl();
				String groupName = mstGroupLogic.getMstGroupById(userInfor.getGroupId()).getGroupName();
				userInfor.setGroupName(groupName);

				// Nếu code level của user khác giá trị mặc định (người dùng
				// có chọn code level) Lấy về name level và gán giá trị cho user
				if (!Constant.DEFAULT_CODE_LEVEL.equals(userInfor.getCodeLevel())) {
					MstJapanLogic mstJapanLogic = new MstJapanLogicImpl();
					String levelName = mstJapanLogic.getMstJapanByCodeLevel(userInfor.getCodeLevel()).getNameLevel();
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
				// Hiển thị màn hình ADM004
				response.sendRedirect(Constant.ADD_CONFIRM_URL + "?key=" + key);
			}
			// Nếu có lỗi, in ra thông báo và hiển thị màn hình System Error
		} catch (Exception e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			response.sendRedirect(Constant.SYSTEM_ERROR_URL + "?error=" + Constant.ER013);
		}
	}

	/**
	 * Phương thức lấy về thông tin UserInfor để hiển thị lên màn hình ADM003
	 * 
	 * @param request
	 * @return Đối tượng UserInfor chứa các giá trị
	 * @throws SQLException           Ngoại lệ ném ra khi có lỗi thao tác với CSDL
	 * @throws ClassNotFoundException Ngoại lệ ném ra khi không tìm thấy Class
	 */
	private UserInfor getDefaultValue(HttpServletRequest request) throws ClassNotFoundException, SQLException {

		// Khởi tạo đối tượng UserInfor
		UserInfor userInfor = new UserInfor();

		/**
		 * Lấy về thông tin user từ request, trong trường hợp thêm mới, các param này
		 * không tồn tại trên request, giá trị các thuộc tính là null (giá trị hiển thị
		 * trên màn hình ADM003 là rỗng)
		 */

		String loginName = Common.getStringFromRequest(request.getParameter(Constant.LOGIN_NAME));
		int groupId = Common.parseInt(request.getParameter(Constant.ATB_GROUP_ID), Constant.DEFAULT_GROUP_ID);
		String fullname = Common.getStringFromRequest(request.getParameter(Constant.ATB_NAME));
		String nameKana = Common.getStringFromRequest(request.getParameter(Constant.NAME_KATAKANA));
		String email = Common.getStringFromRequest(request.getParameter(Constant.EMAIL));
		String tel = Common.getStringFromRequest(request.getParameter(Constant.TEL));
		String password = Common.getStringFromRequest(request.getParameter(Constant.PASSWORD));
		String rePassword = Common.getStringFromRequest(request.getParameter(Constant.REPASSWORD));
		String codeLevel = Common.getStringFromRequest(request.getParameter(Constant.CODE_LEVEL));
		String total = Common.getStringFromRequest(request.getParameter(Constant.TOTAL));

		// Khởi tạo các giá trị mặc định cho đối tượng UserInfor
		String birthday = Constant.EMPTY_STRING;
		String startDate = Constant.EMPTY_STRING;
		String endDate = Constant.EMPTY_STRING;

		// Lấy về kiểu action gọi đến màn hình ADM003
		String action = request.getParameter(Constant.ACTION);

		// Trường hợp hiển thị màn hình ADM003 từ nút thêm mới của ADM002 (param action
		// không tồn tại)
		if (action == null) {

			// Set các giá trị birthday, start date, end date là ngày hiện
			// tại
			int currentYear = Common.getYearNow();
			int currentMonth = Common.getMonthNow();
			int currentDay = Common.getDayNow();

			birthday = Common.convertToString(currentYear, currentMonth, currentDay);
			startDate = Common.convertToString(currentYear, currentMonth, currentDay);
			endDate = Common.convertToString(currentYear + 1, currentMonth, currentDay);

		} else {
			// Nếu param action tồn tại
			switch (action) {
			// Nếu action = back (Click back ở màn hình ADM004)
			case Constant.BACK:
				// Lấy về session
				HttpSession session = request.getSession();
				// Lấy key đánh dấu session từ request
				String key = request.getParameter(Constant.KEY);
				// Lấy userInfor thông qua key
				userInfor = (UserInfor) session.getAttribute(key);
				// Xóa user đánh dấu bởi key vừa được get trên session
				session.removeAttribute(key);
				break;
			// Nếu action = confirm (Lấy về thông tin người dùng nhập khi
			// click nút Xác nhận)
			case Constant.CONFIRM:

				// Lấy về năm, tháng, ngày của birthday và khởi tạo birthday
				int year = Integer.parseInt(request.getParameter(Constant.YEAR_BIRTHDAY));
				int month = Integer.parseInt(request.getParameter(Constant.MONTH_BIRTHDAY));
				int day = Integer.parseInt(request.getParameter(Constant.DAY_BIRTHDAY));
				birthday = Common.convertToString(year, month, day);

				// Lấy về năm, tháng, ngày của start date và khởi tạo start
				// date
				year = Integer.parseInt(request.getParameter(Constant.YEAR_START_DATE));
				month = Integer.parseInt(request.getParameter(Constant.MONTH_START_DATE));
				day = Integer.parseInt(request.getParameter(Constant.DAY_START_DATE));
				startDate = Common.convertToString(year, month, day);

				// Lấy về năm, tháng, ngày của end date và khởi tạo end date
				year = Integer.parseInt(request.getParameter(Constant.YEAR_END_DATE));
				month = Integer.parseInt(request.getParameter(Constant.MONTH_END_DATE));
				day = Integer.parseInt(request.getParameter(Constant.DAY_END_DATE));
				endDate = Common.convertToString(year, month, day);

				break;
			}
		}

		// Nếu kiểu gọi đến trang 003 không phải back từ 004, set các
		// giá trị cho đối tượng user
		if (!Constant.BACK.equals(action)) {
			userInfor.setLoginName(loginName);
			userInfor.setGroupId(groupId);
			userInfor.setFullName(fullname);
			userInfor.setFullNameKana(nameKana);
			userInfor.setBirthday(birthday);
			userInfor.setEmail(email);
			userInfor.setTel(tel);
			userInfor.setPassword(password);
			userInfor.setRePassword(rePassword);
			userInfor.setCodeLevel(codeLevel);
			userInfor.setTotal(total);
			userInfor.setStartDate(startDate);
			userInfor.setEndDate(endDate);
		}
		// Trả về đối tượng user
		return userInfor;
	}

}
