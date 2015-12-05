<%@ page language="java" contentType="text/html; charset=windows-1255"
	pageEncoding="windows-1255"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html dir="rtl">
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1255">
<title>מסך מנהל</title>

<link href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/jquery-ui.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/jquery-ui.theme.min.css"
	type="text/css" />

<script src="${pageContext.request.contextPath}/js/jquery-2.1.3.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>



</head>
<body>
	<p id="demo"></p>
	<%
 	/**
     * Check for admin cookie 
 	 */
		String cookieName = "loggedIn";
		Cookie cookies[] = request.getCookies();
		Cookie myCookie = null;
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(cookieName)) {
					myCookie = cookies[i];
					if (myCookie.getValue().equals("false")) {
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
		if (myCookie == null) {
	%>
	<script>
		window.location = "/CouponsProject/Controller/login-page";
	</script>
	<%
		}
	%>
	<div align="center">
		<a class="btn btn-primary" href="add-coupon" role="button">הוספת
			קופונים</a> <a class="btn btn-primary" href="delete-coupons"
			role="button">מחיקת קופונים</a> <a class="btn btn-primary"
			href="get-coupons" role="button"
			onclick="location.href=this.href+'?uLatitude='+latitude+'&uLongitude='+longitude;return false;">צפייה
			בקופונים</a> <a class="btn btn-primary" href="update-coupons"
			role="button">עדכון קופון</a> <a class="btn btn-danger" href="logout"
			role="button">התנתקות</a>
	</div>
	<script>
		var latitude, longitude;
		$(document).ready(
				function()
				{
					if (navigator.geolocation)
					{
						navigator.geolocation.getCurrentPosition(
								handle_geolocation_query, handle_errors);
					} else
					{
						alert('Device probably not ready.');
					}
				});
		function handle_errors(error)
		{
			// error handling here
		}
		function handle_geolocation_query(position)
		{
			latitude = (position.coords.latitude);
			longitude = (position.coords.longitude);
		}
	</script>
</body>
</html>