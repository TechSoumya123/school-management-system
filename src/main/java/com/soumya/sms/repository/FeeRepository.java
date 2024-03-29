package com.soumya.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soumya.sms.model.Fee;

public interface FeeRepository extends JpaRepository<Fee, Long> {

}
