package il.ac.hit.couponsproject.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import il.ac.hit.couponsproject.*;
import il.ac.hit.couponsproject.exception.CouponException;
import il.ac.hit.couponsproject.model.dao.ICouponsDAO;
import il.ac.hit.couponsproject.model.dao.impl.HibernateCouponsDAO;
import il.ac.hit.couponsproject.model.dto.Coupon;
import il.ac.hit.couponsproject.model.dto.ShoppingCart;

/**
 * This is an class that extends Servlet and using as a Controller that serves as the project manager
 */
@WebServlet("/Controller/*")
public class Controller extends HttpServlet
{
	Logger logger = Logger.getLogger(Controller.class);
	private static final long serialVersionUID = 1L;
	 /* 
	  * creating a ICouponsDAO that will communicate with the database 
	  */ 
	private ICouponsDAO dao = new HibernateCouponsDAO();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	
	
	public Controller()
	{
		super();
		BasicConfigurator.configure();
		try
		{
			logger.addAppender(new FileAppender(new SimpleLayout(), "log4j.txt"));
			logger.info("log4j was started");
		}
		catch (IOException e)
		{
			e.printStackTrace();
			logger.error("Logger file was not found");
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	
	 /** 
     * Forward the page to the errorPage
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws javax.servlet.ServletException if there was a Servlet problem
     * @throws java.io.IOException if there is a problem forward the page.
     */
	private void errorPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		logger.info("errorPage opened");
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/errorPage.jsp");
		dispatcher.forward(request, response);
	}
	
	 /** 
     * Forward the page to the adminPage
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws javax.servlet.ServletException if there was a Servlet problem
     * @throws java.io.IOException if there is a problem forward the page.
     */
	private void adminPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		logger.info("adminPage opened");
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/adminPage.jsp");
		dispatcher.forward(request, response);
	}
	
	 /** 
     * Forward the page to the shoppingCart page in order to remove coupon
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws javax.servlet.ServletException if there was a Servlet problem
     * @throws java.io.IOException if there is a problem forward the page.
     */
	private void removeFromCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		/*
		 * Get the coupon ID from the customer(View model) and search for it in the shopping cart session and remove it.
		 */
		logger.info("removeFromCart page opened");
		String couponId = request.getParameter("id");
		Coupon selectedCoupon = null;
		HttpSession session = request.getSession();
		ShoppingCart cart = (ShoppingCart) (session.getAttribute("cart")); //getting the existent cart from the session 
		if (couponId != null) 
		{
			try
			{
				selectedCoupon = dao.getCoupon(Integer.parseInt(couponId)); //getting the specific coupon from the data base
				cart.removeCoupon(selectedCoupon); //removing the coupon from the cart
			}
			catch (NumberFormatException e)
			{
				e.printStackTrace();
				logger.error("[NumberFormatException] Error while trying to remove from shopping cart: " + e.getMessage());
				response.sendRedirect("/CouponsProject/Controller/error-page?error=" + e.getStackTrace().toString() + "\n\n" + e.getMessage());
			}
			catch (CouponException e)
			{
				e.printStackTrace();
				logger.error("[CouponException] Error while trying to remove from shopping cart: " + e.getMessage());
				response.sendRedirect("/CouponsProject/Controller/error-page?error=" + e.getStackTrace().toString() + "\n\n" + e.getMessage());
			}

		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/shoppingCart.jsp");
		dispatcher.forward(request, response);
	}
	
