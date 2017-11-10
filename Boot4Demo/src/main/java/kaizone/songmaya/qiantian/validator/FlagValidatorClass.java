package kaizone.songmaya.qiantian.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FlagValidatorClass implements ConstraintValidator<FlagValidator, Object> {

    private String values;

    @Override
    public void initialize(FlagValidator flagValidator) {
        this.values = flagValidator.values();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        String[] value_array = values.split(",");
        boolean isFlag = false;
        for (int i = 0; i < value_array.length; i++) {
            if (value_array[i].equals(o)) {
                isFlag = true;
                break;
            }
        }
        return isFlag;
    }
}
