package com.smart.mis.client.function.inventory.material.requisition;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.smart.mis.client.function.inventory.product.transfer.TransferDS;
import com.smart.mis.client.function.production.order.abrading.AbradingDS;
import com.smart.mis.client.function.production.order.casting.CastingDS;
import com.smart.mis.client.function.production.order.casting.CastingData;
import com.smart.mis.client.function.production.order.packing.PackingDS;
import com.smart.mis.client.function.production.order.scraping.ScrapingDS;
import com.smart.mis.client.function.production.plan.PlanDS;
import com.smart.mis.client.function.production.smith.SmithDS;
import com.smart.mis.client.function.purchasing.material.MaterialDS;
import com.smart.mis.client.function.purchasing.order.PurchaseOrderDS;
import com.smart.mis.client.function.purchasing.order.material.OrderMaterialDS;
import com.smart.mis.client.function.purchasing.supplier.SupplierDS;
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
import com.smart.mis.shared.inventory.RequisitionStatus;
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

public class RequestViewWindow extends EditorWindow{

	Double total_issued_amount;
	Window editWindow;
	
	public RequestViewWindow(){
		
	}
	
	public void show(ListGridRecord record, boolean edit, User currentUser, int page){
		editWindow = new Window();
		editWindow.setTitle("ข้อมูลคำขอเบิกวัตถุดิบ");
		editWindow.setWidth(750);  
		editWindow.setHeight(520);
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
		layout.setWidth(730);
		layout.setHeight(500);
		layout.setMargin(10);
		
		final String request_id = record.getAttributeAsString("mat_request_id");
		String job_id = record.getAttributeAsString("job_id");
		String status = record.getAttributeAsString("status");
		Date request_date = record.getAttributeAsDate("req_date");
		
		String smith_id = record.getAttributeAsString("smid");
		String smith_name = record.getAttributeAsString("sname");
		
		String created_by = record.getAttributeAsString("created_by");
		Date created_date = record.getAttributeAsDate("created_date");
		String modified_by = record.getAttributeAsString("modified_by");
		Date modified_date = record.getAttributeAsDate("modified_date");
		
		Double request_amount = record.getAttributeAsDouble("total_request_amount");
		Double issued_amount = record.getAttributeAsDouble("total_issued_amount");
		
		DynamicForm quotationForm = new DynamicForm();
		quotationForm.setWidth100(); 
		quotationForm.setHeight(30);
		quotationForm.setMargin(5);
		quotationForm.setIsGroup(true);
		quotationForm.setNumCols(8);
		quotationForm.setGroupTitle("ข้อมูลคำขอเบิกวัตถุดิบ");

		StaticTextItem orid = new StaticTextItem("mat_request_id", "รหัสเบิกวัตถุดิบ");
		StaticTextItem rqid = new StaticTextItem("job_id", "รหัสคำสั่งผลิต");
		StaticTextItem sts = new StaticTextItem("status", "สถานะ");
		StaticTextItem req_date = new StaticTextItem("request_date", "วันที่ขอเบิก");
		StaticTextItem smid = new StaticTextItem("smid", "รหัสช่าง");
		StaticTextItem smname = new StaticTextItem("sname", "ชื่อช่าง");
		orid.setValue(request_id);
		rqid.setValue(job_id);
		sts.setValue(RequisitionStatus.getDisplay(status));
		req_date.setValue(DateTimeFormat.getFormat("MM/dd/yyy").format(request_date));
		smid.setValue(smith_id);
		smname.setValue(smith_name);
		smname.setColSpan(4);
		quotationForm.setFields(orid, rqid, sts, req_date, smid, smname);
		quotationForm.setColWidths(100,80,100,80,100,100,100,100);
		layout.addMember(quotationForm);
		
//		//Prepare supplier data - SupplierDS.getInstance()
//		SmithDS.getInstance().refreshData();
//		Record[] selectedSmith = SmithDS.getInstance().applyFilter(SmithDS.getInstance().getCacheData(), new Criterion("smid", OperatorId.EQUALS, smith_id));
//		
//		String fullAddress = "no data found, please contact admin";
//		String phone1 = "-";
//		String phone2 = "-";
//		String email = "-";
//		String fax = "-";
//		if (selectedSmith.length == 1) {
//			String address = selectedSmith[0].getAttributeAsString("address");
//			String street = selectedSmith[0].getAttributeAsString("street");
//			String city = selectedSmith[0].getAttributeAsString("city");
//			String state = selectedSmith[0].getAttributeAsString("state");
//			String postal = selectedSmith[0].getAttributeAsString("postal");
//			fullAddress = address;
//			if (street !=null) fullAddress += " ถนน " + street;
//			if (city !=null) fullAddress += " เขต " + city;
//			if (state !=null) fullAddress += " จังหวัด " + state;
//			if (postal !=null) fullAddress += " รหัสไปรษณีย์ " + postal;
//			phone1 = selectedSmith[0].getAttributeAsString("sup_phone1");
//			phone2 = selectedSmith[0].getAttributeAsString("sup_phone2");
//			email = selectedSmith[0].getAttributeAsString("email");
//			fax = selectedSmith[0].getAttributeAsString("fax");
//		}
//		
//		final DynamicForm smithForm = new DynamicForm();
//		smithForm.setWidth100(); 
//		smithForm.setMargin(5); 
//		smithForm.setNumCols(6); 
//		smithForm.setCellPadding(2);
//		smithForm.setAutoFocus(true);
//		smithForm.setSelectOnFocus(true);
//		//smithForm.setDataSource(SupplierDS.getInstance());
//		//smithForm.setUseAllDataSourceFields(false);
//		smithForm.setIsGroup(true);
//		smithForm.setGroupTitle("ข้อมูลช่าง");
//		
//		final StaticTextItem sup_id = new StaticTextItem("sid", "รหัสผู้จำหน่าย");
//		sup_id.setDefaultValue(smith_id);
//		final StaticTextItem sup_name = new StaticTextItem("sup_name", "ชื่อผู้จำหน่าย");
//		//sup_name.setColSpan(4);
//		sup_name.setDefaultValue(smith_name);
//		final StaticTextItem sup_email = new StaticTextItem("email", "อีเมล");
//		sup_email.setDefaultValue(email);
//		final StaticTextItem sup_phone1 = new StaticTextItem("sup_phone1", "โทรศัพท์ 1");
//		sup_phone1.setDefaultValue(phone1);
//		final StaticTextItem sup_phone2 = new StaticTextItem("sup_phone2", "โทรศัพท์ 2");
//		sup_phone2.setDefaultValue(phone2);
//		final StaticTextItem sup_fax = new StaticTextItem("fax", "โทรสาร");
//		sup_fax.setDefaultValue(fax);
//		final StaticTextItem sup_address = new StaticTextItem("fullAddress", "ที่อยู่");
//		sup_address.setDefaultValue(fullAddress);
//		sup_address.setColSpan(6);
//		
//		smithForm.setFields(sup_id, sup_name, sup_email, sup_phone1, sup_phone2, sup_fax, sup_address);
//		smithForm.setColWidths(100,100,100,100,100,100);
//		layout.addMember(smithForm);
		
//		final DynamicForm paymentForm = new DynamicForm();
//		paymentForm.setWidth100(); 
//		paymentForm.setMargin(5); 
//		paymentForm.setNumCols(6); 
//		paymentForm.setCellPadding(2);
//		paymentForm.setAutoFocus(true);
//		paymentForm.setSelectOnFocus(true);
//		//smithForm.setDataSource(SupplierDS.getInstance());
//		//smithForm.setUseAllDataSourceFields(false);
//		paymentForm.setIsGroup(true);
//		paymentForm.setGroupTitle("ข้อมูลการชำระเงิน");
//		
//		StaticTextItem psts = new StaticTextItem("payment_status", "สถานะการชำระเงิน");
//		psts.setDefaultValue(PurchaseOrderStatus.getPaymentDisplay(payment_status));
//		StaticTextItem sup_payment_model = new StaticTextItem("payment_model", "วิธีการชำระเงิน");
//		sup_payment_model.setValue(payment_model);
//		StaticTextItem sup_credit = new StaticTextItem("credit", "เครดิต");
//		sup_credit.setDefaultValue(credit);
//		sup_credit.setHint("วัน");
//		paymentForm.setFields(psts, sup_payment_model, sup_credit);
//		paymentForm.setColWidths(100, 100, 100, 100, 100, 100);
//		layout.addMember(paymentForm);
		
		SectionStack sectionStack = new SectionStack();
    	sectionStack.setWidth100();
    	sectionStack.setHeight(250);
    	SectionStackSection section = new SectionStackSection("รายการวัตถุดิบ");  
    	section.setCanCollapse(false);  
        section.setExpanded(true);
        
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
//			saleListGrid.setCanRemoveRecords(true);
//			saleListGrid.setWarnOnRemoval(true);
//			saleListGrid.setWarnOnRemovalMessage("คุณต้องการลบ รายการสินค้า หรือไม่?");
//		}
		
		MaterialRequestItemDS tempView = new MaterialRequestItemDS(request_id);
		Record[] cachedData = MaterialRequestItemDS.getInstance(request_id).getCacheData();
		if (cachedData.length != 0) {
			tempView.setTestData(cachedData);
		}
		quoteListGrid.setDataSource(tempView);
		quoteListGrid.setUseAllDataSourceFields(false);
		
		ListGridField quoteItemCell_1 = new ListGridField("mid", 60);
		quoteItemCell_1.setTitle("รหัสวัตถุดิบ");
        ListGridField quoteItemCell_2 = new ListGridField("mname"); 
        quoteItemCell_2.setTitle("ชื่อวัตถุดิบ");
        quoteItemCell_2.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });  
        quoteItemCell_2.setShowGridSummary(true);
        ListGridField quoteItemCell_3 = new ListGridField("unit", "หน่วย", 40);
        
        ListGridField quoteItemCell_4 = new ListGridField("request_amount", "จำนวนที่ขอเบิก", 120);
        quoteItemCell_4.setShowGridSummary(false);
        quoteItemCell_4.setCellFormatter(FieldFormatter.getNumberFormat());
        quoteItemCell_4.setAlign(Alignment.RIGHT);
        quoteItemCell_4.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_4.setShowGridSummary(true);
        quoteItemCell_4.setIncludeInRecordSummary(false);
        
