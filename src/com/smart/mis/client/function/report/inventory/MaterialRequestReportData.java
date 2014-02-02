package com.smart.mis.client.function.report.inventory;

import java.util.ArrayList;
import java.util.Date;

import com.smartgwt.client.data.DateRange;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RelativeDate;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class MaterialRequestReportData {
	public static ListGridRecord createRecord(String sub_request_id, String request_id, String material_id, String material_name, String material_unit, Double issued_weight, Double issued_amount, String material_type, Date issued_date) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("sub_request_id", sub_request_id);
        record.setAttribute("mat_request_id", request_id);
        record.setAttribute("mid", material_id);
        record.setAttribute("mname", material_name);
        record.setAttribute("unit", material_unit);
        record.setAttribute("issued_weight", issued_weight);
        record.setAttribute("issued_amount", issued_amount);
        record.setAttribute("type", material_type);
        
        record.setAttribute("issued_date", issued_date);
        
        return record;  
    }
	
    //For test only
    public static ListGridRecord[] getNewRecords() {
    	ArrayList<ListGridRecord> dummyReport = new ArrayList<ListGridRecord>();
    	DateRange dateRange = new DateRange();
        dateRange.setRelativeStartDate(RelativeDate.TODAY);
        //dateRange.setRelativeStartDate(new RelativeDate("-1m"));
    	for (int i = 1; i <= 15 ; i++) {
    		dateRange.setRelativeEndDate(new RelativeDate("-"+ (i * 4) +"d"));
    		Date date = dateRange.getEndDate();
    		Double request_weight = (Math.random() * 1000.0) + 1000;
    		dummyReport.add(createRecord("MRR10"+i , "MR20"+i, "MA10001","แร่เงิน 100%", "กรัม", request_weight, request_weight, "แร่เงิน", date));
    		request_weight += 12;
    		dummyReport.add(createRecord("MRR20"+i , "MR23"+i, "MA10002","แร่เงิน 92.5%", "กรัม", request_weight, request_weight, "แร่เงิน", date));

    		dummyReport.add(createRecord("MRR30"+i , "MR23"+i, "MA20001","แมกกาไซต์ PP6", "เม็ด", (Math.random() * 500.0) + 1000, Math.round((Math.random() * 1000.0) + 1000) * 1.0, "แมกกาไซต์", date));
    		dummyReport.add(createRecord("MRR40"+i , "MR23"+i, "MA20002","แมกกาไซต์ PP7", "เม็ด", (Math.random() * 500.0) + 1000, Math.round((Math.random() * 1000.0) + 1000) * 1.0, "แมกกาไซต์", date));
    		dummyReport.add(createRecord("MRR50"+i , "MR23"+i, "MA20005","ถุงพลาสติก 6x6", "ถุง" , (Math.random() * 500.0) + 1000, Math.round((Math.random() * 1000.0) + 1000) * 1.0, "อื่นๆ", date));
    	}
    	ListGridRecord[] T = new ListGridRecord[]{};
    	return dummyReport.toArray(T);
       }
}
