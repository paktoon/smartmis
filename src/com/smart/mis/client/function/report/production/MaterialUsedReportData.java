package com.smart.mis.client.function.report.production;

import java.util.ArrayList;
import java.util.Date;

import com.smartgwt.client.data.DateRange;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RelativeDate;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class MaterialUsedReportData {
	public static ListGridRecord createRecord(String sub_request_id, String request_id, String material_id, String material_name, String material_unit, Double request_amount, String material_type, Date request_date) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("sub_request_id", sub_request_id);
        record.setAttribute("mat_request_id", request_id);
        record.setAttribute("mid", material_id);
        record.setAttribute("mname", material_name);
        record.setAttribute("unit", material_unit);
        record.setAttribute("request_amount", request_amount);
        record.setAttribute("type", material_type);
        
        record.setAttribute("request_date", request_date);
        
        //New
        record.setAttribute("value", request_amount * (int)(Math.random() * 100));
        return record;  
    }
	
	public static ListGridRecord createRecord(String sub_request_id, String request_id, String material_id, String material_name, String material_unit, Double request_amount, String material_type, Date request_date, Double value) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("sub_request_id", sub_request_id);
        record.setAttribute("mat_request_id", request_id);
        record.setAttribute("mid", material_id);
        record.setAttribute("mname", material_name);
        record.setAttribute("unit", material_unit);
        record.setAttribute("request_amount", request_amount);
        record.setAttribute("type", material_type);
        
        record.setAttribute("request_date", request_date);
        
        //New
        record.setAttribute("value", value);
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
    		Double request_weight_1 = (Math.random() * 500.0);
    		dummyReport.add(createRecord("MRR01"+i , "MR01"+i, "MA10001","แร่เงิน 100%", "กรัม", request_weight_1, "แร่เงิน", date, request_weight_1* 12));
    		//Double request_weight_2 = (Math.random() * 1000.0);
    		dummyReport.add(createRecord("MRR02"+i , "MR02"+i, "MA10002","แร่เงิน 92.5%", "กรัม",request_weight_1 + 500.72 , "แร่เงิน", date, (request_weight_1 + 500.72) * 11));

    		Double request_amount_0 = Math.rint((Math.random() * 20.0));
    		Double request_amount_1 = Math.rint((Math.random() * 1000.0)) + 1000;
    		Double request_amount_2 = Math.rint((Math.random() * 1000.0)) + 1000;
    		Double request_amount_3 = Math.rint((Math.random() * 1000.0)) + 1000;
    		dummyReport.add(createRecord("MRR03"+i , "MR03"+i, "MA20007","บุษราคัม", "เม็ด", request_amount_0, "พลอยประดับ", date, request_amount_0 * 150.0));
    		dummyReport.add(createRecord("MRR04"+i , "MR04"+i, "MA20001","แมกกาไซต์ PP6", "เม็ด", request_amount_1, "แมกกาไซต์", date, request_amount_1 * 0.7));
    		dummyReport.add(createRecord("MRR05"+i , "MR05"+i, "MA20002","แมกกาไซต์ PP7", "เม็ด", request_amount_2, "แมกกาไซต์", date, request_amount_2 * 0.9));
    		dummyReport.add(createRecord("MRR06"+i , "MR06"+i, "MA20005","ถุงพลาสติก 6x6", "ถุง" , request_amount_3, "อื่นๆ", date, request_amount_3 * 0.2));
    	}
    	ListGridRecord[] T = new ListGridRecord[]{};
    	return dummyReport.toArray(T);
       }
}
