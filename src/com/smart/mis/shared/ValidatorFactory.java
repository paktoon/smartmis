package com.smart.mis.shared;

import com.smartgwt.client.widgets.form.validator.FloatRangeValidator;
import com.smartgwt.client.widgets.form.validator.IntegerRangeValidator;
import com.smartgwt.client.widgets.form.validator.Validator;

public class ValidatorFactory {

	public static Validator integerRange(Integer lower, Integer upper){
		IntegerRangeValidator validator = new IntegerRangeValidator();
		if (lower != null) validator.setMin(lower);
		if (upper != null) validator.setMin(upper);
		return validator;
	}
	
	public static Validator doubleRange(Double lower, Double upper){
		FloatRangeValidator validator = new FloatRangeValidator();
		if (lower != null) validator.setMin(lower.floatValue());
		if (upper != null) validator.setMin(upper.floatValue());
		return validator;
	}
}
