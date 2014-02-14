package com.smart.mis.client.function.sale.quotation;

import java.util.Date;

import com.smart.mis.shared.sale.Customer;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class QuotationData {
	// --> Quote Object + ItemQuote Object
	// Quote - quote id , cid , from, to ,delivery , total_weight, total_amount, netExclusive, tax, netInclusive, created_date, modified_date, created_by, modified_by, comment, status [waiting_for_approved, waiting_for_revised, approved, removed] --> to data store
    public static ListGridRecord createRecord(String quote_id, String cid, String cus_name, String payment_model, Integer credit, String cus_type , String bus_type, String cus_group, String zone,Date from,Date to, Date delivery ,Double total_weight,Integer total_amount,Double netExclusive,Date created_date,Date modified_date,String created_by,String modified_by, String comment, String status) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("quote_id", quote_id);
        
        record.setAttribute("cid",cid);  
        record.setAttribute("cus_name",cus_name);
        record.setAttribute("payment_model",payment_model);
        record.setAttribute("credit",credit);
        
        record.setAttribute("cus_type",cus_type);
        record.setAttribute("bus_type",bus_type);
        record.setAttribute("cus_group",cus_group);
        record.setAttribute("zone",zone);
        
        record.setAttribute("from", from); 
        record.setAttribute("to", to);
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
        record.setAttribute("comment", comment);
        record.setAttribute("status", status);
        return record;  
    }
    
    public static ListGridRecord createUpdateRecord(String quote_id,String cid, String cus_name, String payment_model, Integer credit, String cus_type , String bus_type, String cus_group, String zone,Date from,Date to, Date delivery ,Double total_weight,Integer total_amount,Double netExclusive,Date modified_date,String modified_by, String comment, String status) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("quote_id", quote_id);
        
        record.setAttribute("cid",cid);  
        record.setAttribute("cus_name",cus_name);
        record.setAttribute("payment_model",payment_model);
        record.setAttribute("credit",credit);
        
        record.setAttribute("cus_type",cus_type);
        record.setAttribute("bus_type",bus_type);
        record.setAttribute("cus_group",cus_group);
        record.setAttribute("zone",zone);
        
        record.setAttribute("from", from); 
        record.setAttribute("to", to);
        record.setAttribute("delivery", delivery);
        record.setAttribute("total_weight", total_weight);  
        record.setAttribute("total_amount", total_amount); 
        record.setAttribute("netExclusive", netExclusive);
        record.setAttribute("tax", netExclusive * 0.07);
        record.setAttribute("netInclusive", netExclusive * 1.07);
        record.setAttribute("modified_date", modified_date);
        record.setAttribute("modified_by", modified_by);
        record.setAttribute("comment", comment);
        record.setAttribute("status", status);
        return record;  
    }
    
    public static ListGridRecord createStatusRecord(String quote_id, String status, String modified_by, String comment) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("quote_id", quote_id);
        record.setAttribute("status", status);
        record.setAttribute("modified_by", modified_by);
        record.setAttribute("modified_date", new Date());
        record.setAttribute("comment", comment);
        return record;  
    }
    
