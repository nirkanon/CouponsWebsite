package il.ac.hit.couponsproject.model.dao;

import il.ac.hit.couponsproject.exception.CouponException;
import il.ac.hit.couponsproject.model.dto.Coupon;
import java.util.*;


/**
 * This is an interface that connect between the database and the Couopon class
 * all methods listed below
 */
public interface ICouponsDAO
{
    /** 
     * Add a new coupon to the database
     * @param coupon the coupon to add
     * @throws il.ac.hit.couponsproject.exception.CouponException if there is a problem adding the coupon
     */
    public void addCoupon(Coupon coupon) throws CouponException;
    
    /** 
     * Get all coupons from the data base
     * @throws il.ac.hit.couponsproject.exception.CouponException if there is a problem getting the coupons
     * @return returns List of all the coupons from database
     */
    public List<Coupon> getCoupons() throws CouponException;
    
    /** 
     * Get all coupons from the data base
     * @param id the coupon id to get
     * @throws il.ac.hit.couponsproject.exception.CouponException if there is a problem getting the coupons
     * @return returns List of all the coupons from database
     */
    public Coupon getCoupon(int id) throws CouponException;
    
    /** 
     * Delete a specific coupon from the database
     * @param id the Coupon id to delete
     * @throws il.ac.hit.couponsproject.exception.CouponException if there is a problem deleting the coupon
     */
    public void deleteCoupon(int id) throws CouponException;
    
    /** 
     * Update a coupon
     * @param coupon the coupon to update
     * @throws il.ac.hit.couponsproject.exception.CouponException if there is a problem updating the coupon
     */
    public void updateCoupon(Coupon coupon) throws CouponException;
    
    /** 
     * Get all coupons by the nearest ones
     * @param userLatitude the use latitude
     * @param userLongitude the user longitude
     * @throws il.ac.hit.couponsproject.exception.CouponException if there is a problem getting the coupons
     * @return a list with coupons ordered by distance
     */
    public List<Coupon> getCouponsByDistance(double userLatitude, double userLongitude) throws CouponException;
    
    /** 
     * Get the categories of coupons
     * @return hashset that contains the categories
     */
    public HashSet<String> getCategories();
    
    /** 
     * Get all coupons by the nearest ones in a specific category
     * @param category the selected category to get coupons from
     * @param userLatitude the use latitude
     * @param userLongitude the user longitude
     * @throws il.ac.hit.couponsproject.exception.CouponException if there is a problem getting the coupons
     * @return list that contains all the coupons
     */
    public List<Coupon> getCouponsByCategoryDistance(String category, double userLatitude, double userLongitude) throws CouponException;
}