package com.smart.mis.client.function.inventory.product.request;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.smart.mis.client.function.inventory.product.transfer.TransferViewWindow;
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
import com.smart.mis.shared.ListGridNumberField;
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

public class DeliveryOrderLayout extends VLayout {
	
	public DeliveryOrderLayout(final User currentUser){
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
		searchForm.setDataSource(DeliveryDS.getInstance());
		searchForm.setUseAllDataSourceFields(false);
		searchForm.setGroupTitle("ค้นหารายการนำส่งสินค้า");
		
		final TextItem deliveryText = new TextItem("delivery_id", "รหัสรายการนำส่งสินค้า");
		deliveryText.setWrapTitle(false);
		deliveryText.setOperator(OperatorId.REGEXP);
		final TextItem saleText = new TextItem("sale_id", "รหัสรายการขาย");
		saleText.setWrapTitle(false);
		saleText.setOperator(OperatorId.REGEXP);
		final SelectItem statusSelected = new SelectItem("issued_status", "สถานะ");
		statusSelected.setWrapTitle(false);
		//statusSelected.setValueMap("กำลังนำส่ง", "นำส่งแล้ว");
		statusSelected.setValueMap(DeliveryStatus.getIssueValueMap());
		statusSelected.setAllowEmptyValue(true);
		statusSelected.setOperator(OperatorId.EQUALS);
		final TextItem cidText = new TextItem("cid", "รหัสลูกค้า");
		cidText.setWrapTitle(false);
		cidText.setOperator(OperatorId.REGEXP);
        
		final DynamicForm dateForm = new DynamicForm();
		dateForm.setWidth(300); 
		dateForm.setHeight(30);
		dateForm.setMargin(5); 
		dateForm.setNumCols(2);
		dateForm.setCellPadding(2);
		dateForm.setSelectOnFocus(true);
		dateForm.setIsGroup(true);
		dateForm.setGroupTitle("วันที่นำส่งสินค้า");
		DateRange dateRange = new DateRange();  
        dateRange.setRelativeStartDate(new RelativeDate("-1m"));
        dateRange.setRelativeEndDate(RelativeDate.TODAY);
		final DateItem from = new DateItem("delivery_from" , "ตั้งแต่");
		final DateItem to = new DateItem("delivery_to" , "ถึง");
		from.setDefaultChooserDate(dateRange.getStartDate());
		from.setDefaultValue(dateRange.getStartDate());
		from.setUseTextField(true);
        to.setDefaultChooserDate(dateRange.getEndDate());
        to.setDefaultValue(dateRange.getEndDate());
        to.setUseTextField(true);

        searchForm.setItems(deliveryText, statusSelected,saleText, cidText);
        //searchForm.setItems(Text, cidText, cnameText);
        dateForm.setItems(from, to);
        
		//final ListGrid deliveryListGrid = new EditorListGrid(new RequestViewWindow(), currentUser);
		final ListGrid deliveryListGrid = new ListGrid();
				
		deliveryListGrid.setAutoFetchData(true);  
		deliveryListGrid.setCanMultiSort(true);
		//deliveryListGrid.setCriteria(new Criterion("status", OperatorId.NOT_EQUAL, "ยกเลิก"));
		
		deliveryListGrid.setDataSource(DeliveryDS.getInstance());
		deliveryListGrid.setInitialSort(new SortSpecifier[]{ 
                new SortSpecifier("issued_status", SortDirection.ASCENDING),
                new SortSpecifier("delivery", SortDirection.DESCENDING)  
        });
		deliveryListGrid.setUseAllDataSourceFields(false);
		deliveryListGrid.setGroupByField("issued_status");
		deliveryListGrid.setGroupStartOpen(GroupStartOpen.ALL);
		
		ListGridField delivery_id = new ListGridField("delivery_id" , 120);
		ListGridField sale_id = new ListGridField("sale_id" , 100);
		//ListGridField quote_id = new ListGridField("quote_id" , 90);
		ListGridField cus_name = new ListGridField("cus_name", 180);
		ListGridField status = new ListGridField("issued_status");
//		LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
//		valueMap.put("3_approved", "approved");
//		status.setValueMap(valueMap);
		ListGridField total_amount = new ListGridField("total_amount", 120);
		total_amount.setCellFormatter(FieldFormatter.getNumberFormat());
		total_amount.setAlign(Alignment.RIGHT);
//		ListGridField netInclusive = new ListGridField("netInclusive", 125);
//		netInclusive.setCellFormatter(FieldFormatter.getPriceFormat());
//		netInclusive.setAlign(Alignment.RIGHT);
		ListGridField delivery_date = new ListGridField("delivery", 120);
//		created_date.setType(ListGridFieldType.DATE);
//		created_date.setCellFormatter(new CellFormatter() {
//
//			@Override
//			public String format(Object arg0, ListGridRecord arg1, int arg2, int arg3) {
//			DateTimeFormat fmt = DateTimeFormat.getFormat("MM/dd/yyyy");
//			return fmt.format(arg1.getAttributeAsDate("created_date"));
//			}
//		});
			
//		ListGridField iconField = new ListGridField("viewDeliveryField", "เรียกดูรายการ", 80);
		
		deliveryListGrid.setFields(status, delivery_id, sale_id, cus_name, total_amount, delivery_date);
		
		searchLayout.addMembers(searchForm, dateForm);
		//reviseLayout.
		addMember(searchLayout);
		
		HLayout buttonLayout = new HLayout();
		buttonLayout.setMargin(10);
		buttonLayout.setMembersMargin(5);
		buttonLayout.setHeight(30);
		IButton searchButton = new IButton("ค้นหารายการนำส่งสินค้า");
		searchButton.setIcon("icons/16/icon_view.png");
		searchButton.setWidth(150);
		searchButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	Criterion search = new Criterion();
            	search.addCriteria(searchForm.getValuesAsCriteria());
                AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
          		      new Criterion("delivery", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate()),
          		      search
          		  });
              deliveryListGrid.fetchData(criteria);  
              deliveryListGrid.deselectAllRecords();
          }
        });
		
		IButton listAllButton = new IButton("แสดงรายการทั้งหมด");
		listAllButton.setIcon("[SKIN]actions/refresh.png");
		listAllButton.setWidth(150);
		listAllButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
                AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
          		      new Criterion("delivery", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate())
          		  });
                searchForm.reset();
                deliveryListGrid.fetchData(criteria);  
                deliveryListGrid.deselectAllRecords();
          }
        });
		
		HLayout empty = new HLayout();
		empty.setWidth("*");
		IButton viewButton = new IButton("เรียกดูรายการ");
		viewButton.setIcon("icons/16/icon_view.png");
		viewButton.setWidth(150);
		viewButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	ListGridRecord selected = deliveryListGrid.getSelectedRecord();
            	if (selected == null) {
            		SC.warn("กรุณาเลือกรายการเบิกสินค้า");
            		return;
            	}
            	RequestViewWindow deliveryWindow = new RequestViewWindow();
            	deliveryWindow.show(selected, false, currentUser, 1);
          }
        });
		
		IButton receiveOrderButton = new IButton("เบิกจ่ายสินค้า");
		receiveOrderButton.setIcon("icons/16/actions-receive-icon.png");
		receiveOrderButton.setWidth(100);
		receiveOrderButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	ListGridRecord selected = deliveryListGrid.getSelectedRecord();
            	if (selected == null) {
            		SC.warn("กรุณาเลือกรายการเบิกสินค้า");
            		return;
            	}
            	
            	if (selected.getAttributeAsString("issued_status").equalsIgnoreCase("0_product_request")) {
            		RequestViewWindow deliveryWindow = new RequestViewWindow();
            		deliveryWindow.show(selected, true, currentUser, 2);
            	} else {
            		SC.warn("จ่ายสินค้าแล้ว");
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
		
		gridLayout.addMember(deliveryListGrid);
		//reviseLayout.
		addMember(gridLayout);
		
		//reviseTab.setPane(reviseLayout);
		//return reviseTab;
	}
}
