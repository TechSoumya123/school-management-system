package com.soumya.sms.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.soumya.sms.dto.AuthenticationRequest;
import com.soumya.sms.dto.AuthenticationResponse;
import com.soumya.sms.jwt.CustomUserDetailsService;
import com.soumya.sms.model.User;
import com.soumya.sms.repository.UserRepository;
import com.soumya.sms.utils.JwtUtils;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(path = "/check")
public class AuthenticationController {

	private final AuthenticationManager authenticationManager;

	private final JwtUtils jwtUtils;

	private final CustomUserDetailsService customUserDetailsService;

	private final UserRepository userRepository;

	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";

	@PostMapping("/authenticate")
	public AuthenticationResponse createAuthenticationToken(
			@RequestBody(required = true) AuthenticationRequest authRequest, HttpServletResponse response)
			throws IOException, JsonParseException {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
		} catch (BadCredentialsException ex) {
			throw new BadCredentialsException("Incorrect Username or password");
		} catch (DisabledException ex) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "User is not created");
			return null;
		}

		final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getEmail());
		Optional<User> findEmail = userRepository.findByEmail(userDetails.getUsername());
		final String jwt = jwtUtils.getGeneratedToken(userDetails.getUsername());

		var authenticationResponse = new AuthenticationResponse();

		if (findEmail.isPresent()) {
			// authenticationResponse.setJwtToken(jwt);
			authenticationResponse.setUserId(findEmail.get().getId());
			authenticationResponse.setUserRole(findEmail.get().getRole());

		}

		response.setHeader("Access-Control-Expose-Headers", "Authorization");
		response.setHeader("Access-Control-Allow-Headers",
				"Authorization,X-Pingother,Origin,X-Requested-With,Content-Type,Accept,X-Custom-header");
		response.setHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
		return authenticationResponse;
	}
}
