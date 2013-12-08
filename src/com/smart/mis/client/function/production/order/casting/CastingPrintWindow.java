package com.smart.mis.client.function.production.order.casting;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.smart.mis.client.function.financial.disburse.wage.WageDS;
import com.smart.mis.client.function.financial.disburse.wage.WageData;
import com.smart.mis.client.function.financial.disburse.wage.WageItemDS;
import com.smart.mis.client.function.financial.disburse.wage.WageItemData;
import com.smart.mis.client.function.inventory.material.requisition.MaterialRequestDS;
import com.smart.mis.client.function.inventory.material.requisition.MaterialRequestData;
import com.smart.mis.client.function.inventory.material.requisition.MaterialRequestItemDS;
import com.smart.mis.client.function.inventory.material.requisition.MaterialRequestItemData;
import com.smart.mis.client.function.inventory.material.requisition.MaterialRequestItemDetails;
import com.smart.mis.client.function.production.order.scraping.ScrapingCreateWindow;
import com.smart.mis.client.function.production.plan.product.PlanProductDS;
import com.smart.mis.client.function.production.process.ProcessListDS;
import com.smart.mis.client.function.production.smith.SmithDS;
import com.smart.mis.client.function.purchasing.material.MaterialDS;
import com.smart.mis.shared.EditorWindow;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.FieldVerifier;
import com.smart.mis.shared.ListGridNumberField;
import com.smart.mis.shared.PrintHeader;
import com.smart.mis.shared.prodution.ProcessStatus;
import com.smart.mis.shared.prodution.ProcessType;
import com.smart.mis.shared.prodution.ProductType;
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
import com.smartgwt.client.widgets.form.fields.SpinnerItem;
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

public class CastingPrintWindow extends EditorWindow{

	Smith smith;
	Window editWindow;
	Double total_received_weight;
	Integer total_received_amount;
	Double total_paid_wage;
	Double wage_per_gam;
	
