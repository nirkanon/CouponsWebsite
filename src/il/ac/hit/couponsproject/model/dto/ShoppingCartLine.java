package il.ac.hit.couponsproject.model.dto;

import il.ac.hit.couponsproject.model.dao.*;

public class ShoppingCartLine
{
	private Coupon coupon;
	public ShoppingCartLine(Coupon coupon)
	{
		super();
		this.coupon = coupon;
	}
	public boolean equals(Object ob)
	{
		return this.coupon.equals(((ShoppingCartLine)ob).getCoupon());
	}
	public Coupon getCoupon()
	{
		return coupon;
	}
	public void setCoupon(Coupon coupon)
	{
		this.coupon = coupon;
	}
}