package com.soumya.sms.service;

import java.util.List;

import com.soumya.sms.dto.FeeDto;
import com.soumya.sms.dto.SingleStudentDto;
import com.soumya.sms.dto.SingleTeacherDto;
import com.soumya.sms.dto.StudentDto;
import com.soumya.sms.dto.StudentLeaveDto;
import com.soumya.sms.dto.TeacherDto;

public interface AdminService {

	StudentDto postStudent(StudentDto studentDto);

	List<StudentDto> getAllStudents();

	void deleteStudent(Long studentId);

	SingleStudentDto getStudentById(Long studentId);

	StudentDto updateStudent(StudentDto studentDto, Long studentId);

	FeeDto payFee(FeeDto feeDto, Long studentId);

	List<StudentLeaveDto> getAllAppliedLeaves();

	StudentLeaveDto changeLeaveStatus(Long leaveId, String status);

	TeacherDto postTeacher(TeacherDto teacherDto);

	List<TeacherDto> getAllTeacher();

	void deleteTeacher(Long teacherId);

	SingleTeacherDto getTeacherById(Long teacherId);

	TeacherDto updateTeacher(TeacherDto teacherDto, Long teacherId);

}
