package com.smart.mis.client.function.production.process;

import java.util.HashMap;

import com.smart.mis.shared.prodution.ProcessType;
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

public class ProcessListDS extends DataSource  {

		public String pid;
		static HashMap<String, ProcessListDS> instance = new HashMap<String, ProcessListDS>();
		
		public static ProcessListDS getInstance(String pid){
			if (instance.containsKey(pid)) {
				System.out.println("get ProcessListDS for pid " + pid);
				return instance.get(pid);
			} else {
				System.out.println("no ProcessListDS for pid " + pid);
				System.out.println("create new empty");
				ProcessListDS process = new ProcessListDS(pid);
				instance.put(pid, process);
				return process;
			}
		}
		
		 public ProcessListDS(String pid){
			 this.pid = pid;
			 setID("ProcessListDS_"+pid);
			 DataSourceTextField Field_1 = new DataSourceTextField("psid", "รหัสขั้นตอน");
			 DataSourceEnumField Field_2 = new DataSourceEnumField("type");
			 DataSourceEnumField Field_3 = new DataSourceEnumField("desc", "รายละเอียด");
			 DataSourceIntegerField Field_4 = new DataSourceIntegerField("std_time", "ระยะเวลา");
			 Field_2.setValueMap(ProcessType.getValueMap());
			 Field_2.setPrimaryKey(true);
			 DataSourceIntegerField Field_5 = new DataSourceIntegerField("priority");

			 setFields(Field_1, Field_2, Field_3, Field_4, Field_5);
			 //setDataURL("smartmis/security/userData");
			 setTestData(ProcessData.getNewRecords(pid)); // For Test
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
