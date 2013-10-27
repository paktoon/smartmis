package com.smart.mis.client.function.production.process;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceEnumField;
import com.smartgwt.client.data.fields.DataSourceFloatField;
import com.smartgwt.client.data.fields.DataSourceImageField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSOperationType;

public class ProcessListDS extends DataSource  {

		public String pid;
	
		 public ProcessListDS(String pid){
			 this.pid = pid;
			 DataSourceTextField Field_1 = new DataSourceTextField("id", "รหัสขั้นตอน");
			 DataSourceEnumField Field_2 = new DataSourceEnumField("type");
			 DataSourceTextField Field_3 = new DataSourceTextField("desc", "คำอธิบาย");
			 DataSourceFloatField Field_4 = new DataSourceFloatField("std_time", "ระยะเวลา");
			 Field_2.setValueMap("หล่อและขึ้นรูป", "แต่ง", "ฝังพลอย", "ขัดและติดพลอย", "บรรจุหีบห่อ");
			 Field_2.setPrimaryKey(true);
			 
			 setFields(Field_1, Field_2, Field_3, Field_4);
			 //setDataURL("smartmis/security/userData");
			 setTestData(ProcessData.getNewRecords(pid)); // For Test
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
