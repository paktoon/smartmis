package com.smart.mis.client.function.sale.quotation;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.smart.mis.client.function.production.plan.PlanDS;
import com.smart.mis.client.function.production.plan.PlanData;
import com.smart.mis.client.function.production.plan.product.PlanProductDS;
import com.smart.mis.client.function.production.plan.product.PlanProductData;
import com.smart.mis.client.function.production.plan.product.PlanProductDetails;
import com.smart.mis.client.function.production.product.ProductDS;
import com.smart.mis.client.function.production.product.ProductData;
import com.smart.mis.client.function.sale.customer.CustomerDS;
import com.smart.mis.client.function.sale.invoice.InvoiceDS;
import com.smart.mis.client.function.sale.invoice.InvoiceData;
import com.smart.mis.client.function.sale.order.SaleOrderDS;
import com.smart.mis.client.function.sale.order.SaleOrderData;
import com.smart.mis.client.function.sale.order.product.SaleProductDS;
import com.smart.mis.client.function.sale.order.product.SaleProductData;
import com.smart.mis.client.function.sale.order.product.SaleProductDetails;
import com.smart.mis.client.function.sale.quotation.product.QuoteProductDS;
import com.smart.mis.client.function.sale.quotation.product.QuoteProductData;
import com.smart.mis.client.function.sale.quotation.product.QuoteProductDetails;
import com.smart.mis.shared.EditorWindow;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.FieldVerifier;
import com.smart.mis.shared.ListGridNumberField;
import com.smart.mis.shared.PrintHeader;
import com.smart.mis.shared.ValidatorFactory;
import com.smart.mis.shared.sale.Customer;
import com.smart.mis.shared.sale.QuotationStatus;
import com.smart.mis.shared.sale.SaleOrderStatus;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DateRange;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RelativeDate;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.RecordSummaryFunctionType;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SummaryFunctionType;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.ValueCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
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
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
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

public class QuoteViewWindow extends EditorWindow{

	SelectProductList addFunc;
	Customer client;
	
	public QuoteViewWindow(){
		addFunc = new SelectProductList();
	}
	
	public void show(ListGridRecord record, boolean edit, User currentUser, int page){
		client = new Customer();
		Window editWindow = new Window();
		editWindow.setTitle("ข้อมูลใบเสนอราคา");
		editWindow.setWidth(670);  
		editWindow.setHeight(620);
		editWindow.setShowMinimizeButton(false);
		editWindow.setIsModal(true);
		editWindow.setShowModalMask(true);
		editWindow.setCanDragResize(false);
		editWindow.setCanDragReposition(false);
		editWindow.centerInPage();
		
		editWindow.addItem(getViewEditor(record, edit, editWindow, currentUser, page));
		editWindow.show();
	}
	
	private VLayout getViewEditor(final ListGridRecord record, boolean edit, final Window main, final User currentUser, int page) {
		final VLayout layout = new VLayout();
		layout.setWidth(650);
		layout.setHeight(600);
		layout.setMargin(10);
		
		String cid = record.getAttributeAsString("cid");
		String payment_model = record.getAttributeAsString("payment_model");
		Integer credit = record.getAttributeAsInt("credit");
		final String quote_id = record.getAttributeAsString("quote_id");
		String comment = record.getAttributeAsString("comment");
		if (comment == null || comment.isEmpty()) {
			for (int i = 1 ; i <=256; i++) comment += " ";
		}
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
	        
	        final SelectItem cus_payment_model = new SelectItem("payment_model", "วิธีการชำระเงิน");
	        cus_payment_model.setValueMap("เงินสด", "แคชเชียร์เช็ค");
			//paymentModel.setEmptyDisplayValue("--โปรดเลือกวิธีชำระเงิน--");
	        cus_payment_model.setDefaultValue(payment_model);
	        cus_payment_model.setWidth(100);
			final IntegerItem cus_credit = new IntegerItem("credit","เครดิต");
			cus_credit.setRequired(true);
			cus_credit.setHint("วัน*");
			cus_credit.setWidth(50);
			cus_credit.setTextAlign(Alignment.LEFT);
			cus_credit.setDefaultValue(credit);
			
	        cus_name.addChangedHandler(new ChangedHandler() {
				@Override
				public void onChanged(ChangedEvent event) {
					Record selected = cus_name.getSelectedRecord();
					if (selected != null) {
						String customer_id = selected.getAttributeAsString("cid");
						String customer_name = selected.getAttributeAsString("cus_name");
						String customer_type = selected.getAttributeAsString("cus_type");
						//Contact info
						//String customer_address = selected.getAttributeAsString("address");
						String customer_phone = selected.getAttributeAsString("cus_phone");
						String contact_name = selected.getAttributeAsString("contact_name");
						String contact_phone = selected.getAttributeAsString("contact_phone");
						String contact_email = selected.getAttributeAsString("contact_email");
						
						String bus_type = selected.getAttributeAsString("bus_type");
						String cus_group = selected.getAttributeAsString("cus_group");
						String zone = selected.getAttributeAsString("zone");
						
						client.setAttributes(customer_id, customer_name, customer_phone, contact_name, contact_phone, contact_email, customer_type, bus_type, cus_group, zone);
						
						if(customer_type.equalsIgnoreCase("ลูกค้าประจำ")) {
							cus_credit.enable();
						} else {
							cus_credit.setValue(0);
							cus_credit.disable();
						}
						
						cus_id.setValue(customer_id);
						cus_type.setValue(customer_type);
					}
				}
	        });
	        
	        cus_payment_model.addChangedHandler(new ChangedHandler() {
				@Override
				public void onChanged(ChangedEvent event) {
					if (cus_payment_model.validate()) {
						client.setPaymentModel(cus_payment_model.getValueAsString());
					}
				}
	        });
			
	        cus_credit.addChangedHandler(new ChangedHandler() {
				@Override
				public void onChanged(ChangedEvent event) {
					if (cus_credit.validate()) {
						client.setCredit(cus_credit.getValueAsInteger());
					}
				}
	        });
			
	        customerForm.setFields(cus_id, cus_type, cus_name, cus_payment_model, cus_credit );
//	        customerForm.setFields(cus_id, cus_type, cus_name );
		} else {
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
			//customerForm.setFields(cus_id,cus_type, cus_name);
		}
		customerForm.setColWidths(100,100,100,80);
		customerForm.fetchData(new Criterion("cid", OperatorId.EQUALS, cid));
		//customerForm.editRecord(record);
		
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
		if (edit) comment_area.setCanEdit(true);
		else comment_area.setCanEdit(false);
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
        
