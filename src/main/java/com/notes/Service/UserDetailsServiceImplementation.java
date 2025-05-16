package com.notes.Service;

import com.notes.Models.UserModel;
import com.notes.Repository.UsersRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class UserDetailsServiceImplementation{

    @Bean
    public UserDetailsService userDetailsService(UsersRepository usersRepository) {
        return username -> {
            UserModel user = usersRepository.findByLogin(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            return User.builder()
                    .username(user.getLogin())
                    .password(user.getPassword()) // already encoded in registerUser
                    .roles("USER")
                    .build();
        };
    }
}