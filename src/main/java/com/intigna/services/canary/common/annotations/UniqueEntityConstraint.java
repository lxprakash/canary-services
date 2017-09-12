package com.intigna.services.canary.common.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.intigna.services.canary.common.annotations.validator.UniqueEntityValidator;

@Target({java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = UniqueEntityValidator.class)
public @interface UniqueEntityConstraint
{
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<?> entityClass();
    String message() default "";

    String nameProperty() default "name";
    String nameMessage() default "{violation.name.unique}";
    String secondProperty() default "businessCode";
    String secondPropertyMessage() default "{violation.businesscode.unique}";
}