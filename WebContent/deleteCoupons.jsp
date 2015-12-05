<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"
	import="il.ac.hit.couponsproject.model.dto.Coupon"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1255">
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
<script type="text/javascript"
	src="http://maps.google.com/maps/api/js?sensor=false"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/gmaps.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/validator.js"></script>
<title>מחיקת קופונים</title>
</head>
<body dir="rtl">
	<%
 	/**
     * Check for admin cookie 
 	 */
		String cookieName = "loggedIn";
			Cookie cookies[] = request.getCookies();
			Cookie myCookie = null;
			if (cookies != null)
			{
		for (int i = 0; i < cookies.length; i++)
		{
			if (cookies[i].getName().equals(cookieName))
			{
				myCookie = cookies[i];
				if(myCookie.getValue().equals("false"))
				{
	%>
	<script>
		window.location = "/CouponsProject/Controller/login-page";
	</script>
	<%
		}
				break;
			}
		}
			}
			if (myCookie == null)
			{
	%>
	<script>
		window.location = "/CouponsProject/Controller/login-page";
	</script>
	<%
		}
	%>
	<%
 	/**
     * Load the coupons into a HTML select and store the ID as value
     * Pressing the submit will delte a coupon
 	 */
		List<Coupon> couponsList = (List<Coupon>) request.getAttribute("CouponsList");
			if (couponsList.size() != 0)
			{
	%>
	<div id="dialog" title="מחיקת קופון">
		<form data-toggle="validator" action="delete-coupons" method="POST"
			id="delete-coupons"">
			<div class="form-group" align="center">
				<label for="coupon-name">שם קופון</label> <select
					name="couponSelection" class="form-control couponSelection">
					<%
						for (Coupon cop : couponsList)
											{
					%>
					<option class="form-control" id="coupon-name" name="coupon-name"
						value=<%=cop.getId()%>><%=cop.getName()%></option>
					<%
						}
					%>
				</select>
			</div>
			<span class="error"
				style="color : ${textColor}; text-align: center; display: block;  float: center; font-size: 12pt; font-weight: bold;">${couponDeletedFlag}</span>
		</form>
	</div>

	<%
		} else
			{
	%>
	<h2>אין קופונים במערכת</h2>
	<a class="btn btn-primary" href="admin-page" role="button">מסך ראשי</a>
	<%
		}
	%>

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
					location.href = '/CouponsProject/Controller/admin-page';
				}
			},
			{
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
					text : "מחק קופון",
					type : "submit",
					form : "delete-coupons",
					icons :
					{
						primary : "ui-icon-check"

					}
				},
				{
					text : "מסך ראשי",
					type : "back",
					href : "admin-page",
					icons :
					{
						primary : "ui-icon-back"
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