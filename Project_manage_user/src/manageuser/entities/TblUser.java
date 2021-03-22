/**
 * Copyright(C)Jul 17, 2019 Luvina Software Company
 * TblUser.java, Jul 17, 2019, Phạm Minh Thảo
 */
package manageuser.entities;

/**
 * Lớp định nghĩa đối tượng TblUser có các thuộc tính tương ứng với các
 * trường trong bảng tbl_user
 * 
 * @author PhamMinhThao
 *
 */
public class TblUser {
	private int userId; // user_id
	private int groupId; // group_id
	private String loginName; // login_name
	private String password; // password
	private String fullName; // full_name
	private String fullNameKana; // full_name_kana
	private String email; // email
	private String tel; // tel
	private String birthday; // birthday
	private int rule; // rule
	private String salt; // salt

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
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the fullNameKana
	 */
	public String getFullNameKana() {
		return fullNameKana;
	}

	/**
	 * @param fullNameKana the fullNameKana to set
	 */
	public void setFullNameKana(String fullNameKana) {
		this.fullNameKana = fullNameKana;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return the birthday
	 */
	public String getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	/**
	 * @return the rule
	 */
	public int getRule() {
		return rule;
	}

	/**
	 * @param rule the rule to set
	 */
	public void setRule(int rule) {
		this.rule = rule;
	}

	/**
	 * @return the salt
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * @param salt the salt to set
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

}
