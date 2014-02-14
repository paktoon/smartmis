package com.smart.mis.client.function.inventory.material.received;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.smart.mis.client.function.inventory.material.requisition.MaterialRequestItemDS;
import com.smart.mis.client.function.inventory.product.transfer.TransferDS;
import com.smart.mis.client.function.production.plan.PlanDS;
import com.smart.mis.client.function.purchasing.material.MaterialDS;
import com.smart.mis.client.function.purchasing.order.PurchaseOrderDS;
import com.smart.mis.client.function.purchasing.order.material.OrderMaterialDS;
import com.smart.mis.client.function.purchasing.supplier.SupplierDS;
import com.smart.mis.client.function.report.inventory.MaterialReceivedReportDS;
import com.smart.mis.client.function.report.inventory.MaterialRequestReportDS;
import com.smart.mis.client.function.sale.customer.CustomerDS;
import com.smart.mis.client.function.sale.delivery.DeliveryData;
import com.smart.mis.client.function.sale.delivery.DeliveryItemDS;
import com.smart.mis.client.function.sale.order.SaleOrderDS;
import com.smart.mis.client.function.sale.order.SaleOrderData;
import com.smart.mis.shared.Country;
import com.smart.mis.shared.EditorWindow;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.ListGridNumberField;
import com.smart.mis.shared.purchasing.PurchaseOrderStatus;
import com.smart.mis.shared.sale.Customer;
import com.smart.mis.shared.sale.DeliveryStatus;
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

public class ReceivedViewWindow extends EditorWindow{

	Double total_received_weight;
	Double total_received_amount;
	Window editWindow;
	
	public ReceivedViewWindow(){
		
	}
	
	public void show(ListGridRecord record, boolean edit, User currentUser, int page){
		editWindow = new Window();
		editWindow.setTitle("ข้อมูลคำสั่งซื้อ");
		editWindow.setWidth(850);  
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
		VLayout layout = new VLayout();
		layout.setWidth(830);
		layout.setHeight(600);
		layout.setMargin(10);
		
		final String order_id = record.getAttributeAsString("order_id");
		String request_id = record.getAttributeAsString("request_id");
		String status = record.getAttributeAsString("status");
		String payment_status = record.getAttributeAsString("payment_status");
		String received_status = record.getAttributeAsString("received_status");
		Date delivery = record.getAttributeAsDate("delivery");
		
		String supplier_id = record.getAttributeAsString("sid");
		String supplier_name = record.getAttributeAsString("sup_name");
		
		String payment_model = record.getAttributeAsString("payment_model");
		Integer credit = record.getAttributeAsInt("credit");
		//final Double netEx = record.getAttributeAsDouble("netExclusive");

		String created_by = record.getAttributeAsString("created_by");
		Date created_date = record.getAttributeAsDate("created_date");
		String received_by = record.getAttributeAsString("received_by");
		Date received_date = record.getAttributeAsDate("received_date");
		
		Double po_weight = record.getAttributeAsDouble("total_weight");
		Double po_amount = record.getAttributeAsDouble("total_amount");
		Double rc_weight = record.getAttributeAsDouble("total_received_weight");
		Double rc_amount = record.getAttributeAsDouble("total_received_amount");
		
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
		
		SectionStack sectionStack = new SectionStack();
    	sectionStack.setWidth100();
    	sectionStack.setHeight(250);
    	SectionStackSection section = new SectionStackSection("รายการวัตถุดิบ");  
    	section.setCanCollapse(false);  
        section.setExpanded(true);
        
		final ListGrid quoteListGrid = new ListGrid() {
            @Override
        	protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) { 
        		if (getFieldName(colNum).equals("received_weight")) {
        			Double recv_weight = record.getAttributeAsDouble("received_weight");
        			if (recv_weight != null) {
	        			Double weight = record.getAttributeAsDouble("weight");
	        			if (recv_weight > weight * 1.02 || recv_weight < weight * 0.98) {
	        				return "font-weight:bold; color:#d64949;";
	        			} else {
	        				return "font-weight:bold; color:#009900;";
	        			}
        			} else return "font-weight:bold; color:#287fd6;";
        		} else if (getFieldName(colNum).equals("received_amount")) {
        			Double recv_amount = record.getAttributeAsDouble("received_amount");
        			if (recv_amount != null) {
        				Double sent_amount = record.getAttributeAsDouble("request_amount");
	        			//if (recv_amount.intValue() != sent_amount.intValue()) {
        				
        				if (!record.getAttributeAsString("type").equalsIgnoreCase("แร่เงิน") && (recv_amount != Math.floor(recv_amount))) {
        					return "font-weight:bold; color:#d64949;";
        				}
        				
	        			if (recv_amount > sent_amount * 1.02 || recv_amount < sent_amount * 0.98) {
	        				return "font-weight:bold; color:#d64949;";
	        			} else {
	        				return "font-weight:bold; color:#009900;";
	        			}
        			} else return "font-weight:bold; color:#287fd6;";
        		} else {  
                    return super.getCellCSSText(record, rowNum, colNum);  
                } 
        	}
		};
		
