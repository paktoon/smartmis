package com.smart.mis.client.function.sale.invoice;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.smart.mis.client.function.sale.customer.CustomerDS;
import com.smart.mis.client.function.sale.invoice.InvoiceData;
import com.smart.mis.client.function.sale.order.SaleOrderDS;
import com.smart.mis.client.function.sale.order.SaleOrderData;
import com.smart.mis.client.function.sale.order.product.SaleProductDS;
import com.smart.mis.client.function.sale.order.product.SaleProductData;
import com.smart.mis.client.function.sale.order.product.SaleProductDetails;
import com.smart.mis.client.function.sale.quotation.product.QuoteProductDS;
import com.smart.mis.client.function.sale.quotation.product.QuoteProductData;
import com.smart.mis.client.function.sale.quotation.product.QuoteProductDetails;
import com.smart.mis.shared.Country;
import com.smart.mis.shared.EditorWindow;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.FieldVerifier;
import com.smart.mis.shared.ListGridNumberField;
import com.smart.mis.shared.PrintHeader;
import com.smart.mis.shared.sale.Customer;
import com.smart.mis.shared.sale.InvoiceStatus;
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
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.DoubleItem;
import com.smartgwt.client.widgets.form.fields.FloatItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
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

public class InvoiceViewWindow extends EditorWindow{

//	SelectProductList addFunc;
	Customer client;
	
	public InvoiceViewWindow(){
//		super();
//		addFunc = new SelectProductList();
//		setWidth(650);  
//		setHeight(500);
//		setShowMinimizeButton(false);
//		setIsModal(true);
//		setShowModalMask(true);
//		setCanDragResize(false);
//		setCanDragReposition(false);
//		centerInPage();
	}
	
	public void show(ListGridRecord record, boolean edit, User currentUser, int page){
		client = new Customer();
		Window editWindow = new Window();
		editWindow.setTitle("ข้อมูลใบแจ้งหนี้");
		editWindow.setWidth(670);  
		editWindow.setHeight(620);
		editWindow.setShowMinimizeButton(false);
		editWindow.setIsModal(true);
		editWindow.setShowModalMask(true);
		editWindow.setCanDragResize(false);
		editWindow.setCanDragReposition(false);
		editWindow.centerInPage();
		
		//editWindow.setHeaderControls(HeaderControls.HEADER_LABEL);
		editWindow.addItem(getViewEditor(record, edit, editWindow, currentUser, page));
		editWindow.show();
	}
	
