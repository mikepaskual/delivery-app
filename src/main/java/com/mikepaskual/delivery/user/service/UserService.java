package com.mikepaskual.delivery.user.service;

import com.mikepaskual.delivery.user.dto.CreateUserRequest;
import com.mikepaskual.delivery.user.model.User;
import com.mikepaskual.delivery.user.model.UserRepository;
import com.mikepaskual.delivery.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(CreateUserRequest request) {
        User user = User.builder()
            .setEmail(request.getEmail())
            .setPassword(passwordEncoder.encode(request.getPassword()))
            .setRole(UserRole.USER)
            .setUsername(request.getUsername()).build();
        return userRepository.save(user);
    }

    public User changeRole(User user, UserRole userRole) {
        user.setRole(userRole);
        return userRepository.save(user);
    }

    public User changeRole(Long userId, UserRole userRole) {
        return userRepository.findById(userId)
                .map(u -> {
                    u.setRole(userRole);
                    return userRepository.save(u);
                }).orElse(null);
    }

    public List<User> findAll() {
        return userRepository.findAll(Sort.by("username"));
    }

}