//    public static ListGridRecord createStatusRecord(String quote_id, String status, String comment) {  
//        ListGridRecord record = new ListGridRecord();
//        record.setAttribute("quote_id", quote_id);
//        record.setAttribute("status", status);
//        record.setAttribute("comment", comment);
//        return record;  
//    }
    
    public static ListGridRecord[] getNewRecords() {
    	return new ListGridRecord[]{ 
    			createRecord("QA10001","CU10017", "Flora Creek", "เงินสด", 10 , "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date(), new Date(), new Date() , 1360.0, 200, 11000.0, new Date(), null, "สมบัติ ยอดขาย", null, "", "2_waiting_for_approved"),
    			createRecord("QA10002","CU10008", "ประทีปเจมส์", "เงินสด", 0, "ลูกค้าทั่วไป", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "เอเซีย" , new Date(), new Date(), new Date() , 1836.0, 270, 14850.0, new Date(), null, "สมบัติ ยอดขาย", "ภักดิ์ทูล ใจทอง", "", "3_approved"),
    			createRecord("QA10003","CU10004", "มายด์ แอนด์ ลีน่าเครื่องประดับ", "เงินสด", 20 , "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date(), new Date(), new Date() , 1896.0, 300, 16500.0, new Date(), null, "สมบัติ ยอดขาย", "ภักดิ์ทูล ใจทอง", "", "3_approved"),
    			createRecord("QA10004","CU10010", "บริษัท บิ๊กซิลเวอร์ แมนูแฟคเจอร์ริ่ง จำกัด", "เงินสด", 10 ,"ลูกค้าประจำ", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date(), new Date(), new Date() , 632.0, 100, 5000.0, new Date(), null, "สมบัติ ยอดขาย", "ภักดิ์ทูล ใจทอง", "", "4_canceled"),
    			
    			createRecord("QA10005","CU10017", "Flora Creek", "แคชเชียร์เช็ค", 30 , "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date(), new Date(), new Date() , 1360.0, 200, 11000.0, new Date(), null, "สมบัติ ยอดขาย", null, "", "2_waiting_for_approved"),
    			createRecord("QA10006","CU10008", "ประทีปเจมส์", "แคชเชียร์เช็ค", 0 , "ลูกค้าทั่วไป", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date(), new Date(), new Date() , 1836.0, 270, 14850.0, new Date(), null, "สมบัติ ยอดขาย", "ภักดิ์ทูล ใจทอง", "", "3_approved"),
    			createRecord("QA10007","CU10004", "มายด์ แอนด์ ลีน่าเครื่องประดับ", "แคชเชียร์เช็ค", 10 , "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date(), new Date(), new Date() , 1896.0, 300, 16500.0, new Date(), null, "สมบัติ ยอดขาย", "ภักดิ์ทูล ใจทอง", "", "2_waiting_for_approved"),
    			createRecord("QA10008","CU10010", "บริษัท บิ๊กซิลเวอร์ แมนูแฟคเจอร์ริ่ง จำกัด", "แคชเชียร์เช็ค", 10 ,"ลูกค้าประจำ", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date(), new Date(), new Date() , 632.0, 100, 5000.0, new Date(), null, "สมบัติ ยอดขาย", "ภักดิ์ทูล ใจทอง", "", "3_approved"),
    			
    			createRecord("QA10009","CU10017", "Flora Creek", "แคชเชียร์เช็ค", 30 , "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date(), new Date(), new Date() , 1360.0, 200, 11000.0, new Date(), null, "สมบัติ ยอดขาย", "ภักดิ์ทูล ใจทอง", "", "1_waiting_for_revised"),
    			createRecord("QA10010","CU10008", "ประทีปเจมส์", "แคชเชียร์เช็ค", 0 , "ลูกค้าทั่วไป", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date(), new Date(), new Date() , 1836.0, 270, 14850.0, new Date(), null, "สมบัติ ยอดขาย", "ภักดิ์ทูล ใจทอง", "", "3_approved"),
    			createRecord("QA10011","CU10004", "มายด์ แอนด์ ลีน่าเครื่องประดับ", "แคชเชียร์เช็ค", 10 , "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date(), new Date(), new Date() , 1896.0, 300, 16500.0, new Date(), null, "สมบัติ ยอดขาย", "ภักดิ์ทูล ใจทอง", "", "1_waiting_for_revised"),
    			createRecord("QA10012","CU10010", "บริษัท บิ๊กซิลเวอร์ แมนูแฟคเจอร์ริ่ง จำกัด", "แคชเชียร์เช็ค", 10 ,"ลูกค้าประจำ", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date(), new Date(), new Date() , 632.0, 100, 5000.0, new Date(), null, "สมบัติ ยอดขาย", "ภักดิ์ทูล ใจทอง", "", "3_approved"),
    	};
    	//return new ListGridRecord[]{};
    }
}