	 /** 
     * Forward the page to the updateCoupons page
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws javax.servlet.ServletException if there was a Servlet problem
     * @throws java.io.IOException if there is a problem forward the page.
     */
	private void updateCoupons(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		/*
		 * Get the parameters from the view, check if the input is correct , create a coupon object and use DAO to update.
		 * if there is a problem we store it in hashmap and update the view model with the error.
		 */
		//creating input checking assistance
		logger.info("updateCoupons page opened");
		boolean errorFlag = false; 
		boolean forwardFlag = false;
		Map<String, String> errors = new HashMap<String, String>();
		List<Coupon> coupons = null;
		Coupon selectedCoupon = null;
		request.setAttribute("errors", errors);
		for (Map.Entry<String, String> entry : errors.entrySet())
		{
			entry.setValue("");
		}
		try
		{
			coupons = dao.getCoupons();
			request.setAttribute("CouponsList", coupons);
		}
		catch (CouponException e)
		{
			e.printStackTrace();
			forwardFlag = true;
			logger.error("[CouponException] Error while trying to load coupons for the update: " + e.getMessage());
			response.sendRedirect("/CouponsProject/Controller/error-page?error=" + e.getStackTrace().toString() + "\n\n" + e.getMessage());
		}

		if (request.getParameter("couponSelection") != null)
		{
			try
			{
				selectedCoupon = dao.getCoupon(Integer.parseInt(request.getParameter("couponSelection")));
				//Checking if the name of the coupon is correct
				if (request.getParameter("coupon-name").isEmpty() == false)
				{
					if (request.getParameter("coupon-name").length() > 1)
					{
						selectedCoupon.setName(request.getParameter("coupon-name"));
					}
					else
					{
						errors.put("couponName", "שם קופון שגוי");
						errorFlag = true;
					}
				}
				//Checking if the description of the coupon is correct
				if (request.getParameter("coupon-description").isEmpty() == false)
				{
					System.out.println(request.getParameter("coupon-description"));
					if (request.getParameter("coupon-description").length() > 1)
					{
						selectedCoupon.setDescription(request.getParameter("coupon-description"));
					}
					else
					{
						errors.put("couponDescription", "תיאור קופון שגוי");
						errorFlag = true;
					}
				}
				//Checking if the expiredate of the coupon is correct
				if (request.getParameter("coupon-expiredate").length() != 0)
				{
						selectedCoupon.setExpiredate(request.getParameter("coupon-expiredate"));
				}
				if (request.getParameter("coupon-price").length() != 0)
				{
					try
					{
						Double.parseDouble(request.getParameter("coupon-price"));
						selectedCoupon.setPrice(Double.parseDouble(request.getParameter("coupon-price")));
						if (Double.parseDouble(request.getParameter("coupon-price")) <= 0)
						{
							throw new NumberFormatException("Values error");
						}
					}
					catch (NumberFormatException e)
					{
						errors.put("couponPrice", "מחיר לא תקין !");
						errorFlag = true;
					}
				}
				//Checking if the discount of the coupon is correct
				if (request.getParameter("coupon-discount").length() != 0)
				{
					try
					{
						Integer.parseInt(request.getParameter("coupon-discount"));
						selectedCoupon.setDiscount(Integer.parseInt(request.getParameter("coupon-discount")));
						if (Integer.parseInt(request.getParameter("coupon-discount")) <= 0)
						{
							throw new NumberFormatException("Values error");
						}
					}
					catch (NumberFormatException e)
					{
						errors.put("couponDiscount", "הנחה לא תקינה !");
						errorFlag = true;
					}
				}
				//Calculating the new price after the discount
				if ((request.getParameter("coupon-discount").length() != 0) && (request.getParameter("coupon-price").length() != 0))
				{
					selectedCoupon.setNewprice(Double.parseDouble(request.getParameter("coupon-price")) - (Double.parseDouble(request.getParameter("coupon-discount")) * Double.parseDouble((request.getParameter("coupon-price"))) / 100));
				}
				else if(request.getParameter("coupon-discount").length() != 0)
				{
					selectedCoupon.setNewprice(selectedCoupon.getPrice() - (selectedCoupon.getPrice() * Double.parseDouble((request.getParameter("coupon-discount"))) / 100));
				}
				else if(request.getParameter("coupon-price").length() != 0)
				{
					selectedCoupon.setNewprice(Double.parseDouble(request.getParameter("coupon-price")) - (selectedCoupon.getDiscount() * Double.parseDouble(request.getParameter("coupon-price")) / 100));
				}
				//Checking if the image of the coupon is correct
				if (request.getParameter("coupon-image").isEmpty() != true)
				{
					selectedCoupon.setImage(request.getParameter("coupon-image"));
				}
				//Checking if the location of the coupon is correct
				if (request.getParameter("gmaps-input-address").isEmpty() != true)
				{
					selectedCoupon.setLatitude(Double.parseDouble(request.getParameter("gmaps-output-latitude")));
					selectedCoupon.setLongitude(Double.parseDouble(request.getParameter("gmaps-output-longitude")));
				}
				//Checking if there was any errors
				if (errorFlag == false)
				{
					dao.updateCoupon(selectedCoupon);
					try
					{
						coupons = dao.getCoupons();
						request.setAttribute("CouponsList", coupons);
						request.setAttribute("couponUpdatedFlag", "הקופון עודכן בהצלחה");
						request.setAttribute("textColor", "green");
					}
					catch (CouponException e)
					{
						e.getStackTrace().toString();
						if(forwardFlag == false)
						{
							forwardFlag = true;
							response.sendRedirect("/CouponsProject/Controller/error-page?error=" + e.getStackTrace().toString() + "\n\n" + e.getMessage());
						}
					}
				}
				else
				{
					request.setAttribute("couponUpdatedFlag", "שגיאה בעדכון הקופון");
					request.setAttribute("textColor", "red");
				}

			}
			catch (NumberFormatException e)
			{
				e.printStackTrace();
				logger.error("[NumberFormatException] Error while trying to update a coupon: " + e.getMessage());
				if(forwardFlag == false)
				{
					forwardFlag = true;
					response.sendRedirect("/CouponsProject/Controller/error-page?error=[NumberFormatException] Error while trying to update a coupon: " + e.getMessage());
				}
			}
			catch (CouponException e)
			{
				e.printStackTrace();
				logger.error("[CouponException] Error while trying to update a coupon: " + e.getMessage());
				if(forwardFlag == false)
				{
					forwardFlag = true;
					response.sendRedirect("/CouponsProject/Controller/error-page?error=[CouponException] Error while trying to update a coupon: " + e.getMessage());
				}
			}
		}
		if(forwardFlag == false)
		{
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/updateCoupons.jsp");
			dispatcher.forward(request, response);
		}
	}
	
