/**
 * Copyright(C) 2019 Luvina Software Company
 * TblDetailUserJapanImpl.java, Aug 5, 2019, Phạm Minh Thảo
 */
package manageuser.dao.impl;

import java.sql.SQLException;

import manageuser.dao.TblDetailUserJapanDao;
import manageuser.entities.TblDetailUserJapan;
import manageuser.utils.Constant;

/**
 * Lớp thao tác với bảng tbl_detail_user_japan
 *
 * @author PhamMinhThao
 */
public class TblDetailUserJapanDaoImpl extends BaseDaoImpl implements TblDetailUserJapanDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblDetailUserJapanDao#insertDetailUserJapan
	 */
	@Override
	public void insertDetailUserJapan(TblDetailUserJapan tblDetailUserJapan) throws SQLException {
		try {
			if (connection != null) {
				// Khai báo câu lệnh insert
				StringBuilder insert = new StringBuilder();
				insert.append("INSERT INTO tbl_detail_user_japan ");
				insert.append("(user_id, code_level, start_date, end_date, total");
				insert.append(") ");
				insert.append("VALUES (?, ?, ?, ?, ?");
				insert.append(")");

				// Thực hiện gán các giá trị vào câu lệnh insert
				preparedStatement = connection.prepareStatement(insert.toString());
				int index = Constant.DEFAULT_INDEX;
				preparedStatement.setInt(index++, tblDetailUserJapan.getUserId());
				preparedStatement.setString(index++, tblDetailUserJapan.getCodeLevel());
				preparedStatement.setString(index++, tblDetailUserJapan.getStartDate());
				preparedStatement.setString(index++, tblDetailUserJapan.getEndDate());
				preparedStatement.setInt(index++, tblDetailUserJapan.getTotal());

				// Thực hiện câu insert
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblDetailUserJapanDao#getDetailUserJapanByUserId(int)
	 */
	@Override
	public TblDetailUserJapan getDetailUserJapanByUserId(int id) throws ClassNotFoundException, SQLException {
		// Khai báo đối tượng detailUserJapan = null
		TblDetailUserJapan detailUserJapan = null;
		try {
			connectDB();
			if (connection != null) {
				// Viết câu lệnh truy vấn
				StringBuilder query = new StringBuilder();
				query.append("SELECT detail_user_japan_id ");
				query.append("FROM `tbl_detail_user_japan` ");
				query.append("WHERE user_id = ? ");
				//
				preparedStatement = connection.prepareStatement(query.toString());

				// Gán tham số cho câu lệnh truy vấn
				int index = Constant.DEFAULT_INDEX;
				preparedStatement.setInt(index++, id);

				// Thực hiện câu lệnh truy vấn
				resultSet = preparedStatement.executeQuery();
				// Nếu có kết quả trả về
				if (resultSet.next()) {
					// Khai báo đối tượng detailUserJapan = new
					// TblDetailUserJapan
					detailUserJapan = new TblDetailUserJapan();
				}
			}
			// In lỗi và ném ngoại lệ nếu có
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		} finally {
			// Đóng connection
			closeConnection();
		}
		return detailUserJapan;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblDetailUserJapanDao#updateDetailUserJapan(TblDetailUserJapan)
	 */
	@Override
	public void updateDetailUserJapan(TblDetailUserJapan detailUserJapan) throws SQLException {
		try {
			if (connection != null) {
				// Khai báo câu lệnh update
				StringBuilder update = new StringBuilder();
				update.append("UPDATE tbl_detail_user_japan ");
				update.append("SET code_level = ?, start_date = ?, end_date = ?, total = ? ");
				update.append("WHERE user_id = ? ");

				// Thực hiện gán các giá trị vào câu lệnh insert
				preparedStatement = connection.prepareStatement(update.toString());
				int index = Constant.DEFAULT_INDEX;
				preparedStatement.setString(index++, detailUserJapan.getCodeLevel());
				preparedStatement.setString(index++, detailUserJapan.getStartDate());
				preparedStatement.setString(index++, detailUserJapan.getEndDate());
				preparedStatement.setInt(index++, detailUserJapan.getTotal());
				preparedStatement.setInt(index++, detailUserJapan.getUserId());

				// Thực hiện câu update
				preparedStatement.executeUpdate();
			}
			// In lỗi và ném ngoại lệ nếu có
		} catch (SQLException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		}
	}

	/*
	 * 
	 * (non-Javadoc)
	 * @see manageuser.dao.TblDetailUserJapanDao#deleteDetailUserJapan(int)
	 *
	 */
	@Override
	public void deleteDetailUserJapan(int userId) throws SQLException {
		try {
			if (connection != null) {
				// Khai báo câu lệnh delete
				StringBuilder delete = new StringBuilder();
				delete.append("DELETE FROM tbl_detail_user_japan ");
				delete.append("WHERE user_id = ? ");

				// Thực hiện gán các giá trị vào câu lệnh delete
				preparedStatement = connection.prepareStatement(delete.toString());
				int index = Constant.DEFAULT_INDEX;
				preparedStatement.setInt(index++, userId);

				// Thực hiện câu delete
				preparedStatement.execute();
			}
			// In lỗi và ném ngoại lệ nếu có
		} catch (SQLException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		}
	}

}
