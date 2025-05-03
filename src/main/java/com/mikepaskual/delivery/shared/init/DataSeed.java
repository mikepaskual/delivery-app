package com.mikepaskual.delivery.shared.init;

import com.mikepaskual.delivery.driver.model.Driver;
import com.mikepaskual.delivery.driver.model.DriverRepository;
import com.mikepaskual.delivery.user.exception.RoleNotFoundException;
import com.mikepaskual.delivery.user.exception.UserNotFoundException;
import com.mikepaskual.delivery.user.model.*;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Component
public class DataSeed {

    @Autowired
    private final DriverRepository driverRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final UserRepository userRepository;

    public DataSeed(DriverRepository driverRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository,
                    UserRepository userRepository) {
        this.driverRepository = driverRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        loading_roles();
        loading_admin();
        loading_driver();
    }

    private void loading_roles() {
        Arrays.stream(UserRole.values())
                .forEach(role -> roleRepository.save(Role.builder().setName(role.name()).build()));
    }

    @SuppressWarnings("unchecked")
    private void loading_admin() {
        Role adminRole = roleRepository.findByName(UserRole.ADMIN.name())
                .orElseThrow(() -> new RoleNotFoundException(UserRole.ADMIN.name()));
        userRepository.save(User.builder()
                .setCreatedAt(LocalDateTime.now())
                .setEmail("admin@deliveryapp.edu")
                .setPassword(passwordEncoder.encode("P@ssw0rd"))
                .setRoles(Set.of(adminRole))
                .setUsername("admin").build());
    }

    @SuppressWarnings("unchecked")
    private void loading_driver() {
        Role driverRole = roleRepository.findByName(UserRole.DRIVER.name())
                .orElseThrow(() -> new RoleNotFoundException(UserRole.DRIVER.name()));
        User user = User.builder()
                .setCreatedAt(LocalDateTime.now())
                .setEmail("driver@deliveryapp.edu")
                .setPassword(passwordEncoder.encode("P@ssw0rd"))
                .setRoles(Set.of(driverRole))
                .setUsername("driver").build();

        Driver driver = Driver.builder()
                .setAvailableFrom(LocalTime.of(14, 0))
                .setAvailableTo(LocalTime.of(18, 0))
                .setLicenseNumber(LicenseNumberGenerator.generateLicenseNumber())
                .setUser(user).build();
        driverRepository.save(driver);
    }

}
