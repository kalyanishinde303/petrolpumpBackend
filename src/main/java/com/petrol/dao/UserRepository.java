package com.petrol.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.petrol.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	 @Query("SELECT u FROM User u WHERE u.email = ?1")
	    public User findByEmail(String email);
}
