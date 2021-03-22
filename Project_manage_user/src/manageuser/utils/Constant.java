/**
 * Copyright(C)Jul 17, 2019 Luvina Software Company
 * Constant.java, Jul 17, 2019, Phạm Minh Thảo
 */
package manageuser.utils;

/**
 * Lớp định nghĩa các thuộc tính chung cho project
 * 
 * @author PhamMinhThao
 *
 */
public class Constant {
	/**
	 * Định nghĩa chuỗi rỗng
	 */
	public static final String EMPTY_STRING = "";
	public static final int NUMBER_1 = 1;
	public static final int NUMBER_0 = 0;

	public static final int USER_RULE = NUMBER_1;
	public static final int ADMIN_RULE = NUMBER_0;

	// .jsp url
	public static final String JSP_ADM001 = "/views/jsp/ADM001.jsp";
	public static final String JSP_ADM002 = "/views/jsp/ADM002.jsp";
	public static final String JSP_ADM003 = "/views/jsp/ADM003.jsp";
	public static final String JSP_ADM004 = "/views/jsp/ADM004.jsp";
	public static final String JSP_ADM005 = "/views/jsp/ADM005.jsp";
	public static final String JSP_ADM006 = "/views/jsp/ADM006.jsp";
	public static final String JSP_SYSTEM_ERROR = "/views/jsp/System_Error.jsp";

	// Biến mặc định màn hình ADM002
	// Trường hợp Sort
	public static final String FULL_NAME_COL = "full_name";
	public static final String CODE_LEVEL_COL = "code_level";
	public static final String END_DATE_COL = "end_date";
	public static final String ASC = "ASC";
	public static final String DESC = "DESC";
	public static final int DEFAULT_INDEX = 1;

	public static final String SORT_FULLNAME = "sortByName";
	public static final String SORT_LEVEL = "sortByLevel";
	public static final String SORT_DATE = "sortByDate";
	public static final String DEFAULT_SORT_TYPE = SORT_FULLNAME;

	public static final String METHOD_GET = "GET";
	public static final String METHOD_POST = "POST";

	// Action
	public static final String ACTION = "action";
	public static final String SEARCH = "search";
	public static final String SORT = "sort";
	public static final String PAGING = "paging";
	public static final String BACK = "back";
	public static final String CONFIRM = "confirm";
	public static final String EDIT = "edit";

	public static final String ATB_NAME = "fullname";
	public static final String ATB_GROUP_ID_SELECTED = "selectedId";
	public static final String ATB_GROUP_ID = "groupId";
	public static final String ATB_SORT_TYPE = "sortType";
	public static final String ATB_LIST_PAGING = "listPaging";
	public static final String ATB_TOTAL_PAGE = "totalPage";
	public static final String ATB_LIST_GROUP = "listGroup";
	public static final String ATB_LIST_USER = "listUser";
	public static final String ATB_NO_USER_MESS = "userNotFound";
	public static final String ATB_ORDER = "order";
	public static final String ATB_USER_ID = "userId";
	public static final String ATB_USER = "user";

	public static final String LIMIT_PAGE = "limitPage";
	public static final String FIRST_PAGE = "firstpage";
	public static final String LAST_PAGE = "lastpage";
	public static final String CURRENT_PAGE = "currentPage";

	public static final String ERRORS = "error";

	// Controller

	public static final String LISTUSER_URL = "ListUser.do";
	public static final String SYSTEM_ERR_URL = "SystemError";
	public static final String SYSTEM_ERROR_URL = "SystemError";
	public static final String SUCCESS_URL = "Success.do";
	public static final String ADD_INPUT_URL = "AddUserInput.do";
	public static final String ADD_CONFIRM_URL = "AddUserConfirm.do";
	public static final String LOGIN_URL = "Login.do";
	public static final String EDIT_CONFIRM_URL = "EditUserConfirm.do";
	public static final String DELETE_USER_URL = "DeleteUser.do";
	public static final String LOGOUT_CONTROLLER = "Logout";

	public static final int FIRST_YEAR = 1900;

	public static final String DEFAULT_FULLNAME = EMPTY_STRING;
	public static final String DEFAULT_PASSWORD = EMPTY_STRING;
	public static final String DEFAULT_LOGIN_NAME = EMPTY_STRING;
	public static final String DEFAULT_EMAIL = EMPTY_STRING;
	public static final String DEFAULT_TEL = EMPTY_STRING;
	public static final String DEFAULT_CODE_LEVEL = EMPTY_STRING;
	public static final int DEFAULT_USER_ID = NUMBER_0;
	public static final int DEFAULT_GROUP_ID = NUMBER_0;
	public static final int DEFAULT_CURRENT_PAGE = NUMBER_1;
	public static final int DEFAULT_OFFSET = NUMBER_0;
	public static final int DEFAULT_NUMBER_PAGE = 3;

	public static final String LIST_ERROR = "listError";
	public static final String LIST_BIRTHDAY = "listBirthday";
	public static final String LIST_START_DATE = "listStartDate";
	public static final String LIST_END_DATE = "listEndDate";

