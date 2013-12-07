package com.smart.mis.client.function.sale.quotation;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.smart.mis.client.function.sale.customer.CustomerDS;
import com.smart.mis.client.function.sale.quotation.product.QuoteProductDS;
import com.smart.mis.client.function.sale.quotation.product.QuoteProductData;
import com.smart.mis.client.function.sale.quotation.product.QuoteProductDetails;
import com.smart.mis.shared.EditorWindow;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.FieldVerifier;
import com.smart.mis.shared.ListGridNumberField;
import com.smart.mis.shared.sale.Customer;
import com.smart.mis.shared.sale.QuotationStatus;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.RecordSummaryFunctionType;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SummaryFunctionType;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.ValueCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SelectOtherItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.ListGridSummaryField;
import com.smartgwt.client.widgets.grid.SummaryFunction;
import com.smartgwt.client.widgets.grid.events.CellSavedEvent;
import com.smartgwt.client.widgets.grid.events.CellSavedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

public class PrintQuotation {

	VLayout layout;
//	HLayout header = new HLayout(10);
//	header.setHeight(120);
//	Img logo = new Img("Plain-Silver-icon.png", 100, 100);
//	logo.setWidth(120);
//	header.addMember(logo);
//	
//	VLayout companyDetails = new VLayout(10);
//	companyDetails.setWidth(200);
//	companyDetails.setAlign(Alignment.LEFT);
//	Label companyName = new Label();
//	companyName.setContents("<br>Rich Silver Shop");
//	companyName.setStyleName("printTitle");
//	companyName.setWidth(200);
//	companyDetails.addMember(companyName);
//	
//	Label companyAddress = new Label();
//	companyAddress.setContents("<br> 1211 เจริญกรุง ซอย 47/1 <br> ถนนเจริญกรุง แขวงสี่พระยา <br> เขตบางรัก กรุงเทพมหานคร 10500");
//	companyAddress.setWidth(200);
//	companyAddress.setStyleName("printDetails");
//	companyDetails.addMember(companyAddress);
//	header.addMember(companyDetails);
//	header.addMember(empty);
//	
//	VLayout pageHeader = new VLayout(10);
//	Label page = new Label();
//	page.setWidth("5%");
//	page.setContents("หน้าที่ 1/1");
//	pageHeader.addMember(page);
//	header.addMember(pageHeader);
//	
//	layout.addMember(header);
//	
//	//Detail 1
//	HLayout docDetail = new HLayout(10);
//	Label docName = new Label();
//	docName.setContents("ใบเสนอราคา");
//	docName.setStyleName("printTitle");
//	docName.setWidth(200);
//	docDetail.addMember(empty);
//	docDetail.addMember(docName);
	
