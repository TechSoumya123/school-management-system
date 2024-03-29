package com.soumya.sms.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soumya.sms.dto.FeeDto;
import com.soumya.sms.dto.SingleStudentDto;
import com.soumya.sms.dto.SingleTeacherDto;
import com.soumya.sms.dto.StudentDto;
import com.soumya.sms.dto.StudentLeaveDto;
import com.soumya.sms.dto.TeacherDto;
import com.soumya.sms.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminController {

	private final AdminService adminService;

	@PostMapping(path = { "/student" })
	public ResponseEntity<?> addStudent(@RequestBody StudentDto studentDto) {
		StudentDto sDto = adminService.postStudent(studentDto);
		if (sDto == null)
			return ResponseEntity.ok("Something went Wrong");
		return ResponseEntity.status(HttpStatus.CREATED).body(sDto);
	}

	@GetMapping(value = { "/students" })
	public ResponseEntity<List<StudentDto>> getAllStudents() {
		List<StudentDto> studentDtos = adminService.getAllStudents();
		return ResponseEntity.ok(studentDtos);
	}

	@DeleteMapping("/student/{id}")
	public ResponseEntity<Void> deleteStudent(@PathVariable("id") Long studentId) {
		adminService.deleteStudent(studentId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/student/{id}")
	public ResponseEntity<SingleStudentDto> getStudentById(@PathVariable("id") Long studentId) {
		SingleStudentDto singleStudentDto = adminService.getStudentById(studentId);
		if (singleStudentDto == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(singleStudentDto);
	}

	@PutMapping(path = { "/student/{id}" })
	public ResponseEntity<?> updateStudent(@RequestBody StudentDto studentDto, @PathVariable("id") Long studentId) {
		StudentDto sDto = adminService.updateStudent(studentDto, studentId);
		if (sDto == null)
			return ResponseEntity.ok("Something went Wrong");
		return ResponseEntity.status(HttpStatus.CREATED).body(sDto);
	}

	@PostMapping(path = { "/fee/{studentId}" })
	public ResponseEntity<?> payFee(@RequestBody FeeDto feeDto, @PathVariable Long studentId) {
		FeeDto payFeeDto = adminService.payFee(feeDto, studentId);
		if (payFeeDto == null)
			return ResponseEntity.ok("Something went Wrong");
		return ResponseEntity.status(HttpStatus.CREATED).body(payFeeDto);
	}

	@GetMapping("/leaves")
	public ResponseEntity<List<StudentLeaveDto>> getAllAppliedLeaves() {
		List<StudentLeaveDto> studentLeavesDto = adminService.getAllAppliedLeaves();
		if (studentLeavesDto == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(studentLeavesDto);
	}

	@GetMapping("/leave/{leaveId}/{status}")
	public ResponseEntity<?> changeLeaveStatus(@PathVariable Long leaveId, @PathVariable String status) {
		StudentLeaveDto changeLeaveDto = adminService.changeLeaveStatus(leaveId, status);
		if (changeLeaveDto == null) {
			return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(changeLeaveDto);
	}

	// Teacher Operation

	@PostMapping(path = { "/teacher" })
	public ResponseEntity<?> addTeacher(@RequestBody TeacherDto teacherDto) {
		TeacherDto beforeSaveDto = adminService.postTeacher(teacherDto);
		if (beforeSaveDto == null)
			return ResponseEntity.ok("Something went Wrong");
		return ResponseEntity.status(HttpStatus.CREATED).body(beforeSaveDto);
	}

	@GetMapping(value = { "/teachers" })
	public ResponseEntity<List<TeacherDto>> getAllTeacher() {
		List<TeacherDto> teacherDtos = adminService.getAllTeacher();
		return ResponseEntity.ok(teacherDtos);
	}

	@DeleteMapping("/teacher/{teacherId}")
	public ResponseEntity<Void> deleteTeacher(@PathVariable("id") Long teacherId) {
		adminService.deleteTeacher(teacherId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/teacher/{teacherId}")
	public ResponseEntity<SingleTeacherDto> getTeacherById(@PathVariable Long teacherId) {
		SingleTeacherDto singleTeacherDto = adminService.getTeacherById(teacherId);
		if (singleTeacherDto == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(singleTeacherDto);
	}

	@PutMapping(path = { "/teacher/{teacherId}" })
	public ResponseEntity<?> updateTeacher(@RequestBody TeacherDto teacherDto,
			@PathVariable("teacherId") Long teacherId) {
		TeacherDto upTeacherDto = adminService.updateTeacher(teacherDto, teacherId);
		if (upTeacherDto == null)
			return ResponseEntity.ok("Something went Wrong");
		return ResponseEntity.status(HttpStatus.CREATED).body(upTeacherDto);
	}
}
