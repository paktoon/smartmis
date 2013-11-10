package com.smart.mis.shared.sale;

import java.util.LinkedHashMap;

public class DeliveryStatus {
	static LinkedHashMap<String, String> valueMap;
	
	static {
		valueMap = new LinkedHashMap<String, String>();  
        valueMap.put("1_on_delivery", "กำลังนำส่ง");  
        valueMap.put("2_delivery_completed", "นำส่งแล้ว");
	}
	
	public static LinkedHashMap<String, String> getValueMap() {
        return valueMap;
	}
	
	public static String getDisplay(String value) {
        return valueMap.get(value);
	}
}
