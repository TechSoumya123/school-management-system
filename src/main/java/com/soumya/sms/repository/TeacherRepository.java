package com.soumya.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soumya.sms.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

}
