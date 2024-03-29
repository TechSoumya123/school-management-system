package com.soumya.sms.serviceImpl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.soumya.sms.dto.FeeDto;
import com.soumya.sms.dto.SingleStudentDto;
import com.soumya.sms.dto.SingleTeacherDto;
import com.soumya.sms.dto.StudentDto;
import com.soumya.sms.dto.StudentLeaveDto;
import com.soumya.sms.dto.TeacherDto;
import com.soumya.sms.enums.StudentLeaveStatus;
import com.soumya.sms.enums.UserRole;
import com.soumya.sms.model.Fee;
import com.soumya.sms.model.StudentLeave;
import com.soumya.sms.model.Teacher;
import com.soumya.sms.model.User;
import com.soumya.sms.repository.FeeRepository;
import com.soumya.sms.repository.StudentLeaveRepository;
import com.soumya.sms.repository.TeacherRepository;
import com.soumya.sms.repository.UserRepository;
import com.soumya.sms.service.AdminService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final FeeRepository feeRepository;
	private final StudentLeaveRepository studentLeaveRepository;
	private final TeacherRepository teacherRepository;

	@PostConstruct
	public void createAdminAccount() {
		User adminAccount = userRepository.findByRole(UserRole.ADMIN);
		if (adminAccount == null) {
			User admin = new User();
			admin.setEmail("admin@test.com");
			admin.setName("admin");
			admin.setRole(UserRole.ADMIN);
			admin.setPassword(passwordEncoder.encode("admin"));
			userRepository.save(admin);
		}

	}

	@Override
	public StudentDto postStudent(StudentDto studentDto) {
		Optional<User> user = userRepository.findByEmail(studentDto.getEmail());
		if (user.isEmpty()) {
			User saveUser = new User();
			BeanUtils.copyProperties(studentDto, saveUser);
			saveUser.setPassword(passwordEncoder.encode(studentDto.getPassword()));
			saveUser.setRole(UserRole.STUDENT);
			userRepository.save(saveUser);
			var createdStudentDto = new StudentDto();
			createdStudentDto.setId(createdStudentDto.getId());
			createdStudentDto.setEmail(createdStudentDto.getEmail());
			return createdStudentDto;
		}
		return null;
	}

	@Override
	public List<StudentDto> getAllStudents() {
		return userRepository.findAllByRole(UserRole.STUDENT).stream().map(User::getStudentDto)
				.collect(Collectors.toList());

	}

	@Override
	public void deleteStudent(Long studentId) {
		userRepository.deleteById(studentId);
	}

	@Override
	public SingleStudentDto getStudentById(Long studentId) {
		Optional<User> userOptional = userRepository.findById(studentId);
		SingleStudentDto singleStudentDto = new SingleStudentDto();
		userOptional.ifPresent(u -> singleStudentDto.setStudentDto(u.getStudentDto()));
		return singleStudentDto;

	}

	@Override
	public StudentDto updateStudent(StudentDto studentDto, Long studentId) {
		Optional<User> optionalUserOptional = userRepository.findById(studentId);
		if (optionalUserOptional.isPresent()) {
			User updatedUser = optionalUserOptional.get();
			BeanUtils.copyProperties(updatedUser, studentDto);
			User updatedStudent = userRepository.save(updatedUser);
			StudentDto upStudentDto = new StudentDto();
			upStudentDto.setId(updatedStudent.getId());
			return upStudentDto;
		}
		return null;
	}

	@Override
	public FeeDto payFee(FeeDto feeDto, Long studentId) {
		Optional<User> validStudent = userRepository.findById(studentId);
		if (validStudent.isPresent()) {
			Fee fee = new Fee();
			BeanUtils.copyProperties(feeDto, fee);
			Fee paidFee = feeRepository.save(fee);
			FeeDto paidFeeDto = new FeeDto();
			paidFeeDto.setId(paidFee.getId());
			return paidFeeDto;
		}
		return null;
	}

	@Override
	public List<StudentLeaveDto> getAllAppliedLeaves() {

		return studentLeaveRepository.findAll().stream().map(StudentLeave::getStudentLeaveDto)
				.collect(Collectors.toList());
	}

	@Override
	public StudentLeaveDto changeLeaveStatus(Long leaveId, String status) {
		Optional<StudentLeave> optionalStudentLeave = studentLeaveRepository.findById(leaveId);
		if (optionalStudentLeave.isPresent()) {
			StudentLeave studentLeave = optionalStudentLeave.get();
			if (Objects.equals(status, "Approved")) {
				studentLeave.setStudentLeaveStatus(StudentLeaveStatus.APPROVED);
			} else {
				studentLeave.setStudentLeaveStatus(StudentLeaveStatus.DISAPPROVED);
			}
			StudentLeave updatedLeave = studentLeaveRepository.save(studentLeave);
			StudentLeaveDto uLeaveDto = new StudentLeaveDto();
			uLeaveDto.setId(updatedLeave.getId());
			return uLeaveDto;
		}
		return null;
	}

	@Override
	public TeacherDto postTeacher(TeacherDto teacherDto) {
		Teacher teacher = new Teacher();
		BeanUtils.copyProperties(teacherDto, teacher);
		Teacher saveTeacher = teacherRepository.save(teacher);
		TeacherDto createdTeacherDto = new TeacherDto();
		createdTeacherDto.setId(saveTeacher.getId());
		return createdTeacherDto;
	}

	@Override
	public List<TeacherDto> getAllTeacher() {
		return teacherRepository.findAll().stream().map(Teacher::getTeacherDto).collect(Collectors.toList());
	}

	@Override
	public void deleteTeacher(Long teacherId) {
		teacherRepository.deleteById(teacherId);
	}

	@Override
	public SingleTeacherDto getTeacherById(Long teacherId) {
		Optional<Teacher> optionalTeacherOptional = teacherRepository.findById(teacherId);
		if (optionalTeacherOptional.isPresent()) {

			SingleTeacherDto singleTeacherDto = new SingleTeacherDto();
			singleTeacherDto.setTeacherDto(optionalTeacherOptional.get().getTeacherDto());
			return singleTeacherDto;
		}
		return null;
	}

	@Override
	public TeacherDto updateTeacher(TeacherDto teacherDto, Long teacherId) {
		Optional<Teacher> optionalTeacherOptional = teacherRepository.findById(teacherId);
		if (optionalTeacherOptional.isPresent()) {
			Teacher updateTeacher = optionalTeacherOptional.get();
			updateTeacher.setTeacherName(teacherDto.getTeacherName());
			updateTeacher.setGender(teacherDto.getGender());
			updateTeacher.setAddress(teacherDto.getAddress());
			updateTeacher.setDate(teacherDto.getDate());
			updateTeacher.setDepartment(teacherDto.getDepartment());
			updateTeacher.setQualification(teacherDto.getQualification());
			Teacher upDtoTeacher = teacherRepository.save(updateTeacher);
			TeacherDto newUpDto = new TeacherDto();
			newUpDto.setId(upDtoTeacher.getId());
			return newUpDto;
		}
		return null;
	}

}
