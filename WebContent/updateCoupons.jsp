<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"
	import="il.ac.hit.couponsproject.model.dto.Coupon"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>עדכון קופונים</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- <script src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.1.47/jquery.form-validator.min.js"></script> -->

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




<script
	src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
<script>
	var map;
	function initialize()
	{
		var mapOptions =
		{
			zoom : 15,
			center : new google.maps.LatLng(32.016106, 34.773102)
		};
		map = new google.maps.Map(document.getElementById('map-canvas'),
				mapOptions);
	}

	google.maps.event.addDomListener(window, 'load', initialize);
</script>



</head>
<body dir='rtl'>
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
     * Load the coupons and let the admin select one coupon and update his details
 	 */
		List<Coupon> couponsList = (List<Coupon>) request.getAttribute("CouponsList");
		if (couponsList.size() != 0)
		{
	%>
	<div id="dialog" title="עדכון קופון">
		<form action="update-coupons" method="POST" id="update-coupons"">
			<div class="form-group">
				<label for="couponSelection">בחר קופון</label> <select
					id="couponSelection" name="couponSelection"
					class="form-control couponSelection"
					style="border-color: blue; border-width: 2px;">
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


			<div class="form-group">
				<span class="error"
					style="color : ${textColor}; text-align: center; display: block;  float: center; font-size: 12pt;">${couponUpdatedFlag}</span>
			</div>
			<div class="form-group">

				<label for="coupon-name">שם קופון</label> <input type="text"
					class="form-control" id="coupon-name" name="coupon-name"
					placeholder="הכנס שם קופון"
					value="<%if(request.getAttribute("couponNameBackup") != null)%><%=request.getAttribute("couponNameBackup")%>">
				<span class="error" style="color: red">${errors.couponName}</span>
			</div>
			<div class="form-group">
				<label for="coupon-description">תאור קופון</label> <input
					type="text" class="form-control" id="coupon-description"
					name="coupon-description" placeholder="הכנס תאור קופון"
					value="<%if(request.getAttribute("couponDescriptionBackup") != null)%><%=request.getAttribute("couponDescriptionBackup")%>">
				<span class="error" style="color: red">${errors.couponDescription}</span>
			</div>
			<div class="form-group">
				<label for="coupon-expiredate">תאריך תפוגה</label> <input
					type="datetime-local" class="form-control" id="coupon-expiredate"
					name="coupon-expiredate"
					value="<%if(request.getAttribute("couponExpireDateBackup") != null)%><%=request.getAttribute("couponExpireDateBackup")%>">
				<span class="error" style="color: red">${errors.couponExpireDate}</span>
			</div>
			<div class="form-group">
				<label for="coupon-price">מחיר מקורי</label> <input type="text"
					class="form-control" id="coupon-price" name="coupon-price"
					placeholder="הכנס מחיר מקורי"
					value="<%if(request.getAttribute("couponPriceBackup") != null)%><%=request.getAttribute("couponPriceBackup")%>">
				<span class="error" style="color: red">${errors.couponPrice}</span>
			</div>
			<div class="form-group">
				<label for="coupon-discount">אחוז הנחה</label> <input type="text"
					class="form-control" id="coupon-discount" name="coupon-discount"
					placeholder="הכנס אחוז הנחה"
					value="<%if(request.getAttribute("couponDiscountBackup") != null)%><%=request.getAttribute("couponDiscountBackup")%>">
				<span class="error" style="color: red">${errors.couponDiscount}</span>
			</div>

			<div class="form-group">
				<label for="coupon-image">תמונה</label> <input type="text"
					class="form-control" id="coupon-image" name="coupon-image"
					placeholder="הכנס כתובת תמונה"
					value="<%if(request.getAttribute("couponImageBackup") != null)%><%=request.getAttribute("couponImageBackup")%>">
				<span class="error" style="color: red">${errors.couponImage}</span>
			</div>
			<div class="form-group">
				<label for="gmaps-input-address">מיקום</label> <input type="text"
					class="form-control" id="gmaps-input-address"
					name="gmaps-input-address" placeholder="הכנס מיקום"
					value="<%if(request.getAttribute("couponLocationBackup") != null)%><%=request.getAttribute("couponLocationBackup")%>">
				<input type="hidden" class="form-control" id="gmaps-output-latitude"
					name="gmaps-output-latitude"
					value="<%if(request.getAttribute("latitudeBackup") != null)%><%=request.getAttribute("latitudeBackup")%>">
				<input type="hidden" class="form-control"
					id="gmaps-output-longitude" name="gmaps-output-longitude"
					value="<%if(request.getAttribute("longitudeBackup") != null)%><%=request.getAttribute("longitudeBackup")%>">
				<span class="error" style="color: red">${errors.couponLocation}</span>
			</div>
			<div id='gmaps-canvas' style="display: none;"></div>
			<input type="hidden" value=${couponAddedFlag } name="couponError"
				id="couponError">
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
		$(document).ready(
				function()
				{

					$("p").click(function()
					{
						$(this).fadeOut();
					});
					$("#gmaps-input-address , #add-coupon").blur(function()
					{
						$("#aaa").val($("#addcoupon span").val())
					});
					$("#coupon-discount , #add-coupon").blur(
							function()
							{
								$("#coupon-newprice").val(
										$("#coupon-price").val()
												- $("#coupon-price").val()
												* $("#coupon-discount").val()
												/ 100);
							});

				});

		$(function()
		{
			var buttons = $(".selector").dialog("option", "buttons");
			$("#dialog").dialog(
			{
				hide : 'fade',
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
					text : "עדכן קופון",
					type : "submit",
					form : "update-coupons",
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