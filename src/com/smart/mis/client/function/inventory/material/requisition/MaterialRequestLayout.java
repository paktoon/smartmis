package com.smart.mis.client.function.inventory.material.requisition;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.smart.mis.client.function.inventory.product.transfer.TransferViewWindow;
import com.smart.mis.client.function.purchasing.order.OrderViewWindow;
import com.smart.mis.client.function.sale.customer.CustomerDS;
import com.smart.mis.client.function.sale.delivery.DeliveryDS;
import com.smart.mis.client.function.sale.delivery.DeliveryViewWindow;
import com.smart.mis.client.function.sale.invoice.InvoiceDS;
import com.smart.mis.client.function.sale.invoice.InvoiceViewWindow;
import com.smart.mis.client.function.sale.quotation.QuotationDS;
import com.smart.mis.client.function.sale.quotation.QuoteViewWindow;
import com.smart.mis.client.function.sale.quotation.product.QuoteProductDS;
import com.smart.mis.shared.EditorListGrid;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.FromToValidate;
import com.smart.mis.shared.ListGridNumberField;
import com.smart.mis.shared.inventory.RequisitionStatus;
import com.smart.mis.shared.inventory.ReturnStatus;
import com.smart.mis.shared.purchasing.PurchaseOrderStatus;
import com.smart.mis.shared.sale.Customer;
import com.smart.mis.shared.sale.DeliveryStatus;
import com.smart.mis.shared.sale.InvoiceStatus;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DateRange;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RelativeDate;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.RecordSummaryFunctionType;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.SummaryFunctionType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.HeaderControl;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.events.ItemChangedHandler;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.GroupValueFunction;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.ListGridSummaryField;
import com.smartgwt.client.widgets.grid.SummaryFunction;
import com.smartgwt.client.widgets.grid.events.CellSavedEvent;
import com.smartgwt.client.widgets.grid.events.CellSavedHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.cube.CubeGrid;

public class MaterialRequestLayout extends VLayout {
	
