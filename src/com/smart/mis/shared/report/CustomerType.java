package com.smart.mis.shared.report;

import java.util.LinkedHashMap;

public class CustomerType {
	
	static LinkedHashMap<String, String> valueMap;
	
	static {
		valueMap = new LinkedHashMap<String, String>();  
        valueMap.put("cus_type", "ประเภทลูกค้า");  
        valueMap.put("bus_type", "ลักษณะธุรกิจ"); 
        valueMap.put("cus_group", "กลุ่มลูกค้า"); 
        //valueMap.put("zone", "โซน");
	}
	
	public static LinkedHashMap<String, String> getValueMap() {
        return valueMap;
	}
	
	public static String getDisplay(String value) {
        return valueMap.get(value);
	}
}
