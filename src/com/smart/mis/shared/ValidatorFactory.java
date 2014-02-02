package com.smart.mis.shared;

import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.validator.FloatRangeValidator;
import com.smartgwt.client.widgets.form.validator.IntegerRangeValidator;
import com.smartgwt.client.widgets.form.validator.MaskValidator;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.form.validator.Validator;

public class ValidatorFactory {

	public static Validator integerRange(Integer lower, Integer upper){
		IntegerRangeValidator validator = new IntegerRangeValidator();
		if (lower != null) validator.setMin(lower);
		if (upper != null) validator.setMax(upper);
		return validator;
	}
	
	public static Validator doubleRange(Double lower, Double upper){
		FloatRangeValidator validator = new FloatRangeValidator();
		if (lower != null) validator.setMin(lower.floatValue());
		if (upper != null) validator.setMax(upper.floatValue());
		return validator;
	}
	
	public static Validator phoneString(){
        MaskValidator maskValidator = new MaskValidator();  
        maskValidator.setMask("^\\s*(1?)\\s*\\(?\\s*(\\d{2}|\\d{3})\\s*\\)?\\s*-?\\s*(\\d{3})\\s*-?\\s*(\\d{4})\\s*$");  
        maskValidator.setTransformTo("$1($2) $3 - $4");
        return maskValidator;
	}
	
	public static FormItemIcon phoneHint(){
		FormItemIcon icon = new FormItemIcon();  
        icon.setSrc("[SKIN]/actions/help.png"); 
        icon.setPrompt("เฉพาะตัวเลข 9-10 หลัก เช่น 023456789 หรือ 0894561970");
        return icon;
	}
	
	public static Validator emailString(){
		RegExpValidator regExpValidator = new RegExpValidator();  
	    regExpValidator.setExpression("^([a-zA-Z0-9_.\\-+])+@(([a-zA-Z0-9\\-])+\\.)+[a-zA-Z0-9]{2,4}$");  
        return regExpValidator;
	}
    
	public static FormItemIcon emailHint(){
		FormItemIcon icon = new FormItemIcon();  
        icon.setSrc("[SKIN]/actions/help.png"); 
        icon.setPrompt("รูปแบบอีเมล ###@###.### เช่น test@test.com");
        return icon;
	}
	
}
