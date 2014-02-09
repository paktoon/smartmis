package com.smart.mis.client.function.production.order.scraping;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.smart.mis.client.function.inventory.material.requisition.MaterialRequestDS;
import com.smart.mis.client.function.inventory.material.requisition.MaterialRequestData;
import com.smart.mis.client.function.inventory.material.requisition.MaterialRequestItemDS;
import com.smart.mis.client.function.inventory.material.requisition.MaterialRequestItemData;
import com.smart.mis.client.function.inventory.material.requisition.MaterialRequestItemDetails;
import com.smart.mis.client.function.production.order.casting.CastingDS;
import com.smart.mis.client.function.production.order.casting.CastingData;
import com.smart.mis.client.function.production.order.casting.CastingMaterialData;
import com.smart.mis.client.function.production.order.casting.CastingPrintWindow;
import com.smart.mis.client.function.production.order.casting.CastingProductDS;
import com.smart.mis.client.function.production.plan.PlanDS;
import com.smart.mis.client.function.production.plan.PlanData;
import com.smart.mis.client.function.production.plan.product.PlanProductDS;
import com.smart.mis.client.function.production.process.MaterialProcessDS;
import com.smart.mis.client.function.production.process.ProcessListDS;
import com.smart.mis.client.function.production.product.ProductDS;
import com.smart.mis.client.function.production.smith.SmithDS;
import com.smart.mis.client.function.purchasing.material.MaterialDS;
import com.smart.mis.client.function.purchasing.supplier.SupplierDS;
import com.smart.mis.client.function.report.production.MaterialUsedReportDS;
import com.smart.mis.client.function.sale.quotation.product.QuoteProductDS;
import com.smart.mis.shared.EditorWindow;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.FieldVerifier;
import com.smart.mis.shared.FromToValidate;
import com.smart.mis.shared.ListGridNumberField;
import com.smart.mis.shared.PrintHeader;
import com.smart.mis.shared.prodution.ProductionPlanStatus;
import com.smart.mis.shared.prodution.Smith;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
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

public class ScrapingCreateWindow {

//	SelectProductList addFunc;
//	Customer client;
	Smith smith;
	Window editWindow;
	VLayout layout;
	
	public void show(ListGridRecord record, User currentUser, Integer std_time){
		smith = new Smith();
		editWindow = new Window();
		editWindow.setTitle("ข้อมูลคำสั่งผลิต");
		editWindow.setWidth(670);  
		editWindow.setHeight(620);
		editWindow.setShowMinimizeButton(false);
		editWindow.setIsModal(true);
		editWindow.setShowModalMask(true);
		editWindow.setCanDragResize(false);
		editWindow.setCanDragReposition(false);
		editWindow.centerInPage();
		
		//editWindow.setHeaderControls(HeaderControls.HEADER_LABEL);
		editWindow.addItem(getViewEditor(record, editWindow, currentUser, std_time));
		editWindow.show();
	}
	