	public void show(ListGridRecord record, boolean edit, User currentUser, int page){
		smith = new Smith();
		editWindow = new Window();
		editWindow.setTitle("ข้อมูลคำสั่งผลิต");
		editWindow.setWidth(1000);  
		editWindow.setHeight(570);
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
		layout.setWidth(970);
		layout.setHeight(550);
		layout.setMargin(10);
		
		final String job_id = record.getAttributeAsString("job_id");
		String plan_id = record.getAttributeAsString("plan_id");
//		String status = record.getAttributeAsString("status");
		
		String created_by = record.getAttributeAsString("created_by");
		Date created_date = record.getAttributeAsDate("created_date");
		
		Date sent_date = record.getAttributeAsDate("sent_date");
		Date due_date = record.getAttributeAsDate("due_date");
//		Date received_date = record.getAttributeAsDate("received_date");
		
		Double sent_weight = record.getAttributeAsDouble("total_sent_weight");
		Integer sent_amount = record.getAttributeAsInt("total_sent_amount");
		
//		Double recv_weight = record.getAttributeAsDouble("total_recv_weight");
//		if (recv_weight == null) {
//			total_received_weight = 0.0; 
//		} else {
//			total_received_weight = recv_weight;
//		}
//		Integer recv_amount = record.getAttributeAsInt("total_recv_amount");
//		Double job_wage = record.getAttributeAsDouble("total_wage");
//		System.out.println("Casting job wage " + job_wage);
		
		final DynamicForm orderForm = new DynamicForm();
		orderForm.setWidth100(); 
		orderForm.setHeight(30);
		orderForm.setMargin(5);
		orderForm.setIsGroup(true);
		orderForm.setNumCols(8);
		orderForm.setGroupTitle("ข้อมูลแผนการผลิต");

		StaticTextItem jid = new StaticTextItem("job_id", "รหัสคำสั่งผลิต");
		StaticTextItem qid = new StaticTextItem("plan_id", "รหัสแผนการผลิต");
		//StaticTextItem ddate = new StaticTextItem("delivery", "วันที่กำหนดส่งสินค้า");
//		StaticTextItem sts = new StaticTextItem("status", "สถานะ");
		StaticTextItem cby = new StaticTextItem("created_by", "สร้างโดย");
		StaticTextItem cdate = new StaticTextItem("created_date", "สร้างเมื่อ");
		jid.setValue(job_id);
		qid.setValue(plan_id);
//		if (delivery != null ) ddate.setValue(DateTimeFormat.getFormat("MM/dd/yyy").format(delivery));
//		else ddate.setValue("-");
//		sts.setValue(ProcessStatus.getDisplay(status));
		cby.setValue(created_by);
		cdate.setValue(DateTimeFormat.getFormat("MM/dd/yyy").format(created_date));
//		orderForm.setFields(jid, qid, sts, cdate ,cby);
		orderForm.setFields(jid, qid, cdate ,cby);
		orderForm.setColWidths(100,80,120,80,80,120,100,100);
		layout.addMember(orderForm);
		
		final DynamicForm smithForm = new DynamicForm();
		smithForm.setWidth100(); 
		smithForm.setHeight(30);
		smithForm.setMargin(5); 
		smithForm.setNumCols(8); 
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
		
		smith.smid = smid;
		smith.name = sname;
		
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
	    smith_address.setColSpan(6);
	        
	    smithForm.setFields(smith_name, smith_id, smith_type,smith_email, smith_phone1, smith_phone2 , smith_address);  

		smithForm.setColWidths(80,120,100,100,100,100,100,100);
		
		HLayout headerLayout = new HLayout();
		headerLayout.setWidth100();
		headerLayout.addMembers(smithForm);
		layout.addMember(headerLayout);
		
		SectionStack sectionStack = new SectionStack();
    	sectionStack.setWidth100();
    	sectionStack.setHeight(250);
    	SectionStackSection section = new SectionStackSection("รายการสินค้า");  
    	section.setCanCollapse(false);  
        section.setExpanded(true);
        
		//final ListGrid orderListGrid = getListGrid();
		final ListGrid orderListGrid = new ListGrid();
		orderListGrid.setHeight(230);
		orderListGrid.setAlternateRecordStyles(true);
		orderListGrid.setShowAllRecords(true);
//		orderListGrid.setAutoFetchData(true);  
//		if (!edit) orderListGrid.setSelectionType(SelectionStyle.NONE);
//		else orderListGrid.setSelectionType(SelectionStyle.SINGLE);
		orderListGrid.setCanResizeFields(false);
		orderListGrid.setShowGridSummary(true);
//		orderListGrid.setEditEvent(ListGridEditEvent.CLICK);  
//		orderListGrid.setListEndEditAction(RowEndEditAction.NEXT);
		orderListGrid.setShowRowNumbers(true);
//		orderListGrid.setCanExpandRecords(true);
        final Criterion ci = new Criterion("status", OperatorId.EQUALS, true);
		orderListGrid.setCriteria(ci);
		
		CastingProductDS tempView = CastingProductDS.getInstance(job_id);
		tempView.refreshData();
		
		//orderListGrid.setDataSource(tempView);
		//orderListGrid.setUseAllDataSourceFields(false);
		orderListGrid.setData(tempView.getCacheData());
		
		ListGridField quoteItemCell_1 = new ListGridField("pid", "รหัสสินค้า", 60);
        ListGridField quoteItemCell_2 = new ListGridField("name"); 
        quoteItemCell_2.setTitle("ชื่อสินค้า");
        quoteItemCell_2.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });  
        quoteItemCell_2.setShowGridSummary(true);
        ListGridField quoteItemCell_3 = new ListGridField("unit", "หน่วย", 40);
        
        ListGridNumberField quoteItemCell_4 = new ListGridNumberField("sent_weight", 120);
        quoteItemCell_4.setTitle("น้ำหนักที่สั่ง (กรัม)");
        quoteItemCell_4.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_4.setShowGridSummary(true);
        quoteItemCell_4.setIncludeInRecordSummary(false);
        
        ListGridNumberField quoteItemCell_5 = new ListGridNumberField("sent_amount", 120);
        quoteItemCell_5.setTitle("จำนวนที่สั่ง");
        quoteItemCell_5.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_5.setShowGridSummary(true);
        
        ListGridField quoteItemCell_6 = new ListGridField("details", "รายละเอียดสินค้า", 200);
        ListGridField quoteItemCell_7 = new ListGridField("pdetails", "รายละเอียดการผลิต", 100);
        ListGridField quoteItemCell_8 = new ListGridField("type", "ชนิด", 70);
        quoteItemCell_8.setValueMap(ProductType.getValueMap());
