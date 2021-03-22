/**
 * Copyright(C), 2019 Luvina Software Company
 * ListUserController.java, Jul 22, 2019, Phạm Minh Thảo
 */
package manageuser.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.entities.MstGroup;
import manageuser.entities.UserInfor;
import manageuser.logics.MstGroupLogic;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;
import manageuser.utils.MessageProperties;

/**
 * Lớp Servlet thực hiện chức năng list user cho màn hình ADM002
 * 
 * @author PhamMinhThao
 */
public class ListUserController extends HttpServlet {

	/**
	 * Tham số đánh dấu việc đọc và ghi servlet cùng 1 phiên bản
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Phương thức doGet thực hiện việc hiển thị thông tin danh sách user và điều
	 * kiện tìm kiếm lên màn hình ADM002 - List User
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			// Khai báo đối tượng MstGroupLogic
			MstGroupLogic groupLogic = new MstGroupLogicImpl();
			// Khai báo đối tượng TblUserLogic
			TblUserLogic userLogic = new TblUserLogicImpl();
			// Khai báo danh sách user
			List<UserInfor> listUser = new ArrayList<UserInfor>();
			// Lấy về danh sách group cho selectbox
			List<MstGroup> listGroup = groupLogic.getAllMstGroups();
			// Khai báo danh sách page cần hiển thị
			List<Integer> listPaging = new ArrayList<>();

			/**
			 * Lấy các giá trị tìm kiếm (group, name, current page, sort type, order) từ
			 * request, trong trường hợp không các thuộc tính không tồn tại trên request,
			 * gán giá trị mặc định mong muốn
			 */
			// groupId đang tìm kiếm (mặc định bằng 0)
			int groupId = Common.parseInt(request.getParameter(Constant.ATB_GROUP_ID), Constant.DEFAULT_GROUP_ID);
			// Tên tìm kiếm (Mặc định rỗng)
			String fullName = Common.getStringValue(request.getParameter(Constant.ATB_NAME), Constant.DEFAULT_FULLNAME);
			// Trang hiện tại (Mặc định trang 1)
			int currentPage = Common.parseInt(request.getParameter(Constant.CURRENT_PAGE),
					Constant.DEFAULT_CURRENT_PAGE);
			// Cột được ưu tiên sắp xếp (mặc định là fullname)
			String sortType = Common.getStringValue(request.getParameter(Constant.ATB_SORT_TYPE),
					Constant.DEFAULT_SORT_TYPE);
			// Kiểu sắp xếp các bản ghi của trường được ưu tiên (Mặc định tăng)
			String order = Common.getStringValue(request.getParameter(Constant.ATB_ORDER), Constant.ASC);
			

			// Kiểu sort mặc định của cột fullname = ASC
			String sortByFullName = Constant.ASC;
			// Kiểu sort mặc định của cột code_level = ASC
			String sortByCodeLevel = Constant.ASC;
			// Kiểu sort mặc định của cột end_date = DESC
			String sortByEndDate = Constant.DESC;

			// Lấy về kiểu action gọi đến trang ADM002
			String action = request.getParameter(Constant.ACTION);

			// Nếu là kiểu back (trở về từ các trang ADM003, ADM005)
			if (Constant.BACK.equals(action)) {
				fullName = (String) session.getAttribute(Constant.ATB_NAME);
				groupId = (int) session.getAttribute(Constant.ATB_GROUP_ID_SELECTED);
				sortType = (String) session.getAttribute(Constant.ATB_SORT_TYPE);
				sortByFullName = (String) session.getAttribute(Constant.SORT_FULLNAME);
				sortByCodeLevel = (String) session.getAttribute(Constant.SORT_LEVEL);
				sortByEndDate = (String) session.getAttribute(Constant.SORT_DATE);
				currentPage = (int) session.getAttribute(Constant.CURRENT_PAGE);
				// Nếu là paging hoặc sort, lấy điều kiện sort từ request
			} else {
				// Lấy về giá trị trường được ưu tiên sort và gán kiểu sort
				// cho trường đó
				switch (sortType) {
				// Nếu sort theo code level
				case Constant.SORT_LEVEL:
					// Thay đổi kiểu sort được chọn cho level
					sortByCodeLevel = order;
					break;
				// Nếu sort theo enddate
				case Constant.SORT_DATE:
					// Thay đổi kiểu sort được chọn cho enddate
					sortByEndDate = order;
					break;
				default:
					// Các trường hợp khác, sort mặc định theo fullname
					sortByFullName = order;
					break;
				}
			}

