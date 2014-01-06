package com.smart.mis.client.function.inventory.product.transfer;

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

public class TransferItemDS extends DataSource  {

		 static HashMap<String, TransferItemDS> instance = new HashMap<String, TransferItemDS>();
	
		 public static TransferItemDS getInstance(String transfer_id){
				if (instance.containsKey(transfer_id)) {
					return instance.get(transfer_id);
				} else {
					TransferItemDS item = new TransferItemDS(transfer_id);
					instance.put(transfer_id, item);
					return item;
				}
			}
		 
//		 public TransferItemDS(){ 
//			 DataSourceTextField Field_1 = new DataSourceTextField("sub_transfer_id", "รหัสโอนสินค้าย่อย");
//			 Field_1.setPrimaryKey(true);
//			 DataSourceTextField Field_2 = new DataSourceTextField("transfer_id", "รหัสคำขอโอนสินค้า");
//			 DataSourceTextField Field_3 = new DataSourceTextField("pid", "รหัสสินค้า");
//			 DataSourceTextField Field_4 = new DataSourceTextField("name", "ชื่อสินค้า");
//			 DataSourceTextField Field_5 = new DataSourceTextField("type", "ชนิด");
//			 DataSourceTextField Field_6 = new DataSourceTextField("unit", "หน่วย");
//			 
//			 DataSourceFloatField Field_7_2 = new DataSourceFloatField("sent_weight", "น้ำหนักที่โอน(กรัม)");
//			 DataSourceIntegerField Field_7_3 = new DataSourceIntegerField("sent_amount", "จำนวนที่โอน(ชิ้น)");
//			 
//			 DataSourceFloatField Field_7_4 = new DataSourceFloatField("recv_weight", "น้ำหนักที่รับโอน (กรัม)");
//			 DataSourceIntegerField Field_7_5 = new DataSourceIntegerField("recv_amount", "จำนวนที่รับโอน (ชิ้น)");
//
//			 DataSourceTextField Field_8 = new DataSourceTextField("status", "สถานะ");
//			 
//			 //setFields(Field_1, Field_2, Field_4, Field_3, Field_5, Field_6 ,Field_7_1, Field_7_2,  Field_7_3, Field_7_4, Field_7_5, Field_7_6, Field_8, Field_9);
//			 setFields(Field_1, Field_2, Field_4, Field_3, Field_5, Field_6, Field_7_2, Field_7_3, Field_7_4, Field_7_5, Field_8);
//			 
//			 //setDataURL("smartmis/security/userData");
//			 setTestData(new ListGridRecord[]{}); // For create tab
//			 setClientOnly(true);
//		 }
		 
		 public TransferItemDS(String transfer_id){
			 setID("TransferItemDS_"+transfer_id);
			 DataSourceTextField Field_1 = new DataSourceTextField("sub_transfer_id", "รหัสโอนสินค้าย่อย");
			 Field_1.setPrimaryKey(true);
			 DataSourceTextField Field_2 = new DataSourceTextField("transfer_id", "รหัสคำขอโอนสินค้า");
			 DataSourceTextField Field_3 = new DataSourceTextField("pid", "รหัสสินค้า");
			 DataSourceTextField Field_4 = new DataSourceTextField("name", "ชื่อสินค้า");
			 DataSourceTextField Field_5 = new DataSourceTextField("type", "ชนิด");
			 DataSourceTextField Field_6 = new DataSourceTextField("unit", "หน่วย");
			 
			 //DataSourceTextField Field_7_1 = new DataSourceTextField("details", "รายละเอียดสินค้า");
			 //DataSourceFloatField Field_7_2 = new DataSourceFloatField("sent_weight", "น้ำหนักที่ส่ง(กรัม)");
			 //DataSourceIntegerField Field_7_3 = new DataSourceIntegerField("sent_amount", "จำนวนที่ส่ง(ชิ้น)");
			 DataSourceTextField Field_7_4 = new DataSourceTextField("status", "สถานะ");
			 
			 DataSourceFloatField Field_7_2 = new DataSourceFloatField("sent_weight", "น้ำหนักที่โอน(กรัม)");
			 DataSourceIntegerField Field_7_3 = new DataSourceIntegerField("sent_amount", "จำนวนที่โอน(ชิ้น)");
			 
			 DataSourceFloatField Field_7_5 = new DataSourceFloatField("recv_weight", "น้ำหนักที่รับ (กรัม)");
			 DataSourceIntegerField Field_7_6 = new DataSourceIntegerField("recv_amount", "จำนวนที่รับ (ชิ้น)");
			 
			 DataSourceFloatField Field_8 = new DataSourceFloatField("wage", "ค่าจ้างต่อหน่วย (บาท)");
			 DataSourceFloatField Field_9 = new DataSourceFloatField("sum_wage", "ค่าจ้างรวม (บาท)");

			 //setFields(Field_1, Field_2, Field_4, Field_3, Field_5, Field_6 ,Field_7_1, Field_7_2,  Field_7_3, Field_7_4, Field_7_5, Field_7_6, Field_8, Field_9);
			 setFields(Field_1, Field_2, Field_4, Field_3, Field_5, Field_6, Field_7_2, Field_7_3, Field_7_4, Field_7_5, Field_7_6, Field_8, Field_9);
			 
			 //setDataURL("smartmis/security/userData");
			 setTestData(TransferItemData.getRecords(transfer_id)); // For edit tab
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
