package com.ashwetaw.dto.validator;

import com.ashwetaw.config.customannotation.ValidEmail;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.regex.Pattern;


public class EmailValidator implements ConstraintValidator<ValidEmail,String>{
    /**
     * valid email regex pattern from OWASP
     * @link {https://owasp.org/www-community/OWASP_Validation_Regex_Repository}
     */
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private Pattern pattern;
    @Override
    public void initialize(ValidEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return this.validateEmail(email);
    }

    private boolean validateEmail(String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        return StringUtils.isEmpty(email) || Arrays.stream(email.trim().split(",")).allMatch(addr ->  pattern.matcher(addr.trim()).matches());
    }
}
