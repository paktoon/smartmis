package com.smart.mis.client.function.financial.disburse.wage;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.smart.mis.client.function.financial.disburse.wage.WageDS;
import com.smart.mis.client.function.financial.disburse.wage.WageItemDS;
import com.smart.mis.shared.EditorWindow;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.FieldVerifier;
import com.smart.mis.shared.ListGridNumberField;
import com.smart.mis.shared.PrintHeader;
import com.smart.mis.shared.financial.WagePaymentStatus;
import com.smart.mis.shared.prodution.ProcessStatus;
import com.smart.mis.shared.prodution.ProcessType;
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
import com.smartgwt.client.types.ListGridFieldType;
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
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class WageViewWindow extends EditorWindow{

	Smith smith;
	Window editWindow;
	Double total_received_weight;
	Integer total_received_amount;
	Double total_paid_wage;
	Double total_return_mat;
	
	public void show(ListGridRecord record, boolean edit, User currentUser, int page){
		smith = new Smith();
		editWindow = new Window();
		if (page == 2) {
		editWindow.setTitle("บันทึกชำระค่าจ้างผลิต");
		} else editWindow.setTitle("ข้อมูลรายการชำระค่าจ้างผลิต");
		editWindow.setWidth(850);  
		editWindow.setHeight(540);
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
		layout.setWidth(820);
		layout.setHeight(520);
		layout.setMargin(10);

		String wage_id = record.getAttributeAsString("wage_id");
		String job_id = record.getAttributeAsString("job_id");
		String status = record.getAttributeAsString("status");
		
		String created_by = record.getAttributeAsString("created_by");
		Date created_date = record.getAttributeAsDate("created_date");
		
		Double recv_weight = record.getAttributeAsDouble("total_recv_weight");
		Integer recv_amount = record.getAttributeAsInt("total_recv_amount");
		final Double job_wage = record.getAttributeAsDouble("total_wage");
		
		final DynamicForm orderForm = new DynamicForm();
		orderForm.setWidth100(); 
		orderForm.setHeight(30);
		orderForm.setMargin(5);
		orderForm.setIsGroup(true);
		orderForm.setNumCols(6);
		orderForm.setGroupTitle("ข้อมูลค่าจ้างผลิต");

		StaticTextItem jid = new StaticTextItem("wage_id", "รหัสค่าจ้างผลิต");
		StaticTextItem qid = new StaticTextItem("job_id", "รหัสคำสั่งผลิต");
		//StaticTextItem ddate = new StaticTextItem("delivery", "วันที่กำหนดส่งสินค้า");
		StaticTextItem sts = new StaticTextItem("status", "สถานะ");
		StaticTextItem cby = new StaticTextItem("created_by", "สร้างโดย");
		StaticTextItem cdate = new StaticTextItem("created_date", "สร้างเมื่อ");
		jid.setValue(wage_id);
		qid.setValue(job_id);
//		if (delivery != null ) ddate.setValue(DateTimeFormat.getFormat("MM/dd/yyy").format(delivery));
//		else ddate.setValue("-");
		sts.setValue(WagePaymentStatus.getDisplay(status));
		cby.setValue(created_by);
		cdate.setValue(DateTimeFormat.getFormat("MM/dd/yyy").format(created_date));
		orderForm.setFields(jid, qid, sts, cdate ,cby);
		orderForm.setColWidths(100,80,120,80,80,120);
		layout.addMember(orderForm);
		
		final DynamicForm smithForm = new DynamicForm();
		smithForm.setWidth100(); 
		smithForm.setHeight(30);
		smithForm.setMargin(5); 
		smithForm.setNumCols(6); 
		smithForm.setCellPadding(2);
		smithForm.setAutoFocus(true);
		smithForm.setSelectOnFocus(true);
		//smithForm.setDataSource(CastingDS.getInstance());
		//smithForm.setUseAllDataSourceFields(false);
		smithForm.setIsGroup(true);
		//smithForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
		smithForm.setGroupTitle("ข้อมูลช่าง");
		
		String smid = record.getAttributeAsString("smid");
		String sname = record.getAttributeAsString("sname");
		String semail = record.getAttributeAsString("semail");
		String sphone1 = record.getAttributeAsString("sphone1");
		String sphone2 = record.getAttributeAsString("sphone2");
		String saddress = record.getAttributeAsString("saddress");
		String stype = record.getAttributeAsString("stype");
		
		StaticTextItem smith_id = new StaticTextItem("smid", "รหัสช่าง");
		smith_id.setValue(smid);
		StaticTextItem smith_name = new StaticTextItem("name", "ชื่อช่าง");
		smith_name.setValue(sname);
		StaticTextItem smith_type = new StaticTextItem("type", "ประเภทงาน");
		smith_type.setValue(stype);
		StaticTextItem smith_phone1 = new StaticTextItem("phone1", "โทรศัทท์ 1");
		smith_phone1.setValue(sphone1);
	    StaticTextItem smith_phone2 = new StaticTextItem("phone2", "โทรศัทท์ 2");
	    smith_phone2.setValue(sphone2);
	    StaticTextItem smith_email = new StaticTextItem("email", "อีเมล");
	    smith_email.setValue(semail);
	    StaticTextItem smith_address = new StaticTextItem("address", "ที่อยู่");
	    smith_address.setValue(saddress);
	    smith_address.setColSpan(4);
	        
	    smithForm.setFields(smith_name, smith_id, smith_type,smith_email, smith_phone1, smith_phone2 , smith_address);  

		smithForm.setColWidths(100,150,80,80,80,150);
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
//    	section.setCanCollapse(false);  
//        section.setExpanded(true);
        
//        IButton addButton = new IButton("เพิ่มสินค้า");
//		addButton.setIcon("[SKIN]actions/add.png");
//        IButton delButton = new IButton("ลบสินค้า");
//        delButton.setIcon("icons/16/delete.png");
//        if (edit) {
//			section.setControls(addButton, delButton);
//        }
		
        //HLayout itemLayout = new HLayout();
		final ListGrid orderListGrid = new ListGrid();
		orderListGrid.setHeight(230);
		orderListGrid.setAlternateRecordStyles(true);
		orderListGrid.setShowAllRecords(true);
		orderListGrid.setAutoFetchData(true);  
		if (!edit) orderListGrid.setSelectionType(SelectionStyle.NONE);
		else orderListGrid.setSelectionType(SelectionStyle.SINGLE);
		orderListGrid.setCanResizeFields(false);
		orderListGrid.setShowGridSummary(true);
		orderListGrid.setEditEvent(ListGridEditEvent.CLICK);  
//		orderListGrid.setListEndEditAction(RowEndEditAction.NEXT);
		orderListGrid.setShowRowNumbers(true);
//		orderListGrid.setCanExpandRecords(true);
        final Criterion ci = new Criterion("status", OperatorId.EQUALS, true);
		orderListGrid.setCriteria(ci);
//		if (edit) {
//			orderListGrid.setCanRemoveRecords(true);
//			orderListGrid.setWarnOnRemoval(true);
//			orderListGrid.setWarnOnRemovalMessage("คุณต้องการลบ รายการสินค้า หรือไม่?");
//		}
		
		WageItemDS tempView = WageItemDS.getInstance(wage_id);
		tempView.refreshData();
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
        
//        ListGridNumberField quoteItemCell_4 = new ListGridNumberField("sent_weight", 120);
//        quoteItemCell_4.setSummaryFunction(SummaryFunctionType.SUM);
//        quoteItemCell_4.setShowGridSummary(true);
//        quoteItemCell_4.setIncludeInRecordSummary(false);
//        quoteItemCell_4.setIncludeInRecordSummary(false);
//        
//        ListGridNumberField quoteItemCell_5 = new ListGridNumberField("sent_amount", 120);
//        quoteItemCell_5.setSummaryFunction(SummaryFunctionType.SUM);
//        quoteItemCell_5.setShowGridSummary(true);
//        quoteItemCell_5.setIncludeInRecordSummary(false);
        
        ListGridNumberField quoteItemCell_6 = new ListGridNumberField("recv_weight", 120);
        quoteItemCell_6.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_6.setCellFormatter(FieldFormatter.getNumberFormat());
        quoteItemCell_6.setType(ListGridFieldType.FLOAT); 
        quoteItemCell_6.setShowGridSummary(true);
        //if (edit)  quoteItemCell_6.setCanEdit(true);
        quoteItemCell_6.setIncludeInRecordSummary(false);
        
        ListGridNumberField quoteItemCell_7 = new ListGridNumberField("recv_amount", 120);
        quoteItemCell_7.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_7.setCellFormatter(FieldFormatter.getNumberFormat());
        quoteItemCell_7.setType(ListGridFieldType.FLOAT);
        quoteItemCell_7.setShowGridSummary(true);
        //if (edit)  quoteItemCell_7.setCanEdit(true);
        
        ListGridNumberField quoteItemCell_8 = new ListGridNumberField("wage", 130);
        //quoteItemCell_6.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_8.setCellFormatter(FieldFormatter.getPriceFormat());
        //quoteItemCell_8.setType(ListGridFieldType.FLOAT); 
        quoteItemCell_6.setShowGridSummary(false);
        //if (edit) quoteItemCell_8.setCanEdit(true);
        
//        ListGridSummaryField quoteItemCell_sum = new ListGridSummaryField("sum_wage", 120);
//        quoteItemCell_sum.setRecordSummaryFunction(RecordSummaryFunctionType.MULTIPLIER);
//        quoteItemCell_sum.setSummaryFunction(SummaryFunctionType.SUM);
//        quoteItemCell_sum.setShowGridSummary(true);
//        quoteItemCell_sum.setCellFormatter(FieldFormatter.getPriceFormat());
//        quoteItemCell_sum.setAlign(Alignment.RIGHT);
        ListGridField quoteItemCell_sum = new ListGridField("sum_wage", 120);
//        quoteItemCell_sum.setRecordSummaryFunction(RecordSummaryFunctionType.MULTIPLIER);
        quoteItemCell_sum.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_sum.setShowGridSummary(true);
        quoteItemCell_sum.setCellFormatter(FieldFormatter.getPriceFormat());
        quoteItemCell_sum.setAlign(Alignment.RIGHT);
 
        //orderListGrid.setFields(quoteItemCell_1, quoteItemCell_2, quoteItemCell_4, quoteItemCell_5, quoteItemCell_3, quoteItemCell_6, quoteItemCell_7, quoteItemCell_3, quoteItemCell_8, quoteItemCell_sum);
        orderListGrid.setFields(quoteItemCell_1, quoteItemCell_2 , quoteItemCell_6, quoteItemCell_7, quoteItemCell_3, quoteItemCell_8, quoteItemCell_sum);
        
        //itemLayout.addMember(orderListGrid);
        section.setItems(orderListGrid);
        sectionStack.setSections(section);
		layout.addMember(sectionStack);
		
		HLayout footerLayout = new HLayout();
		footerLayout.setHeight(100);
		
//		final DynamicForm dateForm = new DynamicForm();
//		dateForm.setWidth(300);
//		dateForm.setHeight(75);
//		dateForm.setNumCols(2);
//		dateForm.setMargin(5);
//		dateForm.setIsGroup(true);
//		dateForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
//		dateForm.setGroupTitle("ข้อกำหนดคำสั่งผลิต");
//		dateForm.setCanEdit(false);
		
		//DateRange dateRange = new DateRange();  
	    //dateRange.setRelativeStartDate(RelativeDate.TODAY);
	    //dateRange.setRelativeEndDate(new RelativeDate("+"+std_time+"d"));
		
//		final DateItem sentDate = new DateItem();
//		sentDate.setName("sent_date");
//		sentDate.setTitle("วันที่สั่งผลิต");
//		sentDate.setUseTextField(true);
//		sentDate.setDefaultValue(sent_date);
//		sentDate.setCanEdit(false);
//		
//		final DateItem dueDate = new DateItem();
//		dueDate.setName("due_date");
//		dueDate.setTitle("วันที่กำหนดรับของ");
//		dueDate.setUseTextField(true);
//		dueDate.setDefaultValue(due_date);
//		dueDate.setCanEdit(false);
//		
//		final DateItem receivedDate = new DateItem();
//		receivedDate.setName("received_date");
//		receivedDate.setTitle("วันที่บันทึกรับสินค้า");
//		receivedDate.setUseTextField(true);
//		if (page == 2) receivedDate.setDefaultValue(new Date());
//		else if (page == 1) receivedDate.setDefaultValue(received_date);
//		receivedDate.setCanEdit(false);
		
//		dateForm.setFields(sentDate, dueDate, receivedDate);
//		dateForm.setColWidths(130,80);
		//dateForm.editRecord(record);
//		footerLayout.addMember(dateForm);
		//******************End
		
		//******************Summary
//		final DynamicForm summaryForm_1 = new DynamicForm();
//		summaryForm_1.setWidth(200);
//		summaryForm_1.setHeight(75);
//		summaryForm_1.setNumCols(2);
//		summaryForm_1.setMargin(5);
//		summaryForm_1.setIsGroup(true);
//		summaryForm_1.setGroupTitle("สรุปยอดสั่งผลิต");
//		summaryForm_1.setColWidths(120, 80);
//		final NumberFormat nf = NumberFormat.getFormat("#,##0.00");
//		final StaticTextItem total_sent_weight = new StaticTextItem("total_sent_weight");
//		total_sent_weight.setValue(nf.format(sent_weight));
//		final StaticTextItem total_sent_amount = new StaticTextItem("total_sent_amount");
//		total_sent_amount.setValue(nf.format(sent_amount));
//		total_sent_weight.setWidth(100);
//		total_sent_amount.setWidth(100);
//		total_sent_weight.setTitle("น้ำหนักรวม");
//		total_sent_amount.setTitle("จำนวนรวม");
//		total_sent_weight.setTextAlign(Alignment.RIGHT);
//		total_sent_amount.setTextAlign(Alignment.RIGHT);
//		total_sent_weight.setHint("กรัม");
//		total_sent_amount.setHint("ชิ้น");
//		summaryForm_1.setFields(total_sent_amount, total_sent_weight);
//		//summaryForm.editRecord(record);
//		footerLayout.addMember(summaryForm_1);
		
		final DynamicForm summaryForm_2 = new DynamicForm();
		summaryForm_2.setWidth100();
		//summaryForm_2.setHeight(30);
		summaryForm_2.setNumCols(6);
		summaryForm_2.setMargin(5);
		summaryForm_2.setIsGroup(true);
		summaryForm_2.setGroupTitle("สรุปยอดสั่งผลิตสินค้า");
		//summaryForm_2.setColWidths(100, 100, 100, 100, 100, 100);
		final NumberFormat nf = NumberFormat.getFormat("#,##0.00");
		final StaticTextItem total_recv_weight = new StaticTextItem("total_recv_weight");
		if (recv_weight == null) {
			total_recv_weight.setDefaultValue(nf.format(0));
		} else {
			total_recv_weight.setDefaultValue(nf.format(recv_weight));
		}
		final StaticTextItem total_recv_amount = new StaticTextItem("total_recv_amount");
		if (recv_amount == null) {
			total_recv_amount.setDefaultValue(nf.format(0));
		} else {
			total_recv_amount.setDefaultValue(nf.format(recv_amount));
		}
		total_recv_weight.setWidth(100);
		total_recv_amount.setWidth(100);
		total_recv_weight.setTitle("น้ำหนักรวม");
		total_recv_amount.setTitle("จำนวนรวม");
		total_recv_weight.setTextAlign(Alignment.RIGHT);
		total_recv_amount.setTextAlign(Alignment.RIGHT);
		total_recv_weight.setHint("กรัม");
		total_recv_amount.setHint("ชิ้น");
		final StaticTextItem total_wage = new StaticTextItem("total_wage");
		if (job_wage == null) {
			total_wage.setDefaultValue(nf.format(0));
		} else {
			total_wage.setDefaultValue(nf.format(job_wage));
		}
		total_wage.setTitle("ค่าจ้างผลิตรวม");
		total_wage.setTextAlign(Alignment.RIGHT);
		total_wage.setHint("บาท");
		summaryForm_2.setFields(total_recv_amount, total_recv_weight, total_wage);
		footerLayout.addMember(summaryForm_2);
		
//        final DynamicForm summaryForm_3 = new DynamicForm();
//        summaryForm_3.setWidth(300);
//        summaryForm_3.setHeight(75);
//        summaryForm_3.setNumCols(2);
//        summaryForm_3.setMargin(5);
//        summaryForm_3.setIsGroup(true);
//        summaryForm_3.setGroupTitle("สรุปค่าจ้างผลิต");
//        summaryForm_3.setColWidths(80, 70);
//		final StaticTextItem return_mat = new StaticTextItem("return_mat");
//		if (mat_return == null) {
//			return_mat.setDefaultValue(nf.format(0));
//		} else {
//			return_mat.setDefaultValue(nf.format(mat_return));
//		}
		
//		wage_per_gam.addChangedHandler(new ChangedHandler() {
//
//			@Override
//			public void onChanged(ChangedEvent event) {
//				if (wage_per_gam.validate()) {
//					total_paid_wage = wage_per_gam.getValueAsFloat() * ((Double) total_recv_weight.getValue());
//					total_wage.setValue(nf.format(total_paid_wage));
//				}
//			}});
		
//		orderListGrid.addCellSavedHandler(new CellSavedHandler() {  
//			@Override
//			public void onCellSaved(CellSavedEvent event) {
//				summaryRecalculate(orderListGrid.getRecords(), summaryForm_2, summaryForm_3, sent_weight);
//			}  
//        });
//		
//		footerLayout.addMember(summaryForm_3);
		
		layout.addMember(footerLayout);
		
		//Control
		HLayout controls = new HLayout();
		controls.setAlign(Alignment.CENTER);
		controls.setMargin(5);
		controls.setMembersMargin(5);
		final IButton saveButton = new IButton("บันทึกชำระค่าจ้างผลิต");
		saveButton.setIcon("icons/16/save.png");
		saveButton.setWidth(150);
		saveButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
				final Window confirm = new Window();
				confirm.setTitle("รายละเอียดการชำระค่าจ้างผลิต");
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
				//need_payment.setValue(nf.format(job_wage * 1.07));
				need_payment.setValue(nf.format(job_wage));
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
		            	if (!receiptForm.validate() || paid_payment.getValueAsDouble() < (job_wage)) {
		            		SC.warn("ข้อมูลไม่ถูกต้อง กรุณาตรวจสอบข้อมูลใหม่อีกครั้ง");
		            		paid_payment.clearValue();
		            		return;
		            	}
		            	
		            	SC.confirm("ยืนยันการบันทึกจ่ายชำระเงิน", "ต้องการบันทึกจ่ายชำระเงินหรือไม่?" , new BooleanCallback() {
							@Override
							public void execute(Boolean value) {
								if (value) {
					            		record.setAttribute("status", "2_paid");
										record.setAttribute("paidInclusive", paid_payment.getValueAsDouble());
					            		record.setAttribute("paid_date", new Date());
					            		record.setAttribute("paid_by", currentUser.getFirstName() + " " + currentUser.getLastName());
					            		WageDS.getInstance().updateData(record, new DSCallback() {
											@Override
											public void execute(DSResponse dsResponse, Object data,
													DSRequest dsRequest) {
													if (dsResponse.getStatus() != 0) {
														SC.warn("การบันทึกจ่ายชำระเงิน ล้มเหลว");
													} else { 
														SC.warn("การบันทึกจ่ายชำระเงิน เสร็จสมบูรณ์");
														confirm.destroy();
														
														SC.confirm("พิมพ์ใบสำคัญจ่ายค่าจ้างวัตถุดิบ", "ต้องการพิมพ์ใบสำคัญจ่ายค่าจ้างวัตถุดิบหรือไม่?" , new BooleanCallback() {

															@Override
															public void execute(
																	Boolean value) {
																	if(value) {
																		VLayout printLayout = new VLayout(10);
														            	printLayout.addMember(new PrintHeader("ใบสำคัญจ่ายค่าจ้างผลิต"));
														            	printLayout.addMember(layout);
														            	Canvas.showPrintPreview(printLayout);
																	}
													            	main.destroy();
															}});
														//main.destroy();
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
		
		final IButton printButton = new IButton("พิมพ์ใบสำคัญจ่าย");
		printButton.setIcon("icons/16/print.png");
		printButton.setWidth(150);
		printButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
                //SC.say("Click print");
                VLayout printLayout = new VLayout(10);
            	printLayout.addMember(new PrintHeader("ใบสำคัญจ่ายค่าจ้างผลิต"));
            	printLayout.addMember(layout);
            	Canvas.showPrintPreview(printLayout);
            	main.destroy();
          }
        });
		// if (edit || !status.equals("3_approved")) printButton.disable();
//		
//		final IButton createButton = new IButton("บรรจุสินค้า");
//		createButton.setIcon("icons/16/print.png");
//		createButton.setWidth(150);
//		createButton.addClickHandler(new ClickHandler() {  
//            public void onClick(ClickEvent event) { 
//            	SC.confirm("ยืนยันการทำรายการ", "ต้องการออกคำสั่งแต่งและฝังพลอย หรือไม่?" , new BooleanCallback() {
//					@Override
//					public void execute(Boolean value) {
//						if (value) {
//							Record[] all = orderListGrid.getRecords();
//							ArrayList<String> pids = new ArrayList<String>();
//							for (Record product : all) {
//								pids.add(product.getAttributeAsString("pid"));
//							}
//							
//							Integer std_time = ProcessType.getMaxStdTime(pids, "4_packing");
//							createJobOrder(record, currentUser, std_time);
//							//SC.say("To do เริ่มบรรจุสินค้า std_time = " +std_time);
//							main.destroy();
//						}
//					}
//            	});
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
//		if (page == 1) controls.addMember(createButton);
//		if (page == 2) 
		if (record.getAttributeAsString("status").equalsIgnoreCase("2_paid")) saveButton.disable();
		if (!record.getAttributeAsString("status").equalsIgnoreCase("2_paid")) printButton.disable();
		controls.addMember(saveButton);
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
	
//	public void summaryRecalculate(ListGridRecord[] all, DynamicForm target, DynamicForm target_2, Double sent_weight){
//		total_received_weight = 0.0;
//		total_received_amount = 0;
//		total_paid_wage = 0.0;
//		total_return_mat = 0.0;
//		
//		Double need_weight = sent_weight * 0.97;
//		
//		for (ListGridRecord record : all) {
//			String temp_weight = record.getAttribute("recv_weight");
//			String temp_amount = record.getAttribute("recv_amount");
//			String temp_total_wage = record.getAttribute("sum_wage");
//			
//			Double recv_weight = 0.0;
//			Integer recv_amount = 0;
//			Double sum_page = 0.0;
//			
//			try {
//				if (temp_weight == null) {
//					recv_weight = 0.0;
//				} else {
//					recv_weight = Double.parseDouble(temp_weight);
//				}
//				if (temp_amount == null) {
//					recv_amount = 0;
//				} else {
//					recv_amount = Integer.parseInt(temp_amount);
//				}
//				if (temp_total_wage == null) {
//					sum_page = 0.0;
//				} else {
//					sum_page = Double.parseDouble(temp_total_wage);
//				}
//			} catch (Exception e) {
//				SC.warn("กรุณากรอกข้อมูลตัวเลข");
//				return;
//			}
//			
//			total_received_weight += recv_weight;
//			total_received_amount += recv_amount;
//			total_paid_wage += sum_page;
//		}
//		NumberFormat nf = NumberFormat.getFormat("#,##0.00");
//		target.getField("total_recv_weight").setValue(nf.format(total_received_weight));
//		target.getField("total_recv_amount").setValue(nf.format(total_received_amount));
//		target_2.getField("total_wage").setValue(nf.format(total_paid_wage));
//		
//		if (total_received_weight < need_weight) {
//			total_return_mat = need_weight - total_received_weight;
//			target_2.getField("return_mat").setValue(nf.format(need_weight - total_received_weight));
//		} else {
//			target_2.getField("return_mat").setValue(nf.format(0));
//		}
//	}
	
//	public void updateOrder(final String job_id , final ListGridRecord record, ListGrid orderListGrid, User currentUser){
//		final ListGridRecord[] all = orderListGrid.getRecords();
//		
//		for (ListGridRecord item : all){
//			if (item.getAttribute("recv_weight") == null || item.getAttribute("recv_amount") == null) {
//				SC.warn("กรุณากรอกข้อมูลรับสินค้าให้ครบถ้วน");
//				return;
//			}
//		}
//			final String process_status = "2_process_completed";
//			final String user = currentUser.getFirstName() + " " + currentUser.getLastName();
//			
//			record.setAttribute("status", process_status);
//			record.setAttribute("total_recv_weight", total_received_weight);
//			record.setAttribute("total_recv_amount", total_received_amount);
//			record.setAttribute("total_wage", total_paid_wage);
//			record.setAttribute("return_mat", total_return_mat);
//			record.setAttribute("modified_date", new Date());
//			record.setAttribute("modified_by", user);
//			record.setAttribute("received_date", new Date());
//			
//			final String wage_id = createWagePayment(record, user);
//			
//			AbradingDS.getInstance().updateData(record, new DSCallback() {
//				@Override
//				public void execute(DSResponse dsResponse, Object data,
//						DSRequest dsRequest) {
//						//System.out.println("Test " + dsResponse.getStatus());
//						if (dsResponse.getStatus() != 0) {
//							SC.warn("การบันทึกสินค้าล้มเหลว กรุณาทำรายการใหม่อีกครั้ง");
//							editWindow.destroy();
//						} else { 
//							for (ListGridRecord item : all) {
//								AbradingProductDS.getInstance(job_id).updateData(item);
//								createWageItemPayment(item, wage_id);
//							}
//							SC.say("บันทึกรับสินค้าเสร็จสิ้น <br><br> " + " (if any) สร้างรายการขอคืนวัตถุดิบโดยอัตโนมัติ หมายเลข " + "TBD" + "<br> สร้างรายการขอเบิกค่าจ้างผลิตโดยอัตโนมัติ หมายเลข " + wage_id);
//							editWindow.destroy();
//						}
//				}
//			});
//	}
//	
//	String createWagePayment(ListGridRecord record, String user) {
//		String wage_id = "WP70" + Math.round((Math.random() * 100));
//		String status = "1_waiting_for_payment";
//		ListGridRecord newRecord = WageData.createRecord(record, wage_id, new Date(), user, status);
//		WageDS.getInstance().addData(newRecord);
//		return wage_id;
//	}
//	
//	void createWageItemPayment(ListGridRecord record, String wage_id) {
//		String sub_wage_id = "SWP70" + Math.round((Math.random() * 100));
//		ListGridRecord newRecord = WageItemData.createRecord(record, sub_wage_id, wage_id, true);
//		WageItemDS.getInstance(wage_id).addData(newRecord);
//	}
	
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
//	
//	public void createDeliveryOrder(final Window main, final String sale_id, String invoice_id, DynamicForm customer, ListGrid orderListGrid, Date delivery, User currentUser){
//		
//		ListGridRecord[] all = orderListGrid.getRecords();
//		
////		if (all.length == 0) {
////			SC.warn("กรูณาเลือกรายการสินค้าอย่างน้อย 1 รายการ");
////			return;
////		}
//		
//		Double total_weight = 0.0;
//		Double total_netExclusive = 0.0;
//		Integer total_amount = 0;
//		final String delivery_id = "DL70" + Math.round((Math.random() * 100));
//		//final String invoice_id = "IN70" + Math.round((Math.random() * 100));
//		final ArrayList<SaleProductDetails> saleProductList = new ArrayList<SaleProductDetails>();
//		//final ArrayList<SaleProductDetails> invoiceProductList = new ArrayList<SaleProductDetails>();
//
//		for (ListGridRecord item : all){
//			total_weight += item.getAttributeAsDouble("weight");
//			total_amount += item.getAttributeAsInt("sale_amount");
//			total_netExclusive += item.getAttributeAsDouble("sum_price");
//			
//			String pid = item.getAttributeAsString("pid");
//			String pname = item.getAttributeAsString("name");
//			String ptype = item.getAttributeAsString("type");
//			//String psize = item.getAttributeAsString("size");
//			Double pweight = item.getAttributeAsDouble("weight");
//			Integer psale_amount = item.getAttributeAsInt("sale_amount");
//			String punit = item.getAttributeAsString("unit");
//			Double pprice = item.getAttributeAsDouble("price");
//			
//			String sub_sale_id = "SS80" + Math.round((Math.random() * 1000));
//			SaleProductDetails temp = new SaleProductDetails();
//			temp.save(pid, pname, pweight, pprice, ptype, punit);
//			temp.setID(sub_sale_id, delivery_id);
//			temp.setQuantity(psale_amount);
//			saleProductList.add(temp);
//		}	
//
//			final String delivery_status = "1_on_delivery";
//			String cid = (String) customer.getField("cid").getValue();
//			String smith_name = (String) customer.getField("smith_name").getValue();
////			String payment_model = (String) customer.getField("payment_model").getValue();
////			Integer credit = (Integer) customer.getField("credit").getValue();
//			
////			DateRange dateRange = new DateRange();  
////	        dateRange.setRelativeStartDate(RelativeDate.TODAY);
////	        dateRange.setRelativeEndDate(new RelativeDate("+"+credit+"d"));
////	        final Date due_date = dateRange.getEndDate();
//	        
//			final ListGridRecord deliveryRecord = DeliveryData.createRecord(delivery_id, sale_id, invoice_id, cid, smith_name, delivery, total_weight, total_amount, new Date(), null, currentUser.getFirstName() + " " + currentUser.getLastName(), null, delivery_status, new Date(), null, "");
//			//ListGridRecord invoiceRecord = InvoiceData.createRecord(invoice_id, sale_id, cid, smith_name, payment_model, credit, delivery, total_weight, total_amount, total_netExclusive, new Date(), null, currentUser.getFirstName() + " " + currentUser.getLastName(), null, invoice_status, purchase_id, due_date, null);
//			
//			//Auto create invoice
//			DeliveryDS.getInstance().addData(deliveryRecord, new DSCallback() {
//				@Override
//				public void execute(DSResponse dsResponse, Object data,
//						DSRequest dsRequest) {
//						if (dsResponse.getStatus() != 0) {
//							SC.warn("การสร้างรายการนำส่งสินค้าล้มเหลว กรุณาทำรายการใหม่อีกครั้ง");
//							main.destroy();
//						} else { 
//							for (SaleProductDetails item : saleProductList) {
//								ListGridRecord subAddRecord = SaleProductData.createRecord(item);
//								SaleProductDS.getInstance(delivery_id).addData(subAddRecord);
//							}
//							ListGridRecord saleRecord = SaleOrderData.createStatusRecord(sale_id, "4_on_delivery");
//							SaleOrderDS.getInstance().updateData(saleRecord);
//							main.destroy();
//						}
//				}
//			});
//	}
	
//	private ListGrid getListGrid() {
//		return new ListGrid() {  
//            public DataSource getRelatedDataSource(ListGridRecord record) {  
//                //return new CastingMaterialDS(record.getAttributeAsString("psid"), DS.pid);
//                return AbradingMaterialDS.getInstance(record.getAttributeAsString("sub_job_id"), record.getAttributeAsString("job_id"));
//            }  
//  
//            @Override  
//            protected Canvas getExpansionComponent(final ListGridRecord record) {  
//  
//                final ListGrid grid = this;  
//  
//                VLayout layout = new VLayout(5);  
//                layout.setPadding(5);  
//  
//                SectionStack sectionStack = new SectionStack();
//            	sectionStack.setWidth(525);
//            	sectionStack.setHeight(100);
//            	SectionStackSection section = new SectionStackSection("รายการวัตถุดิบ");
//            	section.setCanCollapse(false);
//                section.setExpanded(true);
//                
//                final ListGrid materialGrid = new ListGrid();  
//                materialGrid.setWidth(525);  
//                materialGrid.setHeight(100);  
//                materialGrid.setCellHeight(22);  
//                
//                materialGrid.setDataSource(getRelatedDataSource(record));  
//                materialGrid.fetchRelatedData(record, AbradingProductDS.getInstance(record.getAttributeAsString("job_id"))); 
//            	
//                materialGrid.setModalEditing(true);  
//                materialGrid.setEditEvent(ListGridEditEvent.CLICK);  
//                materialGrid.setListEndEditAction(RowEndEditAction.NEXT);  
//                materialGrid.setAutoSaveEdits(false);  
//  
//                ListGridField Field_1 = new ListGridField("mid", 150);
//                ListGridField Field_2 = new ListGridField("mat_name", 200);
//                ListGridField editField = new ListGridField("produce_amount", 120);
//                editField.setTitle("ปริมาณที่ใช้ผลิต");
//                editField.setCellFormatter(FieldFormatter.getNumberFormat());
//                ListGridField Field_3 = new ListGridField("unit", 50);
//                
//                materialGrid.setFields(Field_1, Field_2, editField, Field_3);
//                
//	    	      section.setItems(materialGrid);
//	    	      sectionStack.setSections(section);
//    	      
//                layout.addMember(sectionStack);
//  
//                return layout;
//            }  
//		};
//	}
//
//	public void createJobOrder(ListGridRecord casting, User currentUser, Integer std_time){
//		PackingCreateWindow order = new PackingCreateWindow();
//		order.show(casting, currentUser, std_time);
//	}
}
