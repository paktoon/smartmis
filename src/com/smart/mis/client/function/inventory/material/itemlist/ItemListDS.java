package com.smart.mis.client.function.inventory.material.itemlist;

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

public class ItemListDS extends DataSource  {

		static HashMap<String, ItemListDS> instance = new HashMap<String, ItemListDS>();
		
		public static ItemListDS getInstance(String ref_id, String job_id){
			if (instance.containsKey(ref_id)) {
				return instance.get(ref_id);
			} else {
				ItemListDS item = new ItemListDS(ref_id);
				instance.put(ref_id, item);
				return item;
			}
		}
		
		 public ItemListDS(String ref_id){
			 DataSourceTextField Field_1 = new DataSourceTextField("id");
			 DataSourceTextField Field_2 = new DataSourceTextField("ref_id");
			 DataSourceTextField Field_3 = new DataSourceTextField("item_id");
			 DataSourceFloatField Field_4 = new DataSourceFloatField("open_amount");
			 DataSourceFloatField Field_5 = new DataSourceFloatField("open_weight");
			 DataSourceFloatField Field_6 = new DataSourceFloatField("close_amount");
			 DataSourceFloatField Field_7 = new DataSourceFloatField("close_weight");
			 
			 Field_3.setPrimaryKey(true);
			 Field_1.setHidden(true);
			 //Field_2.setForeignKey("CastingProductDS_"+job_id+".sub_job_id"); 
			 Field_2.setHidden(true);
			 
			 setFields(Field_1, Field_2, Field_3, Field_4, Field_5, Field_6, Field_7);
			 setTestData(ItemListData.getNewRecords(ref_id)); // For Test
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