	 /** 
     * Forward the page to the addCoupon page
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws javax.servlet.ServletException if there was a Servlet problem
     * @throws java.io.IOException if there is a problem forward the page.
     */
	private void addCoupon(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		/*
		 * Get the parameters from the HttpServletRequest, check the input and if its correct we create a new Coupon object and
		 * insert it with the DAO.
		 * if there is a problem we store it in hashmap and update the view model with the problem.
		 */
		//creating input checking assistance
		logger.info("addCoupon page opened");
		boolean errorFlag = false;
		Map<String, String> errors = new HashMap<String, String>();
		String couponName = request.getParameter("coupon-name");
		if (couponName != null)
		{
			//Getting the parameters of the new coupon
			String[] splitedDate = new String[2];
			String couponDescription = request.getParameter("coupon-description");
			String couponExpireDate = request.getParameter("coupon-expiredate");
			String couponPrice = request.getParameter("coupon-price");
			String couponDiscount = request.getParameter("coupon-discount");
			String couponImage = request.getParameter("coupon-image");
			String couponLocation = request.getParameter("gmaps-input-address");
			String latitude = request.getParameter("gmaps-output-latitude");
			String longitude = request.getParameter("gmaps-output-longitude");
			String couponNewPrice = request.getParameter("coupon-newprice");
			String couponCategory = request.getParameter("coupon-category");
			request.setAttribute("couponNameBackup", couponName);
			request.setAttribute("couponDescriptionBackup", couponDescription);
			request.setAttribute("couponExpireDateBackup", couponExpireDate);
			request.setAttribute("couponPriceBackup", couponPrice);
			request.setAttribute("couponDiscountBackup", couponDiscount);
			request.setAttribute("couponImageBackup", couponImage);
			request.setAttribute("couponLocationBackup", couponLocation);
			request.setAttribute("latitudeBackup", latitude);
			request.setAttribute("longitudeBackup", longitude);
			request.setAttribute("couponNewPriceBackup", couponNewPrice);
			request.setAttribute("couponCategoryBackup", couponCategory);
			request.setAttribute("errors", errors);
			for (Map.Entry<String, String> entry : errors.entrySet())
			{
				entry.setValue("");
			}
			
			//Checking if there is errors on the inputs parameters
		   if (couponName.isEmpty() == true)
			{
					errors.put("couponName", "שם קופון שגוי !");
					errorFlag = true;
		    }
			else
			{
				if (couponName.length() <= 1)
				{
					errors.put("couponName", "שם קופון שגוי !");
					errorFlag = true;
				}
			}
			
			if (couponCategory.isEmpty() == true)
			{

				errors.put("couponCategory", "קטגוריה שגויה !");
				errorFlag = true;
			}
			else
			{
				if (couponName.length() <= 1)
				{
					errors.put("couponCategory", "קטגוריה שגויה !");
					errorFlag = true;
				}
			}

			if (couponDescription.isEmpty() == true)
			{

				errors.put("couponDescription", "תיאור קופון שגוי !");
				errorFlag = true;
			}
			else
			{
				if (couponDescription.length() <= 1)
				{
					errors.put("couponDescription", "תיאור קופון שגוי !");
					errorFlag = true;
				}
			}

			if (couponExpireDate.length() == 0)
			{
				errors.put("couponExpireDate", "תאריך ריק !");
				errorFlag = true;
			}

			if (couponPrice.length() == 0)
			{
				errors.put("couponPrice", "מחיר ריק !");
				errorFlag = true;
			}
			else
			{
				try
				{
					Double.parseDouble(couponPrice);
					if (Double.parseDouble(couponPrice) <= 0)
					{
						throw new NumberFormatException("Values error");
					}
				}
				catch (NumberFormatException e)
				{
					errors.put("couponPrice", "מחיר לא תקין !");
					errorFlag = true;
				}
			}

			if (couponDiscount.length() == 0)
			{
				errors.put("couponDiscount", "הנחה ריקה !");
				errorFlag = true;
			}
			else
			{
				try
				{
					Double.parseDouble(couponDiscount);
					if (Double.parseDouble(couponDiscount) > 100 || Double.parseDouble(couponDiscount) <= 0)
					{
						throw new NumberFormatException("Values error");
					}
				}
				catch (NumberFormatException e)
				{
					errors.put("couponDiscount", "הנחה לא תקינה !");
					errorFlag = true;
				}
			}

			if (couponImage.isEmpty() == true)
			{
				errors.put("couponImage", "אנא הכנס תמונה");
				errorFlag = true;
			}

			if (couponLocation.isEmpty() == true)
			{
				errors.put("couponLocation", "אנא הכנס מיקום");
				errorFlag = true;
			}
			
			//Adding the new coupon
			if (errorFlag == false)
			{
				Coupon newCoupon = new Coupon(couponName, couponDescription, Double.parseDouble(latitude), Double.parseDouble(longitude), couponExpireDate, Double.parseDouble(couponPrice),
						couponImage, Integer.parseInt(couponDiscount), Double.parseDouble(couponNewPrice), couponLocation, couponCategory);
				try
				{
					dao.addCoupon(newCoupon);
					request.setAttribute("couponAddedFlag", "הקופון נוסף");
					request.setAttribute("textColor", "green");
				}
				catch (CouponException e)
				{
					e.printStackTrace();
					logger.error("[CouponException] Error while trying to add a new coupon " + e.getMessage());
					response.sendRedirect("/CouponsProject/Controller/error-page?error=" + e.getStackTrace().toString() + "\n\n" + e.getMessage());
				}
			}
		}
		if(errorFlag == true)
		{
			request.setAttribute("couponAddedFlag", "שגיאה בהוספת הקופון");
			request.setAttribute("textColor", "red");
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/addCoupon.jsp");
		dispatcher.forward(request, response);
	}
	
	 /** 
     * Forward the page to the couponsCat page in order to show coupons by specific category
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws javax.servlet.ServletException if there was a Servlet problem
     * @throws java.io.IOException if there is a problem forward the page.
     */
	private void couponCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		/*
		 * Get the latitude and longitude from the user(view) get the selected category.
		 * Using the DAO to get the closet coupons in the specific category.
		 */
		//get the location of the client
		logger.info("couponCategory page opened");
		double latitude = Double.parseDouble(request.getParameter("latitude").toString());
		double longitude = Double.parseDouble(request.getParameter("longitude").toString());
		String category = request.getParameter("category").toString();//get the category that needs to show
		try
		{
			List<Coupon> coupons = dao.getCouponsByCategoryDistance(category, latitude, longitude);
			request.setAttribute("coupons", coupons);
		}
		catch (CouponException e)
		{
			e.printStackTrace();
			logger.error("[CouponException] Error while trying to get categories: " + e.getMessage());
			response.sendRedirect("/CouponsProject/Controller/error-page?error=" + e.getStackTrace().toString() + "\n\n" + e.getMessage());
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/couponsCat.jsp");
		dispatcher.forward(request, response);
	}
	
	/** 
     * Forward the page to the logout page
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws javax.servlet.ServletException if there was a Servlet problem
     * @throws java.io.IOException if there is a problem forward the page.
     */
	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		logger.info("logout page opened");
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/logout.jsp");
		dispatcher.forward(request, response);
	}
	
