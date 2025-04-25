package com.mikepaskual.delivery.user.service;

import com.mikepaskual.delivery.user.model.*;
import com.mikepaskual.delivery.user.dto.CreateUserRequest;
import com.mikepaskual.delivery.user.dto.UpdateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public User registerUser(CreateUserRequest request) {
        Set<Role> roles = request.getRoles().stream()
                .map(roleName -> roleRepository.save(Role.builder()
                         .setName(roleName).build()))
                .collect(Collectors.toSet());
        User user = User.builder()
                .setCreatedAt(request.getCreatedAt())
                .setEmail(request.getEmail())
                .setPassword(passwordEncoder.encode(request.getPassword()))
                .setRoles(roles)
                .setUsername(request.getUsername()).build();
        return userRepository.save(user);
    }

    public User updateUser(Long userId, UpdateUserRequest request) {
        User user = findUser(userId);
        user.setBirthday(request.getBirthday());
        user.setFirstName(request.getFirstName());
        user.setGender(Gender.valueOf(request.getGender()));
        user.setPhone(request.getPhone());
        user.setLastName(request.getLastName());
        return userRepository.save(user);
    }

    public User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
    }

}
