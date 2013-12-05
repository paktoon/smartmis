package com.smart.mis.client.function.inventory.material.returns;

import com.smart.mis.shared.inventory.ReturnStatus;
import com.smart.mis.shared.purchasing.PurchaseOrderStatus;
import com.smart.mis.shared.purchasing.PurchaseRequestStatus;
import com.smart.mis.shared.sale.QuotationStatus;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceEnumField;
import com.smartgwt.client.data.fields.DataSourceFloatField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class ReturnDS extends DataSource {

	 private static ReturnDS instance = null;
	 
	 public static ReturnDS getInstance() {
		 if (instance == null) {
			 instance = new ReturnDS();
		 }
		 
		 return instance;
	 }
	 
	 public static ReturnDS resetInstance() {
		 instance = new ReturnDS();
		 return instance;
	 }
	 
	 public ReturnDS(){
		 setID("ReturnDS");
		 DataSourceTextField Field_1 = new DataSourceTextField("return_id", "รหัสคืนวัตถุดิบ");
		 Field_1.setPrimaryKey(true);
		 
		 DataSourceTextField Field_1_1 = new DataSourceTextField("job_id", "รหัสคำสั่งผลิต");
		 
		 DataSourceTextField Field_2_1 = new DataSourceTextField("smid", "รหัสช่าง");
		 DataSourceTextField Field_2_2 = new DataSourceTextField("sm_name", "ชื่อช่าง");
		 DataSourceTextField Field_3_1 = new DataSourceTextField("mid", "รหัสวัตถุดิบ");
		 DataSourceTextField Field_3_2 = new DataSourceTextField("mat_name", "ชื่อวัตถุดิบ");
		 
		 DataSourceFloatField Field_6 = new DataSourceFloatField("total_return_weight", "น้ำหนักที่คืนรวม(กรัม)");
		 DataSourceFloatField Field_7 = new DataSourceFloatField("total_received_weight", "น้ำหนักที่รับรวม(กรัม)");
		 
		 DataSourceDateField Field_8 = new DataSourceDateField("return_date", "ขอคืนวัตถุดิบวันที่");
		 DataSourceDateField Field_9 = new DataSourceDateField("received_date", "รับวัตถุดิบวันที่");
		 DataSourceTextField Field_10 = new DataSourceTextField("received_by", "รับวัตถุดิบโดย");
		 Field_10.setForeignKey("UserDS.uid");
		 
		 DataSourceDateField Field_11 = new DataSourceDateField("created_date", "สร้างวันที่");
		 DataSourceTextField Field_12 = new DataSourceTextField("created_by", "สร้างโดย");
		 Field_12.setForeignKey("UserDS.uid");
		 DataSourceDateField Field_13 = new DataSourceDateField("modified_date", "แก้ไขวันที่");
		 DataSourceTextField Field_14 = new DataSourceTextField("modified_by", "แก้ไขโดย");
		 Field_14.setForeignKey("UseDS.uid");
		 
		 DataSourceEnumField Field_16 = new DataSourceEnumField("status", "สถานะ");
		 Field_16.setValueMap(ReturnStatus.getValueMap());
		 
		 setFields(Field_1, Field_1_1, Field_2_1, Field_2_2, Field_3_1, Field_3_2, Field_6 ,Field_7, Field_8,  Field_9, Field_10, Field_11, Field_12, Field_13, Field_14, Field_16);
		 
		 //setDataURL("smartmis/security/userData");
		 setTestData(ReturnData.getNewRecords()); // For Test
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
