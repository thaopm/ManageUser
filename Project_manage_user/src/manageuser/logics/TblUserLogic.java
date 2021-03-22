/**
 * Copyright(C)Jul 17, 2019 Luvina Software Company
 * TblUserLogic.java, Jul 17, 2019, Phạm Minh Thảo
 */
package manageuser.logics;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import manageuser.entities.UserInfor;

/**
 * Lớp interface định nghĩa các phương thức xử lí logic cho đối tượng TblUser
 * 
 * @author PhamMinhThao
 *
 */
public interface TblUserLogic {
	/**
	 * Phương thức kiểm tra user có tồn tại trong db không thông qua username
	 * và password được nhập vào
	 * 
	 * @param loginId username được nhập
	 * @param pass    password được nhập
	 * @return trả về true nếu có tồn tại, false nếu không tồn tại
	 * @throws SQLException             ném exception xảy ra khi thao tác với DB
	 * @throws ClassNotFoundException   ném exception xảy ra khi không tìm thấy file
	 *                                  Class
	 * @throws NoSuchAlgorithmException Ngoại lệ ném ra khi gọi thuật toán mã hóa
	 */
	boolean existLoginId(String loginId, String pass)
			throws SQLException, ClassNotFoundException, NoSuchAlgorithmException;

	/**
	 * Phương thức lấy về danh sách user
	 * 
	 * @param offset          offset vị trí data cần lấy
	 * @param limit           limit số lượng lấy
	 * @param groupId         mã nhóm tìm kiếm
	 * @param fullName        Tên tìm kiếm
	 * @param sortType        Cột được ưu tiên sắp xếp(full_name or end_date or
	 *                        code_level)
	 * @param sortByFullName  Giá trị sắp xếp của cột Tên (ASC or DESC)
	 * @param sortByCodeLevel Giá trị sắp xếp của cột Trình độ tiếng nhật (ASC or
	 *                        DESC)
	 * @param sortByEndDate   Giá trị sắp xếp của cột Ngày kết hạn (ASC or DESC)
	 * @return Danh sách user
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	List<UserInfor> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
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
	int getTotalUsers(String fullName, int groupId) throws ClassNotFoundException, SQLException;

	/**
	 * Phương thức lấy về user thông qua user id
	 * 
	 * @param id id của user
	 * @return user có id cần tìm
	 * @throws SQLException           ném exception xảy ra khi thao tác với DB
	 * @throws ClassNotFoundException ném exception xảy ra khi không tìm thấy file
	 *                                Class
	 */
	UserInfor getUserInforByUserId(int id) throws SQLException, ClassNotFoundException;

	/**
	 * Phương thức kiểm tra login name đã tồn tại hay chưa
	 * 
	 * @param userId    id user cần kiểm tra
	 * @param loginName loginName cần kiểm tra
	 * @return True: login name đã tồn tại, False: login name không tồn tại
	 * @throws SQLException           ném exception xảy ra khi thao tác với DB
	 * @throws ClassNotFoundException ném exception xảy ra khi không tìm thấy file
	 *                                Class
	 */
	boolean checkExistedLoginName(String loginName) throws ClassNotFoundException, SQLException;

	/**
	 * Phương thức kiểm tra email đã tồn tại trong bảng tbl_user chưa
	 * 
	 * @param userId user id cần kiểm tra
	 * @param email  email cần kiểm tra
	 * @return True: email đã tồn tại, False: email không tồn tại
	 * 
	 * @throws SQLException           ném exception xảy ra khi thao tác với DB
	 * @throws ClassNotFoundException ném exception xảy ra khi không tìm thấy file
	 *                                Class
	 */
	boolean checkExistedEmail(int userId, String email) throws ClassNotFoundException, SQLException;

	/**
	 * Phương thức Insert data user vào bảng tbl_user và tbl_detail_user_japan
	 * 
	 * @param userInfor đối tượng userInfor chứa thông tin cần thêm
	 * 
	 * @throws SQLException             ném exception xảy ra khi thao tác với DB
	 * @throws ClassNotFoundException   ném exception xảy ra khi không tìm thấy file
	 *                                  Class
	 * @throws NoSuchAlgorithmException Ngoại lệ ném ra khi gọi thuật toán mã hóa
	 */
	void createUser(UserInfor userInfor) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException;

	/**
	 * Phương thức kiểm tra user id có tồn tại trong DB và có phải id của
	 * user hay không 
	 * 
	 * @param id user id cần kiểm tra
	 * @return True nếu id tồn tại, False nếu không tồn tại
	 * @throws SQLException           ném exception xảy ra khi thao tác với DB
	 * @throws ClassNotFoundException ném exception xảy ra khi không tìm thấy file
	 *                                Class
	 */
	boolean checkExistedUserId(int id) throws ClassNotFoundException, SQLException;

	/**
	 * Phương thức Chỉnh sửa thông tin user
	 * 
	 * @param userInfor User cần chỉnh sửa thông tin
	 * @throws SQLException             ném exception xảy ra khi thao tác với DB
	 * @throws ClassNotFoundException   ném exception xảy ra khi không tìm thấy file
	 *                                  Class
	 * @throws NoSuchAlgorithmException Ngoại lệ ném ra khi gọi thuật toán mã hóa
	 */
	void editUser(UserInfor userInfor) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException;

	/**
	 * Phương thức thực hiện xóa user trong DB
	 * 
	 * @param userId user id của user cần xóa
	 * @throws SQLException           ném exception xảy ra khi thao tác với DB
	 * @throws ClassNotFoundException ném exception xảy ra khi không tìm thấy file
	 *                                Class
	 */
	void deleteUser(int userId) throws ClassNotFoundException, SQLException;

	/**
	 * Phương thức kiểm tra xem user có phải là admin không
	 * 
	 * @param userId user id của user cần kiểm tra
	 * @return true nếu là admin, false nếu là user
	 * @throws SQLException           ném exception xảy ra khi thao tác với DB
	 * @throws ClassNotFoundException ném exception xảy ra khi không tìm thấy file
	 *                                Class
	 */
	boolean checkAdminById(int userId) throws ClassNotFoundException, SQLException;

	/**
	 * Phương thức kiểm tra xem login name được truyền vào có phải login name của
	 * admin hay không
	 * 
	 * @param loginName login name cần kiểm tra
	 * @return True nếu login name của admin, false nếu không phải
	 * @throws SQLException           ném exception xảy ra khi thao tác với DB
	 * @throws ClassNotFoundException ném exception xảy ra khi không tìm thấy file
	 *                                Class
	 */
	boolean checkAdminByLoginName(String loginName) throws ClassNotFoundException, SQLException;
}
