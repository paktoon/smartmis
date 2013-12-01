package com.smart.mis.client.function.inventory.product.request;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.smart.mis.client.function.inventory.product.transfer.TransferDS;
import com.smart.mis.client.function.production.plan.PlanDS;
import com.smart.mis.client.function.production.product.ProductDS;
import com.smart.mis.client.function.sale.customer.CustomerDS;
import com.smart.mis.client.function.sale.delivery.DeliveryDS;
import com.smart.mis.client.function.sale.delivery.DeliveryData;
import com.smart.mis.client.function.sale.delivery.DeliveryItemDS;
import com.smart.mis.client.function.sale.order.SaleOrderDS;
import com.smart.mis.client.function.sale.order.SaleOrderData;
import com.smart.mis.shared.Country;
import com.smart.mis.shared.EditorWindow;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.ListGridNumberField;
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

	Double total_issued_weight;
	Integer total_issued_amount;
	Window editWindow;
	
	public RequestViewWindow(){
		
	}
	
	public void show(ListGridRecord record, boolean edit, User currentUser, int page){
		editWindow = new Window();
		editWindow.setTitle("ข้อมูลรายการนำส่งสินค้า");
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
		
		final String delivery_id = record.getAttributeAsString("delivery_id");
		String cid = record.getAttributeAsString("cid");
		
		String fullAddress = getCustomerFullAddress(cid);
		
		final String sale_id = record.getAttributeAsString("sale_id");
		Date delivery = record.getAttributeAsDate("delivery");
		
		String status = record.getAttributeAsString("status");
		String issued_status = record.getAttributeAsString("issued_status");
		
		String created_by = record.getAttributeAsString("created_by");
		Date created_date = record.getAttributeAsDate("created_date");
		String issued_by = record.getAttributeAsString("issued_by");
		Date issued_date = record.getAttributeAsDate("issued_date");
		
		Double req_weight = record.getAttributeAsDouble("total_weight");
		Integer req_amount = record.getAttributeAsInt("total_amount");
		
		Double issued_weight = record.getAttributeAsDouble("total_issued_weight");
		Integer issued_amount = record.getAttributeAsInt("total_issued_amount");
		
		DynamicForm quotationForm = new DynamicForm();
		quotationForm.setWidth100(); 
		quotationForm.setHeight(30);
		quotationForm.setMargin(5);
		quotationForm.setIsGroup(true);
		quotationForm.setNumCols(6);
		quotationForm.setGroupTitle("ข้อมูลรายการนำส่งสินค้า");

		StaticTextItem did = new StaticTextItem("delivery_id", "รหัสรายการนำส่งสินค้า");
		StaticTextItem sid = new StaticTextItem("sale_id", "รหัสรายการขาย");
		StaticTextItem sts = new StaticTextItem("status", "สถานะการนำส่งสินค้า");
		StaticTextItem issued_sts = new StaticTextItem("issued_status", "สถานะการขอเบิกสินค้า");
		
		StaticTextItem delivery_date = new StaticTextItem("delivery", "กำหนดส่งสินค้า");
		//StaticTextItem cdate = new StaticTextItem("created_date", "ขอเบิกเมื่อ");
		//StaticTextItem cby = new StaticTextItem("created_by", "ขอเบิกโดย");
		did.setValue(delivery_id);
		sid.setValue(sale_id);
		sts.setValue(status);
		sts.setValueMap(DeliveryStatus.getValueMap());
		issued_sts.setValue(issued_status);
		issued_sts.setValueMap(DeliveryStatus.getIssueValueMap());
		delivery_date.setValue(DateTimeFormat.getFormat("MM/dd/yyy").format(delivery));
		//cdate.setValue(DateTimeFormat.getFormat("MM/dd/yyy").format(created_date));
		//cby.setValue(created_by);
		
		quotationForm.setFields(did, sid, delivery_date, sts, issued_sts);
		
		quotationForm.setColWidths(150,100,150,100,100,100);
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
			final StaticTextItem cus_type = new StaticTextItem("cus_type", "ประเภทลูกค้า");
			final StaticTextItem cus_address = new StaticTextItem("fullAddress", "ที่อยู่");
			cus_address.setColSpan(4);
			cus_address.setDefaultValue(fullAddress);
			customerForm.setFields(cus_id,cus_name, cus_type , cus_address);
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
		//else 
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
		
		DeliveryItemDS tempView = new DeliveryItemDS(delivery_id);
		Record[] cachedData = DeliveryItemDS.getInstance(delivery_id).getCacheData();
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
        
        ListGridField quoteItemCell_5 = new ListGridField("sale_weight", "น้ำหนักที่ขอเบิก (กรัม)", 140);
        quoteItemCell_5.setShowGridSummary(false);
        quoteItemCell_5.setCellFormatter(FieldFormatter.getNumberFormat());
        quoteItemCell_5.setAlign(Alignment.RIGHT);
        quoteItemCell_5.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_5.setShowGridSummary(true);
        
        ListGridNumberField quoteItemCell_6 = new ListGridNumberField("sale_amount", 110);
        quoteItemCell_6.setTitle("จำนวนที่ขอเบิก");
        quoteItemCell_6.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_6.setShowGridSummary(true);
        
        ListGridField quoteItemCell_7 = new ListGridField("issued_weight", "น้ำหนักที่จ่าย (กรัม)", 120);
        quoteItemCell_7.setShowGridSummary(false);
        if (edit) quoteItemCell_7.setCanEdit(true);
        quoteItemCell_7.setCellFormatter(FieldFormatter.getNumberFormat());
        quoteItemCell_7.setAlign(Alignment.RIGHT);
        quoteItemCell_7.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_7.setShowGridSummary(true);
        
        ListGridNumberField quoteItemCell_8 = new ListGridNumberField("issued_amount", 110);
        quoteItemCell_8.setTitle("จำนวนที่จ่าย");
        if (edit) quoteItemCell_8.setCanEdit(true);
        quoteItemCell_8.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_8.setShowGridSummary(true);
 
        saleListGrid.setFields(quoteItemCell_1, quoteItemCell_2, quoteItemCell_5, quoteItemCell_6, quoteItemCell_3, quoteItemCell_7, quoteItemCell_8, quoteItemCell_3);
        section.setItems(saleListGrid);
        sectionStack.setSections(section);
		layout.addMember(sectionStack);
		
		HLayout footerLayout = new HLayout();
		footerLayout.setHeight(100);
		
		final DynamicForm endForm = new DynamicForm();
		endForm.setWidth(400);
		endForm.setNumCols(4);
		endForm.setMargin(5);
		endForm.setIsGroup(true);
		//dateForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
		endForm.setGroupTitle("ข้อมูลการรับโอนสินค้า");
		
		StaticTextItem tby = new StaticTextItem("created_by", "ขอเบิกโดย");
		StaticTextItem tdate = new StaticTextItem("created_date", "ขอเบิกเมื่อ");
		tby.setValue(created_by);
		tdate.setValue(DateTimeFormat.getFormat("MM/dd/yyy").format(created_date));
		
		StaticTextItem rby = new StaticTextItem("issued_by", "จ่ายสินค้าโดย");
		StaticTextItem rdate = new StaticTextItem("issued_date", "จ่ายสินค้าเมื่อ");
		if (issued_by != null) {
			rby.setValue(issued_by);
		} else {
			rby.setValue(currentUser.getFirstName() + " " + currentUser.getLastName());
		}
		
		if (issued_date != null) {
			rdate.setValue(DateTimeFormat.getFormat("MM/dd/yyy").format(issued_date));
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
		summaryForm_1.setGroupTitle("สรุปยอดขอเบิกสินค้า");
		summaryForm_1.setColWidths(120, 80);
		final NumberFormat nf = NumberFormat.getFormat("#,##0.00");
		final StaticTextItem total_sent_weight = new StaticTextItem("total_weight");
		total_sent_weight.setValue(nf.format(req_weight));
		final StaticTextItem total_sent_amount = new StaticTextItem("total_amount");
		total_sent_amount.setValue(nf.format(req_amount));
		total_sent_weight.setWidth(100);
		total_sent_amount.setWidth(100);
		total_sent_weight.setTitle("น้ำหนักรวม");
		total_sent_amount.setTitle("จำนวนรวม");
		total_sent_weight.setTextAlign(Alignment.RIGHT);
		total_sent_amount.setTextAlign(Alignment.RIGHT);
		total_sent_weight.setHint("กรัม");
		total_sent_amount.setHint("ชิ้น");
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
		final StaticTextItem total_recv_weight = new StaticTextItem("total_issued_weight");
		if (issued_weight == null) {
			total_recv_weight.setDefaultValue(nf.format(0));
		} else {
			total_recv_weight.setDefaultValue(nf.format(issued_weight));
		}
		final StaticTextItem total_recv_amount = new StaticTextItem("total_issued_amount");
		if (issued_amount == null) {
			total_recv_amount.setDefaultValue(nf.format(0));
		} else {
			total_recv_amount.setDefaultValue(nf.format(issued_amount));
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
		
		saleListGrid.addCellSavedHandler(new CellSavedHandler() {  
			@Override
			public void onCellSaved(CellSavedEvent event) {
				summaryRecalculate(saleListGrid.getRecords(), summaryForm_2);
			}  
        });
		
		layout.addMember(footerLayout);
		
		//Control
		HLayout controls = new HLayout();
		controls.setAlign(Alignment.CENTER);
		controls.setMargin(5);
		controls.setMembersMargin(5);
		final IButton printButton = new IButton("พิมพ์รายการเบิกสินค้า");
		printButton.setIcon("icons/16/print.png");
		printButton.setWidth(150);
		printButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
                SC.say("click print");
            	//Canvas.showPrintPreview(PrintQuotation.getPrintContainer(record));
          }
        });
		
		final IButton issueButton = new IButton("สั่งจ่ายสินค้า");
		issueButton.setIcon("icons/16/save.png");
		issueButton.setWidth(150);
		issueButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	SC.confirm("ยืนยันการทำรายการ", "ต้องการสั่งจ่ายสินค้า หรือไม่?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
							//update stock - clear reserved, clear inStock
							//update delivery order status
							//update sale order status
							updateIssued(delivery_id, record, saleListGrid, currentUser);
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
		
		controls.addMember(printButton);
		if (page == 2 && issued_status.equals("0_product_request")) controls.addMember(issueButton);
		controls.addMember(closeButton);
		layout.addMember(controls);
		
		return layout;
	}
	
