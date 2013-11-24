package com.smart.mis.shared.purchasing;

import java.util.LinkedHashMap;

public class PurchaseRequestStatus {
	
	static LinkedHashMap<String, String> valueMap;
	
	static {
		valueMap = new LinkedHashMap<String, String>();  
        valueMap.put("1_waiting_for_revised", "รอแก้ไข");  
        valueMap.put("2_waiting_for_approved", "รออนุมัติ");  
        valueMap.put("3_approved", "อนุมัติแล้ว");  
        valueMap.put("4_canceled", "ยกเลิก");  
        valueMap.put("5_created_po", "ออกคำสั่งซื้อแล้ว");
	}
	
	public static LinkedHashMap<String, String> getValueMap() {
        return valueMap;
	}
	
	public static String getDisplay(String value) {
        return valueMap.get(value);
	}
}