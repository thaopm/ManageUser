/**
 * Copyright(C) 2019 Luvina Software Company
 * TblDetailUserJapan.java, Aug 5, 2019, Phạm Minh Thảo
 */
package manageuser.dao;

import java.sql.SQLException;

import manageuser.entities.TblDetailUserJapan;

/**
 * Interface thực hiện thao tác với database bảng tblUserDetailJapan
 *
 * @author PhamMinhThao
 */
public interface TblDetailUserJapanDao extends BaseDao {

	/**
	 * Thực hiện thêm mới 1 user vào bảng tbl_User_Detail_Japan. Nếu thêm mới được
	 * thì không return gì. Nếu không thêm mới được sẽ bắn ra Exception tương ứng
	 * 
	 * @param tblDetailUserJapan Đối tượng chứa thông tin của TblDetailUserJapan
	 * @throws SQLException ném exception xảy ra khi thao tác với DB
	 */
	public void insertDetailUserJapan(TblDetailUserJapan tblDetailUserJapan) throws SQLException;

	/**
	 * Phương thức lấy về TblDetailUserJapan trong bảng tbl_User_Detail_Japan thông
	 * qua user id
	 * 
	 * @param id id của user cần tìm
	 * @return TblDetailUserJapan nếu trong bảng tblUserDetailJapan có tồn tại user
	 *         id, trả về null nếu không tồn tại
	 * @throws SQLException           ném exception xảy ra khi thao tác với DB
	 * @throws ClassNotFoundException ném exception xảy ra khi không tìm thấy file
	 *                                Class
	 */
	public TblDetailUserJapan getDetailUserJapanByUserId(int id) throws ClassNotFoundException, SQLException;

	/**
	 * Thực hiện chỉnh sửa thông tin 1 bản ghi trong bảng tbl_User_Detail_Japan.
	 * 
	 * @param detailUserJapan Truyền vào thông tin bản ghi để chỉnh sửa
	 * @throws SQLException ném exception xảy ra khi thao tác với DB
	 */
	public void updateDetailUserJapan(TblDetailUserJapan detailUserJapan) throws SQLException;

	/**
	 * Thực hiện xóa 1 bản ghi trong bảng tbl_User_Detail_Japan
	 * 
	 * @param userId user id của bản ghi cần xóa
	 * @throws SQLException ném exception xảy ra khi thao tác với DB
	 */
	public void deleteDetailUserJapan(int userId) throws SQLException;
}
