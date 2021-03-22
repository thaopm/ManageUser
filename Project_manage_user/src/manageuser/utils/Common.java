/**
 * Copyright(C)Jul 17, 2019 Luvina Software Company
 * Common.java, Jul 17, 2019, Phạm Minh Thảo
 */
package manageuser.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import manageuser.entities.MstGroup;
import manageuser.entities.MstJapan;
import manageuser.logics.MstGroupLogic;
import manageuser.logics.MstJapanLogic;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.MstJapanLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;

/**
 * Lớp định nghĩa các phương thức chung cho project
 * 
 * @author pmthao
 *
 */
public class Common {

	/**
	 * Phương thức kiểm tra admin đã đăng nhập hay chưa (session đã tồn tại hay
	 * và login name là login name của admin hay không)
	 * 
	 * @param session truyền vào session
	 * @return true nếu đã đăng nhập, false nếu chưa
	 * @throws SQLException           Ném Exception khi xảy ra lỗi thao tác với CSDL
	 * @throws ClassNotFoundException Ném ngoại lệ khi không tìm thấy class
	 */
	public static boolean checkLogIn(HttpSession session) throws ClassNotFoundException, SQLException {
		// Khai báo biến kiểm tra log in
		boolean loggedIn = false;
		try {
			// Nếu trên session có lưu giá trị login name
			if (session.getAttribute(Constant.LOGIN_NAME) != null) {
				// Thực hiện lấy về và kiểm tra login name có phải login name
				// của admin hay không
				String loginId = (String) session.getAttribute(Constant.LOGIN_NAME);
				TblUserLogic tblUserLogic = new TblUserLogicImpl();
				// Nếu là admin, gán biến kiểm tra bằng true
				if (tblUserLogic.checkAdminByLoginName(loginId)) {
					loggedIn = true;
				}
			}
			// Nếu có lỗi, in ra thông báo và ném ngoại lệ
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Errors: Common.checkLogIn:\n" + e.getMessage());
			throw e;
		} // trả về kết quả
		return loggedIn;
	}

	/**
	 * Phương thức Tính tổng số trang
	 * 
	 * @param totalUsers tổng số User
	 * @param limit      số lượng user cần hiển thị trên 1 trang
	 * @return tổng số trang
	 */
	public static int getTotalPage(int totalUsers, int limit) {
		int number = 0; // Khai báo số trang
		// Nếu tổng số user chia hết cho số user hiển thị trên 1 trang
		if (totalUsers % limit == 0) {
			// số trang = số user chia limit
			number = totalUsers / limit;
		} else {
			// Nếu tổng số user không chia hết cho số user hiển thị trên
			// 1 trang, số trang = số user chia limit (lấy phần nguyên) + 1
			number = totalUsers / limit + 1;
		}
		return number; // trả về số trang
	}

	/**
	 * Phương thức lấy về số lượng page tối đa được hiển thị trên màn hình ADM002.
	 * Giá trị này được quy đinh trong file config.properties
	 * 
	 * @return số lượng page tối đa được hiển thị
	 */
	public static int getLimitPage() {
		return Integer.parseInt(ConfigProperties.getProperty(Constant.LIMIT_PAGE));
	}

	/**
	 * Phương thức lấy về vị trí data cần lấy
	 * 
	 * @param limit       số lượng user cần hiển thị trên 1 trang
	 * @param currentPage Trang hiện tại
	 * @return vị trí cần lấy
	 */
	public static int getOffset(int currentPage, int limit) {
		// Vị trí cần lấy = số trang trước trang hiện tại * limit
		return (currentPage - 1) * limit;
	}

	/**
	 * Phương thức lấy về số lượng bản ghi hiển thị trên 1 trang
	 * 
	 * @return số lượng bản ghi hiển thị trên 1 trang
	 */
	public static int getLimit() {
		// Đọc file config.properties để lấy về số lượng bản ghi hiển thị trên 1
		// trang
		return Integer.parseInt(ConfigProperties.getProperty("LIMIT"));
	}

