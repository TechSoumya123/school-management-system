package com.soumya.sms.dto;

import com.soumya.sms.enums.UserRole;

import lombok.Data;

@Data
public class AuthenticationResponse {

	//private String jwtToken;j

	private Long userId;

	private UserRole userRole;

}
