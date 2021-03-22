/**
 * Copyright(C) 2019 Luvina Software Company
 * MstGroupDaoImpl.java, Jul 20, 2019, Phạm Minh Thảo
 */
package manageuser.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import manageuser.dao.MstGroupDao;
import manageuser.entities.MstGroup;
import manageuser.utils.Constant;

/**
 * Lớp thao tác với dữ liệu group trong database
 * 
 * @author PhamMinhThao
 * 
 */
public class MstGroupDaoImpl extends BaseDaoImpl implements MstGroupDao {

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.MstGroupDao#getAllMstGroups()
	 *
	 */
	@Override
	public List<MstGroup> getAllMstGroups() throws ClassNotFoundException, SQLException {
		// Khởi tạo danh sách group
		List<MstGroup> listGroup = null;
		try {// Mở connection
			connectDB();
			if (connection != null) {
				listGroup = new ArrayList<MstGroup>();
				// Khai báo câu lệnh truy vấn
				StringBuilder query = new StringBuilder("SELECT * ");
				query.append("FROM mst_group ");
				query.append("ORDER BY group_id;");
				preparedStatement = connection.prepareStatement(query.toString());
				resultSet = preparedStatement.executeQuery();
				// Nếu có kết quả trả về
				while (resultSet.next()) {
					// Tạo 1 đối tượng MstGroup
					MstGroup mstGroup = new MstGroup();
					// Set các thuộc tính cho đối tượng MstGroup và thêm vào danh sách
					mstGroup.setGroupId(resultSet.getInt("group_id"));
					mstGroup.setGroupName(resultSet.getString("group_name"));
					listGroup.add(mstGroup);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		} finally {
			resultSet.close();
			closeConnection();
		}
		return listGroup;
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.MstGroupDao#getMstGroupById(int)
	 *
	 */
	@Override
	public MstGroup getMstGroupById(int groupId) throws ClassNotFoundException, SQLException {
		MstGroup mstGroup = null;
		try {
			connectDB();
			if (connection != null) {
				StringBuilder query = new StringBuilder("SELECT * ");
				query.append("FROM mst_group ");
				query.append("WHERE group_id = ? ");
				query.append(";");
				preparedStatement = connection.prepareStatement(query.toString());
				int index = Constant.DEFAULT_INDEX;
				preparedStatement.setInt(index++, groupId);
				resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					mstGroup = new MstGroup();
					mstGroup.setGroupId(groupId);
					mstGroup.setGroupName(resultSet.getString("group_name"));
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		} finally {
			closeConnection();
		}
		return mstGroup;
	}

}
