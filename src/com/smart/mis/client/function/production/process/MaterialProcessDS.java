package com.smart.mis.client.function.production.process;

import java.util.HashMap;

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

public class MaterialProcessDS extends DataSource  {

		//public String psid;
		//public String pid;
		
		static HashMap<String, MaterialProcessDS> instance = new HashMap<String, MaterialProcessDS>();
		
		public static MaterialProcessDS getInstance(String psid, String pid){
			if (instance.containsKey(psid)) {
				return instance.get(psid);
			} else {
				MaterialProcessDS process = new MaterialProcessDS(psid, pid);
				instance.put(psid, process);
				return process;
			}
		}
		
		 public MaterialProcessDS(String psid, String pid){
			 //this.psid = psid;
			 //this.pid = pid;
			 setID("MaterialProcessDS_"+psid);
			 DataSourceTextField Field_1 = new DataSourceTextField("mpid");
			 DataSourceTextField Field_2 = new DataSourceTextField("psid");
			 DataSourceTextField Field_3 = new DataSourceTextField("mid", "รหัสวัตถุดิบ");
			 DataSourceTextField Field_4 = new DataSourceTextField("mat_name", "ชื่อวัตถุดิบ");
			 DataSourceFloatField Field_5 = new DataSourceFloatField("req_amount", "จำนวนที่ต้องการ");
			 DataSourceTextField Field_6 = new DataSourceTextField("unit", "หน่วย");

			 Field_1.setPrimaryKey(true);
			 Field_1.setHidden(true);
			 Field_2.setForeignKey("ProcessListDS_"+pid+".psid"); 
			 Field_2.setHidden(true);
			 
			 setFields(Field_1, Field_2, Field_3, Field_4, Field_5, Field_6);
			 //setDataURL("smartmis/security/userData");
			 setTestData(MaterialProcessData.getNewRecords(psid)); // For Test
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
