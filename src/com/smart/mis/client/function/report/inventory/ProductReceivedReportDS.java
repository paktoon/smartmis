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

public class ProductReceivedReportDS extends DataSource  {

		 private static ProductReceivedReportDS instance = null;
		 
		 public static ProductReceivedReportDS getInstance() {
			 if (instance == null) {
				 instance = new ProductReceivedReportDS();
			 }
			 
			 return instance;
		 }
		 
		 public ProductReceivedReportDS(){ 
			 DataSourceTextField Field_1 = new DataSourceTextField("sub_transfer_id", "รหัสโอนสินค้าย่อย");
			 Field_1.setPrimaryKey(true);
			 DataSourceTextField Field_2 = new DataSourceTextField("transfer_id", "รหัสคำขอโอนสินค้า");
			 DataSourceTextField Field_3 = new DataSourceTextField("pid", "รหัสสินค้า");
			 DataSourceTextField Field_4 = new DataSourceTextField("name", "ชื่อสินค้า");
			 DataSourceTextField Field_5 = new DataSourceTextField("type", "ชนิด");
			 Field_5.setValueMap(ProductType.getValueMap());
			 DataSourceTextField Field_6 = new DataSourceTextField("unit", "หน่วย");
			 
			 DataSourceFloatField Field_7_2 = new DataSourceFloatField("sent_weight", "น้ำหนักที่โอน(กรัม)");
			 DataSourceIntegerField Field_7_3 = new DataSourceIntegerField("sent_amount", "จำนวนที่โอน(ชิ้น)");
			 
			 DataSourceFloatField Field_7_4 = new DataSourceFloatField("recv_weight", "น้ำหนักที่รับโอน (กรัม)");
			 DataSourceIntegerField Field_7_5 = new DataSourceIntegerField("recv_amount", "จำนวนที่รับโอน (ชิ้น)");
			 
			 DataSourceDateField Field_8 = new DataSourceDateField("received_date", "วันที่รับสินค้า");
			 
			//setFields(Field_1, Field_2, Field_4, Field_3, Field_5, Field_6 ,Field_7_1, Field_7_2,  Field_7_3, Field_7_4, Field_7_5, Field_7_6, Field_8, Field_9);
			 setFields(Field_1, Field_2, Field_4, Field_3, Field_5, Field_6, Field_7_2, Field_7_3, Field_7_4, Field_7_5, Field_8);
			 
			 //setDataURL("smartmis/security/userData");
			 setTestData(new ListGridRecord[]{}); // For create tab
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
