package com.smart.mis.shared.prodution;

import java.util.LinkedHashMap;

public class ProcessStatus {
	static LinkedHashMap<String, String> valueMap;
	
	static {
		valueMap = new LinkedHashMap<String, String>(); 
        valueMap.put("0_request_mat", "รอเบิกวัตถุดิบ"); 
        valueMap.put("1_on_production", "อยู่ในขั้นตอนการผลิต");  
        valueMap.put("2_process_completed", "เสร็จสิ้่นขั้นตอน");
        valueMap.put("3_to_next_process", "ไปขั้นตอนต่อไป");
	}
	
	public static LinkedHashMap<String, String> getValueMap() {
        return valueMap;
	}
	
	public static String getDisplay(String value) {
        return valueMap.get(value);
	}
}
