package com.smart.mis.shared.sale;

import java.util.LinkedHashMap;

public class DeliveryStatus {
	static LinkedHashMap<String, String> valueMap, issuedValueMap;
	
	static {
		valueMap = new LinkedHashMap<String, String>();  
		valueMap.put("0_product_request", "กำลังเบิกสินค้า");
        valueMap.put("1_on_delivery", "กำลังนำส่ง");  
        valueMap.put("2_delivery_completed", "นำส่งแล้ว");
        
        issuedValueMap = new LinkedHashMap<String, String>();  
        issuedValueMap.put("0_product_request", "ขอเบิกสินค้า");
        issuedValueMap.put("1_product_issued", "จ่ายสินค้าแล้ว");  
	}
	
	public static LinkedHashMap<String, String> getValueMap() {
        return valueMap;
	}
	
	public static LinkedHashMap<String, String> getIssueValueMap() {
        return issuedValueMap;
	}
	
	public static String getDisplay(String value) {
        return valueMap.get(value);
	}
	
	public static String getIssueDisplay(String value) {
        return issuedValueMap.get(value);
	}
}
