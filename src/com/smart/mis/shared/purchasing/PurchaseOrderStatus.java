package com.smart.mis.shared.purchasing;

import java.util.LinkedHashMap;

public class PurchaseOrderStatus {
	
	static LinkedHashMap<String, String> valueMap;
	static LinkedHashMap<String, String> valuePaymentMap;
	
	static {
		valueMap = new LinkedHashMap<String, String>();  
        valueMap.put("1_created_order", "ออกคำสั่งซื้อแล้ว");  
        valueMap.put("2_recevied_product", "รับสินค้าแล้ว");  
        valueMap.put("3_returned_product", "คืนสินค้า");
        valueMap.put("4_canceled", "ยกเลิก");
        
        valuePaymentMap = new LinkedHashMap<String, String>();  
        valuePaymentMap.put("1_waiting_for_payment", "รอชำระเงิน");  
        valuePaymentMap.put("2_paid", "ชำระเงินแล้ว");  
        valuePaymentMap.put("3_over_due", "เกินกำหนดชำระเงิน");
        valuePaymentMap.put("4_canceled", "ยกเลิก");
	}
	
	public static LinkedHashMap<String, String> getValueMap() {
        return valueMap;
	}
	
	public static LinkedHashMap<String, String> getValuePaymentMap() {
        return valuePaymentMap;
	}
	
	public static String getDisplay(String value) {
        return valueMap.get(value);
	}
	
	public static String getPaymentDisplay(String value) {
        return valuePaymentMap.get(value);
	}
}
