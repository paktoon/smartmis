package com.smart.mis.client.function.purchasing.request;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.smart.mis.client.function.sale.customer.CustomerDS;
import com.smart.mis.client.function.sale.quotation.product.QuoteProductDS;
import com.smart.mis.shared.EditorListGrid;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.FromToValidate;
import com.smart.mis.shared.ListGridNumberField;
import com.smart.mis.shared.purchasing.PurchaseRequestStatus;
import com.smart.mis.shared.sale.Customer;
import com.smart.mis.shared.sale.QuotationStatus;
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

public class RequestReviseTab {
	
	public Tab getReviseTab(final User currentUser){
		Tab reviseTab = new Tab("ค้นหาและแก้ไข", "icons/16/comment_edit.png");
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
		searchForm.setDataSource(PurchaseRequestDS.getInstance());
		searchForm.setUseAllDataSourceFields(false);
		searchForm.setGroupTitle("ค้นหาใบเสนอซื้อ");
		
		final TextItem quoteText = new TextItem("request_id", "รหัสใบเสนอซื้อ");
		quoteText.setWrapTitle(false);
		quoteText.setOperator(OperatorId.REGEXP);
		final SelectItem statusSelected = new SelectItem("status", "สถานะ");
		statusSelected.setWrapTitle(false);
		//statusSelected.setValueMap("รอแก้ไข", "รออนุมัติ", "อนุมัติแล้ว");
		statusSelected.setValueMap(PurchaseRequestStatus.getValueMap());
		statusSelected.setAllowEmptyValue(true);
		statusSelected.setEmptyDisplayValue("ทั้งหมด");
		statusSelected.setOperator(OperatorId.EQUALS);
		final TextItem cidText = new TextItem("sid", "รหัสผู้จำหน่าย");
		cidText.setWrapTitle(false);
		cidText.setOperator(OperatorId.REGEXP);
		final TextItem cnameText = new TextItem("sup_name", "ชื่อผู้จำหน่าย");
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
		dateForm.setGroupTitle("วันที่สร้างใบเสนอซื้อ");
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
        searchForm.setItems(quoteText,statusSelected, cidText, cnameText);
        dateForm.setItems(from, to);
        
		final ListGrid quoteListGrid = new EditorListGrid(new RequestViewWindow(), currentUser);
 
		quoteListGrid.setAutoFetchData(true);  
		quoteListGrid.setCanMultiSort(true);
		quoteListGrid.setCriteria(new Criterion("status", OperatorId.NOT_EQUAL, "4_canceled"));
		
		quoteListGrid.setDataSource(PurchaseRequestDS.getInstance());
		quoteListGrid.setInitialSort(new SortSpecifier[]{  
                new SortSpecifier("status", SortDirection.ASCENDING),  
                new SortSpecifier("created_date", SortDirection.DESCENDING)  
        });
		quoteListGrid.setUseAllDataSourceFields(false);
		quoteListGrid.setGroupByField("status");
		quoteListGrid.setGroupStartOpen(GroupStartOpen.ALL);
		
		ListGridField quote_id = new ListGridField("request_id" , 100);
		ListGridField cus_name = new ListGridField("sup_name", 200);
		ListGridField status = new ListGridField("status");
		ListGridField total_amount = new ListGridField("total_weight", 120);
		total_amount.setCellFormatter(FieldFormatter.getNumberFormat());
		total_amount.setAlign(Alignment.RIGHT);
		ListGridField netInclusive = new ListGridField("netInclusive", 125);
		netInclusive.setCellFormatter(FieldFormatter.getPriceFormat());
		netInclusive.setAlign(Alignment.RIGHT);
		ListGridField created_date = new ListGridField("created_date", 100);
		ListGridField iconField = new ListGridField("viewAndEditField", "เรียกดู/แก้ไข", 100);
		
		quoteListGrid.setFields(status, quote_id, cus_name, total_amount, netInclusive, created_date, iconField);
		
		quoteListGrid.hideField("status");

		searchLayout.addMembers(searchForm, dateForm);
		reviseLayout.addMember(searchLayout);
		
		HLayout buttonLayout = new HLayout();
		buttonLayout.setMargin(10);
		buttonLayout.setMembersMargin(5);
		buttonLayout.setHeight(30);
		IButton searchButton = new IButton("ค้นหาใบเสนอซื้อ");
		searchButton.setIcon("icons/16/icon_view.png");
		searchButton.setWidth(120);
		searchButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	Criterion search = new Criterion();
            	search.addCriteria(searchForm.getValuesAsCriteria());
                AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
          		      new Criterion("status", OperatorId.NOT_EQUAL, "4_canceled"),
          		      new Criterion("created_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate()),
          		      search
          		  });
              quoteListGrid.fetchData(criteria);  
              quoteListGrid.deselectAllRecords();
          }
        });
		
		IButton listAllButton = new IButton("แสดงรายการทั้งหมด");
		listAllButton.setIcon("[SKIN]actions/refresh.png");
		listAllButton.setWidth(150);
		listAllButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
                AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
          		      new Criterion("status", OperatorId.NOT_EQUAL, "4_canceled"),
          		      new Criterion("created_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate())
          		  });
                searchForm.reset();
                quoteListGrid.fetchData(criteria);  
                quoteListGrid.deselectAllRecords();
          }
        });
		
		IButton cancelQuoteButton = new IButton("ยกเลิกใบเสนอซื้อ");
		cancelQuoteButton.setIcon("icons/16/close.png");
		cancelQuoteButton.setWidth(150);
		cancelQuoteButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	ListGridRecord selected = quoteListGrid.getSelectedRecord();
            	if (selected == null) {
            		SC.warn("กรุณาเลือกใบเสนอซื้อที่ต้องการยกเลิก");
            		return;
            	}
            	SC.confirm("ยืนยันการทำรายการ", "ต้องการยกเลิกใบเสนอซื้อ หรือไม่?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
							ListGridRecord selected = quoteListGrid.getSelectedRecord();
			            	if (selected != null) {
			            		//Do something with DB
			            		selected.setAttribute("status", "4_canceled");
			            		quoteListGrid.updateData(selected);
			            		quoteListGrid.removeSelectedData(new DSCallback() {
									@Override
									public void execute(DSResponse dsResponse, Object data,
											DSRequest dsRequest) {
												if (dsResponse.getStatus() != 0) {
													SC.warn("การยกเลิกใบเสนอซื้อ ล้มเหลว");
												} else { 
													SC.say("การยกเลิกใบเสนอซื้อ เสร็จสมบูรณ์");
												}
									}
								}, null);
			            	} else {
			            		SC.warn("กรุณาเลือกรายการที่ต้องการลบ");
			            	}
						}
					}
            	});
          }
        });

		buttonLayout.addMembers(searchButton, listAllButton, cancelQuoteButton);
		reviseLayout.addMember(buttonLayout);
		
		VLayout gridLayout = new VLayout();
		gridLayout.setWidth100();
		gridLayout.setHeight(355);
		
		gridLayout.addMember(quoteListGrid);
		reviseLayout.addMember(gridLayout);
		
		reviseTab.setPane(reviseLayout);
		return reviseTab;
	}
}
