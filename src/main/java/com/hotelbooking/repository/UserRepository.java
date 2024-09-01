package com.hotelbooking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotelbooking.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	
    User findByUsername(String username);
}