		quoteListGrid.setHeight(230);
		quoteListGrid.setAlternateRecordStyles(true);  
		quoteListGrid.setShowAllRecords(true);  
		quoteListGrid.setAutoFetchData(true);  
		if (edit) quoteListGrid.setSelectionType(SelectionStyle.SINGLE);
		else quoteListGrid.setSelectionType(SelectionStyle.NONE);
		quoteListGrid.setCanResizeFields(false);
		quoteListGrid.setShowGridSummary(true);
		quoteListGrid.setEditEvent(ListGridEditEvent.CLICK);  
		quoteListGrid.setListEndEditAction(RowEndEditAction.NONE);
		quoteListGrid.setShowRowNumbers(true);
        final Criterion ci = new Criterion("status", OperatorId.EQUALS, true);
		quoteListGrid.setCriteria(ci);
//		if (edit) {
//			saleListGrid.setCanRemoveRecords(true);
//			saleListGrid.setWarnOnRemoval(true);
//			saleListGrid.setWarnOnRemovalMessage("คุณต้องการลบ รายการสินค้า หรือไม่?");
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
        
        ListGridField quoteItemCell_4 = new ListGridField("weight", "น้ำหนักที่สั่งซื้อ (กรัม)", 130);
        quoteItemCell_4.setShowGridSummary(false);
        quoteItemCell_4.setCellFormatter(FieldFormatter.getNumberFormat());
        quoteItemCell_4.setAlign(Alignment.RIGHT);
        quoteItemCell_4.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_4.setShowGridSummary(true);
        quoteItemCell_4.setIncludeInRecordSummary(false);
        
        ListGridNumberField quoteItemCell_6 = new ListGridNumberField("request_amount", 100);
        quoteItemCell_6.setTitle("จำนวนที่สั่งซื้อ");
        quoteItemCell_6.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_6.setShowGridSummary(true);
        
        ListGridField quoteItemCell_7 = new ListGridField("received_weight", "น้ำหนักที่รับ (กรัม)", 120);
        quoteItemCell_7.setShowGridSummary(false);
        if (edit) quoteItemCell_7.setCanEdit(true);
        quoteItemCell_7.setCellFormatter(FieldFormatter.getNumberFormat());
        quoteItemCell_7.setAlign(Alignment.RIGHT);
        quoteItemCell_7.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_7.setShowGridSummary(true);
        if (edit) quoteItemCell_7.setEmptyCellValue("--โปรดระบุน้ำหนัก--");
        else quoteItemCell_7.setEmptyCellValue("ยังไม่มีการรับวัตถุดิบ");
        
