<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"
	import="il.ac.hit.couponsproject.model.dto.Coupon"
	import="java.util.Date" import="java.text.DateFormat"
	import="java.text.SimpleDateFormat" import="java.util.Calendar"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html dir="rtl">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="refresh" uLatitude="metaRefresh" uLongitude="metaRefresh" content="30">
<title>צפייה בקופונים</title>

<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/design.css"
	rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/js/jquery-2.1.3.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>
</head>
<body>
	<%
 	/**
     * Load all coupons and categories
 	 */
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			DateFormat couponDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date date = new Date();
			Date theCouponDate;
			String cookieName = "loggedIn";
			boolean adminFlag = false;
			Cookie cookies[] = request.getCookies();
			Cookie myCookie = null;
			if (cookies != null)
			{
		for (int i = 0; i < cookies.length; i++)
		{
			if (cookies[i].getName().equals(cookieName))
			{
				myCookie = cookies[i];
				if(myCookie.getValue().equals("true"))
				{
				 adminFlag = true;
				}
			}
		}
			}
	%>
	<%
		HashSet<String> categories = (HashSet<String>) request.getAttribute("categories");
			List<Coupon> couponsList = (List<Coupon>) request.getAttribute("CouponsList");
	%>

	<%
		if (couponsList.size() != 0)
			{
	%>

	<div align="center">
		<label for="categorySelection">בחר קטגוריה</label> <select
			id="categorySelection">
			<%
				for (String category : categories)
									{
			%>
			<option class="form-control" id="category-name" name="category-name" value=<%=category%>><%=category%></option>
			<%
				}
			%>
		</select>
		<button onclick="forward()">סנן</button>
	</div>



	<table class="table table-bordered table-striped" border="fixed">
		<thead>
			<tr>
				<th>תמונה</th>
				<th>קוד קופון</th>
				<th>שם קופון</th>
				<th>תיאור</th>
				<th>מחיר לפני הנחה</th>
				<th>אחוז הנחה</th>
				<th>מחיר חדש</th>
				<th>מיקום</th>
				<th>תאריך תפוגה</th>
				<th>קטגוריה</th>
				<%
					if(adminFlag == false)
							{
				%>
				<th>הוסף לעגלה</th>
				<%
					}
				%>

			</tr>
		</thead>
		<tbody>
			<%
				for (Coupon cop : couponsList)
				{		
					theCouponDate = couponDate.parse(cop.getExpiredate());

					if(theCouponDate.compareTo(date) < 0)
					{ %>
						<tr class="danger">
					<%}
					else
					{ %>
						<tr class="info">
					<%}%>
				<td><image src=<%=cop.getImage()%> height=100 width=100 /></td>
				<td><%=cop.getId()%></td>
				<td><%=cop.getName()%></td>
				<td><%=cop.getDescription()%></td>
				<td><%=cop.getPrice()%></td>
				<td><%=cop.getDiscount()%></td>
				<td><%=cop.getNewprice()%></td>
				<td><%=cop.getLocation()%></td>
				<td><%=cop.getExpiredate()%></td>
				<td><%=cop.getCategory()%></td>
				<%
					if(adminFlag == false && (theCouponDate.compareTo(date) > 0))
						{
				%>
				<td><a href="addToCart?id=<%=cop.getId()%>"class="btn btn-primary" href="addToCart" role="button">הוסף</a></td>
				<%
					}
					else
					{ %>
						<td><a disabled="disable" href="addToCart?id=<%=cop.getId()%>"class="btn btn-primary" href="addToCart" role="button">הוסף</a></td>
					<%}
				%>
			</tr>

			<%
				}
					
			%>
		</tbody>
	</table>
	<%
		} else
			{
	%>
	<h2>אין קופונים במערכת</h2>
	<a class="btn btn-primary" href="admin-page" role="button">מסך ראשי</a>
	<%
		}
	%>
	<%
		if(adminFlag == true)
				{
	%>
	<a class="btn btn-primary" href="admin-page" role="button">מסך ראשי</a>
	<%
		}
			else{
	%>
	<a class="btn btn-primary" href="main-page" role="button">מסך ראשי</a>
	<%
		}
	%>

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