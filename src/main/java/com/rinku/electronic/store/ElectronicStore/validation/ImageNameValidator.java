package com.rinku.electronic.store.ElectronicStore.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageNameValidator implements ConstraintValidator<ImageNameValid,String> {
    private Logger logger= LoggerFactory.getLogger(ImageNameValidator.class);
    public boolean isValid(String value, ConstraintValidatorContext context) {
        logger.info("Message from isValid: {} ");
        if(value.isBlank())
    {
        return false;
    }
    else{
        return true;
    }
}}

