<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"
	import="il.ac.hit.couponsproject.model.dto.Coupon"
	import = "java.util.Date"
	import = "java.text.DateFormat"
	import = "java.text.SimpleDateFormat"
	import = "java.util.Calendar"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html dir="rtl">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="refresh" content="30">
<title>צפייה בקופונים</title>

<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/design.css"
	rel="stylesheet" type="text/css" />
</head>
<body>
<%
	/**
     * Check if the user is admin and if so he get different redirections
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
 	/**
     * Load the coupons into a table
 	 */
	
		List<Coupon> couponsList = (List<Coupon>) request.getAttribute("coupons");
	%>

	<%
		if (couponsList != null)
		{
	%>

	
	
	
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
				<%if(adminFlag == false)
				{%>
					<th>הוסף לעגלה</th>
				<%} %>

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
				<%if(adminFlag == false && (theCouponDate.compareTo(date) > 0))
						{
				%>
				<td><a href="addToCart?id=<%=cop.getId()%>"class="btn btn-primary" href="addToCart" role="button">הוסף</a></td>
				<%
					}
					else
					{ %>
						<td><a disabled="disable" href="addToCart?id=<%=cop.getId()%>"class="btn btn-primary" href="addToCart" role="button">הוסף</a></td>
					<%}%>
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
	<%="No Coupons"%>
	<%
		}
	%>
			<%if(adminFlag == true)
					{%>
				<a class="btn btn-primary" href="admin-page" role="button">מסך ראשי</a>
				<%}
		else{%>
		<a class="btn btn-primary" href="main-page" role="button">מסך ראשי</a>
		<%} %>
	<script	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>