package com.smart.mis.client.function.sale.invoice;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.smart.mis.client.function.sale.customer.CustomerDS;
import com.smart.mis.client.function.sale.quotation.QuotationDS;
import com.smart.mis.client.function.sale.quotation.QuoteViewWindow;
import com.smart.mis.client.function.sale.quotation.product.QuoteProductDS;
import com.smart.mis.shared.EditorListGrid;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.ListGridNumberField;
import com.smart.mis.shared.sale.Customer;
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

public class InvoiceReviewTab {
	
	public Tab getReviewTab(final User currentUser){
		Tab reviseTab = new Tab("ข้อมูลใบแจ้งหนี้", "icons/16/search-good-icon.png");
		VLayout reviseLayout = new VLayout();
		reviseLayout.setWidth(750);
		reviseLayout.setHeight100();
		
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
		searchForm.setDataSource(InvoiceDS.getInstance());
		searchForm.setUseAllDataSourceFields(false);
		searchForm.setGroupTitle("ค้นหาใบแจ้งหนี้");
		
		final TextItem invoiceText = new TextItem("invoice_id", "รหัสใบแจ้งหนี้");
		invoiceText.setWrapTitle(false);
		invoiceText.setOperator(OperatorId.REGEXP);
		final TextItem saleText = new TextItem("sale_id", "รหัสรายการขาย");
		saleText.setWrapTitle(false);
		saleText.setOperator(OperatorId.REGEXP);
		final TextItem cidText = new TextItem("cid", "รหัสลูกค้า");
		cidText.setWrapTitle(false);
		cidText.setOperator(OperatorId.REGEXP);
		final SelectItem statusSelected = new SelectItem("status", "สถานะ");
		statusSelected.setWrapTitle(false);
		//statusSelected.setValueMap("รอชำระเงิน", "ชำระเงินแล้ว", "เกินกำหนดชำระเงิน");
		statusSelected.setValueMap(InvoiceStatus.getValueMap());
		statusSelected.setAllowEmptyValue(true);
		statusSelected.setOperator(OperatorId.EQUALS);
        
		final DynamicForm dateForm = new DynamicForm();
		dateForm.setWidth(300); 
		dateForm.setHeight(30);
		dateForm.setMargin(5); 
		dateForm.setNumCols(2);
		dateForm.setCellPadding(2);
		dateForm.setSelectOnFocus(true);
		dateForm.setIsGroup(true);
		dateForm.setGroupTitle("วันที่ครบกำหนดชำระเงิน");
		DateRange dateRange = new DateRange();  
        dateRange.setRelativeStartDate(new RelativeDate("-1m"));
        dateRange.setRelativeEndDate(new RelativeDate("+1m"));
		final DateItem from = new DateItem("due_from" , "ตั้งแต่");
		final DateItem to = new DateItem("due_to" , "ถึง");
		from.setDefaultChooserDate(dateRange.getStartDate());
		from.setDefaultValue(dateRange.getStartDate());
		from.setUseTextField(true);
        to.setDefaultChooserDate(dateRange.getEndDate());
        to.setDefaultValue(dateRange.getEndDate());
        to.setUseTextField(true);

        searchForm.setItems(invoiceText,saleText,cidText,statusSelected);
        //searchForm.setItems(Text, cidText, cnameText);
        dateForm.setItems(from, to);
        
		final ListGrid invoiceListGrid = new EditorListGrid(new InvoiceViewWindow(), currentUser);
 
		invoiceListGrid.setAutoFetchData(true);  
		invoiceListGrid.setCanMultiSort(true);
		invoiceListGrid.setCriteria(new Criterion("status", OperatorId.NOT_EQUAL, "4_canceled"));
		
		invoiceListGrid.setDataSource(InvoiceDS.getInstance());
		invoiceListGrid.setInitialSort(new SortSpecifier[]{ 
                new SortSpecifier("status", SortDirection.ASCENDING),
                new SortSpecifier("due_date", SortDirection.DESCENDING)  
        });
		invoiceListGrid.setUseAllDataSourceFields(false);
		invoiceListGrid.setGroupByField("status");
		invoiceListGrid.setGroupStartOpen(GroupStartOpen.ALL);
		
		ListGridField invoice_id = new ListGridField("invoice_id" , 100);
		ListGridField sale_id = new ListGridField("sale_id" , 100);
		//ListGridField quote_id = new ListGridField("quote_id" , 90);
		ListGridField cus_name = new ListGridField("cus_name", 200);
		ListGridField status = new ListGridField("status");
		
//		LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
//		valueMap.put("3_approved", "approved");
//		status.setValueMap(valueMap);
//		ListGridField total_amount = new ListGridField("total_amount", 120);
//		total_amount.setCellFormatter(FieldFormatter.getNumberFormat());
//		total_amount.setAlign(Alignment.RIGHT);
		ListGridField netInclusive = new ListGridField("netInclusive", 125);
		netInclusive.setCellFormatter(FieldFormatter.getPriceFormat());
		netInclusive.setAlign(Alignment.RIGHT);
		ListGridField due_date = new ListGridField("due_date", 120);
//		created_date.setType(ListGridFieldType.DATE);
//		created_date.setCellFormatter(new CellFormatter() {
//
//			@Override
//			public String format(Object arg0, ListGridRecord arg1, int arg2, int arg3) {
//			DateTimeFormat fmt = DateTimeFormat.getFormat("MM/dd/yyyy");
//			return fmt.format(arg1.getAttributeAsDate("created_date"));
//			}
//		});
			
		ListGridField iconField = new ListGridField("viewInvoiceField", "เรียกดู/พิมพ์", 100);
		
		invoiceListGrid.setFields(status, invoice_id, sale_id, cus_name, netInclusive, due_date, iconField);
		
		invoiceListGrid.hideField("status");

		searchLayout.addMembers(searchForm, dateForm);
		reviseLayout.addMember(searchLayout);
		
		HLayout buttonLayout = new HLayout();
		buttonLayout.setMargin(10);
		buttonLayout.setMembersMargin(5);
		buttonLayout.setHeight(30);
		IButton searchButton = new IButton("ค้นหาใบแจ้งหนี้");
		searchButton.setIcon("icons/16/icon_view.png");
		searchButton.setWidth(120);
		searchButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	Criterion search = new Criterion();
            	search.addCriteria(searchForm.getValuesAsCriteria());
                AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
          		      new Criterion("status", OperatorId.NOT_EQUAL, "4_canceled"),
          		      new Criterion("due_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate()),
          		      search
          		  });
              invoiceListGrid.fetchData(criteria);  
              invoiceListGrid.deselectAllRecords();
          }
        });
		
		IButton listAllButton = new IButton("แสดงรายการทั้งหมด");
		listAllButton.setIcon("[SKIN]actions/refresh.png");
		listAllButton.setWidth(150);
		listAllButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
                AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
          		      new Criterion("status", OperatorId.NOT_EQUAL, "4_canceled"),
          		      new Criterion("due_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate())
          		  });
                searchForm.reset();
                invoiceListGrid.fetchData(criteria);  
                invoiceListGrid.deselectAllRecords();
          }
        });
		
