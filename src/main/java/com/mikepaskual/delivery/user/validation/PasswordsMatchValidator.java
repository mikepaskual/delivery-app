package com.mikepaskual.delivery.user.validation;

import com.mikepaskual.delivery.user.dto.CreateUserRequest;
import com.mikepaskual.delivery.user.model.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, CreateUserRequest> {

    @Override
    public boolean isValid(CreateUserRequest userDTO, ConstraintValidatorContext context) {
        boolean isEquals = userDTO.getPassword().equals(userDTO.getVerifyPassword());
        if (!isEquals) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("verifyPassword")
                    .addConstraintViolation();
        }
        return isEquals;
    }
}
