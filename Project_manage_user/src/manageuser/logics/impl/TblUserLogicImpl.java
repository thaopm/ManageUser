/**
 * Copyright(C)Jul 17, 2019 Luvina Software Company
 * TblUserLogicImpl.java, Jul 17, 2019, Phạm Minh Thảo
 */
package manageuser.logics.impl;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import manageuser.dao.TblDetailUserJapanDao;
import manageuser.dao.TblUserDao;
import manageuser.dao.impl.TblDetailUserJapanDaoImpl;
import manageuser.dao.impl.TblUserDaoImpl;
import manageuser.entities.TblDetailUserJapan;
import manageuser.entities.TblUser;
import manageuser.entities.UserInfor;
import manageuser.logics.TblUserLogic;
import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * Lớp thực hiện xử lý logic cho đối tượng user
 * 
 * @author PhamMinhThao
 *
 */
public class TblUserLogicImpl implements TblUserLogic {

	// Khai báo đối tượng TblUserDao
	private TblUserDao userDao = new TblUserDaoImpl();
	// Khai báo đối tượng TblDetailUserJapanDao
	private TblDetailUserJapanDao detailUserJapanDao = new TblDetailUserJapanDaoImpl();

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.TblUserLogic#existLoginId(java.lang.String,
	 * java.lang.String)
	 *
	 */
	@Override
	public boolean existLoginId(String loginName, String password)
			throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {
		// Tạo tham số kết quả trả về
		boolean existLoginId = false;
		try {
			// Gọi userDao lấy về đối tượng user thông qua login_name
			TblUser user = userDao.getUserByLoginId(loginName);
			// Nếu tồn tại đối tượng user có rule là admin
			if (user != null && Constant.ADMIN_RULE == user.getRule()) {
				// Mã hóa password được nhập vào
				String encryptedPass = Common.getEncryptedPass(password, user.getSalt());
				// Kiểm tra xem password được nhập sau khi mã hóa có trùng
				// password trong DB
				// không, nếu trùng, gán tham số trả về bằng true
				if (Common.comparePasswords(encryptedPass, user.getPassword())) {
					existLoginId = true;
				}
			}
			// Bắt exception: In ra lỗi và ném ngoại lệ
		} catch (SQLException | ClassNotFoundException | NoSuchAlgorithmException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		} // Trả về kết quả
		return existLoginId;
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @throws IOException
	 * 
	 * @see manageuser.logics.TblUserLogic#getListUsers(int, int, int,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 *
	 */
	@Override
	public List<UserInfor> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate)
			throws ClassNotFoundException, SQLException, IOException {
		List<UserInfor> userList = new ArrayList<UserInfor>();
		try {
			// Xử lí toán tử wildcard trong chuỗi tên tìm kiếm
			fullName = Common.escapeWildcards(fullName);
			// Gọi đối tượng userDao lấy về list user
			userList = userDao.getListUsers(offset, limit, groupId, fullName, sortType, sortByFullName, sortByCodeLevel,
					sortByEndDate);
			// Bắt exception: In ra lỗi và ném ngoại lệ
		} catch (ClassNotFoundException | SQLException | IOException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		}
		// Trả về list user
		return userList;
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.TblUserLogic#getTotalUsers(java.lang.String, int)
	 *
	 */
	@Override
	public int getTotalUsers(String fullName, int groupId) throws ClassNotFoundException, SQLException {
		int totalUsers = 0;
		try {
			// Xử lí toán tử wildcard trong chuỗi tên tìm kiếm
			fullName = Common.escapeWildcards(fullName);
			// Gọi đối tượng userDao lấy về tổng số user
			totalUsers = userDao.getTotalUsers(fullName, groupId);
			// Bắt exception: In ra lỗi và ném ngoại lệ
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			e.printStackTrace();
			throw e;
		} // trả về kết quả
		return totalUsers;
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.TblUserLogic#getUserInforByUserId(int)
	 *
	 */
	@Override
	public UserInfor getUserInforByUserId(int id) throws SQLException, ClassNotFoundException {
		// Khởi tạo đối tượng UserInfor
		UserInfor userInfor = null;
		try {
			// Lấy về userInfor bằng user id
			userInfor = userDao.getUserInforByUserId(id);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		}
		return userInfor;
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * manageuser.logics.TblUserLogic#checkExistedLoginName(java.lang.String)
	 *
	 */
	@Override
	public boolean checkExistedLoginName(String loginName) throws ClassNotFoundException, SQLException {
		// khởi tạo biến kiểm tra tồn tại login name
		boolean isExisting = false;
		try {
			// Lấy về TblUser thông qua login name và id
			TblUser tblUser = userDao.getUserByLoginId(loginName);
			// Nếu user != null (có tồn tại), gán biến kiểm tra tồn tại = true
			if (tblUser != null) {
				isExisting = true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		}
		return isExisting;
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.TblUserLogic#checkExistedEmail(int,
	 * java.lang.String)
	 *
	 */
	@Override
	public boolean checkExistedEmail(int userId, String email) throws ClassNotFoundException, SQLException {
		// khởi tạo biến kiểm tra tồn tại email
		boolean isExisting = false;
		// Gọi đến phương thức checkExistedEmail trong đối tượng TblUserDao
		try {
			TblUser tblUser = userDao.getUserByEmail(userId, email);
			if (tblUser != null) {
				isExisting = true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return isExisting;
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * manageuser.logics.TblUserLogic#createUser(manageuser.entities.UserInfor)
	 *
	 */
	@Override
	public void createUser(UserInfor userInfor) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
		try {
			// Khởi tạo connection
			userDao.connectDB();
			if (userDao.getConnection() != null) {
				// Set autocommit = false cho connection
				userDao.disableAutoCommit();
				// Khởi tạo đối tượng TblUser
				TblUser tblUser = createTblUser(userInfor);
				// Thêm tbl_user vào bảng tbl_user trong DB và lấy về user id
				int userId = userDao.insertUser(tblUser);
				// Gán user id cho đối tượng user
				userInfor.setUserId(userId);
				// Kiểm tra xem đối tượng user cần thêm mới có thông tin năng
				// lực tiếng Nhật không
				if (!Constant.DEFAULT_CODE_LEVEL.equals(userInfor.getCodeLevel())) {
					// Nếu có, khởi tạo đối tượng TblDetailUserJapan
					TblDetailUserJapan tblDetailUserJapan = createTblDetailUserJapan(userInfor);
					// Set cho detailUserJapanDao đối tượng connection vừa khởi
					// tạo giống với userDao
					detailUserJapanDao.setConnection(userDao.getConnection());
					// Thực hiện thêm đối tượng tblDetailUserJapan vào trong DB
					detailUserJapanDao.insertDetailUserJapan(tblDetailUserJapan);
				}
				// Nếu các thao tác đều không có lỗi, commit các hành động cần
				// thực hiện
				userDao.commit();
			}
			// Nếu có lỗi
		} catch (ClassNotFoundException | SQLException | NoSuchAlgorithmException e) {
			// In ra thông báo lỗi
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			// Rollback lại dữ liệu
			userDao.rollback();
			throw e;
		} finally {
			userDao.closeConnection();
		}
	}

	/*
	 * Phương thức khởi tạo đối tượng TblDetailUserJapan từ đối tượng UserInfor
	 * được truyền vào
	 * 
	 * @param userInfor cần thêm/update
	 * 
	 * @return Đối tượng detailUserJapan để thao tác với bảng
	 * tbl_detail_user_japan
	 */
	private TblDetailUserJapan createTblDetailUserJapan(UserInfor userInfor) {
		// Khởi tạo TblDetailUserJapan
		TblDetailUserJapan detailUserJapan = null;
		if (userInfor.getNameLevel() != null) {
			detailUserJapan = new TblDetailUserJapan();
			// Set các giá trị cho detailUserJapan
			detailUserJapan.setUserId(userInfor.getUserId());
			detailUserJapan.setCodeLevel(userInfor.getCodeLevel());
			detailUserJapan.setStartDate(userInfor.getStartDate());
			detailUserJapan.setEndDate(userInfor.getEndDate());
			int total = Integer.parseInt(userInfor.getTotal());
			detailUserJapan.setTotal(total);

		}
		return detailUserJapan;
	}

	/*
	 * Phương thức khởi tạo đối tượng TblUser từ đối tượng UserInfor được truyền
	 * vào
	 * 
	 * @param userInfor user cần thêm/update
	 * 
	 * @return Đối tượng tblUser để thao tác với bảng tbl_user
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	private TblUser createTblUser(UserInfor userInfor) throws NoSuchAlgorithmException {

		TblUser tblUser = new TblUser();
		try {
			// Trường hợp update (Nếu user_id > 0)
			if (userInfor.getUserId() > 0) {
				tblUser.setUserId(userInfor.getUserId());
				// Trường hợp add
			} else {
				// Khởi tạo chuỗi salt
				String salt = Common.createSalt();
				// Thực hiện mã hóa password
				String password = Common.getEncryptedPass(userInfor.getPassword(), salt);
				// Gán password, rule và salt cho user
				tblUser.setPassword(password);
				tblUser.setRule(Constant.USER_RULE);
				tblUser.setSalt(salt);
				tblUser.setLoginName(userInfor.getLoginName());
			}
			// Set các giá trị cho tblUser
			tblUser.setGroupId(userInfor.getGroupId());
			tblUser.setFullName(userInfor.getFullName());
			tblUser.setFullNameKana(userInfor.getFullNameKana());
			tblUser.setEmail(userInfor.getEmail());
			tblUser.setTel(userInfor.getTel());
			tblUser.setBirthday(userInfor.getBirthday());
			// In lỗi và ném ngoại lệ nếu có
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		}
		return tblUser;
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.TblUserLogic#checkExistedUserId(int)
	 *
	 */
	@Override
	public boolean checkExistedUserId(int id) throws ClassNotFoundException, SQLException {
		// Tạo biến lưu kết quả
		boolean exists = false;
		// Gọi userDao thực hiện lấy user thông qua user id, nếu kết quả trả về
		// khác null, user có tồn tại
		TblUser tblUser = userDao.getUserByUserId(id);
		if (tblUser != null && tblUser.getRule() == Constant.USER_RULE) {
			exists = true;
		}
		return exists;
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * manageuser.logics.TblUserLogic#editUser(manageuser.entities.UserInfor)
	 *
	 */
	@Override
	public void editUser(UserInfor userInfor) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
		try {
			// Dùng đối tượng userDao để kết nối DB
			userDao.connectDB();
			if (userDao.getConnection() != null) {
				// Gán autocommit cho connection = false
				userDao.disableAutoCommit();

				// Tạo đối tượng TblUser từ UserInfor
				TblUser tblUser = createTblUser(userInfor);
				// Thực hiện update bảng tbl_user
				userDao.updateUser(tblUser);

				// Tạo đối tượng TblDetailUserJapan từ UserInfor
				TblDetailUserJapan detailUserJapan = createTblDetailUserJapan(userInfor);
				// Gán connection
				detailUserJapanDao.setConnection(userDao.getConnection());

				// Đối chiếu thông tin tiếng Nhật trong DB và thông tin của user
				// sau khi chỉnh sửa
				// Trường hợp user có thông tin tiếng Nhật trong DB
				if (userInfor.isHaveJapaneseLevel()) {
					if (detailUserJapan == null) {
						// Trường hợp user không có thông tin tiếng Nhật sau
						// chỉnh sửa, Thực hiện xóa thông tin user trong DB
						detailUserJapanDao.deleteDetailUserJapan(userInfor.getUserId());
					} else {
						// Trường hợp user vẫn có thông tin tiếng Nhật sau
						// chỉnh sửa, Thực hiện update thông tin user trong DB
						detailUserJapanDao.updateDetailUserJapan(detailUserJapan);
					}
					// Trường hợp user không có thông tin tiếng Nhật trong DB
				} else {
					// Trường hợp user có thông tin tiếng Nhật sau chỉnh sửa
					if (detailUserJapan != null) {
						detailUserJapanDao.insertDetailUserJapan(detailUserJapan);
					}
					// Nếu sau chỉnh sửa, user vẫn không được thêm thông tin
					// tiếng Nhật, không thực hiện gì
				}
				// thực hiện commit
				userDao.commit();
			}
		} catch (ClassNotFoundException | SQLException | NoSuchAlgorithmException e) {
			// Nếu có lỗi, thực hiện rollback, in và ném ra ngoại lệ
			userDao.rollback();
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		} finally {
			// Đóng kết nối DB
			userDao.closeConnection();
		}
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.TblUserLogic#deleteUser(int)
	 *
	 */
	@Override
	public void deleteUser(int userId) throws ClassNotFoundException, SQLException {
		try {
			// Kết nối CSDL
			userDao.connectDB();
			// Set auto commit false cho connection để trong trường hợp thao tác
			// với 1 bảng có lỗi, bảng còn lại cũng không thực hiện thao tác
			userDao.disableAutoCommit();
			// set connection của userDao cho detailUserJapanDao dùng chung
			detailUserJapanDao.setConnection(userDao.getConnection());
			// Xóa thông tin user trong bảng tbl_detail_user_japan
			detailUserJapanDao.deleteDetailUserJapan(userId);
			// Xóa thông tin user trong bảng tbl_user
			userDao.deleteUser(userId);
			// Nếu 2 bảng đều xóa thành công, thực hiện commit
			userDao.commit();
		} catch (ClassNotFoundException | SQLException e) {
			// Nếu xảy ra lỗi, rollback data, in lỗi và ném ngoại lệ
			userDao.rollback();
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		} finally {
			// Đóng connection
			userDao.closeConnection();
		}
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.TblUserLogic#checkAdminById(int)
	 *
	 */
	@Override
	public boolean checkAdminById(int id) throws ClassNotFoundException, SQLException {
		// Khai báo biến kiểm tra admin
		boolean isAdmin = false;
		try {
			// Gọi phương thức lấy admin thông qua id, nếu kết quả trả về khác
			// null, gán biến kiểm tra = true (user lấy về là admin)
			TblUser tblUser = userDao.getUserByUserId(id);
			if (tblUser != null && tblUser.getRule() == Constant.ADMIN_RULE) {
				isAdmin = true;
			}
			// In và ném ra ngoại lệ
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		}
		// Trả về biến kiểm tra admin
		return isAdmin;
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * manageuser.logics.TblUserLogic#checkAdminByLoginName(java.lang.String)
	 *
	 */
	@Override
	public boolean checkAdminByLoginName(String loginId) throws ClassNotFoundException, SQLException {
		// Khai báo biến kiểm tra admin
		boolean isAdmin = false;
		try {
			// Gọi phương thức lấy admin thông qua id
			TblUser tblUser = userDao.getUserByLoginId(loginId);
			// Nếu kết quả trả về khác null và có rule admin, gán biến kiểm tra
			// = true
			if (tblUser != null && tblUser.getRule() == Constant.ADMIN_RULE) {
				isAdmin = true;
			}
			// In và ném ra ngoại lệ
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		}
		// Trả về biến kiểm tra admin
		return isAdmin;
	}

}