	private VLayout getViewEditor(final ListGridRecord record, final Window main, final User currentUser, Integer std_time) {
		layout = new VLayout();
		layout.setWidth(650);
		layout.setHeight(600);
		layout.setMargin(10);
		
		final String plan_id = record.getAttributeAsString("plan_id");
		final String job_id = record.getAttributeAsString("job_id");
		
		Double total_weight = record.getAttributeAsDouble("total_recv_weight");
		Integer total_amount = record.getAttributeAsInt("total_recv_amount");
		
		PlanDS.getInstance().refreshData();
		Record[] plan_records = PlanDS.getInstance().applyFilter(PlanDS.getInstance().getCacheData(), new Criterion("plan_id", OperatorId.EQUALS, plan_id));
		
		String sale_id = plan_records[0].getAttributeAsString("sale_id");
		if (sale_id == null) sale_id= "-";
		Date delivery = plan_records[0].getAttributeAsDate("delivery");
		String status = plan_records[0].getAttributeAsString("status");
		String created_by = plan_records[0].getAttributeAsString("created_by");
		Date created_date = plan_records[0].getAttributeAsDate("created_date");
		
		System.out.println("plan_id " +  plan_id + " casting job_id " + job_id + " total_recv_weight " + total_weight + " total_recv_amount " + total_amount);
		System.out.println("sale_id " +  sale_id + " delivery " + delivery + " status " + status + " created_by " + created_by + " created_date " + created_date);
//		Double total_weight = plan_records[0].getAttributeAsDouble("total_weight");
//		Integer total_amount = plan_records[0].getAttributeAsInt("total_amount");
		
		final DynamicForm planForm = new DynamicForm();
		planForm.setWidth100(); 
		planForm.setHeight(30);
		planForm.setMargin(5);
		planForm.setIsGroup(true);
		planForm.setNumCols(6);
		planForm.setGroupTitle("ข้อมูลแผนการผลิต");
		
		StaticTextItem qid = new StaticTextItem("plan_id", "รหัสแผนการผลิต");
		StaticTextItem sid = new StaticTextItem("sale_id", "รหัสรายการขาย");
		StaticTextItem ddate = new StaticTextItem("delivery", "วันที่กำหนดส่งสินค้า");
		StaticTextItem sts = new StaticTextItem("status", "สถานะ");
		StaticTextItem cby = new StaticTextItem("created_by", "สร้างโดย");
		StaticTextItem cdate = new StaticTextItem("created_date", "สร้างเมื่อ");
		qid.setValue(plan_id);
		sid.setValue(sale_id);
		if (delivery != null ) ddate.setValue(DateTimeFormat.getFormat("MM/dd/yyy").format(delivery));
		else ddate.setValue("-");
		sts.setValue(ProductionPlanStatus.getDisplay(status));
		cby.setValue(created_by);
		cdate.setValue(DateTimeFormat.getFormat("MM/dd/yyy").format(created_date));
		planForm.setFields(qid, sid, ddate, sts, cdate ,cby);
		planForm.setColWidths(100,100,100,100,100,100);
		
		layout.addMember(planForm);
		
		final DynamicForm smithForm = new DynamicForm();
		smithForm.setWidth100(); 
		smithForm.setHeight(30);
		smithForm.setMargin(5); 
		smithForm.setNumCols(6); 
		smithForm.setCellPadding(2);
		smithForm.setAutoFocus(true);
		smithForm.setSelectOnFocus(true);
		smithForm.setDataSource(SmithDS.getInstance());
		smithForm.setUseAllDataSourceFields(false);
		smithForm.setIsGroup(true);
		smithForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
		smithForm.setGroupTitle("ข้อมูลช่าง");
			final StaticTextItem smith_id = new StaticTextItem("smid", "รหัสช่าง");
			final SelectItem smith_name = new SelectItem("name", "ชื่อช่าง");
			//smith_name.setColSpan(3);
			final StaticTextItem smith_type = new StaticTextItem("type", "ประเภทงาน");
			
			smith_name.setOptionDataSource(SmithDS.getInstance());
			smith_name.setOptionCriteria(new Criterion("type", OperatorId.EQUALS, "แต่งและฝังพลอยประดับ"));
			smith_name.setEmptyDisplayValue("--โปรดเลือกช่าง--");
			smith_name.setPickListWidth(280);
			smith_name.setWidth(240);
			smith_name.setRequired(true);
			smith_name.setHint("*");
			ListGridField Field_1 = new ListGridField("smid", 80);  
	        ListGridField Field_2 = new ListGridField("name", 200);
	        smith_name.setPickListFields(Field_1, Field_2);
	        
	        final StaticTextItem smith_phone1 = new StaticTextItem("phone1", "โทรศัทท์ 1");
	        final StaticTextItem smith_phone2 = new StaticTextItem("phone2", "โทรศัทท์ 2");
	        final StaticTextItem smith_email = new StaticTextItem("email", "อีเมล");
	        final StaticTextItem smith_address = new StaticTextItem("address", "ที่อยู่");
	        smith_address.setColSpan(6);
	        
	        smith_name.addChangedHandler(new ChangedHandler() {
				@Override
				public void onChanged(ChangedEvent event) {
					Record selected = smith_name.getSelectedRecord();
					if (selected != null) {
						String sid = selected.getAttributeAsString("smid");
						String sname = selected.getAttributeAsString("name");
						String stype = selected.getAttributeAsString("type");
						//Contact info
						//String smith_address = selected.getAttributeAsString("address");
						String phone1 = selected.getAttributeAsString("phone1");
						if (phone1 == null) phone1 = "-";
						String phone2 = selected.getAttributeAsString("phone2");
						if (phone2 == null) phone2 = "-";
						String email = selected.getAttributeAsString("email");
						if (email == null) email = "-";
						
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
						
						smith.setAttributes(sid, sname, phone1, phone2, email, fullAddress, stype);

						smith_id.setValue(sid);
						smith_type.setValue(stype);
						
						smith_phone1.setValue(phone1);
						smith_phone2.setValue(phone2);
						smith_email.setValue(email);
						smith_address.setValue(fullAddress);
					}
				}
	        });
	        smithForm.setFields(smith_name, smith_id, smith_type,smith_email, smith_phone1, smith_phone2 , smith_address);  

//			smithForm.setDataSource(CastingDS.getInstance());
//			smithForm.setUseAllDataSourceFields(false);
//			
//			final StaticTextItem smith_id = new StaticTextItem("smid", "รหัสช่าง");
//			final StaticTextItem smith_name = new StaticTextItem("sname", "ชื่อช่าง");
//			final StaticTextItem smith_type = new StaticTextItem("stype", "ประเภทช่าง");
//	        final StaticTextItem smith_phone1 = new StaticTextItem("sphone1", "โทรศัทท์ 1");
//	        final StaticTextItem smith_phone2 = new StaticTextItem("sphone2", "โทรศัทท์ 2");
//	        final StaticTextItem smith_email = new StaticTextItem("semail", "อีเมล");
//	        final StaticTextItem smith_address = new StaticTextItem("saddress", "ที่อยู่");
//	        smithForm.setFields(smith_id, smith_name, smith_type, smith_phone1, smith_phone2, smith_email, smith_address);

		smithForm.setColWidths(100,100,100,100,100,100);
		//smithForm.editRecord(record);
		
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
		//headerLayout.addMembers(smithForm, commentForm);
		headerLayout.addMembers(smithForm);
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
		final ListGrid orderListGrid = getListGrid();
		orderListGrid.setHeight(230);
		orderListGrid.setAlternateRecordStyles(true);  
		orderListGrid.setShowAllRecords(true);  
		orderListGrid.setAutoFetchData(true);  
		orderListGrid.setSelectionType(SelectionStyle.NONE);
		orderListGrid.setCanResizeFields(false);
		orderListGrid.setShowGridSummary(true);
		orderListGrid.setEditEvent(ListGridEditEvent.CLICK);  
		orderListGrid.setListEndEditAction(RowEndEditAction.NONE);
		orderListGrid.setShowRowNumbers(true);
		orderListGrid.setCanExpandRecords(true);
        final Criterion ci = new Criterion("status", OperatorId.EQUALS, true);
		orderListGrid.setCriteria(ci);
//		if (edit) {
//			orderListGrid.setCanRemoveRecords(true);
//			orderListGrid.setWarnOnRemoval(true);
//			orderListGrid.setWarnOnRemovalMessage("คุณต้องการลบ รายการสินค้า หรือไม่?");
//		}
		
		CastingProductDS tempView = CastingProductDS.getInstance(job_id);
		Record[] cachedData = tempView.getCacheData();
		if (cachedData.length != 0) {
			tempView.setTestData(cachedData);
		}
		orderListGrid.setDataSource(tempView);
		orderListGrid.setUseAllDataSourceFields(false);
		
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
        
        ListGridNumberField quoteItemCell_4 = new ListGridNumberField("weight", 90);
        quoteItemCell_4.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_4.setShowGridSummary(true);
        quoteItemCell_4.setIncludeInRecordSummary(false);
        
        ListGridNumberField quoteItemCell_6 = new ListGridNumberField("recv_amount", 90);
        quoteItemCell_6.setTitle("จำนสนสินค้าที่สั่งผลิต");
        
        quoteItemCell_6.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_6.setShowGridSummary(true);
        
        ListGridField quoteItemCell_7 = new ListGridField("details");
        
//        ListGridSummaryField quoteItemCell_sum = new ListGridSummaryField("sum_price", 100);
//
//        quoteItemCell_sum.setRecordSummaryFunction(RecordSummaryFunctionType.MULTIPLIER);
//        quoteItemCell_sum.setSummaryFunction(SummaryFunctionType.SUM);
//        quoteItemCell_sum.setShowGridSummary(true);
//        quoteItemCell_sum.setCellFormatter(FieldFormatter.getPriceFormat());
//        quoteItemCell_sum.setAlign(Alignment.RIGHT);
 
        orderListGrid.setFields(quoteItemCell_1, quoteItemCell_2, quoteItemCell_7, quoteItemCell_6, quoteItemCell_3);
        //itemLayout.addMember(orderListGrid);
        section.setItems(orderListGrid);
        sectionStack.setSections(section);
		layout.addMember(sectionStack);
		
		HLayout footerLayout = new HLayout();
		footerLayout.setHeight(100);
		
		final DynamicForm dateForm = new DynamicForm();
		dateForm.setWidth(300);
		dateForm.setHeight(75);
		dateForm.setNumCols(2);
		dateForm.setMargin(5);
		dateForm.setIsGroup(true);
		dateForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
		dateForm.setGroupTitle("ข้อกำหนดคำสั่งผลิต");
		//dateForm.setCanEdit(false);
		
		DateRange dateRange = new DateRange();  
	    dateRange.setRelativeStartDate(RelativeDate.TODAY);
	    dateRange.setRelativeEndDate(new RelativeDate("+"+std_time+"d"));
		
		final DateItem sentDate = new DateItem();
		sentDate.setName("sent_date");
		sentDate.setTitle("วันที่สั่งผลิต");
		sentDate.setUseTextField(true);
		sentDate.setDefaultValue(dateRange.getStartDate());
		sentDate.setCanEdit(false);
		
		final DateItem dueDate = new DateItem();
		dueDate.setName("due_date");
		dueDate.setTitle("วันที่กำหนดรับของ");
		dueDate.setUseTextField(true);
		dueDate.setDefaultValue(dateRange.getEndDate());
		
		FromToValidate.addValidator(sentDate, dueDate);
		//dueDate.setCanEdit(false);
		
//		final DateItem deliveryDate = new DateItem();
//		deliveryDate.setName("deliveryDate");
//		deliveryDate.setTitle("วันที่กำหนดส่งของ");
//		deliveryDate.setUseTextField(true);
		
//        fromDate.setDefaultChooserDate(from);
//        fromDate.setValue(from);
//        toDate.setDefaultChooserDate(to);
//        toDate.setValue(to);
//        deliveryDate.setDefaultChooserDate(delivery);
//        deliveryDate.setValue(delivery);
//        fromDate.setRequired(true);
//        fromDate.setHint("*");
//		toDate.setRequired(true);
//		toDate.setHint("*");
//		deliveryDate.setRequired(true);
//		deliveryDate.setHint("*");
		
//		dateForm.setFields(fromDate, toDate, deliveryDate);
		dateForm.setFields(sentDate, dueDate);
		dateForm.setColWidths(130,80);
		//dateForm.editRecord(record);
		footerLayout.addMember(dateForm);
		//******************End
		
		//******************Summary
		final DynamicForm summaryForm = new DynamicForm();
		summaryForm.setWidth(300);
		summaryForm.setHeight(75);
		summaryForm.setNumCols(2);
		summaryForm.setMargin(5);
		summaryForm.setIsGroup(true);
		summaryForm.setGroupTitle("สรุปยอดรวม");
		summaryForm.setColWidths(120, 100);
		NumberFormat nf = NumberFormat.getFormat("#,##0.00");
		final StaticTextItem total_sent_weight = new StaticTextItem("total_sent_weight");
		total_sent_weight.setValue(nf.format(total_weight));
		final StaticTextItem total_sent_amount = new StaticTextItem("total_sent_amount");
		total_sent_amount.setValue(nf.format(total_amount));
		total_sent_weight.setWidth(100);
		total_sent_amount.setWidth(100);
		total_sent_weight.setTitle("น้ำหนักรวม");
		total_sent_amount.setTitle("จำนวนรวม");
		total_sent_weight.setTextAlign(Alignment.RIGHT);
		total_sent_amount.setTextAlign(Alignment.RIGHT);
		total_sent_weight.setHint("กรัม");
		total_sent_amount.setHint("ชิ้น");
		summaryForm.setFields(total_sent_amount, total_sent_weight);
		//summaryForm.editRecord(record);
		footerLayout.addMember(summaryForm);
		
		layout.addMember(footerLayout);
		
		//Control
		HLayout controls = new HLayout();
		controls.setAlign(Alignment.CENTER);
		controls.setMargin(5);
		controls.setMembersMargin(5);
		final IButton printButton = new IButton("ออกคำสั่งแต่่งและฝังพลอย");
		printButton.setIcon("icons/16/print.png");
		printButton.setWidth(170);
		printButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	if (smithForm.validate()) {
                    //SC.say("click order and print");
                	SC.confirm("ยืนยันการออกคำสั่งแต่่งและฝังพลอย", "ต้องการออกคำสั่งแต่่งและฝังพลอย หรือไม่?" , new BooleanCallback() {
    					@Override
    					public void execute(Boolean value) {
    						if (value) {
    		                    createCreateOrder(planForm, orderListGrid, sentDate.getValueAsDate(), dueDate.getValueAsDate(), currentUser, record);
    						}
    					}
                	});
            	}
            	else {
            		SC.warn("กรุณาเลือกข้อมูลช่าง");
            	}
            	//Canvas.showPrintPreview(PrintQuotation.getPrintContainer(record));
          }
        });
		// if (edit || !status.equals("3_approved")) printButton.disable();
		
