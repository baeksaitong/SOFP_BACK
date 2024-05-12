package baeksaitong.sofp.global.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

    private ValidEnum annotation;

    @Override
    public void initialize(ValidEnum annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(String valueForValidation, ConstraintValidatorContext context) {
        boolean result = false;
        Object[] enumValues = this.annotation.enumClass().getEnumConstants();
        if (valueForValidation == null) {
            return false;
        }

        for (Object enumValue : enumValues) {
            if (valueForValidation.equals(enumValue.toString())
                    || ((Enum<?>) enumValue).name().equals(valueForValidation)
                    || this.annotation.ignoreCase() && valueForValidation.equalsIgnoreCase(enumValue.toString())
                    || this.annotation.ignoreCase() && ((Enum<?>) enumValue).name().equalsIgnoreCase(valueForValidation)
            ) {
                result = true;
                break;
            }
        }

        return result;
    }
}