package com.mikepaskual.delivery.shared.init;

import com.mikepaskual.delivery.user.dto.CreateUserRequest;
import com.mikepaskual.delivery.user.model.User;
import com.mikepaskual.delivery.user.model.UserRole;
import com.mikepaskual.delivery.user.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataSeed {

    @Autowired
    private UserService userService;

    public DataSeed(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        CreateUserRequest adminRequest = CreateUserRequest.builder()
                .setEmail("admin@delivery.edu")
                .setPassword("1234")
                .setUsername("admin")
                .setVerifyPassword("1234")
                .build();
        User admin = userService.registerUser(adminRequest);
        userService.changeRole(admin, UserRole.ADMIN);

        CreateUserRequest userRequest = CreateUserRequest.builder()
                .setEmail("user@delivery.edu")
                .setPassword("1234")
                .setUsername("user")
                .setVerifyPassword("1234")
                .build();
        User user = userService.registerUser(userRequest);
    }
}
