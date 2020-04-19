package controller;

import java.time.LocalDate;

import model.Order;

public interface IRKController {

	public Order[] searchOrdersByDate(LocalDate startDate, LocalDate endDate);

}
