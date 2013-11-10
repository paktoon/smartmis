package com.smart.mis.client.function.sale.order.product;

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

public class SaleProductDS extends DataSource  {

		 static HashMap<String, SaleProductDS> instance = new HashMap<String, SaleProductDS>();
	
		 public static SaleProductDS getInstance(String ref_id){
				if (instance.containsKey(ref_id)) {
					return instance.get(ref_id);
				} else {
					SaleProductDS item = new SaleProductDS(ref_id);
					instance.put(ref_id, item);
					return item;
				}
			}
		 
		 public SaleProductDS(){ 
			 DataSourceTextField Field_1 = new DataSourceTextField("pid", "รหัสสินค้า");
			 Field_1.setPrimaryKey(true);
			 DataSourceTextField Field_2 = new DataSourceTextField("name", "รายการสินค้า");
			 DataSourceTextField Field_3 = new DataSourceTextField("type", "ประเภท");
			 DataSourceFloatField Field_5 = new DataSourceFloatField("weight", "น้ำหนัก (กรัม)");
			 DataSourceIntegerField Field_6 = new DataSourceIntegerField("sale_amount", "จำนวน");
			 DataSourceTextField Field_7 = new DataSourceTextField("unit", "หน่วย");
			 DataSourceFloatField Field_8 = new DataSourceFloatField("price", "ราคา (บาท)");
			 DataSourceFloatField Field_9 = new DataSourceFloatField("sum_price", "ราคารวม (บาท)");
			 
			 setFields(Field_1, Field_2, Field_3, Field_5, Field_6 ,Field_7, Field_8,  Field_9);
			 
			 //setDataURL("smartmis/security/userData");
			 setTestData(new ListGridRecord[]{}); // For create tab
			 setClientOnly(true);
		 }
		 
		 public SaleProductDS(String ref_id){
			 DataSourceTextField sub_id_field = new DataSourceTextField("sub_id");
			 sub_id_field.setHidden(true);
			 DataSourceTextField ref_id_field = new DataSourceTextField("ref_id");
			 ref_id_field.setHidden(true);
			 
			 DataSourceTextField Field_1 = new DataSourceTextField("pid", "รหัสสินค้า");
			 Field_1.setPrimaryKey(true);
			 DataSourceTextField Field_2 = new DataSourceTextField("name", "รายการสินค้า");
			 DataSourceTextField Field_3 = new DataSourceTextField("type", "ประเภท");
			 DataSourceFloatField Field_5 = new DataSourceFloatField("weight", "น้ำหนัก (กรัม)");
			 DataSourceIntegerField Field_6 = new DataSourceIntegerField("sale_amount", "จำนวน");
			 DataSourceTextField Field_7 = new DataSourceTextField("unit", "หน่วย");
			 DataSourceFloatField Field_8 = new DataSourceFloatField("price", "ราคา (บาท)");
			 DataSourceFloatField Field_9 = new DataSourceFloatField("sum_price", "ราคารวม (บาท)");
			 
			 setFields(sub_id_field, ref_id_field, Field_1, Field_2, Field_3, Field_5, Field_6 ,Field_7, Field_8,  Field_9);
			 
			 //setDataURL("smartmis/security/userData");
			 setTestData(SaleProductData.getRecords(ref_id)); // For edit tab
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