//        ListGridNumberField quoteItemCell_6 = new ListGridNumberField("request_amount", 70);
//        quoteItemCell_6.setTitle("จำนวนที่สั่งซื้อ");
//        quoteItemCell_6.setSummaryFunction(SummaryFunctionType.SUM);
//        quoteItemCell_6.setShowGridSummary(true);
        
        ListGridField quoteItemCell_7 = new ListGridField("issued_amount", "จำนวนที่จ่าย", 120);
        quoteItemCell_7.setShowGridSummary(false);
        if (edit) quoteItemCell_7.setCanEdit(true);
        quoteItemCell_7.setCellFormatter(FieldFormatter.getNumberFormat());
        quoteItemCell_7.setAlign(Alignment.RIGHT);
        quoteItemCell_7.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_7.setShowGridSummary(true);
        if (edit) quoteItemCell_7.setEmptyCellValue("--โปรดระบุจำนวน--");
        else quoteItemCell_7.setEmptyCellValue("ยังไม่มีการเบิกจ่าย");
        
//        ListGridNumberField quoteItemCell_8 = new ListGridNumberField("received_amount", 110);
//        quoteItemCell_8.setTitle("จำนวนที่รับ");
//        if (edit) quoteItemCell_8.setCanEdit(true);
//        quoteItemCell_8.setSummaryFunction(SummaryFunctionType.SUM);
//        quoteItemCell_8.setShowGridSummary(true);
 
        quoteListGrid.setFields(quoteItemCell_1, quoteItemCell_2, quoteItemCell_4, quoteItemCell_3, quoteItemCell_7 , quoteItemCell_3);
        //itemLayout.addMember(quoteListGrid);
        section.setItems(quoteListGrid);
        sectionStack.setSections(section);
		layout.addMember(sectionStack);
		
		HLayout footerLayout = new HLayout();
		footerLayout.setHeight(100);
		
		final DynamicForm endForm = new DynamicForm();
		endForm.setWidth(380);
		//endForm.setHeight(75);
		endForm.setNumCols(4);
		endForm.setMargin(5);
		endForm.setIsGroup(true);
		//dateForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
		endForm.setGroupTitle("ข้อมูลขอเบิกวัตถุดิบ");
		
		StaticTextItem tby = new StaticTextItem("created_by", "ขอเบิกโดย");
		StaticTextItem tdate = new StaticTextItem("created_date", "ขอเบิกเมื่อ");
		tby.setValue(created_by);
		tdate.setValue(DateTimeFormat.getFormat("MM/dd/yyy").format(created_date));
		
		StaticTextItem rby = new StaticTextItem("received_by", "สั่งจ่ายโดย");
		StaticTextItem rdate = new StaticTextItem("received_date", "สั่งจ่ายเมื่อ");
		if (modified_by != null) {
			rby.setValue(modified_by);
		} else {
			rby.setValue(currentUser.getFirstName() + " " + currentUser.getLastName());
		}
		
		if (modified_date != null) {
			rdate.setValue(DateTimeFormat.getFormat("MM/dd/yyy").format(modified_date));
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
		summaryForm_1.setWidth(250);
		//summaryForm_1.setHeight(75);
		summaryForm_1.setNumCols(2);
		summaryForm_1.setMargin(5);
		summaryForm_1.setIsGroup(true);
		summaryForm_1.setGroupTitle("สรุปยอดสั่งจ่ายวัตถุดิบ");
		//summaryForm_1.setColWidths(120, 80);
		final NumberFormat nf = NumberFormat.getFormat("#,##0.00");
		final StaticTextItem total_request_amount = new StaticTextItem("total_request_amount");
		total_request_amount.setValue(nf.format(request_amount));
		final StaticTextItem total_issued_amount = new StaticTextItem("total_issued_amount");
		if (issued_amount == null) {
			total_issued_amount.setDefaultValue(nf.format(0));
		} else {
			total_issued_amount.setDefaultValue(nf.format(issued_amount));
		}
		total_request_amount.setWidth(100);
		total_issued_amount.setWidth(100);
		total_request_amount.setTitle("จำนวนที่ขอเบิกรวม");
		total_issued_amount.setTitle("จำนวนที่สั่งจ่ายรวม");
		total_request_amount.setTextAlign(Alignment.RIGHT);
		total_issued_amount.setTextAlign(Alignment.RIGHT);
		total_request_amount.setHint("หน่วย");
		total_issued_amount.setHint("หน่วย");
		summaryForm_1.setFields(total_request_amount, total_issued_amount);
		//summaryForm.editRecord(record);
		footerLayout.addMember(summaryForm_1);
		
//		final DynamicForm summaryForm_2 = new DynamicForm();
//		summaryForm_2.setWidth(200);
//		summaryForm_2.setHeight(75);
//		summaryForm_2.setNumCols(2);
//		summaryForm_2.setMargin(5);
//		summaryForm_2.setIsGroup(true);
//		summaryForm_2.setGroupTitle("สรุปยอดจ่ายสินค้า");
//		summaryForm_2.setColWidths(80, 120);
//		final StaticTextItem total_recv_weight = new StaticTextItem("total_received_weight");
//		if (rc_weight == null) {
//			total_recv_weight.setDefaultValue(nf.format(0));
//		} else {
//			total_recv_weight.setDefaultValue(nf.format(rc_weight));
//		}
//		final StaticTextItem total_recv_amount = new StaticTextItem("total_received_amount");
//		if (rc_amount == null) {
//			total_recv_amount.setDefaultValue(nf.format(0));
//		} else {
//			total_recv_amount.setDefaultValue(nf.format(rc_amount));
//		}
//		
//		total_recv_weight.setWidth(100);
//		total_recv_amount.setWidth(100);
//		total_recv_weight.setTitle("น้ำหนักรวม");
//		total_recv_amount.setTitle("จำนวนรวม");
//		total_recv_weight.setTextAlign(Alignment.RIGHT);
//		total_recv_amount.setTextAlign(Alignment.RIGHT);
//		total_recv_weight.setHint("กรัม");
//		total_recv_amount.setHint("ชิ้น");
//		summaryForm_2.setFields(total_recv_amount, total_recv_weight);
//		footerLayout.addMember(summaryForm_2);
		
		quoteListGrid.addCellSavedHandler(new CellSavedHandler() {  
			@Override
			public void onCellSaved(CellSavedEvent event) {
				summaryRecalculate(quoteListGrid.getRecords(), summaryForm_1);
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
		
		final IButton issueButton = new IButton("บันทึกสั่งจ่ายวัตถุดิบ");
		issueButton.setIcon("icons/16/save.png");
		issueButton.setWidth(150);
		issueButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	SC.confirm("ยืนยันการทำรายการ", "ต้องการบันทึกสั่งจ่ายวัตถุดิบ หรือไม่?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
							//update stock - clear reserved, clear inStock
							//update delivery order status
							//update sale order status
							updateRequest(request_id, record, quoteListGrid, currentUser);
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
		if (page == 2 && status.equals("1_requested")) controls.addMember(issueButton);
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
		//total_received_weight = 0.0;
		total_issued_amount = 0.0;
		
		for (ListGridRecord record : all) {
			String temp_amount = record.getAttribute("issued_amount");
			
			Double received_amount = 0.0;
			
			try {
				if (temp_amount == null) {
					received_amount = 0.0;
				} else {
					received_amount = Double.parseDouble(temp_amount);
				}
			} catch (Exception e) {
				SC.warn("กรุณากรอกข้อมูลตัวเลข");
				return;
			}
			
			total_issued_amount += received_amount;
		}
		NumberFormat nf = NumberFormat.getFormat("#,##0.00");
		target.getField("total_issued_amount").setValue(nf.format(total_issued_amount));
	}
	
	public void updateRequest(final String request_id , final ListGridRecord record, ListGrid orderListGrid, User currentUser){
		final ListGridRecord[] all = orderListGrid.getRecords();
		
		for (ListGridRecord item : all){
			if (item.getAttribute("issued_amount") == null) {
				SC.warn("กรุณากรอกข้อมูลรับวัตถุดิบให้ครบถ้วน");
				return;
			}
		}
			final String job_id = record.getAttributeAsString("job_id");
			final String job_type = record.getAttributeAsString("req_type");
			//final String delivery_status = "1_on_delivery";
			//final String issued_status = "1_product_issued";
			final String status = "2_issued";
			final String user = currentUser.getFirstName() + " " + currentUser.getLastName();
			
			record.setAttribute("status", status);
			record.setAttribute("total_issued_amount", total_issued_amount);
			record.setAttribute("modified_date", new Date());
			record.setAttribute("modified_by", user);
			record.setAttribute("issued_date", new Date());
			record.setAttribute("issued_by", user);
			
			//final String wage_id = createWagePayment(record, user);
			
			MaterialRequestDS.getInstance().updateData(record, new DSCallback() {
				@Override
				public void execute(DSResponse dsResponse, Object data,
						DSRequest dsRequest) {
						//System.out.println("Test " + dsResponse.getStatus());
						if (dsResponse.getStatus() != 0) {
							SC.warn("การบันทึกสินค้าล้มเหลว กรุณาทำรายการใหม่อีกครั้ง");
							editWindow.destroy();
						} else { 
							
							updateMaterialRequestReport(request_id);
							updateJob(job_id, job_type);
							
							for (ListGridRecord item : all) {
								updateStock(item);
							}
							
							SC.say("บันทึกจ่ายวัตถุดิบเสร็จสิ้น");
							editWindow.destroy();
						}
				}
			});
	}
	
	void updateJob(String job_id, String job_type) {
		String status = "1_on_production";
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("job_id", job_id);
        record.setAttribute("status", status);
        
//		if (job_type.equalsIgnoreCase("1_casting")) {
//			CastingDS.getInstance().updateData(record);
//		} else 
		if (job_type.equalsIgnoreCase("2_scrape")) {
			ScrapingDS.getInstance().updateData(record);
		} else if (job_type.equalsIgnoreCase("3_abrade")) {
			AbradingDS.getInstance().updateData(record);
		} else if (job_type.equalsIgnoreCase("4_packing")) {
			PackingDS.getInstance().updateData(record);
		} 
//		else {
//			SC.warn("ประเภทงานไม่ถูกต้อง " + job_id + ":" + job_type);
//		}
	}
	
	void updateMaterialRequestReport(String mat_request_id) {
		MaterialRequestItemDS.getInstance(mat_request_id).refreshData();
		Record[] records = MaterialRequestItemDS.getInstance(mat_request_id).getCacheData();
		for (Record record : records) {
			record.setAttribute("issued_date", new Date());
			//System.out.println("Add record to ProductRequestDS --");
			MaterialRequestReportDS.getInstance().addData(record);
		}
	}
	
	void updateStock(ListGridRecord record) {
		String mid = record.getAttributeAsString("mid");
		Double issued_amount = record.getAttributeAsDouble("issued_amount");
		
		MaterialDS.getInstance().refreshData();
		Record[] updated_records = MaterialDS.getInstance().applyFilter(MaterialDS.getInstance().getCacheData(), new Criterion("mid", OperatorId.EQUALS, mid));
		Record updated = updated_records[0];
		Double inStock = updated.getAttributeAsDouble("inStock") - issued_amount;
		updated.setAttribute("inStock", inStock);
		Double reserved = updated.getAttributeAsDouble("reserved") - issued_amount;
		updated.setAttribute("reserved", reserved);
		MaterialDS.getInstance().updateData(updated);
	}
	
//	String getCustomerFullAddress(String cid) {
//		CustomerDS.getInstance().refreshData();
//		Record[] records = CustomerDS.getInstance().applyFilter(CustomerDS.getInstance().getCacheData(), new Criterion("cid", OperatorId.EQUALS, cid));
//		Record selected = records[0];
//		String address = selected.getAttributeAsString("address");
//		String street = selected.getAttributeAsString("street");
//		String city = selected.getAttributeAsString("city");
//		String state = selected.getAttributeAsString("state");
//		String postal = selected.getAttributeAsString("postal");
//		String country = selected.getAttributeAsString("country");
//		
//		if (country.equalsIgnoreCase("TH")) {
//			String fullAddress = address;
//			if (street !=null) fullAddress += " ถนน " + street;
//			if (city !=null) fullAddress += " เขต " + city;
//			if (state !=null) fullAddress += " จังหวัด " + state;
//			if (postal !=null) fullAddress += " รหัสไปรษณีย์ " + postal;
//			if (country !=null) fullAddress += " ประเทศ " + Country.getThaiCountryName(country);
//			return fullAddress;
//		} else {
//			String fullAddress = address;
//			if (street !=null) fullAddress += " ,Street " + street;
//			if (city !=null) fullAddress += " ,City " + city;
//			if (state !=null) fullAddress += " ,State " + state;
//			if (postal !=null) fullAddress += " ,Postal " + postal;
//			if (country !=null) fullAddress += " ,Country " + Country.getEnglishCountryName(country);
//			return fullAddress;
//		}
//	}
}
