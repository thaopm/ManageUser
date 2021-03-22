/**
 * Copyright(C) 2019 Luvina Software Company
 * MstJapanLogicImpl.java, Jul 31, 2019, Phạm Minh Thảo
 */
package manageuser.logics.impl;

import java.sql.SQLException;
import java.util.List;

import manageuser.dao.MstJapanDao;
import manageuser.dao.impl.MstJapanDaoImpl;
import manageuser.entities.MstJapan;
import manageuser.logics.MstJapanLogic;

/**
 * Lớp Java định nghĩa các phương thức xử lí logic cho đối tượng MstJapan
 *
 * @author PhamMinhThao
 */
public class MstJapanLogicImpl implements MstJapanLogic {

	// Khai báo đối tượng MstJapanDao
	private MstJapanDao japanDao = new MstJapanDaoImpl();

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.MstJapanLogic#getAllMstJapan()
	 */
	@Override
	public List<MstJapan> getAllMstJapan() throws ClassNotFoundException, SQLException {
		// Khởi tạo danh sách các trình độ tiếng Nhật
		List<MstJapan> listMstJapan = null;
		try {
			// Gọi đến MstJapanDao để lấy về danh sách các trình độ tiếng Nhật
			listMstJapan = japanDao.getAllMstJapan();
		} catch (ClassNotFoundException | SQLException e) {
			// In và ném ngoại lệ nếu có
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		}
		return listMstJapan;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.MstJapanLogic#getMstJapanByCodeLevel(java.lang.String)
	 */
	@Override
	public MstJapan getMstJapanByCodeLevel(String codeLevel) throws ClassNotFoundException, SQLException {
		MstJapan mstJapan = null;
		try {
			// Gọi đến MstJapanDao để lấy về trình độ tiếng Nhật thông qua code level
			mstJapan = japanDao.getMstJapanByCodeLevel(codeLevel);
		} catch (ClassNotFoundException | SQLException e) {
			// In và ném ngoại lệ nếu có
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		}
		return mstJapan;
	}
}
