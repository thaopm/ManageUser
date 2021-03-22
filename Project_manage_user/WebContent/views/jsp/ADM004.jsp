<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
	<jsp:include page="/views/jsp/header.jsp" />

	<!-- End vung header -->

	<!-- Begin vung input-->
	<form action="${user.userId==0 ? 'AddUserOK.do' : 'EditUserOK.do'}"
		method="post" name="inputform">
		<input type="hidden" name="key" value="${key }"></input>
		<table class="tbl_input" border="0" width="75%" cellpadding="0"
			cellspacing="0">
			<tr>
				<th align="left">
					<div style="padding-left: 100px;">
						情報確認<br> </br>入力された情報をＯＫボタンクリックでＤＢへ保存してください
					</div>
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
								<td align="left" style="word-break: break-all;">${fn:escapeXml(user.groupName )}</td>
							</tr>
							<tr>
								<td class="lbl_left">氏名:</td>
								<td align="left" style="word-break: break-all;">${fn:escapeXml(user.fullName )}</td>
							</tr>
							<tr>
								<td class="lbl_left">カタカナ氏名:</td>
								<td align="left" style="word-break: break-all;">${fn:escapeXml(user.fullNameKana) }</td>
							</tr>
							<tr>
								<td class="lbl_left">生年月日:</td>
								<td align="left">${fn:replace(user.birthday, '-','/')}</td>
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
								<th colspan="2"><a href="#"
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
								<td align="left"><c:if test="${not empty user.codeLevel}"> ${fn:replace(user.startDate, '-', '/')}</c:if></td>
							</tr>
							<tr>
								<td class="lbl_left">失効日:</td>
								<td align="left"><c:if test="${not empty user.codeLevel}"> ${fn:replace(user.endDate, '-', '/')}</c:if></td>
							</tr>
							<tr>
								<td class="lbl_left">点数:</td>
								<td align="left"><c:if test="${not empty user.codeLevel}">${fn:escapeXml(user.total) }</c:if></td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
		<div style="padding-left: 100px;">&nbsp;</div>
		<!-- Begin vung button -->
		<div style="padding-left: 45px;">
			<table border="0" cellpadding="4" cellspacing="0" width="300px">
				<tr>
					<th width="200px" align="center">&nbsp;</th>
					<td><input class="btn" type="submit" value="OK" /></td>
					<td><input class="btn" type="button" value="戻る"
						onclick="location.href = 
						'${user.userId==0 ? 'AddUserInput.do' : 'EditUserInput.do'}?action=back&key=${key}'" /></td>
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