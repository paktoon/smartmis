package com.smart.mis.client.function.inventory.material.requisition;

import com.smart.mis.shared.inventory.RequisitionStatus;
import com.smart.mis.shared.prodution.ProcessStatus;
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

public class MaterialRequstDS extends DataSource {

	 private static MaterialRequstDS instance = null;
	 
	 public static MaterialRequstDS getInstance() {
		 if (instance == null) {
			 instance = new MaterialRequstDS();
		 }
		 
		 return instance;
	 }
	 
	 public static MaterialRequstDS resetInstance() {
		 instance = new MaterialRequstDS();
		 return instance;
	 }
	 
	 public MaterialRequstDS(){
		 setID("MaterialRequstDS");
		 DataSourceTextField Field_1 = new DataSourceTextField("mat_req_id", "รหัสคำขอเบิกวัตถุดิบ");
		 Field_1.setPrimaryKey(true);
		 
		 DataSourceTextField Field_2_1 = new DataSourceTextField("job_id", "รหัสคำสั่งผลิต");
		 DataSourceTextField Field_2_2 = new DataSourceTextField("plan_id", "รหัสแผนการผลิต");
		 
		 DataSourceTextField Field_3_1 = new DataSourceTextField("smid", "รหัสช่าง");
		 DataSourceTextField Field_3_2 = new DataSourceTextField("sname", "ชื่อช่าง");
		 DataSourceTextField Field_3_3 = new DataSourceTextField("stype", "ประเภทงาน");
		 
		 DataSourceTextField Field_4_1 = new DataSourceTextField("req_date", "วันที่ขอเบิก");
		 DataSourceTextField Field_4_2 = new DataSourceTextField("issue_date", "วันที่จ่ายวัตถุดิบ");
		 
		 DataSourceFloatField Field_5 = new DataSourceFloatField("total_req_weight", "น้ำหนักที่ขอเบิก(กรัม)");
		 DataSourceFloatField Field_6 = new DataSourceFloatField("total_req_weight", "จำนวนที่ขอเบิก (ชิ้น)");
		 
		 DataSourceFloatField Field_6_1 = new DataSourceFloatField("total_issue_weight", "น้ำหนักที่สั่งจ่าย (กรัม)");
		 DataSourceFloatField Field_6_2 = new DataSourceFloatField("total_issue_weight", "จำนวนที่สั่งจ่าย (ชิ้น)");
	        
		 DataSourceDateField Field_7 = new DataSourceDateField("created_date", "สร้างวันที่");
		 DataSourceTextField Field_8 = new DataSourceTextField("created_by", "สร้างโดย");
		 Field_8.setForeignKey("UserDS.uid");
		 DataSourceDateField Field_9 = new DataSourceDateField("modified_date", "แก้ไขวันที่");
		 DataSourceTextField Field_10 = new DataSourceTextField("modified_by", "แก้ไขโดย");
		 Field_10.setForeignKey("UserDS.uid");
		 
		 DataSourceTextField Field_11 = new DataSourceTextField("comment", "ความคิดเห็น");
		 DataSourceEnumField Field_12 = new DataSourceEnumField("status", "สถานะ");
		 Field_12.setValueMap(RequisitionStatus.getValueMap());
		 
		 setFields(Field_1, Field_2_1, Field_2_2, Field_3_1, Field_3_2, Field_3_3, Field_4_1, Field_4_2 ,Field_5, Field_6, Field_6_1, Field_6_2,  Field_7, Field_8, Field_9, Field_10, Field_11, Field_12);
		 
		 //setDataURL("smartmis/security/userData");
		 setTestData(MaterialRequstData.getNewRecords()); // For Test
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
