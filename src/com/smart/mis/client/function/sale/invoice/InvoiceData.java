package com.smart.mis.client.function.sale.invoice;

import java.util.Date;

import com.smart.mis.shared.sale.Customer;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class InvoiceData {
	// --> Quote Object + ItemQuote Object
	// Quote - quote id , cid , from, to ,delivery , total_weight, total_amount, netExclusive, tax, netInclusive, created_date, modified_date, created_by, modified_by, comment, status [waiting_for_approved, waiting_for_revised, approved, removed] --> to data store
    public static ListGridRecord createRecord(String invoice_id, String sale_id ,String cid, String cus_name, String payment_model, Integer credit,String cus_type , String bus_type, String cus_group, String zone, Date delivery ,Double total_weight,Integer total_amount,Double netExclusive,Date created_date,Date modified_date,String created_by,String modified_by, String status, String purchase_id, Date due_date,Date paid_date) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("invoice_id", invoice_id);
        record.setAttribute("sale_id", sale_id);
        //record.setAttribute("quote_id", quote_id);
        record.setAttribute("cid",cid);  
        record.setAttribute("cus_name",cus_name);
        record.setAttribute("payment_model",payment_model);
        record.setAttribute("credit",credit);

        record.setAttribute("cus_type",cus_type);
        record.setAttribute("bus_type",bus_type);
        record.setAttribute("cus_group",cus_group);
        record.setAttribute("zone",zone);
        
        record.setAttribute("delivery", delivery);
        record.setAttribute("total_weight", total_weight);  
        record.setAttribute("total_amount", total_amount); 
        record.setAttribute("netExclusive", netExclusive);
        record.setAttribute("tax", netExclusive * 0.07);
        record.setAttribute("netInclusive", netExclusive * 1.07);  
        record.setAttribute("created_date", created_date); 
        record.setAttribute("created_by", created_by);
        record.setAttribute("modified_date", modified_date);
        record.setAttribute("modified_by", modified_by);
        //record.setAttribute("comment", comment);
        record.setAttribute("status", status); // รอชำระเงิน, ชำระเงินแล้ว, เกินกำหนดชำระเงิน
        record.setAttribute("purchase_id", purchase_id);
        record.setAttribute("due_date", due_date);
        record.setAttribute("paid_date", paid_date);
        return record;  
    }
    
    public static ListGridRecord createReceiptRecord(ListGridRecord record, Double receivedInclusive) {
        record.setAttribute("receivedInclusive", receivedInclusive);
        return record;  
    }
    
    public static ListGridRecord createStatusRecord(String invoice_id, String status) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("invoice_id", invoice_id);
        record.setAttribute("status", status);
        return record;  
    }
    
    public static ListGridRecord getStatusRecords(String invoice_id) {
    	ListGridRecord[] all = getNewRecords();
    	for (ListGridRecord record : all) {
    		if (record.getAttributeAsString("invoice_id").equalsIgnoreCase(invoice_id)) {
    			return record;
    		}
    	}
    	 return createRecord(invoice_id,"SO10001","CU10017", "Flora Creek", "เงินสด", 10 , "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date() , 120.0, 200, 11000.0, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "1_waiting_for_payment", "", new Date(), null);
    }
    
    public static ListGridRecord[] getNewRecords() {
    	return new ListGridRecord[]{ 
    			createRecord("IN10001","SO10001","CU10017", "Flora Creek", "เงินสด", 10 , "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date() , 1360.0, 200, 11000.0, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "1_waiting_for_payment", "PO1111", new Date(), null),
    			createRecord("IN10002","SO10002","CU10008", "ประทีปเจมส์", "เงินสด", 0 , "ลูกค้าทั่วไป", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "เอเซีย" , new Date() , 1836.0, 270, 14850.0, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "1_waiting_for_payment", "PO1112", new Date(), null),
    			createRecord("IN10003","SO10003","CU10004", "มายด์ แอนด์ ลีน่าเครื่องประดับ", "เงินสด", 20 , "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "เอเซีย" , new Date() , 1896.0, 300, 16500.0, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "4_canceled", "PO1113", new Date(), null),
    			createRecord("IN10004","SO10004","CU10010", "บริษัท บิ๊กซิลเวอร์ แมนูแฟคเจอร์ริ่ง จำกัด", "เงินสด", 10 ,"ลูกค้าประจำ", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "เอเซีย" , new Date() , 632.0, 100, 5000.0, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "1_waiting_for_payment", "PO1114", new Date(), null),
    		
    			createRecord("IN10005","SO10005","CU10017", "Flora Creek", "แคชเชียร์เช็ค", 30 , "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date() , 1360.0, 200, 11000.0, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "1_waiting_for_payment", "PO1177", new Date(), null),
    			createRecord("IN10006","SO10006","CU10008", "ประทีปเจมส์", "แคชเชียร์เช็ค", 0 , "ลูกค้าทั่วไป", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "เอเซีย" , new Date() , 1836.0, 270, 14850.0, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "2_paid", "PO1179", new Date(), new Date()),
    			createRecord("IN10007","SO10007","CU10004", "มายด์ แอนด์ ลีน่าเครื่องประดับ", "แคชเชียร์เช็ค", 10 , "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "เอเซีย" , new Date() , 1896.0, 300, 16500.0, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "1_waiting_for_payment", "PO1126", new Date(), null),
    			createRecord("IN10008","SO10008","CU10010", "บริษัท บิ๊กซิลเวอร์ แมนูแฟคเจอร์ริ่ง จำกัด", "แคชเชียร์เช็ค", 10 ,"ลูกค้าประจำ", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "เอเซีย" , new Date() , 632.0, 100, 5000.0, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "2_paid", "PO1178", new Date(), new Date())
    			};
    	//return new ListGridRecord[]{};
    }
}
