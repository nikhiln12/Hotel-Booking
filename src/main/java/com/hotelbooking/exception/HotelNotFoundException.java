package com.hotelbooking.exception;

public class HotelNotFoundException extends RuntimeException{

	public HotelNotFoundException(String message) {
		super(message);
	}
}
