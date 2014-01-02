package com.smart.mis.client.function.inventory.material.requisition;

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

public class MaterialRequestItemDS extends DataSource  {

		static HashMap<String, MaterialRequestItemDS> instance = new HashMap<String, MaterialRequestItemDS>();
		
		public static MaterialRequestItemDS getInstance(String request_id){
			if (instance.containsKey(request_id)) {
				return instance.get(request_id);
			} else {
				MaterialRequestItemDS item = new MaterialRequestItemDS(request_id);
				instance.put(request_id, item);
				return item;
			}
		}
		
		 public MaterialRequestItemDS(String request_id){
			 DataSourceTextField Field_1 = new DataSourceTextField("sub_request_id");
			 DataSourceTextField Field_2 = new DataSourceTextField("mat_request_id");
			 DataSourceTextField Field_3 = new DataSourceTextField("mid");
			 DataSourceTextField Field_4 = new DataSourceTextField("mname");
			 DataSourceTextField Field_5 = new DataSourceTextField("unit");
			 DataSourceTextField Field_5_1 = new DataSourceTextField("type");
			 DataSourceFloatField Field_6 = new DataSourceFloatField("request_weight");
			 DataSourceFloatField Field_7 = new DataSourceFloatField("request_amount");
			 DataSourceFloatField Field_8 = new DataSourceFloatField("issued_weight");
			 DataSourceFloatField Field_9 = new DataSourceFloatField("issued_amount");
			 
			 Field_3.setPrimaryKey(true);
			 Field_1.setHidden(true);
			 //Field_2.setForeignKey("CastingProductDS_"+job_id+".sub_job_id"); 
			 Field_2.setHidden(true);
			 
			 setFields(Field_1, Field_2, Field_3, Field_4, Field_5, Field_5_1, Field_6, Field_7, Field_8, Field_9);
			 setTestData(MaterialRequestItemData.getNewRecords(request_id)); // For Test
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
