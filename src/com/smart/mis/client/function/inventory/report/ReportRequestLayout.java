package com.smart.mis.client.function.inventory.report;

//import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smart.mis.client.function.inventory.material.requisition.MaterialRequestDS;
import com.smart.mis.client.function.inventory.material.returns.ReturnDS;
import com.smart.mis.client.function.inventory.product.transfer.TransferDS;
import com.smart.mis.client.function.purchasing.order.PurchaseOrderDS;
import com.smart.mis.client.function.sale.delivery.DeliveryDS;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.FromToValidate;
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

public class ReportRequestLayout extends VLayout{

	VLayout gridLayout;
	ListGrid productListGrid, materialListGrid;
	Label reportDate;
	Label text;
	DynamicForm searchForm;
	IButton printButton;
	
	public ReportRequestLayout(final User currentUser) {
		
		gridLayout = new VLayout();
		gridLayout.setWidth(950);
		gridLayout.setHeight(450);
		gridLayout.setMargin(10);
		
		createProductResult();
		createMaterialResult();
		
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
        typeMap.put("product", "รายงานการเบิกสินค้า");
        typeMap.put("material", "รายงานการเบิกวัตถุดิบ");
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
        
        FromToValidate.addValidator(from, to);
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
					text.setContents("รายงานการเบิกสินค้า");
					gridLayout.setMembers(productListGrid);
				}  else {
					text.setContents("รายงานการเบิกวัตถุดิบ");
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
        text.setContents("รายงานการเบิกสินค้า");
        text.setAlign(Alignment.CENTER);
        text.setHeight(10);
        text.setStyleName("printTitle");
        reportDate = new Label();
        reportDate.setContents("ตั้งแต่วันที่ " + DateTimeFormat.getFormat( "d-M-yyyy" ).format(dateRange.getStartDate()) + " ถึงวันที่ " +  DateTimeFormat.getFormat( "d-M-yyyy" ).format(dateRange.getEndDate()));
        reportDate.setAlign(Alignment.CENTER);
        reportDate.setHeight(10);
        reportDate.setStyleName("printDetails");
        
		Label createDate = new Label();
		Date today = new Date();
		DateTimeFormat pattern = DateTimeFormat.getFormat("MM/dd/yyyy");
		createDate.setContents("วันที่ออกรายงาน : " + pattern.format(today));
		createDate.setWidth("20%");
		createDate.setHeight(15);
		createDate.setAlign(Alignment.LEFT);
		createDate.setMargin(10);
        report.addMember(createDate);
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
		productListGrid.setDataSource(DeliveryDS.getInstance());
		//productListGrid.setCriteria(new Criterion("issued_status", OperatorId.EQUALS, "1_product_issued"));
		productListGrid.setInitialSort(new SortSpecifier[]{
				new SortSpecifier("issued_status", SortDirection.DESCENDING),
        });
		productListGrid.setUseAllDataSourceFields(false);
		
		AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
				new Criterion("issued_status", OperatorId.EQUALS, "1_product_issued"),
				new Criterion("issued_date", OperatorId.EQUALS, new Date())
  		  });
		
		productListGrid.setCriteria(criteria);
		productListGrid.setShowRowNumbers(true);
		productListGrid.setShowGridSummary(true);
		
		ListGridField delivery_id = new ListGridField("delivery_id" , 120);
		delivery_id.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });  
		delivery_id.setShowGridSummary(true);
        
		ListGridField status = new ListGridField("issued_status" , 100);
		ListGridField sale_id = new ListGridField("sale_id", 100);
		ListGridField issued_by = new ListGridField("issued_by");
		ListGridField issued_date = new ListGridField("issued_date");

		ListGridField total_weight = new ListGridField("total_issued_weight", 150);
		total_weight.setCellFormatter(FieldFormatter.getNumberFormat());
		total_weight.setAlign(Alignment.RIGHT);
		total_weight.setSummaryFunction(SummaryFunctionType.SUM);
		total_weight.setShowGridSummary(true);
        
		ListGridField total_amount = new ListGridField("total_issued_amount", 150);
		total_amount.setCellFormatter(FieldFormatter.getNumberFormat());
		total_amount.setAlign(Alignment.RIGHT);
		total_amount.setSummaryFunction(SummaryFunctionType.SUM);
		total_amount.setShowGridSummary(true);
        
		productListGrid.setFields(issued_date, delivery_id, status, sale_id, issued_by, total_weight, total_amount);

	}
	
	private void createMaterialResult() {
		
		materialListGrid = new ListGrid();
		
		materialListGrid.setAutoFetchData(true);  
		materialListGrid.setCanMultiSort(true);
		materialListGrid.setDataSource(MaterialRequestDS.getInstance());
		materialListGrid.setInitialSort(new SortSpecifier[]{
				new SortSpecifier("status", SortDirection.DESCENDING),
        });
		materialListGrid.setUseAllDataSourceFields(false);
		
		AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
				new Criterion("status", OperatorId.EQUALS, "2_issued"),
				new Criterion("issued_date", OperatorId.EQUALS, new Date())
  		  });
		
		materialListGrid.setCriteria(criteria );
		materialListGrid.setShowRowNumbers(true);
		materialListGrid.setShowGridSummary(true);
		
		ListGridField mat_request_id = new ListGridField("mat_request_id" , 120);
		mat_request_id.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });  
		mat_request_id.setShowGridSummary(true);
        
		ListGridField status = new ListGridField("status" , 120);
		ListGridField job_id = new ListGridField("job_id", 100);
		ListGridField sname = new ListGridField("sname");
		ListGridField req_type = new ListGridField("req_type");
		
		ListGridField issued_by = new ListGridField("issued_by");
		ListGridField issued_date = new ListGridField("issued_date");
		
		ListGridField total_amount = new ListGridField("total_issued_amount", 130);
		total_amount.setCellFormatter(FieldFormatter.getNumberFormat());
		total_amount.setAlign(Alignment.RIGHT);
		total_amount.setSummaryFunction(SummaryFunctionType.SUM);
		total_amount.setShowGridSummary(true);
        
		materialListGrid.setFields(issued_date, mat_request_id, status, job_id, sname,req_type, issued_by, total_amount);

	}
	
	private void updateDetails(Date from, Date to) {
		
		AdvancedCriteria criteria_p = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
				new Criterion("issued_status", OperatorId.EQUALS, "1_product_issued"),
				new Criterion("issued_date", OperatorId.BETWEEN_INCLUSIVE, from, to)
  		  });
		
		AdvancedCriteria criteria_m = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
				new Criterion("status", OperatorId.EQUALS, "2_issued"),
				new Criterion("issued_date", OperatorId.BETWEEN_INCLUSIVE, from, to)
  		  });
		
		productListGrid.fetchData(criteria_p);
		productListGrid.deselectAllRecords();
		materialListGrid.fetchData(criteria_m);
		materialListGrid.deselectAllRecords();
	}
	
}
