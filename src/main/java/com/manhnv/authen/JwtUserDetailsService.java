package com.manhnv.authen;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.manhnv.user.IUserRepository;
import com.manhnv.utils.TextUtils;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired(required = true)
	private IUserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (TextUtils.isEmpty(username) || !userRepository.existsByName(username)) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		com.manhnv.entity.User user = userRepository.findByName(username);
		return new User(user.getName(), user.getPassword(), new ArrayList<>());
	}

	public UserDetails login(String username, String password) {
		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || !userRepository.existsByName(username)) {
			return null;
		}
		com.manhnv.entity.User user = userRepository.findByName(username);
		if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
			return null;
		}
		return new User(user.getName(), password, new ArrayList<>());
	}
}