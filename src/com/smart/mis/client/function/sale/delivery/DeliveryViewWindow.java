package com.smart.mis.client.function.sale.delivery;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.smart.mis.client.function.sale.customer.CustomerDS;
import com.smart.mis.client.function.sale.delivery.DeliveryDS;
import com.smart.mis.client.function.sale.delivery.DeliveryData;
import com.smart.mis.client.function.sale.order.SaleOrderDS;
import com.smart.mis.shared.Country;
import com.smart.mis.shared.EditorWindow;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.ListGridNumberField;
import com.smart.mis.shared.PrintHeader;
import com.smart.mis.shared.PrintSign;
import com.smart.mis.shared.Printing;
import com.smart.mis.shared.sale.Customer;
import com.smart.mis.shared.sale.DeliveryStatus;
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

public class DeliveryViewWindow extends EditorWindow{

	Customer client;
	
	public DeliveryViewWindow(){
		
	}
	
	public void show(ListGridRecord record, boolean edit, User currentUser, int page){
		client = new Customer();
		Window editWindow = new Window();
		editWindow.setTitle("ข้อมูลรายการนำส่งสินค้า");
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
		final PrintHeader header = new PrintHeader("ใบนำส่งสินค้า");
		
		layout.setWidth(650);
		layout.setHeight(600);
		layout.setMargin(10);
		layout.addMember(header);
		header.hide();
		
		final String delivery_id = record.getAttributeAsString("delivery_id");
		String cid = record.getAttributeAsString("cid");
		
		String fullAddress = getCustomerFullAddress(cid);
		
		final String sale_id = record.getAttributeAsString("sale_id");
		//final String quote_id = record.getAttributeAsString("quote_id");
		Date delivery = record.getAttributeAsDate("delivery");
		//Double netEx = record.getAttributeAsDouble("netExclusive");
		
		String status = record.getAttributeAsString("status");
		
		Double total_weight = record.getAttributeAsDouble("total_weight");
		Integer total_amount = record.getAttributeAsInt("total_amount");
		
		String created_by = record.getAttributeAsString("created_by");
		//String issued_by = record.getAttributeAsString("issued_by");
		//if (issued_by == null) issued_by = "รอเบิกสินค้า";
		Date issued_date = record.getAttributeAsDate("issued_date");
		
		String[][] signItem = new String[][] {
				{"ผู้ส่งมอบสินค้า",created_by,"พนักงานขาย"},
				{"ผู้รับมอบสินค้า","......................................................",""}
		};
		final PrintSign sign = new PrintSign(signItem);
		
		DynamicForm quotationForm = new DynamicForm();
		quotationForm.setWidth100(); 
		quotationForm.setHeight(30);
		quotationForm.setMargin(5);
		quotationForm.setIsGroup(true);
		quotationForm.setNumCols(8);
		quotationForm.setGroupTitle("ข้อมูลรายการนำส่งสินค้า");

		StaticTextItem did = new StaticTextItem("delivery_id", "รหัสรายการนำส่งสินค้า");
		StaticTextItem sid = new StaticTextItem("sale_id", "รหัสรายการขาย");
		//StaticTextItem qid = new StaticTextItem("quote_id", "รหัสใบเสนอราคา");
		StaticTextItem sts = new StaticTextItem("status", "สถานะ");
		//StaticTextItem issued_sts = new StaticTextItem("issued_status", "สถานะ");
		StaticTextItem delivery_date = new StaticTextItem("delivery", "กำหนดส่งสินค้า");
		//StaticTextItem cdate = new StaticTextItem("created_date", "สร้างเมื่อ");
		did.setValue(delivery_id);
		sid.setValue(sale_id);
		sts.setValue(status);
		sts.setValueMap(DeliveryStatus.getValueMap());
		//issued_sts.setValue(issued_status);
		//issued_sts.setValueMap(DeliveryStatus.getIssueValueMap());
		delivery_date.setValue(DateTimeFormat.getFormat("MM/dd/yyy").format(delivery));
		
		//if (page == 2) quotationForm.setFields(did, sid, issued_sts, delivery_date);
		//else 
		quotationForm.setFields(did, sid, sts, delivery_date);
		
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
			customerForm.setFields(cus_id,cus_type, cus_name, cus_address);
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
		
		//HLayout headerLayout = new HLayout();
		//headerLayout.setWidth100();
		//headerLayout.addMembers(customerForm, commentForm);
		//headerLayout.addMembers(customerForm);
		//layout.addMember(headerLayout);
		layout.addMember(customerForm);
		
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
		saleListGrid.setListEndEditAction(RowEndEditAction.NONE);
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
        
        ListGridField quoteItemCell_5 = new ListGridField("sale_weight", 90);
        quoteItemCell_5.setShowGridSummary(false);
        quoteItemCell_5.setCellFormatter(FieldFormatter.getNumberFormat());
        quoteItemCell_5.setAlign(Alignment.RIGHT);
        quoteItemCell_5.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_5.setShowGridSummary(true);
        
        ListGridNumberField quoteItemCell_6 = new ListGridNumberField("sale_amount", 70);
        quoteItemCell_6.setCellFormatter(FieldFormatter.getIntegerFormat());
        if (edit) quoteItemCell_6.setCanEdit(true);
        quoteItemCell_6.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_6.setShowGridSummary(true);
        
//        ListGridSummaryField quoteItemCell_sum = new ListGridSummaryField("sum_price", 100);
//        quoteItemCell_sum.setRecordSummaryFunction(RecordSummaryFunctionType.MULTIPLIER);
//        quoteItemCell_sum.setSummaryFunction(SummaryFunctionType.SUM);
//        quoteItemCell_sum.setShowGridSummary(true);
//        quoteItemCell_sum.setCellFormatter(FieldFormatter.getPriceFormat());
//        quoteItemCell_sum.setAlign(Alignment.RIGHT);
 
        saleListGrid.setFields(quoteItemCell_1, quoteItemCell_2, quoteItemCell_6, quoteItemCell_3, quoteItemCell_5);
        //itemLayout.addMember(saleListGrid);
        section.setItems(saleListGrid);
        sectionStack.setSections(section);
		layout.addMember(sectionStack);
		
		HLayout footerLayout = new HLayout();
		footerLayout.setHeight(100);
//		final Label empty = Printing.empty();
//		empty.hide();
//		footerLayout.addMember(empty);
		
		final DynamicForm dateForm = new DynamicForm();
		dateForm.setWidth("40%");
		dateForm.setNumCols(2);
		dateForm.setMargin(5);
		dateForm.setIsGroup(true);
		//dateForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
		dateForm.setGroupTitle("รายละเอียดการนำส่งสินค้า");
		dateForm.setCanEdit(false);
		//dateForm.setPrintChildrenAbsolutelyPositioned(true);
		final DateItem issuedDate = new DateItem();
		issuedDate.setName("issueDate");
		issuedDate.setTitle("วันที่เบิกสินค้า");
		issuedDate.setUseTextField(true);
		if (issued_date != null) issuedDate.setValue(issued_date);
		final StaticTextItem deliverBy = new StaticTextItem();
		deliverBy.setTitle("ผู้นำส่งสินค้า");
		if (issued_date != null) deliverBy.setValue(currentUser.getFirstName() + " " + currentUser.getLastName());
		else deliverBy.setValue("-");
		
		dateForm.setFields(issuedDate, deliverBy);
		dateForm.setColWidths(130,150);
		footerLayout.addMember(dateForm);
//		//******************End
//		
		//******************Summary
		final DynamicForm summaryForm = new DynamicForm();
		summaryForm.setWidth("40%");
		summaryForm.setNumCols(2);
		summaryForm.setMargin(5);
		summaryForm.setIsGroup(true);
		summaryForm.setGroupTitle("สรุปยอดนำส่งสินค้า");
		summaryForm.setColWidths(120, 100);
		//summaryForm.setPrintChildrenAbsolutelyPositioned(true);
		NumberFormat nf = NumberFormat.getFormat("#,##0.00");
		NumberFormat ef = NumberFormat.getFormat("#,##0");
		StaticTextItem tweight = new StaticTextItem("total_weight");
		tweight.setValue(nf.format(total_weight));
		StaticTextItem tamount = new StaticTextItem("total_amount");
		tamount.setValue(ef.format(total_amount));
		tweight.setWidth(100);
		tamount.setWidth(100);
		tweight.setTitle("น้ำหนักรวม");
		tamount.setTitle("จำนวนรวม");
		tweight.setTextAlign(Alignment.RIGHT);
		tamount.setTextAlign(Alignment.RIGHT);
		tweight.setHint("กรัม");
		tamount.setHint("ชิ้น");
		summaryForm.setFields(tweight, tamount);
		footerLayout.addMember(summaryForm);
		layout.addMember(footerLayout);
		
		//Control
		HLayout controls = new HLayout();
		controls.setAlign(Alignment.CENTER);
		controls.setMargin(5);
		controls.setMembersMargin(5);
		final IButton printButton = new IButton("พิมพ์รายการนำส่งสินค้า");
		printButton.setIcon("icons/16/print.png");
		printButton.setWidth(150);
		printButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
                //SC.say("click print");
            	//Canvas.showPrintPreview(PrintQuotation.getPrintContainer(record));
//                VLayout printLayout = new VLayout(10);
//                printLayout.setWidth100();
//            	printLayout.addMember(new PrintHeader("ใบนำส่งสินค้า"));
//            	printLayout.addMember(layout);
//            	Canvas.showPrintPreview(printLayout);
//            	main.destroy();
            	
            	VLayout printLayout = new VLayout(10);
            	//printLayout.addMember(new PrintHeader("ใบนำส่งสินค้า"));
            	header.show();
            	sign.show();
            	printLayout.setPrintChildrenAbsolutelyPositioned(true);
            	//empty.show();
            	printLayout.addMember(layout);
            	//System.out.println(printLayout.getPrintHTML(null, null));
            	Canvas.showPrintPreview(printLayout);
            	main.destroy();
          }
        });
		
		final IButton saveButton = new IButton("บันทึกการนำส่งสินค้า");
		saveButton.setIcon("icons/16/save.png");
		saveButton.setWidth(150);
		saveButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	SC.confirm("ยืนยันการทำรายการ", "ต้องการบันทึกการนำส่งสินค้า หรือไม่?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
							SC.askforValue("กรุณาระบุเลขที่ใบรับสินค้า", new ValueCallback() {
								@Override
								public void execute(String value) {
									if (value == null || value.equals("")){
										SC.warn("กรุณาระบุเลขที่ใบรับสินค้า");
									} else {
										updateDeliveryOrder(delivery_id, "2_delivery_completed", value, sale_id);
										main.destroy();
									}
								}});
						}
					}
            	});
          
          }
        });
		