	 /** 
     * Forward the page to the deleteCoupon page
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws javax.servlet.ServletException if there was a Servlet problem
     * @throws java.io.IOException if there is a problem forward the page.
     */
	private void deleteCoupons(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		/*
		 * Get the selected coupon ID from the user, using the DAO we remove the coupon by the ID.
		 * Send a message to the user if the coupon removed or not.
		 */
		//get the list of all coupons that exist
		logger.info("deleteCoupons page opened");
		List<Coupon> coupons = null;
		try
		{
			coupons = dao.getCoupons();
		}
		catch (CouponException e1)
		{
			e1.printStackTrace();
			logger.error("[CouponException] Error while trying to delete coupon: " + e1.getMessage());
			response.sendRedirect("/CouponsProject/Controller/error-page?error=" + e1.getMessage());
		}
		//getting the id of the coupon that should be delete
		String couponId = request.getParameter("couponSelection");
		//deletes the coupon
		if (couponId != null)
		{
			try
			{
				dao.deleteCoupon(Integer.parseInt(couponId));
				request.setAttribute("couponDeletedFlag", "הקופון נמחק");
				request.setAttribute("textColor", "green");
				coupons = dao.getCoupons();
			}
			catch (CouponException e)
			{
				e.printStackTrace();
				request.setAttribute("couponDeletedFlag", "שגיאה במחיקה");
				request.setAttribute("textColor", "red");
			}
		}
		request.setAttribute("CouponsList", coupons);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/deleteCoupons.jsp");
		dispatcher.forward(request, response);
	}
	
