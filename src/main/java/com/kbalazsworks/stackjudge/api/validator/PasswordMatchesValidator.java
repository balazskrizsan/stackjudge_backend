package com.kbalazsworks.stackjudge.api.validator;

import com.kbalazsworks.stackjudge.api.dto.UserRegistrationForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserRegistrationForm>
{

    @Override
    public boolean isValid(final UserRegistrationForm user, final ConstraintValidatorContext context)
    {
        return user.getPassword().equals(user.getMatchingPassword());
    }

}