	private VLayout getViewEditor(final ListGridRecord record, boolean edit, final Window main, final User currentUser, int page) {
		final VLayout layout = new VLayout();
		layout.setWidth(650);
		layout.setHeight(600);
		layout.setMargin(10);

		final String invoice_id = record.getAttributeAsString("invoice_id");
		String cid = record.getAttributeAsString("cid");

		String fullAddress = getCustomerFullAddress(cid);
		
		String payment_model = record.getAttributeAsString("payment_model");
		Integer credit = record.getAttributeAsInt("credit");
		final String sale_id = record.getAttributeAsString("sale_id");
		Date delivery = record.getAttributeAsDate("delivery");
		final Double netEx = record.getAttributeAsDouble("netExclusive");
		
		String status = record.getAttributeAsString("status");
		//String created_by = record.getAttributeAsString("created_by");
		//Date created_date = record.getAttributeAsDate("created_date");
		Date due_date = record.getAttributeAsDate("due_date");
		
		Date created_date = record.getAttributeAsDate("created_date");
		String purchase_id = record.getAttributeAsString("purchase_id");
		
		//Double recIn = record.getAttributeAsDouble("receivedInclusive");
		//Date paid_date = record.getAttributeAsDate("paid_date");
		
		DynamicForm quotationForm = new DynamicForm();
		quotationForm.setWidth100(); 
		quotationForm.setHeight(30);
		quotationForm.setMargin(5);
		quotationForm.setIsGroup(true);
		quotationForm.setNumCols(8);
		quotationForm.setGroupTitle("ข้อมูลใบแจ้งหนี้");

		StaticTextItem invid = new StaticTextItem("invoice_id", "รหัสใบแจ้งหนี้");
		StaticTextItem sid = new StaticTextItem("sale_id", "รหัสรายการขาย");
		StaticTextItem sts = new StaticTextItem("status", "สถานะ");
		StaticTextItem cdate = new StaticTextItem("due_date", "วันครบกำหนดชำระเงิน");
		//StaticTextItem pdate = new StaticTextItem("paid_date", "วันจ่ายชำระเงิน");
		invid.setValue(invoice_id);
		sid.setValue(sale_id);
		sts.setValue(status);
		sts.setValueMap(InvoiceStatus.getValueMap());
		cdate.setValue(DateTimeFormat.getFormat("MM/dd/yyy").format(due_date));
		quotationForm.setFields(invid, sid, sts, cdate);
		quotationForm.setColWidths(80,80,80,80,80,80,80,80);
		layout.addMember(quotationForm);
		
		final DynamicForm customerForm = new DynamicForm();
		customerForm.setWidth100(); 
		customerForm.setHeight(30);
		customerForm.setMargin(5); 
		customerForm.setNumCols(6); 
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
			final StaticTextItem cus_address = new StaticTextItem("fullAddress", "ที่อยู่");
			cus_address.setColSpan(4);
			cus_address.setDefaultValue(fullAddress);
			final StaticTextItem cus_payment_model = new StaticTextItem("payment_model", "วิธีการชำระเงิน");
			cus_payment_model.setDefaultValue(payment_model);
			final StaticTextItem cus_credit = new StaticTextItem("credit", "เครดิต");
			cus_credit.setDefaultValue(credit);
			cus_credit.setHint("วัน");
			customerForm.setFields(cus_id,cus_type, cus_name, cus_address, cus_payment_model, cus_credit);
		customerForm.setColWidths(100,100,100,100,100,100);
		customerForm.fetchData(new Criterion("cid", OperatorId.EQUALS, cid));
		//customerForm.editRecord(record);
		
//		DynamicForm commentForm = new DynamicForm();
//		commentForm.setWidth(250); 
//		commentForm.setHeight(70);
//		commentForm.setMargin(5);
//		commentForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
////		commentForm.setIsGroup(true);
////		commentForm.setGroupTitle("ความคิดเห็น");
//		
//		TextAreaItem comment_area = new TextAreaItem();
//		//comment_area.setShowTitle(false);
//		comment_area.setTitle("ความคิดเห็น");
//		comment_area.setTitleOrientation(TitleOrientation.TOP);
//		comment_area.setHeight(60);
//		comment_area.setWidth(250);
//		if (edit) comment_area.setCanEdit(true);
//		else comment_area.setCanEdit(false);
//		comment_area.setValue(comment);
//		commentForm.setFields(comment_area);
		
		HLayout headerLayout = new HLayout();
		headerLayout.setWidth100();
		//headerLayout.addMembers(customerForm, commentForm);
		headerLayout.addMembers(customerForm);
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
		final ListGrid saleListGrid = new ListGrid();
		saleListGrid.setHeight(230);
		saleListGrid.setAlternateRecordStyles(true);  
		saleListGrid.setShowAllRecords(true);  
		saleListGrid.setAutoFetchData(true);  
		//if (edit) saleListGrid.setSelectionType(SelectionStyle.SINGLE);
		saleListGrid.setSelectionType(SelectionStyle.NONE);
		saleListGrid.setCanResizeFields(false);
		saleListGrid.setShowGridSummary(true);
		saleListGrid.setEditEvent(ListGridEditEvent.CLICK);  
		saleListGrid.setListEndEditAction(RowEndEditAction.NEXT);
		saleListGrid.setShowRowNumbers(true);
        final Criterion ci = new Criterion("status", OperatorId.EQUALS, true);
		saleListGrid.setCriteria(ci);
//		if (edit) {
//			saleListGrid.setCanRemoveRecords(true);
//			saleListGrid.setWarnOnRemoval(true);
//			saleListGrid.setWarnOnRemovalMessage("คุณต้องการลบ รายการสินค้า หรือไม่?");
//		}
		
		SaleProductDS tempView = new SaleProductDS(invoice_id);
		Record[] cachedData = SaleProductDS.getInstance(invoice_id).getCacheData();
		if (cachedData.length != 0) {
			tempView.setTestData(cachedData);
		}
		saleListGrid.setDataSource(tempView);
		saleListGrid.setUseAllDataSourceFields(false);
		
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
        
        ListGridNumberField quoteItemCell_6 = new ListGridNumberField("sale_amount", 70);
        
        //if (edit) quoteItemCell_6.setCanEdit(true);
        quoteItemCell_6.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_6.setShowGridSummary(true);
        
        ListGridSummaryField quoteItemCell_sum = new ListGridSummaryField("sum_price", 100);

        quoteItemCell_sum.setRecordSummaryFunction(RecordSummaryFunctionType.MULTIPLIER);
        quoteItemCell_sum.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_sum.setShowGridSummary(true);
        quoteItemCell_sum.setCellFormatter(FieldFormatter.getPriceFormat());
        quoteItemCell_sum.setAlign(Alignment.RIGHT);
 
        saleListGrid.setFields(quoteItemCell_1, quoteItemCell_2, quoteItemCell_6, quoteItemCell_3, quoteItemCell_5 , quoteItemCell_sum);
        //itemLayout.addMember(saleListGrid);
        section.setItems(saleListGrid);
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
		dateForm.setGroupTitle("ข้อกำหนดรายการขาย");
		//if (!edit) 
		dateForm.setCanEdit(false);
		final DateItem createDate = new DateItem();
		createDate.setName("created_date");
		createDate.setTitle("วันที่ออกใบแจ้งหนี้");
		createDate.setUseTextField(true);
		
//		final DateItem toDate = new DateItem();
//		toDate.setName("toDate");
//		toDate.setTitle("วันที่สิ้นสุดข้อเสนอ");
//		toDate.setUseTextField(true);
		
		final DateItem deliveryDate = new DateItem();
		deliveryDate.setName("deliveryDate");
		deliveryDate.setTitle("วันที่กำหนดส่งของ");
		deliveryDate.setUseTextField(true);
		
		StaticTextItem ref_id = new StaticTextItem("purchase_id", "เลขที่คำสั่งซื้อ");
		
		createDate.setDefaultChooserDate(created_date);
		createDate.setValue(created_date);
//        toDate.setDefaultChooserDate(to);
//        toDate.setValue(to);
        deliveryDate.setDefaultChooserDate(delivery);
        deliveryDate.setValue(delivery);
        ref_id.setValue(purchase_id);
//        fromDate.setRequired(true);
//        fromDate.setHint("*");
//		toDate.setRequired(true);
//		toDate.setHint("*");
//		deliveryDate.setRequired(true);
//		deliveryDate.setHint("*");
		
//		dateForm.setFields(fromDate, toDate, deliveryDate);
		dateForm.setFields(createDate, deliveryDate, ref_id);
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
		final IButton printButton = new IButton("พิมพ์ใบแจ้งหนี้");
		printButton.setIcon("icons/16/print.png");
		printButton.setWidth(120);
		printButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
                //SC.say("click print invoice");
            	VLayout printLayout = new VLayout(10);
            	printLayout.addMember(new PrintHeader("ใบแจ้งหนี้"));
            	printLayout.addMember(layout);
            	Canvas.showPrintPreview(printLayout);
            	main.destroy();
          }
        });
		if (!status.equals("1_waiting_for_payment")) printButton.disable();
		
		final IButton printReceipt = new IButton("พิมพ์ใบเสร็จ");
		printReceipt.setIcon("icons/16/print.png");
		printReceipt.setWidth(120);
		printReceipt.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
                //SC.say("click print receipt");
            	VLayout printLayout = new VLayout(10);
            	printLayout.addMember(new PrintHeader("ใบเสร็จรับเงิน"));
            	printLayout.addMember(layout);
            	Canvas.showPrintPreview(printLayout);
            	main.destroy();
          }
        });
		
		final IButton closeButton = new IButton("ปิด");
		closeButton.setIcon("icons/16/close.png");
		closeButton.setWidth(120);
		closeButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	main.destroy();
          }
        });
		
		final IButton receiptButton = new IButton("บันทึกรับชำระเงิน");
		receiptButton.setIcon("icons/16/save.png");
		receiptButton.setWidth(120);
		receiptButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 

				final Window confirm = new Window();
				confirm.setTitle("รายละเอียดการรับชำระเงิน");
				confirm.setWidth(350);
				confirm.setHeight(200);
				confirm.setShowMinimizeButton(false);
				confirm.setIsModal(true);
				confirm.setShowModalMask(true);
				confirm.setCanDragResize(false);
				confirm.setCanDragReposition(false);
				confirm.centerInPage();
				VLayout receiptLayout = new VLayout();
				receiptLayout.setMargin(10);
				
				final DynamicForm receiptForm = new DynamicForm();
				receiptForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
				StaticTextItem need_payment = new StaticTextItem("need_payment", "ยอดที่ต้องชำระ");
				NumberFormat nf = NumberFormat.getFormat("#,##0.00");
				need_payment.setValue(nf.format(netEx * 1.07));
				need_payment.setHint("บาท");
				final DoubleItem received_payment = new DoubleItem("received_payment", "ยอดที่รับชำระ");
				StaticTextItem received_date = new StaticTextItem("received_date", "วันที่รับชำระเงิน");
				received_date.setValue(DateTimeFormat.getFormat("MM/dd/yyy").format(new Date()));
				StaticTextItem received_by = new StaticTextItem("received_by", "รับชำระเงินโดย");
				received_by.setValue(currentUser.getFirstName() + " " + currentUser.getLastName());
				
				received_payment.setRequired(true);
				received_payment.setHint("บาท *");
				
				receiptForm.setFields(need_payment, received_payment, received_date, received_by);
				
				receiptLayout.addMember(receiptForm);
				
				HLayout controlLayout = new HLayout();
		        controlLayout.setMargin(10);
		        controlLayout.setMembersMargin(10);
		        controlLayout.setAlign(Alignment.CENTER);
		        IButton confirmButton = new IButton("บันทึก");
		        confirmButton.setIcon("icons/16/save.png");
		        IButton cancelButton = new IButton("ยกเลิก");
		        cancelButton.setIcon("icons/16/close.png");
		        controlLayout.addMember(confirmButton);
		        controlLayout.addMember(cancelButton);
		        confirmButton.addClickHandler(new ClickHandler() {  
		            public void onClick(ClickEvent event) { 
		            	if (!receiptForm.validate() || received_payment.getValueAsDouble() < (netEx * 1.07)) {
		            		SC.warn("ข้อมูลไม่ถูกต้อง กรุณาตรวจสอบข้อมูลใหม่อีกครั้ง");
		            		received_payment.clearValue();
		            		return;
		            	}
		            	
		            	SC.confirm("ยืนยันการบันทึกรับชำระเงิน", "ต้องการบันทึกรับชำระเงินหรือไม่?" , new BooleanCallback() {
							@Override
							public void execute(Boolean value) {
								if (value) {
					            		record.setAttribute("status", "2_paid");
										record.setAttribute("receivedInclusive", received_payment.getValueAsDouble());
					            		record.setAttribute("paid_date", new Date());
					            		record.setAttribute("paid_by", currentUser.getFirstName() + " " + currentUser.getLastName());
					            		InvoiceDS.getInstance().updateData(record, new DSCallback() {
											@Override
											public void execute(DSResponse dsResponse, Object data,
													DSRequest dsRequest) {
													if (dsResponse.getStatus() != 0) {
														SC.warn("การบันทึกรับชำระเงิน ล้มเหลว");
													} else { 
														SC.warn("การบันทึกรับชำระเงิน เสร็จสมบูรณ์");
														confirm.destroy();
														main.destroy();
													}
											}
										});
								}
							}
		            	});
		          }
		        });
		        
		        cancelButton.addClickHandler(new ClickHandler() {  
		            public void onClick(ClickEvent event) { 
		            	confirm.destroy();
		          }
		        });
		        
		        receiptLayout.addMember(controlLayout);
		        
		        confirm.addItem(receiptLayout);
		        
		        confirm.show();
          }
        });
		