        ListGridNumberField quoteItemCell_8 = new ListGridNumberField("received_amount", 100);
        quoteItemCell_8.setTitle("จำนวนที่รับ");
        if (edit) quoteItemCell_8.setCanEdit(true);
        quoteItemCell_8.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_8.setShowGridSummary(true);
        if (edit) quoteItemCell_8.setEmptyCellValue("--โปรดระบุจำนวน--");
        else quoteItemCell_8.setEmptyCellValue("ยังไม่มีการรับวัตถุดิบ");
 
        quoteListGrid.setFields(quoteItemCell_1, quoteItemCell_2, quoteItemCell_4, quoteItemCell_6, quoteItemCell_3, quoteItemCell_7, quoteItemCell_8 , quoteItemCell_3);
        //itemLayout.addMember(quoteListGrid);
        section.setItems(quoteListGrid);
        sectionStack.setSections(section);
		layout.addMember(sectionStack);
		
		HLayout footerLayout = new HLayout();
		footerLayout.setHeight(100);
		
		final DynamicForm endForm = new DynamicForm();
		endForm.setWidth(380);
		endForm.setHeight(75);
		endForm.setNumCols(4);
		endForm.setMargin(5);
		endForm.setIsGroup(true);
		//dateForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
		endForm.setGroupTitle("ข้อมูลการรับวัตถุดิบ");
		
		StaticTextItem tby = new StaticTextItem("created_by", "สั่งซื้อโดย");
		StaticTextItem tdate = new StaticTextItem("created_date", "สั่งซื้อเมื่อ");
		tby.setValue(created_by);
		tdate.setValue(DateTimeFormat.getFormat("MM/dd/yyy").format(created_date));
		
		StaticTextItem rby = new StaticTextItem("received_by", "รับวัตถุดิบโดย");
		StaticTextItem rdate = new StaticTextItem("received_date", "รับวัตถุดิบเมื่อ");
		if (received_by != null) {
			rby.setValue(received_by);
		} else {
			rby.setValue(currentUser.getFirstName() + " " + currentUser.getLastName());
		}
		
		if (received_date != null) {
			rdate.setValue(DateTimeFormat.getFormat("MM/dd/yyy").format(received_date));
		} else {
			rdate.setValue(DateTimeFormat.getFormat("MM/dd/yyy").format(new Date()));
		}
		
		endForm.setFields(tby, rby, tdate, rdate);
		endForm.setColWidths(100,180,120,180);
		//dateForm.editRecord(record);
		footerLayout.addMember(endForm);
		//******************End
		
		//******************Summary
		final DynamicForm summaryForm_1 = new DynamicForm();
		summaryForm_1.setWidth(200);
		summaryForm_1.setHeight(75);
		summaryForm_1.setNumCols(2);
		summaryForm_1.setMargin(5);
		summaryForm_1.setIsGroup(true);
		summaryForm_1.setGroupTitle("สรุปยอดสั่งซื้อวัตถุดิบ");
		summaryForm_1.setColWidths(120, 80);
		final NumberFormat nf = NumberFormat.getFormat("#,##0.00");
		final StaticTextItem total_sent_weight = new StaticTextItem("total_weight");
		total_sent_weight.setValue(nf.format(po_weight));
		final StaticTextItem total_sent_amount = new StaticTextItem("total_amount");
		total_sent_amount.setValue(nf.format(po_amount));
		total_sent_weight.setWidth(100);
		total_sent_amount.setWidth(100);
		total_sent_weight.setTitle("น้ำหนักรวม");
		total_sent_amount.setTitle("จำนวนรวม");
		total_sent_weight.setTextAlign(Alignment.RIGHT);
		total_sent_amount.setTextAlign(Alignment.RIGHT);
		total_sent_weight.setHint("กรัม");
		total_sent_amount.setHint("หน่วย");
		summaryForm_1.setFields(total_sent_amount, total_sent_weight);
		//summaryForm.editRecord(record);
		footerLayout.addMember(summaryForm_1);
		
