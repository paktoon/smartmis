package com.smart.mis.shared.prodution;

import java.util.LinkedHashMap;

public class ProductionPlanStatus {
	
	static LinkedHashMap<String, String> valueMap;
	static LinkedHashMap<String, String> filteredValueMap;
	
	static {
		valueMap = new LinkedHashMap<String, String>();  
        valueMap.put("1_waiting_for_revised", "รอแก้ไข");  
        valueMap.put("2_waiting_for_approved", "รออนุมัติ");  
        valueMap.put("3_approved", "อนุมัติแล้ว");  
        valueMap.put("4_canceled", "ยกเลิก");  
        valueMap.put("5_on_production", "กำลังผลิต");
        valueMap.put("6_production_completed", "ผลิตเสร็จสิ้น");
        valueMap.put("7_transferred", "โอนสินค้าแล้ว"); // Done
        
        filteredValueMap = new LinkedHashMap<String, String>();  
        filteredValueMap.put("1_waiting_for_revised", "รอแก้ไข");  
        filteredValueMap.put("2_waiting_for_approved", "รออนุมัติ");  
        filteredValueMap.put("3_approved", "อนุมัติแล้ว"); 
        filteredValueMap.put("5_on_production", "กำลังผลิต");
        filteredValueMap.put("6_production_completed", "ผลิตเสร็จสิ้น");
        filteredValueMap.put("7_transferred", "โอนสินค้าแล้ว");
	}
	
	public static LinkedHashMap<String, String> getValueMap() {
        return valueMap;
	}
	
	public static LinkedHashMap<String, String> getFilteredValueMap() {
        return filteredValueMap;
	}
	
	public static String getDisplay(String value) {
        return valueMap.get(value);
	}
}
