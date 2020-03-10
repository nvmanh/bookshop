package com.manhnv.error.validator;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.manhnv.utils.TextUtils;

public class EmailValidator implements ConstraintValidator<Email, String> {
	private Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return !TextUtils.isEmpty(value) && pattern.matcher(value).matches();
	}
}
