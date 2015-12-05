package il.ac.hit.couponsproject.model.dto;


/**
 *  
 * Class that describe a coupon.
 */
public class Coupon
{
	/** Field that contains the Coupon id and the discount */
    private int id, discount;
	/** Field that contains the Coupon name, description, expire date, image, location and category */
    private String name, description, expiredate, image, location, category;
	/** Field that contains the Coupon latitude, longitude, price and the new price */
    private double latitude, longitude, price, newprice;

    /**
     *  
     * Default constructor.
     */
	public Coupon()
	{
		super();
	}
    
    /**
     *  
     * Constructor that create a new coupon.
     *
     * @param id coupon id
     * @param name coupon name
     * @param description coupon description
     * @param latitude coupon latitude
     * @param longitude coupon longitude
     * @param expiredate coupon expiredate
     * @param price coupon price
     * @param image coupon image
     * @param discount coupon discount
     * @param newprice the newprice
     * @param location coupon location
     * @param category coupon category
     */
	public Coupon(int id, String name, String description, double latitude, double longitude, String expiredate, double price, String image, int discount, double newprice, String location,
			String category)
	{
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.expiredate = expiredate;
		this.price = price;
		this.image = image;
		this.discount = discount;
		this.newprice = newprice;
		this.location = location;
		this.category = category;
	}
	
	/**
	 *  
	 * Constructor that create a new coupon without an id.
	 *
	 * @param name coupon name
	 * @param description coupon description
	 * @param latitude coupon latitude
	 * @param longitude coupon longitude
	 * @param expiredate coupon expiredate
	 * @param price coupon price
	 * @param image coupon image
	 * @param discount coupon discount
	 * @param newprice the newprice
	 * @param location coupon location
	 * @param category coupon category
	 */
	public Coupon(String name, String description, double latitude, double longitude, String expiredate, double price, String image, int discount, double newprice, String location,
			String category)
	{
		super();
		this.name = name;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.expiredate = expiredate;
		this.price = price;
		this.image = image;
		this.discount = discount;
		this.newprice = newprice;
		this.location = location;
		this.category = category;
	}
	
	/**
	 * Gets the category.
	 *
	 * @return the category
	 */
	public String getCategory()
	{
		return category;
	}
	
	/**
	 * Sets the category.
	 *
	 * @param category the new category
	 */
	public void setCategory(String category)
	{
		this.category = category;
	}
	
	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public String getLocation()
	{
		return location;
	}
	
	/**
	 * Sets the location.
	 *
	 * @param location the new location
	 */
	public void setLocation(String location)
	{
		this.location = location;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId()
	{
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id)
	{
		this.id = id;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}
	
	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	/**
	 * Gets the latitude.
	 *
	 * @return the latitude
	 */
	public double getLatitude()
	{
		return latitude;
	}
	
	/**
	 * Sets the latitude.
	 *
	 * @param latitude the new latitude
	 */
	public void setLatitude(double latitude)
	{
		this.latitude = latitude;
	}
	
	/**
	 * Gets the longitude.
	 *
	 * @return the longitude
	 */
	public double getLongitude()
	{
		return longitude;
	}
	
	/**
	 * Sets the longitude.
	 *
	 * @param longitude the new longitude
	 */
	public void setLongitude(double longitude)
	{
		this.longitude = longitude;
	}
	
	/**
	 * Gets the expiredate.
	 *
	 * @return the expiredate
	 */
	public String getExpiredate()
	{
		return expiredate;
	}
	
	/**
	 * Sets the expiredate.
	 *
	 * @param expiredate the new expiredate
	 */
	public void setExpiredate(String expiredate)
	{
		this.expiredate = expiredate;
	}
	
	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public double getPrice()
	{
		return price;
	}
	
	/**
	 * Sets the price.
	 *
	 * @param price the new price
	 */
	public void setPrice(double price)
	{
		this.price = price;
	}
	
	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public String getImage()
	{
		return image;
	}
	
	/**
	 * Sets the image.
	 *
	 * @param image the new image
	 */
	public void setImage(String image)
	{
		this.image = image;
	}
	
	/**
	 * Gets the discount.
	 *
	 * @return the discount
	 */
	public int getDiscount()
	{
		return discount;
	}
	
	/**
	 * Sets the discount.
	 *
	 * @param discount the new discount
	 */
	public void setDiscount(int discount)
	{
		this.discount = discount;
	}
	
	/**
	 * Gets the newprice.
	 *
	 * @return the newprice
	 */
	public double getNewprice()
	{
		return newprice;
	}
	
	/**
	 * Sets the newprice.
	 *
	 * @param newprice the new newprice
	 */
	public void setNewprice(double newprice)
	{
		this.newprice = newprice;
	}
	
	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Coupon [id=" + id + ", name=" + name + ", description=" + description + ", latitude=" + latitude + ", longitude=" + longitude + ", expiredate=" + expiredate + ", price=" + price
				+ ", image=" + image + ", discount=" + discount + ", newprice=" + newprice + ", location=" + location + ", category=" + category + "]";
	}
}