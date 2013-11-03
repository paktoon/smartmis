package com.smart.mis.client.function.sale.quotation;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.smart.mis.client.function.sale.customer.CustomerDS;
import com.smart.mis.client.function.sale.quotation.product.QuoteProductDS;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.ListGridNumberField;
import com.smart.mis.shared.sale.Customer;
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
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.RecordSummaryFunctionType;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.SummaryFunctionType;
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

public class QuoteReviseTab {
	
	Customer client = new Customer();
	SelectProductList addFunc;
	
	public QuoteReviseTab(){
		addFunc = new SelectProductList(this);
	}
	
	public Tab getReviseTab(final User currentUser){
		Tab reviseTab = new Tab("ค้นหาและแก้ไข", "icons/16/comment_edit.png");
		VLayout reviseLayout = new VLayout();
		reviseLayout.setWidth(750);
		reviseLayout.setHeight(400);
		
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
		searchForm.setDataSource(QuotationDS.getInstance());
		searchForm.setUseAllDataSourceFields(false);
		searchForm.setGroupTitle("ค้นหาใบเสนอราคา");
		
		final TextItem quoteText = new TextItem("quote_id", "รหัสใบเสนอราคา");
		quoteText.setWrapTitle(false);
		quoteText.setOperator(OperatorId.REGEXP);
		final SelectItem statusSelected = new SelectItem("status", "สถานะ");
		statusSelected.setWrapTitle(false);
		statusSelected.setValueMap("รอแก้ไข", "รออนุมัติ", "อนุมัติแล้ว");
		statusSelected.setAllowEmptyValue(true);
		statusSelected.setOperator(OperatorId.EQUALS);
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
		dateForm.setGroupTitle("วันที่สร้างใบเสนอราคา");
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

        searchForm.setItems(quoteText,statusSelected, cidText, cnameText);
        dateForm.setItems(from, to);
        
		final ListGrid quoteListGrid = new ListGrid();
		quoteListGrid.setWidth(300);
		quoteListGrid.setHeight(500);
		quoteListGrid.setAlternateRecordStyles(true);  
		quoteListGrid.setShowAllRecords(true);  
		quoteListGrid.setAutoFetchData(true);  
		quoteListGrid.setCanMultiSort(true);
		quoteListGrid.setCriteria(new Criterion("status", OperatorId.NOT_EQUAL, "ยกเลิก"));
		quoteListGrid.setSelectionType(SelectionStyle.SINGLE);
		quoteListGrid.setCanResizeFields(false);
		quoteListGrid.setDataSource(QuotationDS.getInstance());
		quoteListGrid.setInitialSort(new SortSpecifier[]{  
                new SortSpecifier("status", SortDirection.ASCENDING),  
                new SortSpecifier("created_date", SortDirection.DESCENDING)  
        });
		quoteListGrid.setUseAllDataSourceFields(false);
		quoteListGrid.setGroupByField("status");
		quoteListGrid.setGroupStartOpen(GroupStartOpen.ALL);
		
		ListGridField quote_id = new ListGridField("quote_id" , 100);
		ListGridField cus_name = new ListGridField("cus_name", 200);
		ListGridField status = new ListGridField("status");
		
		quoteListGrid.setFields(quote_id, cus_name, status);
		
		final VLayout editLayout = getEditor();
		final IButton printButton = new IButton("พิมพ์ใบเสนอราคา");
		printButton.setIcon("icons/16/print.png");
		printButton.setWidth(120);
		printButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
                SC.warn("click print");
          }
        });
		printButton.disable();
	      
		quoteListGrid.addRecordClickHandler(new RecordClickHandler() {  
			@Override
			public void onRecordClick(RecordClickEvent event) {
				//itemDetailTabPane.updateDetails(); TBD
				ListGridRecord selected = quoteListGrid.getSelectedRecord();
				if (selected != null) {
					if (selected.getAttributeAsString("status").equalsIgnoreCase("อนุมัติแล้ว")) {
						printButton.enable();
						editLayout.setMembers(getViewEditor(selected, false));
					} else {
						printButton.disable();
						editLayout.setMembers(getViewEditor(selected, true));
					}
				}
			}  
        });
		quoteListGrid.hideField("status");

		searchLayout.addMembers(searchForm, dateForm);
		reviseLayout.addMember(searchLayout);
		
		HLayout buttonLayout = new HLayout();
		buttonLayout.setMargin(10);
		buttonLayout.setMembersMargin(5);
		IButton searchButton = new IButton("ค้นหาใบเสนอราคา");
		searchButton.setIcon("icons/16/icon_view.png");
		searchButton.setWidth(120);
		searchButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	Criterion search = new Criterion();
            	search.addCriteria(searchForm.getValuesAsCriteria());
                AdvancedCriteria criteria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
          		      new Criterion("status", OperatorId.NOT_EQUAL, "ยกเลิก"),
          		      new Criterion("created_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate()),
          		      search
          		  });
              printButton.disable();
              editLayout.setMembers(getClearEditor());
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
          		      new Criterion("status", OperatorId.NOT_EQUAL, "ยกเลิก"),
          		      new Criterion("created_date", OperatorId.BETWEEN_INCLUSIVE, from.getValueAsDate(), to.getValueAsDate())
          		  });
                searchForm.reset();
                printButton.disable();
                editLayout.setMembers(getClearEditor());
                quoteListGrid.fetchData(criteria);  
                quoteListGrid.deselectAllRecords();
          }
        });

		buttonLayout.addMembers(searchButton, listAllButton, printButton);
		reviseLayout.addMember(buttonLayout);
		
		Window editWindow = new Window();
		editWindow.setTitle("ข้อมูลใบเสนอราคา");
		editWindow.setWidth(650);  
		editWindow.setHeight(500);

		editWindow.setHeaderControls(HeaderControls.HEADER_LABEL);
		editWindow.addItem(editLayout);
		
		HLayout itemLayout = new HLayout();
		itemLayout.setMembersMargin(5);
		itemLayout.setWidth100();
		itemLayout.setHeight(500);
		VLayout gridLayout = new VLayout();
		gridLayout.setWidth100();
		gridLayout.setHeight(500);
		gridLayout.setShowResizeBar(true);
		gridLayout.addMember(quoteListGrid);
		itemLayout.addMember(gridLayout);
		itemLayout.addMember(editWindow);
        
		reviseLayout.addMember(itemLayout);
		
		reviseTab.setPane(reviseLayout);
		return reviseTab;
	}
	
	private VLayout getEditor() {
		VLayout layout = new VLayout();
		layout.setWidth100();
		layout.setHeight100();
		layout.setMembers(getClearEditor());
		return layout;
	}
	
	private Label getClearEditor() {
		Label test = new Label("ใบเสนอราคา");
		test.setAlign(Alignment.CENTER);
		test.setWrap(false);
		test.setStyleName("initFunctionLabel");
		return test;
	}
	
	private VLayout getViewEditor(ListGridRecord record, boolean edit) {
		VLayout layout = new VLayout();
		
		String cid = record.getAttributeAsString("cid");
		String quote_id = record.getAttributeAsString("quote_id");
		Date from = record.getAttributeAsDate("from");
		Date to = record.getAttributeAsDate("to");
		Date delivery = record.getAttributeAsDate("delivery");
		Double netEx = record.getAttributeAsDouble("netExclusive");
		
		String status = record.getAttributeAsString("status");
		String created_by = record.getAttributeAsString("created_by");
		Date created_date = record.getAttributeAsDate("created_date");
		
		DynamicForm quotationForm = new DynamicForm();
		quotationForm.setWidth100(); 
		quotationForm.setHeight(30);
		quotationForm.setMargin(5);
		quotationForm.setIsGroup(true);
		quotationForm.setNumCols(8);
		quotationForm.setGroupTitle("ข้อมูลใบเสนอราคา");

		StaticTextItem qid = new StaticTextItem("quote_id", "รหัสใบเสนอราคา");
		StaticTextItem sts = new StaticTextItem("status", "สถานะ");
		StaticTextItem cby = new StaticTextItem("created_by", "สร้างโดย");
		StaticTextItem cdate = new StaticTextItem("created_date", "สร้างเมื่อ");
		qid.setValue(quote_id);
		sts.setValue(status);
		cby.setValue(created_by);
		cdate.setValue(DateTimeFormat.getFormat("MM/dd/yyy").format(created_date));
		quotationForm.setFields(qid, sts, cdate ,cby);
		quotationForm.setColWidths(100,70,70,70,70,80,60,100);
		layout.addMember(quotationForm);
		
		DynamicForm customerForm = new DynamicForm();
		customerForm.setWidth(360); 
		customerForm.setHeight(30);
		customerForm.setMargin(5); 
		customerForm.setNumCols(4); 
		customerForm.setCellPadding(2);
		customerForm.setAutoFocus(true);
		customerForm.setSelectOnFocus(true);
		customerForm.setDataSource(CustomerDS.getInstance());
		customerForm.setUseAllDataSourceFields(false);
		customerForm.setIsGroup(true);
		customerForm.setGroupTitle("ข้อมูลลูกค้า");
		if (edit) {
			final StaticTextItem cus_id = new StaticTextItem("cid", "รหัสลูกค้า");
			final SelectItem cus_name = new SelectItem("cus_name", "ชื่อลูกค้า");
			cus_name.setColSpan(3);
			final StaticTextItem cus_type = new StaticTextItem("cus_type", "ประเภทลูกค้า");
			cus_name.setOptionDataSource(CustomerDS.getInstance());
			cus_name.setEmptyDisplayValue("--โปรดเลือกลูกค้า--");
			cus_name.setPickListWidth(350);
			cus_name.setWidth(240);
			cus_name.setRequired(true);
			cus_name.setHint("*");
			ListGridField Field_1 = new ListGridField("cid", 80);  
	        ListGridField Field_2 = new ListGridField("cus_name", 200);  
	        ListGridField Field_3 = new ListGridField("cus_type", 70);
	        cus_name.setPickListFields(Field_1, Field_2, Field_3);
	        
	        cus_name.addChangedHandler(new ChangedHandler() {
				@Override
				public void onChanged(ChangedEvent event) {
					Record selected = cus_name.getSelectedRecord();
					if (selected != null) {
						String customer_id = selected.getAttributeAsString("cid");
						String customer_name = selected.getAttributeAsString("cus_name");
						String customer_type = selected.getAttributeAsString("cus_type");
						//Contact info
						String customer_address = selected.getAttributeAsString("address");
						String customer_phone = selected.getAttributeAsString("cus_phone");
						String contact_name = selected.getAttributeAsString("contact_name");
						String contact_phone = selected.getAttributeAsString("contact_phone");
						String contact_email = selected.getAttributeAsString("contact_email");
						String zone = selected.getAttributeAsString("zone");
						
						client.setAttributes(customer_id, customer_name, customer_phone, contact_name, contact_phone, contact_email, customer_address, customer_type, zone);
						cus_id.setValue(customer_id);
						cus_type.setValue(customer_type);
					}
				}
	        });
	        customerForm.setFields(cus_id, cus_type, cus_name );
		} else {
			final StaticTextItem cus_id = new StaticTextItem("cid", "รหัสลูกค้า");
			final StaticTextItem cus_name = new StaticTextItem("cus_name", "ชื่อลูกค้า");
			cus_name.setColSpan(4);
			final StaticTextItem cus_type = new StaticTextItem("cus_type", "ประเภทลูกค้า");
			customerForm.setFields(cus_id,cus_type, cus_name);
		}
		customerForm.setColWidths(100,80,100,80);
		customerForm.fetchData(new Criterion("cid", OperatorId.EQUALS, cid));
		//customerForm.editRecord(record);
		
		layout.addMember(customerForm);
		
		SectionStack sectionStack = new SectionStack();
    	sectionStack.setWidth100();
    	SectionStackSection section = new SectionStackSection("รายการสินค้า");  
    	section.setCanCollapse(false);  
        section.setExpanded(true);
        
		IButton addButton = new IButton("เพิ่มรายการสินค้า");
		addButton.setIcon("[SKIN]actions/add.png");
		addButton.setWidth(120);
		if (edit) addButton.enable();
		else addButton.disable();
		
		section.setControls(addButton);
		
		final ListGrid quoteListGrid = new ListGrid();
		quoteListGrid.setHeight(220);
		quoteListGrid.setAlternateRecordStyles(true);  
		quoteListGrid.setShowAllRecords(true);  
		quoteListGrid.setAutoFetchData(true);  
		if (edit) quoteListGrid.setSelectionType(SelectionStyle.SINGLE);
		else quoteListGrid.setSelectionType(SelectionStyle.NONE);
		quoteListGrid.setCanResizeFields(false);
		quoteListGrid.setShowGridSummary(true);
		quoteListGrid.setEditEvent(ListGridEditEvent.CLICK);  
		quoteListGrid.setListEndEditAction(RowEndEditAction.NEXT);
		quoteListGrid.setShowRowNumbers(true);
		
		if (edit) {
			quoteListGrid.setCanRemoveRecords(true);
			quoteListGrid.setWarnOnRemoval(true);
			quoteListGrid.setWarnOnRemovalMessage("คุณต้องการลบ รายการสินค้า หรือไม่?");
		}
		
		QuoteProductDS tempView = new QuoteProductDS(quote_id);
		quoteListGrid.setDataSource(tempView);
		quoteListGrid.setUseAllDataSourceFields(false);
		
		ListGridField quoteItemCell_1 = new ListGridField("pid", 60);
		quoteItemCell_1.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });  
        quoteItemCell_1.setShowGridSummary(true);
        ListGridField quoteItemCell_2 = new ListGridField("name"); 
        quoteItemCell_2.setTitle("ชื่อสินค้า");
        ListGridField quoteItemCell_3 = new ListGridField("unit", 40);
        
        ListGridField quoteItemCell_5 = new ListGridField("price", 90);
        quoteItemCell_5.setShowGridSummary(false);
        quoteItemCell_5.setCellFormatter(FieldFormatter.getPriceFormat());
        quoteItemCell_5.setAlign(Alignment.RIGHT);
        
        ListGridNumberField quoteItemCell_6 = new ListGridNumberField("quote_amount", 70);
        
        if (edit) quoteItemCell_6.setCanEdit(true);
        quoteItemCell_6.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_6.setShowGridSummary(true);
        
        ListGridSummaryField quoteItemCell_sum = new ListGridSummaryField("sum_price", 100);

        quoteItemCell_sum.setRecordSummaryFunction(RecordSummaryFunctionType.MULTIPLIER);
        quoteItemCell_sum.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_sum.setShowGridSummary(true);
        quoteItemCell_sum.setCellFormatter(FieldFormatter.getPriceFormat());
        quoteItemCell_sum.setAlign(Alignment.RIGHT);
 
        quoteListGrid.setFields(quoteItemCell_1, quoteItemCell_2, quoteItemCell_6, quoteItemCell_3, quoteItemCell_5 , quoteItemCell_sum);
        section.setItems(quoteListGrid);
        sectionStack.setSections(section);
		layout.addMember(sectionStack);
		
		addButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {
            	//SC.warn("add click " + quoteListGrid.getRecords().length);
            	ArrayList<String> selected = new ArrayList<String>();
            	for (ListGridRecord item : quoteListGrid.getRecords()) {
            		selected.add(item.getAttributeAsString("pid"));
            	}
            	addFunc.show(selected, quoteListGrid.getDataSource());
            }
        });
		
		HLayout footerLayout = new HLayout();
		footerLayout.setHeight(100);
		
		DynamicForm dateForm = new DynamicForm();
		dateForm.setWidth(300);
		dateForm.setNumCols(2);
		dateForm.setMargin(5);
		dateForm.setIsGroup(true);
		dateForm.setGroupTitle("ข้อกำหนดใบเสนอราคา");
		final DateItem fromDate = new DateItem();
		fromDate.setName("fromDate");
		fromDate.setTitle("วันที่เริ่มข้อเสนอ");
		fromDate.setUseTextField(true);
		
		final DateItem toDate = new DateItem();
		toDate.setName("toDate");
		toDate.setTitle("วันที่สิ้นสุดข้อเสนอ");
		toDate.setUseTextField(true);
		
		final DateItem deliveryDate = new DateItem();
		deliveryDate.setName("deliveryDate");
		deliveryDate.setTitle("วันที่กำหนดส่งของ");
		deliveryDate.setUseTextField(true);
		
        fromDate.setDefaultChooserDate(from);
        fromDate.setDefaultValue(from);
        toDate.setDefaultChooserDate(to);
        toDate.setDefaultValue(to);
        deliveryDate.setDefaultChooserDate(delivery);
        deliveryDate.setDefaultValue(delivery);
