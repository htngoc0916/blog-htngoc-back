package com.htn.blog.security.custom;

import com.htn.blog.common.MessageKeys;
import com.htn.blog.entity.User;
import com.htn.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException(MessageKeys.USER_NOT_FOUND_EMAIL + ": " + email)
        );
        return CustomUserDetailsServiceImpl.build(user);
    }
}
