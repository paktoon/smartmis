package com.smart.mis.client.function.production.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.smart.mis.client.function.production.plan.PlanDS;
import com.smart.mis.client.function.production.plan.PlanViewWindow;
import com.smart.mis.shared.EditorListGrid;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.FromToValidate;
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

public class OrderCreateTab {
	
	public Tab getCreateTab(final User currentUser){
		Tab reviseTab = new Tab("สร้างคำสั่งผลิต", "icons/16/comment_edit.png");
		VLayout reviseLayout = new VLayout();
		reviseLayout.setWidth(750);
		reviseLayout.setHeight100();
		
		HLayout searchLayout = new HLayout();
		searchLayout.setHeight(20);
		
		VLayout leftLayout = new VLayout();
		final DynamicForm searchForm = new DynamicForm();
		searchForm.setWidth(450); 
		searchForm.setHeight(30);
		searchForm.setMargin(5); 
		searchForm.setNumCols(4);
		searchForm.setCellPadding(2);
		searchForm.setAutoFocus(true);
		searchForm.setSelectOnFocus(true);
		searchForm.setIsGroup(true);
		searchForm.setDataSource(PlanDS.getInstance());
		searchForm.setUseAllDataSourceFields(false);
		searchForm.setGroupTitle("ค้นหาแผนการผลิต");
		
		final TextItem quoteText = new TextItem("plan_id", "รหัสแผนการผลิต");
		quoteText.setWrapTitle(false);
		quoteText.setOperator(OperatorId.REGEXP);
//		final SelectItem statusSelected = new SelectItem("status", "สถานะ");
//		statusSelected.setWrapTitle(false);
//		//statusSelected.setValueMap("รอแก้ไข", "รออนุมัติ", "อนุมัติแล้ว");
//		statusSelected.setValueMap(QuotationStatus.getValueMap());
//		statusSelected.setAllowEmptyValue(true);
//		statusSelected.setOperator(OperatorId.EQUALS);
		final TextItem cidText = new TextItem("sale_id", "รหัสรายการขาย");
		cidText.setWrapTitle(false);
		cidText.setOperator(OperatorId.REGEXP);
//		final TextItem cnameText = new TextItem("cus_name", "ชื่อลูกค้า");
//		cnameText.setWrapTitle(false);
//		cnameText.setOperator(OperatorId.REGEXP);
        
		final DynamicForm dateForm = new DynamicForm();
		dateForm.setWidth(300); 
		dateForm.setHeight(30);
		dateForm.setMargin(5); 
		dateForm.setNumCols(2);
		dateForm.setCellPadding(2);
		dateForm.setSelectOnFocus(true);
		dateForm.setIsGroup(true);
		dateForm.setGroupTitle("วันที่สร้างแผนการผลิต");
		DateRange dateRange = new DateRange();  
        dateRange.setRelativeStartDate(new RelativeDate("-1m"));
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
        //searchForm.setItems(quoteText,statusSelected, cidText, cnameText);
        searchForm.setItems(quoteText, cidText);
        dateForm.setItems(from, to);
        
		//final ListGrid planListGrid = new EditorListGrid(new PlanViewWindow(), currentUser);
		final ListGrid planListGrid = new ListGrid();
		 
		planListGrid.setAutoFetchData(true);  
		planListGrid.setCanMultiSort(true);
		
		AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
    		      new Criterion("status", OperatorId.EQUALS, "3_approved"),
    		      new Criterion("created_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate())
    		  });
		planListGrid.setCriteria(criteria);
		
		planListGrid.setDataSource(PlanDS.getInstance());
		planListGrid.setInitialSort(new SortSpecifier[]{  
				new SortSpecifier("status", SortDirection.DESCENDING),  
                new SortSpecifier("created_date", SortDirection.DESCENDING)  
        });
		planListGrid.setUseAllDataSourceFields(false);
		//planListGrid.setGroupByField("created_date");
		//planListGrid.setGroupStartOpen(GroupStartOpen.ALL);
		
		ListGridField plan_id = new ListGridField("plan_id" , 100);
		ListGridField reason = new ListGridField("reason", 200);
		ListGridField status = new ListGridField("status");
		ListGridField total_amount = new ListGridField("total_amount", 120);
		total_amount.setCellFormatter(FieldFormatter.getNumberFormat());
		total_amount.setAlign(Alignment.RIGHT);
		ListGridField total_weight = new ListGridField("total_weight", 120);
		total_weight.setCellFormatter(FieldFormatter.getNumberFormat());
		total_weight.setAlign(Alignment.RIGHT);
		ListGridField created_date = new ListGridField("created_date", 100);
		//ListGridField iconField = new ListGridField("createProductionOrderField", "ออกคำสั่งผลิต", 100);
		
		//planListGrid.setFields(status, plan_id, reason, total_amount, total_weight, created_date, iconField);
		planListGrid.setFields(status, plan_id, reason, total_amount, total_weight, created_date);
		
		//planListGrid.hideField("status");
		
		HLayout buttonLayout = new HLayout();
		buttonLayout.setMargin(10);
		buttonLayout.setMembersMargin(5);
		buttonLayout.setHeight(30);
		IButton searchButton = new IButton("ค้นหาแผนการผลิต");
		searchButton.setIcon("icons/16/icon_view.png");
		searchButton.setWidth(120);
		searchButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	Criterion search = new Criterion();
            	search.addCriteria(searchForm.getValuesAsCriteria());
                AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
          		      new Criterion("status", OperatorId.EQUALS, "3_approved"),
          		      new Criterion("created_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate()),
        		      search
          		  });
              planListGrid.fetchData(criteria);  
              planListGrid.deselectAllRecords();
          }
        });
		
		IButton listAllButton = new IButton("แสดงรายการทั้งหมด");
		listAllButton.setIcon("[SKIN]actions/refresh.png");
		listAllButton.setWidth(150);
		listAllButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
                AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
          		      new Criterion("status", OperatorId.EQUALS, "3_approved"),
          		      new Criterion("created_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate())
          		  });
                searchForm.reset();
                planListGrid.fetchData(criteria);  
                planListGrid.deselectAllRecords();
          }
        });
		
//		HLayout empty = new HLayout();
//		empty.setWidth("*");
		
		IButton createOrderButton = new IButton("ออกคำสั่งผลิต");
		createOrderButton.setIcon("icons/16/process-info-icon.png");
		createOrderButton.setWidth(150);
		createOrderButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	ListGridRecord selected = planListGrid.getSelectedRecord();
            	if (selected == null) {
            		SC.warn("กรุณาเลือกแผนการผลิตที่ต้องการออกคำสั่งผลิต");
            		return;
            	}
            	PlanViewWindow createWindow = new PlanViewWindow();
            	createWindow.show(selected, false, currentUser, 3);
          }
        });

//		buttonLayout.addMembers(searchButton, listAllButton, cancelQuoteButton);
		buttonLayout.addMembers(searchButton, listAllButton, createOrderButton);
		
		leftLayout.addMembers(searchForm, buttonLayout);
		searchLayout.addMembers(leftLayout, dateForm);
		reviseLayout.addMember(searchLayout);
		
		//reviseLayout.addMember(buttonLayout);
		
		VLayout gridLayout = new VLayout();
		gridLayout.setWidth100();
		gridLayout.setHeight(355);
		
		gridLayout.addMember(planListGrid);
		reviseLayout.addMember(gridLayout);
		
		reviseTab.setPane(reviseLayout);
		return reviseTab;
	}
}
