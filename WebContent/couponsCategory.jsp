<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"
	import="il.ac.hit.couponsproject.model.dto.Coupon"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html dir="rtl">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>צפייה בקופונים</title>

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
	<%
 	/**
     * Load the categories from the Controller
 	 */
		HashSet<String> categories = (HashSet<String>) request.getAttribute("categories");
	%>
	<div align="center">
		<label for="categorySelection">בחר קטגוריה</label> <select
			id="categorySelection">
			<%
				for (String category : categories)
									{
			%>
			<option class="form-control" id="category-name" name="category-name"
				value=<%=category%>><%=category%></option>
			<%
				}
			%>
		</select>
		<button class="btn btn-primary" onclick="forward()">קטגוריה</button>
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
		function forward()
		{
			var select = document.getElementById("categorySelection");
			var selectedCategory = select.options[select.selectedIndex].value;
			window.location = "/CouponsProject/Controller/coupon-cat?category="
					+ selectedCategory + "&latitude=" + latitude
					+ "&longitude=" + longitude;
		}
	</script>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>