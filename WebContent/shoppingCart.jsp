<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"
	import="il.ac.hit.couponsproject.model.dto.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html dir="rtl">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="refresh" uLatitude="metaRefresh" uLongitude="metaRefresh" content="30">
<title>ShoppingCart</title>
<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/design.css" rel="stylesheet" type="text/css" />
</head>
<body>
<%
	/**
 	 * Load the ShoppingCart (Get the XML data) for the user
	 */
	ShoppingCart cart = (ShoppingCart)(session.getAttribute("cart"));
	if(cart != null)
	{
		if(cart.getSize() != 0)
		{
			out.println(cart.getXMLTable());
			%>
			<a class="btn btn-primary" href="main-page" role="button">מסך ראשי</a>
			<%
		}
		else
		{%>
				<h2>העגלה ריקה</h2>
				<a class="btn btn-primary" href="main-page" role="button">מסך ראשי</a>
		<%}
	}
	else
	{
%>
	<h2>העגלה ריקה</h2>
	<a class="btn btn-primary" href="main-page" role="button">מסך ראשי</a>
<%} %>
	<script	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

</body>
</html>