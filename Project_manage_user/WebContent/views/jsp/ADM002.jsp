<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="views/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="views/js/user.js"></script>
<script type="text/javascript" src="views/js/ADM002.js"></script>
<title>ユーザ管理</title>
</head>
<body>
	<!-- Begin vung header -->
	<jsp:include page="header.jsp" />

	<!-- End vung header -->

	<!-- Begin vung dieu kien tim kiem -->
	<form action="ListUser.do" method="get" name="mainform">
		<input type="hidden" name="action" value="search"></input>
		<table class="tbl_input" border="0" width="90%" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>会員名称で会員を検索します。検索条件無しの場合は全て表示されます。</td>
			</tr>
			<c:if test="${not empty userNotExist }">
				<tr>
					<td>${userNotExist }</td>
				</tr>
			</c:if>
			<tr>
				<td width="100%">
					<table class="tbl_input" cellpadding="4" cellspacing="0">
						<tr>
							<td class="lbl_left">氏名:</td>
							<td align="left"><input class="txBox" type="text"
								autofocus="autofocus" name="fullname"
								value="${fn:escapeXml(fullname)}" size="20"
								onfocus="this.style.borderColor='#0066ff';"
								onblur="this.style.borderColor='#aaaaaa';" /></td>
							<td></td>
						</tr>
						<tr>
							<td class="lbl_left">グループ:</td>
							<td align="left" width="80px"><select name="groupId"
								id="group">
									<option value="0">全て</option>
									<c:forEach items="${listGroup}" var="group">
										<option value="${group.groupId}"
											${group.groupId == selectedId ? 'selected' : ''}>${fn:escapeXml(group.groupName)}</option>
									</c:forEach>
							</select></td>
							<td align="left"><input class="btn" type="submit" value="検索" />
								<input class="btn" type="button" value="新規追加"
								onclick="location.href = 'AddUserInput.do'" /></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<!-- End vung dieu kien tim kiem -->
	</form>
	<!-- Begin vung hien thi danh sach user -->

	<c:if test="${empty userNotFound}">
		<table class="tbl_list" border="1" cellpadding="4" cellspacing="0"
			width="80%">

			<c:url var="sortUrl" value="ListUser.do">
				<c:param name="action" value="sort"></c:param>
				<c:param name="fullname" value="${fullname}"></c:param>
				<c:param name="groupId" value="${selectedId}"></c:param>
				<c:param name="currentPage" value="${currentPage}"></c:param>
			</c:url>

			<tr class="tr2">
				<th align="center" width="20px">ID</th>
				<th align="left">氏名 <a
					href="${sortUrl }&sortType=sortByName&order=${sortByName == 'DESC' ? 'ASC': 'DESC'}">
					<c:out value="${sortByName == 'DESC' ? '△▼'  : '▲▽' }"></c:out>
				</a>
				</th>
				<th align="left">生年月日</th>
				<th align="left">グループ</th>
				<th align="left">メールアドレス</th>
				<th align="left" width="70px">電話番号</th>
				<th align="left">日本語能力 <a
					href="${sortUrl }&sortType=sortByLevel&order=${sortByLevel == 'DESC' ? 'ASC': 'DESC'}">
					<c:out value="${sortByLevel == 'DESC' ? '△▼'  : '▲▽' }"></c:out>
				</a>

				</th>
				<th align="left">失効日 <a
					href="${sortUrl }&sortType=sortByDate&order=${sortByDate == 'DESC' ? 'ASC': 'DESC'}">
					<c:out value="${sortByDate == 'DESC' ? '△▼'  : '▲▽' }"></c:out>
				</a>
				</th>
				<th align="left">点数</th>
			</tr>

			<c:forEach items="${listUser}" var="user">
				<tr>
					<td align="right"><a href="ShowUserInfo.do?userId=${user.userId}">${user.userId}</a></td>
					<td><c:choose>
							<c:when test="${fn:length(user.fullName) <= 20 }">
								<c:out value="${user.fullName}" />
							</c:when>
							<c:otherwise>
								<c:out value="${fn:substring(user.fullName, 0, 17)}..." />
							</c:otherwise>
						</c:choose></td>
					<td align="center">${fn:replace(user.birthday, '-','/')}</td>
					<td><c:choose>
							<c:when test="${fn:length(user.groupName) <= 20 }">${user.groupName}</c:when>
							<c:otherwise>${fn:escapeXml(fn:substring(user.groupName, 0, 17))}...</c:otherwise>
						</c:choose></td>
					<td><c:choose>
							<c:when test="${fn:length(user.email) <= 20 }">${fn:escapeXml(user.email)}</c:when>
							<c:otherwise>${fn:escapeXml(fn:substring(user.email, 0, 17))}...</c:otherwise>
						</c:choose></td>
					<td>${user.tel}</td>
					<td>${user.nameLevel}</td>
					<td align="center">${fn:replace(user.endDate, '-', '/')}</td>
					<td align="right"><c:if test="${not empty user.nameLevel}">
							${user.total}
						</c:if></td>
				</tr>
			</c:forEach>



		</table>
	</c:if>
	${userNotFound}
	<!-- End vung hien thi danh sach user -->

	<!-- Begin vung paging -->

	<c:if test="${not empty listPaging}">

		<c:url var="pagingUrl" value="ListUser.do">
			<c:param name="action" value="paging"></c:param>
			<c:param name="fullname" value="${fullname}"></c:param>
			<c:param name="groupId" value="${selectedId}"></c:param>
			<c:param name="sortType" value="${sortType}"></c:param>
			<c:param name="order" value="${order}"></c:param>
		</c:url>

		<table>
			<tr>
				<td class="lbl_paging"><c:if test="${firstpage > limitPage}">
						<a href="${pagingUrl}&currentPage=${firstpage - limitPage}"><<</a> &nbsp;
			</c:if> <c:if test="${fn:length(listPaging) > 1 || firstpage > 1}">
						<c:forEach items="${listPaging}" var="page">
							<c:choose>
								<c:when test="${page == currentPage }">${page}</c:when>
								<c:otherwise>
									<a href="${pagingUrl }&currentPage=${page}">${page}</a>
								</c:otherwise>
							</c:choose> &nbsp;
				</c:forEach>
					</c:if> <c:if test="${lastpage < totalPage}">
						<a href="${pagingUrl }&currentPage=${lastpage + 1}">>></a>
					</c:if>
		</table>

	</c:if>
	<!-- End vung paging -->

	<!-- Begin vung footer -->
	<jsp:include page="footer.jsp" />
	<!-- End vung footer -->

</body>

</html>