//		final IButton saveButton = new IButton("บันทึกการแก้ไข");
//		saveButton.setIcon("icons/16/save.png");
//		saveButton.setWidth(120);
//		saveButton.addClickHandler(new ClickHandler() {  
//            public void onClick(ClickEvent event) { 
//            	SC.confirm("ยืนยันการทำรายการ", "ต้องการบันทึกการแก้ไขใบเสนอราคา หรือไม่?" , new BooleanCallback() {
//					@Override
//					public void execute(Boolean value) {
//						if (value) {
//							if (smithForm.validate()) saveQuotation(main, quote_id, smithForm, orderListGrid, fromDate.getValueAsDate(), toDate.getValueAsDate(), deliveryDate.getValueAsDate(), currentUser);
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
		
//		final IButton deliveryButton = new IButton("นำส่งสินค้า");
//		deliveryButton.setIcon("icons/16/truck-icon-16.png");
//		deliveryButton.setWidth(120);
//		deliveryButton.addClickHandler(new ClickHandler() {  
//            public void onClick(ClickEvent event) {  
//            	SC.confirm("ยืนยันการสร้างรายการนำส่งสินค้า", "ต้องการสร้างรายการนำส่งสินค้า หรือไม่?" , new BooleanCallback() {
//					@Override
//					public void execute(Boolean value) {
//						if (value) {
////							SC.askforValue("กรุณาระบุเลขที่คำสั่งซื้อ", new ValueCallback() {
////								@Override
////								public void execute(String value) {
////									if (value == null || value.equals("")){
////										SC.warn("กรุณาระบุเลขที่คำสั่งซื้อในกล่องข้อความ");
////									} else {
////										if (smithForm.validate()) createSaleOrder(main, quote_id, smithForm, quoteListGrid, deliveryDate.getValueAsDate(), currentUser, value);
////									}
////								}});
//							String invoice_id = record.getAttributeAsString("invoice_id");
//							createDeliveryOrder(main, sale_id, invoice_id, smithForm, orderListGrid, deliveryDate.getValueAsDate(), currentUser);
//						}
//					}
//            	});
//          }
//        });
		
