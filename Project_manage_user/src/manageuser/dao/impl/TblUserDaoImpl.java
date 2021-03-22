/**
 * Copyright(C)Jul 17, 2019 Luvina Software Company
 * TblUserLogicImpl.java, Jul 17, 2019, Phạm Minh Thảo
 */
package manageuser.dao.impl;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import manageuser.dao.TblUserDao;
import manageuser.entities.TblUser;
import manageuser.entities.UserInfor;
import manageuser.utils.Constant;
import manageuser.utils.DatabaseProperties;

/**
 * Lớp Dao thực hiện thao tác với bảng tbl_user trong DB
 * 
 * @author PhamMinhThao
 *
 */
public class TblUserDaoImpl extends BaseDaoImpl implements TblUserDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDao#getUserByLoginId(java.lang.String)
	 */
	@Override
	public TblUser getUserByLoginId(String loginName) throws ClassNotFoundException, SQLException {
		TblUser tblUser = null; // Khởi tạo user
		try {
			// Mở connection tới DB
			connectDB();
			if (connection != null) {
				// Khai báo câu lệnh select
				StringBuilder query = new StringBuilder();
				query.append("SELECT password, salt, rule ");
				query.append("FROM tbl_user ");
				query.append("WHERE login_name = ? ");
				PreparedStatement ps = connection.prepareStatement(query.toString());
				int index = Constant.DEFAULT_INDEX;
				ps.setString(index++, loginName);
				// Thực hiện lấy kết quả câu truy vấn
				resultSet = ps.executeQuery();
				// Nếu có kết quả trả về, khai báo đối tượng TblUser
				// và gán thuộc tính pass, salt cho đối tượng
				while (resultSet.next()) {
					tblUser = new TblUser();
					tblUser.setPassword(resultSet.getString("password"));
					tblUser.setSalt(resultSet.getString("salt"));
					tblUser.setRule(resultSet.getInt("rule"));
				}
			}
			// Nếu có lỗi, in ra thông báo và ném ngoại lệ
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			closeConnection();
		}
		return tblUser;
	}

	/**
	 * Phương thức trả về phần order by của câu lệnh query lấy list user
	 * 
	 * @param sortType
	 *            Cột được ưu tiên sắp xếp (full_name or end_date or code_level)
	 * @param sortByFullName
	 *            Giá trị sắp xếp của cột Tên (ASC or DESC)
	 * @param sortByCodeLevel
	 *            Giá trị sắp xếp của cột Năng lực tiếng Nhật (ASC or DESC)
	 * @param sortByEndDate
	 *            Giá trị sắp xếp của cột Ngày hết hạn (ASC or DESC)
	 * @return
	 * @throws SQLException,
	 *             ClassNotFoundException
	 * @throws IOException
	 */
	private String getOrderString(String sortType, String sortByFullName, String sortByCodeLevel, String sortByEndDate)
			throws SQLException, IOException {
		// Khai báo câu lệnh truy vấn
		StringBuilder orderString = new StringBuilder();
		try {
			// Lấy về danh sách tên cột trong DB
			List<String> listColumn = getListColumnNames(DatabaseProperties.getProperty("db_name"), null, null);
			// Kiểm tra xem các cột cần order có tồn tại trong DB
			// không
			if (listColumn.contains(Constant.FULL_NAME_COL) && listColumn.contains(Constant.CODE_LEVEL_COL)
					&& listColumn.contains(Constant.END_DATE_COL)) {
				// Nếu các cột có tồn tại trong DB tạo câu lệnh
				// order by
				orderString.append("ORDER BY ");
				// Định nghĩa cột fullname và điều kiện tìm kiếm
				// theo fullname
				String sortFullName = "u.full_name" + " " + sortByFullName;
				// Định nghĩa cột code_level và điều kiện tìm kiếm
				// theo code_level
				String sortCodeLevel = "d.code_level" + " " + sortByCodeLevel;
				// Định nghĩa cột end_date và điều kiện tìm kiếm
				// theo end_date
				String sortEndDate = "d.end_date" + " " + sortByEndDate;

				// Kiểm tra cột được ưu tiên sắp xếp
				switch (sortType) {
				// Sắp xếp câu lệnh truy vấn theo các kiểu truy vấn
				// Trường hợp sort theo level
				case Constant.SORT_LEVEL:
					orderString.append(sortCodeLevel).append(", ");
					orderString.append(sortFullName).append(", ");
					orderString.append(sortEndDate);
					break;
				// Trường hợp sort theo end_date
				case Constant.SORT_DATE:
					orderString.append(sortEndDate).append(", ");
					orderString.append(sortFullName).append(", ");
					orderString.append(sortCodeLevel);
					break;
				// Các trường hợp khác, sort mặc định theo full_name
				default:
					orderString.append(sortFullName).append(", ");
					orderString.append(sortCodeLevel).append(", ");
					orderString.append(sortEndDate);
					break;
				}
			}
			// Nếu có lỗi, in ra thông báo và ném ngoại lệ
		} catch (SQLException | IOException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			// Ném ra ngoại lệ
			throw e;
		}
		return orderString.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDao#getTotalUsers(java.lang.String, int)
	 */
	@Override
	public int getTotalUsers(String fullName, int groupId) throws ClassNotFoundException, SQLException {
		int number = 0;
		try {
			// Mở kết nối tới DB
			connectDB();
			if (connection != null) {
				// Viết câu lệnh select
				StringBuilder query = new StringBuilder("SELECT ");
				// lấy về số lượng user thông qua user_id
				query.append("COUNT(u.user_id) ");
				// lấy từ bảng tbl_user
				query.append("FROM tbl_user u ");
				// INNER JOIN mst_group thông qua group_id
				query.append("INNER JOIN mst_group g USING(group_id) ");
				query.append("LEFT JOIN tbl_detail_user_japan d USING(user_id) ");
				query.append("LEFT JOIN mst_japan j USING(code_level) ");
				// lấy user có rule = 1
				query.append("WHERE u.rule = ? ");
				// Nếu groupId > 0 (có chọn group)
				if (groupId > 0) {
					// Thêm điều kiện tìm kiếm theo groupId
					query.append("AND g.group_id = ? ");
				}
				// Nếu textbox tìm kiếm theo name được nhập vào
				if (!Constant.EMPTY_STRING.equals(fullName)) {
					query.append("AND u.full_name LIKE BINARY ? ");
				}
				// Khai báo biến chỉ số thứ tự cột
				int index = Constant.DEFAULT_INDEX;
				preparedStatement = connection.prepareStatement(query.toString());
				// Gán giá trị cho user rule
				preparedStatement.setInt(index++, Constant.USER_RULE);
				// Nếu groupId > 0 (có chọn group)
				if (groupId != Constant.DEFAULT_GROUP_ID) {
					// Gán giá trị groupId cho câu truy vấn
					preparedStatement.setInt(index++, groupId);
				}
				// Nếu textbox tìm kiếm theo name được nhập vào
				if (!Constant.DEFAULT_FULLNAME.equals(fullName)) {
					// Gán giá trị name cho câu truy vấn
					preparedStatement.setString(index++, "%" + fullName + "%");
				}
				// Thực hiện câu lệnh truy vấn
				resultSet = preparedStatement.executeQuery();
				// Nếu có kết quả trả về
				if (resultSet.next()) {
					// Lấy về số lượng user tại cột 1 của bảng kết quả
					number = resultSet.getInt(1);
				}
			}
			// Bắt lỗi và in thông báo
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		} finally {
			// Đóng connection tới DB
			closeConnection();
		}
		// trả về số lượng user
		return number;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDao#getListUsers(int, int, int,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<UserInfor> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate)
			throws SQLException, ClassNotFoundException, IOException {
		// Khai báo list user để trả về
		List<UserInfor> listUser = null;
		try {
			connectDB();
			if (connection != null) {
				// Khai báo câu lệnh truy vấn
				StringBuilder selectString = new StringBuilder("SELECT ");
				// Thêm các trường tìm kiếm
				selectString.append("u.user_id, u.login_name, u.full_name, u.full_name_kana, u.birthday, u.email, ");
				selectString.append(" u.tel, g.group_name, j.name_level, d.start_date, d.end_date, d.total ");
				// Thêm các bảng chứa dữ liệu cần lấy và điều kiện
				// join bảng
				selectString.append("FROM tbl_user u ");
				selectString.append("INNER JOIN mst_group g USING(group_id) ");
				selectString.append("LEFT JOIN tbl_detail_user_japan d USING(user_id) ");
				selectString.append("LEFT JOIN mst_japan j USING(code_level) ");
				// Điều kiện user rule = 1 (user không phải admin)
				selectString.append("WHERE u.rule = ? ");
				// Thêm điều kiện tìm kiếm theo group hoặc fullname nếu có
				if (groupId != Constant.DEFAULT_GROUP_ID) {
					selectString.append("AND u.group_id = ? ");
				}
				if (!Constant.DEFAULT_FULLNAME.equals(fullName)) {
					selectString.append("AND u.full_name LIKE BINARY ? ");
				}
				// Lấy về phần order trong câu truy vấn
				String orderString = getOrderString(sortType, sortByFullName, sortByCodeLevel, sortByEndDate);

				// Khởi tạo danh sách user
				listUser = new ArrayList<UserInfor>();
				// Khởi tạo câu lệnh truy vấn = select string + order string
				StringBuilder query = new StringBuilder(selectString).append(orderString);
				// Thêm điều kiện lấy có limit
				query.append(" LIMIT ").append(limit);
				query.append(" OFFSET ").append(offset);
				query.append(";");
				// Gán câu truy vấn
				preparedStatement = connection.prepareStatement(query.toString());
				// Tạo biến index để đếm chỉ số
				int index = Constant.DEFAULT_INDEX;
				// Gán giá trị rule cho user
				preparedStatement.setInt(index++, Constant.USER_RULE);
				if (groupId > Constant.DEFAULT_GROUP_ID) {
					preparedStatement.setInt(index++, groupId);
				}
				if (!Constant.DEFAULT_FULLNAME.equals(fullName)) {
					preparedStatement.setString(index++, "%" + fullName + "%");
				}
				// Thực hiện câu lệnh truy vấn
				resultSet = preparedStatement.executeQuery();
				// Nếu có kết quả trả về
				while (resultSet.next()) {
					// Khai báo đối tượng UserInfor
					UserInfor user = new UserInfor();
					user.setUserId(resultSet.getInt("user_id")); // lấy user id
					user.setFullName(resultSet.getString("full_name")); // lấy
					// fullname
					user.setBirthday(resultSet.getString("birthday")); // lấy
																		// birthday
					user.setGroupName(resultSet.getString("group_name")); // lấy
																			// group
					// name
					user.setEmail(resultSet.getString("email")); // lấy email
					user.setTel(resultSet.getString("tel")); // lấy sđt
					// lấy trình độ tiếng Nhật
					user.setNameLevel(resultSet.getString("name_level"));
					// lấy ngày hết hạn
					user.setEndDate(resultSet.getString("end_date"));
					// lấy điểm
					user.setTotal(resultSet.getString("total"));
					// Thêm user vào danh sách
					listUser.add(user);
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			// In lỗi và ném ra ngoại lệ
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		} finally {
			// Đóng connection
			closeConnection();
		}
		return listUser;
	}

	/**
	 * 
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDao#getUserInforByUserId(int)
	 *
	 */
	@Override
	public UserInfor getUserInforByUserId(int id) throws SQLException, ClassNotFoundException {
		UserInfor userInfor = null;
		try {
			// Mở kết nối tới DB
			connectDB();
			if (connection != null) {
				// Khai báo câu lệnh truy vấn
				StringBuilder query = new StringBuilder("SELECT ");
				// Thêm các trường tìm kiếm
				query.append("u.group_id, u.login_name, u.full_name, u.full_name_kana, u.birthday, u.email, ");
				query.append(" u.tel, g.group_name, j.name_level, d.code_level, d.start_date, d.end_date, d.total ");
				// Thêm các bảng chứa dữ liệu cần lấy và điều kiện
				// join bảng
				query.append("FROM tbl_user u ");
				query.append("INNER JOIN mst_group g USING(group_id) ");
				query.append("LEFT JOIN tbl_detail_user_japan d USING(user_id) ");
				query.append("LEFT JOIN mst_japan j USING(code_level) ");
				// Điều kiện user rule = 1 (user không phải admin)
				query.append("WHERE u.rule = ? ");
				query.append("AND u.user_id = ?");
				preparedStatement = connection.prepareStatement(query.toString());
				// Set các giá trị cho câu truy vấn
				int index = Constant.DEFAULT_INDEX;
				preparedStatement.setInt(index++, Constant.USER_RULE);
				preparedStatement.setInt(index++, id);
				resultSet = preparedStatement.executeQuery();
				// Nếu có giá trị thỏa mãn điều kiện truy vấn
				while (resultSet.next()) {
					// Khởi tạo đối tượng userInfor và gán cho các thuộc tính
					// lấy ra từ DB
					userInfor = new UserInfor();
					userInfor.setUserId(id); // gán user id
					userInfor.setLoginName(resultSet.getString("login_name"));
					userInfor.setFullName(resultSet.getString("full_name"));
					userInfor.setFullNameKana(resultSet.getString("full_name_kana"));
					userInfor.setBirthday(resultSet.getString("birthday"));
					userInfor.setGroupId(resultSet.getInt("group_id"));
					userInfor.setGroupName(resultSet.getString("group_name"));
					userInfor.setEmail(resultSet.getString("email"));
					userInfor.setTel(resultSet.getString("tel"));
					// lấy trình độ tiếng Nhật
					userInfor.setCodeLevel(resultSet.getString("code_level"));
					userInfor.setNameLevel(resultSet.getString("name_level"));
					userInfor.setStartDate(resultSet.getString("start_date"));
					userInfor.setEndDate(resultSet.getString("end_date"));
					userInfor.setTotal(resultSet.getInt(Constant.TOTAL) + "");
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			// In ra lỗi và ném ra ngoại lệ
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		} finally {
			closeConnection();// Đóng connection
		}
		return userInfor;
	}

	/**
	 * 
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDao#getUserByLoginName(java.lang.String)
	 *
	 */
	@Override
	public TblUser getUserByLoginName(String loginName) throws ClassNotFoundException, SQLException {
		TblUser tblUser = null;
		try {// Khai báo connection
			connectDB();
			if (connection != null) {
				// Khai báo câu lệnh query
				StringBuilder query = new StringBuilder();
				query.append("SELECT user_id ");
				query.append("FROM tbl_user u ");
				query.append("WHERE u.login_name = ? ");
				query.append(";");
				// Set các giá trị cho câu truy vấn
				int index = Constant.DEFAULT_INDEX;
				preparedStatement = connection.prepareStatement(query.toString());
				preparedStatement.setString(index++, loginName);
				// Thực hiện câu lệnh truy vấn
				resultSet = preparedStatement.executeQuery();
				// Nếu có kết quả trả về, khởi tạo đối tượng tblUser
				if (resultSet.next()) {
					tblUser = new TblUser();
				}
			}
			// In ra lỗi và ném ra ngoại lệ
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		}
		return tblUser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDao#getUserByEmail(int, java.lang.String)
	 */
	@Override
	public TblUser getUserByEmail(int userId, String email) throws ClassNotFoundException, SQLException {
		TblUser tblUser = null;
		try {// Khai báo connection
			connectDB();
			if (connection != null) {
				// Khai báo câu lệnh query lấy về email
				StringBuilder query = new StringBuilder();
				query.append("SELECT user_id ");
				query.append("FROM tbl_user u ");
				query.append("WHERE u.email = ? ");
				// Nếu user > 0 (TH edit) không lấy email của chính người dùng
				// cần chỉnh sửa
				if (userId > 0) {
					query.append("AND u.user_id != ? ");
				}
				query.append(";");
				// Set các giá trị cho câu truy vấn
				int index = Constant.DEFAULT_INDEX;
				preparedStatement = connection.prepareStatement(query.toString());
				preparedStatement.setString(index++, email);
				// Nếu user > 0 (TH edit) gán user id của người dùng
				if (userId > 0) {
					preparedStatement.setInt(index++, userId);
				}
				// Thực hiện câu lệnh truy vấn
				resultSet = preparedStatement.executeQuery();
				// Nếu có kqua trả về, khởi tạo đối tượng
				if (resultSet.next()) {
					tblUser = new TblUser();
				}
			}
			// In ra lỗi và ném ra ngoại lệ
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		}
		return tblUser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDao#insertUser(manageuser.entities.TblUser)
	 */
	@Override
	public int insertUser(TblUser tblUser) throws SQLException {
		int userId = 0;
		try {
			if (connection != null) {
				// Khai báo câu lệnh insert
				StringBuilder insert = new StringBuilder();
				insert.append("INSERT INTO tbl_user ");
				insert.append("(group_id, login_name, `password`, full_name, full_name_kana ");
				insert.append(", email, tel, birthday, Rule, Salt ");
				insert.append(") ");
				insert.append("VALUES (");
				insert.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?");
				insert.append(")");

				// Thêm các tham số vào câu insert
				preparedStatement = connection.prepareStatement(insert.toString(), Statement.RETURN_GENERATED_KEYS);
				int index = Constant.DEFAULT_INDEX;
				preparedStatement.setInt(index++, tblUser.getGroupId());
				preparedStatement.setString(index++, tblUser.getLoginName());
				preparedStatement.setString(index++, tblUser.getPassword());
				preparedStatement.setString(index++, tblUser.getFullName());
				preparedStatement.setString(index++, tblUser.getFullNameKana());
				preparedStatement.setString(index++, tblUser.getEmail());
				preparedStatement.setString(index++, tblUser.getTel());
				preparedStatement.setString(index++, tblUser.getBirthday());
				preparedStatement.setInt(index++, tblUser.getRule());
				preparedStatement.setString(index++, tblUser.getSalt());

				// Thực hiện câu lệnh
				preparedStatement.executeUpdate();

				// Nếu thực hiện thành công, lấy về user id
				resultSet = preparedStatement.getGeneratedKeys();
				if (resultSet.next()) {
					int resultIndex = 1;
					userId = resultSet.getInt(resultIndex++);
				}
			}
		} catch (SQLException e) {
			// In ra thông báo lỗi và ném ra ngoại lệ
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		}
		return userId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDao#updateUser(manageuser.entities.TblUser)
	 */
	@Override
	public void updateUser(TblUser tblUser) throws SQLException {
		try {
			// Khai báo câu lệnh update
			StringBuilder update = new StringBuilder();
			update.append("UPDATE tbl_user ");
			update.append("SET ");
			update.append("group_id = ?, full_name = ?, full_name_kana = ?,");
			update.append("email = ?, tel = ?, birthday = ? ");
			update.append("WHERE user_id = ? ");
			update.append("AND rule = ? ");

			preparedStatement = connection.prepareStatement(update.toString());

			// Gán các giá trị cho tham số truyền vào câu lệnh
			int index = Constant.DEFAULT_INDEX;
			preparedStatement.setInt(index++, tblUser.getGroupId());
			preparedStatement.setString(index++, tblUser.getFullName());
			preparedStatement.setString(index++, tblUser.getFullNameKana());
			preparedStatement.setString(index++, tblUser.getEmail());
			preparedStatement.setString(index++, tblUser.getTel());
			preparedStatement.setString(index++, tblUser.getBirthday());
			preparedStatement.setInt(index++, tblUser.getUserId());
			preparedStatement.setInt(index++, Constant.USER_RULE);

			// Thực hiện câu lệnh update
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDao#deleteUser(int)
	 */
	@Override
	public void deleteUser(int userId) throws SQLException {
		try {
			// Nếu connection != null
			if (connection != null) {
				// Khai báo câu lệnh delete bản ghi trong bảng tbl_user với
				// user_id = id truyền vào
				StringBuilder delete = new StringBuilder();
				delete.append("DELETE FROM tbl_user ");
				delete.append("WHERE user_id = ? ");
				delete.append("AND rule = ? ");

				preparedStatement = connection.prepareStatement(delete.toString());
				// Gán tham số cho câu lệnh delete
				int index = Constant.DEFAULT_INDEX;
				preparedStatement.setInt(index++, userId);
				preparedStatement.setInt(index++, Constant.USER_RULE);
				// Thực hiện câu lệnh delete
				preparedStatement.execute();
			}
			// In và ném ra ngoại lệ nếu có
		} catch (SQLException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDao#getUserByUserId(int)
	 */
	@Override
	public TblUser getUserByUserId(int id) throws ClassNotFoundException, SQLException {
		TblUser tblUser = null;
		try {
			// keets nối DB
			connectDB();
			// Viết câu truy vấn
			StringBuilder query = new StringBuilder();
			query.append("SELECT rule ");
			query.append("FROM tbl_user ");
			query.append("WHERE user_id = ? ");
			preparedStatement = connection.prepareStatement(query.toString());
			int index = Constant.DEFAULT_INDEX;
			// Set các giá trị cho câu truy vấn
			preparedStatement.setInt(index++, id);
			// Thực hiện truy vấn
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				tblUser = new TblUser();
				tblUser.setRule(resultSet.getInt("rule"));
			}
			// In và ném ra ngoại lệ
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		} finally {
			closeConnection();
		}
		return tblUser;
	}



	

}
