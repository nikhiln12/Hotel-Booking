package com.hotelbooking.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.hotelbooking.exception.ResourceNotFoundException;
import com.hotelbooking.model.Booking;
import com.hotelbooking.model.Payment;
import com.hotelbooking.repository.BookingRepository;
import com.hotelbooking.repository.PaymentRepository;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private BookingRepository bookingRepository;
	
	public Payment processPayment(Long BookingId, double amount) {
		
		Booking booking = bookingRepository.findById(BookingId).orElseThrow(() -> new ResourceNotFoundException("Booking Not Found"));
		
		Payment payment = new Payment();
		payment.setAmount(amount);
		payment.setBooking(booking);
		payment.setPaymentTime(LocalDateTime.now());
		payment.setPaymentMethod("ONLINE");
		payment.setStatus("SUCCESS");
		
		return paymentRepository.save(payment);
	}
	
	@Cacheable(value = "payments", key = "#bookingId")
	public List<Payment> getPaymentsByBookingId(Long BookingId){
		return paymentRepository.findByBookingId(BookingId);
	}
}