//        fromDate.setRequired(true);
//        fromDate.setHint("*");
//		toDate.setRequired(true);
//		toDate.setHint("*");
//		deliveryDate.setRequired(true);
//		deliveryDate.setHint("*");
		
		dateForm.setFields(fromDate, toDate, deliveryDate);
		dateForm.setColWidths(130,80);
		//dateForm.editRecord(record);
		footerLayout.addMember(dateForm);
		//******************End
		
		//******************Summary
		final DynamicForm summaryForm = new DynamicForm();
		summaryForm.setWidth(300);
		summaryForm.setNumCols(2);
		summaryForm.setMargin(5);
		summaryForm.setIsGroup(true);
		summaryForm.setGroupTitle("สรุปยอดรวม");
		summaryForm.setColWidths(120, 100);
		NumberFormat nf = NumberFormat.getFormat("#,##0.00");
		StaticTextItem netExclusive = new StaticTextItem("netExclusive");
		netExclusive.setDefaultValue(nf.format(netEx));
		StaticTextItem tax = new StaticTextItem("tax");
		tax.setDefaultValue(nf.format(netEx * 0.07));
		StaticTextItem netInclusive = new StaticTextItem("netInclusive");
		netInclusive.setDefaultValue(nf.format(netEx * 1.07));
		netExclusive.setWidth(100);
		tax.setWidth(100);
		netInclusive.setWidth(100);
		netExclusive.setTitle("ราคารวม");
		tax.setTitle("ภาษีมูลค่าเพิ่ม (7%)");
		netInclusive.setTitle("ราคาสุทธิ");
		netExclusive.setTextAlign(Alignment.RIGHT);
		tax.setTextAlign(Alignment.RIGHT);
		netInclusive.setTextAlign(Alignment.RIGHT);
		netExclusive.setHint("บาท");
		tax.setHint("บาท");
		netInclusive.setHint("บาท");
		summaryForm.setFields(netExclusive, tax, netInclusive);
		//summaryForm.editRecord(record);
		footerLayout.addMember(summaryForm);
		
		layout.addMember(footerLayout);
		
        quoteListGrid.addCellSavedHandler(new CellSavedHandler() {  
			@Override
			public void onCellSaved(CellSavedEvent event) {
				summaryPriceRecalculate(quoteListGrid.getRecords(), summaryForm);
			}  
        });
        
		return layout;
	}
	
	private void summaryPriceRecalculate(ListGridRecord[] all, DynamicForm target){
		Double sum_price = 0.0;
		for (ListGridRecord record : all) {
			sum_price += record.getAttributeAsDouble("sum_price");
		}
		NumberFormat nf = NumberFormat.getFormat("#,##0.00");
		target.getField("netExclusive").setValue(nf.format(sum_price));
		target.getField("tax").setValue(nf.format(sum_price * 0.07));
		target.getField("netInclusive").setValue(nf.format(sum_price * 1.07));
	}
}
