<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="views/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="views/js/user.js"></script>
<title>ユーザ管理</title>
</head>
<body>
	<!-- Begin vung header -->
	<jsp:include page="header.jsp" />

	<!-- End vung header -->
	<fmt:setBundle basename="message_ja" />

	<!-- Begin vung input-->
	<form action="EditUserInput.do" method="get" name="inputform">
		<input type="hidden" name="action" value="edit"></input> <input
			type="hidden" name="userId" value="${user.userId}"></input>

		<table class="tbl_input" border="0" width="75%" cellpadding="0"
			cellspacing="0">
			<tr>
				<th align="left">
					<div style="padding-left: 100px;">情報確認</div>
					<div style="padding-left: 100px;">&nbsp;</div>
				</th>
			</tr>
			<tr>
				<td align="left">
					<div style="padding-left: 100px;">
						<table border="1" width="70%" class="tbl_input" cellpadding="4"
							cellspacing="0">
							<tr>
								<td class="lbl_left">アカウント名:</td>
								<td align="left">${fn:escapeXml(user.loginName)}</td>
							</tr>
							<tr>
								<td class="lbl_left">グループ:</td>
								<td align="left" style="word-break: break-all;">${fn:escapeXml(user.groupName) }</td>
							</tr>
							<tr>
								<td class="lbl_left">氏名:</td>
								<td align="left" style="word-break: break-all;">${fn:escapeXml(user.fullName) }</td>
							</tr>
							<tr>
								<td class="lbl_left">カタカナ氏名:</td>
								<td align="left" style="word-break: break-all;">${fn:escapeXml(user.fullNameKana) }</td>
							</tr>
							<tr>
								<td class="lbl_left">生年月日:</td>
								<td align="left">${fn:replace(user.birthday, '-','/') }</td>
							</tr>
							<tr>
								<td class="lbl_left">メールアドレス:</td>
								<td align="left" style="word-break: break-all;">${fn:escapeXml(user.email) }</td>
							</tr>
							<tr>
								<td class="lbl_left">電話番号:</td>
								<td align="left">${fn:escapeXml(user.tel) }</td>
							</tr>
							<tr>
								<th colspan="2" align="center"><a href="#"
									onclick="displayJapaneseLevel()">日本語能力</a></th>
							</tr>
						</table>
					</div>
					<div id="level" style="padding-left: 100px; display: none">
						<table border="1" width="70%" class="tbl_input" cellpadding="4"
							cellspacing="0">
							<tr>
								<td class="lbl_left">資格:</td>
								<td align="left" style="word-break: break-all;">${fn:escapeXml(user.nameLevel) }</td>
							</tr>
							<tr>
								<td class="lbl_left">資格交付日:</td>
								<td align="left">${fn:replace(user.startDate, '-','/') }</td>
							</tr>
							<tr>
								<td class="lbl_left">失効日:</td>
								<td align="left">${fn:replace(user.endDate, '-','/') }</td>
							</tr>
							<tr>
								<td class="lbl_left">点数:</td>
								<td align="left"><c:if test="${not empty user.nameLevel}">
										${user.total}
						</c:if></td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
		<div style="padding-left: 100px;">&nbsp;</div>
		<!-- Begin vung button -->
		<div style="padding-left: 100px;">
			<table border="0" cellpadding="4" cellspacing="0" width="300px">
				<tr>
					<th width="200px" align="center">&nbsp;</th>
					<td><input class="btn" type="submit" value="編集" /></td>
					<td><input class="btn" type="button" value="削除"
						onclick="confirmDelete('<fmt:message key="MSG004"/>' ,'DeleteUser.do', ${user.userId})" /></td>
					<td><input class="btn" type="button" value="戻る"
						onclick="location.href = 'ListUser.do?action=back'" /></td>
				</tr>
			</table>
		</div>
		<!-- End vung button -->
	</form>
	<!-- End vung input -->

	<!-- Begin vung footer -->
	<jsp:include page="footer.jsp" />
	<!-- End vung footer -->
</body>

</html>