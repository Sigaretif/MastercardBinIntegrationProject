package com.org.wortel.mastercardbin.infrastructure.api.internal.dto.transactionaggregate.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

public class DateBeforeValidator implements ConstraintValidator<DateBefore, Object> {

    private String fromFieldName;
    private String toFieldName;
    private String message;

    @Override
    public void initialize(DateBefore constraintAnnotation) {
        this.fromFieldName = constraintAnnotation.from();
        this.toFieldName = constraintAnnotation.to();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (ObjectUtils.isEmpty(value)) {
                return true;
            }

            Field fromField = value.getClass().getDeclaredField(fromFieldName);
            fromField.setAccessible(true);
            LocalDateTime fromDate = (LocalDateTime) fromField.get(value);

            Field toField = value.getClass().getDeclaredField(toFieldName);
            toField.setAccessible(true);
            LocalDateTime toDate = (LocalDateTime) toField.get(value);

            if (ObjectUtils.isEmpty(fromDate) || ObjectUtils.isEmpty(toDate)) {
                return true;
            }

            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(String.format(message, fromFieldName, toFieldName))
                    .addConstraintViolation();

            return fromDate.isEqual(toDate) || toDate.isAfter(fromDate);
        }
        catch (Exception e) {
            throw new IllegalStateException("Error parsing date: " + e.getMessage());
        }
    }
}