	public PrintQuotation(ListGridRecord record) {
		this.layout = new VLayout();
		layout.setWidth(650);
		layout.setHeight(600);
		layout.setMargin(10);
		
		String cid = record.getAttributeAsString("cid");
		String payment_model = record.getAttributeAsString("payment_model");
		Integer credit = record.getAttributeAsInt("credit");
		final String quote_id = record.getAttributeAsString("quote_id");
		String comment = record.getAttributeAsString("comment");
		Date from = record.getAttributeAsDate("from");
		Date to = record.getAttributeAsDate("to");
		Date delivery = record.getAttributeAsDate("delivery");
		Double netEx = record.getAttributeAsDouble("netExclusive");
		
		//System.out.println(payment_model + " " + credit);
		//System.out.println(from + " " + to + " " + delivery + " " + netEx);
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
		sts.setValueMap(QuotationStatus.getValueMap());
		cby.setValue(created_by);
		cdate.setValue(DateTimeFormat.getFormat("MM/dd/yyy").format(created_date));
		quotationForm.setFields(qid, sts, cdate ,cby);
		quotationForm.setColWidths(100,70,70,70,70,80,60,100);
		layout.addMember(quotationForm);
		
		final DynamicForm customerForm = new DynamicForm();
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
		customerForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
		customerForm.setGroupTitle("ข้อมูลลูกค้า");
			final StaticTextItem cus_id = new StaticTextItem("cid", "รหัสลูกค้า");
			final StaticTextItem cus_name = new StaticTextItem("cus_name", "ชื่อลูกค้า");
			cus_name.setColSpan(4);
			final StaticTextItem cus_type = new StaticTextItem("cus_type", "ประเภทลูกค้า");
			final StaticTextItem cus_payment_model = new StaticTextItem("payment_model", "วิธีการชำระเงิน");
			cus_payment_model.setDefaultValue(payment_model);
			final StaticTextItem cus_credit = new StaticTextItem("credit", "เครดิต");
			cus_credit.setDefaultValue(credit);
			cus_credit.setHint("วัน");
			customerForm.setFields(cus_id,cus_type, cus_name, cus_payment_model, cus_credit);
		customerForm.setColWidths(100,100,100,80);
		customerForm.fetchData(new Criterion("cid", OperatorId.EQUALS, cid));
		
		DynamicForm commentForm = new DynamicForm();
		commentForm.setWidth(250); 
		commentForm.setHeight(70);
		commentForm.setMargin(5);
		commentForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
//		commentForm.setIsGroup(true);
//		commentForm.setGroupTitle("ความคิดเห็น");
		
		TextAreaItem comment_area = new TextAreaItem();
		//comment_area.setShowTitle(false);
		comment_area.setTitle("ความคิดเห็น");
		comment_area.setTitleOrientation(TitleOrientation.TOP);
		comment_area.setHeight(60);
		comment_area.setWidth(250);
		comment_area.setValue(comment);
		commentForm.setFields(comment_area);
		
		HLayout headerLayout = new HLayout();
		headerLayout.setWidth100();
		headerLayout.addMembers(customerForm, commentForm);
		//headerLayout.addMembers(customerForm);
		layout.addMember(headerLayout);
		
		SectionStack sectionStack = new SectionStack();
    	sectionStack.setWidth100();
    	sectionStack.setHeight(250);
    	SectionStackSection section = new SectionStackSection("รายการสินค้า");  
    	section.setCanCollapse(false);  
        section.setExpanded(true);
        
//        IButton addButton = new IButton("เพิ่มสินค้า");
//		addButton.setIcon("[SKIN]actions/add.png");
//        IButton delButton = new IButton("ลบสินค้า");
//        delButton.setIcon("icons/16/delete.png");
//        if (edit) {
//			section.setControls(addButton, delButton);
//        }
		
        //HLayout itemLayout = new HLayout();
		final ListGrid quoteListGrid = new ListGrid();
		quoteListGrid.setHeight(230);
		quoteListGrid.setAlternateRecordStyles(true);  
		quoteListGrid.setShowAllRecords(true);  
		quoteListGrid.setAutoFetchData(true);  
//		if (edit) quoteListGrid.setSelectionType(SelectionStyle.SINGLE);
//		else quoteListGrid.setSelectionType(SelectionStyle.NONE);
		quoteListGrid.setCanResizeFields(false);
		quoteListGrid.setShowGridSummary(true);
		quoteListGrid.setEditEvent(ListGridEditEvent.CLICK);  
		quoteListGrid.setListEndEditAction(RowEndEditAction.NEXT);
		quoteListGrid.setShowRowNumbers(true);
        final Criterion ci = new Criterion("status", OperatorId.EQUALS, true);
		quoteListGrid.setCriteria(ci);
//		if (edit) {
//			quoteListGrid.setCanRemoveRecords(true);
//			quoteListGrid.setWarnOnRemoval(true);
//			quoteListGrid.setWarnOnRemovalMessage("คุณต้องการลบ รายการสินค้า หรือไม่?");
//		}
		
		QuoteProductDS tempView = new QuoteProductDS(quote_id);
		Record[] cachedData = QuoteProductDS.getInstance(quote_id).getCacheData();
		if (cachedData.length != 0) {
			tempView.setTestData(cachedData);
		}
		quoteListGrid.setDataSource(tempView);
		quoteListGrid.setUseAllDataSourceFields(false);
		
		ListGridField quoteItemCell_1 = new ListGridField("pid", 60);
        ListGridField quoteItemCell_2 = new ListGridField("name"); 
        quoteItemCell_2.setTitle("ชื่อสินค้า");
        quoteItemCell_2.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });  
        quoteItemCell_2.setShowGridSummary(true);
        ListGridField quoteItemCell_3 = new ListGridField("unit", 40);
        
        ListGridField quoteItemCell_5 = new ListGridField("price", 90);
        quoteItemCell_5.setShowGridSummary(false);
        quoteItemCell_5.setCellFormatter(FieldFormatter.getPriceFormat());
        quoteItemCell_5.setAlign(Alignment.RIGHT);
        
        ListGridNumberField quoteItemCell_6 = new ListGridNumberField("quote_amount", 70);
        
//        if (edit) quoteItemCell_6.setCanEdit(true);
        quoteItemCell_6.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_6.setShowGridSummary(true);
        
        ListGridSummaryField quoteItemCell_sum = new ListGridSummaryField("sum_price", 100);

        quoteItemCell_sum.setRecordSummaryFunction(RecordSummaryFunctionType.MULTIPLIER);
        quoteItemCell_sum.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_sum.setShowGridSummary(true);
        quoteItemCell_sum.setCellFormatter(FieldFormatter.getPriceFormat());
        quoteItemCell_sum.setAlign(Alignment.RIGHT);
 
        quoteListGrid.setFields(quoteItemCell_1, quoteItemCell_2, quoteItemCell_6, quoteItemCell_3, quoteItemCell_5 , quoteItemCell_sum);
        //itemLayout.addMember(quoteListGrid);
        section.setItems(quoteListGrid);
        sectionStack.setSections(section);
		layout.addMember(sectionStack);
		
		HLayout footerLayout = new HLayout();
		footerLayout.setHeight(100);
		
		final DynamicForm dateForm = new DynamicForm();
		dateForm.setWidth(300);
		dateForm.setNumCols(2);
		dateForm.setMargin(5);
		dateForm.setIsGroup(true);
		dateForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
		dateForm.setGroupTitle("ข้อกำหนดใบเสนอราคา");
//		if (!edit) dateForm.setCanEdit(false);
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
        fromDate.setValue(from);
        toDate.setDefaultChooserDate(to);
        toDate.setValue(to);
        deliveryDate.setDefaultChooserDate(delivery);
        deliveryDate.setValue(delivery);
		
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
		netExclusive.setValue(nf.format(netEx));
		StaticTextItem tax = new StaticTextItem("tax");
		tax.setValue(nf.format(netEx * 0.07));
		StaticTextItem netInclusive = new StaticTextItem("netInclusive");
		netInclusive.setValue(nf.format(netEx * 1.07));
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
	}
	
	public void print(){
		//this.layout.draw();
		Canvas.showPrintPreview(this.layout);
	}
}
