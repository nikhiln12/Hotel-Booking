package com.hotelbooking.exception;

public class RoomUnavailableException extends RuntimeException {

	public RoomUnavailableException(String message) {
		super(message);
	}
}