//        ListGridNumberField quoteItemCell_6 = new ListGridNumberField("recv_weight", 120);
//        quoteItemCell_6.setSummaryFunction(SummaryFunctionType.SUM);
//        quoteItemCell_6.setCellFormatter(FieldFormatter.getNumberFormat());
//        quoteItemCell_6.setType(ListGridFieldType.FLOAT); 
//        quoteItemCell_6.setShowGridSummary(true);
//        if (edit) quoteItemCell_6.setCanEdit(true);
//        
//        ListGridNumberField quoteItemCell_7 = new ListGridNumberField("recv_amount", 120);
//        quoteItemCell_7.setSummaryFunction(SummaryFunctionType.SUM);
//        quoteItemCell_7.setCellFormatter(FieldFormatter.getNumberFormat());
//        quoteItemCell_7.setType(ListGridFieldType.FLOAT);
//        quoteItemCell_7.setShowGridSummary(true);
//        if (edit) quoteItemCell_7.setCanEdit(true);
 
        //orderListGrid.setFields(quoteItemCell_1, quoteItemCell_2, quoteItemCell_4, quoteItemCell_5, quoteItemCell_3, quoteItemCell_6, quoteItemCell_7, quoteItemCell_3);
        orderListGrid.setFields(quoteItemCell_1, quoteItemCell_2, quoteItemCell_8, quoteItemCell_6, quoteItemCell_7, quoteItemCell_4, quoteItemCell_5, quoteItemCell_3);
        
        section.setItems(orderListGrid);
        sectionStack.setSections(section);
		layout.addMember(sectionStack);
		
		HLayout footerLayout = new HLayout();
		footerLayout.setHeight(100);
		
		final DynamicForm dateForm = new DynamicForm();
		dateForm.setWidth(350);
		dateForm.setHeight(75);
		dateForm.setNumCols(2);
		dateForm.setMargin(5);
		dateForm.setIsGroup(true);
		dateForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
		dateForm.setGroupTitle("ข้อกำหนดคำสั่งผลิต");
		dateForm.setCanEdit(false);
		
		final DateItem sentDate = new DateItem();
		sentDate.setName("sent_date");
		sentDate.setTitle("วันที่สั่งผลิต");
		sentDate.setUseTextField(true);
		sentDate.setDefaultValue(sent_date);
		sentDate.setCanEdit(false);
		
		final DateItem dueDate = new DateItem();
		dueDate.setName("due_date");
		dueDate.setTitle("วันที่กำหนดรับของ");
		dueDate.setUseTextField(true);
		dueDate.setDefaultValue(due_date);
		dueDate.setCanEdit(false);
		
//		final DateItem receivedDate = new DateItem();
//		receivedDate.setName("received_date");
//		receivedDate.setTitle("วันที่บันทึกรับสินค้า");
//		receivedDate.setUseTextField(true);
//		if (page == 2) receivedDate.setDefaultValue(new Date());
//		else if (page == 1) receivedDate.setDefaultValue(received_date);
//		receivedDate.setCanEdit(false);
		
//		dateForm.setFields(sentDate, dueDate, receivedDate);
		dateForm.setFields(sentDate, dueDate);
		dateForm.setColWidths(130,80);
		footerLayout.addMember(dateForm);
		//******************End
		
		//******************Summary
		final DynamicForm summaryForm_1 = new DynamicForm();
		summaryForm_1.setWidth(350);
		summaryForm_1.setHeight(75);
		summaryForm_1.setNumCols(2);
		summaryForm_1.setMargin(5);
		summaryForm_1.setIsGroup(true);
		summaryForm_1.setGroupTitle("สรุปยอดสั่งผลิต");
		summaryForm_1.setColWidths(120, 80);
		final NumberFormat nf = NumberFormat.getFormat("#,##0.00");
		final StaticTextItem total_sent_weight = new StaticTextItem("total_sent_weight");
		total_sent_weight.setValue(nf.format(sent_weight));
		final StaticTextItem total_sent_amount = new StaticTextItem("total_sent_amount");
		total_sent_amount.setValue(nf.format(sent_amount));
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
		
