/**
 * Copyright(C) 2019 Luvina Software Company
 * MstJapanDaoImpl.java, Jul 31, 2019, Phạm Minh Thảo
 */
package manageuser.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import manageuser.dao.MstJapanDao;
import manageuser.entities.MstJapan;
import manageuser.utils.Constant;

/**
 * Lớp khai báo các phương thức làm việc với bảng mst_japan
 *
 * @author PhamMinhThao
 */
public class MstJapanDaoImpl extends BaseDaoImpl implements MstJapanDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.MstJapanDao#getAllMstJapan()
	 */
	@Override
	public List<MstJapan> getAllMstJapan() throws ClassNotFoundException, SQLException {
		List<MstJapan> listMstJapan = null;
		try {
			// Mở kết nối tới DB
			connectDB();
			// nếu connection khác null, thực hiện truy vấn
			if (connection != null) {
				// Khởi tạo list MstJapan
				listMstJapan = new ArrayList<>();
				// Viết câu lệnh truy vấn từ bảng mst_japan
				StringBuilder query = new StringBuilder("SELECT * ");
				query.append("FROM mst_japan ");
				query.append("ORDER BY code_level ");
				query.append(";");
				preparedStatement = connection.prepareStatement(query.toString());
				resultSet = preparedStatement.executeQuery();
				// Nếu có kết quả trả về
				while (resultSet.next()) {
					// Tạo 1 đối tượng MstJapan
					MstJapan mstJapan = new MstJapan();
					// Set các thuộc tính cho đối tượng MstJapan
					mstJapan.setCodeLevel(resultSet.getString("code_level"));
					mstJapan.setNameLevel(resultSet.getString("name_level"));
					// Thêm đối tượng MstJapan vào danh sách
					listMstJapan.add(mstJapan);
				}
			}
			// Nếu có lỗi, in ra thông báo và ném ngoại lệ
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		} finally {
			closeConnection();
		}
		return listMstJapan;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.MstJapanDao#getMstJapanByCodeLevel(java.lang.String)
	 */
	@Override
	public MstJapan getMstJapanByCodeLevel(String codeLevel) throws SQLException, ClassNotFoundException {
		MstJapan mstJapan = null;
		try {
			// Mở kết nối tới DB
			connectDB();
			// nếu connection khác null, thực hiện truy vấn
			if (connection != null) {
				// Viết câu lệnh truy vấn
				StringBuilder query = new StringBuilder("SELECT * ");
				query.append("FROM mst_japan ");
				query.append("WHERE code_level = ? ");
				query.append(";");
				preparedStatement = connection.prepareStatement(query.toString());
				int index = Constant.DEFAULT_INDEX;
				// Gán giá trị cho các tham số cần truy vấn
				preparedStatement.setString(index++, codeLevel);
				// Thực hiện câu lệnh truy vấn
				resultSet = preparedStatement.executeQuery();
				// Nếu có kết quả trả về
				if (resultSet.next()) {
					// Khởi tạo đối tượng MstJapan và gán thuộc tính cho đối
					// tượng
					mstJapan = new MstJapan();
					mstJapan.setCodeLevel(codeLevel);
					mstJapan.setNameLevel(resultSet.getString("name_level"));
				}
			}
			// Nếu có lỗi, in ra thông báo và ném ngoại lệ
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
			// Đóng kết nối DB
		} finally {
			closeConnection();
		}
		return mstJapan;
	}

}