	/**
	 * Phương thức tạo chuỗi paging
	 * 
	 * @param totalUsers  Tổng sô user
	 * @param limit       Số lượng user cần hiển thị trên 1 trang
	 * @param currentPage Trang hiện tại
	 * @return Danh sách các trang cần hiển thị ở chuỗi paging theo trang hiện tại
	 */
	public static List<Integer> getListPaging(int totalUsers, int limit, int currentPage) {
		// Khai báo danh sách trang trả về
		List<Integer> listPaging = new ArrayList<Integer>();
		// Lấy về tổng số trang
		int pageNumberTotal = getTotalPage(totalUsers, limit);
		// Lấy về số trang được phép hiển thị
		int pageNumberDisplay = getLimitPage();

		// Lấy về số thứ tự của trang được hiển thị đầu tiên
		int firstPage = (currentPage - 1) / pageNumberDisplay * pageNumberDisplay + 1;
		// Lấy về số thứ tự của trang được hiển thị cuối cùng
		int endPage = firstPage + pageNumberDisplay - 1;
		// Nếu số thứ tự trang cuối lớn hơn tổng số trang
		if (endPage > pageNumberTotal) {
			// Gán số thứ tự trang cuối bằng tổng số trang
			endPage = pageNumberTotal;
		}
		// Thêm số thứ tự các trang vào danh sách trang
		for (int i = firstPage; i <= endPage; i++) {
			listPaging.add(i);
		}
		return listPaging;
	}

	/**
	 * Phương thức lấy trang đầu tiên trong 3 trang được hiển thị
	 * 
	 * @param listPaging danh sách trang được hiển thị
	 * @return Trang đầu tiên được hiển thị
	 */
	public static int getFirstPage(List<Integer> listPaging) {
		int page = 0;
		if (listPaging.size() != 0) {
			page = listPaging.get(0);
		}
		return page;
	}

	/**
	 * Phương thức lấy trang cuối cùng trong 3 trang được hiển thị
	 * 
	 * @param listPaging danh sách trang được hiển thị
	 * @return Trang cuối cùng được hiển thị
	 */
	public static int getLastPage(List<Integer> listPaging) {
		int page = 0;
		if (listPaging.size() != 0) {
			page = listPaging.get(listPaging.size() - 1);
		}
		return page;
	}