        IButton addButton = new IButton("เพิ่มสินค้า");
		addButton.setIcon("[SKIN]actions/add.png");
        IButton delButton = new IButton("ลบสินค้า");
        delButton.setIcon("icons/16/delete.png");
        if (edit) {
			section.setControls(addButton, delButton);
        }
		
        //HLayout itemLayout = new HLayout();
		final ListGrid quoteListGrid = new ListGrid();
		quoteListGrid.setHeight(230);
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
        quoteItemCell_6.setValidators(ValidatorFactory.integerRange(50, 5000));
        
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
		if (!edit) dateForm.setCanEdit(false);
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
//        fromDate.setRequired(true);
//        fromDate.setHint("*");
//		toDate.setRequired(true);
//		toDate.setHint("*");
//		deliveryDate.setRequired(true);
//		deliveryDate.setHint("*");
        fromDate.addChangeHandler( new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				// TODO Auto-generated method stub
					try {
						Date from = (Date) event.getValue();
						
						//if (!from.before(toDate.getValueAsDate()) || !from.before(deliveryDate.getValueAsDate())) {
						if (!from.before(toDate.getValueAsDate()) || from.after(deliveryDate.getValueAsDate())) {
								//SC.warn("วันที่เลือกไม่ถูกต้อง กรุณาเลือกใหม่อีกครั้ง");
								SC.warn("วันที่เลือกไม่ถูกต้อง กรุณาเลือกใหม่อีกครั้ง <br> " + fromDate.getTitle() + " ต้องก่อนหน้า " + toDate.getTitle() + " และ " + deliveryDate.getTitle());
								fromDate.setValue(fromDate.getValueAsDate());
							}
					} catch (Exception e) {
						fromDate.setValue(fromDate.getValueAsDate());
						SC.warn("รูปแบบวันที่ไม่ถูกต้อง กรุณากรอกด้วยรูปแบบ เดือน/วันที่/ปี เช่น 01/01/2014");
					}
				}
			});
        
		toDate.addChangeHandler( new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				// TODO Auto-generated method stub
					try {
						Date to = (Date) event.getValue();
						
						//if (!to.after(fromDate.getValueAsDate()) || !to.after(new Date())) {
						if (!to.after(fromDate.getValueAsDate()) || to.before(new Date())) {
								SC.warn("วันที่เลือกไม่ถูกต้อง กรุณาเลือกใหม่อีกครั้ง <br> " + toDate.getTitle() + " ต้องภายหลังจาก " + fromDate.getTitle() + " และวันนี้");
								toDate.setValue(toDate.getValueAsDate());
							}
					} catch (Exception e) {
						toDate.setValue(toDate.getValueAsDate());
						SC.warn("รูปแบบวันที่ไม่ถูกต้อง กรุณากรอกด้วยรูปแบบ เดือน/วันที่/ปี เช่น 01/01/2014");
					}
				} 
		});
		
		deliveryDate.addChangeHandler( new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				// TODO Auto-generated method stub
					try {
						Date delivery = (Date) event.getValue();
						//if (!delivery.after(fromDate.getValueAsDate())  || !delivery.after(new Date())) {
						if (!delivery.after(fromDate.getValueAsDate())  || delivery.before(new Date())) {
								//SC.warn("วันที่เลือกไม่ถูกต้อง กรุณาเลือกใหม่อีกครั้ง");
								SC.warn("วันที่เลือกไม่ถูกต้อง กรุณาเลือกใหม่อีกครั้ง <br> " + deliveryDate.getTitle() + " ต้องภายหลังจาก " + fromDate.getTitle() + " และวันนี้");
								deliveryDate.setValue(deliveryDate.getValueAsDate());
							}
					} catch (Exception e) {
						deliveryDate.setValue(deliveryDate.getValueAsDate());
						SC.warn("รูปแบบวันที่ไม่ถูกต้อง กรุณากรอกด้วยรูปแบบ เดือน/วันที่/ปี เช่น 01/01/2014");
					}
				}
			});
		
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
		
		//Control
		HLayout controls = new HLayout();
		controls.setAlign(Alignment.CENTER);
		controls.setMargin(5);
		controls.setMembersMargin(5);
		final IButton printButton = new IButton("พิมพ์ใบเสนอราคา");
		printButton.setIcon("icons/16/print.png");
		printButton.setWidth(120);
		printButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
                //SC.warn("click print");
            	//Canvas.showPrintPreview(PrintQuotation.getPrintContainer(record));
            	
