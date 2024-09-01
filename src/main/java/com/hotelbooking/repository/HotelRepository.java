package com.hotelbooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hotelbooking.model.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long>{

	List<Hotel> findByLocation(String location);

    @Query("SELECT DISTINCT h.location FROM Hotel h")
	List<String> findDistinctLocations();


}
