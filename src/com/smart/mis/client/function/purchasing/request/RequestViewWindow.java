package com.smart.mis.client.function.purchasing.request;

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
import com.smart.mis.shared.EditorWindow;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.FieldVerifier;
import com.smart.mis.shared.ListGridNumberField;
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

public class RequestViewWindow extends EditorWindow{

	SelectMaterailList addFunc;
	Supplier client;
	String material_list = "NONE";
	
	public RequestViewWindow(){
		addFunc = new SelectMaterailList();
	}
	
	public void show(ListGridRecord record, boolean edit, User currentUser, int page){
		client = new Supplier();
		Window editWindow = new Window();
		editWindow.setTitle("ข้อมูลใบเสนอซื้อ");
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
		VLayout layout = new VLayout();
		layout.setWidth(650);
		layout.setHeight(600);
		layout.setMargin(10);
		
		final String request_id = record.getAttributeAsString("request_id");
		String status = record.getAttributeAsString("status");
		String created_by = record.getAttributeAsString("created_by");
		Date created_date = record.getAttributeAsDate("created_date");
		
		String quote_id = record.getAttributeAsString("quote_id");
		String supplier_id = record.getAttributeAsString("sid");
		String supplier_name = record.getAttributeAsString("sup_name");
		String payment_model = record.getAttributeAsString("payment_model");
		Integer credit = record.getAttributeAsInt("credit");
		Date from = record.getAttributeAsDate("from");
		Date to = record.getAttributeAsDate("to");
		Date delivery = record.getAttributeAsDate("delivery");
		
		Double netEx = record.getAttributeAsDouble("netExclusive");
		String comment = record.getAttributeAsString("comment");
		//System.out.println(payment_model + " " + credit);
		//System.out.println(from + " " + to + " " + delivery + " " + netEx);
		
		DynamicForm quotationForm = new DynamicForm();
		quotationForm.setWidth100(); 
		quotationForm.setHeight(30);
		quotationForm.setMargin(5);
		quotationForm.setIsGroup(true);
		quotationForm.setNumCols(8);
		quotationForm.setGroupTitle("ข้อมูลใบเสนอซื้อ");

		StaticTextItem rqid = new StaticTextItem("request_id", "รหัสใบเสนอซื้อ");
		StaticTextItem sts = new StaticTextItem("status", "สถานะ");
		StaticTextItem cby = new StaticTextItem("created_by", "สร้างโดย");
		StaticTextItem cdate = new StaticTextItem("created_date", "สร้างเมื่อ");
		rqid.setValue(request_id);
		sts.setValue(status);
		sts.setValueMap(PurchaseRequestStatus.getValueMap());
		cby.setValue(created_by);
		cdate.setValue(DateTimeFormat.getFormat("MM/dd/yyy").format(created_date));
		quotationForm.setFields(rqid, sts, cdate ,cby);
		quotationForm.setColWidths(100,70,70,70,70,80,60,100);
		layout.addMember(quotationForm);
		
		final DynamicForm suppilerForm = new DynamicForm();
		suppilerForm.setWidth(360); 
		suppilerForm.setHeight(30);
		suppilerForm.setMargin(5); 
		suppilerForm.setNumCols(4); 
		suppilerForm.setCellPadding(2);
		suppilerForm.setAutoFocus(true);
		suppilerForm.setSelectOnFocus(true);
		//suppilerForm.setDataSource(SupplierDS.getInstance());
		//suppilerForm.setUseAllDataSourceFields(false);
		suppilerForm.setIsGroup(true);
		suppilerForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
		suppilerForm.setGroupTitle("ข้อมูลการเสนอซื้อ");
		if (edit) {
			final TextItem ref_id = new TextItem("quote_id", "รหัสใบเสนอราคา");
			ref_id.setDefaultValue(quote_id);
			
			final StaticTextItem sup_id = new StaticTextItem("sid", "รหัสผู้จำหน่าย");
			sup_id.setDefaultValue(supplier_id);
			final SelectItem sup_name = new SelectItem("sup_name", "ชื่อผู้จำหน่าย");
			sup_name.setColSpan(4);
			//final StaticTextItem sup_type = new StaticTextItem("sup_type", "ประเภทลูกค้า");
			sup_name.setOptionDataSource(SupplierDS.getInstance());
			sup_name.setDefaultValue(supplier_name);
			sup_name.setEmptyDisplayValue("--โปรดเลือกผู้ผลิต--");
			sup_name.setPickListWidth(280);
			sup_name.setWidth(200);
			sup_name.setRequired(true);
			sup_name.setHint("*");
			ListGridField Field_1 = new ListGridField("sid", 80);  
	        ListGridField Field_2 = new ListGridField("sup_name", 200);  
	        //ListGridField Field_3 = new ListGridField("sup_type", 70);
	        //sup_name.setPickListFields(Field_1, Field_2, Field_3);
	        sup_name.setPickListFields(Field_1, Field_2);
	        
	        final SelectItem sup_payment_model = new SelectItem("payment_model", "วิธีการชำระเงิน");
	        sup_payment_model.setValueMap("เงินสด", "แคชเชียร์เช็ค");
			//paymentModel.setEmptyDisplayValue("--โปรดเลือกวิธีชำระเงิน--");
	        sup_payment_model.setDefaultValue(payment_model);
	        sup_payment_model.setWidth(100);
			final IntegerItem sup_credit = new IntegerItem("credit","เครดิต");
			sup_credit.setRequired(true);
			sup_credit.setHint("วัน*");
			sup_credit.setWidth(50);
			sup_credit.setTextAlign(Alignment.LEFT);
			sup_credit.setDefaultValue(credit);
			
	        sup_name.addChangedHandler(new ChangedHandler() {
				@Override
				public void onChanged(ChangedEvent event) {
					Record selected = sup_name.getSelectedRecord();
					if (selected != null) {
						String supplier_id = selected.getAttributeAsString("sid");
						String supplier_name = selected.getAttributeAsString("sup_name");
						String supplier_phone1 = selected.getAttributeAsString("sup_phone1");
						String supplier_phone2 = selected.getAttributeAsString("sup_phone2");
						
						String address = selected.getAttributeAsString("address");
						String street = selected.getAttributeAsString("street");
						String city = selected.getAttributeAsString("city");
						String state = selected.getAttributeAsString("state");
						String postal = selected.getAttributeAsString("postal");
						String fullAddress = address;
						if (street !=null) fullAddress += " ถนน " + street;
						if (city !=null) fullAddress += " เขต " + city;
						if (state !=null) fullAddress += " จังหวัด " + state;
						if (postal !=null) fullAddress += " รหัสไปรษณีย์ " + postal;
						
						String supplier_email = selected.getAttributeAsString("email");
						String supplier_fax = selected.getAttributeAsString("fax");
						material_list = selected.getAttributeAsString("list");
						Integer supplier_leadtime = selected.getAttributeAsInt("leadtime");
						
						client.setAttributes(supplier_id, supplier_name, supplier_phone1, supplier_phone2, supplier_email, supplier_fax, fullAddress, material_list, supplier_leadtime);

						sup_id.setValue(supplier_id);
						sup_name.setValue(supplier_name);
					}
				}
	        });
	        
	        sup_payment_model.addChangedHandler(new ChangedHandler() {
				@Override
				public void onChanged(ChangedEvent event) {
					if (sup_payment_model.validate()) {
						client.setPaymentModel(sup_payment_model.getValueAsString());
					}
				}
	        });
			
	        sup_credit.addChangedHandler(new ChangedHandler() {
				@Override
				public void onChanged(ChangedEvent event) {
					if (sup_credit.validate()) {
						client.setCredit(sup_credit.getValueAsInteger());
					}
				}
	        });
			
	        suppilerForm.setFields(ref_id, sup_id, sup_name, sup_payment_model, sup_credit);
//	        suppilerForm.setFields(sup_id, sup_type, sup_name );
		} else {
			final StaticTextItem ref_id = new StaticTextItem("quote_id", "รหัสใบเสนอราคา");
			ref_id.setDefaultValue(quote_id);
			final StaticTextItem sup_id = new StaticTextItem("sid", "รหัสผู้จำหน่าย");
			sup_id.setDefaultValue(supplier_id);
			final StaticTextItem sup_name = new StaticTextItem("sup_name", "ชื่อผู้จำหน่าย");
			sup_name.setColSpan(4);
			sup_name.setDefaultValue(supplier_name);
			final StaticTextItem sup_payment_model = new StaticTextItem("payment_model", "วิธีการชำระเงิน");
			sup_payment_model.setDefaultValue(payment_model);
			final StaticTextItem sup_credit = new StaticTextItem("credit", "เครดิต");
			sup_credit.setDefaultValue(credit);
			sup_credit.setHint("วัน");
			suppilerForm.setFields(ref_id, sup_id, sup_name, sup_payment_model, sup_credit);
			//suppilerForm.setFields(sup_id,sup_type, sup_name);
		}
		suppilerForm.setColWidths(100,100,100,80);
		//suppilerForm.fetchData(new Criterion("cid", OperatorId.EQUALS, cid));
		//suppilerForm.editRecord(record);
		
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
		headerLayout.addMembers(suppilerForm, commentForm);
		//headerLayout.addMembers(suppilerForm);
		layout.addMember(headerLayout);
		
		SectionStack sectionStack = new SectionStack();
    	sectionStack.setWidth100();
    	sectionStack.setHeight(250);
    	SectionStackSection section = new SectionStackSection("รายการวัตถุดิบ");  
    	section.setCanCollapse(false);  
        section.setExpanded(true);
        
        IButton addButton = new IButton("เพิ่มวัตถุดิบ");
		addButton.setIcon("[SKIN]actions/add.png");
        IButton delButton = new IButton("ลบวัตถุดิบ");
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
		
		RequestMaterialDS tempView = new RequestMaterialDS(request_id);
		Record[] cachedData = RequestMaterialDS.getInstance(request_id).getCacheData();
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
        quoteItemCell_4.setIncludeInRecordSummary(false);
        
        ListGridField quoteItemCell_5 = new ListGridField("price", 90);
        if (edit) quoteItemCell_5.setCanEdit(true);
        quoteItemCell_5.setShowGridSummary(false);
        quoteItemCell_5.setCellFormatter(FieldFormatter.getPriceFormat());
        quoteItemCell_5.setAlign(Alignment.RIGHT);
        
        ListGridNumberField quoteItemCell_6 = new ListGridNumberField("request_amount", 70);
        if (edit) quoteItemCell_6.setCanEdit(true);
        quoteItemCell_6.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_6.setShowGridSummary(true);
        
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
		
		final DynamicForm dateForm = new DynamicForm();
		dateForm.setWidth(300);
		dateForm.setNumCols(2);
		dateForm.setMargin(5);
		dateForm.setIsGroup(true);
		dateForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
		dateForm.setGroupTitle("ข้อกำหนดใบเสนอราคาอ้างอิง");
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
//		final IButton printButton = new IButton("พิมพ์ใบเสนอซื้อ");
//		printButton.setIcon("icons/16/print.png");
//		printButton.setWidth(120);
//		printButton.addClickHandler(new ClickHandler() {  
//            public void onClick(ClickEvent event) { 
//                SC.warn("click print");
//            	//Canvas.showPrintPreview(PrintQuotation.getPrintContainer(record));
//          }
//        });
//		if (edit || !status.equals("3_approved")) printButton.disable();
		
		final IButton saveButton = new IButton("บันทึกการแก้ไข");
		saveButton.setIcon("icons/16/save.png");
		saveButton.setWidth(120);
		saveButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	SC.confirm("ยืนยันการทำรายการ", "ต้องการบันทึกการแก้ไขใบเสนอซื้อ หรือไม่?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
							if (suppilerForm.validate()) saveRequest(main, request_id, suppilerForm, quoteListGrid, fromDate.getValueAsDate(), toDate.getValueAsDate(), deliveryDate.getValueAsDate(), currentUser);
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
            	SC.confirm("ยืนยันการทำรายการ", "ต้องการอนุมัติใบเสนอซื้อ หรือไม่?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
							//saveRequest(main, quote_id, suppilerForm, quoteListGrid, fromDate.getValueAsDate(), toDate.getValueAsDate(), deliveryDate.getValueAsDate(), currentUser);
							updateRequestStatus(request_id, "3_approved", "", currentUser.getFirstName() + " " + currentUser.getLastName());
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
            	SC.confirm("ยืนยันการทำรายการ", "ต้องการส่งใบเสนอซื้อกลับไปแก้ไข หรือไม่?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
//							SC.askforValue("กรุณาใส่ความคิดเห็น", new ValueCallback() {
//								@Override
//								public void execute(String value) {
//									if (value == null || value.equals("")){
//										SC.warn("กรุณาใส่ความคิดเห็นในกล่องข้อความ");
//									} else {
//										updateRequestStatus(quote_id, "1_waiting_for_revised", value);
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
					        selectOtherItem.setValueMap("ข้อมูลผู้จำหน่ายไม่ถูกต้อง", "รายการวัตถุดิบไม่ถูกต้อง", "เงื่อนไขในใบเสนอราคาไม่ถูกต้อง", "วันที่กำหนดส่งสินค้าไม่ถูกต้อง");  
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
						            	updateRequestStatus(request_id, "1_waiting_for_revised", selectOtherItem.getValueAsString(), currentUser.getFirstName() + " " + currentUser.getLastName());
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
		
		final IButton createPurchaseOrderButton = new IButton("ออกคำสั่งซื้อ");
		createPurchaseOrderButton.setIcon("icons/16/coins.png");
		createPurchaseOrderButton.setWidth(120);
		createPurchaseOrderButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	SC.confirm("ยืนยันการทำรายการ", "ต้องการสร้างคำสั่งซื้อ หรือไม่?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
							if (suppilerForm.validate()) createPurchaseOrder(main, request_id, suppilerForm, quoteListGrid, deliveryDate.getValueAsDate(), currentUser, record);
						}
					}
            	});
          }
        });
		
		if (page == 3) {
			controls.addMember(createPurchaseOrderButton);
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
			//controls.addMember(printButton);
			controls.addMember(saveButton);
		}
		controls.addMember(closeButton);
		layout.addMember(controls);
		
		addButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {
            	//SC.warn("add click " + quoteListGrid.getRecords().length);
            	ArrayList<String> selected = new ArrayList<String>();
            	for (ListGridRecord item : quoteListGrid.getRecords()) {
            		selected.add(item.getAttributeAsString("mid"));
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
									SC.warn("การลบวัตถุดิบล้มเหลว");
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
	
	public void saveRequest(final Window main, final String request_id, DynamicForm Suppiler, ListGrid quoteListGrid, Date from, Date to, Date delivery, User currentUser){
		ListGridRecord[] all = quoteListGrid.getRecords();
		
		if (all.length == 0) {
			SC.warn("กรูณาเลือกรายการสินค้าอย่างน้อย 1 รายการ");
			return;
		}
		
		Double total_weight = 0.0;
		Double total_netExclusive = 0.0;
		Double total_amount = 0.0;
		//final String quote_id = "QA70" + Math.round((Math.random() * 100));
		final ArrayList<RequestMaterialDetails> materialList = new ArrayList<RequestMaterialDetails>();
		
		for (ListGridRecord item : all){
			total_weight += item.getAttributeAsDouble("weight");
			total_amount += item.getAttributeAsDouble("request_amount");
			total_netExclusive += item.getAttributeAsDouble("sum_price");
			
			String sub_request_id = item.getAttributeAsString("sub_request_id");
			String mid = item.getAttributeAsString("mid");
			String pname = item.getAttributeAsString("mat_name");
			String ptype = item.getAttributeAsString("type");
			Double pweight = item.getAttributeAsDouble("weight");
			Double prequest_amount = item.getAttributeAsDouble("request_amount");
			String punit = item.getAttributeAsString("unit");
			Double pprice = item.getAttributeAsDouble("price");
			RequestMaterialDetails temp = new RequestMaterialDetails();
			temp.save(mid, pname, pweight, ptype, punit);
			temp.setID(sub_request_id, request_id);
			temp.setQuantity(prequest_amount, pprice);
			materialList.add(temp);
		}
		//System.out.println(total_weight + " " + total_amount + " " + total_netExclusive);
			//status
			final String request_status = "2_waiting_for_approved";
			
			if (Suppiler.getField("sid").getValue() == null || Suppiler.getField("sup_name").getValue() == null) {
				SC.warn("ชื่อและรหัสผู้จำหน่ายไม่ถุกต้อง");
				return;
			}
			
			String quoteId = (String) Suppiler.getField("quote_id").getValue();
			String payment_model = (String) Suppiler.getField("payment_model").getValue();
			Integer credit = (Integer) Suppiler.getField("credit").getValue();
			ListGridRecord updateRecord = PurchaseRequestData.createRecord(request_id, client.sid, client.sup_name, quoteId, payment_model, credit, from, to, delivery, total_weight, total_amount, total_netExclusive, new Date(), null, currentUser.getFirstName() + " " + currentUser.getLastName(), null, "", request_status);
			
			PurchaseRequestDS.getInstance().updateData(updateRecord, new DSCallback() {
				@Override
				public void execute(DSResponse dsResponse, Object data,
						DSRequest dsRequest) {
						//System.out.println("Test " + dsResponse.getStatus());
						if (dsResponse.getStatus() != 0) {
							SC.warn("การบันทึกใบเสนอซื้อล้มเหลว กรุณาทำรายการใหม่อีกครั้ง");
							main.destroy();
						} else { 
							for (RequestMaterialDetails item : materialList) {
								if (item.sub_request_id == null) {
									item.sub_request_id = "SPR80" + Math.round((Math.random() * 100));
									ListGridRecord subUpdateRecord = RequestMaterialData.createRecord(item);
									RequestMaterialDS.getInstance(request_id).addData(subUpdateRecord);
								} else  {
									ListGridRecord subUpdateRecord = RequestMaterialData.createRecord(item);
									RequestMaterialDS.getInstance(request_id).updateData(subUpdateRecord);
								}
							}
							SC.say("แก้ไขใบเสนอซื้อเสร็จสิ้น <br> " + "รหัสใบเสนอซื้อ " + request_id + "<br> สถานะของใบเสนอซื้อ " + PurchaseRequestStatus.getDisplay(request_status));
							main.destroy();
						}
				}
			});
	}
	
	void updateRequestStatus(String quote_id, final String status, String comment, String user) {
		Record updated = PurchaseRequestData.createStatusRecord(quote_id,status,comment, user);
		PurchaseRequestDS.getInstance().updateData(updated, new DSCallback() {
			@Override
			public void execute(DSResponse dsResponse, Object data,
					DSRequest dsRequest) {
					if (dsResponse.getStatus() != 0) {
						SC.warn("การอนุมัติใบเสนอซื้อล้มเหลว");
					} else { 
						SC.say("แก้ไขสถานะใบเสนอซื้อ \"" + PurchaseRequestStatus.getDisplay(status) + "\" เสร็จสิ้น");
					}
			}
		});
	}
	
	public void createPurchaseOrder(final Window main, final String request_id, DynamicForm Suppiler, ListGrid quoteListGrid, final Date delivery, final User currentUser, final ListGridRecord request_record){
		
		ListGridRecord[] all = quoteListGrid.getRecords();
		
//		if (all.length == 0) {
//			SC.warn("กรูณาเลือกรายการสินค้าอย่างน้อย 1 รายการ");
//			return;
//		}
		
		Double total_weight = 0.0;
		Double total_netExclusive = 0.0;
		//Double total_produce_weight = 0.0;
		Double total_amount = 0.0;
		//Integer total_produce_amount = 0;
		
		final String order_id = "PO70" + Math.round((Math.random() * 100));
		//final String invoice_id = "IN70" + Math.round((Math.random() * 100));
		//final String plan_id = "PL70" + Math.round((Math.random() * 100));
		final ArrayList<OrderMaterialDetails> orderProductList = new ArrayList<OrderMaterialDetails>();
		//final ArrayList<SaleProductDetails> invoiceProductList = new ArrayList<SaleProductDetails>();
		//final ArrayList<PlanProductDetails> planProductList = new ArrayList<PlanProductDetails>();
		//final ArrayList<Record> productUpdateList = new ArrayList<Record>();
		
		for (ListGridRecord item : all){
			total_weight += item.getAttributeAsDouble("weight");
			total_amount += item.getAttributeAsDouble("request_amount");
			total_netExclusive += item.getAttributeAsDouble("sum_price");
			
			String pid = item.getAttributeAsString("mid");
			String pname = item.getAttributeAsString("mat_name");
			String ptype = item.getAttributeAsString("type");
			//String psize = item.getAttributeAsString("size");
			Double pweight = item.getAttributeAsDouble("weight");
			Double psale_amount = item.getAttributeAsDouble("request_amount");
			String punit = item.getAttributeAsString("unit");
			Double pprice = item.getAttributeAsDouble("price");
			
			//Purchase order
			String sub_order_id = "SUO80" + Math.round((Math.random() * 1000));
			OrderMaterialDetails temp1 = new OrderMaterialDetails();
			temp1.save(pid, pname, pweight, ptype, punit);
			temp1.setID(sub_order_id, order_id);
			temp1.setQuantity(psale_amount, pprice);
			orderProductList.add(temp1);
			
//			//Invoice
//			String sub_invoice_id = "SI80" + Math.round((Math.random() * 1000));
//			OrderMaterialDetails temp2 = new OrderMaterialDetails();
//			temp2.save(pid, pname, pweight, pprice, ptype, punit);
//			temp2.setID(sub_invoice_id, invoice_id);
//			temp2.setQuantity(psale_amount);
//			invoiceProductList.add(temp2);
//
//			//Production Plan
//			Record[] selectedProduct = ProductDS.getInstance().applyFilter(ProductDS.getInstance().getCacheData(), new Criterion("pid", OperatorId.EQUALS, pid));
//			if (selectedProduct.length == 0) {
//				SC.warn("ไม่พบรายการสินค้า รหัส " + pid + " <br> โปรดตรวจสอบ ข้อมูลสินค้าอีกครั้ง" );
//				return;
//			} else {
//				Record product = selectedProduct[0];
//				Integer remain = product.getAttributeAsInt("remain");
//				Integer reserved = product.getAttributeAsInt("reserved");
//				Integer produce = psale_amount - remain;
//				
//				System.out.println("Produce " + pid + " - reserved = " + reserved + " remain = " + remain + ", need for sale = " + psale_amount);
//				if (produce > 0) {
//					//Create sub production order in planProductList
//					total_produce_weight += product.getAttributeAsDouble("weight") * produce;
//					total_produce_amount += produce;
//					//reserved = remain
//					Record product_update = ProductData.createReservedRecord(pid, reserved + remain, 0, product);
//					System.out.println("Create product_update - reserved = " + (reserved + remain) + " remain = 0");
//					//Update reserved
//					productUpdateList.add(product_update);
//					planProductList.add(CreatePlanProductDetails(plan_id, product, produce));
//					System.out.println("CreatePlanProductDetails - produce = " + produce);
//				} else {
//					//Nothing reserved = psale_amount
//					Record product_update = ProductData.createReservedRecord(pid, reserved + psale_amount, remain - psale_amount, product);
//					System.out.println("Create product_update - reserved = " + (reserved + psale_amount) + " remain = " + (remain - psale_amount));
//					//Update reserved
//					productUpdateList.add(product_update);
//				}
//				
//			}
		}	
			//For sale and invoice
			String order_status = "1_created_order";
			String payment_status = "1_waiting_for_payment";
			String received_status = "1_waiting_for_received";
//			if (planProductList.size() != 0) {
//				sale_status = "1_waiting_for_production";
//			}
//			final String invoice_status = "1_waiting_for_payment";
			String sid = (String) Suppiler.getField("sid").getValue();
			String sup_name = (String) Suppiler.getField("sup_name").getValue();
			String quote_id = (String) Suppiler.getField("quote_id").getValue();
			String payment_model = (String) Suppiler.getField("payment_model").getValue();
			Integer credit = (Integer) Suppiler.getField("credit").getValue();
			
//			DateRange dateRange = new DateRange();  
//	        dateRange.setRelativeStartDate(RelativeDate.TODAY);
//	        dateRange.setRelativeEndDate(new RelativeDate("+"+credit+"d"));
//	        final Date due_date = dateRange.getEndDate();
	        
			final ListGridRecord orderRecord = PurchaseOrderData.createRecord(order_id, request_id, sid, sup_name, quote_id, payment_model, credit, null, null, delivery, total_weight, total_amount, total_netExclusive, new Date(), request_record.getAttributeAsDate("modified_date"), currentUser.getFirstName() + " " + currentUser.getLastName(), request_record.getAttribute("modified_by"), "", order_status, payment_status, received_status);
//			final ListGridRecord invoiceRecord = InvoiceData.createRecord(invoice_id, sale_id, cid, sup_name, payment_model, credit, delivery, total_weight, total_amount, total_netExclusive, new Date(), null, currentUser.getFirstName() + " " + currentUser.getLastName(), null, invoice_status, purchase_id, due_date, null);
			
			PurchaseOrderDS.getInstance().addData(orderRecord, new DSCallback() {
				@Override
				public void execute(DSResponse dsResponse, Object data,
						DSRequest dsRequest) {
						if (dsResponse.getStatus() != 0) {
							SC.warn("การสร้างคำสั่งซื้อล้มเหลว กรุณาทำรายการใหม่อีกครั้ง");
							main.destroy();
						} else { 
							for (OrderMaterialDetails item : orderProductList) {
									ListGridRecord subAddRecord = OrderMaterialData.createRecord(item);
									OrderMaterialDS.getInstance(order_id).addData(subAddRecord);
							}
							request_record.setAttribute("status", "5_created_po");
							PurchaseRequestDS.getInstance().updateData(request_record);
							
							SC.say("ใบเสนอซื้อรหัส " + request_id + " " + PurchaseRequestStatus.getDisplay(request_record.getAttributeAsString("status")) + "<br><br>รหัสคำสั่งซื้อ " + order_id + "<br> สถานะเป็น " + PurchaseOrderStatus.getDisplay(orderRecord.getAttributeAsString("status")) + " และ " + PurchaseOrderStatus.getPaymentDisplay(orderRecord.getAttributeAsString("payment_status")));
							main.destroy();
						}
				}
			});
		
//			//For production
//			for (Record updateProduct : productUpdateList) {
//				ProductDS.getInstance().updateData(updateProduct);
//			}
		
//			final Double produce_weight = total_produce_weight;
//			final Integer produce_amount = total_produce_amount;
//			if (planProductList.size() != 0) {
//				SC.confirm("สร้างแผนการผลิตโดยอัตโนมัติ", "สินค้าในรายการขายไม่เพียงพอ <br> ต้องการสร้างแผนการผลิต หรือไม่?" , new BooleanCallback() {
//					@Override
//					public void execute(Boolean value) {
//						if (value) {
//							//Date delivery = null;
//							String plan_status = "3_approved";
//							//xxxService.xxx(Callback quoteId);
//							ListGridRecord newRecord = PlanData.createRecord(plan_id, sale_id, delivery, produce_weight, produce_amount, new Date(), null,  currentUser.getFirstName() + " " + currentUser.getLastName(), null, "", plan_status, "สร้างจากรายการขายโดยอัตโนมัติ");
//							PlanDS.getInstance().addData(newRecord, new DSCallback() {
//								@Override
//								public void execute(DSResponse dsResponse, Object data,
//										DSRequest dsRequest) {
//										if (dsResponse.getStatus() != 0) {
//											SC.warn("การสร้างแผนการผลิตล้มเหลว");
//										} else { 
//											for (PlanProductDetails item : planProductList) {
//												ListGridRecord subNewRecord = PlanProductData.createRecord(item);
//												PlanProductDS.getInstance(plan_id).addData(subNewRecord);
//												//System.out.println("add data " + item.sub_plan_id);
//											}
//											CreateInvoiceAndSaleOrder(main, invoice_id, invoiceRecord, sale_id, saleRecord, invoiceProductList,saleProductList, " <br><br> สร้างแผนการผลิตเสร็จสิ้น " + "รหัสแผนการผลิต " + plan_id + "<br>" + "กำหนดส่งสินค้าวันที่ " + DateUtil.formatAsShortDate(delivery));
//										}
//								}
//							});
//						}
//					}
//            	});
//			} else {
//				CreateInvoiceAndSaleOrder(main, invoice_id, invoiceRecord, sale_id, saleRecord, invoiceProductList,saleProductList, "");
//			}
	}
//	
//	private PlanProductDetails CreatePlanProductDetails(String plan_id, Record product, Integer pplan_amount) {
//		String sub_plan_id = "SP80" + Math.round((Math.random() * 100));
//		String pid = product.getAttributeAsString("pid");
//		String pname = product.getAttributeAsString("name");
//		String ptype = product.getAttributeAsString("type");
//		Double pweight = product.getAttributeAsDouble("weight");
//		String punit = product.getAttributeAsString("unit");
//		
//		Double psize = product.getAttributeAsDouble("size");
//		Double pwidth = product.getAttributeAsDouble("width");
//		Double plength = product.getAttributeAsDouble("length");
//		Double pheight = product.getAttributeAsDouble("height");
//		Double pdiameter = product.getAttributeAsDouble("diameter");
//		Double pthickness = product.getAttributeAsDouble("thickness");
//		
//		PlanProductDetails temp = new PlanProductDetails();
//		temp.save(pid, pname, pweight * pplan_amount, ptype, punit, psize, pwidth, plength, pheight, pdiameter, pthickness);
//		temp.setID(sub_plan_id, plan_id);
//		temp.setQuantity(pplan_amount);
//		
//		return temp;
//	}
//	
//	private void CreateInvoiceAndSaleOrder(final Window main, final String invoice_id, Record invoiceRecord, final String sale_id, final Record saleRecord, final ArrayList<SaleProductDetails> invoiceProductList,final ArrayList<SaleProductDetails> saleProductList, final String message) {
//		//Auto create invoice
//		InvoiceDS.getInstance().addData(invoiceRecord, new DSCallback() {
//			@Override
//			public void execute(DSResponse dsResponse, Object data,
//					DSRequest dsRequest) {
//					if (dsResponse.getStatus() != 0) {
//						SC.warn("การสร้างรายการขายล้มเหลว กรุณาทำรายการใหม่อีกครั้ง");
//						main.destroy();
//					} else { 
//						for (SaleProductDetails item : invoiceProductList) {
//							ListGridRecord subAddRecord = SaleProductData.createRecord(item);
//							SaleProductDS.getInstance(invoice_id).addData(subAddRecord);
//						}
//						
//						SaleOrderDS.getInstance().addData(saleRecord, new DSCallback() {
//							@Override
//							public void execute(DSResponse dsResponse, Object data,
//									DSRequest dsRequest) {
//									if (dsResponse.getStatus() != 0) {
//										SC.warn("การสร้างรายการขายล้มเหลว กรุณาทำรายการใหม่อีกครั้ง");
//										main.destroy();
//									} else { 
//										for (SaleProductDetails item : saleProductList) {
//												ListGridRecord subAddRecord = SaleProductData.createRecord(item);
//												SaleProductDS.getInstance(sale_id).addData(subAddRecord);
//										}
//										SC.say("สร้างรายการขายเสร็จสิ้น <br> " + "รหัสรายการขาย " + sale_id + "<br> สถานะของรายการขาย " + SaleOrderStatus.getDisplay(saleRecord.getAttributeAsString("status")) + "<br><br> สร้างใบแจ้งหนี้โดยอัตโนมัติ เลขที่ "+ invoice_id + "<br> กำหนดชำระเงินวันที่ " + DateUtil.formatAsShortDate(saleRecord.getAttributeAsDate("due_date")) + message);
//										main.destroy();
//									}
//							}
//						});
//					}
//			}
//		});
//	}
}
