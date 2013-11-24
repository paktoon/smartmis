package com.smart.mis.client.function.purchasing.request.material;

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

public class RequestMaterialDS extends DataSource  {

		 static HashMap<String, RequestMaterialDS> instance = new HashMap<String, RequestMaterialDS>();
	
		 public static RequestMaterialDS getInstance(String request_id){
				if (instance.containsKey(request_id)) {
					return instance.get(request_id);
				} else {
					RequestMaterialDS item = new RequestMaterialDS(request_id);
					instance.put(request_id, item);
					return item;
				}
			}
		 
		 public RequestMaterialDS(){ 
			 DataSourceTextField Field_1 = new DataSourceTextField("mid", "รหัสวัตถุดิบ");
			 Field_1.setPrimaryKey(true);
			 DataSourceTextField Field_2 = new DataSourceTextField("mat_name", "รายการวัตถุดิบ");
			 DataSourceTextField Field_3 = new DataSourceTextField("type", "ประเภท");
			 DataSourceFloatField Field_5 = new DataSourceFloatField("weight", "น้ำหนักรวม (กรัม)");
			 DataSourceFloatField Field_6 = new DataSourceFloatField("request_amount", "จำนวน");
			 DataSourceTextField Field_7 = new DataSourceTextField("unit", "หน่วย");
			 DataSourceFloatField Field_8 = new DataSourceFloatField("price", "ราคา (บาท)");
			 DataSourceFloatField Field_9 = new DataSourceFloatField("sum_price", "ราคารวม (บาท)");
			 
			 setFields(Field_1, Field_2, Field_3, Field_5, Field_6 ,Field_7, Field_8,  Field_9);
			 
			 //setDataURL("smartmis/security/userData");
			 setTestData(new ListGridRecord[]{}); // For create tab
			 setClientOnly(true);
		 }
		 
		 public RequestMaterialDS(String request_id){
			 DataSourceTextField sub_request_id_field = new DataSourceTextField("sub_request_id");
			 sub_request_id_field.setHidden(true);
			 DataSourceTextField request_id_field = new DataSourceTextField("request_id");
			 request_id_field.setHidden(true);
			 
			 DataSourceTextField Field_1 = new DataSourceTextField("mid", "รหัสวัตถุดิบ");
			 Field_1.setPrimaryKey(true);
			 DataSourceTextField Field_2 = new DataSourceTextField("mat_name", "รายการวัตถุดิบ");
			 DataSourceTextField Field_3 = new DataSourceTextField("type", "ประเภท");
			 DataSourceFloatField Field_5 = new DataSourceFloatField("weight", "น้ำหนัก (กรัม)");
			 DataSourceFloatField Field_6 = new DataSourceFloatField("request_amount", "จำนวน");
			 DataSourceTextField Field_7 = new DataSourceTextField("unit", "หน่วย");
			 DataSourceFloatField Field_8 = new DataSourceFloatField("price", "ราคา (บาท)");
			 DataSourceFloatField Field_9 = new DataSourceFloatField("sum_price", "ราคารวม (บาท)");
			 
			 setFields(sub_request_id_field, request_id_field, Field_1, Field_2, Field_3, Field_5, Field_6 ,Field_7, Field_8,  Field_9);
			 
			 //setDataURL("smartmis/security/userData");
			 setTestData(RequestMaterialData.getRecords(request_id)); // For edit tab
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