//            	PrintQuotation item = new PrintQuotation(record);
//            	item.print();
            	
            	VLayout printLayout = new VLayout(10);
            	printLayout.addMember(new PrintHeader("ใบเสนอราคา"));
            	printLayout.addMember(layout);
            	Canvas.showPrintPreview(printLayout);
            	main.destroy();
          }
        });
		if (edit || !status.equals("3_approved")) printButton.disable();
		
		final IButton saveButton = new IButton("บันทึกการแก้ไข");
		saveButton.setIcon("icons/16/save.png");
		saveButton.setWidth(120);
		saveButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	if (quoteListGrid.hasErrors()) {
            		SC.warn("ข้อมูลจำนวนสินค้าไม่ถูกต้อง");
            		return;
            	}
            		
            	SC.confirm("ยืนยันการทำรายการ", "ต้องการบันทึกการแก้ไขใบเสนอราคา หรือไม่?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
							if (customerForm.validate()) saveQuotation(main, quote_id, customerForm, quoteListGrid, fromDate.getValueAsDate(), toDate.getValueAsDate(), deliveryDate.getValueAsDate(), currentUser);
						}
					}
            	});
          }
        });
		if (!edit) saveButton.disable();
		
		final IButton closeButton = new IButton("ปิด");
		closeButton.setIcon("icons/16/close.png");
		closeButton.setWidth(120);
		closeButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	main.destroy();
          }
        });
		
		final IButton approveButton = new IButton("อนุมัติ");
		//approveButton.setIcon("icons/16/approved.png");
		approveButton.setWidth(120);
		approveButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	SC.confirm("ยืนยันการทำรายการ", "ต้องการอนุมัติใบเสนอราคา หรือไม่?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
							//saveQuotation(main, quote_id, customerForm, quoteListGrid, fromDate.getValueAsDate(), toDate.getValueAsDate(), deliveryDate.getValueAsDate(), currentUser);
							updateQuoteStatus(quote_id, "3_approved", "");
							main.destroy();
						}
					}
            	});
          }
        });
		
		final IButton disapproveButton = new IButton("ไม่อนุมัติ");
		//disapproveButton.setIcon("icons/16/delete.png");
		disapproveButton.setWidth(120);
		disapproveButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	SC.confirm("ยืนยันการทำรายการ", "ต้องการส่งใบเสนอราคากลับไปแก้ไข หรือไม่?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
//							SC.askforValue("กรุณาใส่ความคิดเห็น", new ValueCallback() {
//								@Override
//								public void execute(String value) {
//									if (value == null || value.equals("")){
//										SC.warn("กรุณาใส่ความคิดเห็นในกล่องข้อความ");
//									} else {
//										updateQuoteStatus(quote_id, "1_waiting_for_revised", value);
//										main.destroy();
//									}
//								}});
							final Window confirm = new Window();
							confirm.setTitle("ความคิดเห็น");
							confirm.setWidth(350);
							confirm.setHeight(150);
							confirm.setShowMinimizeButton(false);
							confirm.setIsModal(true);
							confirm.setShowModalMask(true);
							confirm.setCanDragResize(false);
							confirm.setCanDragReposition(false);
							confirm.centerInPage();
							VLayout commentLayout = new VLayout();
							commentLayout.setMargin(10);
							
							DynamicForm commentForm = new DynamicForm();
							final SelectOtherItem selectOtherItem = new SelectOtherItem();  
					        selectOtherItem.setOtherTitle("อื่นๆ..");  
					        selectOtherItem.setOtherValue("OtherVal");
					        selectOtherItem.setEmptyDisplayValue("---โปรดระบุความคิดเห็น---");
					        selectOtherItem.setTitle("ความคิดเห็น");  
					        selectOtherItem.setValueMap("ข้อมูลลูกค้าไม่ถูกต้อง", "รายการสินค้าไม่ถูกต้อง", "เงื่อนไขในใบเสนอราคาไม่ถูกต้อง", "วันที่กำหนดส่งสินค้าไม่ถูกต้อง");  
					        selectOtherItem.setWidth(250);
					        commentForm.setFields(selectOtherItem);  
					        	
					        commentLayout.addMember(commentForm);
					        
					        HLayout controlLayout = new HLayout();
					        controlLayout.setMargin(10);
					        controlLayout.setMembersMargin(10);
					        controlLayout.setAlign(Alignment.CENTER);
					        IButton confirmButton = new IButton("ยืนยัน");
					        confirmButton.setIcon("icons/16/approved.png");
					        IButton cancelButton = new IButton("ยกเลิก");
					        cancelButton.setIcon("icons/16/delete.png");
					        controlLayout.addMember(confirmButton);
					        controlLayout.addMember(cancelButton);
					        confirmButton.addClickHandler(new ClickHandler() {  
					            public void onClick(ClickEvent event) { 
					            	String value = selectOtherItem.getValueAsString();
					            	if (value != null && !value.equalsIgnoreCase("")) {
						            	updateQuoteStatus(quote_id, "1_waiting_for_revised", selectOtherItem.getValueAsString());
						            	confirm.destroy();
						            	main.destroy();
					            	} else {
					            		SC.warn("กรูณาใส่ความคิดเห็น");
					            	}
					          }
					        });
					        
					        cancelButton.addClickHandler(new ClickHandler() {  
					            public void onClick(ClickEvent event) { 
					            	confirm.destroy();
					            	//main.destroy();
					          }
					        });
					        
					        commentLayout.addMember(controlLayout);
					        
					        confirm.addItem(commentLayout);
					        
					        confirm.show();
						}
					}
            	});
          }
        });
		
		final IButton createSaleOrderButton = new IButton("สร้างรายการขาย");
		createSaleOrderButton.setIcon("icons/16/coins.png");
		createSaleOrderButton.setWidth(120);
		createSaleOrderButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	SC.confirm("ยืนยันการทำรายการ", "ต้องการสร้างรายการขาย หรือไม่?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
							SC.askforValue("กรุณาระบุเลขที่คำสั่งซื้อ", new ValueCallback() {
								@Override
								public void execute(String value) {
									if (value == null || value.equals("")){
										SC.warn("กรุณาระบุเลขที่คำสั่งซื้อในกล่องข้อความ");
									} else {
										//if (customerForm.validate()) createSaleOrder(main, quote_id, customerForm, quoteListGrid, deliveryDate.getValueAsDate(), currentUser, value);
										if (customerForm.validate()) createSaleOrder(main, quote_id, record, quoteListGrid, deliveryDate.getValueAsDate(), currentUser, value);
									}
								}});
						}
					}
            	});
          }
        });
		
		if (page == 3) {
			controls.addMember(createSaleOrderButton);
		} else if (page == 2) {
			if (!record.getAttributeAsString("status").equalsIgnoreCase("2_waiting_for_approved")){
				approveButton.disable();
				disapproveButton.disable();
			} else {
				approveButton.setIcon("icons/16/approved.png");
				disapproveButton.setIcon("icons/16/delete.png");
				controls.addMember(approveButton);
				controls.addMember(disapproveButton);
			}
		} else {
			controls.addMember(printButton);
			controls.addMember(saveButton);
		}
		controls.addMember(closeButton);
		layout.addMember(controls);
		
		addButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {
            	//SC.warn("add click " + quoteListGrid.getRecords().length);
            	ArrayList<String> selected = new ArrayList<String>();
            	for (ListGridRecord item : quoteListGrid.getRecords()) {
            		selected.add(item.getAttributeAsString("pid"));
            	}
            	addFunc.show(selected, quoteListGrid, summaryForm);
            }
        });
		
		delButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {
            	ListGridRecord selected = quoteListGrid.getSelectedRecord();
            	if (selected != null) {
            		//quoteListGrid.removeSelectedData();
            		selected.setAttribute("status", false);
            		quoteListGrid.updateData(selected);
            		quoteListGrid.removeSelectedData(new DSCallback() {
						@Override
						public void execute(DSResponse dsResponse, Object data,
								DSRequest dsRequest) {
								if (dsResponse.getStatus() != 0) {
									SC.warn("การลบสินค้าล้มเหลว");
								} else { 
									summaryPriceRecalculate(quoteListGrid.getRecords(), summaryForm);
								}
						}
					}, null);
            	} else {
            		SC.warn("กรุณาเลือกรายการที่ต้องการลบ");
            	}
            }
        });
		
        quoteListGrid.addCellSavedHandler(new CellSavedHandler() {  
			@Override
			public void onCellSaved(CellSavedEvent event) {
				summaryPriceRecalculate(quoteListGrid.getRecords(), summaryForm);
			}  
        });
        