//		final IButton cancelButton = new IButton("ยกเลิกรายการขาย");
//		cancelButton.setIcon("icons/16/delete.png");
//		cancelButton.setWidth(120);
//		cancelButton.addClickHandler(new ClickHandler() {  
//            public void onClick(ClickEvent event) { 
//            	SC.confirm("ยืนยันการยกเลิกรายการขาย", "ต้องการยกเลิกรายการขาย หรือไม่?" , new BooleanCallback() {
//					@Override
//					public void execute(Boolean value) {
//						if (value) {
//								String invoice_id = record.getAttributeAsString("invoice_id");
//								ListGridRecord checkRecord = InvoiceData.getStatusRecords(invoice_id);
//								if (checkRecord.getAttributeAsString("status").equalsIgnoreCase("2_paid")) {
//									SC.warn("ลูกค้าชำระเงินแล้วไม่สามารถยกเลิกรายการขายได้");
//									return;
//								}
//								
//								ListGridRecord invRecord = InvoiceData.createStatusRecord(invoice_id, "4_canceled");
//								InvoiceDS.getInstance().updateData(invRecord, new DSCallback() {
//									@Override
//									public void execute(DSResponse dsResponse, Object data,
//											DSRequest dsRequest) {
//											record.setAttribute("status", "6_canceled");
//						            		//SaleOrderDS.getInstance().updateData(record);
//											SaleOrderDS.getInstance().updateData(record, new DSCallback() {
//												@Override
//												public void execute(DSResponse dsResponse, Object data,
//														DSRequest dsRequest) {
//														if (dsResponse.getStatus() != 0) {
//															SC.warn("การยกเลิกรายการขาย ล้มเหลว");
//														} else { 
//															SC.say("การยกเลิกรายการขาย เสร็จสมบูรณ์");
//															main.destroy();
//														}
//												}
//											});
//									}
//								});
//						}
//					}
//            	});
//          }
//        });
//		
//		final IButton approveButton = new IButton("อนุมัติ");
//		//approveButton.setIcon("icons/16/approved.png");
//		approveButton.setWidth(120);
//		approveButton.addClickHandler(new ClickHandler() {  
//            public void onClick(ClickEvent event) { 
//            	SC.confirm("ยืนยันการทำรายการ", "ต้องการอนุมัติใบเสนอราคา หรือไม่?" , new BooleanCallback() {
//					@Override
//					public void execute(Boolean value) {
//						if (value) {
//							//saveQuotation(main, quote_id, smithForm, orderListGrid, fromDate.getValueAsDate(), toDate.getValueAsDate(), deliveryDate.getValueAsDate(), currentUser);
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
//										if (smithForm.validate()) createSaleOrder(main, quote_id, smithForm, orderListGrid, deliveryDate.getValueAsDate(), currentUser, value);
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
		controls.addMember(printButton);
