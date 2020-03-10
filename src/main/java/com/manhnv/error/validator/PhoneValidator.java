package com.manhnv.error.validator;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.manhnv.utils.TextUtils;

public class PhoneValidator implements ConstraintValidator<Phone, String> {

	private static final Pattern GLOBAL_PHONE_NUMBER_PATTERN = Pattern.compile("^(\\+)?[0-9]\\d{8,12}$");

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (TextUtils.isEmpty(value))
			return true;
		return GLOBAL_PHONE_NUMBER_PATTERN.matcher(value).matches();
	}

}