	public MaterialRequestLayout(final User currentUser){
		//Tab reviseTab = new Tab("ข้อมูลใบแจ้งหนี้", "icons/16/search-good-icon.png");
		//VLayout reviseLayout = new VLayout();
		//reviseLayout.
		setWidth(750);
		//reviseLayout.
		setHeight100();
		
		HLayout searchLayout = new HLayout();
		searchLayout.setHeight(20);
		
		final DynamicForm searchForm = new DynamicForm();
		searchForm.setWidth(450); 
		searchForm.setHeight(30);
		searchForm.setMargin(5); 
		searchForm.setNumCols(4);
		searchForm.setCellPadding(2);
		searchForm.setAutoFocus(true);
		searchForm.setSelectOnFocus(true);
		searchForm.setIsGroup(true);
		searchForm.setDataSource(MaterialRequestDS.getInstance());
		searchForm.setUseAllDataSourceFields(false);
		searchForm.setGroupTitle("ค้นหารายการเบิกวัตถุดิบ");
		
		final TextItem saleText = new TextItem("mat_request_id", "รหัสเบิกวัตถุดิบ");
		saleText.setWrapTitle(false);
		saleText.setOperator(OperatorId.REGEXP);
		final SelectItem statusSelected = new SelectItem("status", "สถานะ");
		statusSelected.setWrapTitle(false);
		//statusSelected.setValueMap("รอผลิต", "กำลังผลิต", "พร้อมนำส่ง", "อยู่ระหว่างนำส่ง", "นำส่งแล้ว");
		statusSelected.setValueMap(RequisitionStatus.getValueMap());
		statusSelected.setAllowEmptyValue(true);
		statusSelected.setEmptyDisplayValue("ทั้งหมด");
		statusSelected.setOperator(OperatorId.EQUALS);
		final TextItem cidText = new TextItem("job_id", "รหัสคำสั่งผลิต");
		cidText.setWrapTitle(false);
		cidText.setOperator(OperatorId.REGEXP);
		final TextItem cnameText = new TextItem("smid", "รหัสช่าง");
		cnameText.setWrapTitle(false);
		cnameText.setOperator(OperatorId.REGEXP);
        
		final DynamicForm dateForm = new DynamicForm();
		dateForm.setWidth(300); 
		dateForm.setHeight(30);
		dateForm.setMargin(5); 
		dateForm.setNumCols(2);
		dateForm.setCellPadding(2);
		dateForm.setSelectOnFocus(true);
		dateForm.setIsGroup(true);
		dateForm.setGroupTitle("วันที่ขอเบิกวัตถุดิบ");
		DateRange dateRange = new DateRange();  
        dateRange.setRelativeStartDate(new RelativeDate("-7d"));
        dateRange.setRelativeEndDate(RelativeDate.TODAY);
		final DateItem from = new DateItem("create_from" , "ตั้งแต่");
		final DateItem to = new DateItem("create_to" , "ถึง");
		from.setDefaultChooserDate(dateRange.getStartDate());
		from.setDefaultValue(dateRange.getStartDate());
		from.setUseTextField(true);
        to.setDefaultChooserDate(dateRange.getEndDate());
        to.setDefaultValue(dateRange.getEndDate());
        to.setUseTextField(true);

        FromToValidate.addValidator(from, to);
        searchForm.setItems(saleText,statusSelected, cidText, cnameText);
        //searchForm.setItems(Text, cidText, cnameText);
        dateForm.setItems(from, to);
        
		//final ListGrid deliveryListGrid = new EditorListGrid(new RequestViewWindow(), currentUser);
		final ListGrid returnListGrid = new ListGrid();
		 
		returnListGrid.setAutoFetchData(true);  
		returnListGrid.setCanMultiSort(true);
		//returnListGrid.setCriteria(new Criterion("status", OperatorId.NOT_EQUAL, "4_canceled"));
		
		returnListGrid.setDataSource(MaterialRequestDS.getInstance());
		returnListGrid.setInitialSort(new SortSpecifier[]{ 
                new SortSpecifier("status", SortDirection.ASCENDING),
                new SortSpecifier("created_date", SortDirection.DESCENDING)  
        });
		returnListGrid.setUseAllDataSourceFields(false);
		returnListGrid.setGroupByField("status");
		returnListGrid.setGroupStartOpen(GroupStartOpen.ALL);
		
		ListGridField sale_id = new ListGridField("mat_request_id" , 110);
		ListGridField quote_id = new ListGridField("job_id" , 100);
		ListGridField cus_name = new ListGridField("sname", 180);
		ListGridField status = new ListGridField("status", 80);
//		ListGridField total_amount = new ListGridField("total_request_amount");
//		total_amount.setCellFormatter(FieldFormatter.getNumberFormat());
//		total_amount.setAlign(Alignment.RIGHT);
		ListGridField req_type = new ListGridField("req_type");
		ListGridField created_date = new ListGridField("req_date", 80);
		
		returnListGrid.setFields(status, sale_id, quote_id, cus_name, req_type, created_date);
		
		searchLayout.addMembers(searchForm, dateForm);
		//reviseLayout.
		addMember(searchLayout);
		
		HLayout buttonLayout = new HLayout();
		buttonLayout.setMargin(10);
		buttonLayout.setMembersMargin(5);
		buttonLayout.setHeight(30);
		IButton searchButton = new IButton("ค้นหาคำขอเบิกวัตถุดิบ");
		searchButton.setIcon("icons/16/icon_view.png");
		searchButton.setWidth(150);
		searchButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	Criterion search = new Criterion();
            	search.addCriteria(searchForm.getValuesAsCriteria());
                AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
          		      //new Criterion("status", OperatorId.NOT_EQUAL, "4_canceled"),
          		      new Criterion("created_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate()),
          		      search
          		  });
              returnListGrid.fetchData(criteria);  
              returnListGrid.deselectAllRecords();
          }
        });
		
		IButton listAllButton = new IButton("แสดงรายการทั้งหมด");
		listAllButton.setIcon("[SKIN]actions/refresh.png");
		listAllButton.setWidth(150);
		listAllButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
                AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
          		      //new Criterion("status", OperatorId.NOT_EQUAL, "4_canceled"),
          		      new Criterion("created_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate())
          		  });
                searchForm.reset();
                returnListGrid.fetchData(criteria);  
                returnListGrid.deselectAllRecords();
          }
        });
		
		HLayout empty = new HLayout();
		empty.setWidth("*");
		IButton viewButton = new IButton("เรียกดูรายการ");
		viewButton.setIcon("icons/16/icon_view.png");
		viewButton.setWidth(150);
		viewButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	ListGridRecord selected = returnListGrid.getSelectedRecord();
            	if (selected == null) {
            		SC.warn("กรุณาเลือกรายการ");
            		return;
            	}
            	RequestViewWindow requestWindow = new RequestViewWindow();
            	requestWindow.show(selected, false, currentUser, 1);
          }
        });
		
		IButton receiveOrderButton = new IButton("บันทึกเบิกจ่ายวัตถุดิบ");
		receiveOrderButton.setIcon("icons/16/actions-receive-icon.png");
		receiveOrderButton.setWidth(150);
		receiveOrderButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	ListGridRecord selected = returnListGrid.getSelectedRecord();
            	if (selected == null) {
            		SC.warn("กรุณาเลือกรายการ");
            		return;
            	}
            	
            	if (selected.getAttributeAsString("status").equalsIgnoreCase("1_requested")) {
            		RequestViewWindow requestWindow = new RequestViewWindow();
            		requestWindow.show(selected, true, currentUser, 2);
            	} else {
            		SC.warn("จ่ายวัตถุดิบแล้ว");
            	}
          }
        });

		buttonLayout.addMembers(searchButton, listAllButton, empty, viewButton, receiveOrderButton);
		//buttonLayout.addMembers(searchButton, listAllButton);
		//reviseLayout.
		addMember(buttonLayout);
		
		VLayout gridLayout = new VLayout();
		gridLayout.setWidth100();
		gridLayout.setHeight(355);
		
		gridLayout.addMember(returnListGrid);
		//reviseLayout.
		addMember(gridLayout);
		
		//reviseTab.setPane(reviseLayout);
		//return reviseTab;
	}
}