//		if (page == 1 && status.equals("1_waiting_for_production")) controls.addMember(cancelButton);
//		if (page == 1 && status.equals("3_production_completed")) controls.addMember(deliveryButton);
		controls.addMember(closeButton);
		layout.addMember(controls);
		
//		addButton.addClickHandler(new ClickHandler() {  
//            public void onClick(ClickEvent event) {
//            	//SC.warn("add click " + orderListGrid.getRecords().length);
//            	ArrayList<String> selected = new ArrayList<String>();
//            	for (ListGridRecord item : orderListGrid.getRecords()) {
//            		selected.add(item.getAttributeAsString("pid"));
//            	}
//            	addFunc.show(selected, orderListGrid, summaryForm);
//            }
//        });
//		
//		delButton.addClickHandler(new ClickHandler() {  
//            public void onClick(ClickEvent event) {
//            	ListGridRecord selected = orderListGrid.getSelectedRecord();
//            	if (selected != null) {
//            		//orderListGrid.removeSelectedData();
//            		selected.setAttribute("status", false);
//            		orderListGrid.updateData(selected);
//            		orderListGrid.removeSelectedData(new DSCallback() {
//						@Override
//						public void execute(DSResponse dsResponse, Object data,
//								DSRequest dsRequest) {
//								if (dsResponse.getStatus() != 0) {
//									SC.warn("การลบสินค้าล้มเหลว");
//								} else { 
//									summaryPriceRecalculate(orderListGrid.getRecords(), summaryForm);
//								}
//						}
//					}, null);
//            	} else {
//            		SC.warn("กรุณาเลือกรายการที่ต้องการลบ");
//            	}
//            }
//        });
		
//        orderListGrid.addCellSavedHandler(new CellSavedHandler() {  
//			@Override
//			public void onCellSaved(CellSavedEvent event) {
//				summaryPriceRecalculate(orderListGrid.getRecords(), summaryForm);
//			}  
//        });
        
//        orderListGrid.addRemoveRecordClickHandler(new RemoveRecordClickHandler() {
//
//			@Override
//			public void onRemoveRecordClick(RemoveRecordClickEvent event) {
//				//System.out.println("onRemoveRecordClick getResultSize " +orderListGrid.getResultSet().getResultSize());
//				System.out.println("onRemoveRecordClick getRecords " +orderListGrid.getRecords().length);
//				summaryPriceRecalculate(orderListGrid.getRecords(), summaryForm);
//			}
//        });
//        
//        orderListGrid.addFetchDataHandler(new FetchDataHandler() {
//
//			@Override
//			public void onFilterData(FetchDataEvent event) {
//				//System.out.println("onFilterData getResultSize " +orderListGrid.getResultSet().getResultSize());
//				System.out.println("onFilterData getRecords " +orderListGrid.getRecords().length);
//				if (orderListGrid.getRecords().length != 0) {
//					summaryPriceRecalculate(orderListGrid.getRecords(), summaryForm);
//				}
//			}
//        	
//        });
        
		return layout;
	}
	
