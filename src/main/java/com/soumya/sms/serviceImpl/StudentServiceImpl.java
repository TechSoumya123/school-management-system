package com.soumya.sms.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import org.springframework.stereotype.Service;

import com.soumya.sms.dto.SingleStudentDto;
import com.soumya.sms.dto.StudentDto;
import com.soumya.sms.dto.StudentLeaveDto;
import com.soumya.sms.model.StudentLeave;
import com.soumya.sms.model.User;
import com.soumya.sms.repository.StudentLeaveRepository;
import com.soumya.sms.repository.UserRepository;
import com.soumya.sms.service.StudentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
	private final UserRepository userRepository;

	private final StudentLeaveRepository studentLeaveRepository;

	@Override
	public SingleStudentDto getStudentById(Long studentId) {
		Optional<User> userOptional = userRepository.findById(studentId);
		SingleStudentDto singleStudentDto = new SingleStudentDto();
		userOptional.ifPresent(u -> singleStudentDto.setStudentDto(u.getStudentDto()));
		return singleStudentDto;

	}

	@Override
	public StudentLeaveDto applyLeave(StudentLeaveDto studentLeaveDto) {
		Optional<User> applyOptional = userRepository.findById(studentLeaveDto.getUserId());
		if (applyOptional.isPresent()) {
			User user = applyOptional.get();
			StudentLeave sLeave = new StudentLeave();
			BeanUtils.copyProperties(studentLeaveDto, sLeave);
			StudentLeave studentLeave = studentLeaveRepository.save(sLeave);
			StudentLeaveDto sLeaveDto = new StudentLeaveDto();
			sLeaveDto.setId(studentLeave.getId());
			return sLeaveDto;
		}
		return null;
	}

	@Override
	public List<StudentLeaveDto> getAllAppliedLeavesByStudentId(Long studentId) {

		return studentLeaveRepository.findAllByUserId(studentId).stream().map(StudentLeave::getStudentLeaveDto)
				.collect(Collectors.toList());
	}

	@Override
	public StudentDto updateStudent(StudentDto studentDto, Long studentId) {
		Optional<User> optionalUserOptional = userRepository.findById(studentId);
		if (optionalUserOptional.isPresent()) {
			User updatedUser = optionalUserOptional.get();
			BeanUtils.copyProperties(studentDto, updatedUser);
			User updatedStudent = userRepository.save(updatedUser);
			StudentDto upStudentDto = new StudentDto();
			return upStudentDto;
		}
		return null;
	}

}
