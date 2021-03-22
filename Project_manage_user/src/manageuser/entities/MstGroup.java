/**
 * Copyright(C), 2019 Luvina Software Company
 * MstGroup.java, Jul 17, 2019, Phạm Minh Thảo
 */
package manageuser.entities;

/**
 * Lớp định nghĩa đối tượng MstGroup có các thuộc tính tương ứng với các trường
 * trong bảng mst_group
 * 
 * @author PhamMinhThao
 *
 */
public class MstGroup {
	// 2 thuộc tính tương ứng với 2 trường trong bảng mst_group
	private int groupId;
	private String groupName;

	/**
	 * @return the groupId
	 */
	public int getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
