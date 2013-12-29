package com.smart.mis.client.function.financial.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.smart.mis.client.function.sale.customer.CustomerDS;
import com.smart.mis.client.function.sale.invoice.InvoiceDS;
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
import com.smartgwt.client.widgets.Canvas;
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

public class ReportReceiptLayout extends VLayout {
	Label reportDate;
	
	public ReportReceiptLayout(final User currentUser){
		//Tab reviseTab = new Tab("ข้อมูลใบแจ้งหนี้", "icons/16/search-good-icon.png");
		//VLayout reviseLayout = new VLayout();
		//reviseLayout.
		setWidth(950);
		//reviseLayout.
		setHeight100();
		
		HLayout searchLayout = new HLayout();
		searchLayout.setHeight(20);
		
		final DynamicForm searchForm = new DynamicForm();
		searchForm.setWidth(300); 
		searchForm.setHeight(30);
		searchForm.setMargin(5); 
		searchForm.setNumCols(2);
		searchForm.setCellPadding(2);
		searchForm.setAutoFocus(true);
		searchForm.setSelectOnFocus(true);
		searchForm.setIsGroup(true);
		searchForm.setDataSource(InvoiceDS.getInstance());
		searchForm.setUseAllDataSourceFields(false);
		searchForm.setGroupTitle("ค้นหาใบแจ้งหนี้");
		
//		final SelectItem statusSelected = new SelectItem("status", "สถานะ");
//		statusSelected.setWrapTitle(false);
//		statusSelected.setValueMap(InvoiceStatus.getValueMap());
//		statusSelected.setAllowEmptyValue(true);
//		statusSelected.setOperator(OperatorId.EQUALS);
		final TextItem cidText = new TextItem("cid", "รหัสลูกค้า");
		cidText.setWrapTitle(false);
		cidText.setOperator(OperatorId.REGEXP);
		final TextItem cnameText = new TextItem("cus_name", "ชื่อลูกค้า");
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
		dateForm.setGroupTitle("วันที่รับชำระเงิน");
		DateRange dateRange = new DateRange();  
        dateRange.setRelativeStartDate(new RelativeDate("-1w"));
        dateRange.setRelativeEndDate(RelativeDate.TODAY);
		final DateItem from = new DateItem("create_from" , "ตั้งแต่");
		final DateItem to = new DateItem("create_to" , "ถึง");
		from.setDefaultChooserDate(dateRange.getStartDate());
		from.setDefaultValue(dateRange.getStartDate());
		from.setUseTextField(true);
        to.setDefaultChooserDate(dateRange.getEndDate());
        to.setDefaultValue(dateRange.getEndDate());
        to.setUseTextField(true);

        searchForm.setItems(cidText, cnameText);
        //searchForm.setItems(Text, cidText, cnameText);
        dateForm.setItems(from, to);
        
		final ListGrid invoiceListGrid = new ListGrid();
 
		invoiceListGrid.setAutoFetchData(true);  
		invoiceListGrid.setCanMultiSort(true);
		invoiceListGrid.setCriteria(new Criterion("status", OperatorId.EQUALS, "2_paid"));
		invoiceListGrid.setShowRowNumbers(true);
		invoiceListGrid.setShowGridSummary(true);
		
		invoiceListGrid.setDataSource(InvoiceDS.getInstance());
		invoiceListGrid.setInitialSort(new SortSpecifier[]{ 
                new SortSpecifier("status", SortDirection.DESCENDING),
                new SortSpecifier("paid_date", SortDirection.DESCENDING)  
        });
		invoiceListGrid.setUseAllDataSourceFields(false);
		invoiceListGrid.setSelectionType(SelectionStyle.NONE);
		
		ListGridField invoice_id = new ListGridField("invoice_id" , 100);
		invoice_id.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });  
		invoice_id.setShowGridSummary(true);
        
		ListGridField sale_id = new ListGridField("sale_id" , 100);
		ListGridField status = new ListGridField("status" , 100);
		//ListGridField cid = new ListGridField("cid", 100);
		ListGridField cus_name = new ListGridField("cus_name");
		ListGridField payment_model = new ListGridField("payment_model");
        
		ListGridField netInclusive = new ListGridField("netInclusive", 100);
		netInclusive.setCellFormatter(FieldFormatter.getPriceFormat());
		netInclusive.setAlign(Alignment.RIGHT);
		netInclusive.setSummaryFunction(SummaryFunctionType.SUM);
		netInclusive.setShowGridSummary(true);
		
		ListGridField paid_date = new ListGridField("paid_date", 120);
		//ListGridField due_date = new ListGridField("due_date", 120);
		
		invoiceListGrid.setFields(paid_date, invoice_id, status, sale_id, cus_name, payment_model, netInclusive);
		
		//invoiceListGrid.hideField("status");

		searchLayout.addMembers(searchForm, dateForm);
		addMember(searchLayout);
		
		HLayout buttonLayout = new HLayout();
		buttonLayout.setMargin(10);
		buttonLayout.setMembersMargin(5);
		buttonLayout.setHeight(30);
		IButton searchButton = new IButton("ออกรายงานการรับชำระเงิน");
		searchButton.setIcon("icons/16/reports-icon.png");
		searchButton.setWidth(170);
		searchButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	Criterion search = new Criterion();
            	search.addCriteria(searchForm.getValuesAsCriteria());
                AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
                	  new Criterion("status", OperatorId.EQUALS, "2_paid"),
          		      new Criterion("paid_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate()),
          		      search
          		  });
                reportDate.setContents("ตั้งแต่วันที่ " + DateTimeFormat.getFormat( "d-M-yyyy" ).format(from.getValueAsDate()) + " ถึงวันที่ " +  DateTimeFormat.getFormat( "d-M-yyyy" ).format(to.getValueAsDate()));
                
              invoiceListGrid.fetchData(criteria);  
              invoiceListGrid.deselectAllRecords();
          }
        });
		
		IButton listAllButton = new IButton("ล้างรายการค้นหา");
		listAllButton.setIcon("[SKIN]actions/refresh.png");
		listAllButton.setWidth(150);
		listAllButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
                AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
                	  new Criterion("status", OperatorId.EQUALS, "2_paid"),
          		      new Criterion("paid_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate())
          		  });
                searchForm.reset();
                reportDate.setContents("ตั้งแต่วันที่ " + DateTimeFormat.getFormat( "d-M-yyyy" ).format(from.getValueAsDate()) + " ถึงวันที่ " +  DateTimeFormat.getFormat( "d-M-yyyy" ).format(to.getValueAsDate()));
                invoiceListGrid.fetchData(criteria);  
                invoiceListGrid.deselectAllRecords();
          }
        });
		
		IButton printButton = new IButton("พิมพ์รายงาน");
		printButton.setIcon("icons/16/print.png");
        printButton.setWidth(120);
        
		buttonLayout.addMembers(searchButton, listAllButton, printButton);
		addMember(buttonLayout);
		
		final VLayout gridLayout = new VLayout();
		gridLayout.setWidth100();
		gridLayout.setHeight(500);
		gridLayout.setMargin(10);
        Label text = new Label();
        text.setContents("รายงานการรับชำระเงินค่าสินค้า");
        text.setAlign(Alignment.CENTER);
        text.setHeight(10);
        text.setStyleName("printTitle");
        reportDate = new Label();
        reportDate.setContents("ตั้งแต่วันที่ " + DateTimeFormat.getFormat( "d-M-yyyy" ).format(from.getValueAsDate()) + " ถึงวันที่ " +  DateTimeFormat.getFormat( "d-M-yyyy" ).format(to.getValueAsDate()));
        reportDate.setAlign(Alignment.CENTER);
        reportDate.setHeight(10);
        reportDate.setStyleName("printDetails");
		
        gridLayout.addMember(text);
        gridLayout.addMember(reportDate);
		gridLayout.addMember(invoiceListGrid);
		addMember(gridLayout);
		
        printButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	Canvas.showPrintPreview(gridLayout);
          }
        });
	}
}
