package com.smart.mis.client.function.purchasing.request;

import com.smart.mis.shared.purchasing.PurchaseRequestStatus;
import com.smart.mis.shared.sale.QuotationStatus;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceEnumField;
import com.smartgwt.client.data.fields.DataSourceFloatField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class PurchaseRequestDS extends DataSource {

	 private static PurchaseRequestDS instance = null;
	 
	 public static PurchaseRequestDS getInstance() {
		 if (instance == null) {
			 instance = new PurchaseRequestDS();
		 }
		 
		 return instance;
	 }
	 
	 public static PurchaseRequestDS resetInstance() {
		 instance = new PurchaseRequestDS();
		 return instance;
	 }
	 
	 public PurchaseRequestDS(){
		 setID("PurchaseRequestDS");
		 DataSourceTextField Field_1 = new DataSourceTextField("request_id", "รหัสใบเสนอซื้อ");
		 Field_1.setPrimaryKey(true);
		 
		 DataSourceTextField Field_2_1 = new DataSourceTextField("sid", "รหัสผู้จำหน่าย");
		 DataSourceTextField Field_2_2 = new DataSourceTextField("sup_name", "ชื่อผู้จำหน่าย");
		 
		 DataSourceTextField Field_3_1 = new DataSourceTextField("quote_id", "รหัสใบเสนอราคา");
		 DataSourceTextField Field_3_2 = new DataSourceTextField("payment_model", "วิธีการชำระเงิน");
		 DataSourceIntegerField Field_3_3 = new DataSourceIntegerField("credit", "เครดิต");
		 
		 DataSourceDateField Field_3 = new DataSourceDateField("from", "เริ่ม");
		 DataSourceDateField Field_4 = new DataSourceDateField("to", "สิ้นสุด");
		 DataSourceDateField Field_5 = new DataSourceDateField("delivery", "กำหนดส่งสินค้า");
		 
		 DataSourceFloatField Field_6 = new DataSourceFloatField("total_weight", "น้ำหนักรวม (กรัม)");
		 DataSourceFloatField Field_7 = new DataSourceFloatField("total_amount", "จำนวนรวม");
		 
		 DataSourceFloatField Field_8 = new DataSourceFloatField("netExclusive", "ราคารวม (บาท)");
		 DataSourceFloatField Field_9 = new DataSourceFloatField("tax", "ภาษีมูลค่าเพิ่ม (7%)");
		 DataSourceFloatField Field_10 = new DataSourceFloatField("netInclusive", "ราคาสุทธิ (บาท)");
		 
		 DataSourceDateField Field_11 = new DataSourceDateField("created_date", "สร้างวันที่");
		 DataSourceTextField Field_12 = new DataSourceTextField("created_by", "สร้างโดย");
		 Field_12.setForeignKey("UserDS.uid");
		 DataSourceDateField Field_13 = new DataSourceDateField("modified_date", "แก้ไขวันที่");
		 DataSourceTextField Field_14 = new DataSourceTextField("modified_by", "แก้ไขโดย");
		 Field_14.setForeignKey("UserDS.uid");
		 
		 DataSourceTextField Field_15 = new DataSourceTextField("comment", "ความคิดเห็น");
		 DataSourceEnumField Field_16 = new DataSourceEnumField("status", "สถานะ");
		 //Field_16.setValueMap("รอแก้ไข" , "รออนุมัติ" , "อนุมัติแล้ว" , "ยกเลิก");
		 Field_16.setValueMap(PurchaseRequestStatus.getValueMap());
		 
		 setFields(Field_1, Field_2_1, Field_2_2, Field_3_1,Field_3_2,Field_3_3, Field_3, Field_4, Field_5, Field_6 ,Field_7, Field_8,  Field_9, Field_10, Field_11, Field_12, Field_13, Field_14, Field_15, Field_16);
		 
		 //setDataURL("smartmis/security/userData");
		 setTestData(PurchaseRequestData.getNewRecords()); // For Test
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