	/**
	 * Phương thức mã hóa password cùng với salt
	 * 
	 * @param input giá trị string cần mã hóa được truyền vào
	 * @throws NoSuchAlgorithmException Ngoại lệ ném ra khi gọi thuật toán mã hóa
	 * @return Trả về chuỗi string đã được mã hóa
	 */
	public static String getEncryptedPass(String password, String salt) throws NoSuchAlgorithmException {
		String encryptedPass = Constant.EMPTY_STRING;
		try {
			StringBuilder stringBuilder = new StringBuilder();
			String input = password + salt;
			/// Sử dụng đối tượng MessageDigest để cấu hình hàm băm sử dụng thuật toán SHA-1
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			// Mật khẩu sau khi băm sẽ được trả về dưới dạng mảng byte
			byte[] bytes = md.digest(input.getBytes());
			// Đưa mảng byte vào hàm mã hóa
			for (int i = 0; i < bytes.length; i++) {
				// Convert ký tự của chuỗi thành chuỗi Hex dạng String
				// Integer.toString (src, 16) để chuyển đổi số nguyên thành một chuỗi
				// thập lục phân
				stringBuilder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			encryptedPass = stringBuilder.toString();
			// In ra lỗi và ném ngoại lệ
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Common.getEncryptedPass:\n" + e.getMessage());
			throw e;
		}
		return encryptedPass;
	}

	/**
	 * Phương thức lấy về danh sách các năm
	 * 
	 * @param fromYear năm đầu tiên
	 * @param toYear   năm cuối cùng
	 * @return danh sách các năm
	 */
	public static ArrayList<Integer> getListYear(int fromYear, int toYear) {
		// Khởi tạo danh sách năm
		ArrayList<Integer> listYear = null;
		// Thêm các năm từ fromYear - toYear vào danh sách
		if (fromYear < toYear) {
			listYear = new ArrayList<Integer>();
			for (int i = fromYear; i <= toYear; i++) {
				listYear.add(i);
			}
		}
		return listYear;
	}

	/**
	 * Phương thức lấy về danh sách các tháng trong năm
	 * 
	 * @return các tháng trong năm
	 */
	public static List<Integer> getListMonth() {
		// Khởi tạo danh sách tháng trong năm
		List<Integer> listMonth = new ArrayList<Integer>();
		// Thêm các tháng vào danh sách
		for (int i = 1; i <= 12; i++) {
			listMonth.add(i);
		}
		return listMonth;
	}

	/**
	 * Phương thức lấy về danh sách các ngày trong tháng
	 * 
	 * @return danh sách các ngày trong tháng
	 */
	public static ArrayList<Integer> getListDay() {
		// Khởi tạo danh sách ngày trong tháng
		ArrayList<Integer> listDay = new ArrayList<>();
		// Thêm các ngày vào danh sách
		for (int i = 1; i <= 31; i++) {
			listDay.add(i);
		}
		return listDay;
	}

	/**
	 * Phương thức tạo ra một chuỗi năm tháng ngày từ các giá trị năm, tháng, ngày
	 * được truyền vào
	 * 
	 * @param year  năm
	 * @param month tháng
	 * @param day   ngày
	 * @return chuỗi năm tháng ngày định dạng YYYY/MM/DD
	 */
	public static String convertToString(int year, int month, int day) {
		StringBuilder string = new StringBuilder();
		string.append(year).append("-");
		if (month < 10) {
			string.append("0");
		}
		string.append(month).append("-");
		if (day < 10) {
			string.append("0");
		}
		string.append(day);
		return string.toString();
	}

	/**
	 * Phương thức lấy ra năm hiện tại
	 * 
	 * @return năm hiện tại
	 */
	public static int getYearNow() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * Phương thức lấy về tháng hiện tại
	 * 
	 * @return Tháng hiện tại
	 */
	public static int getMonthNow() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	/**
	 * Phương thức lấy về ngày hiện tại
	 * 
	 * @return Ngày hiện tại
	 */
	public static int getDayNow() {
		return Calendar.getInstance().get(Calendar.DATE);
	}

	/**
	 * Phương thức kiểm tra giá trị của chuỗi truyền vào
	 * 
	 * @param string chuỗi cần kiểm tra giá trị
	 * @return false nếu chuỗi có kí tự, true nếu chuỗi null hoặc rỗng
	 */
	public static boolean checkEmpty(String string) {
		boolean rs = false;
		if (string == null) {
			rs = true;
		} else {
			if (string.isEmpty()) {
				rs = true;
			}
		}
		return rs;
	}

	/**
	 * Phương thức thêm 1 chuỗi thông báo vào danh sách lỗi nếu chuỗi không rỗng
	 * 
	 * @param listErrors danh sách lỗi
	 * @param error      Chuỗi cần thêm
	 */
	public static void addErrorsToList(List<String> listErrors, String error) {
		if (!error.isEmpty()) {
			listErrors.add(error);
		}
	}

	/**
	 * Phương thức kiểm tra ngày có hợp lệ hay không
	 * 
	 * @param checkedDate Ngày cần kiểm tra
	 * @return true nếu hợp lệ, false nếu không hợp lệ
	 */
	public static boolean checkDateFormat(String checkedDate) {
		boolean err = true; // kết quả trả về
		// Lấy ra năm, tháng, ngày từ chuỗi ngày cần kiểm tra
		String[] dateList = checkedDate.split("-");
		int year = Integer.parseInt(dateList[0]);
		int month = Integer.parseInt(dateList[1]);
		int day = Integer.parseInt(dateList[2]);
		// Gán mặc định số ngày có trong 1 tháng là 31
		int daysInMonth = 31;
		switch (month) { // Kiểm tra các tháng
		case 4: // Nếu là tháng 4, 6, 9, 11
		case 6:
		case 9:
		case 11: // Gán số ngày trong tháng là 30
			daysInMonth = 30;
			break;
		case 2: // Trường hợp tháng 2 năm nhuận, gán số ngày là 29
			if ((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0)) {
				daysInMonth = 29;
				// Trường hợp tháng 2 năm thường, gán số ngày là 29
			} else
				daysInMonth = 28;
			break;
		}
		// Kiểm tra xem các giá trị năm, tháng, ngày có hợp lệ hay không
		// Nếu không, gán kết quả trả về là false
		if (year < 0 || month < 0 || month > 12 || day < 0 || day > daysInMonth) {
			err = false;
		}
		return err;
	}

	/**
	 * Phương thức trả về key để lưu lên session khi mở sang màn hình 004
	 * 
	 * @return Giá trị của key (Thời gian hiện tại)
	 */
	public static String createKey() {
		return getCurrentTime();
	}

	/**
	 * Phương thức lấy về salt khi thực hiện thêm user vào DB
	 * 
	 * @return salt: thời gian hiện tại
	 * 
	 */
	public static String createSalt() {
		return getCurrentTime();
	}

	/**
	 * Phương thức lấy về thời gian hiện tại tính đến mili giây
	 * 
	 * @return Thời gian hiện tại tính đến mili giây
	 */
	private static String getCurrentTime() {
		return System.currentTimeMillis() + "";
	}

	/**
	 * Phương thức kiểm tra độ dài của chuỗi có vượt quá max length quy định không
	 * 
	 * @param value     Chuỗi cần kiểm tra
	 * @param maxLength độ dài lớn nhất
	 * 
	 * @return Nếu không vượt quá max length, trả về true, vượt quá trả về false
	 */
	public static boolean checkMaxLength(String value, int maxLength) {
		boolean result = true;
		if (value.length() > maxLength) {
			result = false;
		}
		return result;
	}

	/**
	 * Kiểm tra chuỗi có khớp với định dạng quy định không
	 * 
	 * @param value   Chuỗi cần kiểm tra
	 * @param pattern Định dạng quy định
	 * @return True nếu khớp định dạng, false nếu không khớp
	 */
	public static boolean checkFormat(String value, String regex) {
		boolean result = false;
		if (value.matches(regex)) {
			result = true;
		}
		return result;
	}

	/**
	 * Phương thức kiểm tra độ dài của chuỗi có nằm trong khoảng minlength -
	 * maxLength không
	 * 
	 * @param value     Chuỗi cần kiểm tra
	 * @param minLength Độ dài nhỏ nhất
	 * @param maxLength Độ dài lớn nhất
	 * @return True nếu độ dài chuỗi nằm trong khoảng cần kiểm tra, False nếu vượt
	 *         quá khoảng cần kiểm tra
	 */
	public static boolean checkLength(String value, int minLength, int maxLength) {
		boolean result = true;
		if (value.length() < minLength || value.length() > maxLength) {
			result = false;
		}
		return result;
	}

	/**
	 * Phương thức kiểm tra password có được nhập đúng hay không (chuỗi được mã hóa
	 * có trùng với password được lưu trong DB hay không)
	 * 
	 * @param encryptedPass Chuỗi được mã hóa từ password nhập vào
	 * @param password      Password của user đã được mã hóa lưu trong DB
	 * @return True nếu trùng nhau, False nếu không trùng
	 */
	public static boolean comparePasswords(String encryptedPass, String password) {
		boolean equal = false;
		// Kiểm tra 2 chuỗi mật khẩu có khớp nhau không
		if (encryptedPass.equals(password)) {
			equal = true;
		}
		return equal;
	}

	/**
	 * Phương thức xử lý các toán tử Wildcard ở mệnh đề LIKE trong câu truy vấn SQL
	 * 
	 * @param fullName Chuỗi tìm kiếm người dùng nhập vào
	 * @return Chuỗi tìm kiếm đã được xử lí
	 */
	public static String escapeWildcards(String fullName) {
		String result = Constant.EMPTY_STRING;
		// Xử lí toán tử % và _
		result = fullName.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
		return result;
	}

	/**
	 * Phương thức dùng để chuyển 1 chuỗi sang số, nếu có lỗi, trả về giá trị mặc
	 * định mong muốn
	 * 
	 * @param string       Chuỗi cần chuyển
	 * @param defaultValue Giá trị mặc định mong muốn
	 * @return Giá trị kiểu int
	 */
	public static int parseInt(String string, int defaultValue) {
		int result = defaultValue;
		try {
			result = Integer.parseInt(string);
			// Nếu có lỗi xảy ra, gán giá trị trả về = giá trị mặc định mong
			// muốn
		} catch (NumberFormatException e) {
			result = defaultValue;
		}
		return result;
	}

	/**
	 * Thực hiện set giá trị cho các hạng mục selectbox ở màn hình ADM003
	 * 
	 * @param request Request nhận được
	 */
	public static void setDataLogic(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		try {
			// Khai báo đối tượng MstGroupLogic
			MstGroupLogic groupLogic = new MstGroupLogicImpl();
			// Khai báo đối tượng MstJapanLogic
			MstJapanLogic japanLogic = new MstJapanLogicImpl();
			// Lấy về danh sách listGroup
			List<MstGroup> listGroup = groupLogic.getAllMstGroups();
			// Lấy về danh sách listGroup
			List<MstJapan> listJapan = japanLogic.getAllMstJapan();
			// Lấy về danh sách các ngày trong tháng
			List<Integer> listDay = Common.getListDay();
			// Lấy về danh sách các tháng trong năm
			List<Integer> listMonth = Common.getListMonth();

			// Lấy về năm hiện tại
			int currentYear = Common.getYearNow();

			// Lấy về danh sách các năm cho selectbox birthday
			List<Integer> listYearBirthday = Common.getListYear(Constant.FIRST_YEAR, currentYear);
			// Lấy về danh sách các năm cho selectbox birthday
			List<Integer> listYearStartDate = Common.getListYear(Constant.FIRST_YEAR, currentYear);
			// Lấy về danh sách các năm cho selectbox birthday
			List<Integer> listYearEndDate = Common.getListYear(Constant.FIRST_YEAR, currentYear + 1);

			request.setAttribute(Constant.ATB_LIST_GROUP, listGroup);
			request.setAttribute("listJapan", listJapan);
			request.setAttribute("listDay", listDay);
			request.setAttribute("listMonth", listMonth);
			request.setAttribute("listYearBirthday", listYearBirthday);
			request.setAttribute("listYearStartDate", listYearStartDate);
			request.setAttribute("listYearEndDate", listYearEndDate);

		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Common.setDataLogic:\n" + e.getMessage());
			throw e;
		}
	}

	/**
	 * Phương thức kiểm tra chuỗi có gồm các kí tự 1 byte hay không (có nằm trong
	 * 128 kí tự của bảng mã ASCII)
	 * 
	 * @param text Chuỗi cần kiểm tra
	 * @return True nếu chỉ gồm các kí tự 1 byte, ngược lại trả về false
	 */
	public static boolean checkOneByteCharacters(String text) {
		boolean isOneByte = true;
		// Nếu chuỗi chứa kí tự lớn hơn 1 byte, gán kết quả trả về là false
		for (int i = 0; i < text.length(); i++) {
			if ((char) text.charAt(i) > 127) {
				isOneByte = false;
			}
		}
		return isOneByte;
	}

	/**
	 * Phương thức lấy về giá trị của chuỗi string được truyền vào, nếu chuỗi string
	 * khác null, trả về giá trị của chuỗi được cắt bỏ khoảng trắng ở hai đầu, ngược
	 * lại, trả về giá trị default mong muốn
	 * 
	 * @param string       Chuỗi cần lấy giá trị
	 * @param defaultValue Giá trị mặc định muốn gán cho chuỗi trong trường hợp
	 *                     chuỗi null
	 * @return giá trị của chuỗi
	 */
	public static String getStringValue(String string, String defaultValue) {
		if (string == null) {
			string = defaultValue;
		}
		return string.trim();
	}

	/**
	 * Phương thức lấy về giá trị của chuỗi string được truyền vào, nếu chuỗi string
	 * khác null, trả về giá trị của chuỗi được cắt bỏ khoảng trắng ở hai đầu, ngược
	 * lại, trả về null
	 * 
	 * @param string Chuỗi cần lấy giá trị
	 * @return giá trị của chuỗi
	 */
	public static String getStringFromRequest(String string) {
		if (string != null) {
			string = string.trim();
		}
		return string;
	}

}