//		final IButton approveButton = new IButton("อนุมัติ");
//		//approveButton.setIcon("icons/16/approved.png");
//		approveButton.setWidth(120);
//		approveButton.addClickHandler(new ClickHandler() {  
//            public void onClick(ClickEvent event) { 
//            	SC.confirm("ยืนยันการทำรายการ", "ต้องการอนุมัติใบเสนอราคา หรือไม่?" , new BooleanCallback() {
//					@Override
//					public void execute(Boolean value) {
//						if (value) {
//							//saveQuotation(main, quote_id, customerForm, saleListGrid, fromDate.getValueAsDate(), toDate.getValueAsDate(), deliveryDate.getValueAsDate(), currentUser);
//							updateQuoteStatus(quote_id, "3_approved", "");
//							main.destroy();
//						}
//					}
//            	});
//          }
//        });
//		
//		final IButton disapproveButton = new IButton("ไม่อนุมัติ");
//		//disapproveButton.setIcon("icons/16/delete.png");
//		disapproveButton.setWidth(120);
//		disapproveButton.addClickHandler(new ClickHandler() {  
//            public void onClick(ClickEvent event) { 
//            	SC.confirm("ยืนยันการทำรายการ", "ต้องการส่งใบเสนอราคากลับไปแก้ไข หรือไม่?" , new BooleanCallback() {
//					@Override
//					public void execute(Boolean value) {
//						if (value) {
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
//						}
//					}
//            	});
//          }
//        });
		
