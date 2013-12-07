package com.smart.mis.shared;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class PrintHeader extends HLayout {
	
	public PrintHeader(String type) {
		
		setMembersMargin(10);
		setHeight(120);
		
		Img logo = new Img("Plain-Silver-icon.png", 50, 50);
		//logo.setWidth(120);
		addMember(logo);
		
		VLayout companyDetails = new VLayout(10);
		companyDetails.setWidth(150);
		companyDetails.setAlign(Alignment.LEFT);
		Label companyName = new Label();
		companyName.setContents("Rich Silver Shop");
		companyName.setStyleName("printTitle");
		companyName.setWidth(200);
		companyDetails.addMember(companyName);
		
		Label companyAddress = new Label();
		companyAddress.setContents("<br> 1211 เจริญกรุง ซอย 47/1 <br> ถนนเจริญกรุง แขวงสี่พระยา <br> เขตบางรัก กรุงเทพมหานคร 10500");
		companyAddress.setWidth(150);
		companyAddress.setStyleName("printDetails");
		companyDetails.addMember(companyAddress);
		addMember(companyDetails);
		
		VLayout docDetail = new VLayout(10);
		Label docName = new Label();
		docName.setWidth("5%");
		docName.setContents(type);
		docName.setStyleName("printDocHeader");
		docDetail.addMember(docName);
		addMember(docDetail);
	}
}
