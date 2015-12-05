package il.ac.hit.couponsproject.model.dto;

import il.ac.hit.couponsproject.exception.CouponException;
import il.ac.hit.couponsproject.model.dao.ICouponsDAO;
import il.ac.hit.couponsproject.model.dao.impl.HibernateCouponsDAO;

import java.util.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/** 
 * Class that describe a shopping cart
 */
public class ShoppingCart
{
	Map lines = new Hashtable();
	int size = 0;
	
	/** 
     * Adding new coupon to the shopping cart
     * @param coupon specific coupon
     */
	public void addCoupon(Coupon coupon)
	{
		if(lines.get(coupon)==null)
		{
			lines.put(coupon.getId(), new ShoppingCartLine(coupon));
			size++;
		}
		else
		{
			ShoppingCartLine line = (ShoppingCartLine)(lines.get(coupon));
		}
	}
	
	/**
	 * Return the shopping cart items amount
	 * @return size
	 */
	public int getSize()
	{
		return size;
	}
	
	/** 
     * Remove a coupon from the shopping cart
     * @param coupon the coupon to remove
     */
	public void removeCoupon(Coupon coupon)
	{
		lines.remove(coupon.getId());
		size--;
	}
	
	
	/** 
     * Creating the XML table that show all the coupons that in the cart
     * @return a String that contains the XML data.
	 * @throws ParseException 
     */
	public String getXMLTable() throws ParseException
	{
		/*
		 * Load all the coupons in the cart and create a XML table with the coupons.
		 */
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		DateFormat couponDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = new Date();
		Date theCouponDate;
		StringBuffer sb = new StringBuffer();
		Coupon currentCoupon = null;
		Iterator iterator = lines.values().iterator();
		sb.append("<table class='table table-bordered table-striped' border='fixed'><thead><tr><th>תמונה</th><th>קוד קופון</th><th>שם קופון</th><th>תיאור</th><th>מחיר לפני הנחה</th><th>אחוז הנחה</th><th>מחיר חדש</th><th>תאריך תפוגה</th><th>מיקום</th><th>קטגוריה</th><th>הסר מהעגלה</th></tr></thead><tbody>");

		while(iterator.hasNext())
		{
			ShoppingCartLine line = (ShoppingCartLine)iterator.next();
			currentCoupon = line.getCoupon();
			theCouponDate = couponDate.parse(currentCoupon.getExpiredate());
			if(theCouponDate.compareTo(date) > 0)
			{
				sb.append("<td><img src="+currentCoupon.getImage()+" height=50 width=50 /></td>");
				sb.append("<td>"+currentCoupon.getId()+"</td>");
				sb.append("<td>"+currentCoupon.getName()+"</td>");
				sb.append("<td>"+currentCoupon.getDescription()+"</td>");
				sb.append("<td>"+currentCoupon.getPrice()+"</td>");
				sb.append("<td>"+currentCoupon.getDiscount()+"</td>");
				sb.append("<td>"+currentCoupon.getNewprice()+"</td>");
				sb.append("<td>"+currentCoupon.getExpiredate()+"</td>");
				sb.append("<td>"+currentCoupon.getLocation()+"</td>");
				sb.append("<td>"+currentCoupon.getCategory()+"</td>");
				sb.append("<td><a href=removeFromCart?id=" + currentCoupon.getId() +" class='btn btn-primary' href='delete-coupons' role='button'>הסר</a></td></tr>");
			}
		}
		sb.append("</tbody></table>");
		return sb.toString();
	}
}