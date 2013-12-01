package com.smart.mis.client.function.sale.delivery;

import com.smart.mis.shared.sale.DeliveryStatus;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceEnumField;
import com.smartgwt.client.data.fields.DataSourceFloatField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class DeliveryDS extends DataSource {

	 private static DeliveryDS instance = null;
	 
	 public static DeliveryDS getInstance() {
		 if (instance == null) {
			 instance = new DeliveryDS();
		 }
		 
		 return instance;
	 }
	 
	 public static DeliveryDS resetInstance() {
		 instance = new DeliveryDS();
		 return instance;
	 }
	 
	 public DeliveryDS(){
		 setID("DeliveryDS");
		 DataSourceTextField Field_0 = new DataSourceTextField("delivery_id", "รหัสรายการนำส่งสินค้า");
		 Field_0.setPrimaryKey(true);
		 
		 DataSourceTextField Field_1 = new DataSourceTextField("sale_id", "รหัสรายการขาย");
		 DataSourceTextField Field_1_1 = new DataSourceTextField("invoice_id", "รหัสใบแจ้งหนี้");
		 DataSourceTextField Field_2 = new DataSourceTextField("cid", "รหัสลูกค้า");
		 Field_2.setForeignKey("CustomerDS.cid");
		 DataSourceTextField Field_2_1 = new DataSourceTextField("cus_name", "ชื่อลูกค้า");
//		 DataSourceTextField Field_2_2 = new DataSourceTextField("payment_model", "วิธีการชำระเงิน");
//		 DataSourceIntegerField Field_2_3 = new DataSourceIntegerField("credit", "เครดิต");
		 
		 //DataSourceDateField Field_3 = new DataSourceDateField("from", "เริ่ม");
		 //DataSourceDateField Field_4 = new DataSourceDateField("to", "สิ้นสุด");
		 DataSourceDateField Field_5 = new DataSourceDateField("delivery", "กำหนดส่งสินค้า");
		 //DataSourceDateField Field_5_1 = new DataSourceDateField("due_date", "กำหนดชำระเงิน");
		 
		 DataSourceFloatField Field_6 = new DataSourceFloatField("total_weight", "น้ำหนักรวม (กรัม)");
		 DataSourceIntegerField Field_7 = new DataSourceIntegerField("total_amount", "จำนวนรวม (ชิ้น)");
		 
		 DataSourceFloatField Field_8 = new DataSourceFloatField("total_issued_weight", "น้ำหนักที่เบิกรวม (กรัม)");
		 DataSourceIntegerField Field_9 = new DataSourceIntegerField("total_issued_amount", "จำนวนที่เบิกรวม (ชิ้น)");
		 DataSourceDateField Field_10_1 = new DataSourceDateField("issued_date", "เบิกสินค้าวันที่");
		 DataSourceTextField Field_10_2 = new DataSourceTextField("issued_by", "จ่ายสินค้าโดย");
		 
//		 DataSourceFloatField Field_8 = new DataSourceFloatField("netExclusive", "ราคารวม (บาท)");
//		 DataSourceFloatField Field_9 = new DataSourceFloatField("tax", "ภาษีมูลค่าเพิ่ม (7%)");
//		 DataSourceFloatField Field_10 = new DataSourceFloatField("netInclusive", "ราคาสุทธิ (บาท)");
		 
		 DataSourceDateField Field_11 = new DataSourceDateField("created_date", "สร้างวันที่");
		 DataSourceTextField Field_12 = new DataSourceTextField("created_by", "สร้างโดย");
		 Field_12.setForeignKey("UserDS.uid");
		 DataSourceDateField Field_13 = new DataSourceDateField("modified_date", "แก้ไขวันที่");
		 DataSourceTextField Field_14 = new DataSourceTextField("modified_by", "แก้ไขโดย");
		 Field_14.setForeignKey("UserDS.uid");
		 
		 //DataSourceTextField Field_15 = new DataSourceTextField("comment", "ความคิดเห็น");
		 //DataSourceTextField Field_16 = new DataSourceTextField("status", "สถานะ");
		 DataSourceEnumField Field_16 = new DataSourceEnumField("status", "สถานะ");
		 DataSourceEnumField Field_17 = new DataSourceEnumField("issued_status", "สถานะ");
		 //Field_16.setValueMap("กำลังนำส่ง", "นำส่งแล้ว");
		 Field_16.setValueMap(DeliveryStatus.getValueMap());
		 Field_17.setValueMap(DeliveryStatus.getIssueValueMap());
		 
		 setFields(Field_0, Field_1, Field_1_1, Field_2, Field_2_1, Field_5, Field_6 ,Field_7, Field_8, Field_9, Field_10_1, Field_10_2, Field_11, Field_12, Field_13, Field_14, Field_16,Field_17);
		 
		 //setDataURL("smartmis/security/userData");
		 setTestData(DeliveryData.getNewRecords()); // For Test
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
