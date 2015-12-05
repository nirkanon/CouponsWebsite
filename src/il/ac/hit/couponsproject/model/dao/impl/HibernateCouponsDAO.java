package il.ac.hit.couponsproject.model.dao.impl;

import il.ac.hit.couponsproject.controller.Controller;
import il.ac.hit.couponsproject.exception.CouponException;

import org.apache.log4j.*;

import il.ac.hit.couponsproject.model.dao.ICouponsDAO;
import il.ac.hit.couponsproject.model.dto.Coupon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * This is a class which implements the ICouponsDAO interface
 */
public class HibernateCouponsDAO implements ICouponsDAO
{
	// create singleton instance of the HibernateCouponsDAO
	public static HibernateCouponsDAO instance;
	// Create a new SessionFactory
	SessionFactory factory = new AnnotationConfiguration().configure().buildSessionFactory();
	// creating a new session for adding products
	Session session = null;

	@Override
	public List<Coupon> getCouponsByCategoryDistance(String category, double userLatitude, double userLongitude) throws CouponException
	{
		/*
		 * Create a HQL query which gets the user latitude, longitude, category
		 * and calculate the distance and order by the nearest.
		 */
		Session session = factory.openSession();
		List<Coupon> list = new ArrayList<Coupon>();
		try
		{
			final Transaction tx = session.beginTransaction();
			Query query = session.createQuery("FROM Coupon WHERE CATEGORY = :ucategory ORDER BY (6371 * 2 * ASIN(SQRT(POWER(SIN((:ulatitude - abs(latitude)) * pi()/180 / 2),2) +"
					+ "COS(:ulatitude * pi()/180 ) * COS(abs(latitude) * pi()/180) *" + "POWER(SIN((:ulongitude - longitude) * pi()/180 / 2), 2))))*1000 " + "");

			query.setParameter("ulongitude", userLongitude);
			query.setParameter("ulatitude", userLatitude);
			query.setParameter("ucategory", category);
			//query.setMaxResults(10);

			list = query.list();

			tx.commit();
			System.out.println("HibernateCouponsDAO.getCouponsByDistance() : " + list);

		}
		catch (HibernateException e)
		{
			e.printStackTrace();
			if (session.getTransaction() != null)
				session.getTransaction().rollback();
			return null;
		}
		finally
		{
			session.close();
		}
		return list;
	}

	@Override
	public void addCoupon(Coupon coupon) throws CouponException
	{
		/*
		 * This method get a new Coupons and insert it to the database.
		 */
		session = factory.openSession();
		try
		{
			session.beginTransaction();
			session.save(coupon);
			session.getTransaction().commit();
		}
		catch (HibernateException e)
		{
			System.out.println("There was a problem adding a new coupon:" + e.getMessage());
		}
		finally
		{
			session.close();
		}
	}

	@Override
	public List<Coupon> getCoupons() throws CouponException
	{
		/*
		 * Create a HQL query which gets all the coupons from the database.
		 */
		List<Coupon> coupons = null;
		try
		{
			session = factory.openSession();
			session.beginTransaction();
			coupons = session.createQuery("from Coupon").list();
		}
		catch (HibernateException e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			session.close();
			return coupons;
		}
	}

	@Override
	public HashSet<String> getCategories()
	{
		/*
		 * Create a HQL query which gets the categories.
		 */
		HashSet<String> categories = new HashSet<String>();
		List<Coupon> coupons = null;
		try
		{
			session = factory.openSession();
			session.beginTransaction();
			coupons = session.createQuery("from Coupon").list();

			for (Coupon coupon : coupons)
			{
				categories.add(coupon.getCategory());
			}
		}
		catch (HibernateException e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			session.close();
			return categories;
		}
	}

	public List<Coupon> getCouponsByDistance(double userLatitude, double userLongitude) throws CouponException
	{
		/*
		 * Return the nearest coupons by the user latitude and longitude.
		 */
		Session session = factory.openSession();
		List<Coupon> list = new ArrayList<Coupon>();
		try
		{
			final Transaction tx = session.beginTransaction();
			Query query = session.createQuery("FROM Coupon ORDER BY (6371 * 2 * ASIN(SQRT(POWER(SIN((:ulatitude - abs(latitude)) * pi()/180 / 2),2) +"
					+ "COS(:ulatitude * pi()/180 ) * COS(abs(latitude) * pi()/180) *" + "POWER(SIN((:ulongitude - longitude) * pi()/180 / 2), 2))))*1000 " + "");

			query.setParameter("ulongitude", userLongitude);
			query.setParameter("ulatitude", userLatitude);
			//query.setMaxResults(10);

			list = query.list();

			tx.commit();
			System.out.println("HibernateCouponsDAO.getCouponsByDistance() : " + list);

		}
		catch (HibernateException e)
		{
			e.printStackTrace();
			if (session.getTransaction() != null)
				session.getTransaction().rollback();
			return null;
		}
		finally
		{
			session.close();
		}
		return list;
	}

	@Override
	public Coupon getCoupon(int i_id) throws CouponException
	{
		/*
		 * Return a specific coupon by his id.
		 */
		List coupons = null;
		try
		{
			session = factory.openSession();
			session.beginTransaction();
			Query q = session.createQuery("from Coupon where ID = :id");
			q.setParameter("id", i_id);
			coupons = q.list();
		}
		catch (HibernateException e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			session.close();
			return (Coupon) coupons.get(0);
		}
	}

	public static HibernateCouponsDAO getInstance()
	{
		/*
		 * Return the HibernateCouponsDAO instance and create a new one if it
		 * doesn't exists (singeltone).
		 */
		if (instance == null)
		{
			instance = new HibernateCouponsDAO();
		}
		return instance;
	}

	@Override
	public void deleteCoupon(int i_id) throws CouponException
	{
		/*
		 * Delete a specific coupon by id.
		 */
		int rowCount = 0;
		try
		{
			session = factory.openSession();
			session.beginTransaction();
			Query q = session.createQuery("delete from Coupon where ID = :id");
			q.setParameter("id", i_id);
			rowCount = q.executeUpdate();
			session.getTransaction().commit();
		}
		catch (HibernateException e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			session.close();
			System.out.println("rows affected: " + rowCount);
		}

	}

	@Override
	public void updateCoupon(Coupon coupon) throws CouponException
	{
		/*
		 * Update a coupon.
		 */
		int rowCount = 0;
		try
		{
			session = factory.openSession();
			session.beginTransaction();
			Query q = session
					.createQuery("update Coupon set NAME = :name, DESCRIPTION = :description, LATITUDE = :latitude, LONGITUDE = :longitude , EXPIREDATE = :expiredate, PRICE = :price, IMAGE = :image, DISCOUNT = :discount, NEWPRICE = :newprice, LOCATION = :location, CATEGORY =:category where ID = :id");
			q.setParameter("id", coupon.getId());
			q.setParameter("name", coupon.getName());
			q.setParameter("description", coupon.getDescription());
			q.setDouble("latitude", coupon.getLatitude());
			q.setDouble("longitude", coupon.getLongitude());
			q.setParameter("expiredate", coupon.getExpiredate());
			q.setDouble("price", coupon.getPrice());
			q.setParameter("image", coupon.getImage());
			q.setParameter("discount", coupon.getDiscount());
			q.setDouble("newprice", coupon.getNewprice());
			q.setParameter("location", coupon.getLocation());
			q.setParameter("category", coupon.getCategory());
			rowCount = q.executeUpdate();
			session.getTransaction().commit();
		}
		catch (HibernateException e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			session.close();
			System.out.println("rows affected: " + rowCount);
		}

	}

}
