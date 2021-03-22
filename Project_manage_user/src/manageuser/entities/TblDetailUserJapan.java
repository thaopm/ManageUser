/**
 * Copyright(C)Jul 17, 2019 Luvina Software Company
 * TblDetailUserJapan.java, Jul 17, 2019, Phạm Minh Thảo
 */
package manageuser.entities;

/**
 * Lớp định nghĩa đối tượng TblDetailUserJapan có các thuộc tính tương ứng với
 * các trường trong bảng tbl_detail_user_japan
 * 
 * @author PhamMinhThao
 *
 */
public class TblDetailUserJapan {

	// Các trường có trong bảng tbl_detail_user_japan
	private int detailUserJapanId;
	private int userId;
	private String codeLevel;
	private String startDate;
	private String endDate;
	private int total;

	/**
	 * @return the detailUserJapanId
	 */
	public int getDetailUserJapanId() {
		return detailUserJapanId;
	}

	/**
	 * @param detailUserJapanId the detailUserJapanId to set
	 */
	public void setDetailUserJapanId(int detailUserJapanId) {
		this.detailUserJapanId = detailUserJapanId;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the codeLevel
	 */
	public String getCodeLevel() {
		return codeLevel;
	}

	/**
	 * @param string the codeLevel to set
	 */
	public void setCodeLevel(String string) {
		this.codeLevel = string;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}

}
