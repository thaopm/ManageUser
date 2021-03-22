/**
 * Copyright(C) 2019 Luvina Software Company
 * MstGroupLogic.java, Jul 20, 2019, Phạm Minh Thảo
 */
package manageuser.logics;

import java.sql.SQLException;
import java.util.List;

import manageuser.entities.MstGroup;

/**
 * Lớp interface định nghĩa các phương thức xử lí logic cho đối tượng MstGroup
 * 
 * @author PhamMinhThao
 * 
 */
public interface MstGroupLogic {

	/**
	 * Phương thức lấy về danh sách các group trong CSDL
	 * 
	 * @return danh sách group
	 * @throws SQLException           ném exception xảy ra khi thao tác với DB
	 * @throws ClassNotFoundException ném exception xảy ra khi không tìm thấy file
	 *                                Class
	 */
	public List<MstGroup> getAllMstGroups() throws ClassNotFoundException, SQLException;

	/**
	 * Phương thức lấy về đối tượng MstGroup thông qua groupId
	 * 
	 * @param groupId id của group cần lấy
	 * @return MstGroup nếu id có tồn tại trong DB, null nếu id không tồn tại
	 * @throws SQLException           ném exception xảy ra khi thao tác với DB
	 * @throws ClassNotFoundException ném exception xảy ra khi không tìm thấy file
	 *                                Class
	 */
	public MstGroup getMstGroupById(int groupId) throws ClassNotFoundException, SQLException;

}
