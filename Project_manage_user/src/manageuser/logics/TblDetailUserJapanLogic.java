/**
 * Copyright(C) 2019 Luvina Software Company
 * TblDetailUserJapanLogic.java, Aug 13, 2019, Phạm Minh Thảo
 */
package manageuser.logics;

import java.sql.SQLException;

/**
 * Lớp interface định nghĩa các phương thức xử lí logic cho đối tượng
 * TblDetailUserJapan
 *
 * @author PhamMinhThao
 */
public interface TblDetailUserJapanLogic {

	/**
	 * Phương thức kiểm tra xem user có thông tin năng lực tiếng Nhật trong DB hay
	 * không
	 * 
	 * @param id user id của user cần kiểm tra
	 * @return True nếu có thông tin năng lực tiếng Nhật trong DB, False nếu không
	 *         có
	 * @throws SQLException           ném exception xảy ra khi thao tác với DB
	 * @throws ClassNotFoundException ném exception xảy ra khi không tìm thấy file
	 *                                Class
	 */
	boolean checkExistedJapaneseLevel(int id) throws ClassNotFoundException, SQLException;
}
