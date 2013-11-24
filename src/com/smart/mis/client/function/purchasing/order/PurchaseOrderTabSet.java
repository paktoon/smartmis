package com.smart.mis.client.function.purchasing.order;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.smartgwt.client.data.DateRange;
import com.smart.mis.client.function.production.process.ProcessData;
import com.smart.mis.client.function.production.product.ProductDS;
import com.smart.mis.client.function.purchasing.material.MaterialDS;
import com.smart.mis.client.function.sale.customer.CustomerDS;
import com.smart.mis.client.function.sale.delivery.OrderDeliveryTab;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.ListGridNumberField;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RelativeDate;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.RecordSummaryFunctionType;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SummaryFunctionType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.ListGridSummaryField;
import com.smartgwt.client.widgets.grid.SortNormalizer;
import com.smartgwt.client.widgets.grid.SummaryFunction;
import com.smartgwt.client.widgets.grid.events.CellSavedEvent;
import com.smartgwt.client.widgets.grid.events.CellSavedHandler;
import com.smartgwt.client.widgets.grid.events.EditorExitEvent;
import com.smartgwt.client.widgets.grid.events.EditorExitHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

public class PurchaseOrderTabSet extends TabSet {

	//Boolean allowApproved;
	User currentUser;
	
	public PurchaseOrderTabSet(User user) {
		//this.allowApproved = allow;
		this.currentUser = user;
		
		setWidth100();
		setHeight100();

		OrderCreateTab createTab = new OrderCreateTab();
		OrderReviseTab reviseTab = new OrderReviseTab();
		//OrderDeliveryTab deliveryTab = new OrderDeliveryTab();
		
		addTab(createTab.getCreateTab(user));
		addTab(reviseTab.getReviseTab(user));
		//addTab(deliveryTab.getReviewTab(user));
//		
//		if (this.allowApproved) {
//			QuoteApproveTab approveTab = new QuoteApproveTab();
//			addTab(approveTab.getApproveTab(user));
//		}
		
	}
}