//		final IButton createSaleOrderButton = new IButton("สร้างรายการขาย");
//		//approveButton.setIcon("icons/16/approved.png");
//		createSaleOrderButton.setWidth(120);
//		createSaleOrderButton.addClickHandler(new ClickHandler() {  
//            public void onClick(ClickEvent event) { 
//            	SC.confirm("ยืนยันการทำรายการ", "ต้องการสร้างรายการขาย หรือไม่?" , new BooleanCallback() {
//					@Override
//					public void execute(Boolean value) {
//						if (value) {
//							SC.askforValue("กรุณาระบุเลขที่คำสั่งซื้อ", new ValueCallback() {
//								@Override
//								public void execute(String value) {
//									if (value == null || value.equals("")){
//										SC.warn("กรุณาระบุเลขที่คำสั่งซื้อในกล่องข้อความ");
//									} else {
//										if (customerForm.validate()) createSaleOrder(main, quote_id, customerForm, saleListGrid, deliveryDate.getValueAsDate(), currentUser, value);
//									}
//								}});
//						}
//					}
//            	});
//          }
//        });
		
//		if (page == 3) {
//			controls.addMember(createSaleOrderButton);
//		} else if (page == 2) {
//			if (!record.getAttributeAsString("status").equalsIgnoreCase("2_waiting_for_approved")){
//				approveButton.disable();
//				disapproveButton.disable();
//			} else {
//				approveButton.setIcon("icons/16/approved.png");
//				disapproveButton.setIcon("icons/16/delete.png");
//			}
//			controls.addMember(approveButton);
//			controls.addMember(disapproveButton);
//		} else {
//			controls.addMember(printButton);
//			controls.addMember(saveButton);
//		}
		if (page == 1) controls.addMember(printButton);
		if (page == 2) controls.addMember(receiptButton);
		if (page == 3) controls.addMember(printReceipt);
		controls.addMember(closeButton);
		layout.addMember(controls);
		
