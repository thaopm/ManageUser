
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<link href="views/css/style.css" rel="stylesheet" type="text/css" />
<title>ユーザ管理</title>
</head>
<body>
	<form action="Login.do" method="post">
		<center>
			<table class="tbl_input" cellpadding="4" cellspacing="0"
				width="500px">
				<tr>
					<th width="120px">&nbsp;</th>
					<th></th>
				</tr>
				<tr>
					<th colspan="2" align="left">アカウント名およびパスワードを入力してください</th>
				</tr>
				<c:forEach items="${listError}" var="error">
					<tr>
						<th class="errMsg" colspan="2" style="color: red">${error}</th>
					</tr>
				</c:forEach>

				<tr align="left">
					<td class="lbl_left">アカウント名:</td>
					<td align="left"><input class="txBox" type="text"
						autofocus="autofocus" name="loginName"
						value="${fn:escapeXml(loginName)}" size="20"
						onfocus="this.style.borderColor='#0066ff';"
						onblur="this.style.borderColor='#aaaaaa';" /></td>
				</tr>
				<tr>
					<td class="lbl_left">パスワード:</td>
					<td align="left"><input class="txBox" type="password"
						name="password" size="22" value=""
						onfocus="this.style.borderColor='#0066ff';"
						onblur="this.style.borderColor='#aaaaaa';" /></td>
				</tr>
				<tr>
					<td></td>
					<td align="left"><input class="btn btn_wider" type="submit"
						value="ログイン" /></td>
				</tr>
			</table>
		</center>
	</form>
</body>
</html>