			// Lấy về số lượng user theo điều kiện tìm kiếm
			int totalUsers = userLogic.getTotalUsers(fullName, groupId);

			// Nếu totalUser > 0, thực hiện
			if (totalUsers > 0) {
				// Lấy về số lượng page tối đa được hiển thị
				int limitPage = Common.getLimitPage();
				// Lấy về số lượng bản ghi hiển thị trên 1 trang
				int limit = Common.getLimit();
				// Lấy về tổng số page (totalPage) thông qua tổng số
				// user và số lượng user hiển thị trên 1 trang
				int totalPage = Common.getTotalPage(totalUsers, limit);

				/**
				 * Kiểm tra page hiện tại có lớn hơn tổng số total page không. Nếu lớn hơn (tức
				 * current page không tồn tại), gán current page = total page (page cuối cùng)
				 */
				if (currentPage > totalPage) {
					currentPage = totalPage;
				}
				// Lấy về vị trí lấy data (offset) thông qua trang
				// hiện tại và số lượng bản ghi hiển thị trên 1 trang
				int offset = Common.getOffset(currentPage, limit);

				// Lấy về danh sách user nếu total user > 0
				listUser = userLogic.getListUsers(offset, limit, groupId, fullName, sortType, sortByFullName,
						sortByCodeLevel, sortByEndDate);
				// Lấy về Danh sách các trang cần hiển thị ở chuỗi paging
				// theo trang hiện tại
				listPaging = Common.getListPaging(totalUsers, limit, currentPage);
				// Lấy về trang đầu tiên được hiển thị
				int firstPage = Common.getFirstPage(listPaging);
				// Lấy về trang cuối cùng được hiển thị
				int lastPage = Common.getLastPage(listPaging);

				// Set attribute lên request để lấy dữ liệu cho chức
				// năng search, sort, paging
				request.setAttribute(Constant.ATB_LIST_USER, listUser);
				request.setAttribute(Constant.ATB_TOTAL_PAGE, totalPage);
				request.setAttribute(Constant.FIRST_PAGE, firstPage);
				request.setAttribute(Constant.LAST_PAGE, lastPage);
				request.setAttribute(Constant.LIMIT_PAGE, limitPage);
				request.setAttribute(Constant.ATB_LIST_PAGING, listPaging);
				// Nếu không tìm thấy user nào, gửi thông báo không
				// tìm thấy user lên request
			} else {
				request.setAttribute(Constant.ATB_NO_USER_MESS, MessageProperties.getMessage(Constant.MSG005));
			}

			// Lưu danh sách group lên request để hiển thị lên ADM002
			request.setAttribute(Constant.ATB_LIST_GROUP, listGroup);

			// Set attribute lên session để lấy dữ liệu cho chức
			// năng back về màn hình ADM002 từ ADM003, ADM005
			session.setAttribute(Constant.ATB_NAME, fullName);
			session.setAttribute(Constant.ATB_GROUP_ID_SELECTED, groupId);
			session.setAttribute(Constant.ATB_SORT_TYPE, sortType);
			session.setAttribute(Constant.SORT_FULLNAME, sortByFullName);
			session.setAttribute(Constant.SORT_LEVEL, sortByCodeLevel);
			session.setAttribute(Constant.SORT_DATE, sortByEndDate);
			session.setAttribute(Constant.ATB_ORDER, order);
			session.setAttribute(Constant.CURRENT_PAGE, currentPage);

			// Hiển thị lên màn hình ADM002
			RequestDispatcher dispatcher = request.getRequestDispatcher(Constant.JSP_ADM002);
			dispatcher.forward(request, response);

			// Nếu có lỗi, in ra và di chuyển tới màn hình System Error
		} catch (Exception e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + " " + e.getMessage());
			e.printStackTrace();
			response.sendRedirect(Constant.SYSTEM_ERROR_URL);
		}
	}

}
