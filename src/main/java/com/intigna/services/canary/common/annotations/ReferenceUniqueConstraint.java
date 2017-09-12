/**
 * 
 */
package com.intigna.services.canary.common.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.intigna.services.canary.common.annotations.validator.ReferenceUniqueValidator;

/**
 * Reference Unique Entity Constraint, To Check uniqueness for combination of name and reference fields of each Entity.
 * @author Chris Lynn
 *
 */
@Target({java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ReferenceUniqueValidator.class)
public @interface ReferenceUniqueConstraint
{
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<?> entityClass();
    String message() default "{violation.name.unique}";
    
    String nameProperty() default "name";
    String referenceProperty();
}