		final DynamicForm summaryForm_2 = new DynamicForm();
		summaryForm_2.setWidth(200);
		summaryForm_2.setHeight(75);
		summaryForm_2.setNumCols(2);
		summaryForm_2.setMargin(5);
		summaryForm_2.setIsGroup(true);
		summaryForm_2.setGroupTitle("สรุปยอดจ่ายสินค้า");
		summaryForm_2.setColWidths(80, 120);
		final StaticTextItem total_recv_weight = new StaticTextItem("total_received_weight");
		if (rc_weight == null) {
			total_recv_weight.setDefaultValue(nf.format(0));
		} else {
			total_recv_weight.setDefaultValue(nf.format(rc_weight));
		}
		final StaticTextItem total_recv_amount = new StaticTextItem("total_received_amount");
		if (rc_amount == null) {
			total_recv_amount.setDefaultValue(nf.format(0));
		} else {
			total_recv_amount.setDefaultValue(nf.format(rc_amount));
		}
		
		total_recv_weight.setWidth(100);
		total_recv_amount.setWidth(100);
		total_recv_weight.setTitle("น้ำหนักรวม");
		total_recv_amount.setTitle("จำนวนรวม");
		total_recv_weight.setTextAlign(Alignment.RIGHT);
		total_recv_amount.setTextAlign(Alignment.RIGHT);
		total_recv_weight.setHint("กรัม");
		total_recv_amount.setHint("ชิ้น");
		summaryForm_2.setFields(total_recv_amount, total_recv_weight);
		footerLayout.addMember(summaryForm_2);
		
		quoteListGrid.addCellSavedHandler(new CellSavedHandler() {  
			@Override
			public void onCellSaved(CellSavedEvent event) {
				summaryRecalculate(quoteListGrid.getRecords(), summaryForm_2);
			}  
        });
		
		layout.addMember(footerLayout);
		
		//Control
		HLayout controls = new HLayout();
		controls.setAlign(Alignment.CENTER);
		controls.setMargin(5);
		controls.setMembersMargin(5);
//		final IButton printButton = new IButton("พิมพ์รายการเบิกสินค้า");
//		printButton.setIcon("icons/16/print.png");
//		printButton.setWidth(150);
//		printButton.addClickHandler(new ClickHandler() {  
//            public void onClick(ClickEvent event) { 
//                SC.say("click print");
//            	//Canvas.showPrintPreview(PrintQuotation.getPrintContainer(record));
//          }
//        });
		
		final IButton issueButton = new IButton("บันทึกรับวัตถุดิบ");
		issueButton.setIcon("icons/16/save.png");
		issueButton.setWidth(150);
		issueButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	
            	listGridValidate(quoteListGrid);
            	
            	if (quoteListGrid.hasErrors()){
            		SC.warn("ข้อมูลการรับวัตถุดิบไม่ถูกต้อง กรุณาตรวจสอบอีกครั้ง");
            		return;
            	}
            	
            	SC.confirm("ยืนยันการทำรายการ", "ต้องการบันทึกรับวัตถุดิบ หรือไม่?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
							//update stock - clear reserved, clear inStock
							//update delivery order status
							//update sale order status
							updateReceived(order_id, record, quoteListGrid, currentUser);
						}
					}
            	});
          
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
		
		//controls.addMember(printButton);
		if (page == 2 && received_status.equals("1_waiting_for_received")) controls.addMember(issueButton);
		controls.addMember(closeButton);
		layout.addMember(controls);
		
		return layout;
	}
	
