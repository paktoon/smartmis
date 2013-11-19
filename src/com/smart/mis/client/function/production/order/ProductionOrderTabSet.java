package com.smart.mis.client.function.production.order;

import com.smart.mis.shared.security.User;
import com.smartgwt.client.widgets.tab.TabSet;

public class ProductionOrderTabSet extends TabSet {

	//Boolean allowApproved;
	User currentUser;
	
	public ProductionOrderTabSet(User user) {
		//this.allowApproved = allow;
		this.currentUser = user;
		
		setWidth100();
		setHeight100();

		OrderCreateTab createTab = new OrderCreateTab();
//		OrderReviseTab reviseTab = new OrderReviseTab();
		CastingTab castingTab = new CastingTab();
		ScrapingTab scrapingTab = new ScrapingTab();
//		OrderDeliveryTab deliveryTab = new OrderDeliveryTab();
		
		addTab(createTab.getCreateTab(user));
		addTab(castingTab.getReviseTab(user));
		addTab(scrapingTab.getReviseTab(user));
//		addTab(deliveryTab.getReviewTab(user));
//		
//		if (this.allowApproved) {
//			QuoteApproveTab approveTab = new QuoteApproveTab();
//			addTab(approveTab.getApproveTab(user));
//		}
		
	}
}
