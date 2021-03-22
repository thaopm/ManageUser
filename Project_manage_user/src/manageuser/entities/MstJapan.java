/**
 * Copyright(C)Jul 17, 2019 Luvina Software Company
 * MstJapan.java, Jul 17, 2019, Phạm Minh Thảo
 */
package manageuser.entities;

/**
 * Lớp định nghĩa đối tượng MstJapan có các thuộc tính tương ứng với các trường
 * trong bảng mst_japan
 * 
 * @author PhamMinhThao
 *
 */
public class MstJapan {
	// 2 thuộc tính tương ứng với 2 trường trong bảng mst_japan
	private String codeLevel;
	private String nameLevel;

	/**
	 * @return the codeLevel
	 */
	public String getCodeLevel() {
		return codeLevel;
	}

	/**
	 * @param string the codeLevel to set
	 */
	public void setCodeLevel(String codeLevel) {
		this.codeLevel = codeLevel;
	}

	/**
	 * @return the nameLevel
	 */
	public String getNameLevel() {
		return nameLevel;
	}

	/**
	 * @param nameLevel the nameLevel to set
	 */
	public void setNameLevel(String nameLevel) {
		this.nameLevel = nameLevel;
	}

}
