package com.smart.mis.shared;

import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

public class CustomDateItem extends DateItem {
	
	public CustomDateItem() {
		final TextItem textItem = new TextItem();
		//textItem.setDisabled(true);
		textItem.setAttribute("readOnly", true);
		setUseTextField(true);
		setTextFieldProperties(textItem);
	}
}
