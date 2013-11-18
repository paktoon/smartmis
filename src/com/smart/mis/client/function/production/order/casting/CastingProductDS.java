package com.smart.mis.client.function.production.order.casting;

import java.util.HashMap;

import com.smart.mis.client.function.production.process.MaterialProcessDS;
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

public class CastingProductDS extends DataSource  {

		 static HashMap<String, CastingProductDS> instance = new HashMap<String, CastingProductDS>();
	
		 public static CastingProductDS getInstance(String job_id){
				if (instance.containsKey(job_id)) {
					return instance.get(job_id);
				} else {
					CastingProductDS item = new CastingProductDS(job_id);
					instance.put(job_id, item);
					return item;
				}
			}
		 
		 public CastingProductDS(){ 
			 DataSourceTextField Field_1 = new DataSourceTextField("pid", "รหัสสินค้า");
			 Field_1.setPrimaryKey(true);
			 DataSourceTextField Field_2 = new DataSourceTextField("name", "รายการสินค้า");
			 DataSourceTextField Field_3 = new DataSourceTextField("type", "ประเภท");
			 DataSourceFloatField Field_4 = new DataSourceFloatField("weight", "น้ำหนัก (กรัม)");
			 DataSourceIntegerField Field_5 = new DataSourceIntegerField("plan_amount", "จำนวน");
			 DataSourceTextField Field_6 = new DataSourceTextField("unit", "หน่วย");
			 
			 DataSourceTextField Field_7_1 = new DataSourceTextField("size", "หน่วย");
			 DataSourceTextField Field_7_2 = new DataSourceTextField("width", "หน่วย");
			 DataSourceTextField Field_7_3 = new DataSourceTextField("length", "หน่วย");
			 DataSourceTextField Field_7_4 = new DataSourceTextField("height", "หน่วย");
			 DataSourceTextField Field_7_5 = new DataSourceTextField("diameter", "หน่วย");
			 DataSourceTextField Field_7_6 = new DataSourceTextField("thickness", "หน่วย");
			 
			 DataSourceTextField Field_7 = new DataSourceTextField("details", "รายละเอียดสินค้า");

			 setFields(Field_1, Field_2, Field_4, Field_3, Field_5, Field_6 ,Field_7_1, Field_7_2,  Field_7_3, Field_7_4, Field_7_5, Field_7_6, Field_7);
			 
			 //setDataURL("smartmis/security/userData");
			 setTestData(new ListGridRecord[]{}); // For create tab
			 setClientOnly(true);
		 }
		 
		 public CastingProductDS(String job_id){
			 DataSourceTextField sub_plan_id_field = new DataSourceTextField("sub_plan_id");
			 sub_plan_id_field.setHidden(true);
			 DataSourceTextField plan_id_field = new DataSourceTextField("plan_id");
			 plan_id_field.setHidden(true);
			 
			 DataSourceTextField Field_1 = new DataSourceTextField("pid", "รหัสสินค้า");
			 Field_1.setPrimaryKey(true);
			 DataSourceTextField Field_2 = new DataSourceTextField("name", "รายการสินค้า");
			 DataSourceTextField Field_3 = new DataSourceTextField("type", "ประเภท");
			 DataSourceFloatField Field_4 = new DataSourceFloatField("weight", "น้ำหนัก (กรัม)");
			 DataSourceIntegerField Field_5 = new DataSourceIntegerField("plan_amount", "จำนวน");
			 DataSourceTextField Field_6 = new DataSourceTextField("unit", "หน่วย");
			 
			 DataSourceTextField Field_7_1 = new DataSourceTextField("size", "หน่วย");
			 DataSourceTextField Field_7_2 = new DataSourceTextField("width", "หน่วย");
			 DataSourceTextField Field_7_3 = new DataSourceTextField("length", "หน่วย");
			 DataSourceTextField Field_7_4 = new DataSourceTextField("height", "หน่วย");
			 DataSourceTextField Field_7_5 = new DataSourceTextField("diameter", "หน่วย");
			 DataSourceTextField Field_7_6 = new DataSourceTextField("thickness", "หน่วย");

			 setFields(sub_plan_id_field, plan_id_field, Field_1, Field_2, Field_4, Field_3, Field_5, Field_6 ,Field_7_1, Field_7_2,  Field_7_3, Field_7_4, Field_7_5, Field_7_6);
			 
			 //setDataURL("smartmis/security/userData");
			 setTestData(CastingProductData.getRecords(job_id)); // For edit tab
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
