package com.smart.mis.client.function.financial.report;

//import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smart.mis.client.function.financial.disburse.wage.WageDS;
import com.smart.mis.client.function.inventory.material.requisition.MaterialRequestDS;
import com.smart.mis.client.function.inventory.material.returns.ReturnDS;
import com.smart.mis.client.function.inventory.product.transfer.TransferDS;
import com.smart.mis.client.function.purchasing.order.PurchaseOrderDS;
import com.smart.mis.client.function.sale.delivery.DeliveryDS;
import com.smart.mis.client.function.sale.quotation.QuotationDS;
import com.smart.mis.shared.DateTimeMapping;
import com.smart.mis.shared.EditorListGrid;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.purchasing.PurchaseOrderStatus;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DateRange;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RelativeDate;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.SummaryFunctionType;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SummaryFunction;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ReportDisburseLayout extends VLayout{

	VLayout gridLayout;
	ListGrid productListGrid, materialListGrid;
	Label reportDate;
	Label text;
	DynamicForm searchForm;
	//DateItem searchItem;
	IButton printButton;
	
//	String select_day;
//	String select_month;
//	String select_year;
	
	//String type;
	
	public ReportDisburseLayout(final User currentUser) {
		
		gridLayout = new VLayout();
		gridLayout.setWidth(950);
		gridLayout.setHeight(450);
		gridLayout.setMargin(5);
		
		//type = "product"; // "material"
		//productListGrid = new ListGrid();
		createProductResult();
		createMaterialResult();
		//createReturnResult();
		//materialListGrid = new ListGrid();
		
//		select_day = DateTimeFormat.getFormat( "d-M-yyyy" ).format( new Date() ).split( "-")[0];
//		select_month = DateTimeFormat.getFormat( "d-M-yyyy" ).format( new Date() ).split( "-")[1];
//		select_year = DateTimeFormat.getFormat( "d-M-yyyy" ).format( new Date() ).split( "-")[2];
//		select_year = Integer.toString((Integer.parseInt(select_year) + 543));
		
		setWidth(950);
		setHeight100();
		
		searchForm = new DynamicForm();
		searchForm.setWidth(750);
		searchForm.setNumCols(6);
		searchForm.setMargin(5);
		searchForm.setIsGroup(true);
		searchForm.setGroupTitle("ตัวเลือกรายงาน");

        final SelectItem typeItem = new SelectItem("typeItem", "ประเภทรายงาน");
        LinkedHashMap<String,String> typeMap = new LinkedHashMap<String,String>();
        typeMap.put("product", "รายงานการจ่ายชำระหนี้ค่าจ้างผลิต");
        typeMap.put("material", "รายงานการจ่ายชำระหนี้วัตถุดิบ");
        typeItem.setValueMap(typeMap);
        typeItem.setDefaultValue("product");
        typeItem.setWidth(180);
        typeItem.setPickListWidth(170);
		
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
        
//        typeItem.addChangedHandler(new ChangedHandler(){
//
//			@Override
//			public void onChanged(ChangedEvent event) {
//				if (typeItem.getValueAsString().equalsIgnoreCase("product")){
//					text.setContents("รายงานการจ่ายชำระหนี้ค่าจ้างผลิต");
//					gridLayout.setMembers(productListGrid);
//				} else {
//					text.setContents("รายงานการจ่ายชำระหนี้วัตถุดิบ");
//					gridLayout.setMembers(materialListGrid);
//				}
//			}        	
//        });
        
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
					text.setContents("รายงานการจ่ายชำระหนี้ค่าจ้างผลิต");
					gridLayout.setMembers(productListGrid);
				} else {
					text.setContents("รายงานการจ่ายชำระหนี้ค่าวัตถุดิบ");
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
        text.setContents("รายงานการจ่ายชำระหนี้ค่าจ้างผลิต");
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
		productListGrid.setDataSource(WageDS.getInstance());
		//productListGrid.setCriteria(new Criterion("issued_status", OperatorId.EQUALS, "1_product_issued"));
		productListGrid.setInitialSort(new SortSpecifier[]{
				new SortSpecifier("status", SortDirection.DESCENDING),
        });
		productListGrid.setUseAllDataSourceFields(false);
		
		AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
				new Criterion("status", OperatorId.EQUALS, "2_paid"),
				new Criterion("paid_date", OperatorId.EQUALS, new Date())
  		  });
		
		productListGrid.setCriteria(criteria);
		productListGrid.setShowRowNumbers(true);
		productListGrid.setShowGridSummary(true);
		
		ListGridField wage_id = new ListGridField("wage_id" , 100);
		wage_id.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });  
		wage_id.setShowGridSummary(true);
        
		ListGridField status = new ListGridField("status" , 100);
		ListGridField job_id = new ListGridField("job_id", 100);
		//ListGridField smid = new ListGridField("smid");
		ListGridField sname = new ListGridField("sname");
		
		ListGridField paid_by = new ListGridField("paid_by");
		ListGridField paid_date = new ListGridField("paid_date", 120);
		
		ListGridField total_wage = new ListGridField("total_wage", 150);
		total_wage.setCellFormatter(FieldFormatter.getPriceFormat());
		total_wage.setAlign(Alignment.RIGHT);
		total_wage.setSummaryFunction(SummaryFunctionType.SUM);
		total_wage.setShowGridSummary(true);
        
		ListGridField paidInclusive = new ListGridField("paidInclusive", 150);
		paidInclusive.setCellFormatter(FieldFormatter.getPriceFormat());
		paidInclusive.setAlign(Alignment.RIGHT);
		paidInclusive.setSummaryFunction(SummaryFunctionType.SUM);
		paidInclusive.setShowGridSummary(true);
        
		productListGrid.setFields(paid_date, wage_id, status, job_id, sname, paid_by, total_wage, paidInclusive);

	}
	
	private void createMaterialResult() {
		
		materialListGrid = new ListGrid();
		
		materialListGrid.setAutoFetchData(true);  
		materialListGrid.setCanMultiSort(true);
		materialListGrid.setDataSource(PurchaseOrderDS.getInstance());
		//materialListGrid.setCriteria(new Criterion("status", OperatorId.EQUALS, "2_issued"));
		materialListGrid.setInitialSort(new SortSpecifier[]{
				new SortSpecifier("payment_status", SortDirection.DESCENDING),
        });
		materialListGrid.setUseAllDataSourceFields(false);
		
		AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
				new Criterion("payment_status", OperatorId.EQUALS, "2_paid"),
				new Criterion("paid_date", OperatorId.EQUALS, new Date())
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
        
		ListGridField status = new ListGridField("payment_status" , 120);
		//ListGridField sid = new ListGridField("sid", 100);
		ListGridField sup_name = new ListGridField("sup_name");

		ListGridField paid_by = new ListGridField("paid_by");
		ListGridField paid_date = new ListGridField("paid_date", 120);

		ListGridField netInclusive = new ListGridField("netInclusive", 150);
		netInclusive.setCellFormatter(FieldFormatter.getPriceFormat());
		netInclusive.setAlign(Alignment.RIGHT);
		netInclusive.setSummaryFunction(SummaryFunctionType.SUM);
		netInclusive.setShowGridSummary(true);
        
		ListGridField paidInclusive = new ListGridField("paidInclusive", 150);
		paidInclusive.setCellFormatter(FieldFormatter.getPriceFormat());
		paidInclusive.setAlign(Alignment.RIGHT);
		paidInclusive.setSummaryFunction(SummaryFunctionType.SUM);
		paidInclusive.setShowGridSummary(true);
        
		materialListGrid.setFields(paid_date, order_id, status, sup_name, paid_by, netInclusive, paidInclusive);

	}
	
	private void updateDetails(Date from, Date to) {
		AdvancedCriteria criteria_p = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
				new Criterion("status", OperatorId.EQUALS, "2_paid"),
				new Criterion("paid_date", OperatorId.BETWEEN_INCLUSIVE, from, to)
  		  });
		
		AdvancedCriteria criteria_m = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
				new Criterion("payment_status", OperatorId.EQUALS, "2_paid"),
				new Criterion("paid_date", OperatorId.BETWEEN_INCLUSIVE, from, to)
  		  });
		
		productListGrid.fetchData(criteria_p);
		productListGrid.deselectAllRecords();
		materialListGrid.fetchData(criteria_m);
		materialListGrid.deselectAllRecords();
	}
	
}
