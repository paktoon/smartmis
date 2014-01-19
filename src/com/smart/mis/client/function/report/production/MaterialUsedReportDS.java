package com.smart.mis.client.function.report.production;

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

public class MaterialUsedReportDS extends DataSource  {

	 private static MaterialUsedReportDS instance = null;
	 
	 public static MaterialUsedReportDS getInstance() {
		 if (instance == null) {
			 instance = new MaterialUsedReportDS();
		 }
		 return instance;
	 }
		
		 public MaterialUsedReportDS(){
			 DataSourceTextField Field_1 = new DataSourceTextField("sub_request_id");
			 DataSourceTextField Field_2 = new DataSourceTextField("mat_request_id", "รหัสคำขอเบิก");
			 DataSourceTextField Field_3 = new DataSourceTextField("mid", "รหัสวัตถุดิบ");
			 DataSourceTextField Field_4 = new DataSourceTextField("mname", "ชื่อวัตถุดิบ");
			 DataSourceTextField Field_5 = new DataSourceTextField("unit", "หน่วย");
			 DataSourceTextField Field_5_1 = new DataSourceTextField("type", "ชนิด");
			 DataSourceFloatField Field_7 = new DataSourceFloatField("request_amount", "จำนวนที่ใช้ในการผลิต");
			 DataSourceFloatField Field_9 = new DataSourceFloatField("issued_amount", "จำนวนที่สั่งจ่าย");
			 
			 DataSourceDateField Field_10 = new DataSourceDateField("request_date", "วันที่ใช้วัตถุดิบ");
			 
			 Field_1.setPrimaryKey(true);
			 Field_1.setHidden(true);
			 //Field_2.setForeignKey("CastingProductDS_"+job_id+".sub_job_id"); 
			 Field_2.setHidden(true);
			 
			 //setFields(Field_1, Field_2, Field_3, Field_4, Field_5, Field_5_1, Field_6, Field_7, Field_8, Field_9, Field_10);
			 setFields(Field_1, Field_2, Field_3, Field_4, Field_5, Field_5_1, Field_7, Field_9, Field_10);
			 setTestData(MaterialUsedReportData.getNewRecords()); // For Test
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
