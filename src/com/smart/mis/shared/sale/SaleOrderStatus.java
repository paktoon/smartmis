package com.smart.mis.shared.sale;

import java.util.LinkedHashMap;

public class SaleOrderStatus {
	
	static LinkedHashMap<String, String> valueMap;
	
	static {
		valueMap = new LinkedHashMap<String, String>();  
        valueMap.put("1_waiting_for_production", "รอผลิต");  
        valueMap.put("2_production_in_progress", "กำลังผลิต");  
        valueMap.put("3_production_completed", "พร้อมนำส่ง");  
        valueMap.put("3_waiting_for_issued", "รอเบิกสินค้า");  
        valueMap.put("4_on_delivery", "อยู่ระหว่างนำส่ง");  
        valueMap.put("5_delivery_completed", "นำส่งแล้ว");
        valueMap.put("6_canceled", "ยกเลิก");
	}
	
	public static LinkedHashMap<String, String> getValueMap() {
        return valueMap;
	}
	
	public static String getDisplay(String value) {
        return valueMap.get(value);
	}
}
