package com.smart.mis.client.function.sale.quotation;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.smart.mis.client.function.sale.customer.CustomerDS;
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
import com.smart.mis.shared.sale.Customer;
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

public class QuoteViewWindow extends EditorWindow{

	SelectProductList addFunc;
	Customer client;
	
	public QuoteViewWindow(){
//		super();
		addFunc = new SelectProductList();
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
		editWindow.setTitle("ข้อมูลใบเสนอราคา");
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
		VLayout layout = new VLayout();
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
						String customer_address = selected.getAttributeAsString("address");
						String customer_phone = selected.getAttributeAsString("cus_phone");
						String contact_name = selected.getAttributeAsString("contact_name");
						String contact_phone = selected.getAttributeAsString("contact_phone");
						String contact_email = selected.getAttributeAsString("contact_email");
						String zone = selected.getAttributeAsString("zone");
						
						client.setAttributes(customer_id, customer_name, customer_phone, contact_name, contact_phone, contact_email, customer_address, customer_type, zone);

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
            	Canvas.showPrintPreview(PrintQuotation.getPrintContainer(record));
          }
        });
		if (edit || !status.equals("อนุมัติแล้ว")) printButton.disable();
		
		final IButton saveButton = new IButton("บันทึกการแก้ไข");
		saveButton.setIcon("icons/16/save.png");
		saveButton.setWidth(120);
		saveButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
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
							updateQuoteStatus(quote_id, "อนุมัติแล้ว", "");
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
							SC.askforValue("กรุณาใส่ความคิดเห็น", new ValueCallback() {
								@Override
								public void execute(String value) {
									if (value == null || value.equals("")){
										SC.warn("กรุณาใส่ความคิดเห็นในกล่องข้อความ");
									} else {
										updateQuoteStatus(quote_id, "รอแก้ไข", value);
										main.destroy();
									}
								}});
						}
					}
            	});
          }
        });
		
		final IButton createSaleOrderButton = new IButton("สร้างรายการขาย");
		//approveButton.setIcon("icons/16/approved.png");
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
										if (customerForm.validate()) createSaleOrder(main, quote_id, customerForm, quoteListGrid, deliveryDate.getValueAsDate(), currentUser, value);
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
			if (!record.getAttributeAsString("status").equalsIgnoreCase("รออนุมัติ")){
				approveButton.disable();
				disapproveButton.disable();
			} else {
				approveButton.setIcon("icons/16/approved.png");
				disapproveButton.setIcon("icons/16/delete.png");
			}
			controls.addMember(approveButton);
			controls.addMember(disapproveButton);
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
		//final String quote_id = "QA70" + Math.round((Math.random() * 100));
		final ArrayList<QuoteProductDetails> productList = new ArrayList<QuoteProductDetails>();
		
		for (ListGridRecord item : all){
			total_weight += item.getAttributeAsDouble("weight");
			total_amount += item.getAttributeAsInt("quote_amount");
			total_netExclusive += item.getAttributeAsDouble("sum_price");
			
			String sub_quote_id = item.getAttributeAsString("sub_quote_id");
			String pid = item.getAttributeAsString("pid");
			String pname = item.getAttributeAsString("name");
			String ptype = item.getAttributeAsString("type");
			String psize = item.getAttributeAsString("size");
			Double pweight = item.getAttributeAsDouble("weight");
			Integer pquote_amount = item.getAttributeAsInt("quote_amount");
			String punit = item.getAttributeAsString("unit");
			Double pprice = item.getAttributeAsDouble("price");
			QuoteProductDetails temp = new QuoteProductDetails();
			temp.save(pid, pname, psize, pweight, pprice, ptype, punit);
			temp.setID(sub_quote_id, quote_id);
			temp.setQuantity(pquote_amount);
			productList.add(temp);
		}
		//System.out.println(total_weight + " " + total_amount + " " + total_netExclusive);
			//status
			final String quote_status = "รออนุมัติ";
			
			if (customer.getField("cid").getValue() == null || customer.getField("cus_name").getValue() == null) {
				SC.warn("ชื่อและรหัสลูกค้าไม่ถุกต้อง");
				return;
			}
			
			String cid = (String) customer.getField("cid").getValue();
			String cus_name = (String) customer.getField("cus_name").getValue();
			String payment_model = (String) customer.getField("payment_model").getValue();
			Integer credit = (Integer) customer.getField("credit").getValue();
			//System.out.println(cid + " " + cus_name + " " + payment_model + " " + credit);
			
			ListGridRecord updateRecord = QuotationData.createUpdateRecord(quote_id, cid, cus_name, payment_model, credit, from, to, delivery, total_weight, total_amount, total_netExclusive, new Date(), currentUser.getFirstName() + " " + currentUser.getLastName(), "", quote_status);
			
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
									item.sub_quote_id = "QS80" + Math.round((Math.random() * 100));
									ListGridRecord subUpdateRecord = QuoteProductData.createRecord(item);
									QuoteProductDS.getInstance(quote_id).addData(subUpdateRecord);
								} else  {
									ListGridRecord subUpdateRecord = QuoteProductData.createRecord(item);
									QuoteProductDS.getInstance(quote_id).updateData(subUpdateRecord);
								}
							}
							SC.warn("แก้ไขใบเสนอราคาเสร็จสิ้น <br> " + "รหัสใบเสนอราคา " + quote_id + "<br> สถานะของใบเสนอราคา " + quote_status);
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
						SC.warn("แก้ไขสถานะใบเสนอราคา \"" + status + "\" เสร็จสิ้น");
					}
			}
		});
	}
	
	public void createSaleOrder(final Window main, final String quote_id, DynamicForm customer, ListGrid quoteListGrid, Date delivery, User currentUser, String purcharse_id){
		
		ListGridRecord[] all = quoteListGrid.getRecords();
		
//		if (all.length == 0) {
//			SC.warn("กรูณาเลือกรายการสินค้าอย่างน้อย 1 รายการ");
//			return;
//		}
		
		Double total_weight = 0.0;
		Double total_netExclusive = 0.0;
		Integer total_amount = 0;
		final String sale_id = "SO70" + Math.round((Math.random() * 100));
		final ArrayList<SaleProductDetails> productList = new ArrayList<SaleProductDetails>();
		
		for (ListGridRecord item : all){
			total_weight += item.getAttributeAsDouble("weight");
			total_amount += item.getAttributeAsInt("quote_amount");
			total_netExclusive += item.getAttributeAsDouble("sum_price");
			
			String sub_sale_id = "SS80" + Math.round((Math.random() * 100));
			String pid = item.getAttributeAsString("pid");
			String pname = item.getAttributeAsString("name");
			String ptype = item.getAttributeAsString("type");
			String psize = item.getAttributeAsString("size");
			Double pweight = item.getAttributeAsDouble("weight");
			Integer psale_amount = item.getAttributeAsInt("quote_amount");
			String punit = item.getAttributeAsString("unit");
			Double pprice = item.getAttributeAsDouble("price");
			SaleProductDetails temp = new SaleProductDetails();
			temp.save(pid, pname, psize, pweight, pprice, ptype, punit);
			temp.setID(sub_sale_id, sale_id);
			temp.setQuantity(psale_amount);
			productList.add(temp);
			//status
		}	
//			if (customer.getField("cid").getValue() == null || customer.getField("cus_name").getValue() == null) {
//				SC.warn("ชื่อและรหัสลูกค้าไม่ถุกต้อง");
//				return;
//			}

			final String sale_status = "รอผลิต";
			String cid = (String) customer.getField("cid").getValue();
			String cus_name = (String) customer.getField("cus_name").getValue();
			String payment_model = (String) customer.getField("payment_model").getValue();
			Integer credit = (Integer) customer.getField("credit").getValue();
			
			ListGridRecord newRecord = SaleOrderData.createRecord(sale_id, quote_id, cid, cus_name, payment_model, credit, delivery, total_weight, total_amount, total_netExclusive, new Date(), null, currentUser.getFirstName() + " " + currentUser.getLastName(), null, sale_status, purcharse_id);
			
			SaleOrderDS.getInstance().addData(newRecord, new DSCallback() {
				@Override
				public void execute(DSResponse dsResponse, Object data,
						DSRequest dsRequest) {
						if (dsResponse.getStatus() != 0) {
							SC.warn("การสร้างรายการขายล้มเหลว กรุณาทำรายการใหม่อีกครั้ง");
							main.destroy();
						} else { 
							for (SaleProductDetails item : productList) {
								//if (item.sub_sale_id == null) {
									//item.sub_sale_id = "SS80" + Math.round((Math.random() * 100));
									ListGridRecord subUpdateRecord = SaleProductData.createRecord(item);
									SaleProductDS.getInstance(sale_id).addData(subUpdateRecord);
								//} else  {
								//	ListGridRecord subUpdateRecord = SaleProductData.createRecord(item);
								//	SaleProductDS.getInstance(sale_id).updateData(subUpdateRecord);
								//}
							}
							SC.warn("สร้างรายการขายเสร็จสิ้น <br> " + "รหัสรายการขาย " + quote_id + "<br> สถานะของรายการขาย " + sale_status);
							main.destroy();
						}
				}
			});
	}
}
