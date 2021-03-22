/**
 * Copyright(C) 2019 Luvina Software Company
 * TblDetailUserJapanLogicImpl.java, Aug 13, 2019, Phạm Minh Thảo
 */
package manageuser.logics.impl;

import java.sql.SQLException;

import manageuser.dao.TblDetailUserJapanDao;
import manageuser.dao.impl.TblDetailUserJapanDaoImpl;
import manageuser.entities.TblDetailUserJapan;
import manageuser.logics.TblDetailUserJapanLogic;

/**
 * Class định nghĩa các phương thức xử lí logic cho đối tượng TblDetailUserJapan
 *
 * @author PhamMinhThao
 */
public class TblDetailUserJapanLogicImpl implements TblDetailUserJapanLogic {

	// Khởi tạo detailUserJapanDao
	private TblDetailUserJapanDao detailUserJapanDao = new TblDetailUserJapanDaoImpl();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * manageuser.logics.TblDetailUserJapanLogic#checkExistedJapaneseLevel(int)
	 */
	@Override
	public boolean checkExistedJapaneseLevel(int id) throws ClassNotFoundException, SQLException {
		// Khai báo biến kiểm tra tồn tại thông tin năng lực tiếng Nhật
		boolean exists = false;
		try {
			// Lấy về đối tượng TblDetailUserJapan thông qua user id
			TblDetailUserJapan detailUserJapan = detailUserJapanDao.getDetailUserJapanByUserId(id);
			if (detailUserJapan != null) {
				exists = true;
			}
			// In lỗi và ném ngoại lệ nếu có
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		}
		return exists;
	}

}
