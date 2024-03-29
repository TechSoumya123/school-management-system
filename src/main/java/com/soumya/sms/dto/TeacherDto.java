package com.soumya.sms.dto;

import java.util.Date;

import lombok.Data;

@Data
public class TeacherDto {

	private Long id;
	private String teacherName;
	private String gender;
	private String qualification;
	private String department;
	private Date date;
	private String address;

}
