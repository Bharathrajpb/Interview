package com.c4l.fileUploader.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile>  {


	
    @Override
    public void initialize(ValidFile constraintAnnotation) {

    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
         log.info("FileValidator isValid for multipartvalidation");
    	
        boolean result = true;

        String contentType = multipartFile.getContentType();
        if (!isSupportedContentType(contentType)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Only Text files are allowed")
                   .addConstraintViolation();
            result = false;
        }

        if (multipartFile.isEmpty())  {
        	context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "File is Empty")
                   .addConstraintViolation();
            result = false;
        }
        
        return result;
    }

    private boolean isSupportedContentType(String contentType) {
    	log.info("FileValidator isSupportedContentType Checking contenttype");
        return contentType.equals("text/plain");
    }	
	
    
    
}