//	public void updateDeliveryOrder(String order_id, String status, String receipt_id){
//		ListGridRecord updated = DeliveryData.createStatusRecord(order_id, "2_delivery_completed", new Date(),receipt_id);
//		PurchaseOrderDS.getInstance().updateData(updated, new DSCallback() {
//			@Override
//			public void execute(DSResponse dsResponse, Object data,
//					DSRequest dsRequest) {
//					if (dsResponse.getStatus() != 0) {
//						SC.warn("การบันทึกการนำส่งสินค้าล้มเหลว กรุณาทำรายการใหม่อีกครั้ง");
//						return;
//					} else {
//						SC.say("การบันทึกการนำส่งสินค้าเสร็จสิ้น <br> บันทึกเมื่อวันที่ " + DateUtil.formatAsShortDate(new Date()));
//					}
//			}
//		});
//	}
	
	public void summaryRecalculate(ListGridRecord[] all, DynamicForm target){
		total_received_weight = 0.0;
		total_received_amount = 0.0;
		
		for (ListGridRecord record : all) {
			String temp_weight = record.getAttribute("received_weight");
			String temp_amount = record.getAttribute("received_amount");
			
			Double received_weight = 0.0;
			Double received_amount = 0.0;
			
			try {
				if (temp_weight == null) {
					received_weight = 0.0;
				} else {
					received_weight = Double.parseDouble(temp_weight);
				}
				if (temp_amount == null) {
					received_amount = 0.0;
				} else {
					received_amount = Double.parseDouble(temp_amount);
				}
			} catch (Exception e) {
				SC.warn("กรุณากรอกข้อมูลตัวเลข");
				return;
			}
			
			total_received_weight += received_weight;
			total_received_amount += received_amount;
		}
		NumberFormat nf = NumberFormat.getFormat("#,##0.00");
		target.getField("total_received_weight").setValue(nf.format(total_received_weight));
		target.getField("total_received_amount").setValue(nf.format(total_received_amount));
	}
	
	public void updateReceived(final String order_id , final ListGridRecord record, ListGrid orderListGrid, User currentUser){
		final ListGridRecord[] all = orderListGrid.getRecords();
		
		for (ListGridRecord item : all){
			if (item.getAttribute("received_weight") == null || item.getAttribute("received_amount") == null) {
				SC.warn("กรุณากรอกข้อมูลรับวัตถุดิบให้ครบถ้วน");
				return;
			}
		}
			//final String sale_id = record.getAttributeAsString("sale_id");
		
			//final String delivery_status = "1_on_delivery";
			//final String issued_status = "1_product_issued";
			final String received_status = "2_received";
			final String order_status = "2_recevied_product";
			final String user = currentUser.getFirstName() + " " + currentUser.getLastName();
			
			record.setAttribute("received_status", received_status);
			record.setAttribute("status", order_status);
			record.setAttribute("total_received_weight", total_received_weight);
			record.setAttribute("total_received_amount", total_received_amount);
			record.setAttribute("modified_date", new Date());
			record.setAttribute("modified_by", user);
			record.setAttribute("received_date", new Date());
			record.setAttribute("received_by", user);
			
			//final String wage_id = createWagePayment(record, user);
			
			PurchaseOrderDS.getInstance().updateData(record, new DSCallback() {
				@Override
				public void execute(DSResponse dsResponse, Object data,
						DSRequest dsRequest) {
						//System.out.println("Test " + dsResponse.getStatus());
						if (dsResponse.getStatus() != 0) {
							SC.warn("การบันทึกสินค้าล้มเหลว กรุณาทำรายการใหม่อีกครั้ง");
							editWindow.destroy();
						} else { 
							
							//updateSale(sale_id);
							updateMaterialReceivedReport(order_id);
							
							for (ListGridRecord item : all) {
								updateStock(item);
							}
							
							PurchaseOrderDS.getInstance().refreshData();
							SC.say("บันทึกรับวัตถุดิบเสร็จสิ้น");
							editWindow.destroy();
						}
				}
			});
	}
	
