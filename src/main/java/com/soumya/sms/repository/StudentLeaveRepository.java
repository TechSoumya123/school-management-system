package com.soumya.sms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soumya.sms.model.StudentLeave;

public interface StudentLeaveRepository extends JpaRepository<StudentLeave, Long> {

	List<StudentLeave> findAllByUserId(Long studentId);

}
