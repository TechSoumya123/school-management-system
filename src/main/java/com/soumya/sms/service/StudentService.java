package com.soumya.sms.service;

import java.util.List;

import com.soumya.sms.dto.SingleStudentDto;
import com.soumya.sms.dto.StudentDto;
import com.soumya.sms.dto.StudentLeaveDto;

public interface StudentService {

	SingleStudentDto getStudentById(Long studentId);

	StudentLeaveDto applyLeave(StudentLeaveDto studentLeaveDto);

	List<StudentLeaveDto> getAllAppliedLeavesByStudentId(Long studentId);

	StudentDto updateStudent(StudentDto studentDto, Long studentId);

	

}
