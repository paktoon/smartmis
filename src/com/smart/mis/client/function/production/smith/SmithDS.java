package com.smart.mis.client.function.production.smith;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceEnumField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSOperationType;

public class SmithDS extends DataSource  {

		 private static SmithDS instance = null;
		 
		 public static SmithDS getInstance() {
			 if (instance == null) {
				 instance = new SmithDS();
			 }
			 
			 return instance;
		 }
		 
		 public static SmithDS resetInstance() {
			 instance = new SmithDS();
			 return instance;
		 }
		 
		 public SmithDS(){
			 DataSourceTextField Field_1 = new DataSourceTextField("smid", "รหัสช่าง");
			 Field_1.setPrimaryKey(true);
			 DataSourceTextField Field_2 = new DataSourceTextField("name", "ชื่อช่าง");
			 DataSourceTextField Field_3 = new DataSourceTextField("phone1", "หมายเลขโทรศัพท์ 1");
			 DataSourceTextField Field_4 = new DataSourceTextField("phone2", "หมายเลขโทรศัพท์ 2");
			 DataSourceTextField Field_5 = new DataSourceTextField("email", "อีเมล");
			 
			 DataSourceTextField Field_6_1 = new DataSourceTextField("address", "ที่อยู่");
			 DataSourceTextField Field_6_2 = new DataSourceTextField("street", "ถนน");
			 DataSourceTextField Field_6_3 = new DataSourceTextField("city", "อำเภอ/เขต");
			 DataSourceTextField Field_6_4 = new DataSourceTextField("state", "จังหวัด");
			 DataSourceIntegerField Field_6_5 = new DataSourceIntegerField("postal", "รหัสไปรษณีย์");
			 
			 DataSourceEnumField Field_7 = new DataSourceEnumField("type", "ประเภทงาน");
			 Field_7.setValueMap("หล่อขึ้นรูป", "แต่งและฝังพลอยประดับ", "ขัดและติดพลอยแมกกาไซต์");
			 
			 setFields(Field_1, Field_2, Field_3, Field_4, Field_5, Field_6_1, Field_6_2,Field_6_3, Field_6_4, Field_6_5 , Field_7);
			 //setDataURL("smartmis/security/userData");
			 setTestData(SmithData.getNewRecords()); // For Test
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