//	public void summaryPriceRecalculate(ListGridRecord[] all, DynamicForm target){
//		Double sum_price = 0.0;
//		for (ListGridRecord record : all) {
//			sum_price += record.getAttributeAsDouble("sum_price");
//		}
//		NumberFormat nf = NumberFormat.getFormat("#,##0.00");
//		target.getField("netExclusive").setValue(nf.format(sum_price));
//		target.getField("tax").setValue(nf.format(sum_price * 0.07));
//		target.getField("netInclusive").setValue(nf.format(sum_price * 1.07));
//	}
//	
//	public void saveQuotation(final Window main, final String quote_id, DynamicForm customer, ListGrid orderListGrid, Date from, Date to, Date delivery, User currentUser){
//		ListGridRecord[] all = orderListGrid.getRecords();
//		
//		if (all.length == 0) {
//			SC.warn("กรูณาเลือกรายการสินค้าอย่างน้อย 1 รายการ");
//			return;
//		}
//		
//		Double total_weight = 0.0;
//		Double total_netExclusive = 0.0;
//		Integer total_amount = 0;
//		//final String quote_id = "QA70" + Math.round((Math.random() * 100)) + Math.round((Math.random() * 100));
//		final ArrayList<QuoteProductDetails> productList = new ArrayList<QuoteProductDetails>();
//		
//		for (ListGridRecord item : all){
//			total_weight += item.getAttributeAsDouble("weight");
//			total_amount += item.getAttributeAsInt("quote_amount");
//			total_netExclusive += item.getAttributeAsDouble("sum_price");
//			
//			String sub_quote_id = item.getAttributeAsString("sub_quote_id");
//			String pid = item.getAttributeAsString("pid");
//			String pname = item.getAttributeAsString("name");
//			String ptype = item.getAttributeAsString("type");
//			String psize = item.getAttributeAsString("size");
//			Double pweight = item.getAttributeAsDouble("weight");
//			Integer pquote_amount = item.getAttributeAsInt("quote_amount");
//			String punit = item.getAttributeAsString("unit");
//			Double pprice = item.getAttributeAsDouble("price");
//			QuoteProductDetails temp = new QuoteProductDetails();
//			temp.save(pid, pname, psize, pweight, pprice, ptype, punit);
//			temp.setID(sub_quote_id, quote_id);
//			temp.setQuantity(pquote_amount);
//			productList.add(temp);
//		}
//		//System.out.println(total_weight + " " + total_amount + " " + total_netExclusive);
//			//status
//			final String quote_status = "2_waiting_for_approved";
//			
//			if (customer.getField("cid").getValue() == null || customer.getField("smith_name").getValue() == null) {
//				SC.warn("ชื่อและรหัสลูกค้าไม่ถุกต้อง");
//				return;
//			}
//			
//			String cid = (String) customer.getField("cid").getValue();
//			String smith_name = (String) customer.getField("smith_name").getValue();
//			String payment_model = (String) customer.getField("payment_model").getValue();
//			Integer credit = (Integer) customer.getField("credit").getValue();
//			//System.out.println(cid + " " + smith_name + " " + payment_model + " " + credit);
//			
//			ListGridRecord updateRecord = QuotationData.createUpdateRecord(quote_id, cid, smith_name, payment_model, credit, from, to, delivery, total_weight, total_amount, total_netExclusive, new Date(), currentUser.getFirstName() + " " + currentUser.getLastName(), "", quote_status);
//			
//			QuotationDS.getInstance().updateData(updateRecord, new DSCallback() {
//				@Override
//				public void execute(DSResponse dsResponse, Object data,
//						DSRequest dsRequest) {
//						//System.out.println("Test " + dsResponse.getStatus());
//						if (dsResponse.getStatus() != 0) {
//							SC.warn("การบันทึกใบเสนอราคาล้มเหลว กรุณาทำรายการใหม่อีกครั้ง");
//							main.destroy();
//						} else { 
//							for (QuoteProductDetails item : productList) {
//								if (item.sub_quote_id == null) {
//									item.sub_quote_id = "QS80" + Math.round((Math.random() * 100)) + Math.round((Math.random() * 100));
//									ListGridRecord subUpdateRecord = QuoteProductData.createRecord(item);
//									QuoteProductDS.getInstance(quote_id).addData(subUpdateRecord);
//								} else  {
//									ListGridRecord subUpdateRecord = QuoteProductData.createRecord(item);
//									QuoteProductDS.getInstance(quote_id).updateData(subUpdateRecord);
//								}
//							}
//							SC.warn("แก้ไขใบเสนอราคาเสร็จสิ้น <br> " + "รหัสใบเสนอราคา " + quote_id + "<br> สถานะของใบเสนอราคา " + quote_status);
//							main.destroy();
//						}
//				}
//			});
//	}
//	
//	void updateQuoteStatus(String quote_id, final String status, String comment) {
//		Record updated = QuotationData.createStatusRecord(quote_id,status,comment);
//		QuotationDS.getInstance().updateData(updated, new DSCallback() {
//			@Override
//			public void execute(DSResponse dsResponse, Object data,
//					DSRequest dsRequest) {
//					if (dsResponse.getStatus() != 0) {
//						SC.warn("การอนุมัติใบเสนอราคาล้มเหลว");
//					} else { 
//						SC.warn("แก้ไขสถานะใบเสนอราคา \"" + status + "\" เสร็จสิ้น");
//					}
//			}
//		});
//	}

	public void createCreateOrder(DynamicForm planForm, ListGrid orderListGrid, Date sent_date, Date due_date, final User currentUser, final ListGridRecord previousRecord) {
		ListGridRecord[] all = orderListGrid.getRecords();
		
		final String plan_id = (String) planForm.getField("plan_id").getValue();
		
		final String job_id = "JOB70" + Math.round((Math.random() * 100)) + Math.round((Math.random() * 100));
		
		Double total_sent_weight = 0.0;
		Integer total_sent_amount = 0;
		final ArrayList<ListGridRecord> orderProductList = new ArrayList<ListGridRecord>();
		final HashMap<String, MaterialRequestItemDetails> matRequest = new HashMap<String, MaterialRequestItemDetails>();
		
		for (ListGridRecord item : all){
			
			String pid = item.getAttributeAsString("pid");
			String name = item.getAttributeAsString("name");
			String type = item.getAttributeAsString("type");
			String unit = item.getAttributeAsString("unit");
			String details = item.getAttributeAsString("details");
			Double recv_weight = item.getAttributeAsDouble("recv_weight");
			Integer sent_amount = item.getAttributeAsInt("recv_amount");

			total_sent_amount += sent_amount;

			ProcessListDS.getInstance(pid).refreshData();
			Record[] selectedProcess = ProcessListDS.getInstance(pid).applyFilter(ProcessListDS.getInstance(pid).getCacheData(), new Criterion("type", OperatorId.EQUALS, "2_scrape"));
			Record process = selectedProcess[0];
			String psid = process.getAttributeAsString("psid");
			String desc = process.getAttributeAsString("desc");
			Double sent_weight =  process.getAttributeAsDouble("weight") * sent_amount; 

			total_sent_weight += sent_weight + recv_weight;
			
			//if (desc != null && !desc.equals("")) details += "(" + desc + ")";
			
			final String sub_job_id = "SJ70" + Math.round((Math.random() * 100)) + Math.round((Math.random() * 100));
			ListGridRecord temp = ScrapingProductData.createSentRecord(sub_job_id, job_id, pid, name, type, unit, details, desc, sent_weight + recv_weight, sent_amount, true);
			orderProductList.add(temp);
			
			MaterialProcessDS.getInstance(psid, pid).refreshData();
			Record[] selectedMaterialProcess = MaterialProcessDS.getInstance(psid, pid).getCacheData();
			
			for (Record mat : selectedMaterialProcess) {
				String cm_id = "SM70" + Math.round((Math.random() * 100)) + Math.round((Math.random() * 100));
				String mid = mat.getAttributeAsString("mid");
				
				Record[] materail = MaterialDS.getInstance().applyFilter(MaterialDS.getInstance().getCacheData(), new Criterion("mid", OperatorId.EQUALS, mid));
				Double remain = materail[0].getAttributeAsDouble("remain");
						
				ScrapingMaterialDS.getInstance(sub_job_id, job_id).addData(ScrapingMaterialData.createRecord(sub_job_id, cm_id,  sent_amount, mat));
				
				if (matRequest.containsKey(mid)) {
					matRequest.get(mid).addAmount(mat, sent_amount);
				} else {
					matRequest.put(mid, new MaterialRequestItemDetails(mat, sent_amount, remain));
				}
			}
		}
		
		//Required
		ArrayList<MaterialRequestItemDetails> outOfStock = checkStock(matRequest);
		if (outOfStock.size() != 0) {
			String msg = "การสร้างคำสั่งผลิตล้มเหลว ปริมาณวัตถุดิบไม่เพียงพอต่อการผลิต";
			for (MaterialRequestItemDetails item : outOfStock){
				msg += "<br>	ต้องการ " + item.material_name + " จำนวน " + item.request_amount + " " + item.material_unit + " คงเหลือ " + item.remain + " " + item.material_unit; 
			}
			SC.warn(msg);
			return;
		}
		//End
		
		String status = "1_on_production";
		if (matRequest.size() != 0) {
			status = "0_request_mat";
		}
		final ListGridRecord jobOrder = ScrapingData.createSentRecord(job_id, plan_id, smith, sent_date, due_date, total_sent_weight, total_sent_amount, new Date(), null, currentUser.getFirstName() + " " + currentUser.getLastName(), "", "", status);
		
		ScrapingDS.getInstance().addData(jobOrder, new DSCallback() {
		@Override
		public void execute(DSResponse dsResponse, Object data,
				DSRequest dsRequest) {
				if (dsResponse.getStatus() != 0) {
					SC.warn("การสร้างคำสั่งผลิตล้มเหลว กรุณาทำรายการใหม่อีกครั้ง");
					editWindow.destroy();
				} else { 
					for (ListGridRecord item : orderProductList) {
						System.out.println("Debug : " + item.getAttributeAsString("sub_job_id"));
						ScrapingProductDS.getInstance(job_id).addData(item);
					}
					
					SC.showPrompt("กำลังบันทึกข้อมูล");
					
					String order_status = "3_to_next_process";
					ListGridRecord update_order = CastingData.createStatusRecord(new Date(), currentUser.getFirstName() + " " + currentUser.getLastName(), "เสร็จสิ้นขั้นตอนแล้ว", order_status, previousRecord);
					CastingDS.getInstance().updateData(update_order, new DSCallback() {

						@Override
						public void execute(DSResponse dsResponse, Object data,
								DSRequest dsRequest) {
							// TODO Auto-generated method stub
							String message = "สร้างคำสั่งเสร็จสิ้น เลขที่คำสั่งผลิต " + job_id;
							if (matRequest.size() != 0) {
								final String request_id = "MR70" + Math.round((Math.random() * 100)) + Math.round((Math.random() * 100));
								createMaterialRequest(request_id, job_id, smith, currentUser.getFirstName() + " " + currentUser.getLastName(), matRequest);
								message = "สร้างคำสั่งเสร็จสิ้น เลขที่คำสั่งผลิต " + job_id + " <br> สร้างรายการขอเบิกวัตถุดิบ เลขที่ " + request_id;
							}
							SC.clearPrompt();
							
//							SC.say(message, new BooleanCallback(){
//								@Override
//								public void execute(Boolean value) {
//									if (value) {
//										editWindow.destroy();
//									}
//								}
//							} );
							
							printDialog(jobOrder, currentUser, message);
						}
						
					});

//					String message = "สร้างคำสั่งเสร็จสิ้น เลขที่คำสั่งผลิต " + job_id;
//					if (matRequest.size() != 0) {
//						final String request_id = "MR70" + Math.round((Math.random() * 100)) + Math.round((Math.random() * 100));
//						createMaterialRequest(request_id, job_id, smith, currentUser.getFirstName() + " " + currentUser.getLastName(), matRequest);
//						message = "สร้างคำสั่งเสร็จสิ้น เลขที่คำสั่งผลิต " + job_id + " <br> สร้างรายการขอเบิกวัตถุดิบ เลขที่ " + request_id;
//					}
//					
//					SC.say(message, new BooleanCallback(){
//						@Override
//						public void execute(Boolean value) {
//							if (value) {
//								editWindow.destroy();
//							}
//						}
//					} );
				}
			}
		});
	}
	
	public void createMaterialRequest(String request_id, String job_id, Smith smith,String user, HashMap<String, MaterialRequestItemDetails> matRequest){
		Double total_request_amount = 0.0;
		for (MaterialRequestItemDetails item : matRequest.values()) {
			total_request_amount += item.getAmount();
			final String sub_request_id = "SMR70" + Math.round((Math.random() * 100)) + Math.round((Math.random() * 100));
			ListGridRecord newRecord = MaterialRequestItemData.createRecord(sub_request_id, request_id, item);
			newRecord.setAttribute("request_date", new Date());
			MaterialRequestItemDS.getInstance(request_id).addData(newRecord);
			//For report
			MaterialUsedReportDS.getInstance().addData(newRecord);
			
			MaterialDS.getInstance().refreshData();
			Record[] updated_records = MaterialDS.getInstance().applyFilter(MaterialDS.getInstance().getCacheData(), new Criterion("mid", OperatorId.EQUALS, item.material_id));
			Record updated = updated_records[0];
			Double remain = updated.getAttributeAsDouble("remain") - item.getAmount();
			updated.setAttribute("remain", remain);
			Double reserved = updated.getAttributeAsDouble("reserved") + item.getAmount();
			updated.setAttribute("reserved", reserved);
			MaterialDS.getInstance().updateData(updated);
		}
		
		ListGridRecord newRecord = MaterialRequestData.createRecord(request_id, job_id, smith, "2_scrape", new Date(), total_request_amount, new Date(), null, user, null, "1_requested");
		MaterialRequestDS.getInstance().addData(newRecord);
	}
	
	private ListGrid getListGrid() {
		return new ListGrid() {  
//            public DataSource getRelatedDataSource(ListGridRecord record) {  
//                //return new CastingMaterialDS(record.getAttributeAsString("psid"), DS.pid);
//                return ScrapingMaterialDS.getInstance(record.getAttributeAsString("sub_job_id"), record.getAttributeAsString("job_id"));
//            }  
  
            @Override  
            protected Canvas getExpansionComponent(final ListGridRecord record) {  
  
                final ListGrid grid = this;  
  
                VLayout layout = new VLayout(5);  
                layout.setPadding(5);  
  
                SectionStack sectionStack = new SectionStack();
            	sectionStack.setWidth(525);
            	sectionStack.setHeight(100);
            	SectionStackSection section = new SectionStackSection("รายการวัตถุดิบ");
            	section.setCanCollapse(false);
                section.setExpanded(true);
                
                final ListGrid materialGrid = new ListGrid();  
                materialGrid.setWidth(525);  
                materialGrid.setHeight(100);  
                materialGrid.setCellHeight(22);  
                
                String pid = record.getAttributeAsString("pid");
    			Integer sent_amount = record.getAttributeAsInt("recv_amount");
    			
    			ProcessListDS.getInstance(pid).refreshData();
    			Record[] selectedProcess = ProcessListDS.getInstance(pid).applyFilter(ProcessListDS.getInstance(pid).getCacheData(), new Criterion("type", OperatorId.EQUALS, "2_scrape"));
    			Record process = selectedProcess[0];
    			String psid = process.getAttributeAsString("psid");
    			MaterialProcessDS.getInstance(psid, pid).refreshData();
    			Record[] selectedMaterialProcess = MaterialProcessDS.getInstance(psid, pid).getCacheData();
    			
    			//System.out.println("MaterialList for " + pid);
    			//System.out.println("MaterialList size : " + selectedMaterialProcess.length);
    			ArrayList<ListGridRecord> materialList = new ArrayList<ListGridRecord>();
    			for (Record material : selectedMaterialProcess) {
    				materialList.add(CastingMaterialData.createRecord(material, sent_amount));
    			}
    			
                materialGrid.setRecords(materialList.toArray(new ListGridRecord[]{}));
  
                ListGridField Field_1 = new ListGridField("mid", 150);
                Field_1.setTitle("รหัสวัตถุดิบ");
                ListGridField Field_2 = new ListGridField("mat_name", 200);
                Field_2.setTitle("ชื่อวัตถุดิบ");
                ListGridField editField = new ListGridField("produce_amount", 120);
                editField.setTitle("ปริมาณที่ใช้ผลิต");
                editField.setCellFormatter(FieldFormatter.getNumberFormat());
                ListGridField Field_3 = new ListGridField("unit", 50);
                Field_3.setTitle("หน่วย");
                materialGrid.setFields(Field_1, Field_2, editField, Field_3);
                
	    	    section.setItems(materialGrid);
	    	    sectionStack.setSections(section);
    	      
                layout.addMember(sectionStack);
  
                return layout;
            }  
		};
	}
	
	private ArrayList<MaterialRequestItemDetails> checkStock(HashMap<String, MaterialRequestItemDetails> matRequest){
		ArrayList<MaterialRequestItemDetails> outOfStock = new ArrayList<MaterialRequestItemDetails>();
		for (MaterialRequestItemDetails item : matRequest.values()) {
			if (item.request_amount > item.remain) outOfStock.add(item);
		}
		return outOfStock;
	}
	
	private void printDialog(final ListGridRecord jobOrder, final User currentUser, String message){
		message += "<br><br> ต้องการพิมพ์คำสังผลิตหรือไม่?" ;
		SC.confirm(message, new BooleanCallback(){
		@Override
		public void execute(Boolean value) {
			if (value != null && value) {
//				VLayout printLayout = new VLayout(10);
//            	printLayout.addMember(new PrintHeader("ใบสั่งผลิต"));
//            	printLayout.addMember(layout);
//            	Canvas.showPrintPreview(printLayout);
				ScrapingPrintWindow printWindow = new ScrapingPrintWindow();
				printWindow.show(jobOrder, false, currentUser, 1);
				editWindow.destroy();
			} else {
				editWindow.destroy();
			}
		}
		} );
}
}