//        quoteListGrid.addRemoveRecordClickHandler(new RemoveRecordClickHandler() {
//
//			@Override
//			public void onRemoveRecordClick(RemoveRecordClickEvent event) {
//				//System.out.println("onRemoveRecordClick getResultSize " +quoteListGrid.getResultSet().getResultSize());
//				System.out.println("onRemoveRecordClick getRecords " +quoteListGrid.getRecords().length);
//				summaryPriceRecalculate(quoteListGrid.getRecords(), summaryForm);
//			}
//        });
//        
//        quoteListGrid.addFetchDataHandler(new FetchDataHandler() {
//
//			@Override
//			public void onFilterData(FetchDataEvent event) {
//				//System.out.println("onFilterData getResultSize " +quoteListGrid.getResultSet().getResultSize());
//				System.out.println("onFilterData getRecords " +quoteListGrid.getRecords().length);
//				if (quoteListGrid.getRecords().length != 0) {
//					summaryPriceRecalculate(quoteListGrid.getRecords(), summaryForm);
//				}
//			}
//        	
//        });
        
		return layout;
	}
	
	public void summaryPriceRecalculate(ListGridRecord[] all, DynamicForm target){
		Double sum_price = 0.0;
		for (ListGridRecord record : all) {
			sum_price += record.getAttributeAsDouble("sum_price");
		}
		NumberFormat nf = NumberFormat.getFormat("#,##0.00");
		target.getField("netExclusive").setValue(nf.format(sum_price));
		target.getField("tax").setValue(nf.format(sum_price * 0.07));
		target.getField("netInclusive").setValue(nf.format(sum_price * 1.07));
	}
	
	public void saveQuotation(final Window main, final String quote_id, DynamicForm customer, ListGrid quoteListGrid, Date from, Date to, Date delivery, User currentUser){
		ListGridRecord[] all = quoteListGrid.getRecords();
		
		if (all.length == 0) {
			SC.warn("กรูณาเลือกรายการสินค้าอย่างน้อย 1 รายการ");
			return;
		}
		
		Double total_weight = 0.0;
		Double total_netExclusive = 0.0;
		Integer total_amount = 0;
		//final String quote_id = "QA70" + Math.round((Math.random() * 100)) + Math.round((Math.random() * 100));
		final ArrayList<QuoteProductDetails> productList = new ArrayList<QuoteProductDetails>();
		
		for (ListGridRecord item : all){
			total_weight += item.getAttributeAsDouble("weight");
			total_amount += item.getAttributeAsInt("quote_amount");
			total_netExclusive += item.getAttributeAsDouble("sum_price");
			
			String sub_quote_id = item.getAttributeAsString("sub_quote_id");
			String pid = item.getAttributeAsString("pid");
			String pname = item.getAttributeAsString("name");
			String ptype = item.getAttributeAsString("type");
			Double pweight = item.getAttributeAsDouble("weight");
			Integer pquote_amount = item.getAttributeAsInt("quote_amount");
			String punit = item.getAttributeAsString("unit");
			Double pprice = item.getAttributeAsDouble("price");
			QuoteProductDetails temp = new QuoteProductDetails();
			temp.save(pid, pname, pweight, pprice, ptype, punit);
			temp.setID(sub_quote_id, quote_id);
			temp.setQuantity(pquote_amount);
			productList.add(temp);
		}
		//System.out.println(total_weight + " " + total_amount + " " + total_netExclusive);
			//status
			final String quote_status = "2_waiting_for_approved";
			
			if (customer.getField("cid").getValue() == null || customer.getField("cus_name").getValue() == null) {
				SC.warn("ชื่อและรหัสลูกค้าไม่ถุกต้อง");
				return;
			}
			
			String cid = (String) customer.getField("cid").getValue();
			String cus_name = (String) customer.getField("cus_name").getValue();
			String payment_model = (String) customer.getField("payment_model").getValue();
			Integer credit = (Integer) customer.getField("credit").getValue();
			//System.out.println(cid + " " + cus_name + " " + payment_model + " " + credit);
			
			ListGridRecord updateRecord = QuotationData.createUpdateRecord(quote_id, cid, cus_name, payment_model, credit, client.cus_type, client.bus_type, client.cus_group, client.zone, from, to, delivery, total_weight, total_amount, total_netExclusive, new Date(), currentUser.getFirstName() + " " + currentUser.getLastName(), "", quote_status);
			
			QuotationDS.getInstance().updateData(updateRecord, new DSCallback() {
				@Override
				public void execute(DSResponse dsResponse, Object data,
						DSRequest dsRequest) {
						//System.out.println("Test " + dsResponse.getStatus());
						if (dsResponse.getStatus() != 0) {
							SC.warn("การบันทึกใบเสนอราคาล้มเหลว กรุณาทำรายการใหม่อีกครั้ง");
							main.destroy();
						} else { 
							for (QuoteProductDetails item : productList) {
								if (item.sub_quote_id == null) {
									item.sub_quote_id = "QS80" + Math.round((Math.random() * 100)) + Math.round((Math.random() * 100));
									ListGridRecord subUpdateRecord = QuoteProductData.createRecord(item);
									QuoteProductDS.getInstance(quote_id).addData(subUpdateRecord);
								} else  {
									ListGridRecord subUpdateRecord = QuoteProductData.createRecord(item);
									QuoteProductDS.getInstance(quote_id).updateData(subUpdateRecord);
								}
							}
							SC.say("แก้ไขใบเสนอราคาเสร็จสิ้น <br> " + "รหัสใบเสนอราคา " + quote_id + "<br> สถานะของใบเสนอราคา " + QuotationStatus.getDisplay(quote_status));
							main.destroy();
						}
				}
			});
	}
	
	void updateQuoteStatus(String quote_id, final String status, String comment) {
		Record updated = QuotationData.createStatusRecord(quote_id,status,comment);
		QuotationDS.getInstance().updateData(updated, new DSCallback() {
			@Override
			public void execute(DSResponse dsResponse, Object data,
					DSRequest dsRequest) {
					if (dsResponse.getStatus() != 0) {
						SC.warn("การอนุมัติใบเสนอราคาล้มเหลว");
					} else { 
						SC.say("แก้ไขสถานะใบเสนอราคา \"" + QuotationStatus.getDisplay(status) + "\" เสร็จสิ้น");
					}
			}
		});
	}
	
	//public void createSaleOrder(final Window main, final String quote_id, DynamicForm customer, ListGrid quoteListGrid, final Date delivery, final User currentUser, String purchase_id){
	public void createSaleOrder(final Window main, final String quote_id, ListGridRecord quoteRecord, ListGrid quoteListGrid, final Date delivery, final User currentUser, String purchase_id){
		
		ListGridRecord[] all = quoteListGrid.getRecords();
		
		Double total_weight = 0.0;
		Double total_netExclusive = 0.0;
		Double total_produce_weight = 0.0;
		Integer total_amount = 0;
		Integer total_produce_amount = 0;
		
		final ArrayList<SaleProductDetails> saleProductList = new ArrayList<SaleProductDetails>();
		final ArrayList<SaleProductDetails> invoiceProductList = new ArrayList<SaleProductDetails>();
		final ArrayList<PlanProductDetails> planProductList = new ArrayList<PlanProductDetails>();
		final ArrayList<Record> productUpdateList = new ArrayList<Record>();
		
		for (ListGridRecord item : all){
			total_weight += item.getAttributeAsDouble("weight");
			total_amount += item.getAttributeAsInt("quote_amount");
			total_netExclusive += item.getAttributeAsDouble("sum_price");
			
			String pid = item.getAttributeAsString("pid");
			String pname = item.getAttributeAsString("name");
			String ptype = item.getAttributeAsString("type");
			//String psize = item.getAttributeAsString("size");
			Double pweight = item.getAttributeAsDouble("weight");
			Integer psale_amount = item.getAttributeAsInt("quote_amount");
			String punit = item.getAttributeAsString("unit");
			Double pprice = item.getAttributeAsDouble("price");
			
			//Sale order
			String sub_sale_id = "SS80" + Math.round((Math.random() * 1000));
			SaleProductDetails temp1 = new SaleProductDetails();
			temp1.save(pid, pname, pweight, pprice, ptype, punit);
			//temp1.setID(sub_sale_id, sale_id);
			temp1.setID(sub_sale_id);
			temp1.setQuantity(psale_amount);
			saleProductList.add(temp1);
			
			//Invoice
			String sub_invoice_id = "SI80" + Math.round((Math.random() * 1000));
			SaleProductDetails temp2 = new SaleProductDetails();
			temp2.save(pid, pname, pweight, pprice, ptype, punit);
			//temp2.setID(sub_invoice_id, invoice_id);
			temp2.setID(sub_invoice_id);
			temp2.setQuantity(psale_amount);
			invoiceProductList.add(temp2);

			//Production Plan
			Record[] selectedProduct = ProductDS.getInstance().applyFilter(ProductDS.getInstance().getCacheData(), new Criterion("pid", OperatorId.EQUALS, pid));
			if (selectedProduct.length == 0) {
				SC.warn("ไม่พบรายการสินค้า รหัส " + pid + " <br> โปรดตรวจสอบ ข้อมูลสินค้าอีกครั้ง" );
				return;
			} else {
				Record product = selectedProduct[0];
				Integer remain = product.getAttributeAsInt("remain");
				Integer reserved = product.getAttributeAsInt("reserved");
				Integer produce = psale_amount - remain;
				
				System.out.println("Produce " + pid + " - reserved = " + reserved + " remain = " + remain + ", need for sale = " + psale_amount);
				if (produce > 0) {
					//Create sub production order in planProductList
					total_produce_weight += product.getAttributeAsDouble("weight") * produce;
					total_produce_amount += produce;
					//reserved = remain
					Record product_update = ProductData.createReservedRecord(pid, reserved + remain, 0, product);
					System.out.println("Create product_update - reserved = " + (reserved + remain) + " remain = 0");
					//Update reserved
					productUpdateList.add(product_update);
					planProductList.add(CreatePlanProductDetails(product, produce));
					System.out.println("CreatePlanProductDetails - produce = " + produce);
				} else {
					//Nothing reserved = psale_amount
					Record product_update = ProductData.createReservedRecord(pid, reserved + psale_amount, remain - psale_amount, product);
					System.out.println("Create product_update - reserved = " + (reserved + psale_amount) + " remain = " + (remain - psale_amount));
					//Update reserved
					productUpdateList.add(product_update);
				}
				
			}
		}	
			//For sale and invoice
			String sale_status = "3_production_completed";
			if (planProductList.size() != 0) {
				sale_status = "1_waiting_for_production";
			}
			final String invoice_status = "1_waiting_for_payment";
//			String cid = (String) customer.getField("cid").getValue();
//			String cus_name = (String) customer.getField("cus_name").getValue();
//			String payment_model = (String) customer.getField("payment_model").getValue();
//			Integer credit = (Integer) customer.getField("credit").getValue();
			String cid = quoteRecord.getAttributeAsString("cid");
			String cus_name = quoteRecord.getAttributeAsString("cus_name");
			String payment_model = quoteRecord.getAttributeAsString("payment_model");
			Integer credit = quoteRecord.getAttributeAsInt("credit");
			
			String cus_type = quoteRecord.getAttributeAsString("cus_type");
			String bus_type = quoteRecord.getAttributeAsString("bus_type");
			String cus_group = quoteRecord.getAttributeAsString("cus_group");
			String zone = quoteRecord.getAttributeAsString("zone");
			
			System.out.println("QuoteViewWindow create saleorder for " + cid + " " + cus_name + " " + payment_model + " " + credit + " " + cus_type + " " + bus_type + " " + cus_group + " " + zone);
			
			DateRange dateRange = new DateRange();  
	        dateRange.setRelativeStartDate(RelativeDate.TODAY);
	        dateRange.setRelativeEndDate(new RelativeDate("+"+credit+"d"));
	        final Date due_date = dateRange.getEndDate();
	        
			final String sale_id = "SO70" + Math.round((Math.random() * 100)) + Math.round((Math.random() * 100));
			final String invoice_id = "IN70" + Math.round((Math.random() * 100)) + Math.round((Math.random() * 100));
			final String plan_id = "PL70" + Math.round((Math.random() * 100)) + Math.round((Math.random() * 100));
			
			final ListGridRecord saleRecord = SaleOrderData.createRecord(sale_id, quote_id, invoice_id, cid, cus_name, payment_model, credit, cus_type ,bus_type, cus_group, zone, delivery, total_weight, total_amount, total_netExclusive, new Date(), null, currentUser.getFirstName() + " " + currentUser.getLastName(), null, sale_status, purchase_id, due_date);
			final ListGridRecord invoiceRecord = InvoiceData.createRecord(invoice_id, sale_id, cid, cus_name, payment_model, credit, cus_type ,bus_type, cus_group, zone, delivery, total_weight, total_amount, total_netExclusive, new Date(), null, currentUser.getFirstName() + " " + currentUser.getLastName(), null, invoice_status, purchase_id, due_date, null);
			
			//For production
			for (Record updateProduct : productUpdateList) {
				ProductDS.getInstance().updateData(updateProduct);
			}
			
			final Double produce_weight = total_produce_weight;
			final Integer produce_amount = total_produce_amount;
			if (planProductList.size() != 0) {
				SC.confirm("สร้างแผนการผลิตโดยอัตโนมัติ", "สินค้าในรายการขายไม่เพียงพอ <br> ต้องการสร้างแผนการผลิต หรือไม่?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
							//Date delivery = null;
							String plan_status = "3_approved";
							//xxxService.xxx(Callback quoteId);
							ListGridRecord newRecord = PlanData.createRecord(plan_id, sale_id, delivery, produce_weight, produce_amount, new Date(), null,  currentUser.getFirstName() + " " + currentUser.getLastName(), null, "", plan_status, "สร้างจากรายการขายโดยอัตโนมัติ");
							PlanDS.getInstance().addData(newRecord, new DSCallback() {
								@Override
								public void execute(DSResponse dsResponse, Object data,
										DSRequest dsRequest) {
										if (dsResponse.getStatus() != 0) {
											SC.warn("การสร้างแผนการผลิตล้มเหลว");
										} else { 
											for (PlanProductDetails item : planProductList) {
												ListGridRecord subNewRecord = PlanProductData.createRecord(item, plan_id);
												PlanProductDS.getInstance(plan_id).addData(subNewRecord);
												//System.out.println("add data " + item.sub_plan_id);
											}
											CreateInvoiceAndSaleOrder(main, invoice_id, invoiceRecord, sale_id, saleRecord, invoiceProductList,saleProductList, " <br><br> สร้างแผนการผลิตเสร็จสิ้น " + "รหัสแผนการผลิต " + plan_id + "<br>" + "กำหนดส่งสินค้าวันที่ " + DateUtil.formatAsShortDate(delivery));
										}
								}
							});
						}
					}
            	});
			} else {
				CreateInvoiceAndSaleOrder(main, invoice_id, invoiceRecord, sale_id, saleRecord, invoiceProductList,saleProductList, "");
			}
			
			
			
	}
	
	private PlanProductDetails CreatePlanProductDetails(Record product, Integer pplan_amount) {
		String sub_plan_id = "SP80" + Math.round((Math.random() * 100)) + Math.round((Math.random() * 100));
		String pid = product.getAttributeAsString("pid");
		String pname = product.getAttributeAsString("name");
		String ptype = product.getAttributeAsString("type");
		Double pweight = product.getAttributeAsDouble("weight");
		String punit = product.getAttributeAsString("unit");
		
		Double psize = product.getAttributeAsDouble("size");
		Double pwidth = product.getAttributeAsDouble("width");
		Double plength = product.getAttributeAsDouble("length");
		Double pheight = product.getAttributeAsDouble("height");
		Double pdiameter = product.getAttributeAsDouble("diameter");
		Double pthickness = product.getAttributeAsDouble("thickness");
		
		PlanProductDetails temp = new PlanProductDetails();
		temp.save(pid, pname, pweight * pplan_amount, ptype, punit, psize, pwidth, plength, pheight, pdiameter, pthickness);
		temp.setID(sub_plan_id);
		temp.setQuantity(pplan_amount);
		
		return temp;
	}
	
	private void CreateInvoiceAndSaleOrder(final Window main, final String invoice_id, Record invoiceRecord, final String sale_id, final Record saleRecord, final ArrayList<SaleProductDetails> invoiceProductList,final ArrayList<SaleProductDetails> saleProductList, final String message) {
		//Auto create invoice
		InvoiceDS.getInstance().addData(invoiceRecord, new DSCallback() {
			@Override
			public void execute(DSResponse dsResponse, Object data,
					DSRequest dsRequest) {
					if (dsResponse.getStatus() != 0) {
						SC.warn("การสร้างรายการขายล้มเหลว กรุณาทำรายการใหม่อีกครั้ง");
						main.destroy();
					} else { 
						for (SaleProductDetails item : invoiceProductList) {
							ListGridRecord subAddRecord = SaleProductData.createRecord(item, invoice_id);
							SaleProductDS.getInstance(invoice_id).addData(subAddRecord);
						}
						
						SaleOrderDS.getInstance().addData(saleRecord, new DSCallback() {
							@Override
							public void execute(DSResponse dsResponse, Object data,
									DSRequest dsRequest) {
									if (dsResponse.getStatus() != 0) {
										SC.warn("การสร้างรายการขายล้มเหลว กรุณาทำรายการใหม่อีกครั้ง");
										main.destroy();
									} else { 
										for (SaleProductDetails item : saleProductList) {
												ListGridRecord subAddRecord = SaleProductData.createRecord(item, sale_id);
												SaleProductDS.getInstance(sale_id).addData(subAddRecord);
										}
										SC.say("สร้างรายการขายเสร็จสิ้น <br> " + "รหัสรายการขาย " + sale_id + "<br> สถานะของรายการขาย " + SaleOrderStatus.getDisplay(saleRecord.getAttributeAsString("status")) + "<br><br> สร้างใบแจ้งหนี้โดยอัตโนมัติ เลขที่ "+ invoice_id + "<br> กำหนดชำระเงินวันที่ " + DateUtil.formatAsShortDate(saleRecord.getAttributeAsDate("due_date")) + message);
										main.destroy();
									}
							}
						});
					}
			}
		});
	}
	
}
