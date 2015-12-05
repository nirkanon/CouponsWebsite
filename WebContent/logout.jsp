<%@ page language="java" contentType="text/html; charset=windows-1255"
	pageEncoding="windows-1255"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1255">
<title>Insert title here</title>
</head>
<body>
	<%
 	/**
     * Set the 'loggedIn' cookie value as 'false' to set that the admin has logged out 
 	 */
		String cookieName = "loggedIn";
		Cookie cookies[] = request.getCookies();
		Cookie myCookie = null;
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(cookieName)) {
					myCookie = cookies[i];
					myCookie.setValue("false");
					response.addCookie(myCookie);

					break;
				}
			}
		}
	%>
	<script>
		window.location = "/CouponsProject/Controller/login-page";
	</script>
</body>
</html>