//		final DynamicForm summaryForm_2 = new DynamicForm();
//		summaryForm_2.setWidth(200);
//		summaryForm_2.setHeight(75);
//		summaryForm_2.setNumCols(2);
//		summaryForm_2.setMargin(5);
//		summaryForm_2.setIsGroup(true);
//		summaryForm_2.setGroupTitle("สรุปยอดรับสินค้า");
//		summaryForm_2.setColWidths(80, 120);
//		final StaticTextItem total_recv_weight = new StaticTextItem("total_recv_weight");
//		if (recv_weight == null) {
//			total_recv_weight.setDefaultValue(nf.format(0));
//		} else {
//			total_recv_weight.setDefaultValue(nf.format(recv_weight));
//		}
//		final StaticTextItem total_recv_amount = new StaticTextItem("total_recv_amount");
//		if (recv_amount == null) {
//			total_recv_amount.setDefaultValue(nf.format(0));
//		} else {
//			total_recv_amount.setDefaultValue(nf.format(recv_amount));
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
		
//        final DynamicForm summaryForm_3 = new DynamicForm();
//        summaryForm_3.setWidth(240);
//        summaryForm_3.setHeight(75);
//        summaryForm_3.setNumCols(2);
//        summaryForm_3.setMargin(5);
//        summaryForm_3.setIsGroup(true);
//        summaryForm_3.setGroupTitle("สรุปค่าจ้างผลิต");
//        summaryForm_3.setColWidths(80, 70);
//		final SpinnerItem  wage_per_gam = new SpinnerItem("wage_per_gam");
//		wage_per_gam.setDefaultValue(0.9);
//		wage_per_gam.setMin(0.1);  
//		wage_per_gam.setMax(10.0);
//		wage_per_gam.setStep(0.1d);
//		if (!edit) wage_per_gam.setCanEdit(false);
//		final StaticTextItem total_wage = new StaticTextItem("total_wage");
//		if (job_wage == null) {
//			total_wage.setDefaultValue(nf.format(0));
//		} else {
//			total_wage.setDefaultValue(nf.format(job_wage));
//		}
//		wage_per_gam.setWidth(80);
//		total_wage.setWidth(80);
//		wage_per_gam.setTitle("ค่าจ้าง");
//		total_wage.setTitle("ค่าจ้างรวม");
//		wage_per_gam.setTextAlign(Alignment.RIGHT);
//		total_wage.setTextAlign(Alignment.RIGHT);
//		wage_per_gam.setHint("บาทต่อกรัม *");
//		total_wage.setHint("บาท");
//		summaryForm_3.setFields(wage_per_gam, total_wage);
//		
//		wage_per_gam.addChangedHandler(new ChangedHandler() {
//
//			@Override
//			public void onChanged(ChangedEvent event) {
//				if (wage_per_gam.validate()) {
//					total_paid_wage = Double.parseDouble(wage_per_gam.getValueAsString()) * total_received_weight;
//					total_wage.setValue(nf.format(total_paid_wage));
//				}
//			}});
//		
//		orderListGrid.addCellSavedHandler(new CellSavedHandler() {  
//			@Override
//			public void onCellSaved(CellSavedEvent event) {
//				summaryRecalculate(orderListGrid.getRecords(), summaryForm_2, summaryForm_3, Double.parseDouble(wage_per_gam.getValueAsString()));
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
//		final IButton receivedButton = new IButton("บันทึกรับสินค้า");
//		receivedButton.setIcon("icons/16/save.png");
//		receivedButton.setWidth(120);
//		receivedButton.addClickHandler(new ClickHandler() {  
//            public void onClick(ClickEvent event) { 
//            	
//            	SC.confirm("ยืนยันการบันทึกรับสินค้า", "ต้องการบันทึกรับสินค้า หรือไม่?" , new BooleanCallback() {
//					@Override
//					public void execute(Boolean value) {
//						if (value) {
//			                updateOrder(job_id, record, orderListGrid, currentUser);
//						}
//					}
//            	});
//          }
//        });
//		// if (edit || !status.equals("3_approved")) receivedButton.disable();
//		
//		final IButton createButton = new IButton("ออกคำสั่งแต่งและฝังพลอย");
//		createButton.setIcon("icons/16/print.png");
//		createButton.setWidth(170);
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
//							Integer std_time = ProcessType.getMaxStdTime(pids, "2_scrape");
//							createJobOrder(record, currentUser, std_time);
//							main.destroy();
//						}
//					}
//            	});
//          }
//        });
		
		final IButton printButton = new IButton("พิมพ์คำสั่งผลิต");
		printButton.setIcon("icons/16/print.png");
		printButton.setWidth(120);
		printButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	
