package com.smart.mis.client.function.purchasing.order;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.smart.mis.client.function.purchasing.order.PurchaseOrderDS;
import com.smart.mis.client.function.purchasing.order.PurchaseOrderData;
import com.smart.mis.client.function.purchasing.order.material.OrderMaterialDS;
import com.smart.mis.client.function.purchasing.order.material.OrderMaterialData;
import com.smart.mis.client.function.purchasing.order.material.OrderMaterialDetails;
import com.smart.mis.client.function.purchasing.request.material.RequestMaterialDS;
import com.smart.mis.client.function.purchasing.request.material.RequestMaterialData;
import com.smart.mis.client.function.purchasing.request.material.RequestMaterialDetails;
import com.smart.mis.client.function.purchasing.supplier.SupplierDS;
import com.smart.mis.client.function.report.financial.DisburseMaterialDS;
import com.smart.mis.client.function.report.inventory.MaterialReceivedReportDS;
import com.smart.mis.client.function.sale.invoice.InvoiceDS;
import com.smart.mis.shared.EditorWindow;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.FieldVerifier;
import com.smart.mis.shared.ListGridNumberField;
import com.smart.mis.shared.PrintHeader;
import com.smart.mis.shared.purchasing.PurchaseOrderStatus;
import com.smart.mis.shared.purchasing.PurchaseRequestStatus;
import com.smart.mis.shared.purchasing.Supplier;
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
import com.smartgwt.client.widgets.form.fields.DoubleItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SelectOtherItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
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

public class OrderViewWindow extends EditorWindow{

//	SelectMaterailList addFunc;
//	Supplier client;
//	String material_list = "NONE";
	
	public OrderViewWindow(){
//		addFunc = new SelectMaterailList();
	}
	
