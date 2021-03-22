/**
 * Copyright(C) 2019 Luvina Software Company
 * MstGroupDao.java, Jul 31, 2019, Phạm Minh Thảo
 */
package manageuser.dao;

import java.sql.SQLException;
import java.util.List;

import manageuser.entities.MstJapan;

/**
 * Interface định nghĩa các phương thức làm việc với bảng mst_japan
 * 
 * @author PhamMinhThao
 * 
 */
public interface MstJapanDao {

	/**
	 * Phương thức lấy về danh sách trình độ tiếng Nhật
	 * 
	 * @return danh sách trình độ tiếng Nhật
	 * @throws SQLException           ném exception xảy ra khi thao tác với DB
	 * @throws ClassNotFoundException ném exception xảy ra khi không tìm thấy file
	 *                                Class
	 */
	public List<MstJapan> getAllMstJapan() throws ClassNotFoundException, SQLException;

	/**
	 * Lấy về đối tượng MstJapan thông qua codeLevel
	 * 
	 * @param codeLevel
	 *            CodeLevel để lấy về MstJapan
	 * @return MstJapan nếu CodeLevel tồn tại trong DB, null nếu không tồn tại
	 * @throws SQLException           ném exception xảy ra khi thao tác với DB
	 * @throws ClassNotFoundException ném exception xảy ra khi không tìm thấy file
	 *                                Class
	 */
	public MstJapan getMstJapanByCodeLevel(String codeLevel) throws SQLException, ClassNotFoundException;
}
