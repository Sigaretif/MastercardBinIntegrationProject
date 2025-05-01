package com.org.wortel.mastercardbin.infrastructure.api.internal.dto.transactionaggregate.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateBeforeValidator.class)
public @interface DateBefore {

    String message() default "Date in field %s must be before or equal to date in field %s";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String from();
    String to();
}
