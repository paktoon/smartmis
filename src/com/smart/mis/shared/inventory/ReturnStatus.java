package com.smart.mis.shared.inventory;

import java.util.LinkedHashMap;

public class ReturnStatus {
	static LinkedHashMap<String, String> valueMap;
	
	static {
		valueMap = new LinkedHashMap<String, String>();  
        valueMap.put("1_return", "ขอคืนวัตถุดิบ");  
        valueMap.put("2_received", "รับวัตถุดิบแล้ว");
	}
	
	public static LinkedHashMap<String, String> getValueMap() {
        return valueMap;
	}
	
	public static String getDisplay(String value) {
        return valueMap.get(value);
	}
}