//            	SC.confirm("ยืนยันการบันทึกรับสินค้า", "ต้องการบันทึกรับสินค้า หรือไม่?" , new BooleanCallback() {
//					@Override
//					public void execute(Boolean value) {
//						if (value) {
//			                updateOrder(job_id, record, orderListGrid, currentUser);
//						}
//					}
//            	});
				VLayout printLayout = new VLayout(10);
            	printLayout.addMember(new PrintHeader("ใบสั่งผลิต"));
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
//			controls.addMember(receivedButton);
//			controls.addMember(saveButton);
//		}
		
//		if (page == 1 && status.equals("1_waiting_for_production")) controls.addMember(cancelButton);
//		if (page == 1 && status.equals("3_production_completed")) controls.addMember(deliveryButton);
		
		controls.addMember(printButton);
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
	
//	public void summaryRecalculate(ListGridRecord[] all, DynamicForm target, DynamicForm target_2, Double perGam){
//		total_received_weight = 0.0;
//		total_received_amount = 0;
//		for (ListGridRecord record : all) {
//			String temp_weight = record.getAttribute("recv_weight");
//			String temp_amount = record.getAttribute("recv_amount");
//			
//			Double recv_weight = 0.0;
//			Integer recv_amount = 0;
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
//			} catch (Exception e) {
//				SC.warn("กรุณากรอกข้อมูลตัวเลข");
//				return;
//			}
//			
//			total_received_weight += recv_weight;
//			total_received_amount += recv_amount;
//		}
//		NumberFormat nf = NumberFormat.getFormat("#,##0.00");
//		target.getField("total_recv_weight").setValue(nf.format(total_received_weight));
//		target.getField("total_recv_amount").setValue(nf.format(total_received_amount));
//		wage_per_gam = perGam;
//		total_paid_wage = total_received_weight * perGam;
//		target_2.getField("total_wage").setValue(nf.format(total_paid_wage));
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
//			record.setAttribute("modified_date", new Date());
//			record.setAttribute("modified_by", user);
//			record.setAttribute("received_date", new Date());
//			
//			final String wage_id = createWagePayment(record, user);
//			
//			//Create Material Request total_recv_weight / 2 -> Silver 92.5% + 100%
//			//final HashMap<String, MaterialRequestItemDetails> matRequest = new HashMap<String, MaterialRequestItemDetails>();
//			final String request_id = "MR70" + Math.round((Math.random() * 100));
//			createMaterialRequest(request_id, job_id, smith, currentUser.getFirstName() + " " + currentUser.getLastName(), total_received_weight);
//
//			CastingDS.getInstance().updateData(record, new DSCallback() {
//				@Override
//				public void execute(DSResponse dsResponse, Object data,
//						DSRequest dsRequest) {
//						//System.out.println("Test " + dsResponse.getStatus());
//						if (dsResponse.getStatus() != 0) {
//							SC.warn("การบันทึกสินค้าล้มเหลว กรุณาทำรายการใหม่อีกครั้ง");
//							editWindow.destroy();
//						} else { 
//							for (ListGridRecord item : all) {
//								item.setAttribute("wage", wage_per_gam);
//								item.setAttribute("sum_wage", item.getAttributeAsDouble("recv_weight") * wage_per_gam);
//								CastingProductDS.getInstance(job_id).updateData(item);
//								createWageItemPayment(item, wage_id);
//							}
//							//String request_id = createSilverRequisition(total_received_weight);
//							
//							String message = "บันทึกรับสินค้าเสร็จสิ้น <br><br> " + " สร้างรายการขอเบิกวัตถุดิบโดยอัตโนมัติ หมายเลข " + request_id + "<br> สร้างรายการขอเบิกค่าจ้างผลิตโดยอัตโนมัติ หมายเลข " + wage_id;
//							SC.say(message, new BooleanCallback(){
//								@Override
//								public void execute(Boolean value) {
//									if (value) {
//										editWindow.destroy();
//									}
//								}
//							} );
//						}
//				}
//			});
//	}
	
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
//	
//	public void createMaterialRequest(String request_id, String job_id, Smith smith,String user, Double total_received_weight){
//		
//		MaterialDS.getInstance().refreshData();
//		Record[] silver100 = MaterialDS.getInstance().applyFilter(MaterialDS.getInstance().getCacheData(), new Criterion("mat_name", OperatorId.EQUALS, "แร่เงิน 100%"));
//		Record[] silver925 = MaterialDS.getInstance().applyFilter(MaterialDS.getInstance().getCacheData(), new Criterion("mat_name", OperatorId.EQUALS, "แร่เงิน 92.5%"));
//		
//		MaterialRequestItemDetails[] matRequest = new MaterialRequestItemDetails[] {
//				new MaterialRequestItemDetails(silver100[0], total_received_weight / 2, silver100[0].getAttributeAsDouble("remain")),
//				new MaterialRequestItemDetails(silver925[0], total_received_weight / 2, silver100[0].getAttributeAsDouble("remain"))
//		};
//		
//		Double totel_request_amount = 0.0;
//		for (MaterialRequestItemDetails item : matRequest) {
//			totel_request_amount += item.getAmount();
//			final String sub_request_id = "SMR70" + Math.round((Math.random() * 100));
//			ListGridRecord newRecord = MaterialRequestItemData.createRecord(sub_request_id, request_id, item);
//			MaterialRequestItemDS.getInstance(request_id).addData(newRecord);
//			
//			MaterialDS.getInstance().refreshData();
//			Record[] updated_records = MaterialDS.getInstance().applyFilter(MaterialDS.getInstance().getCacheData(), new Criterion("mid", OperatorId.EQUALS, item.material_id));
//			Record updated = updated_records[0];
//			Double remain = updated.getAttributeAsDouble("remain") - item.getAmount();
//			updated.setAttribute("remain", remain);
//			Double reserved = updated.getAttributeAsDouble("reserved") + item.getAmount();
//			updated.setAttribute("reserved", reserved);
//			MaterialDS.getInstance().updateData(updated);
//		}
//		ListGridRecord newRecord = MaterialRequestData.createRecord(request_id, job_id, smith, "1_casting", new Date(), totel_request_amount, new Date(), null, user, null, "1_requested");
//		MaterialRequestDS.getInstance().addData(newRecord);
//	}
	
//	private ListGrid getListGrid() {
//		return new ListGrid() {  
//            public DataSource getRelatedDataSource(ListGridRecord record) {  
//                //return new CastingMaterialDS(record.getAttributeAsString("psid"), DS.pid);
//                return CastingMaterialDS.getInstance(record.getAttributeAsString("sub_job_id"), record.getAttributeAsString("job_id"));
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
//                materialGrid.fetchRelatedData(record, CastingProductDS.getInstance(record.getAttributeAsString("job_id"))); 
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
	
//	public void createJobOrder(ListGridRecord casting, User currentUser, Integer std_time){
//		ScrapingCreateWindow order = new ScrapingCreateWindow();
//		order.show(casting, currentUser, std_time);
//	}
}