//		final IButton issueButton = new IButton("สั่งจ่ายสินค้า");
//		issueButton.setIcon("icons/16/save.png");
//		issueButton.setWidth(150);
//		issueButton.addClickHandler(new ClickHandler() {  
//            public void onClick(ClickEvent event) { 
//            	SC.confirm("ยืนยันการทำรายการ", "ต้องการสั่งจ่ายสินค้า หรือไม่?" , new BooleanCallback() {
//					@Override
//					public void execute(Boolean value) {
//						if (value) {
//							//update stock - clear reserved, clear inStock
//							//update delivery order status
//							//update sale order status
//						}
//					}
//            	});
//          
//          }
//        });
		
		final IButton closeButton = new IButton("ปิด");
		closeButton.setIcon("icons/16/close.png");
		closeButton.setWidth(120);
		closeButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	main.destroy();
          }
        });
		
		controls.addMember(printButton);
		if (page == 1 && status.equals("1_on_delivery")) controls.addMember(saveButton);
		//if (page == 2 && issued_status.equals("0_product_request")) controls.addMember(issueButton);
		controls.addMember(closeButton);
		layout.addMember(controls);
		
		layout.addMember(sign);
		if (sign.isVisible()) sign.hide();
		
		return layout;
	}
	
	public void updateDeliveryOrder(String delivery_id, String status, String receipt_id, final String sale_id){
		ListGridRecord updated = DeliveryData.createStatusRecord(delivery_id, "2_delivery_completed", new Date(),receipt_id);
		DeliveryDS.getInstance().updateData(updated, new DSCallback() {
			@Override
			public void execute(DSResponse dsResponse, Object data,
					DSRequest dsRequest) {
					if (dsResponse.getStatus() != 0) {
						SC.warn("การบันทึกการนำส่งสินค้าล้มเหลว กรุณาทำรายการใหม่อีกครั้ง");
						return;
					} else {
						ListGridRecord selectedSaleOrder = new ListGridRecord();
						final String sale_status = "5_delivery_completed";
						selectedSaleOrder.setAttribute("sale_id", sale_id);
						selectedSaleOrder.setAttribute("status", sale_status); 
						SaleOrderDS.getInstance().updateData(selectedSaleOrder, new DSCallback() {
							@Override
							public void execute(DSResponse dsResponse,
									Object data, DSRequest dsRequest) {
									SaleOrderDS.getInstance().refreshData();
									SC.say("การบันทึกการนำส่งสินค้าเสร็จสิ้น <br> บันทึกเมื่อวันที่ " + DateUtil.formatAsShortDate(new Date()) + "<br><br> รายการขายรหัส " + sale_id + " สถานะเป็น " + SaleOrderStatus.getDisplay(sale_status));
							}
						});
					}
			}
		});
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
