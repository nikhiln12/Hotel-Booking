package com.hotelbooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotelbooking.model.Hotel;
import com.hotelbooking.model.Room;
import com.hotelbooking.service.HotelService;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

	/*
	 * @GetMapping("/location") public ResponseEntity<List<String>> getLocations() {
	 * try { List<String> locations = hotelService.getAllLocations(); return
	 * ResponseEntity.ok(locations); } catch (Exception e) { return
	 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); } }
	 */

    @GetMapping("/locations/{location}/hotels")
    public ResponseEntity<List<Hotel>> getHotelsByLocation(@PathVariable String location) {
        try {
            List<Hotel> hotels = hotelService.getHotelsByLocation(location);
            return ResponseEntity.ok(hotels);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{hotelId}/rooms")
    public ResponseEntity<List<Room>> getRoomsByHotel(@PathVariable Long hotelId) {
        try {
            List<Room> rooms = hotelService.getRoomsByHotel(hotelId);
            return ResponseEntity.ok(rooms);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @GetMapping("/locations")
    public ResponseEntity<List<String>> getDistinctLocations() {
        try {
            List<String> locations = hotelService.getDistinctLocations();
            return ResponseEntity.ok(locations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
