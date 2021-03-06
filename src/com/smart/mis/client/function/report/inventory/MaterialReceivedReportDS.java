package com.smart.mis.client.function.report.inventory;

import java.util.HashMap;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceEnumField;
import com.smartgwt.client.data.fields.DataSourceFloatField;
import com.smartgwt.client.data.fields.DataSourceImageField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class MaterialReceivedReportDS extends DataSource  {

	 private static MaterialReceivedReportDS instance = null;
	 
	 public static MaterialReceivedReportDS getInstance() {
		 if (instance == null) {
			 instance = new MaterialReceivedReportDS();
		 }
		 return instance;
	 }
		
		 public MaterialReceivedReportDS(){
			 DataSourceTextField sub_request_id_field = new DataSourceTextField("sub_order_id");
			 sub_request_id_field.setPrimaryKey(true);
			 
			 DataSourceTextField request_id_field = new DataSourceTextField("order_id", "รหัสคำสั่งซื้อ");
			 DataSourceTextField Field_1 = new DataSourceTextField("mid", "รหัสวัตถุดิบ");
			 DataSourceTextField Field_2 = new DataSourceTextField("mat_name", "รายการวัตถุดิบ");
			 DataSourceTextField Field_3 = new DataSourceTextField("type", "ประเภท");
			 //DataSourceFloatField Field_5 = new DataSourceFloatField("weight", "น้ำหนัก (กรัม)");
			 //DataSourceFloatField Field_6 = new DataSourceFloatField("request_amount", "จำนวน");
			 DataSourceTextField Field_7 = new DataSourceTextField("unit", "หน่วย");
			 //DataSourceFloatField Field_8 = new DataSourceFloatField("price", "ราคา (บาท)");
			 //DataSourceFloatField Field_9 = new DataSourceFloatField("sum_price", "ราคารวม (บาท)");

			 DataSourceFloatField Field_10 = new DataSourceFloatField("received_weight", "น้ำหนักที่รับรวม (กรัม)");
			 DataSourceFloatField Field_11 = new DataSourceFloatField("received_amount", "จำนวนที่รับรวม");
			 
			 DataSourceDateField Field_12 = new DataSourceDateField("received_date", "วันที่รับวัตถุดิบ");
			 
			 //setFields(sub_request_id_field, request_id_field, Field_1, Field_2, Field_3, Field_5, Field_6 ,Field_7, Field_8,  Field_9, Field_10, Field_11, Field_12);
			 setFields(sub_request_id_field, request_id_field, Field_1, Field_2, Field_3, Field_7, Field_10, Field_11, Field_12);
			 
			 setTestData(MaterialReceivedReportData.getNewRecords()); // For Test
			 setClientOnly(true);
		 }
		 
		 public void refreshData() {
			 fetchData(null, new DSCallback() {
					@Override
					public void execute(DSResponse dsResponse, Object data,
							DSRequest dsRequest) {
						dsResponse.setInvalidateCache(true);
						updateCaches(dsResponse, dsRequest);
					}
		    	});
		 }
}
