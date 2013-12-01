package com.smart.mis.shared.financial;

import java.util.LinkedHashMap;

public class WagePaymentStatus {
	static LinkedHashMap<String, String> valueMap;
	
	static {
		valueMap = new LinkedHashMap<String, String>();  
        valueMap.put("1_waiting_for_payment", "รอชำระเงิน");  
        valueMap.put("2_paid", "ชำระเงินแล้ว");
	}
	
	public static LinkedHashMap<String, String> getValueMap() {
        return valueMap;
	}
	
	public static String getDisplay(String value) {
        return valueMap.get(value);
	}
}
