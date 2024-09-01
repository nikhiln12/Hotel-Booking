package com.hotelbooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotelbooking.model.Room;

public interface RoomRepository extends JpaRepository<Room, Long>{

	List<Room> findByHotelId(Long hotelId);
}
