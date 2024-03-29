package com.soumya.sms.dto;

import java.util.Date;

import com.soumya.sms.enums.StudentLeaveStatus;

import lombok.Data;

@Data
public class StudentLeaveDto {
	
	private Long id;
	private String subject;
	private String body;
	private Date date;
	private StudentLeaveStatus studentLeaveStatus;
	private Long userId;

}
