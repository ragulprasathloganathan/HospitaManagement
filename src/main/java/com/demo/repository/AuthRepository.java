package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.demo.model.User;

public interface AuthRepository extends JpaRepository<User, Integer> {

	UserDetails findByUsername(String username);

}
