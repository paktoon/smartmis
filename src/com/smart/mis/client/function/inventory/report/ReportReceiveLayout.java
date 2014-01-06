package com.smart.mis.client.function.inventory.report;

//import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smart.mis.client.function.inventory.material.returns.ReturnDS;
import com.smart.mis.client.function.inventory.product.transfer.TransferDS;
import com.smart.mis.client.function.purchasing.order.PurchaseOrderDS;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DateRange;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RelativeDate;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.SummaryFunctionType;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.SummaryFunction;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ReportReceiveLayout extends VLayout{

	VLayout gridLayout;
	ListGrid productListGrid, materialListGrid, returnListGrid;
	Label reportDate;
	Label text;
	DynamicForm searchForm;
	IButton printButton;
	
	public ReportReceiveLayout(final User currentUser) {
		
		gridLayout = new VLayout();
		gridLayout.setWidth(950);
		gridLayout.setHeight(450);
		gridLayout.setMargin(10);
		
		createProductResult();
		createMaterialResult();
		createReturnResult();
		
		setWidth(950);
		setHeight100();
		
		searchForm = new DynamicForm();
		searchForm.setWidth(700);
		searchForm.setNumCols(6);
		searchForm.setMargin(5);
		searchForm.setIsGroup(true);
		searchForm.setGroupTitle("ตัวเลือกรายงาน");
		
		final SelectItem typeItem = new SelectItem("typeItem", "ประเภทรายงาน");
        LinkedHashMap<String,String> typeMap = new LinkedHashMap<String,String>();
        typeMap.put("product", "รายงานการรับสินค้า");
        typeMap.put("material", "รายงานการรับวัตถุดิบ");
        typeMap.put("return", "รายงานการคืนวัตถุดิบ");
        typeItem.setValueMap(typeMap);
        typeItem.setDefaultValue("product");
        typeItem.setWidth(140);
		
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
        
        searchForm.setFields(typeItem, from, to);
        
		HLayout buttonLayout = new HLayout();
		buttonLayout.setMargin(10);
		buttonLayout.setMembersMargin(5);
		buttonLayout.setHeight(30);
		
		IButton searchButton = new IButton("ออกรายงาน");
		searchButton.setIcon("icons/16/reports-icon.png");
		searchButton.setWidth(120);
		searchButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
                updateDetails(from.getValueAsDate(), to.getValueAsDate());
                reportDate.setContents("ตั้งแต่วันที่ " + DateTimeFormat.getFormat( "d-M-yyyy" ).format(from.getValueAsDate()) + " ถึงวันที่ " +  DateTimeFormat.getFormat( "d-M-yyyy" ).format(to.getValueAsDate()));
				if (typeItem.getValueAsString().equalsIgnoreCase("product")){
					text.setContents("รายงานการรับสินค้า");
					gridLayout.setMembers(productListGrid);
				} else if (typeItem.getValueAsString().equalsIgnoreCase("return")) {
					text.setContents("รายงานการคืนวัตถุดิบ");
					gridLayout.setMembers(returnListGrid);
				} else {
					text.setContents("รายงานการรับวัตถุดิบ");
					gridLayout.setMembers(materialListGrid);
				}
            }
        });
		
		IButton printButton = new IButton("พิมพ์รายงาน");
		printButton.setIcon("icons/16/print.png");
        printButton.setWidth(120);
		
		buttonLayout.addMembers(searchButton, printButton);
		
        final VLayout report = new VLayout();
        report.setWidth(950);
        report.setHeight(10);
        text = new Label();
        text.setContents("รายงานการรับสินค้า");
        text.setAlign(Alignment.CENTER);
        text.setHeight(10);
        text.setStyleName("printTitle");
        reportDate = new Label();
        reportDate.setContents("ตั้งแต่วันที่ " + DateTimeFormat.getFormat( "d-M-yyyy" ).format(dateRange.getStartDate()) + " ถึงวันที่ " +  DateTimeFormat.getFormat( "d-M-yyyy" ).format(dateRange.getEndDate()));
        reportDate.setAlign(Alignment.CENTER);
        reportDate.setHeight(10);
        reportDate.setStyleName("printDetails");
        report.addMember(text);
        report.addMember(reportDate);
        gridLayout.setMembers(productListGrid);
        report.addMember(gridLayout);
        
        addMember(searchForm);
        addMember(buttonLayout);
        addMember(report);
        
        printButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	Canvas.showPrintPreview(report);
          }
        });
	}
	
	private void createProductResult() {
		
		productListGrid = new ListGrid();
		
		productListGrid.setAutoFetchData(true);  
		productListGrid.setCanMultiSort(true);
		productListGrid.setDataSource(TransferDS.getInstance());
		productListGrid.setInitialSort(new SortSpecifier[]{
				new SortSpecifier("status", SortDirection.DESCENDING),
        });
		productListGrid.setUseAllDataSourceFields(false);
		
		AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
    		      new Criterion("status", OperatorId.NOT_EQUAL, "2_received"),
    		      new Criterion("received_date", OperatorId.EQUALS, new Date())
    		  });
		
		productListGrid.setCriteria(criteria);
		productListGrid.setShowRowNumbers(true);
		productListGrid.setShowGridSummary(true);
		
		ListGridField transfer_id = new ListGridField("transfer_id" , 100);
		transfer_id.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });  
		transfer_id.setShowGridSummary(true);
        
		ListGridField status = new ListGridField("status" , 100);
		ListGridField plan_id = new ListGridField("plan_id", 100);
		ListGridField received_by = new ListGridField("received_by");
		ListGridField received_date = new ListGridField("received_date");

		ListGridField total_weight = new ListGridField("total_recv_weight", 200);
		total_weight.setCellFormatter(FieldFormatter.getNumberFormat());
		total_weight.setAlign(Alignment.RIGHT);
		total_weight.setSummaryFunction(SummaryFunctionType.SUM);
		total_weight.setShowGridSummary(true);
        
		ListGridField total_amount = new ListGridField("total_recv_amount", 150);
		total_amount.setCellFormatter(FieldFormatter.getNumberFormat());
		total_amount.setAlign(Alignment.RIGHT);
		total_amount.setSummaryFunction(SummaryFunctionType.SUM);
		total_amount.setShowGridSummary(true);
        
		productListGrid.setFields(received_date, transfer_id, status, plan_id, received_by, total_weight, total_amount);

	}
	
	private void createMaterialResult() {
		
		materialListGrid = new ListGrid();
		
		materialListGrid.setAutoFetchData(true);  
		materialListGrid.setCanMultiSort(true);
		materialListGrid.setDataSource(PurchaseOrderDS.getInstance());
		materialListGrid.setInitialSort(new SortSpecifier[]{
				new SortSpecifier("received_status", SortDirection.DESCENDING),
        });
		materialListGrid.setUseAllDataSourceFields(false);
		
		AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
				new Criterion("received_status", OperatorId.EQUALS, "2_received"),
				new Criterion("received_date", OperatorId.EQUALS, new Date()) 
  		  });
		
		materialListGrid.setCriteria(criteria );
		materialListGrid.setShowRowNumbers(true);
		materialListGrid.setShowGridSummary(true);
		
		ListGridField order_id = new ListGridField("order_id" , 100);
		order_id.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });  
		order_id.setShowGridSummary(true);
        
		ListGridField received_status = new ListGridField("received_status" , 120);
		//ListGridField sid = new ListGridField("sid", 100);
		ListGridField sup_name = new ListGridField("sup_name");
		
		ListGridField received_by = new ListGridField("received_by");
		ListGridField received_date = new ListGridField("received_date");

		ListGridField total_weight = new ListGridField("total_received_weight", 130);
		total_weight.setCellFormatter(FieldFormatter.getNumberFormat());
		total_weight.setAlign(Alignment.RIGHT);
		total_weight.setSummaryFunction(SummaryFunctionType.SUM);
		total_weight.setShowGridSummary(true);
        
		ListGridField total_amount = new ListGridField("total_received_amount", 120);
		total_amount.setCellFormatter(FieldFormatter.getNumberFormat());
		total_amount.setAlign(Alignment.RIGHT);
		total_amount.setSummaryFunction(SummaryFunctionType.SUM);
		total_amount.setShowGridSummary(true);
        
		materialListGrid.setFields(received_date, order_id, received_status, sup_name, received_by, total_weight, total_amount);

	}
	
	private void createReturnResult() {
		
		returnListGrid = new ListGrid();
		
		returnListGrid.setAutoFetchData(true);  
		returnListGrid.setCanMultiSort(true);
		returnListGrid.setDataSource(ReturnDS.getInstance());
		returnListGrid.setCriteria(new Criterion("status", OperatorId.EQUALS, "2_received"));
		returnListGrid.setInitialSort(new SortSpecifier[]{
				new SortSpecifier("status", SortDirection.DESCENDING),
        });
		returnListGrid.setUseAllDataSourceFields(false);
		returnListGrid.setCriteria(new Criterion("received_date", OperatorId.EQUALS, new Date()) );
		returnListGrid.setShowRowNumbers(true);
		returnListGrid.setShowGridSummary(true);
		
		ListGridField return_id = new ListGridField("return_id" , 100);
		return_id.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });  
		return_id.setShowGridSummary(true);
        
		ListGridField status = new ListGridField("status" , 100);
		ListGridField sm_name = new ListGridField("sm_name", 180);
		ListGridField mat_name = new ListGridField("mat_name", 150);
		
		ListGridField received_by = new ListGridField("received_by");
		ListGridField received_date = new ListGridField("received_date");
		
		ListGridField total_weight = new ListGridField("total_received_weight", 130);
		total_weight.setCellFormatter(FieldFormatter.getNumberFormat());
		total_weight.setAlign(Alignment.RIGHT);
		total_weight.setSummaryFunction(SummaryFunctionType.SUM);
		total_weight.setShowGridSummary(true);
        
		returnListGrid.setFields(received_date, return_id, status, sm_name, mat_name, received_by, total_weight);
	}
	
	private void updateDetails(Date from, Date to) {
		AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
				new Criterion("received_status", OperatorId.EQUALS, "2_received"),
				new Criterion("received_date", OperatorId.BETWEEN_INCLUSIVE, from, to)
  		  });
		
		productListGrid.fetchData(criteria);
		productListGrid.deselectAllRecords();
		materialListGrid.fetchData(criteria);
		materialListGrid.deselectAllRecords();
		returnListGrid.fetchData(criteria);
		returnListGrid.deselectAllRecords();
	}
	
}
