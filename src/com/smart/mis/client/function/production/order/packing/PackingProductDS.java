package com.smart.mis.client.function.production.order.packing;

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

public class PackingProductDS extends DataSource  {

		 static HashMap<String, PackingProductDS> instance = new HashMap<String, PackingProductDS>();
	
		 public static PackingProductDS getInstance(String job_id){
				if (instance.containsKey(job_id)) {
					return instance.get(job_id);
				} else {
					PackingProductDS item = new PackingProductDS(job_id);
					instance.put(job_id, item);
					return item;
				}
			}
		 
		 public PackingProductDS(){ 
			 DataSourceTextField Field_1 = new DataSourceTextField("sub_job_id", "รหัสคำสั่งย่อย");
			 Field_1.setPrimaryKey(true);
			 DataSourceTextField Field_2 = new DataSourceTextField("job_id", "รหัสคำสั่งผลิต");
			 DataSourceTextField Field_3 = new DataSourceTextField("pid", "รหัสสินค้า");
			 DataSourceTextField Field_4 = new DataSourceTextField("name", "ชื่อสินค้า");
			 DataSourceTextField Field_5 = new DataSourceTextField("type", "ชนิด");
			 DataSourceTextField Field_6 = new DataSourceTextField("unit", "หน่วย");
			 
			 DataSourceTextField Field_7_1 = new DataSourceTextField("details", "รายละเอียดสินค้า");
			 DataSourceFloatField Field_7_2 = new DataSourceFloatField("sent_weight", "น้ำหนักที่ส่ง(กรัม)");
			 DataSourceIntegerField Field_7_3 = new DataSourceIntegerField("sent_amount", "จำนวนที่ส่ง(ชิ้น)");
			 DataSourceTextField Field_7_4 = new DataSourceTextField("status", "สถานะ");
			 
//			 DataSourceFloatField Field_7_5 = new DataSourceFloatField("recv_weight", "น้ำหนักที่รับ (กรัม)");
//			 DataSourceIntegerField Field_7_6 = new DataSourceIntegerField("recv_amount", "จำนวนที่รับ (ชิ้น)");
			 
//			 DataSourceFloatField Field_8 = new DataSourceFloatField("wage", "ค่าจ้างต่อชิ้น (บาท)");
//			 DataSourceFloatField Field_9 = new DataSourceFloatField("sum_wage", "ค่าจ้างรวม (บาท)");
			 
//			 setFields(Field_1, Field_2, Field_4, Field_3, Field_5, Field_6 ,Field_7_1, Field_7_2,  Field_7_3, Field_7_4, Field_7_5, Field_7_6, Field_8, Field_9);
			 setFields(Field_1, Field_2, Field_4, Field_3, Field_5, Field_6 ,Field_7_1, Field_7_2,  Field_7_3, Field_7_4);
			 
			 //setDataURL("smartmis/security/userData");
			 setTestData(new ListGridRecord[]{}); // For create tab
			 setClientOnly(true);
		 }
		 
		 public PackingProductDS(String job_id){
			 setID("AbradingProductDS_"+job_id);
			 DataSourceTextField Field_1 = new DataSourceTextField("sub_job_id", "รหัสคำสั่งย่อย");
			 Field_1.setPrimaryKey(true);
			 DataSourceTextField Field_2 = new DataSourceTextField("job_id", "รหัสคำสั่งผลิต");
			 DataSourceTextField Field_3 = new DataSourceTextField("pid", "รหัสสินค้า");
			 DataSourceTextField Field_4 = new DataSourceTextField("name", "ชื่อสินค้า");
			 DataSourceTextField Field_5 = new DataSourceTextField("type", "ชนิด");
			 DataSourceTextField Field_6 = new DataSourceTextField("unit", "หน่วย");
			 
			 DataSourceTextField Field_7_1 = new DataSourceTextField("details", "รายละเอียดสินค้า");
			 DataSourceFloatField Field_7_2 = new DataSourceFloatField("sent_weight", "น้ำหนักที่สั่ง (กรัม)");
			 DataSourceIntegerField Field_7_3 = new DataSourceIntegerField("sent_amount", "จำนวนที่สั่ง (ชิ้น)");
			 DataSourceTextField Field_7_4 = new DataSourceTextField("status", "สถานะ");
			 
			 DataSourceFloatField Field_7_5 = new DataSourceFloatField("recv_weight", "น้ำหนักที่รับ (กรัม)");
			 DataSourceIntegerField Field_7_6 = new DataSourceIntegerField("recv_amount", "จำนวนที่รับ (ชิ้น)");
			 
			 DataSourceFloatField Field_8 = new DataSourceFloatField("wage", "ค่าจ้างต่อชิ้น (บาท)");
			 DataSourceFloatField Field_9 = new DataSourceFloatField("sum_wage", "ค่าจ้างรวม (บาท)");

			 setFields(Field_1, Field_2, Field_4, Field_3, Field_5, Field_6 ,Field_7_1, Field_7_2,  Field_7_3, Field_7_4, Field_7_5, Field_7_6, Field_8, Field_9);
			 
			 //setDataURL("smartmis/security/userData");
			 setTestData(PackingProductData.getRecords(job_id)); // For edit tab
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
