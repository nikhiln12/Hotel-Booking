package com.hotelbooking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.hotelbooking.model.Hotel;
import com.hotelbooking.model.Room;
import com.hotelbooking.repository.HotelRepository;
import com.hotelbooking.repository.RoomRepository;

@Service
public class HotelService {

	@Autowired
	private HotelRepository hotelRepository;
	
	@Autowired
    private RoomRepository roomRepository;
	
	//@Cacheable(value = "hotels", key="#location")
	public List<Hotel> searchHotels(String Location){
		return hotelRepository.findByLocation(Location);
	}
	
	
	@Cacheable(value ="hotel", key = "#hotelId")
	public Optional<Hotel> getHotelById(Long hotelId){
		return hotelRepository.findById(hotelId);
	}
	
	
	public List<String> getAllLocations() {
		return hotelRepository.findDistinctLocations();
	}

	
	public List<Hotel> getHotelsByLocation(String location) {
		return hotelRepository.findByLocation(location);
	}

	
	public List<Room> getRoomsByHotel(Long hotelId) {
		return roomRepository.findByHotelId(hotelId);
	}
	

    public List<String> getDistinctLocations() {
        return hotelRepository.findDistinctLocations();
    }
}
