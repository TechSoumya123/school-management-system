package com.soumya.sms.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soumya.sms.dto.SingleStudentDto;
import com.soumya.sms.dto.StudentDto;
import com.soumya.sms.dto.StudentLeaveDto;
import com.soumya.sms.service.StudentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

	private final StudentService studentService;

	@GetMapping("/{studentId}")
	public ResponseEntity<SingleStudentDto> getStudentById(@PathVariable Long studentId) {
		SingleStudentDto singleStudentDto = studentService.getStudentById(studentId);
		if (singleStudentDto == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(singleStudentDto);
	}

	@PostMapping("/leave")
	public ResponseEntity<?> applyLeave(@RequestBody StudentLeaveDto studentLeaveDto) {
		StudentLeaveDto submitteStudentLeaveDto = studentService.applyLeave(studentLeaveDto);
		if (submitteStudentLeaveDto == null)
			return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
		return ResponseEntity.status(HttpStatus.CREATED).body(submitteStudentLeaveDto);
	}

	@GetMapping("/leave/{studentId}")
	public ResponseEntity<List<StudentLeaveDto>> getAllAppliedLeavesByStudentId(@PathVariable Long studentId) {
		List<StudentLeaveDto> studentLeavesDto = studentService.getAllAppliedLeavesByStudentId(studentId);
		if (studentLeavesDto == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(studentLeavesDto);
	}

	@PutMapping(path = { "/{studentId}" })
	public ResponseEntity<?> updateStudent(@RequestBody StudentDto studentDto, @PathVariable("id") Long studentId) {
		StudentDto sDto = studentService.updateStudent(studentDto, studentId);
		if (sDto == null)
			return ResponseEntity.ok("Something went Wrong");
		return ResponseEntity.status(HttpStatus.CREATED).body(sDto);
	}

}
