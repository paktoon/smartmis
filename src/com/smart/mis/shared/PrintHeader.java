package com.smart.mis.shared;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class PrintHeader extends HLayout {
	
	public PrintHeader(String type) {
		
		setMembersMargin(10);
		setHeight(120);
		
		Img logo = new Img("Plain-Silver-icon.png", 120, 120);
		//logo.setWidth("10%");
		addMember(logo);
		
		VLayout companyDetails = new VLayout(10);
		companyDetails.setWidth("30%");
		companyDetails.setAlign(Alignment.LEFT);
		Label companyName = new Label();
		companyName.setContents("Rich Silver Shop");
		companyName.setStyleName("printTitle");
		companyName.setWidth(200);
		companyName.setOverflow(Overflow.HIDDEN); 
		companyDetails.addMember(companyName);
		
		Label companyAddress = new Label();
		companyAddress.setContents("<br> 1211 เจริญกรุง ซอย 47/1 <br> ถนนเจริญกรุง แขวงสี่พระยา <br> เขตบางรัก กรุงเทพมหานคร 10500");
		companyAddress.setWidth("30%");
		//companyAddress.setStyleName("printDetails");
		companyAddress.setOverflow(Overflow.HIDDEN); 
		companyDetails.addMember(companyAddress);
		addMember(companyDetails);
		
		//addMember(Printing.empty());
		
		//VLayout docDetail = new VLayout(10);
		Label docName = new Label();
		docName.setWidth("10%");
		docName.setContents(type);
		docName.setAlign(Alignment.CENTER);
		docName.setOverflow(Overflow.HIDDEN); 
		docName.setStyleName("printDocHeader");
		//docDetail.addMember(docName);
		addMember(docName);
		
		VLayout docTail = new VLayout(10);
		Label docDate = new Label();
		docDate.setWidth("5%");
		docDate.setContents("วันที่พิมพ์"+ type + ": " + DateTimeFormat.getFormat( "MM/dd/yyyy" ).format(new Date()) + "<br><br><br><br><br><br><br><br><br>");
		docDate.setAlign(Alignment.RIGHT);
		docDate.setOverflow(Overflow.HIDDEN);
		docTail.addMember(docDate);
		docTail.setAlign(VerticalAlignment.TOP);
		addMember(docTail);
	}
}