//	public void updateDeliveryOrder(String delivery_id, String status, String receipt_id){
//		ListGridRecord updated = DeliveryData.createStatusRecord(delivery_id, "2_delivery_completed", new Date(),receipt_id);
//		DeliveryDS.getInstance().updateData(updated, new DSCallback() {
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
		total_issued_weight = 0.0;
		total_issued_amount = 0;
		
		for (ListGridRecord record : all) {
			String temp_weight = record.getAttribute("issued_weight");
			String temp_amount = record.getAttribute("issued_amount");
			
			Double issued_weight = 0.0;
			Integer issued_amount = 0;
			
			try {
				if (temp_weight == null) {
					issued_weight = 0.0;
				} else {
					issued_weight = Double.parseDouble(temp_weight);
				}
				if (temp_amount == null) {
					issued_amount = 0;
				} else {
					issued_amount = Integer.parseInt(temp_amount);
				}
			} catch (Exception e) {
				SC.warn("กรุณากรอกข้อมูลตัวเลข");
				return;
			}
			
			total_issued_weight += issued_weight;
			total_issued_amount += issued_amount;
		}
		NumberFormat nf = NumberFormat.getFormat("#,##0.00");
		target.getField("total_issued_weight").setValue(nf.format(total_issued_weight));
		target.getField("total_issued_amount").setValue(nf.format(total_issued_amount));
	}
	
	public void updateIssued(final String delivery_id , final ListGridRecord record, ListGrid orderListGrid, User currentUser){
		final ListGridRecord[] all = orderListGrid.getRecords();
		
		for (ListGridRecord item : all){
			if (item.getAttribute("issued_weight") == null || item.getAttribute("issued_amount") == null) {
				SC.warn("กรุณากรอกข้อมูลรับสินค้าให้ครบถ้วน");
				return;
			}
		}
			final String sale_id = record.getAttributeAsString("sale_id");
		
			final String delivery_status = "1_on_delivery";
			final String issued_status = "1_product_issued";
			final String user = currentUser.getFirstName() + " " + currentUser.getLastName();
			
			record.setAttribute("issued_status", issued_status);
			record.setAttribute("status", delivery_status);
			record.setAttribute("total_issued_weight", total_issued_weight);
			record.setAttribute("total_issued_amount", total_issued_amount);
			record.setAttribute("modified_date", new Date());
			record.setAttribute("modified_by", user);
			record.setAttribute("issued_date", new Date());
			record.setAttribute("issued_by", user);
			
			//final String wage_id = createWagePayment(record, user);
			
			DeliveryDS.getInstance().updateData(record, new DSCallback() {
				@Override
				public void execute(DSResponse dsResponse, Object data,
						DSRequest dsRequest) {
						//System.out.println("Test " + dsResponse.getStatus());
						if (dsResponse.getStatus() != 0) {
							SC.warn("การบันทึกสินค้าล้มเหลว กรุณาทำรายการใหม่อีกครั้ง");
							editWindow.destroy();
						} else { 
							
							updateSale(sale_id);
							
							for (ListGridRecord item : all) {
								updateStock(item);
							}
							
							SC.say("บันทึกรับโอนสินค้าเสร็จสิ้น");
							editWindow.destroy();
						}
				}
			});
	}
	
	void updateSale(String sale_id) {
		ListGridRecord saleRecord = SaleOrderData.createStatusRecord(sale_id, "4_on_delivery");
		SaleOrderDS.getInstance().updateData(saleRecord);
	}
	
	void updateStock(ListGridRecord record) {
		String pid = record.getAttributeAsString("pid");
		Integer issued_amount = record.getAttributeAsInt("issued_amount");
		
		ProductDS.getInstance().fetchData();
		Record[] updated_records = ProductDS.getInstance().applyFilter(ProductDS.getInstance().getCacheData(), new Criterion("pid", OperatorId.EQUALS, pid));
		Record updated = updated_records[0];
		Integer inStock = updated.getAttributeAsInt("inStock") - issued_amount;
		updated.setAttribute("inStock", inStock);
		Integer reserved = updated.getAttributeAsInt("reserved") - issued_amount;
		updated.setAttribute("reserved", reserved);
		ProductDS.getInstance().updateData(updated);
	}
	
	String getCustomerFullAddress(String cid) {
		CustomerDS.getInstance().fetchData();
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
