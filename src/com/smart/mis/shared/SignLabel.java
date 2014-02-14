package com.smart.mis.shared;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Label;

public class SignLabel extends Label {
	
	public SignLabel(String content) {
		setContents(content);
		setOverflow(Overflow.HIDDEN); 
		setAlign(Alignment.CENTER);
		//setStyleName("printSigns");
	}
}
