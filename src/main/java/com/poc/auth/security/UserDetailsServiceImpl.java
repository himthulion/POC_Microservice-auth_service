package com.poc.auth.security;

import java.util.List;

import com.poc.auth.dao.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService  {
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		com.poc.auth.entity.User appUser = userDao.findByEmail(username);
		if(appUser != null){
			List<GrantedAuthority> grantedAuthorities = AuthorityUtils
								.commaSeparatedStringToAuthorityList("ROLE_" + appUser.getRole());
			return new User (
				appUser.getEmail(), 
				encoder.encode(appUser.getPassword()), grantedAuthorities);
		}

		throw new UsernameNotFoundException("Username: " + username + " not found");
	}
}