package controller;

import java.time.LocalDate;

import model.Order;

/**
 * Interface for the restaurant keeper controller
 * 
 * @author Arttu Seuna
 *
 */
public interface IRKController {

	public Order[] searchOrdersByDate(LocalDate startDate, LocalDate endDate);

}
