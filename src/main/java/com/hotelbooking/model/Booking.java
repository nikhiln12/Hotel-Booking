package com.hotelbooking.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime bookindDate;
	private LocalDateTime checkInDate;
	private LocalDateTime checkOutDate;
	private Double amountPaid;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getBookindDate() {
		return bookindDate;
	}

	public void setBookindDate(LocalDateTime bookindDate) {
		this.bookindDate = bookindDate;
	}

	public LocalDateTime getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(LocalDateTime checkInDate) {
		this.checkInDate = checkInDate;
	}

	public LocalDateTime getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(LocalDateTime checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public Double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(Double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public BookingStatus getStatus() {
		return status;
	}

	public void setStatus(BookingStatus status) {
		this.status = status;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Enumerated(EnumType.STRING)
	private BookingStatus status;
	
	@ManyToOne
	@JoinColumn(name = "room_id")
	private Room room;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	

}