//		addButton.addClickHandler(new ClickHandler() {  
//            public void onClick(ClickEvent event) {
//            	//SC.warn("add click " + saleListGrid.getRecords().length);
//            	ArrayList<String> selected = new ArrayList<String>();
//            	for (ListGridRecord item : saleListGrid.getRecords()) {
//            		selected.add(item.getAttributeAsString("pid"));
//            	}
//            	addFunc.show(selected, saleListGrid, summaryForm);
//            }
//        });
//		
//		delButton.addClickHandler(new ClickHandler() {  
//            public void onClick(ClickEvent event) {
//            	ListGridRecord selected = saleListGrid.getSelectedRecord();
//            	if (selected != null) {
//            		//saleListGrid.removeSelectedData();
//            		selected.setAttribute("status", false);
//            		saleListGrid.updateData(selected);
//            		saleListGrid.removeSelectedData(new DSCallback() {
//						@Override
//						public void execute(DSResponse dsResponse, Object data,
//								DSRequest dsRequest) {
//								if (dsResponse.getStatus() != 0) {
//									SC.warn("การลบสินค้าล้มเหลว");
//								} else { 
//									summaryPriceRecalculate(saleListGrid.getRecords(), summaryForm);
//								}
//						}
//					}, null);
//            	} else {
//            		SC.warn("กรุณาเลือกรายการที่ต้องการลบ");
//            	}
//            }
//        });
		
