<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"
	import="il.ac.hit.couponsproject.model.dto.Coupon"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1255">
<title>מסך התחברות</title>
<link href="${pageContext.request.contextPath}/css/main.css"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/jquery-ui.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/jquery-ui.theme.min.css"
	type="text/css" />
<link href="${pageContext.request.contextPath}/css/design.css"
	rel="stylesheet" type="text/css" />

<script src="${pageContext.request.contextPath}/js/jquery-2.1.3.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>
</head>
<body dir="rtl">
	<div id="dialog" title="התחברות">
		<form action="login-page" method="POST" id="login-page">
			<div class="form-group">
				<label for="username">שם משתמש</label> <input type="text"
					class="form-control" id="username" name="username"
					placeholder="הכנס שם משתמש" />
			</div>
			<div class="form-group">
				<label for="password">סיסמה</label> <input type="password"
					class="form-control" id="password" name="password"
					placeholder="הכנס סיסמה" />
			</div>
			<div class="form-group" align="center">
				<span class="error" style="color: red">${incorrect}</span>
			</div>
		</form>
	</div>


	<script>
		$(function()
		{
			var buttons = $(".selector").dialog("option", "buttons");
			$("#dialog").dialog(
			{
				hide : 'fade'
			},
			{
				resizable : false,
				modal : true,
				draggable : false,
				width : '330',
				close : function()
				{
					location.href = '/CouponsProject/Controller/main-page';
				},
				show :
				{
					effect : 'fade'
				},
				durtion : 1000
			});
			var form = $("#dialog").dialog(
			{
				buttons : [
				{
					text : "התחבר",
					type : "submit",
					form : "login-page",
					name : "submit",
					icons :
					{
						primary : "ui-icon-check"
					}
				},
				{
					text : "מסך ראשי",
					type : "back",
					href : "main-page",
					icons :
					{
						primary : "ui-icon-arrowthick-1-w"
					},
					click : function()
					{
						$(this).dialog("close");
					}

				} ]
			});
		});
	</script>
</body>
</html>