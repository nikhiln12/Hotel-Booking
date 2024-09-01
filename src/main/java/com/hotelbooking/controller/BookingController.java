package com.hotelbooking.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotelbooking.exception.BookingNotFoundException;
import com.hotelbooking.exception.ResourceNotFoundException;
import com.hotelbooking.exception.RoomUnavailableException;
import com.hotelbooking.model.Booking;
import com.hotelbooking.model.User;
import com.hotelbooking.service.BookingService;
import com.hotelbooking.service.UserService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {


    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @PostMapping("/bookRoom")
    public ResponseEntity<?> bookRoom(
            @RequestParam Long roomId,
            @RequestParam String checkInTime,
            @RequestParam String checkOutTime,
            @RequestParam double amount) {
        try {
            // Assuming the user is authenticated and you have a way to get the logged-in user ID
            // Replace the below line with your method of fetching the logged-in user's ID
        	Long userId = 1L;
        	System.out.println(roomId);
            LocalDateTime checkIn = LocalDateTime.parse(checkInTime);
            LocalDateTime checkOut = LocalDateTime.parse(checkOutTime);
            User user = userService.findUserById(userId);
            
            Booking booking = bookingService.bookRoom(roomId, user, checkIn, checkOut, amount);
            return ResponseEntity.ok(booking);
        } catch (RoomUnavailableException | ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserBookings(@PathVariable String userId) {
    	try {
            Long userIdLong = 1L; // Ensure correct conversion
            List<Booking> bookings = bookingService.getBookingsByUser(userIdLong);
            return ResponseEntity.ok(bookings);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user ID format");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<?> getBookingById(@PathVariable Long bookingId) {
        try {
            Booking booking = bookingService.getBookingById(bookingId);
            return ResponseEntity.ok(booking);
        } catch (BookingNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long bookingId) {
        try {
            bookingService.cancelBooking(bookingId);
            return ResponseEntity.ok("Booking canceled successfully");
        } catch (BookingNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    // Placeholder method to get logged-in user's ID
    private Long getLoggedInUserId() {
        // Implement logic to fetch the logged-in user's ID
        // This could be from Spring Security, session, etc.
        // For example, return some static ID for now:
        return 1L;
    }
    
}
    
