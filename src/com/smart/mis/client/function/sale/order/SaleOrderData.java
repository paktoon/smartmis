package com.smart.mis.client.function.sale.order;

import java.util.Date;

import com.smart.mis.shared.sale.Customer;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SaleOrderData {
	// --> Quote Object + ItemQuote Object
	// Quote - quote id , cid , from, to ,delivery , total_weight, total_amount, netExclusive, tax, netInclusive, created_date, modified_date, created_by, modified_by, comment, status [waiting_for_approved, waiting_for_revised, approved, removed] --> to data store
    public static ListGridRecord createRecord(String sale_id, String quote_id, String invoice_id,String cid, String cus_name, String payment_model, Integer credit,String cus_type , String bus_type, String cus_group, String zone, Date delivery ,Double total_weight,Integer total_amount,Double netExclusive,Date created_date,Date modified_date,String created_by,String modified_by, String status, String purchase_id, Date due_date) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("sale_id", sale_id);
        record.setAttribute("quote_id", quote_id);
        record.setAttribute("invoice_id", invoice_id);
        
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
        record.setAttribute("status", status);
        record.setAttribute("purchase_id", purchase_id);
        record.setAttribute("due_date", due_date);
        return record;  
    }
    
//    public static ListGridRecord createUpdateRecord(String sale_id, String quote_id,String cid, String cus_name, String payment_model, Integer credit, Date delivery ,Double total_weight,Integer total_amount,Double netExclusive,Date modified_date,String modified_by, String status, String purchase_id) {  
//        ListGridRecord record = new ListGridRecord();
//        record.setAttribute("sale_id", sale_id);
//        record.setAttribute("quote_id", quote_id);
//        record.setAttribute("cid",cid);  
//        record.setAttribute("cus_name",cus_name);
//        record.setAttribute("payment_model",payment_model);
//        record.setAttribute("credit",credit);
//        //record.setAttribute("from", from); 
//        //record.setAttribute("to", to);
//        record.setAttribute("delivery", delivery);
//        record.setAttribute("total_weight", total_weight);  
//        record.setAttribute("total_amount", total_amount); 
//        record.setAttribute("netExclusive", netExclusive);
//        record.setAttribute("tax", netExclusive * 0.07);
//        record.setAttribute("netInclusive", netExclusive * 1.07);
//        record.setAttribute("modified_date", modified_date);
//        record.setAttribute("modified_by", modified_by);
//        //record.setAttribute("comment", comment);
//        record.setAttribute("status", status);
//        return record;  
//    }
    
    public static ListGridRecord createStatusRecord(String sale_id, String status) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("sale_id", sale_id);
        record.setAttribute("status", status);
        return record;  
    }
    
    public static ListGridRecord[] getNewRecords() {
    	return new ListGridRecord[]{ 
    			createRecord("SO10001","QA10001", "IN10001","CU10017", "Flora Creek", "เงินสด", 10 , "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date() , 1360.0, 200, 11000.0, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "1_waiting_for_production", "PO1111", new Date()),
    			createRecord("SO10002","QA10002", "IN10002","CU10008", "ประทีปเจมส์", "เงินสด", 0 , "ลูกค้าทั่วไป", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date() , 1836.0, 270, 14850.0, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "2_production_in_progress", "PO1112", new Date()),
    			createRecord("SO10003","QA10003", "IN10003","CU10004", "มายด์ แอนด์ ลีน่าเครื่องประดับ", "เงินสด", 20 , "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date() , 1896.0, 300, 16500.0, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "6_canceled", "PO1113", new Date()),
    			createRecord("SO10004","QA10004", "IN10004","CU10010", "บริษัท บิ๊กซิลเวอร์ แมนูแฟคเจอร์ริ่ง จำกัด", "เงินสด", 10 ,"ลูกค้าประจำ", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date() , 632.0, 100, 5000.0, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "2_production_in_progress", "PO1114", new Date()),
    		
    			createRecord("SO10005","QA10005", "IN10005","CU10017", "Flora Creek", "แคชเชียร์เช็ค", 30 , "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date() , 1360.0, 200, 11000.0, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "3_production_completed", "PO1177", new Date()),
    			createRecord("SO10006","QA10006", "IN10006","CU10008", "ประทีปเจมส์", "แคชเชียร์เช็ค", 0 , "ลูกค้าทั่วไป", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date() , 1836.0, 270, 14850.0, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "4_on_delivery", "PO1179", new Date()),
    			createRecord("SO10007","QA10007", "IN10007","CU10004", "มายด์ แอนด์ ลีน่าเครื่องประดับ", "แคชเชียร์เช็ค", 10 , "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date() , 1896.0, 300, 16500.0, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "4_on_delivery", "PO1126", new Date()),
    			createRecord("SO10008","QA10008", "IN10008","CU10010", "บริษัท บิ๊กซิลเวอร์ แมนูแฟคเจอร์ริ่ง จำกัด", "แคชเชียร์เช็ค", 10 ,"ลูกค้าประจำ", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date() , 632.0, 100, 5000.0, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "5_delivery_completed", "PO1178", new Date())
    	};
    	//return new ListGridRecord[]{};
    }
}
