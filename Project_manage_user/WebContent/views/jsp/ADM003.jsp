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
<title>ユーザ管理</title>
</head>
<body>
	<!-- Begin vung header -->
	<jsp:include page="header.jsp" />
	<!-- End vung header -->

	<!-- Begin vung input-->
	<form
		action="${user.userId == 0 ? 'AddUserValidate.do' : 'EditUserValidate.do' }"
		method="post" name="inputform">
		<input type="hidden" name="action" value="confirm"></input> <input
			type="hidden" name="userId" value="${user.userId }"></input>
		<table class="tbl_input" border="0" width="75%" cellpadding="0"
			cellspacing="0">
			<tr>
				<th align="left">
					<div style="padding-left: 100px;">会員情報編集</div>
				</th>
			</tr>
			<tr>
				<td class="errMsg">
					<div style="padding-left: 120px">
						<c:choose>
							<c:when test="${listError == null || listError.size() == 0}">
						&nbsp;</c:when>
							<c:otherwise>
								<c:forEach items="${listError}" var="errMsg">
									<p style="color: red">${errMsg}</p>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</div>
				</td>
			</tr>
			<tr>
				<td align="left">
					<div style="padding-left: 100px;">
						<table border="0" width="100%" class="tbl_input" cellpadding="4"
							cellspacing="0">
							<tr>
								<td class="lbl_left"><font color="red">*</font> アカウント名:</td>
								<td align="left"><input class="txBox" type="text"
									<c:if test="${user.userId == 0 }">autofocus="autofocus"</c:if>
									name="loginName" value="${fn:escapeXml(user.loginName)}"
									size="15" onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';"
									<c:if test="${user.userId != 0}">readonly</c:if> /></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> グループ:</td>
								<td align="left"><select name="groupId">
										<option value="0">選択してくださ</option>
										<c:forEach items="${listGroup}" var="mstGroup">
											<option value="${mstGroup.groupId}"
												${mstGroup.groupId == user.groupId ? 'selected' : ''}>${mstGroup.groupName}</option>
										</c:forEach>
								</select> <span>&nbsp;&nbsp;&nbsp;</span></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> 氏名:</td>
								<td align="left"><input class="txBox" type="text"
									style="word-break: break-all;" name="fullname"
									<c:if test="${user.userId > 0 }">autofocus="autofocus"</c:if>
									value="${fn:escapeXml(user.fullName)}" size="30"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<td class="lbl_left">カタカナ氏名:</td>
								<td align="left"><input class="txBox" type="text"
									style="word-break: break-all;" name="nameKana"
									value="${fn:escapeXml(user.fullNameKana)}" size="30"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> 生年月日:</td>
								<td align="left"><c:set var="birthday"
										value="${fn:split(user.birthday, '-')}"></c:set><select
									name="yearBirthday">
										<c:forEach items="${listYearBirthday}" var="year">
											<option value="${year}"
												${year == birthday[0] ? 'selected' : ''}>${year}</option>
										</c:forEach>
								</select>年 <select name="monthBirthday">
										<c:forEach items="${listMonth}" var="month">
											<option value="${month}"
												${month == birthday[1] ? 'selected' : ''}>${month}</option>
										</c:forEach>
								</select>月 <select name="dayBirthday">
										<c:forEach items="${listDay}" var="day">
											<option value="${day}"
												${day == birthday[2] ? 'selected' : ''}>${day}</option>
										</c:forEach>
								</select>日</td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> メールアドレス:</td>
								<td align="left"><input class="txBox" type="text"
									style="word-break: break-all;" name="email"
									value="${fn:escapeXml(user.email)}" size="30"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font>電話番号:</td>
								<td align="left"><input class="txBox" type="text"
									name="tel" value="${fn:escapeXml(user.tel)}" size="30"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<c:if test="${user.userId == 0 }">
								<tr>
									<td class="lbl_left"><font color="red">*</font> パスワード:</td>
									<td align="left"><input class="txBox" type="password"
										name="password" value="" size="30"
										onfocus="this.style.borderColor='#0066ff';"
										onblur="this.style.borderColor='#aaaaaa';" /></td>
								</tr>
								<tr>
									<td class="lbl_left">パスワード（確認）:</td>
									<td align="left"><input class="txBox" type="password"
										name="repassword" value="" size="30"
										onfocus="this.style.borderColor='#0066ff';"
										onblur="this.style.borderColor='#aaaaaa';" /></td>
								</tr>
							</c:if>
							<tr>
								<th align="left" colspan="2"><a href="#"
									onclick="displayJapaneseLevel()">日本語能力</a></th>
							</tr>
						</table>
					</div>
					<div id="level" style="padding-left: 100px; display: none">
						<table border="0" width="100%" class="tbl_input" cellpadding="4"
							cellspacing="1">
							<tr>
								<td class="lbl_left">資格:</td>
								<td align="left"><select name="codeLevel">
										<option value="">選択してください</option>
										<c:forEach items="${listJapan}" var="mstJapan">
											<option value="${mstJapan.codeLevel}"
												${mstJapan.codeLevel == user.codeLevel ? 'selected' : ''}>${mstJapan.nameLevel}</option>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td class="lbl_left">資格交付日:</td>
								<td align="left"><c:set var="startDate"
										value="${fn:split(user.startDate, '-')}"></c:set> <select
									name="yearStartDate">
										<c:forEach items="${listYearStartDate}" var="year">
											<option value="${year}"
												${year == startDate[0] ? 'selected' : ''}>${year}</option>
										</c:forEach>
								</select>年 <select name="monthStartDate">
										<c:forEach items="${listMonth}" var="month">
											<option value="${month}"
												${month == startDate[1] ? 'selected' : ''}>${month}</option>
										</c:forEach>
								</select>月 <select name="dayStartDate">
										<c:forEach items="${listDay}" var="day">
											<option value="${day}"
												${day == startDate[2] ? 'selected' : ''}>${day}</option>
										</c:forEach>
								</select>日</td>
							</tr>
							<tr>
								<td class="lbl_left">失効日:</td>
								<td align="left"><c:set var="endDate"
										value="${fn:split(user.endDate, '-')}"></c:set><select
									name="yearEndDate">
										<c:forEach items="${listYearEndDate}" var="year">
											<option value="${year}"
												${year == endDate[0] ? 'selected' : ''}>${year}</option>
										</c:forEach>
								</select>年 <select name="monthEndDate">
										<c:forEach items="${listMonth}" var="month">
											<option value="${month}"
												${month == endDate[1] ? 'selected' : ''}>${month}</option>
										</c:forEach>
								</select>月 <select name="dayEndDate">
										<c:forEach items="${listDay}" var="day">
											<option value="${day}" ${day == endDate[2] ? 'selected' : ''}>${day}</option>
										</c:forEach>
								</select>日</td>
							</tr>
							<tr>
								<td class="lbl_left">点数:</td>
								<td align="left"><input class="txBox" type="text"
									name="total"
									<c:if test="${not empty user.codeLevel}"> value="${fn:escapeXml(user.total)}" </c:if>
									size="5" onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
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
					<td><input class="btn" type="submit" value="確認" /></td>

					<td><a
						href='<c:choose>
						<c:when test="${user.userId == 0 }">
							ListUser.do?action=back
						</c:when>
						<c:otherwise>
							ShowUserInfo.do?userId=${user.userId}
						</c:otherwise>
							</c:choose>'>
							<input class="btn" type="button" value="戻る" />
					</a></td>

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