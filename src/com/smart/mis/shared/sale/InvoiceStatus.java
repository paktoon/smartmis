package com.smart.mis.shared.sale;

import java.util.LinkedHashMap;

public class InvoiceStatus {
	
	static LinkedHashMap<String, String> valueMap;
	
	static {
		valueMap = new LinkedHashMap<String, String>();  
        valueMap.put("1_waiting_for_payment", "รอชำระเงิน");  
        valueMap.put("2_paid", "ชำระเงินแล้ว"); 
        valueMap.put("3_over_due", "เกินกำหนดชำระเงิน"); 
        valueMap.put("4_canceled", "ยกเลิก");
	}
	
	public static LinkedHashMap<String, String> getValueMap() {
        return valueMap;
	}
	
	public static String getDisplay(String value) {
        return valueMap.get(value);
	}
}
