package com.smart.mis.shared;

import java.util.Date;

import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;

public class FromToValidate {

	public static void addValidator(final DateItem fromDate, final DateItem toDate)
	{
		fromDate.addChangeHandler( new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				// TODO Auto-generated method stub
					try {
						Date from = (Date) event.getValue();
						//if (!from.before(toDate.getValueAsDate())) {
						if (from.after(toDate.getValueAsDate())) {
								SC.warn("วันที่เลือกไม่ถูกต้อง กรุณาเลือกใหม่อีกครั้ง");
								fromDate.setValue(fromDate.getValueAsDate());
							}
					} catch (Exception e) {
						fromDate.setValue(fromDate.getValueAsDate());
						SC.warn("รูปแบบวันที่ไม่ถูกต้อง กรุณากรอกด้วยรูปแบบ เดือน/วันที่/ปี เช่น 01/01/2014");
					}
				}
			});
        
		toDate.addChangeHandler( new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				// TODO Auto-generated method stub
					try {
						Date to = (Date) event.getValue();
						//if (!to.after(fromDate.getValueAsDate())) {
						if (to.before(fromDate.getValueAsDate())) {
								SC.warn("วันที่เลือกไม่ถูกต้อง กรุณาเลือกใหม่อีกครั้ง");
								toDate.setValue(toDate.getValueAsDate());
							}
					} catch (Exception e) {
						toDate.setValue(toDate.getValueAsDate());
						SC.warn("รูปแบบวันที่ไม่ถูกต้อง กรุณากรอกด้วยรูปแบบ เดือน/วันที่/ปี เช่น 01/01/2014");
					}
				} 
		});
	}
}