	public void show(ListGridRecord record, boolean edit, User currentUser, int page){
//		client = new Supplier();
		Window editWindow = new Window();
		editWindow.setTitle("ข้อมูลคำสั่งซื้อ");
		editWindow.setWidth(670);  
		editWindow.setHeight(650);
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
		
		final String order_id = record.getAttributeAsString("order_id");
		String request_id = record.getAttributeAsString("request_id");
		String status = record.getAttributeAsString("status");
		String payment_status = record.getAttributeAsString("payment_status");
		Date delivery = record.getAttributeAsDate("delivery");
		
		String supplier_id = record.getAttributeAsString("sid");
		String supplier_name = record.getAttributeAsString("sup_name");
		
		String payment_model = record.getAttributeAsString("payment_model");
		Integer credit = record.getAttributeAsInt("credit");
		final Double netEx = record.getAttributeAsDouble("netExclusive");
		//String comment = record.getAttributeAsString("comment");
		//System.out.println(payment_model + " " + credit);
		//System.out.println(from + " " + to + " " + delivery + " " + netEx);

		String created_by = record.getAttributeAsString("created_by");
		Date created_date = record.getAttributeAsDate("created_date");
		String modified_by = record.getAttributeAsString("modified_by");
		Date modified_date = record.getAttributeAsDate("modified_date");
		
		DynamicForm quotationForm = new DynamicForm();
		quotationForm.setWidth100(); 
		quotationForm.setHeight(30);
		quotationForm.setMargin(5);
		quotationForm.setIsGroup(true);
		quotationForm.setNumCols(8);
		quotationForm.setGroupTitle("ข้อมูลคำสั่งซื้อ");

		StaticTextItem orid = new StaticTextItem("order_id", "รหัสคำสั่งซื้อ");
		StaticTextItem rqid = new StaticTextItem("request_id", "รหัสใบเสนอซื้อ");
		StaticTextItem sts = new StaticTextItem("status", "สถานะคำสั่งซื้อ");
		StaticTextItem ddate = new StaticTextItem("delivery", "วันที่กำหนดส่ง");
		orid.setValue(order_id);
		rqid.setValue(request_id);
		sts.setValue(PurchaseOrderStatus.getDisplay(status));
		ddate.setValue(DateTimeFormat.getFormat("MM/dd/yyy").format(delivery));
		quotationForm.setFields(orid, rqid, sts, ddate);
		quotationForm.setColWidths(80,80,100,100,100,100,100,100);
		layout.addMember(quotationForm);
		
		//Prepare supplier data - SupplierDS.getInstance()
		SupplierDS.getInstance().refreshData();
		Record[] selectedSupplier = SupplierDS.getInstance().applyFilter(SupplierDS.getInstance().getCacheData(), new Criterion("sid", OperatorId.EQUALS, supplier_id));
		
		String fullAddress = "no data found, please contact admin";
		String phone1 = "-";
		String phone2 = "-";
		String email = "-";
		String fax = "-";
		if (selectedSupplier.length == 1) {
			String address = selectedSupplier[0].getAttributeAsString("address");
			String street = selectedSupplier[0].getAttributeAsString("street");
			String city = selectedSupplier[0].getAttributeAsString("city");
			String state = selectedSupplier[0].getAttributeAsString("state");
			String postal = selectedSupplier[0].getAttributeAsString("postal");
			fullAddress = address;
			if (street !=null) fullAddress += " ถนน " + street;
			if (city !=null) fullAddress += " เขต " + city;
			if (state !=null) fullAddress += " จังหวัด " + state;
			if (postal !=null) fullAddress += " รหัสไปรษณีย์ " + postal;
			phone1 = selectedSupplier[0].getAttributeAsString("sup_phone1");
			phone2 = selectedSupplier[0].getAttributeAsString("sup_phone2");
			email = selectedSupplier[0].getAttributeAsString("email");
			fax = selectedSupplier[0].getAttributeAsString("fax");
		}
		
		
		final DynamicForm suppilerForm = new DynamicForm();
		suppilerForm.setWidth100(); 
		suppilerForm.setMargin(5); 
		suppilerForm.setNumCols(6); 
		suppilerForm.setCellPadding(2);
		suppilerForm.setAutoFocus(true);
		suppilerForm.setSelectOnFocus(true);
		//suppilerForm.setDataSource(SupplierDS.getInstance());
		//suppilerForm.setUseAllDataSourceFields(false);
		suppilerForm.setIsGroup(true);
		suppilerForm.setGroupTitle("ข้อมูลผู้จำหน่าย");
		
		final StaticTextItem sup_id = new StaticTextItem("sid", "รหัสผู้จำหน่าย");
		sup_id.setDefaultValue(supplier_id);
		final StaticTextItem sup_name = new StaticTextItem("sup_name", "ชื่อผู้จำหน่าย");
		//sup_name.setColSpan(4);
		sup_name.setDefaultValue(supplier_name);
		final StaticTextItem sup_email = new StaticTextItem("email", "อีเมล");
		sup_email.setDefaultValue(email);
		final StaticTextItem sup_phone1 = new StaticTextItem("sup_phone1", "โทรศัพท์ 1");
		sup_phone1.setDefaultValue(phone1);
		final StaticTextItem sup_phone2 = new StaticTextItem("sup_phone2", "โทรศัพท์ 2");
		sup_phone2.setDefaultValue(phone2);
		final StaticTextItem sup_fax = new StaticTextItem("fax", "โทรสาร");
		sup_fax.setDefaultValue(fax);
		final StaticTextItem sup_address = new StaticTextItem("fullAddress", "ที่อยู่");
		sup_address.setDefaultValue(fullAddress);
		sup_address.setColSpan(6);
		
		suppilerForm.setFields(sup_id, sup_name, sup_email, sup_phone1, sup_phone2, sup_fax, sup_address);
		suppilerForm.setColWidths(100,100,100,100,100,100);
		layout.addMember(suppilerForm);
		
		final DynamicForm paymentForm = new DynamicForm();
		paymentForm.setWidth100(); 
		paymentForm.setMargin(5); 
		paymentForm.setNumCols(6); 
		paymentForm.setCellPadding(2);
		paymentForm.setAutoFocus(true);
		paymentForm.setSelectOnFocus(true);
		//suppilerForm.setDataSource(SupplierDS.getInstance());
		//suppilerForm.setUseAllDataSourceFields(false);
		paymentForm.setIsGroup(true);
		paymentForm.setGroupTitle("ข้อมูลการชำระเงิน");
		
		StaticTextItem psts = new StaticTextItem("payment_status", "สถานะการชำระเงิน");
		psts.setDefaultValue(PurchaseOrderStatus.getPaymentDisplay(payment_status));
		StaticTextItem sup_payment_model = new StaticTextItem("payment_model", "วิธีการชำระเงิน");
		sup_payment_model.setValue(payment_model);
		StaticTextItem sup_credit = new StaticTextItem("credit", "เครดิต");
		sup_credit.setDefaultValue(credit);
		sup_credit.setHint("วัน");
		paymentForm.setFields(psts, sup_payment_model, sup_credit);
		paymentForm.setColWidths(100, 100, 100, 100, 100, 100);
		layout.addMember(paymentForm);
		
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
//		
//		HLayout headerLayout = new HLayout();
//		headerLayout.setWidth100();
//		headerLayout.addMembers(suppilerForm, commentForm);
//		//headerLayout.addMembers(suppilerForm);
//		layout.addMember(headerLayout);
		
		SectionStack sectionStack = new SectionStack();
    	sectionStack.setWidth100();
    	sectionStack.setHeight(250);
    	SectionStackSection section = new SectionStackSection("รายการวัตถุดิบ");  
    	section.setCanCollapse(false);  
        section.setExpanded(true);
        
//        IButton addButton = new IButton("เพิ่มวัตถุดิบ");
//		addButton.setIcon("[SKIN]actions/add.png");
//        IButton delButton = new IButton("ลบวัตถุดิบ");
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
		
		OrderMaterialDS tempView = new OrderMaterialDS(order_id);
		Record[] cachedData = OrderMaterialDS.getInstance(order_id).getCacheData();
		if (cachedData.length != 0) {
			tempView.setTestData(cachedData);
		}
		quoteListGrid.setDataSource(tempView);
		quoteListGrid.setUseAllDataSourceFields(false);
		
		ListGridField quoteItemCell_1 = new ListGridField("mid", 60);
        ListGridField quoteItemCell_2 = new ListGridField("mat_name"); 
        quoteItemCell_2.setTitle("ชื่อวัตถุดิบ");
        quoteItemCell_2.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });  
        quoteItemCell_2.setShowGridSummary(true);
        ListGridField quoteItemCell_3 = new ListGridField("unit", 40);
        
        ListGridField quoteItemCell_4 = new ListGridField("weight", 90);
        quoteItemCell_4.setShowGridSummary(false);
        quoteItemCell_4.setCellFormatter(FieldFormatter.getNumberFormat());
        quoteItemCell_4.setAlign(Alignment.RIGHT);
        quoteItemCell_4.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_4.setShowGridSummary(true);
        quoteItemCell_4.setIncludeInRecordSummary(false);
        
        ListGridField quoteItemCell_5 = new ListGridField("price", 90);
        //if (edit) quoteItemCell_5.setCanEdit(true);
        quoteItemCell_5.setShowGridSummary(false);
        quoteItemCell_5.setCellFormatter(FieldFormatter.getPriceFormat());
        quoteItemCell_5.setAlign(Alignment.RIGHT);
        
        ListGridNumberField quoteItemCell_6 = new ListGridNumberField("request_amount", 70);
        //if (edit) quoteItemCell_6.setCanEdit(true);
        //quoteItemCell_6.setSummaryFunction(SummaryFunctionType.SUM);
        //quoteItemCell_6.setShowGridSummary(true);
        
        ListGridSummaryField quoteItemCell_sum = new ListGridSummaryField("sum_price", 100);

        quoteItemCell_sum.setRecordSummaryFunction(RecordSummaryFunctionType.MULTIPLIER);
        quoteItemCell_sum.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_sum.setShowGridSummary(true);
        quoteItemCell_sum.setCellFormatter(FieldFormatter.getPriceFormat());
        quoteItemCell_sum.setAlign(Alignment.RIGHT);
 
        quoteListGrid.setFields(quoteItemCell_1, quoteItemCell_2, quoteItemCell_6, quoteItemCell_3, quoteItemCell_4, quoteItemCell_5 , quoteItemCell_sum);
        //itemLayout.addMember(quoteListGrid);
        section.setItems(quoteListGrid);
        sectionStack.setSections(section);
		layout.addMember(sectionStack);
		
		HLayout footerLayout = new HLayout();
		footerLayout.setHeight(100);
		
		final DynamicForm endForm = new DynamicForm();
		endForm.setWidth(330);
		endForm.setNumCols(4);
		endForm.setMargin(5);
		endForm.setIsGroup(true);
		//dateForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
		endForm.setGroupTitle("ข้อมูลอ้างอิง");
		
		StaticTextItem cby = new StaticTextItem("created_by", "สร้างโดย");
		StaticTextItem cdate = new StaticTextItem("created_date", "สร้างเมื่อ");
		cby.setValue(created_by);
		cdate.setValue(DateTimeFormat.getFormat("MM/dd/yyy").format(created_date));
		
		StaticTextItem aby = new StaticTextItem("modified_by", "อนุมัติโดย");
		StaticTextItem adate = new StaticTextItem("modified_date", "อนุมัติเมื่อ");
		aby.setValue(modified_by);
		adate.setValue(DateTimeFormat.getFormat("MM/dd/yyy").format(modified_date));
		
		endForm.setFields(cby, aby, cdate, adate);
		endForm.setColWidths(100,180,100,180);
		//dateForm.editRecord(record);
		footerLayout.addMember(endForm);
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
		final IButton printButton = new IButton("พิมพ์คำสั่งซื้อ");
		printButton.setIcon("icons/16/print.png");
		printButton.setWidth(120);
		printButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
                //SC.warn("click print");
            	//Canvas.showPrintPreview(PrintQuotation.getPrintContainer(record));
                VLayout printLayout = new VLayout(10);
            	printLayout.addMember(new PrintHeader("ใบสั่งซื้อ"));
            	printLayout.addMember(layout);
            	Canvas.showPrintPreview(printLayout);
            	main.destroy();
          }
        });