//	void updateSale(String sale_id) {
//		ListGridRecord saleRecord = SaleOrderData.createStatusRecord(sale_id, "4_on_delivery");
//		SaleOrderDS.getInstance().updateData(saleRecord);
//	}
	
	void updateStock(ListGridRecord record) {
		String mid = record.getAttributeAsString("mid");
		Double received_amount = record.getAttributeAsDouble("received_amount");
		
		MaterialDS.getInstance().refreshData();
		Record[] updated_records = MaterialDS.getInstance().applyFilter(MaterialDS.getInstance().getCacheData(), new Criterion("mid", OperatorId.EQUALS, mid));
		Record updated = updated_records[0];
		Double inStock = updated.getAttributeAsDouble("inStock") + received_amount;
		updated.setAttribute("inStock", inStock);
		Double remain = updated.getAttributeAsDouble("remain") + received_amount;
		updated.setAttribute("remain", remain);
		MaterialDS.getInstance().updateData(updated);
	}
	
	void updateMaterialReceivedReport(String order_id) {
		OrderMaterialDS.getInstance(order_id).refreshData();
		Record[] records = OrderMaterialDS.getInstance(order_id).getCacheData();
		//System.out.println("Found " + records.length + " records");
		for (Record record : records) {
			record.setAttribute("received_date", new Date());
			//System.out.println("Add record to MaterialReceivedReportDS -- " + record.getAttribute("order_id") + " - mid " + record.getAttribute("mid"));
			MaterialReceivedReportDS.getInstance().addData(record);
		}
	}
	
	public void listGridValidate(ListGrid listGrid){
		
		 
//		if (getFieldName(colNum).equals("received_weight")) {
//			Double recv_weight = record.getAttributeAsDouble("received_weight");
//			if (recv_weight != null) {
//    			Double weight = record.getAttributeAsDouble("weight");
//    			if (recv_weight > weight * 1.02 || recv_weight < weight * 0.98) {
//    				return "font-weight:bold; color:#d64949;";
//    			} else {
//    				return "font-weight:bold; color:#009900;";
//    			}
//			} else return "font-weight:bold; color:#287fd6;";
//		} else if (getFieldName(colNum).equals("received_amount")) {
//			Integer recv_amount = record.getAttributeAsInt("received_amount");
//			if (recv_amount != null) {
//				Double sent_amount = record.getAttributeAsDouble("request_amount");
//    			//if (recv_amount.intValue() != sent_amount.intValue()) {
//    			if (recv_amount > sent_amount * 1.02 || recv_amount < sent_amount * 0.98) {
//    				return "font-weight:bold; color:#d64949;";
//    			} else {
//    				return "font-weight:bold; color:#009900;";
//    			}
//			} else return "font-weight:bold; color:#287fd6;";
//		} else {  
//            return super.getCellCSSText(record, rowNum, colNum);  
//        } 
	
		
		int row = 0;
		for (ListGridRecord record : listGrid.getRecords()){
				Double sent_weight = record.getAttributeAsDouble("weight");
				Double sent_amount = record.getAttributeAsDouble("request_amount");
				
				Double recv_weight = record.getAttributeAsDouble("received_weight");
				Double recv_amount = record.getAttributeAsDouble("received_amount");
				
				String mat_type = record.getAttributeAsString("type");
				
				if (!mat_type.equalsIgnoreCase("แร่เงิน") && (recv_amount != Math.floor(recv_amount))) {
					listGrid.setFieldError(row, "received_amount", "ปริมาณไม่เหมาะสมกับชนิดของวัตถุดิบ");
				}
				
				if (recv_weight != null) {
					if (recv_weight > sent_weight * 1.02 || recv_weight < sent_weight * 0.98) {
						listGrid.setFieldError(row, "received_weight", "น้ำหนักวัตถุดิบไม่อยู่ในช่วงที่เหมาะสม");
					}
				} else {
					listGrid.setFieldError(row, "received_weight", "น้ำหนักวัตถุดิบไม่ถูกต้อง");
				}
				
				if (recv_amount != null) {
					//if (recv_amount.intValue() != sent_amount.intValue()) {
					if (recv_amount > sent_amount * 1.02 || recv_amount < sent_amount * 0.98) {
						listGrid.setFieldError(row, "received_amount", "จำนวนวัตถุดิบไม่ถูกต้อง");
					}
				} else {
					listGrid.setFieldError(row, "received_amount", "จำนวนวัตถุดิบไม่ถูกต้อง");
				}
			row++;
		}
	}
}
