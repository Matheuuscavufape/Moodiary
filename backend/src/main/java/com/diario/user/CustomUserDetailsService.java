package com.diario.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  @Autowired private UserRepository repo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User u = repo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    List<GrantedAuthority> roles = List.of(new SimpleGrantedAuthority(u.getRole()));
    return new org.springframework.security.core.userdetails.User(u.getEmail(), u.getPasswordHash(), roles);
  }
}