	public static final int LENGTH_LOGIN_MIN = 4;
	public static final int LENGTH_LOGIN_MAX = 15;
	public static final int LENGTH_FULLNAME = 255;
	public static final int LENGTH_FULLNAME_KANA = 255;
	public static final int LENGTH_EMAIL = 100;
	public static final int LENGTH_TEL = 14;
	public static final int LENGTH_PASS_MIN = 5;
	public static final int LENGTH_PASS_MAX = 15;
	public static final int LENGTH_TOTAL = 11;

	public static final String FORMAT_TEL = "[0-9]{1,4}-[0-9]{1,4}-[0-9]{1,4}";
	// Start fix bug ID 123 – PMT 2019/08/08
	public static final String FORMAT_EMAIL = ".+@.+\\..+";
	// End fix bug ID 123 – PMT 2019/08/08
	public static final String FORMAT_KANA = "[\\u30a0-\\u30ff\\uFF65-\\uFF9F　]*";
	public static final String FORMAT_LOGIN_NAME = "[a-zA-Z_][0-9a-zA-Z_]+";
	public static final String FORMAT_TOTAL = "[1-9][0-9]+";

	public static final String TOTAL = "total";
	public static final String LOGIN_NAME = "loginName";
	public static final String MESSAGE = "mess";
	public static final String UTF8_ENCODING = "UTF-8";
	public static final String EMAIL = "email";
	public static final String TEL = "tel";
	public static final String PASSWORD = "password";
	public static final String REPASSWORD = "repassword";
	public static final String CODE_LEVEL = "codeLevel";
	public static final String NAME_KATAKANA = "nameKana";

	public static final String EDIT_SUCCESS = "EditSuccess";
	public static final String DELETE_SUCCESS = "DeleteSuccess";
	public static final String INSERT_SUCCESS = "InsertSuccess";

	public static final String ER001_LOGIN_NAME = "ER001_LoginName";
	public static final String ER001_FULLNAME = "ER001_FullName";
	public static final String ER001_EMAIL = "ER001_Email";
	public static final String ER001_TEL = "ER001_Tel";
	public static final String ER001_PASS = "ER001_Pass";
	public static final String ER001_PASS_CONFIRM = "ER001_PassConfirm";
	public static final String ER001_TOTAL = "ER001_Total";
	public static final String ER001_LOGIN_PASS = "ER001_LoginPass";
	public static final String ER001_CODE_LEVEL = "ER001_CodeLevel";
	public static final String ER002_GROUP_ID = "ER002_GroupId";
	public static final String ER003_LOGIN_NAME = "ER003_LoginName";
	public static final String ER003_EMAIL = "ER003_Email";
	public static final String ER004_GROUP_ID = "ER004_GroupId";
	public static final String ER004_CODE_LEVEL = "ER004_CodeLevel";
	public static final String ER005_EMAIL = "ER005_Email";
	public static final String ER005_TEL = "ER005_Tel";
	public static final String ER006_FULLNAME = "ER006_FullName";
	public static final String ER006_FULLNAME_KANA = "ER006_FullNameKana";
	public static final String ER006_EMAIL = "ER006_Email";
	public static final String ER006_TEL = "ER006_Tel";
	public static final String ER006_TOTAL = "ER006_Total";
	public static final String ER007_LOGIN_NAME = "ER007_LoginName";
	public static final String ER007_TEL = "ER007_Tel";
	public static final String ER007_PASS = "ER007_Pass";
	public static final String ER008 = "ER008";
	public static final String ER009 = "ER009";
	public static final String ER010 = "ER010";
	public static final String ER011_BIRTHDAY = "ER011_Birthday";
	public static final String ER011_START_DATE = "ER011_StartDate";
	public static final String ER011_END_DATE = "ER011_EndDate";
	public static final String ER012 = "ER012";
	public static final String ER013 = "ER013";
	public static final String ER014 = "ER014";
	public static final String ER015 = "ER015";
	public static final String ER016 = "ER016";
	public static final String ER017 = "ER017";
	public static final String ER018 = "ER018";
	public static final String ER019 = "ER019";
	public static final String ER020 = "ER020";

	public static final String MSG001 = "MSG001";
	public static final String MSG002 = "MSG002";
	public static final String MSG003 = "MSG003";
	public static final String MSG004 = "MSG004";
	public static final String MSG005 = "MSG005";

	public static final String KEY = "key";
	public static final String FROM_ADM003 = "fromADM003";
	public static final String YEAR_BIRTHDAY = "yearBirthday";
	public static final String MONTH_BIRTHDAY = "monthBirthday";
	public static final String DAY_BIRTHDAY = "dayBirthday";
	public static final String YEAR_START_DATE = "yearStartDate";
	public static final String MONTH_START_DATE = "monthStartDate";
	public static final String DAY_START_DATE = "dayStartDate";
	public static final String YEAR_END_DATE = "yearEndDate";
	public static final String MONTH_END_DATE = "monthEndDate";
	public static final String DAY_END_DATE = "dayEndDate";
}
