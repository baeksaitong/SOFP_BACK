package baeksaitong.sofp.global.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class NonNullListValidator implements ConstraintValidator<NonNullList, List<?>> {

    @Override
    public boolean isValid(List<?> list, ConstraintValidatorContext context) {
        if (list == null) {
            return false; // Null list is not allowed
        }

        for (Object item : list) {
            if (item == null) {
                return false; // Null values in list are not allowed
            }
        }

        return true;
    }
}