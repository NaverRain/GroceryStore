package com.naverrain.grocery.core.utils.impl;

import com.naverrain.grocery.core.dto.UserDto;
import com.naverrain.grocery.core.utils.PasswordMatches;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserDto> {

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext constraintValidatorContext) {
        if (userDto.getPassword() == null || userDto.getRepeatPassword() == null){
            return false;
        }
        return userDto.getPassword().equals(userDto.getRepeatPassword());
    }
}
