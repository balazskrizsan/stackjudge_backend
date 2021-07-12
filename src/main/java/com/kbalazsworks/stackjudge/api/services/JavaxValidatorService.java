package com.kbalazsworks.stackjudge.api.services;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.*;
import java.util.ArrayList;
import java.util.Set;

@Service
@Slf4j
public class JavaxValidatorService<T>
{
    public void validateWithConsoleLog(@NonNull T entity)
    {
        ValidatorFactory factory   = Validation.buildDefaultValidatorFactory();
        Validator        validator = factory.getValidator();

        Set<ConstraintViolation<T>> violations = validator.validate(entity);

        for (ConstraintViolation<T> violation : violations)
        {
            log.info("Validation error: " + violation.getMessage());
        }
    }

    public void validate(@NonNull T request)
    {
        ValidatorFactory factory   = Validation.buildDefaultValidatorFactory();
        Validator        validator = factory.getValidator();

        Set<ConstraintViolation<T>> violations = validator.validate(request);

        ArrayList<ValidationException> validationException = new ArrayList<>();

        for (ConstraintViolation<T> violation : violations)
        {
            validationException.add(new ValidationException(violation.getMessage()));
        }

        new RecursiveValidationExceptionBuilder().buildAndThrow(validationException);
    }

    public void arrayValidateWithConsoleLog(ArrayList<T> entities)
    {
        for (T entity : entities)
        {
            validateWithConsoleLog(entity);
        }
    }
}
