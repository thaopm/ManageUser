/**
 * Copyright(C) 2019 Luvina Software Company
 * MstGroupLogicImpl.java, Jul 20, 2019, Phạm Minh Thảo
 */
package manageuser.logics.impl;

import java.sql.SQLException;
import java.util.List;

import manageuser.dao.MstGroupDao;
import manageuser.dao.impl.MstGroupDaoImpl;
import manageuser.entities.MstGroup;
import manageuser.logics.MstGroupLogic;

/**
 * Lớp thực hiện xử lý logic cho đối tượng group
 * 
 * @author PhamMinhThao
 * 
 */
public class MstGroupLogicImpl implements MstGroupLogic {

	// Khai báo đối tượng MstGroupDao
	private MstGroupDao groupDao = new MstGroupDaoImpl();

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.MstGroupLogic#getAllMstGroups()
	 */
	@Override
	public List<MstGroup> getAllMstGroups() throws ClassNotFoundException, SQLException {
		// Khởi tạo listGroup
		List<MstGroup> listGroup = null;
		try {
			// Gọi đến MstGroupDao để lấy về danh sách các group
			listGroup = groupDao.getAllMstGroups();
		} catch (ClassNotFoundException | SQLException e) {
			// In lỗi và ném ngoại lệ nếu có
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		}
		return listGroup;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.MstGroupLogic#getMstGroupById(int)
	 */
	@Override
	public MstGroup getMstGroupById(int groupId) throws ClassNotFoundException, SQLException {
		MstGroup mstGroup = null;
		try {
			// Gọi đến MstGroupDao để lấy về group thông qua id
			mstGroup = groupDao.getMstGroupById(groupId);
		} catch (ClassNotFoundException | SQLException e) {
			// In lỗi và ném ngoại lệ nếu có
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		}
		return mstGroup;
	}

}
