package com.smart.mis.client.function.production.plan;

import java.util.Date;

import com.smart.mis.shared.sale.Customer;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class PlanData {
	// --> Quote Object + ItemQuote Object
	// Quote - quote id , sale_id , from, to ,delivery , total_weight, total_amount, netExclusive, tax, netInclusive, created_date, modified_date, created_by, modified_by, comment, status [waiting_for_approved, waiting_for_revised, approved, removed] --> to data store
    public static ListGridRecord createRecord(String plan_id,String sale_id, Date delivery ,Double total_weight,Integer total_amount,Date created_date,Date modified_date,String created_by,String modified_by, String comment, String status, String reason) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("plan_id", plan_id);
        record.setAttribute("sale_id",sale_id);  
        //record.setAttribute("cus_name",cus_name);
        //record.setAttribute("payment_model",payment_model);
        //record.setAttribute("credit",credit);
        //record.setAttribute("from", from); 
        //record.setAttribute("to", to);
        record.setAttribute("delivery", delivery);
        record.setAttribute("total_weight", total_weight);  
        record.setAttribute("total_amount", total_amount); 
        //record.setAttribute("netExclusive", netExclusive);
        //record.setAttribute("tax", netExclusive * 0.07);
        //record.setAttribute("netInclusive", netExclusive * 1.07);  
        record.setAttribute("created_date", created_date); 
        record.setAttribute("created_by", created_by);
        record.setAttribute("modified_date", modified_date);
        record.setAttribute("modified_by", modified_by);
        record.setAttribute("comment", comment);
        record.setAttribute("status", status);
        
        record.setAttribute("reason",reason);
        return record;  
    }
    
    public static ListGridRecord createUpdateRecord(ListGridRecord record,Double total_weight,Integer total_amount,Date modified_date,String modified_by, String comment, String status) {  
        //ListGridRecord record = new ListGridRecord();
        //record.setAttribute("plan_id", plan_id);
        //record.setAttribute("sale_id",sale_id);  
        //record.setAttribute("cus_name",cus_name);
        //record.setAttribute("payment_model",payment_model);
        //record.setAttribute("credit",credit);
        //record.setAttribute("from", from); 
        //record.setAttribute("to", to);
        //record.setAttribute("delivery", delivery);
        record.setAttribute("total_weight", total_weight);  
        record.setAttribute("total_amount", total_amount); 
        //record.setAttribute("netExclusive", netExclusive);
        //record.setAttribute("tax", netExclusive * 0.07);
        //record.setAttribute("netInclusive", netExclusive * 1.07);
        record.setAttribute("modified_date", modified_date);
        record.setAttribute("modified_by", modified_by);
        record.setAttribute("comment", comment);
        record.setAttribute("status", status);
        return record;  
    }
    
    public static ListGridRecord createStatusRecord(ListGridRecord record, String status, String comment) {  
        //ListGridRecord record = new ListGridRecord();
        //record.setAttribute("plan_id", plan_id);
        record.setAttribute("status", status);
        record.setAttribute("comment", comment);
        return record;  
    }
    
    public static ListGridRecord[] getNewRecords() {
    	return new ListGridRecord[]{ 
    			createRecord("PL10001","-", null, 1914.4, 290 , new Date(), null, "ภักดิ์ทูล ใจทอง" , null , "", "1_waiting_for_revised", "สินค้ามีปริมาณต่ำกว่าที่ควรจะเป็น"),
    			createRecord("PL10002","-", null, 2458.4, 370 , new Date(), null, "ภักดิ์ทูล ใจทอง" , null , "", "2_waiting_for_approved", "สินค้าขายดี"),
    			createRecord("PL10003","-", null, 2856.0, 420 , new Date(), null, "ภักดิ์ทูล ใจทอง" , null , "", "3_approved", "สินค้าขายดี"),
    			createRecord("PL10004","-", null, 3740.0, 550 , new Date(), null, "ภักดิ์ทูล ใจทอง" , null , "", "5_on_production", "สินค้าขายดี"),
    			
    			createRecord("PL10005","-", null, 1496.0, 220 , new Date(), null, "ภักดิ์ทูล ใจทอง" , null , "", "6_production_completed", "สินค้าขาดตลาด"),
    			createRecord("PL10006","SO10004", new Date(), 632.0, 100 , new Date(), null, "ภักดิ์ทูล ใจทอง" , null , "", "5_on_production", "สร้างจากรายการขายโดยอัตโนมัติ"),
    			createRecord("PL10007","SO10002", new Date(), 1836.0 , 270,  new Date(), null, "ภักดิ์ทูล ใจทอง" , null , "", "5_on_production", "สร้างจากรายการขายโดยอัตโนมัติ"),
    			createRecord("PL10008","SO10001", new Date(), 1360.0, 200 , new Date(), null, "ภักดิ์ทูล ใจทอง" , null , "", "3_approved", "สร้างจากรายการขายโดยอัตโนมัติ")
    	};
//    	return new ListGridRecord[]{};
    }
}
