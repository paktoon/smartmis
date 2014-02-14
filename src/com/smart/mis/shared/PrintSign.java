package com.smart.mis.shared;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class PrintSign extends HLayout {
	
	public PrintSign(String[][] values) {
		setMembersMargin(10);
		setHeight(120);
		
		for (String[] item : values){ // expect size = 3
			if (item.length == 3) {
				VLayout component = new VLayout(20);
				component.setMargin(30);
				//component.setOverflow(Overflow.HIDDEN); 
				component.setAlign(Alignment.CENTER);
				SignLabel item_0 = new SignLabel("");
				SignLabel item_1 = new SignLabel("(ลงชื่อ).............................................."+item[0]);
				SignLabel item_2 = new SignLabel("("+item[1]+")");
				SignLabel item_3 = new SignLabel(item[2]);
				//SignLabel item_4 = new SignLabel("วันที่ "+DateTimeFormat.getFormat( "MM/dd/yyyy" ).format(new Date()));
				SignLabel item_4 = new SignLabel("วันที่ ...../...../.....");
				component.addMembers(item_0, item_1, item_2, item_3, item_4);
				addMember(component);
			}
		}
	}
}
