package com.soumya.sms.dto;

import java.util.Date;

import lombok.Data;

@Data
public class StudentDto {

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
}
