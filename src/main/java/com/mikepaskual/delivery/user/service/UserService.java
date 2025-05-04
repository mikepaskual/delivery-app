package com.mikepaskual.delivery.user.service;

import com.mikepaskual.delivery.driver.model.Driver;
import com.mikepaskual.delivery.driver.model.DriverRepository;
import com.mikepaskual.delivery.user.exception.RoleNotFoundException;
import com.mikepaskual.delivery.user.exception.UserNotFoundException;
import com.mikepaskual.delivery.user.model.*;
import com.mikepaskual.delivery.user.dto.CreateUserRequest;
import com.mikepaskual.delivery.user.dto.UpdateUserRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RoleNotFoundException(roleName)))
                .collect(Collectors.toSet());

        User user = userRepository.save(User.builder()
                .setCreatedAt(LocalDateTime.now())
                .setEmail(request.getEmail())
                .setPassword(passwordEncoder.encode(request.getPassword()))
                .setRoles(roles)
                .setUsername(request.getUsername()).build());

        if (roles.stream().anyMatch(role -> UserRole.DRIVER.name().equals(role.getName()))) {
            driverRepository.save(Driver.builder().setUser(user).build());
        }
        if (roles.stream().anyMatch(role -> UserRole.CUSTOMER.name().equals(role.getName()))) {
            // TODO
        }

        return user;
    }

    public boolean isIncomplete(Long userId) {
        User user = getUserOrThrow(userId);
        return user.getFirstName() == null || user.getLastName() == null || user.getPhone() == null
                || user.getBirthday() == null || user.getGender() == null;
    }

    public boolean isCurrentPassword(Long userId, String currentPassword) {
        return passwordEncoder.matches(currentPassword, getUserOrThrow(userId).getPassword());
    }

    public void updatePassword(Long userId, String newPassword) {
        User user = getUserOrThrow(userId);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public User getUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    public User updateUser(Long userId, UpdateUserRequest request) {
        User user = getUserOrThrow(userId);
        user.setBirthday(request.getBirthday());
        user.setFirstName(request.getFirstName());
        user.setGender(Gender.valueOf(request.getGender()));
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        return userRepository.save(user);
    }

}
