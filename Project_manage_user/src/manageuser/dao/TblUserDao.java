/**
 * Copyright(C)Jul 17, 2019 Luvina Software Company
 * TblUserDao.java, Jul 17, 2019, Phạm Minh Thảo
 */
package manageuser.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import manageuser.entities.TblUser;
import manageuser.entities.UserInfor;

/**
 * Lớp interface định nghĩa các phương thức làm việc với bảng tbl_user
 * 
 * @author PhamMinhThao
 *
 */
public interface TblUserDao extends BaseDao {
	/**
	 * Lấy về thông tin user thông qua id
	 * 
	 * @param loginId tên đăng nhập của người dùng
	 * @return TblUser nếu tồn tại loginId trong DB, ngược lại trả về null
	 * @throws SQLException           ném exception xảy ra khi thao tác với DB
	 * @throws ClassNotFoundException ném exception xảy ra khi không tìm thấy file
	 *                                Class
	 */
	public TblUser getUserByLoginId(String loginId) throws SQLException, ClassNotFoundException;

	/**
	 * Phương thức lấy về danh sách user
	 * 
	 * @param offset          offset vị trí data cần lấy
	 * @param limit           limit số lượng lấy
	 * @param groupId         mã nhóm tìm kiếm
	 * @param fullName        Tên tìm kiếm
	 * @param sortType        Cột được ưu tiên sắp xếp(full_name or end_date or
	 *                        code_level)
	 * @param sortByFullName  Giá trị sắp xếp của cột Tên(ASC or DESC)
	 * @param sortByCodeLevel Giá trị sắp xếp của cột Trình độ tiếng nhật(ASC or
	 *                        DESC)
	 * @param sortByEndDate   Giá trị sắp xếp của cột Ngày kết hạn(ASC or DESC)
	 * @return Danh sách user
	 * @throws SQLException           ném exception xảy ra khi thao tác với DB
	 * @throws ClassNotFoundException ném exception xảy ra khi không tìm thấy file
	 *                                Class
	 */
	public List<UserInfor> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate)
			throws ClassNotFoundException, SQLException, IOException;

	/**
	 * Phương thức lấy về số lượng user
	 * 
	 * @param fullName Tên tìm kiếm
	 * @param groupId  Group tìm kiếm
	 * @return số lượng user
	 * @throws SQLException           ném exception xảy ra khi thao tác với DB
	 * @throws ClassNotFoundException ném exception xảy ra khi không tìm thấy file
	 *                                Class
	 */
	public int getTotalUsers(String fullName, int groupId) throws ClassNotFoundException, SQLException;

	/**
	 * Phương thức lấy về user thông qua user id
	 * 
	 * @param id id của user
	 * @return user có id cần tìm
	 * @throws SQLException           ném exception xảy ra khi thao tác với DB
	 * @throws ClassNotFoundException ném exception xảy ra khi không tìm thấy file
	 *                                Class
	 */
	public UserInfor getUserInforByUserId(int id) throws SQLException, ClassNotFoundException;

	/**
	 * Phương thức lấy về user theo loginName và email
	 * 
	 * @param userId    userId cần tìm
	 * @param loginName loginName cần tìm
	 * @return null nếu không tồn tại user thỏa mãn, ngược lại trả về đối tượng
	 *         TblUser
	 * @throws SQLException           ném exception xảy ra khi thao tác với DB
	 * @throws ClassNotFoundException ném exception xảy ra khi không tìm thấy file
	 *                                Class
	 */
	public TblUser getUserByLoginName(String loginName) throws ClassNotFoundException, SQLException;

	/**
	 * Phương thức lấy về user theo email và email
	 * 
	 * @param userId userId cần tìm
	 * @param email  email cần tìm
	 * @return null nếu không tồn tại user thỏa mãn, ngược lại trả về đối tượng
	 *         TblUser
	 * @throws SQLException           ném exception xảy ra khi thao tác với DB
	 * @throws ClassNotFoundException ném exception xảy ra khi không tìm thấy file
	 *                                Class
	 */
	public TblUser getUserByEmail(int userId, String email) throws ClassNotFoundException, SQLException;

	/**
	 * Phương thức thêm đối tượng tblUser vào bảng tbl_user
	 * 
	 * @param tblUser Đối tượng cần thêm
	 * @return id user tự động tăng trong DB
	 * @throws SQLException           ném exception xảy ra khi thao tác với DB
	 * @throws ClassNotFoundException ném exception xảy ra khi không tìm thấy file
	 *                                Class
	 */
	public int insertUser(TblUser tblUser) throws SQLException;

	/**
	 * Phương thức lấy về TblUser thông qua id
	 * 
	 * @param id id của user cần lấy
	 * @return Nếu id tồn tại, trả về TblUser, nếu không, trả về null
	 * @throws SQLException           ném exception xảy ra khi thao tác với DB
	 * @throws ClassNotFoundException ném exception xảy ra khi không tìm thấy file
	 *                                Class
	 */
	public TblUser getUserByUserId(int id) throws ClassNotFoundException, SQLException;

	/**
	 * Phương thức chỉnh sửa đối tượng tblUser trong bảng tbl_user
	 * 
	 * @param tblUser Đối tượng tblUser cần chỉnh sửa
	 * @throws SQLException ném exception xảy ra khi thao tác với DB
	 */
	public void updateUser(TblUser tblUser) throws SQLException;

	/**
	 * Phương thức xóa user trong bảng tbl_user
	 * 
	 * @param userId user id của user muốn xóa
	 * @throws SQLException ném exception xảy ra khi thao tác với DB
	 */
	public void deleteUser(int userId) throws SQLException;



}
