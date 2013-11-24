package com.smart.mis.shared.inventory;

import java.util.LinkedHashMap;

public class RequisitionStatus {
	static LinkedHashMap<String, String> valueMap;
	
	static {
		valueMap = new LinkedHashMap<String, String>();  
        valueMap.put("1_requested", "ขอเบิก");  
        valueMap.put("2_issued", "จ่ายแล้ว");
	}
	
	public static LinkedHashMap<String, String> getValueMap() {
        return valueMap;
	}
	
	public static String getDisplay(String value) {
        return valueMap.get(value);
	}
}
