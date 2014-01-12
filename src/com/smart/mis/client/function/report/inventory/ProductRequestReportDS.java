package com.smart.mis.client.function.report.inventory;

import com.smart.mis.shared.prodution.ProductType;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceFloatField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ProductRequestReportDS extends DataSource  {

		 private static ProductRequestReportDS instance = null;
		 
		 public static ProductRequestReportDS getInstance() {
			 if (instance == null) {
				 instance = new ProductRequestReportDS();
			 }
			 
			 return instance;
		 }
		 
		 public ProductRequestReportDS(){ 
			 DataSourceTextField sub_id_field = new DataSourceTextField("sub_delivery_id");
			 sub_id_field.setPrimaryKey(true);
			 DataSourceTextField ref_id_field = new DataSourceTextField("delivery_id", "รหัสใบส่งสินค้า");
			 DataSourceTextField Field_1 = new DataSourceTextField("pid", "รหัสสินค้า");
			 DataSourceTextField Field_2 = new DataSourceTextField("name", "รายการสินค้า");
			 DataSourceTextField Field_3 = new DataSourceTextField("type", "ประเภท");
			 Field_3.setValueMap(ProductType.getValueMap());
			 DataSourceFloatField Field_5 = new DataSourceFloatField("sale_weight", "น้ำหนัก (กรัม)");
			 DataSourceIntegerField Field_6 = new DataSourceIntegerField("sale_amount", "จำนวน");
			 DataSourceFloatField Field_5_1 = new DataSourceFloatField("issued_weight", "น้ำหนัก (กรัม)");
			 DataSourceIntegerField Field_6_1 = new DataSourceIntegerField("issued_amount", "จำนวน");
			 DataSourceTextField Field_7 = new DataSourceTextField("unit", "หน่วย");
			 DataSourceFloatField Field_8 = new DataSourceFloatField("price", "ราคา (บาท)");
			 DataSourceFloatField Field_9 = new DataSourceFloatField("sum_price", "ราคารวม (บาท)");
			 
			 DataSourceDateField Field_10 = new DataSourceDateField("issued_date", "เบิกจ่ายวันที่");
			 
			 setFields(sub_id_field, ref_id_field, Field_1, Field_2, Field_3, Field_5, Field_6, Field_5_1, Field_6_1 ,Field_7, Field_8,  Field_9, Field_10);
			 
			 //setDataURL("smartmis/security/userData");
			 //setTestData(new ListGridRecord[]{}); // For create tab
			 setTestData(ProductRequestReportData.getNewRecords()); // For create tab
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
