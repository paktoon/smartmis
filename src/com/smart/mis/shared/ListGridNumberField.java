package com.smart.mis.shared;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.grid.ListGridField;

public class ListGridNumberField extends ListGridField{
	
	public ListGridNumberField(String titel) {
		super(titel);
		setCellFormatter(FieldFormatter.getNumberFormat());
		setAlign(Alignment.RIGHT);
	}
	
	public ListGridNumberField(String titel, Integer width) {
		super(titel, width);
		setCellFormatter(FieldFormatter.getNumberFormat());
		setAlign(Alignment.RIGHT);
	}
	
	public ListGridNumberField() {
		setCellFormatter(FieldFormatter.getNumberFormat());
		setAlign(Alignment.RIGHT);
	}
}
