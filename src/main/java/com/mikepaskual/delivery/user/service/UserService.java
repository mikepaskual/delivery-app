package com.mikepaskual.delivery.user.service;

import com.mikepaskual.delivery.driver.model.Driver;
import com.mikepaskual.delivery.driver.model.DriverRepository;
import com.mikepaskual.delivery.user.model.*;
import com.mikepaskual.delivery.user.dto.CreateUserRequest;
import com.mikepaskual.delivery.user.dto.UpdateUserRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private final DriverRepository driverRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final UserRepository userRepository;

    public UserService(DriverRepository driverRepository, PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository, UserRepository userRepository) {
        this.driverRepository = driverRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public User registerUser(CreateUserRequest request) {
        Set<Role> roles = request.getRoles().stream()
                .map(roleName -> Role.builder()
                        .setName(roleName).build())
                .collect(Collectors.toSet());
        roleRepository.saveAll(roles);

        User user = userRepository.save(User.builder()
                .setCreatedAt(request.getCreatedAt())
                .setEmail(request.getEmail())
                .setPassword(passwordEncoder.encode(request.getPassword()))
                .setRoles(roles)
                .setUsername(request.getUsername()).build());

        if (roles.stream().anyMatch(role -> UserRole.DRIVER.name().equals(role.getName()))) {
            driverRepository.save(Driver.builder()
                    .setUser(user).build());
        }
        return user;
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
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "redirect:/error/not-found"));
    }

}