//		IButton cancelQuoteButton = new IButton("ยกเลิกใบเสนอราคา");
//		cancelQuoteButton.setIcon("icons/16/close.png");
//		cancelQuoteButton.setWidth(150);
//		cancelQuoteButton.addClickHandler(new ClickHandler() {  
//            public void onClick(ClickEvent event) { 
//            	ListGridRecord selected = invoiceListGrid.getSelectedRecord();
//            	if (selected == null) {
//            		SC.warn("กรุณาเลือกใบเสนอราคาที่ต้องการยกเลิก");
//            		return;
//            	}
//            	SC.confirm("ยืนยันการทำรายการ", "ต้องการยกเลิกใบเสนอราคา หรือไม่?" , new BooleanCallback() {
//					@Override
//					public void execute(Boolean value) {
//						if (value) {
//							ListGridRecord selected = invoiceListGrid.getSelectedRecord();
//			            	if (selected != null) {
//			            		//Do something with DB
//			            		selected.setAttribute("status", "4_canceled");
//			            		invoiceListGrid.updateData(selected);
//			            		invoiceListGrid.removeSelectedData(new DSCallback() {
//									@Override
//									public void execute(DSResponse dsResponse, Object data,
//											DSRequest dsRequest) {
//											if (dsResponse.getStatus() != 0) {
//												SC.warn("การยกเลิกใบเสนอราคา ล้มเหลว");
//											} else { 
//												SC.warn("การยกเลิกใบเสนอราคา เสร็จสมบูรณ์");
//											}
//									}
//								}, null);
//			            	} else {
//			            		SC.warn("กรุณาเลือกรายการที่ต้องการลบ");
//			            	}
//						}
//					}
//            	});
//          }
//        });

//		buttonLayout.addMembers(searchButton, listAllButton, cancelQuoteButton);
		buttonLayout.addMembers(searchButton, listAllButton);
		reviseLayout.addMember(buttonLayout);
		
		VLayout gridLayout = new VLayout();
		gridLayout.setWidth100();
		gridLayout.setHeight(355);
		
		gridLayout.addMember(invoiceListGrid);
		reviseLayout.addMember(gridLayout);
		
		reviseTab.setPane(reviseLayout);
		return reviseTab;
	}
}
