/**
 * Copyright(C)Jul 17, 2019 Luvina Software Company
 * ValidateUser.java, Jul 17, 2019, Phạm Minh Thảo
 */
package manageuser.validates;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import manageuser.entities.MstGroup;
import manageuser.entities.MstJapan;
import manageuser.entities.UserInfor;
import manageuser.logics.MstGroupLogic;
import manageuser.logics.MstJapanLogic;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.MstJapanLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;
import manageuser.utils.MessageErrorProperties;

/**
 * Class dùng để validate các giá trị người dùng nhập và chọn
 * 
 * @author PhamMinhThao
 *
 */
public class ValidateUser {

	// Tạo đối tượng TblUserLogic
	private TblUserLogic tblUserLogic = new TblUserLogicImpl();
	// Tạo đối tượng TblUserLogic
	private MstGroupLogic mstGroupLogic = new MstGroupLogicImpl();
	// Tạo đối tượng TblUserLogic
	private MstJapanLogic mstJapanLogic = new MstJapanLogicImpl();

	/**
	 * Phương thức kiểm tra việc đăng nhập vào hệ thống thông qua tên tài khoản
	 * và mật khẩu được nhập vào
	 * 
	 * @param loginName tên tài khoản
	 * @param password  mật khẩu
	 * @return Danh sách lỗi
	 * @throws SQLException             ném exception xảy ra khi thao tác với DB
	 * @throws ClassNotFoundException   ném exception xảy ra khi không tìm thấy file
	 *                                  Class
	 * @throws NoSuchAlgorithmException Ngoại lệ ném ra khi gọi thuật toán mã hóa
	 */
	public List<String> validateLogin(String loginName, String password)
			throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
		// Khởi tạo danh sách lỗi
		List<String> listError = new ArrayList<String>();
		try {
			// Kiểm tra login name và password đã được nhập chưa
			if (loginName.isEmpty()) {
				// Thêm lỗi "Không nhập login name" vào ds lỗi
				listError.add(MessageErrorProperties.getMessage(Constant.ER001_LOGIN_NAME));
			}
			if (password.isEmpty()) {
				// Thêm lỗi "Không nhập mật khẩu" vào ds lỗi
				listError.add(MessageErrorProperties.getMessage(Constant.ER001_PASS));
			}
			// Nếu cả login name và pass đều đã được nhập
			if (listError.isEmpty()) {
				// Kiểm tra có tồn tại user thỏa mãn login name và pass được
				// nhập không
				// Nếu không tồn tại, thêm lỗi nhập sai vào danh sách lỗi
				if (!tblUserLogic.existLoginId(loginName, password)) {
					listError.add(MessageErrorProperties.getMessage(Constant.ER016));
				}
			}
			// Bắt exception: In ra lỗi và ném ngoại lệ
		} catch (ClassNotFoundException | SQLException | NoSuchAlgorithmException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		} // Trả về danh sách lỗi
		return listError;
	}

	/**
	 * Phương thức kiểm tra các thông tin để thêm mới người dùng có hợp lệ không
	 * 
	 * @param userInfor Đối tượng userInfor chứa các thông tin được nhập
	 * @return Danh sách lỗi, nếu tất cả các thông tin đều hợp lệ, trả về null
	 * @throws SQLException           ném exception xảy ra khi thao tác với DB
	 * @throws ClassNotFoundException ném exception xảy ra khi không tìm thấy file
	 *                                Class
	 */
	public List<String> validateUserInfor(UserInfor userInfor) throws ClassNotFoundException, SQLException {
		// Khai báo danh sách lỗi
		List<String> listError = new ArrayList<>();

		try {

			/**
			 * Trường hợp userId = 0 (thêm mới user), kiểm tra login name và mật khẩu, mật
			 * khẩu xác nhận. Nếu là trường hợp update userId > 0, không cần kiểm tra. Thêm
			 * nội dung thông báo lỗi vào danh sách nếu có
			 */
			if (userInfor.getUserId() == 0) {
				Common.addErrorsToList(listError, validateLoginName(userInfor.getLoginName(), userInfor.getUserId()));
			}

			// Kiểm tra các hạng mục group, full name, full name Katakana,
			// birthday, email và tel và lấy về thông báo lỗi (nếu có)

			Common.addErrorsToList(listError, validateGroupId(userInfor.getGroupId()));
			Common.addErrorsToList(listError, validateFullname(userInfor.getFullName()));
			Common.addErrorsToList(listError, validateNameKana(userInfor.getFullNameKana()));
			Common.addErrorsToList(listError, validateBirthday(userInfor.getBirthday()));
			Common.addErrorsToList(listError, validateEmail(userInfor.getUserId(), userInfor.getEmail()));
			Common.addErrorsToList(listError, validateTel(userInfor.getTel()));

			/**
			 * Trường hợp userId = 0 (thêm mới user), kiểm tra mật khẩu và mật khẩu xác
			 * nhận. Nếu là trường hợp update (userId > 0), không cần kiểm tra. Thêm nội
			 * dung thông báo lỗi vào danh sách nếu có
			 */
			if (userInfor.getUserId() == 0) {
				Common.addErrorsToList(listError, validatePassword(userInfor.getPassword()));
				Common.addErrorsToList(listError,
						validateRePassword(userInfor.getPassword(), userInfor.getRePassword()));
			}

			// Nếu có trình độ tiếng Nhật, kiểm tra các hạng mục level, start
			// date, end date, total, nếu có lỗi thêm vào danh sách lỗi
			if (!Constant.DEFAULT_CODE_LEVEL.equals(userInfor.getCodeLevel())) {

				// Kiểm tra các hạng mục level, start date, end date, total
				// Thêm nội dung lỗi vào danh sách nếu có
				Common.addErrorsToList(listError, validateLevel(userInfor.getCodeLevel()));
				Common.addErrorsToList(listError, validateStartDate(userInfor.getStartDate()));
				Common.addErrorsToList(listError, validateEndDate(userInfor.getEndDate()));
				Common.addErrorsToList(listError,
						validateStartDateEndDate(userInfor.getStartDate(), userInfor.getEndDate()));
				Common.addErrorsToList(listError, validateTotal(userInfor.getTotal()));
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		}
		// Trả về danh sách lỗi
		return listError;
	}

	/**
	 * Phương thức kiểm tra ngày có hiệu lực và ngày hết hạn có hợp lệ không
	 * (endDate có sau startDate không)
	 * 
	 * @param startDate ngày có hiệu lực
	 * @param endDate   ngày hết hạn
	 * @return Nếu hợp lệ trả về chuỗi rỗng, nếu không trả về nội dung lỗi
	 */
	private String validateStartDateEndDate(String startDate, String endDate) {
		String err = Constant.EMPTY_STRING;
		// Nếu ngày hết hạn trước ngày có hiệu lực, gán chuỗi trả về là nội dung
		// lỗi
		if (endDate.compareTo(startDate) < 0) {
			err = MessageErrorProperties.getMessage(Constant.ER012);
		}
		return err;
	}

	/**
	 * Phương thức kiểm tra ngày hết hạn có hợp lệ không (Ngày được chọn không vượt
	 * quá số ngày có trong các tháng)
	 * 
	 * @param endDate Ngày cần kiểm tra
	 * @return Nếu hợp lệ trả về chuỗi rỗng, nếu không trả về nội dung lỗi
	 */
	private String validateEndDate(String endDate) {
		String err = Constant.EMPTY_STRING;
		// Nếu ngày hết hạn không đúng format, gán chuỗi trả về là nội dung
		// lỗi
		if (!Common.checkDateFormat(endDate)) {
			err = MessageErrorProperties.getMessage(Constant.ER011_END_DATE);
		}
		return err;
	}

	/**
	 * Phương thức kiểm tra ngày cấp chứng chỉ có hợp lệ không (Ngày được chọn không
	 * vượt quá số ngày có trong các tháng)
	 * 
	 * @param startDate Ngày cần kiểm tra
	 * @return Nếu hợp lệ trả về chuỗi rỗng, nếu không trả về nội dung lỗi
	 */
	private String validateStartDate(String startDate) {
		String err = Constant.EMPTY_STRING;
		// Nếu ngày có hiệu lực không đúng format, gán chuỗi trả về là nội dung
		// lỗi
		if (!Common.checkDateFormat(startDate)) {
			err = MessageErrorProperties.getMessage(Constant.ER011_START_DATE);
		}
		return err;
	}

	/**
	 * Phương thức kiểm tra ngày sinh có hợp lệ không (Ngày được chọn không vượt quá
	 * số ngày có trong các tháng)
	 * 
	 * @param birthday Ngày sinh cần kiểm tra
	 * @return Nếu hợp lệ trả về chuỗi rỗng, nếu không trả về nội dung lỗi
	 */
	private String validateBirthday(String birthday) {
		String err = Constant.EMPTY_STRING;
		// Nếu ngày sinh không đúng format, gán chuỗi trả về là nội dung lỗi
		if (!Common.checkDateFormat(birthday)) {
			err = MessageErrorProperties.getMessage(Constant.ER011_BIRTHDAY);
		}
		return err;
	}

	/**
	 * Phương thức kiểm tra hạng mục điểm (total) có hợp lệ hay không (được nhập
	 * đúng độ dài và định dạng quy định)
	 * 
	 * @param total Điểm cần kiểm tra
	 * @return Nếu hợp lệ trả về chuỗi rỗng, nếu không trả về nội dung lỗi
	 */
	private String validateTotal(String total) {
		String err = Constant.EMPTY_STRING;
		// Kiểm tra total có rỗng hay không
		if (Common.checkEmpty(total)) {
			err = MessageErrorProperties.getMessage(Constant.ER001_TOTAL);
			// Kiểm tra total có vượt quá độ dài quy định
		} else if (!Common.checkMaxLength(total, Constant.LENGTH_TOTAL)) {
			err = MessageErrorProperties.getMessage(Constant.ER006_TOTAL);
			// Kiểm tra total có đúng định dạng
		} else if (!Common.checkFormat(total, Constant.FORMAT_TOTAL)) {
			err = MessageErrorProperties.getMessage(Constant.ER018);
		}
		return err;
	}

	/**
	 * Phương thức kiểm tra mã trình độ tiếng Nhật có hợp lệ hay không (kiểm tra có
	 * tồn tại trong DB hay không)
	 * 
	 * @param codeLevel mã trình độ tiếng Nhật
	 * @return Nếu hợp lệ trả về chuỗi rỗng, nếu không trả về nội dung lỗi
	 * @throws SQLException           ném exception xảy ra khi thao tác với DB
	 * @throws ClassNotFoundException ném exception xảy ra khi không tìm thấy file
	 *                                Class
	 */
	private String validateLevel(String codeLevel) throws ClassNotFoundException, SQLException {
		String err = Constant.EMPTY_STRING;
		// Kiểm tra xem codeLevel có tồn tại trong DB không
		try {// lấy về đối tượng mstJapan thông qua codeLevel
			MstJapan mstJapan = mstJapanLogic.getMstJapanByCodeLevel(codeLevel);
			if (mstJapan == null) {
				err = MessageErrorProperties.getMessage(Constant.ER004_CODE_LEVEL);
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		}
		return err;
	}

	// Start fix bug ID 126 – PhamMinhThao 2019/08/08

	/**
	 * Phương thức kiểm tra mật khẩu xác nhận có hợp lệ hay không (có trùng với mật
	 * khẩu hay không)
	 * 
	 * @param password   mật khẩu để so sánh
	 * @param rePassword mật khẩu xác nhận
	 * @return Nếu hợp lệ trả về chuỗi rỗng, nếu không trả về nội dung lỗi
	 */
	private String validateRePassword(String password, String rePassword) {
		String err = Constant.EMPTY_STRING;
		// Nếu mật khẩu không rỗng
		if (!Common.checkEmpty(password)) {
			// Kiểm tra xem mật khẩu xác nhận có khớp với mật khẩu không
			// Nếu không khớp, gán lỗi cho thông báo
			if (!Common.comparePasswords(password, rePassword)) {
				err = MessageErrorProperties.getMessage(Constant.ER017);
			}
		}
		return err;
	}

	// End fix bug ID 126 – PhamMinhThao 2019/08/08

	/**
	 * Phương thức kiểm tra mật khẩu có hợp lệ hay không (Có được nhập đúng định
	 * dạng và độ dài quy định hay không)
	 * 
	 * @param password mật khẩu cần kiểm tra
	 * @return Nếu hợp lệ trả về chuỗi rỗng, nếu không trả về nội dung lỗi
	 */
	private String validatePassword(String password) {
		String err = Constant.EMPTY_STRING;
		// Kiểm tra mật khẩu có được nhập hay không
		if (Common.checkEmpty(password)) {
			err = MessageErrorProperties.getMessage(Constant.ER001_PASS);
			// Kiểm tra mật khẩu có đúng định dạng hay không
		} else if (!Common.checkOneByteCharacters(password)) {
			err = MessageErrorProperties.getMessage(Constant.ER008);
			// Kiểm tra mật khẩu có đúng độ dài quy định hay không
		} else if (!Common.checkLength(password, Constant.LENGTH_PASS_MIN, Constant.LENGTH_PASS_MAX)) {
			err = MessageErrorProperties.getMessage(Constant.ER007_PASS);
		}
		return err;
	}

	/**
	 * Phương thức kiểm tra số điện thoại có hợp lệ hay không (Có được nhập đúng độ
	 * dài và định dạng quy định hay không)
	 * 
	 * @param tel số điện thoại cần kiểm tra
	 * @return Nếu hợp lệ trả về chuỗi rỗng, nếu không trả về nội dung lỗi
	 */
	private String validateTel(String tel) {
		String err = Constant.EMPTY_STRING;
		// Kiểm tra sđt có được nhập hay không
		if (Common.checkEmpty(tel)) {
			err = MessageErrorProperties.getMessage(Constant.ER001_TEL);
			// Kiểm tra sđt có đúng độ dài quy định hay không
		} else if (!Common.checkMaxLength(tel, Constant.LENGTH_TEL)) {
			err = MessageErrorProperties.getMessage(Constant.ER006_TEL);
			// Kiểm tra sđt có đúng định dạng hay không
		} else if (!Common.checkFormat(tel, Constant.FORMAT_TEL)) {
			err = MessageErrorProperties.getMessage(Constant.ER005_TEL);
		}
		return err;
	}

	/**
	 * Phương thức kiểm tra email có hợp lệ hay không (Có được nhập đúng độ dài và
	 * định dạng quy định, có trùng email user khác hay không)
	 * 
	 * @param userId id của user
	 * @param email  email cần kiểm tra
	 * @return Nếu hợp lệ trả về chuỗi rỗng, nếu không trả về nội dung lỗi
	 * @throws SQLException           ném exception xảy ra khi thao tác với DB
	 * @throws ClassNotFoundException ném exception xảy ra khi không tìm thấy file
	 *                                Class
	 */
	private String validateEmail(int userId, String email) throws ClassNotFoundException, SQLException {
		String err = Constant.EMPTY_STRING;
		try {
			// Kiểm tra email có được nhập hay không
			if (Common.checkEmpty(email)) {
				err = MessageErrorProperties.getMessage(Constant.ER001_EMAIL);
				// Kiểm tra email có đúng độ dài quy định hay không
			} else if (!Common.checkMaxLength(email, Constant.LENGTH_EMAIL)) {
				err = MessageErrorProperties.getMessage(Constant.ER006_EMAIL);
				// Kiểm tra email có đúng định dạng hay không
			} else if (!Common.checkFormat(email, Constant.FORMAT_EMAIL)) {
				err = MessageErrorProperties.getMessage(Constant.ER005_EMAIL);
				// Kiểm tra email đã tồn tại hay chưa
			} else if (tblUserLogic.checkExistedEmail(userId, email)) {
				err = MessageErrorProperties.getMessage(Constant.ER003_EMAIL);
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		}
		return err;
	}

	/**
	 * Phương thức kiểm tra tên katakana có hợp lệ hay không (có phải là kí tự
	 * katakana và đúng độ dài quy định hay không)
	 * 
	 * @param fullNameKana Tên katakana cần kiểm tra
	 * @return Nếu hợp lệ trả về chuỗi rỗng, nếu không trả về nội dung lỗi
	 */
	private String validateNameKana(String fullNameKana) {
		String err = Constant.EMPTY_STRING;
		// Nếu Tên katakana được nhập
		if (!Common.checkEmpty(fullNameKana)) {
			// Kiểm tra chuỗi nhập vào có phải kí tự katakana hay không
			if (!Common.checkFormat(fullNameKana, Constant.FORMAT_KANA)) {
				err = MessageErrorProperties.getMessage(Constant.ER009);
				// Kiểm tra Tên katakana có đúng độ dài quy định hay không
			} else if (!Common.checkMaxLength(fullNameKana, Constant.LENGTH_FULLNAME_KANA)) {
				err = MessageErrorProperties.getMessage(Constant.ER006_FULLNAME_KANA);
			}
		}
		return err;
	}

	/**
	 * Phương thức kiểm tra tên có hợp lệ hay không (Có được nhập đúng độ dài quy
	 * định hay không)
	 * 
	 * @param fullName Tên cần kiểm tra
	 * @return Nếu hợp lệ trả về chuỗi rỗng, nếu không trả về nội dung lỗi
	 */
	private String validateFullname(String fullName) {
		String err = Constant.EMPTY_STRING;
		// Kiểm tra fullName có được nhập hay không
		if (Common.checkEmpty(fullName)) {
			err = MessageErrorProperties.getMessage(Constant.ER001_FULLNAME);
			// Kiểm tra fullName có đúng độ dài quy định hay không
		} else if (!Common.checkMaxLength(fullName, Constant.LENGTH_FULLNAME)) {
			err = MessageErrorProperties.getMessage(Constant.ER006_FULLNAME);
		}
		return err;
	}

	/**
	 * Phương thức kiểm tra group id có hợp lệ hay không (Có được chọn và group có
	 * tồn tại trong DB hay không)
	 * 
	 * @param groupId group id cần kiểm tra
	 * @return Nếu hợp lệ trả về chuỗi rỗng, nếu không trả về nội dung lỗi
	 * @throws SQLException           ném exception xảy ra khi thao tác với DB
	 * @throws ClassNotFoundException ném exception xảy ra khi không tìm thấy file
	 *                                Class
	 */
	private String validateGroupId(int groupId) throws ClassNotFoundException, SQLException {
		String err = Constant.EMPTY_STRING;
		try {
			// Kiểm tra group đã được chọn chưa, nếu chưa, gán thông báo chưa
			// chọn group
			if (groupId == Constant.DEFAULT_GROUP_ID) {
				err = MessageErrorProperties.getMessage(Constant.ER002_GROUP_ID);
			} else {
				// Kiểm tra xem group id truyền vào có trong DB không
				MstGroup mstGroup = mstGroupLogic.getMstGroupById(groupId);
				if (mstGroup == null) {
					err = MessageErrorProperties.getMessage(Constant.ER004_GROUP_ID);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("ValidateUser.validateGroupId:\n" + e.getMessage());
			throw e;
		}
		return err;
	}

	/**
	 * Phương thức kiểm tra loginName có hợp lệ hay không (Có được nhập đúng độ dài
	 * và định dạng quy định, có trùng login name với user khác hay không)
	 * 
	 * @param loginName loginName cần tra
	 * @param id        id user của user
	 * @return Nếu hợp lệ trả về chuỗi rỗng, nếu không trả về nội dung lỗi
	 * @throws SQLException           ném exception xảy ra khi thao tác với DB
	 * @throws ClassNotFoundException ném exception xảy ra khi không tìm thấy file
	 *                                Class
	 */
	private String validateLoginName(String loginName, int id) throws ClassNotFoundException, SQLException {
		String err = Constant.EMPTY_STRING;
		try {
			// Kiểm tra loginName có được nhập hay không
			if (Common.checkEmpty(loginName)) {
				err = MessageErrorProperties.getMessage(Constant.ER001_LOGIN_NAME);
				// Kiểm tra loginName đã tồn tại hay chưa
			} else if (tblUserLogic.checkExistedLoginName(loginName)) {
				err = MessageErrorProperties.getMessage(Constant.ER003_LOGIN_NAME);
				// Kiểm tra loginName có đúng độ dài quy định hay không
			} else if (!Common.checkLength(loginName, Constant.LENGTH_LOGIN_MIN, Constant.LENGTH_LOGIN_MAX)) {
				err = MessageErrorProperties.getMessage(Constant.ER007_LOGIN_NAME);
				// Kiểm tra loginName có đúng định dạng quy định hay không
			} else if (!Common.checkFormat(loginName, Constant.FORMAT_LOGIN_NAME)) {
				err = MessageErrorProperties.getMessage(Constant.ER019);
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("ValidateUser.validateLoginName\n" + e.getMessage());
			throw e;
		}
		return err;
	}
}