	 /** 
     * Forward the page to the shoppingCart page
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws javax.servlet.ServletException if there was a Servlet problem
     * @throws java.io.IOException if there is a problem forward the page.
     */
	private void shoppingCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		logger.info("shoppingCart page opened");
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/shoppingCart.jsp");
		dispatcher.forward(request, response);
	}
	
	 /** 
     * Add a new coupon to the shopping cart.
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws javax.servlet.ServletException if there was a Servlet problem
     * @throws java.io.IOException if there is a problem forward the page.
     */
	private void addToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		/*
		 * Get the ID parameter and add the coupon to the shopping cart.
		 */
		logger.info("addToCart page opened");
		Coupon coupon = null;
		String couponId = request.getParameter("id").toString();
		if (couponId != null) {
			couponId = couponId.trim();
			int id = Integer.parseInt(couponId);

			try
			{
				coupon = dao.getCoupon(id);
			}
			catch (CouponException e)
			{
				e.printStackTrace();
				logger.error("[CouponException] Error while trying to add to cart " + e.getMessage());
				response.sendRedirect("/CouponsProject/Controller/error-page?error=" + e.getStackTrace().toString() + "\n\n" + e.getMessage());
			}
			//getting the existent cart 
			HttpSession session = request.getSession();
			if (session.getAttribute("cart") == null) {
				session.setAttribute("cart", new ShoppingCart());
			}
			//adding the selected coupon to the cart
			ShoppingCart cart = (ShoppingCart) (session.getAttribute("cart"));
			cart.addCoupon(coupon);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/shoppingCart.jsp");
			dispatcher.forward(request, response);
			
		} else {
			
	}}

	 /** 
     * Forward the page to the viewCoupons page in order to show all coupons
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws javax.servlet.ServletException if there was a Servlet problem
     * @throws java.io.IOException if there is a problem forward the page.
     */
	private void getCouponsPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		/*
		 * Get the user latitude and longitude.
		 * Get the nearest coupons by the latitude and longitude using the DAO.
		 * Get the categories and show them too.
		 */
		//getting the location of the client
		logger.info("getCouponsPage opened");
		boolean exceptionOccured = false;
		double latitude = 0;
		double longitude = 0;
		try
		{
		     latitude = Double.parseDouble(request.getParameter("uLatitude").toString());
			 longitude = Double.parseDouble(request.getParameter("uLongitude").toString());
		}
		catch(NullPointerException e)
		{
			exceptionOccured = true;
			logger.error("[NullPointerException] Error while trying to get coupons " + e.getMessage());
			response.sendRedirect("/CouponsProject/Controller/error-page?error=" + e.getStackTrace().toString() + "\n\n" + e.getMessage());
		}
		List<Coupon> coupons = null;
		HashSet<String> categories = dao.getCategories();
		request.setAttribute("categories", categories);
		try
		{
			coupons = dao.getCouponsByDistance(latitude, longitude);
		}
		catch (CouponException e)
		{
			e.printStackTrace();
			logger.error("[CouponException] Error while trying to get coupons " + e.getMessage());
			response.sendRedirect("/CouponsProject/Controller/error-page?error=" + e.getStackTrace().toString() + "\n\n" + e.getMessage());
		}
		request.setAttribute("CouponsList", coupons);
		
		if(exceptionOccured != true)
		{
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/viewCoupons.jsp");
			dispatcher.forward(request, response);
		}
	}

	 /** 
     * Forward the page to the mainPage page
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws javax.servlet.ServletException f there was a problem with the Servlet
     * @throws java.io.IOException if there is a problem forward the page.
     */
	private void mainPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		logger.info("mainPage opened");
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/mainPage.jsp");
		dispatcher.forward(request, response);
	}
	
	 /** 
     * Forward the page to the login page
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws javax.servlet.ServletException if there was a problem with the Servlet
     * @throws java.io.IOException if there is a problem forward the page.
     */
	private void loginPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		/*
		 * Get the user input (username, password)
		 * Encrypt the password to MD5 and check if it equals the admin password from the login file.
		 * Is its correct the admin forward to the admin page and if not it stays the login page.
		 */
		logger.info("loginPage opened");
		List<String> lines = Files.readAllLines(Paths.get(getServletContext().getRealPath("/WEB-INF/login.txt")), Charset.defaultCharset());
		byte[] bytesOfMessage;

		MessageDigest md;
		String md5UserPassword;
		String userName = lines.get(0);
		String password = lines.get(1);
		String incorrect = "";
		Cookie loggedIn;
		if(request.getParameter("submit") != null)
		{
			bytesOfMessage = request.getParameter("password").getBytes("UTF-8");
			try
			{
				
				md = MessageDigest.getInstance("MD5");
				md.update(request.getParameter("password").getBytes(),0,request.getParameter("password").length());
				md5UserPassword = (new BigInteger(1,md.digest()).toString(16));
				if((userName.equals(request.getParameter("username")) == true) && (password.equals(md5UserPassword)) == true)
				{
					loggedIn = new Cookie("loggedIn", "true");
					loggedIn.setMaxAge(300);
					response.addCookie(loggedIn);
					response.sendRedirect("/CouponsProject/Controller/admin-page");
					//RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Controller/admin-page");
					//dispatcher.forward(request, response);
				}
				else
				{
					incorrect = "Incorrect username or password";	
					request.setAttribute("incorrect", incorrect);
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
					dispatcher.forward(request, response);
				}
			}
			catch (NoSuchAlgorithmException e)
			{
				e.printStackTrace();
				logger.error("[NuSuchAlgortithmException] Error while trying to login " + e.getMessage());
				response.sendRedirect("/CouponsProject/Controller/error-page?error=" + e.getStackTrace().toString() + "\n\n" + e.getMessage());
			}			
		}
		else
		{
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
			dispatcher.forward(request, response);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		/*
		 * Get the URL path and forward it to the correct request.
		 */
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		String path = request.getPathInfo();
		if (path.contains("get-coupons"))
		{
			getCouponsPage(request, response);
		}
		else if(path.contains("main-page"))
		{
			mainPage(request, response);
		}
		else if(path.contains("error-page"))
		{
			errorPage(request, response);
		}
		else if(path.contains("coupon-cat"))
		{
			couponCategory(request, response);
			
		}
		else if(path.contains("logout"))
		{
			logout(request, response);
		}
		else if(path.contains("admin-page"))
		{
			adminPage(request, response);
		}
		else if(path.contains("login-page"))
		{
			loginPage(request, response);
		}
		else if(path.contains("removeFromCart"))
		{
			removeFromCart(request, response);
		}
		else if(path.contains("addToCart"))
		{
			addToCart(request, response);
		}
		else if(path.contains("shopping-cart"))
		{
			shoppingCart(request, response);
		}
		else if (path.contains("update-coupons"))
		{
			updateCoupons(request, response);
		}
		else if (path.contains("delete-coupons"))
		{
			deleteCoupons(request, response);
		}
		else if (path.contains("add-coupon"))
		{
			addCoupon(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}
