package com.hotelbooking.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.hotelbooking.exception.BookingNotFoundException;
import com.hotelbooking.exception.PaymentFailedExcpetion;
import com.hotelbooking.exception.ResourceNotFoundException;
import com.hotelbooking.exception.RoomUnavailableException;
import com.hotelbooking.model.Booking;
import com.hotelbooking.model.BookingStatus;
import com.hotelbooking.model.Payment;
import com.hotelbooking.model.Room;
import com.hotelbooking.model.User;
import com.hotelbooking.repository.BookingRepository;
import com.hotelbooking.repository.RoomRepository;

import jakarta.transaction.Transactional;

@Service
public class BookingService {

	@Autowired
	private BookingRepository bookingRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private ExecutorService executorService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Transactional
	public synchronized Booking bookRoom(Long roomId, User user, LocalDateTime checkInTime, LocalDateTime checkOutTime,
			 double amount) {
		
		Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room Not Found"));
		
		if(!room.isAvailable()) {
			throw new RoomUnavailableException("Room is Not Available");
		}
		
		Booking booking = new Booking();
		booking.setBookindDate(LocalDateTime.now());
		booking.setCheckInDate(checkInTime);
		booking.setCheckOutDate(checkOutTime);
		booking.setRoom(room);
		booking.setAmountPaid(amount);
		booking.setUser(user);
		booking.setStatus(BookingStatus.CONFIRMED);
		
		room.setAvailable(false);
		roomRepository.save(room);
		bookingRepository.save(booking);
		
		Payment payment = paymentService.processPayment(roomId, amount);
		if(!payment.getStatus().equals("SUCCESS")) {
			throw new PaymentFailedExcpetion("Payment Failed");
		}
		
		return booking;
	}
	
	public void cancelBooking(Long bookingId) {
		
		Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new ResourceNotFoundException("Booking Not Found"));
		
		Room room = booking.getRoom();
		room.setAvailable(true);
		roomRepository.save(room);
		bookingRepository.delete(booking);
	}
	
	@Cacheable(value = "userBookings", key="#userId")
	public List<Booking> getUserBookings(Long userId){
		return bookingRepository.findByUserId(userId);
	}
	
	public Booking getBookingById(Long bookingId) {
		return bookingRepository.findById(bookingId)
				.orElseThrow(() -> new BookingNotFoundException("Booking not found"));
	}

	public List<Booking> getBookingsByUser(Long userId) {
		return bookingRepository.findByUserId(userId);
	}
}
