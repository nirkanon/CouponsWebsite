<%@ page language="java" contentType="text/html; charset=windows-1255"
	pageEncoding="windows-1255"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html dir="rtl">
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1255">
<title>מסך ראשי</title>

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
	<div align="center">
		<a class="btn btn-primary" href="get-coupons" role="button"
			onclick="location.href=this.href+'?uLatitude='+latitude+'&uLongitude='+longitude;return false;">צפייה
			בקופונים</a> <a class="btn btn-primary" href="shopping-cart"
			role="button">עגלה</a> <a class="btn btn-success" href="login-page"
			role="button" on>התחבר כמנהל</a>
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