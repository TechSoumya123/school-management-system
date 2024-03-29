package com.soumya.sms.model;

import java.util.Date;

import com.soumya.sms.dto.TeacherDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "teachers")
public class Teacher {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String teacherName;
	private String gender;
	private String qualification;
	private String department;
	private Date date;
	private String address;
	
	public TeacherDto getTeacherDto() {
		TeacherDto teacherDto=new TeacherDto();
		teacherDto.setId(id);
		teacherDto.setTeacherName(teacherName);
		teacherDto.setGender(gender);
		teacherDto.setAddress(address);
		teacherDto.setDepartment(department);		
		teacherDto.setQualification(qualification);
		teacherDto.setDate(date);		
		return teacherDto;
	}

}
