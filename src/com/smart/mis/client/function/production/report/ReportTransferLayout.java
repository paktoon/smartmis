package com.smart.mis.client.function.production.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.smart.mis.client.function.inventory.product.transfer.TransferDS;
import com.smart.mis.client.function.production.order.casting.CastingDS;
import com.smart.mis.client.function.production.order.casting.CastingViewWindow;
import com.smart.mis.client.function.production.order.scraping.ScrapingViewWindow;
import com.smart.mis.client.function.production.plan.PlanDS;
import com.smart.mis.client.function.production.plan.PlanViewWindow;
import com.smart.mis.shared.EditorListGrid;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.FromToValidate;
import com.smart.mis.shared.financial.WagePaymentStatus;
import com.smart.mis.shared.inventory.TransferStatus;
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

public class ReportTransferLayout extends VLayout {
	
	Label reportDate;
	
	public ReportTransferLayout(final User currentUser){
		//Tab scrapingTab = new Tab("แต่งและฝังพลอย","icons/16/comment_edit.png");
		//VLayout reviseLayout = new VLayout();
		//reviseLayout.
		setWidth(950);
		//reviseLayout.
		setHeight100();
		
		HLayout searchLayout = new HLayout();
		searchLayout.setHeight(20);
		
		//VLayout leftLayout = new VLayout();
		final DynamicForm searchForm = new DynamicForm();
		searchForm.setWidth(950); 
		searchForm.setHeight(30);
		searchForm.setMargin(5); 
		searchForm.setNumCols(8);
		searchForm.setCellPadding(2);
		searchForm.setAutoFocus(true);
		searchForm.setSelectOnFocus(true);
		searchForm.setIsGroup(true);
		searchForm.setDataSource(TransferDS.getInstance());
		searchForm.setUseAllDataSourceFields(false);
		searchForm.setGroupTitle("ค้นหาคำขอโอนสินค้า");
		
//		final TextItem planText = new TextItem("transfer_id", "รหัสคำขอโอนสินค้า");
//		planText.setWrapTitle(false);
//		planText.setOperator(OperatorId.REGEXP);
		final SelectItem statusSelected = new SelectItem("status", "สถานะ");
		statusSelected.setWrapTitle(false);
		//statusSelected.setValueMap("รอแก้ไข", "รออนุมัติ", "อนุมัติแล้ว");
		statusSelected.setValueMap(TransferStatus.getValueMap());
		statusSelected.setAllowEmptyValue(true);
		statusSelected.setEmptyDisplayValue("ทั้งหมด");
		statusSelected.setOperator(OperatorId.EQUALS);
		final TextItem jidText = new TextItem("plan_id", "รหัสแผนการผลิต");
		jidText.setWrapTitle(false);
		jidText.setOperator(OperatorId.REGEXP);
//		final TextItem smidText = new TextItem("smid", "รหัสช่าง");
//		smidText.setWrapTitle(false);
//		smidText.setOperator(OperatorId.REGEXP);
        
//		final DynamicForm dateForm = new DynamicForm();
//		dateForm.setWidth(300); 
//		dateForm.setHeight(30);
//		dateForm.setMargin(5); 
//		dateForm.setNumCols(2);
//		dateForm.setCellPadding(2);
//		dateForm.setSelectOnFocus(true);
//		dateForm.setIsGroup(true);
//		dateForm.setGroupTitle("วันที่ขอโอนสินค้า");
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
        searchForm.setItems(statusSelected, jidText, from, to);
        //searchForm.setItems(planText, jidText);
//        dateForm.setItems(from, to);
        
		final ListGrid orderListGrid = new ListGrid();
		
		orderListGrid.setAutoFetchData(true);  
		orderListGrid.setCanMultiSort(true);
		orderListGrid.setShowRowNumbers(true);
		orderListGrid.setShowGridSummary(true);
		
		AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
    		      //new Criterion("status", OperatorId.NOT_EQUAL, "3_to_next_process"),
    		      //new Criterion("created_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate())
    		      new Criterion("created_date", OperatorId.BETWEEN_INCLUSIVE, dateRange.getStartDate(), dateRange.getEndDate())
    		  });
		orderListGrid.setCriteria(criteria);
		
		orderListGrid.setDataSource(TransferDS.getInstance());
		orderListGrid.setInitialSort(new SortSpecifier[]{ 
				new SortSpecifier("status", SortDirection.DESCENDING),
                new SortSpecifier("created_date", SortDirection.DESCENDING)  
        });
		orderListGrid.setUseAllDataSourceFields(false);
		orderListGrid.setSelectionType(SelectionStyle.NONE);
		
		ListGridField transfer_id = new ListGridField("transfer_id" , 100);
		transfer_id.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });  
		transfer_id.setShowGridSummary(true);
        
		ListGridField status = new ListGridField("status" , 120);
		ListGridField plan_id = new ListGridField("plan_id", 100);
		ListGridField transfer_by = new ListGridField("transfer_by");
		ListGridField transfer_date = new ListGridField("transfer_date", 150);
		
		ListGridField total_weight = new ListGridField("total_sent_weight", 120);
		total_weight.setCellFormatter(FieldFormatter.getNumberFormat());
		total_weight.setAlign(Alignment.RIGHT);
		total_weight.setSummaryFunction(SummaryFunctionType.SUM);
		total_weight.setShowGridSummary(true);
        
		ListGridField total_amount = new ListGridField("total_sent_amount", 120);
		total_amount.setCellFormatter(FieldFormatter.getIntegerFormat());
		total_amount.setAlign(Alignment.RIGHT);
		total_amount.setSummaryFunction(SummaryFunctionType.SUM);
		total_amount.setShowGridSummary(true);
		
		orderListGrid.setFields(transfer_date, transfer_id, status, plan_id, transfer_by, total_weight, total_amount);
		
		//orderListGrid.hideField("status");
		
		HLayout buttonLayout = new HLayout();
		buttonLayout.setMargin(10);
		buttonLayout.setMembersMargin(5);
		buttonLayout.setHeight(30);
		IButton searchButton = new IButton("ออกรายงานโอนสินค้า");
		searchButton.setIcon("icons/16/reports-icon.png");
		searchButton.setWidth(170);
		searchButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	//Criterion search = new Criterion();
            	//search.addCriteria(searchForm.getValuesAsCriteria());
            	AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
          		      new Criterion("created_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate()),
          		      new Criterion("status", OperatorId.REGEXP, statusSelected.getValueAsString()),
          		      new Criterion("plan_id", OperatorId.REGEXP, jidText.getValueAsString()),
            		  });
                reportDate.setContents("ตั้งแต่วันที่ " + DateTimeFormat.getFormat( "d-M-yyyy" ).format(from.getValueAsDate()) + " ถึงวันที่ " +  DateTimeFormat.getFormat( "d-M-yyyy" ).format(to.getValueAsDate()));
              orderListGrid.fetchData(criteria);  
              orderListGrid.deselectAllRecords();
          }
        });
		
		IButton listAllButton = new IButton("ล้างรายการค้นหา");
		listAllButton.setIcon("[SKIN]actions/refresh.png");
		listAllButton.setWidth(150);
		listAllButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
                AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
          		      new Criterion("created_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate())
          		  });
                searchForm.reset();
                reportDate.setContents("ตั้งแต่วันที่ " + DateTimeFormat.getFormat( "d-M-yyyy" ).format(from.getValueAsDate()) + " ถึงวันที่ " +  DateTimeFormat.getFormat( "d-M-yyyy" ).format(to.getValueAsDate()));
                orderListGrid.fetchData(criteria);  
                orderListGrid.deselectAllRecords();
          }
        });
		
		IButton printButton = new IButton("พิมพ์รายงาน");
		printButton.setIcon("icons/16/print.png");
        printButton.setWidth(120);
		
		buttonLayout.addMembers(searchButton, listAllButton, printButton);
		
		searchLayout.addMembers(searchForm);
		//reviseLayout.
		addMember(searchLayout);
		
		//reviseLayout.
		addMember(buttonLayout);
		
		final VLayout gridLayout = new VLayout();
		gridLayout.setWidth100();
		gridLayout.setHeight(500);
		gridLayout.setMargin(10);
		Label text = new Label();
        text.setContents("รายงานการโอนสินค้าเข้าคลังสินค้า");
        text.setAlign(Alignment.CENTER);
        text.setHeight(10);
        text.setStyleName("printTitle");
        reportDate = new Label();
        reportDate.setContents("ตั้งแต่วันที่ " + DateTimeFormat.getFormat( "d-M-yyyy" ).format(from.getValueAsDate()) + " ถึงวันที่ " +  DateTimeFormat.getFormat( "d-M-yyyy" ).format(to.getValueAsDate()));
        reportDate.setAlign(Alignment.CENTER);
        reportDate.setHeight(10);
        reportDate.setStyleName("printDetails");
		
		Label createDate = new Label();
		Date today = new Date();
		DateTimeFormat pattern = DateTimeFormat.getFormat("MM/dd/yyyy");
		createDate.setContents("วันที่ออกรายงาน : " + pattern.format(today));
		createDate.setWidth("15%");
		createDate.setHeight(15);
		createDate.setAlign(Alignment.LEFT);
        gridLayout.addMember(createDate);
        gridLayout.addMember(text);
        gridLayout.addMember(reportDate);
		gridLayout.addMember(orderListGrid);
		addMember(gridLayout);
		
        printButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	Canvas.showPrintPreview(gridLayout);
          }
        });
	}
}