//		if (edit || !status.equals("3_approved")) printButton.disable();
//		
//		final IButton saveButton = new IButton("บันทึกการแก้ไข");
//		saveButton.setIcon("icons/16/save.png");
//		saveButton.setWidth(120);
//		saveButton.addClickHandler(new ClickHandler() {  
//            public void onClick(ClickEvent event) { 
//            	SC.confirm("ยืนยันการทำรายการ", "ต้องการบันทึกการแก้ไขใบเสนอซื้อ หรือไม่?" , new BooleanCallback() {
//					@Override
//					public void execute(Boolean value) {
//						if (value) {
//							if (suppilerForm.validate()) saveRequest(main, request_id, suppilerForm, quoteListGrid, fromDate.getValueAsDate(), toDate.getValueAsDate(), deliveryDate.getValueAsDate(), currentUser);
//						}
//					}
//            	});
//          }
//        });
//		if (!edit) saveButton.disable();
		
		final IButton closeButton = new IButton("ปิด");
		closeButton.setIcon("icons/16/close.png");
		closeButton.setWidth(120);
		closeButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	main.destroy();
          }
        });
		
		final IButton paidButton = new IButton("บันทึกจ่ายชำระเงิน");
		paidButton.setIcon("icons/16/save.png");
		paidButton.setWidth(120);
		paidButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 

				final Window confirm = new Window();
				confirm.setTitle("รายละเอียดการจ่ายชำระเงิน");
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
				final DoubleItem paid_payment = new DoubleItem("paid_payment", "ยอดที่จ่ายชำระ");
				StaticTextItem paid_date = new StaticTextItem("paid_date", "วันที่จ่ายชำระเงิน");
				paid_date.setValue(DateTimeFormat.getFormat("MM/dd/yyy").format(new Date()));
				StaticTextItem paid_by = new StaticTextItem("paid_by", "รับชำระเงินโดย");
				paid_by.setValue(currentUser.getFirstName() + " " + currentUser.getLastName());
				
				paid_payment.setRequired(true);
				paid_payment.setHint("บาท *");
				
				receiptForm.setFields(need_payment, paid_payment, paid_date, paid_by);
				
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
		            	if (!receiptForm.validate() || paid_payment.getValueAsDouble() < (netEx * 1.07)) {
		            		SC.warn("ข้อมูลไม่ถูกต้อง กรุณาตรวจสอบข้อมูลใหม่อีกครั้ง");
		            		paid_payment.clearValue();
		            		return;
		            	}
		            	
		            	SC.confirm("ยืนยันการบันทึกจ่ายชำระเงิน", "ต้องการบันทึกจ่ายชำระเงินหรือไม่?" , new BooleanCallback() {
							@Override
							public void execute(Boolean value) {
								if (value) {
					            		record.setAttribute("payment_status", "2_paid");
										record.setAttribute("paidInclusive", paid_payment.getValueAsDouble());
					            		record.setAttribute("paid_date", new Date());
					            		record.setAttribute("paid_by", currentUser.getFirstName() + " " + currentUser.getLastName());
					            		PurchaseOrderDS.getInstance().updateData(record, new DSCallback() {
											@Override
											public void execute(DSResponse dsResponse, Object data,
													DSRequest dsRequest) {
													if (dsResponse.getStatus() != 0) {
														SC.warn("การบันทึกจ่ายชำระเงิน ล้มเหลว");
													} else { 
														
														updateDisburseMaterialReport(order_id);
														
														SC.say("การบันทึกจ่ายชำระเงิน เสร็จสมบูรณ์");
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
		
		if (record.getAttributeAsString("payment_status").equalsIgnoreCase("2_paid")) paidButton.disable();
		
		if (page == 1) controls.addMember(printButton);
		if (page == 2) controls.addMember(paidButton);
		controls.addMember(closeButton);
		layout.addMember(controls);
        
		return layout;
	}
	
	void updateDisburseMaterialReport(String order_id) {
		OrderMaterialDS.getInstance(order_id).refreshData();
		Record[] records = OrderMaterialDS.getInstance(order_id).getCacheData();
		//System.out.println("Found " + records.length + " records");
		for (Record record : records) {
			record.setAttribute("paid_date", new Date());
			//System.out.println("Add record to MaterialReceivedReportDS -- " + record.getAttribute("order_id") + " - mid " + record.getAttribute("mid"));
			DisburseMaterialDS.getInstance().addData(record);
		}
	}
}
