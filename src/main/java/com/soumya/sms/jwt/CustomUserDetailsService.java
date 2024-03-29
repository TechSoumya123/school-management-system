package com.soumya.sms.jwt;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.soumya.sms.model.User;
import com.soumya.sms.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> emailfind = userRepository.findByEmail(username);
		if (emailfind.isEmpty())
			throw new UsernameNotFoundException("Username not found", null);
		return new org.springframework.security.core.userdetails.User(emailfind.get().getEmail(),
				emailfind.get().getPassword(), new ArrayList<>());
	}

}
