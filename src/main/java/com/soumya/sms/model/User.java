package com.soumya.sms.model;

import java.util.Date;

import com.soumya.sms.dto.StudentDto;
import com.soumya.sms.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(name = "users", schema = "sms_db", uniqueConstraints = @UniqueConstraint(columnNames = { "email" }))
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	private String name;

	private String email;

	private String password;

	private String fatherName;

	private String motherName;

	private String studentClass;

	private Date dob;

	private String address;

	private String gender;

	private UserRole role;
	
	public StudentDto getStudentDto() {
		StudentDto studentDto=new StudentDto();
		studentDto.setId(id);
		studentDto.setName(name);
		studentDto.setEmail(email);
		studentDto.setAddress(address);
		studentDto.setDob(dob);
		studentDto.setStudentClass(studentClass);
		studentDto.setGender(gender);
		studentDto.setFatherName(fatherName);
		studentDto.setMotherName(motherName);
		setPassword(password);
		return studentDto;
	}

}
