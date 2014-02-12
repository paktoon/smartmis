package com.smart.mis.shared;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.PrintWindow;
import com.smartgwt.client.widgets.layout.VLayout;

public class Printing {

	public static void show(VLayout printLayout) {
    	PrintWindow printWindow = new PrintWindow();
    	printWindow.setWidth(650);
    	printWindow.setHeight(620);
    	printWindow.setIsModal(true);
    	printWindow.setShowModalMask(true);
    	printWindow.setCanDragResize(false);
    	printWindow.setCanDragReposition(false);
    	printWindow.centerInPage();
    	printWindow.addItem(printLayout);
    	printWindow.show();
	}
	
	public static Label empty(){
		Label empty = new Label();  
		empty.setContents("");  
		empty.setAlign(Alignment.CENTER);  
		empty.setOverflow(Overflow.HIDDEN);  
		empty.setWidth("*");
		//empty.hide();
		return empty;
	}
}
