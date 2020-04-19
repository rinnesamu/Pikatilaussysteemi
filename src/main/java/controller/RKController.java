package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import model.IOrderDao;
import model.Order;
import model.OrderAccessObject;
import view.RestaurantKeeper;

/**
 * Controller class for handling the logic for restaurant keeper view
 * 
 * @author Arttu Seuna
 *
 */

public class RKController implements IRKController {
	
	// DAOs
	private IOrderDao orderDao;
	
	
	private RestaurantKeeper restaurantKeeper;
	
	
	public RKController(RestaurantKeeper restaurantKeeper) {
		this.orderDao = new OrderAccessObject();
		
		this.restaurantKeeper = restaurantKeeper;
	}
	
	/**
	 * Method for searching the database for orders based on passed dates
	 */
	@Override
	public Order[] searchOrdersByDate(LocalDate startDate, LocalDate endDate) {
		System.out.println("In controller: search button pressed");
		LocalDateTime startDateTime = startDate.atStartOfDay();
		LocalDateTime endDateTime = endDate.atTime(23,59, 59);
		
		return orderDao.readOrdersByDate(startDateTime, endDateTime);
	}
}
