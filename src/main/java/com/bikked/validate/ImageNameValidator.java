package com.bikked.validate;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.logging.Logger;


@Slf4j
public class ImageNameValidator implements ConstraintValidator<ImageNameValid,String>
{

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        log.info("Message from isValid : {}",value);

        //logic

        if (value.isBlank()) {
            return false;
        }else {
            return true;
        }

    }
}
