package com.soumya.sms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soumya.sms.enums.UserRole;
import com.soumya.sms.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByRole(UserRole userRole);

	Optional<User> findByEmail(String email);

	List<User> findAllByRole(UserRole userRole);

}
