package com.smart.mis.client.function.production.order.packing;

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
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class PackingMaterialDS extends DataSource  {

		static HashMap<String, PackingMaterialDS> instance = new HashMap<String, PackingMaterialDS>();
		
		public static PackingMaterialDS getInstance(String sub_job_id, String job_id){
			if (instance.containsKey(sub_job_id)) {
				//System.out.println("get ProcessListDS for psid " + psid + " and pid " + pid);
				return instance.get(sub_job_id);
			} else {
				PackingMaterialDS process = new PackingMaterialDS(sub_job_id, job_id);
				instance.put(sub_job_id, process);
				return process;
			}
		}
		
		 public PackingMaterialDS(String sub_job_id, String job_id){
			 //this.psid = psid;
			 //this.pid = pid;
			 //setID("MaterialProcessDS_"+psid);
			 DataSourceTextField Field_1 = new DataSourceTextField("cm_id");
			 DataSourceTextField Field_2 = new DataSourceTextField("sub_job_id");
			 DataSourceTextField Field_3 = new DataSourceTextField("mid", "รหัสวัตถุดิบ");
			 DataSourceTextField Field_4 = new DataSourceTextField("mat_name", "ชื่อวัตถุดิบ");
			 DataSourceFloatField Field_5 = new DataSourceFloatField("produce_amount", "ปริมาณที่ใช้ผลิต");
			 DataSourceTextField Field_6 = new DataSourceTextField("unit", "หน่วย");

			 Field_3.setPrimaryKey(true);
			 Field_1.setHidden(true);
			 Field_2.setForeignKey("PackingProductDS_"+job_id+".sub_job_id"); 
			 Field_2.setHidden(true);
			 
			 setFields(Field_1, Field_2, Field_3, Field_4, Field_5, Field_6);
			 //setDataURL("smartmis/security/userData");
			 setTestData(PackingMaterialData.getNewRecords(sub_job_id)); // For Test
			 setClientOnly(true);
		 }
		 
//		 public CastingMaterialDS() {
//			 DataSourceTextField Field_1 = new DataSourceTextField("mpid");
//			 DataSourceTextField Field_2 = new DataSourceTextField("psid");
//			 DataSourceTextField Field_3 = new DataSourceTextField("mid", "รหัสวัตถุดิบ");
//			 DataSourceTextField Field_4 = new DataSourceTextField("mat_name", "ชื่อวัตถุดิบ");
//			 DataSourceFloatField Field_5 = new DataSourceFloatField("req_amount", "จำนวนที่ต้องการ");
//			 DataSourceTextField Field_6 = new DataSourceTextField("unit", "หน่วย");
//
//			 Field_3.setPrimaryKey(true);
//			 Field_1.setHidden(true);
//			 Field_2.setHidden(true);
//			 
//			 setFields(Field_1, Field_2, Field_3, Field_4, Field_5, Field_6);
//			 //setDataURL("smartmis/security/userData");
//			 setTestData(new ListGridRecord[]{}); // For Test
//			 setClientOnly(true);
//		 }
		 
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