//        saleListGrid.addCellSavedHandler(new CellSavedHandler() {  
//			@Override
//			public void onCellSaved(CellSavedEvent event) {
//				summaryPriceRecalculate(saleListGrid.getRecords(), summaryForm);
//			}  
//        });
        
//        saleListGrid.addRemoveRecordClickHandler(new RemoveRecordClickHandler() {
//
//			@Override
//			public void onRemoveRecordClick(RemoveRecordClickEvent event) {
//				//System.out.println("onRemoveRecordClick getResultSize " +saleListGrid.getResultSet().getResultSize());
//				System.out.println("onRemoveRecordClick getRecords " +saleListGrid.getRecords().length);
//				summaryPriceRecalculate(saleListGrid.getRecords(), summaryForm);
//			}
//        });
//        
//        saleListGrid.addFetchDataHandler(new FetchDataHandler() {
//
//			@Override
//			public void onFilterData(FetchDataEvent event) {
//				//System.out.println("onFilterData getResultSize " +saleListGrid.getResultSet().getResultSize());
//				System.out.println("onFilterData getRecords " +saleListGrid.getRecords().length);
//				if (saleListGrid.getRecords().length != 0) {
//					summaryPriceRecalculate(saleListGrid.getRecords(), summaryForm);
//				}
//			}
//        	
//        });
        
		return layout;
	}

	String getCustomerFullAddress(String cid) {
		CustomerDS.getInstance().refreshData();
		Record[] records = CustomerDS.getInstance().applyFilter(CustomerDS.getInstance().getCacheData(), new Criterion("cid", OperatorId.EQUALS, cid));
		Record selected = records[0];
		String address = selected.getAttributeAsString("address");
		String street = selected.getAttributeAsString("street");
		String city = selected.getAttributeAsString("city");
		String state = selected.getAttributeAsString("state");
		String postal = selected.getAttributeAsString("postal");
		String country = selected.getAttributeAsString("country");
		
		if (country.equalsIgnoreCase("TH")) {
			String fullAddress = address;
			if (street !=null) fullAddress += " ถนน " + street;
			if (city !=null) fullAddress += " เขต " + city;
			if (state !=null) fullAddress += " จังหวัด " + state;
			if (postal !=null) fullAddress += " รหัสไปรษณีย์ " + postal;
			if (country !=null) fullAddress += " ประเทศ " + Country.getThaiCountryName(country);
			return fullAddress;
		} else {
			String fullAddress = address;
			if (street !=null) fullAddress += " ,Street " + street;
			if (city !=null) fullAddress += " ,City " + city;
			if (state !=null) fullAddress += " ,State " + state;
			if (postal !=null) fullAddress += " ,Postal " + postal;
			if (country !=null) fullAddress += " ,Country " + Country.getEnglishCountryName(country);
			return fullAddress;
		}
	}
}
