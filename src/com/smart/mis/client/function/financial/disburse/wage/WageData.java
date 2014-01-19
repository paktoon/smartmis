package com.smart.mis.client.function.financial.disburse.wage;

import java.util.ArrayList;
import java.util.Date;

import com.smart.mis.shared.prodution.Smith;
import com.smart.mis.shared.sale.Customer;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.data.DateRange;
import com.smartgwt.client.data.RelativeDate;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class WageData {
	
	//public static ListGridRecord createSentRecord(String job_id,String plan_id,Smith smith, Date sent_date, Date due_date,Double total_sent_weight,Integer total_sent_amount, Date created_date,Date modified_date,String created_by,String modified_by, String comment, String status) {  
	public static ListGridRecord createRecord(ListGridRecord ref_record, String wage_id, Date created_date, String created_by, String status) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute("job_id", ref_record.getAttributeAsString("job_id"));
        record.setAttribute("smid", ref_record.getAttributeAsString("smid"));
        
        record.setAttribute("sname", ref_record.getAttributeAsString("sname"));
        record.setAttribute("semail", ref_record.getAttributeAsString("semail"));
        record.setAttribute("sphone1", ref_record.getAttributeAsString("sphone1"));
        record.setAttribute("sphone2", ref_record.getAttributeAsString("sphone2"));
        record.setAttribute("saddress", ref_record.getAttributeAsString("saddress"));
        record.setAttribute("stype", ref_record.getAttributeAsString("stype"));
        
        record.setAttribute("total_recv_weight", ref_record.getAttributeAsDouble("total_recv_weight"));  
        record.setAttribute("total_recv_amount", ref_record.getAttributeAsInt("total_recv_amount")); 
        record.setAttribute("total_wage", ref_record.getAttributeAsDouble("total_wage"));
		
		record.setAttribute("wage_id", wage_id);
        record.setAttribute("created_date", created_date); 
        record.setAttribute("created_by", created_by);
        
        Date modified_date = null;
        String modified_by = null;
        record.setAttribute("modified_date", modified_date);
        record.setAttribute("modified_by", modified_by);
        //record.setAttribute("comment", comment);
        record.setAttribute("status", status); // created, sent
        return record;  
    }
    
    public static ListGridRecord createStatusRecord(ListGridRecord record, Date modified_date,String modified_by, String status) {  
        record.setAttribute("modified_date", modified_date);
        record.setAttribute("modified_by", modified_by);
        record.setAttribute("status", status);
        return record; 
    }
    
    public static ListGridRecord createPaidRecord(ListGridRecord record, Date modified_date,String modified_by, String status) {  
        record.setAttribute("paidInclusive", modified_date);
        record.setAttribute("paid_date", modified_by);
        record.setAttribute("paid_by", status);
        return record; 
    }
    
    public static ListGridRecord createRecord(String job_id, String smid, String sname, String stype, Double paidInclusive, Date paid_date, String status, String semail, String sphone1, String sphone2, String saddress, Double total_recv_weight, Integer total_recv_amount, Double total_wage, String wage_id) {
		ListGridRecord record = new ListGridRecord();
		
		//For report
		record.setAttribute("job_id", job_id);
        record.setAttribute("smid", smid);
        record.setAttribute("sname", sname);
        record.setAttribute("stype", stype);
        record.setAttribute("paidInclusive", paidInclusive);
        record.setAttribute("paid_date", paid_date);
        record.setAttribute("status", status);
        
        record.setAttribute("semail", semail);
        record.setAttribute("sphone1", sphone1);
        record.setAttribute("sphone2", sphone2);
        record.setAttribute("saddress", saddress);
        record.setAttribute("total_recv_weight", total_recv_weight);  
        record.setAttribute("total_recv_amount", total_recv_amount); 
        record.setAttribute("total_wage", total_wage);
		record.setAttribute("wage_id", wage_id);
		
        record.setAttribute("created_date", paid_date); 
        record.setAttribute("created_by", "ภักดิ์ทูล ใจทอง");
        
        Date modified_date = null;
        String modified_by = null;
        record.setAttribute("modified_date", modified_date);
       record.setAttribute("modified_by", modified_by);
        return record;  
    }
    
//    public static ListGridRecord[] getNewRecords() {
//    	return new ListGridRecord[]{};
//    }
    
    public static ListGridRecord[] getNewRecords() {
    	ArrayList<ListGridRecord> dummyReport = new ArrayList<ListGridRecord>();
    	DateRange dateRange = new DateRange();
        dateRange.setRelativeStartDate(RelativeDate.TODAY);
        //dateRange.setRelativeStartDate(new RelativeDate("-1m"));
    	for (int i = 1; i <= 6 ; i++) {
    		dateRange.setRelativeEndDate(new RelativeDate("-"+ (i * 10) +"d"));
    		Date date = dateRange.getEndDate();
    		
    		Double wage = (Math.random() * 10000.0) + 1000;
    		dummyReport.add(createRecord("JOB10"+i , "SM10001","จำนงค์ คำเงิน", "แต่งและฝังพลอยประดับ", wage, date, "2_paid", "test_1@smith.co.th","022588888", "", "392/21 สุขุมวิท 20 คลองเตย กรุงเทพ 10110", wage, Math.round((float)(wage/11)), wage, "WP10" + i));
    		dummyReport.add(createRecord("JOB20"+i , "SM10002","อุดมพร แสงคำ", "ขัดและติดพลอยแมกกาไซต์", wage, date, "2_paid" , "test_2@smith.co.th","022588888", "", "392/21 สุขุมวิท 20 คลองเตย กรุงเทพ 10110", wage + 251, Math.round((float)(wage + 251)/11), wage, "WP20" + i));
    		dummyReport.add(createRecord("JOB30"+i , "SM10003","โรงหล่อ บุษราคัม จิวเวอรี่", "หล่อขึ้นรูป", wage + 3526, date, "2_paid", "test_3@smith.co.th","024544963", "0818433075", "392/21 สุขุมวิท 20 คลองเตย กรุงเทพ 10110", wage + 3526, Math.round((float)(wage + 3526)/11), wage + 3526, "WP30" + i));
    	}
    	ListGridRecord[] T = new ListGridRecord[]{};
    	return dummyReport.toArray(T);
    }
}
