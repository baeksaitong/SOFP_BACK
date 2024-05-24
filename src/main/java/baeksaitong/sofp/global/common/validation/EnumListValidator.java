package baeksaitong.sofp.global.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class EnumListValidator implements ConstraintValidator<ValidEnum, List<String>> {

    private ValidEnum annotation;

    @Override
    public void initialize(ValidEnum annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(List<String> valueListForValidation, ConstraintValidatorContext context) {
        if (valueListForValidation == null) {
            return true;
        }

        for (String valueForValidation : valueListForValidation) {
            if (!isValidEnumValue(valueForValidation)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidEnumValue(String valueForValidation) {
        if (valueForValidation == null) {
            return false;
        }

        Object[] enumValues = this.annotation.enumClass().getEnumConstants();
        for (Object enumValue : enumValues) {
            if (valueForValidation.equals(enumValue.toString())
                    || ((Enum<?>) enumValue).name().equals(valueForValidation)
                    || this.annotation.ignoreCase() && valueForValidation.equalsIgnoreCase(enumValue.toString())
                    || this.annotation.ignoreCase() && ((Enum<?>) enumValue).name().equalsIgnoreCase(valueForValidation)) {
                return true;
            }
        }
        return false;
    }
}