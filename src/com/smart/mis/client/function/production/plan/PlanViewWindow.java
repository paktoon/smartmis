package com.smart.mis.client.function.production.plan;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.smart.mis.client.function.production.plan.product.PlanProductDS;
import com.smart.mis.client.function.production.plan.product.PlanProductData;
import com.smart.mis.client.function.production.plan.product.PlanProductDetails;
import com.smart.mis.client.function.sale.order.SaleOrderDS;
import com.smart.mis.client.function.sale.quotation.product.QuoteProductDS;
import com.smart.mis.shared.EditorWindow;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.FieldVerifier;
import com.smart.mis.shared.ListGridNumberField;
import com.smart.mis.shared.prodution.ProductionPlanStatus;
import com.smart.mis.shared.sale.Customer;
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

public class PlanViewWindow extends EditorWindow{

	SelectProductList addFunc;
	Customer client;
	
	public PlanViewWindow(){
		addFunc = new SelectProductList();
	}
	
	public void show(ListGridRecord record, boolean edit, User currentUser, int page){
		client = new Customer();
		Window editWindow = new Window();
		editWindow.setTitle("ข้อมูลแผนการผลิค");
		editWindow.setWidth(690);  
		editWindow.setHeight(450);
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
		layout.setHeight(400);
		layout.setMargin(10);
		
		final String plan_id = record.getAttributeAsString("plan_id");
		String status = record.getAttributeAsString("status");
		Double total_weight = record.getAttributeAsDouble("total_weight");
		Double total_amount = record.getAttributeAsDouble("total_amount");
		
		//May be null
		final String sale_id = record.getAttributeAsString("sale_id");
		Date delivery = record.getAttributeAsDate("delivery");
		String comment = record.getAttributeAsString("comment");
		String reason = record.getAttributeAsString("reason");
		
		String created_by = record.getAttributeAsString("created_by");
		Date created_date = record.getAttributeAsDate("created_date");
		
		DynamicForm productionPlanForm = new DynamicForm();
		productionPlanForm.setWidth100(); 
		productionPlanForm.setHeight(30);
		productionPlanForm.setMargin(5);
		productionPlanForm.setIsGroup(true);
		productionPlanForm.setNumCols(8);
		productionPlanForm.setGroupTitle("ข้อมูลแผนการผลิค");

		StaticTextItem qid = new StaticTextItem("plan_id", "รหัสแผนการผลิค");
		StaticTextItem sts = new StaticTextItem("status", "สถานะ");
		StaticTextItem cby = new StaticTextItem("created_by", "สร้างโดย");
		StaticTextItem cdate = new StaticTextItem("created_date", "สร้างเมื่อ");
		qid.setValue(plan_id);
		sts.setValue(status);
		sts.setValueMap(ProductionPlanStatus.getValueMap());
		cby.setValue(created_by);
		cdate.setValue(DateTimeFormat.getFormat("MM/dd/yyy").format(created_date));
		productionPlanForm.setFields(qid, sts, cdate ,cby);
		productionPlanForm.setColWidths(100,70,70,70,70,80,60,100);
		layout.addMember(productionPlanForm);
		
		if (sale_id != null && sale_id.equalsIgnoreCase("")) {
			final DynamicForm saleOrderForm = new DynamicForm();
			saleOrderForm.setWidth(360); 
			saleOrderForm.setHeight(30);
			saleOrderForm.setMargin(5); 
			saleOrderForm.setNumCols(4); 
			saleOrderForm.setCellPadding(2);
			saleOrderForm.setAutoFocus(true);
			saleOrderForm.setSelectOnFocus(true);
			saleOrderForm.setDataSource(SaleOrderDS.getInstance());
			saleOrderForm.setUseAllDataSourceFields(false);
			saleOrderForm.setIsGroup(true);
			saleOrderForm.setGroupTitle("ข้อมูลรายการขาย");
			StaticTextItem sid = new StaticTextItem("sale_id", "รหัสรายการขาย");
			StaticTextItem cus_name = new StaticTextItem("cus_name", "ชื่อลูกค้า");
			StaticTextItem ddate = new StaticTextItem("delivery", "กำหนดส่งสินค้า");
			ddate.setValue(DateTimeFormat.getFormat("MM/dd/yyy").format(delivery));
			saleOrderForm.setFields(sid, cus_name, ddate);
			saleOrderForm.setColWidths(100,100,100,200,100,100);
			saleOrderForm.fetchData(new Criterion("sale_id", OperatorId.EQUALS, sale_id));
			layout.addMember(saleOrderForm);
		}
		
		DynamicForm commentForm = new DynamicForm();
		commentForm.setWidth(650); 
		commentForm.setHeight(30);
		commentForm.setMargin(5);
		commentForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
		
		final SelectOtherItem reason_area = new SelectOtherItem(); 
		if (edit) reason_area.setCanEdit(true);
		else reason_area.setCanEdit(false);
		reason_area.setOtherTitle("อื่นๆ..");  
		reason_area.setOtherValue("OtherVal");
		reason_area.setEmptyDisplayValue("---โปรดระบุความคิดเห็น---");
		reason_area.setTitle("เหตุผลในการผลิต");  
		reason_area.setValueMap("สินค้ามีปริมาณต่ำกว่าที่ควรจะเป็น", "สินค้าขายดี", "สินค้าขาดตลาด"); 
		reason_area.setValue(reason);
		reason_area.setWidth(250);
		
		final StaticTextItem comment_area = new StaticTextItem();
		comment_area.setTitle("ความคิดเห็น");
		comment_area.setWidth(250);
		comment_area.setValue(comment);
		
		commentForm.setFields(reason_area, comment_area);
		
		HLayout headerLayout = new HLayout();
		headerLayout.setWidth100();
		headerLayout.addMembers(commentForm);
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
		final ListGrid planListGrid = new ListGrid();
		planListGrid.setHeight(230);
		planListGrid.setAlternateRecordStyles(true);  
		planListGrid.setShowAllRecords(true);  
		planListGrid.setAutoFetchData(true);  
		if (edit) planListGrid.setSelectionType(SelectionStyle.SINGLE);
		else planListGrid.setSelectionType(SelectionStyle.NONE);
		planListGrid.setCanResizeFields(false);
		planListGrid.setShowGridSummary(true);
		planListGrid.setEditEvent(ListGridEditEvent.CLICK);  
		planListGrid.setListEndEditAction(RowEndEditAction.NEXT);
		planListGrid.setShowRowNumbers(true);
        final Criterion ci = new Criterion("status", OperatorId.EQUALS, true);
		planListGrid.setCriteria(ci);
		
		PlanProductDS tempView = new PlanProductDS(plan_id);
		tempView.setTestData(PlanProductDS.getInstance(plan_id).getCacheData());
		planListGrid.setDataSource(tempView);
		planListGrid.setUseAllDataSourceFields(false);
		
		ListGridField quoteItemCell_1 = new ListGridField("pid", 60);
        ListGridField quoteItemCell_2 = new ListGridField("name"); 
        quoteItemCell_2.setTitle("ชื่อสินค้า");
        quoteItemCell_2.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });  
        quoteItemCell_2.setShowGridSummary(true);
        ListGridField quoteItemCell_3 = new ListGridField("unit", 50);
        
        ListGridNumberField quoteItemCell_4 = new ListGridNumberField("weight", 90);
        quoteItemCell_4.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_4.setShowGridSummary(true);
        quoteItemCell_4.setIncludeInRecordSummary(false);
        
        ListGridNumberField quoteItemCell_6 = new ListGridNumberField("plan_amount", 70);
        
        if (edit) quoteItemCell_6.setCanEdit(true);
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
 
        planListGrid.setFields(quoteItemCell_1, quoteItemCell_2, quoteItemCell_7, quoteItemCell_6, quoteItemCell_3);
        //itemLayout.addMember(planListGrid);
        section.setItems(planListGrid);
        sectionStack.setSections(section);
		layout.addMember(sectionStack);
		
//		HLayout footerLayout = new HLayout();
//		footerLayout.setHeight(100);
//		
//		final DynamicForm dateForm = new DynamicForm();
//		dateForm.setWidth(300);
//		dateForm.setNumCols(2);
//		dateForm.setMargin(5);
//		dateForm.setIsGroup(true);
//		dateForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
//		dateForm.setGroupTitle("ข้อกำหนดแผนการผลิค");
//		if (!edit) dateForm.setCanEdit(false);
//		final DateItem fromDate = new DateItem();
//		fromDate.setName("fromDate");
//		fromDate.setTitle("วันที่เริ่มข้อเสนอ");
//		fromDate.setUseTextField(true);
//		
//		final DateItem toDate = new DateItem();
//		toDate.setName("toDate");
//		toDate.setTitle("วันที่สิ้นสุดข้อเสนอ");
//		toDate.setUseTextField(true);
//		
//		final DateItem deliveryDate = new DateItem();
//		deliveryDate.setName("deliveryDate");
//		deliveryDate.setTitle("วันที่กำหนดส่งของ");
//		deliveryDate.setUseTextField(true);
//		
//        fromDate.setDefaultChooserDate(from);
//        fromDate.setValue(from);
//        toDate.setDefaultChooserDate(to);
//        toDate.setValue(to);
//        deliveryDate.setDefaultChooserDate(delivery);
//        deliveryDate.setValue(delivery);
////        fromDate.setRequired(true);
////        fromDate.setHint("*");
////		toDate.setRequired(true);
////		toDate.setHint("*");
////		deliveryDate.setRequired(true);
////		deliveryDate.setHint("*");
//		
//		dateForm.setFields(fromDate, toDate, deliveryDate);
//		dateForm.setColWidths(130,80);
//		//dateForm.editRecord(record);
//		footerLayout.addMember(dateForm);
//		//******************End
//		
//		//******************Summary
//		final DynamicForm summaryForm = new DynamicForm();
//		summaryForm.setWidth(300);
//		summaryForm.setNumCols(2);
//		summaryForm.setMargin(5);
//		summaryForm.setIsGroup(true);
//		summaryForm.setGroupTitle("สรุปยอดรวม");
//		summaryForm.setColWidths(120, 100);
//		NumberFormat nf = NumberFormat.getFormat("#,##0.00");
//		StaticTextItem netExclusive = new StaticTextItem("netExclusive");
//		netExclusive.setValue(nf.format(netEx));
//		StaticTextItem tax = new StaticTextItem("tax");
//		tax.setValue(nf.format(netEx * 0.07));
//		StaticTextItem netInclusive = new StaticTextItem("netInclusive");
//		netInclusive.setValue(nf.format(netEx * 1.07));
//		netExclusive.setWidth(100);
//		tax.setWidth(100);
//		netInclusive.setWidth(100);
//		netExclusive.setTitle("ราคารวม");
//		tax.setTitle("ภาษีมูลค่าเพิ่ม (7%)");
//		netInclusive.setTitle("ราคาสุทธิ");
//		netExclusive.setTextAlign(Alignment.RIGHT);
//		tax.setTextAlign(Alignment.RIGHT);
//		netInclusive.setTextAlign(Alignment.RIGHT);
//		netExclusive.setHint("บาท");
//		tax.setHint("บาท");
//		netInclusive.setHint("บาท");
//		summaryForm.setFields(netExclusive, tax, netInclusive);
//		//summaryForm.editRecord(record);
//		footerLayout.addMember(summaryForm);
//		
//		layout.addMember(footerLayout);
		
		//Control
		HLayout controls = new HLayout();
		controls.setAlign(Alignment.CENTER);
		controls.setMargin(5);
		controls.setMembersMargin(5);
		final IButton printButton = new IButton("พิมพ์แผนการผลิค");
		printButton.setIcon("icons/16/print.png");
		printButton.setWidth(120);
		printButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
                SC.warn("click print");
            	//Canvas.showPrintPreview(PrintQuotation.getPrintContainer(record));
          }
        });
		if (edit || !status.equals("3_approved")) printButton.disable();
		
		final IButton saveButton = new IButton("บันทึกการแก้ไข");
		saveButton.setIcon("icons/16/save.png");
		saveButton.setWidth(120);
		saveButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	SC.confirm("ยืนยันการทำรายการ", "ต้องการบันทึกการแก้ไขแผนการผลิค หรือไม่?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
							//if (customerForm.validate()) saveQuotation(main, plan_id, customerForm, planListGrid, fromDate.getValueAsDate(), toDate.getValueAsDate(), deliveryDate.getValueAsDate(), currentUser);
							saveProductionPlan(main, plan_id, planListGrid, currentUser);
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
            	SC.confirm("ยืนยันการทำรายการ", "ต้องการอนุมัติแผนการผลิค หรือไม่?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
							//saveQuotation(main, plan_id, customerForm, planListGrid, fromDate.getValueAsDate(), toDate.getValueAsDate(), deliveryDate.getValueAsDate(), currentUser);
							updatePlanStatus(plan_id, "3_approved", "");
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
            	SC.confirm("ยืนยันการทำรายการ", "ต้องการส่งแผนการผลิคกลับไปแก้ไข หรือไม่?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
//							SC.askforValue("กรุณาใส่ความคิดเห็น", new ValueCallback() {
//								@Override
//								public void execute(String value) {
//									if (value == null || value.equals("")){
//										SC.warn("กรุณาใส่ความคิดเห็นในกล่องข้อความ");
//									} else {
//										updateQuoteStatus(plan_id, "1_waiting_for_revised", value);
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
					        selectOtherItem.setValueMap("รายการสินค้าไม่เหมาะสม", "จำนวนสินค้าไม่เหมาะสม", "แผนการผลิตซ้ำซ้อน");  
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
						            	updatePlanStatus(plan_id, "1_waiting_for_revised", selectOtherItem.getValueAsString());
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
		
		final IButton createSaleOrderButton = new IButton("สร้างคำสั่งผลิต");
		approveButton.setIcon("icons/16/approved.png");
		createSaleOrderButton.setWidth(120);
		createSaleOrderButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	SC.confirm("ยืนยันการทำรายการ", "ต้องการสร้างรายการขาย หรือไม่?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
							//createJobOrder(main, plan_id, sale_id, planListGrid, currentUser);	
						}
					}
            	});
          }
        });
		
		if (page == 3) {
			controls.addMember(createSaleOrderButton);
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
			controls.addMember(printButton);
			controls.addMember(saveButton);
		}
		controls.addMember(closeButton);
		layout.addMember(controls);
		
		addButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {
            	//SC.warn("add click " + planListGrid.getRecords().length);
            	ArrayList<String> selected = new ArrayList<String>();
            	for (ListGridRecord item : planListGrid.getRecords()) {
            		selected.add(item.getAttributeAsString("pid"));
            	}
            	addFunc.show(selected, planListGrid);
            }
        });
		
		delButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {
            	ListGridRecord selected = planListGrid.getSelectedRecord();
            	if (selected != null) {
            		//planListGrid.removeSelectedData();
            		selected.setAttribute("status", false);
            		planListGrid.updateData(selected);
            		planListGrid.removeSelectedData(new DSCallback() {
						@Override
						public void execute(DSResponse dsResponse, Object data,
								DSRequest dsRequest) {
								if (dsResponse.getStatus() != 0) {
									SC.warn("การลบสินค้าล้มเหลว");
								} else { 
									//summaryPriceRecalculate(planListGrid.getRecords(), summaryForm);
								}
						}
					}, null);
            	} else {
            		SC.warn("กรุณาเลือกรายการที่ต้องการลบ");
            	}
            }
        });
		
        planListGrid.addCellSavedHandler(new CellSavedHandler() {  
			@Override
			public void onCellSaved(CellSavedEvent event) {
				//summaryPriceRecalculate(planListGrid.getRecords(), summaryForm);
			}  
        });
        
//        planListGrid.addRemoveRecordClickHandler(new RemoveRecordClickHandler() {
//
//			@Override
//			public void onRemoveRecordClick(RemoveRecordClickEvent event) {
//				//System.out.println("onRemoveRecordClick getResultSize " +planListGrid.getResultSet().getResultSize());
//				System.out.println("onRemoveRecordClick getRecords " +planListGrid.getRecords().length);
//				summaryPriceRecalculate(planListGrid.getRecords(), summaryForm);
//			}
//        });
//        
//        planListGrid.addFetchDataHandler(new FetchDataHandler() {
//
//			@Override
//			public void onFilterData(FetchDataEvent event) {
//				//System.out.println("onFilterData getResultSize " +planListGrid.getResultSet().getResultSize());
//				System.out.println("onFilterData getRecords " +planListGrid.getRecords().length);
//				if (planListGrid.getRecords().length != 0) {
//					summaryPriceRecalculate(planListGrid.getRecords(), summaryForm);
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
	
	public void saveProductionPlan(final Window main, final String plan_id, ListGrid planListGrid, User currentUser){
		ListGridRecord[] all = planListGrid.getRecords();
		
		if (all.length == 0) {
			SC.warn("กรูณาเลือกรายการสินค้าอย่างน้อย 1 รายการ");
			return;
		}
		
		Double total_weight = 0.0;
		//Double total_netExclusive = 0.0;
		Integer total_amount = 0;
		//final String plan_id = "QA70" + Math.round((Math.random() * 100));
		final ArrayList<PlanProductDetails> productList = new ArrayList<PlanProductDetails>();
		
		for (ListGridRecord item : all){
			total_weight += item.getAttributeAsDouble("weight");
			total_amount += item.getAttributeAsInt("plan_amount");
			//total_netExclusive += item.getAttributeAsDouble("sum_price");
			
			String sub_plan_id = item.getAttributeAsString("sub_plan_id");
			String pid = item.getAttributeAsString("pid");
			String pname = item.getAttributeAsString("name");
			String ptype = item.getAttributeAsString("type");
			Double pweight = item.getAttributeAsDouble("weight");
			Integer pplan_amount = item.getAttributeAsInt("plan_amount");
			String punit = item.getAttributeAsString("unit");
			
			Double psize = item.getAttributeAsDouble("size");
			Double pwidth = item.getAttributeAsDouble("width");
			Double plength = item.getAttributeAsDouble("length");
			Double pheight = item.getAttributeAsDouble("height");
			Double pdiameter = item.getAttributeAsDouble("diameter");
			Double pthickness = item.getAttributeAsDouble("thickness");
			
			PlanProductDetails temp = new PlanProductDetails();
			temp.save(pid, pname, pweight, ptype, punit, psize, pwidth, plength, pheight, pdiameter, pthickness);
			temp.setID(sub_plan_id, plan_id);
			temp.setQuantity(pplan_amount);
			productList.add(temp);
		}
		//System.out.println(total_weight + " " + total_amount + " " + total_netExclusive);
			//status
			final String plan_status = "2_waiting_for_approved";
			
//			if (customer.getField("cid").getValue() == null || customer.getField("cus_name").getValue() == null) {
//				SC.warn("ชื่อและรหัสลูกค้าไม่ถุกต้อง");
//				return;
//			}
			
//			String cid = (String) customer.getField("cid").getValue();
//			String cus_name = (String) customer.getField("cus_name").getValue();
//			String payment_model = (String) customer.getField("payment_model").getValue();
//			Integer credit = (Integer) customer.getField("credit").getValue();
			//System.out.println(cid + " " + cus_name + " " + payment_model + " " + credit);
			
			ListGridRecord updateRecord = PlanData.createUpdateRecord(plan_id, total_weight, total_amount, new Date(), currentUser.getFirstName() + " " + currentUser.getLastName(), "", plan_status);
			
			PlanDS.getInstance().updateData(updateRecord, new DSCallback() {
				@Override
				public void execute(DSResponse dsResponse, Object data,
						DSRequest dsRequest) {
						//System.out.println("Test " + dsResponse.getStatus());
						if (dsResponse.getStatus() != 0) {
							SC.warn("การบันทึกแผนการผลิคล้มเหลว กรุณาทำรายการใหม่อีกครั้ง");
							main.destroy();
						} else { 
							for (PlanProductDetails item : productList) {
								if (item.sub_plan_id == null) {
									item.sub_plan_id = "QS80" + Math.round((Math.random() * 100));
									ListGridRecord subUpdateRecord = PlanProductData.createRecord(item);
									PlanProductDS.getInstance(plan_id).addData(subUpdateRecord);
								} else  {
									ListGridRecord subUpdateRecord = PlanProductData.createRecord(item);
									PlanProductDS.getInstance(plan_id).updateData(subUpdateRecord);
								}
							}
							SC.say("แก้ไขแผนการผลิคเสร็จสิ้น <br> " + "รหัสแผนการผลิค " + plan_id + "<br> สถานะของแผนการผลิค " + ProductionPlanStatus.getDisplay(plan_status));
							main.destroy();
						}
				}
			});
	}
	
	void updatePlanStatus(String plan_id, final String status, String comment) {
		Record updated = PlanData.createStatusRecord(plan_id,status,comment);
		PlanDS.getInstance().updateData(updated, new DSCallback() {
			@Override
			public void execute(DSResponse dsResponse, Object data,
					DSRequest dsRequest) {
					if (dsResponse.getStatus() != 0) {
						SC.warn("การอนุมัติแผนการผลิคล้มเหลว");
					} else { 
						SC.say("แก้ไขสถานะแผนการผลิต \"" + ProductionPlanStatus.getDisplay(status) + "\" เสร็จสิ้น");
					}
			}
		});
	}
	
//	public void createJobOrder(final Window main, final String plan_id, DynamicForm customer, ListGrid planListGrid, Date delivery, User currentUser, String purchase_id){
//		
//		ListGridRecord[] all = planListGrid.getRecords();
//		
////		if (all.length == 0) {
////			SC.warn("กรูณาเลือกรายการสินค้าอย่างน้อย 1 รายการ");
////			return;
////		}
//		
//		Double total_weight = 0.0;
//		Double total_netExclusive = 0.0;
//		Integer total_amount = 0;
//		final String sale_id = "SO70" + Math.round((Math.random() * 100));
//		final String invoice_id = "IN70" + Math.round((Math.random() * 100));
//		final ArrayList<SaleProductDetails> saleProductList = new ArrayList<SaleProductDetails>();
//		final ArrayList<SaleProductDetails> invoiceProductList = new ArrayList<SaleProductDetails>();
//
//		for (ListGridRecord item : all){
//			total_weight += item.getAttributeAsDouble("weight");
//			total_amount += item.getAttributeAsInt("quote_amount");
//			total_netExclusive += item.getAttributeAsDouble("sum_price");
//			
//			String pid = item.getAttributeAsString("pid");
//			String pname = item.getAttributeAsString("name");
//			String ptype = item.getAttributeAsString("type");
//			//String psize = item.getAttributeAsString("size");
//			Double pweight = item.getAttributeAsDouble("weight");
//			Integer psale_amount = item.getAttributeAsInt("quote_amount");
//			String punit = item.getAttributeAsString("unit");
//			Double pprice = item.getAttributeAsDouble("price");
//			
//			String sub_sale_id = "SS80" + Math.round((Math.random() * 1000));
//			SaleProductDetails temp1 = new SaleProductDetails();
//			temp1.save(pid, pname, pweight, pprice, ptype, punit);
//			temp1.setID(sub_sale_id, sale_id);
//			temp1.setQuantity(psale_amount);
//			saleProductList.add(temp1);
//			
//			String sub_invoice_id = "SI80" + Math.round((Math.random() * 1000));
//			SaleProductDetails temp2 = new SaleProductDetails();
//			temp2.save(pid, pname, pweight, pprice, ptype, punit);
//			temp2.setID(sub_invoice_id, invoice_id);
//			temp2.setQuantity(psale_amount);
//			invoiceProductList.add(temp2);
//			//status
//		}	
////			if (customer.getField("cid").getValue() == null || customer.getField("cus_name").getValue() == null) {
////				SC.warn("ชื่อและรหัสลูกค้าไม่ถุกต้อง");
////				return;
////			}
//
//			final String sale_status = "1_waiting_for_production";
//			final String invoice_status = "1_waiting_for_payment";
//			String cid = (String) customer.getField("cid").getValue();
//			String cus_name = (String) customer.getField("cus_name").getValue();
//			String payment_model = (String) customer.getField("payment_model").getValue();
//			Integer credit = (Integer) customer.getField("credit").getValue();
//			
//			DateRange dateRange = new DateRange();  
//	        dateRange.setRelativeStartDate(RelativeDate.TODAY);
//	        dateRange.setRelativeEndDate(new RelativeDate("+"+credit+"d"));
//	        final Date due_date = dateRange.getEndDate();
//	        
//			final ListGridRecord saleRecord = SaleOrderData.createRecord(sale_id, plan_id, invoice_id, cid, cus_name, payment_model, credit, delivery, total_weight, total_amount, total_netExclusive, new Date(), null, currentUser.getFirstName() + " " + currentUser.getLastName(), null, sale_status, purchase_id, due_date);
//			ListGridRecord invoiceRecord = InvoiceData.createRecord(invoice_id, sale_id, cid, cus_name, payment_model, credit, delivery, total_weight, total_amount, total_netExclusive, new Date(), null, currentUser.getFirstName() + " " + currentUser.getLastName(), null, invoice_status, purchase_id, due_date, null);
//			
//			//Auto create invoice
//			InvoiceDS.getInstance().addData(invoiceRecord, new DSCallback() {
//				@Override
//				public void execute(DSResponse dsResponse, Object data,
//						DSRequest dsRequest) {
//						if (dsResponse.getStatus() != 0) {
//							SC.warn("การสร้างรายการขายล้มเหลว กรุณาทำรายการใหม่อีกครั้ง");
//							main.destroy();
//						} else { 
//							for (SaleProductDetails item : invoiceProductList) {
//								ListGridRecord subAddRecord = SaleProductData.createRecord(item);
//								SaleProductDS.getInstance(invoice_id).addData(subAddRecord);
//							}
//							
//							SaleOrderDS.getInstance().addData(saleRecord, new DSCallback() {
//								@Override
//								public void execute(DSResponse dsResponse, Object data,
//										DSRequest dsRequest) {
//										if (dsResponse.getStatus() != 0) {
//											SC.warn("การสร้างรายการขายล้มเหลว กรุณาทำรายการใหม่อีกครั้ง");
//											main.destroy();
//										} else { 
//											for (SaleProductDetails item : saleProductList) {
//													ListGridRecord subAddRecord = SaleProductData.createRecord(item);
//													SaleProductDS.getInstance(sale_id).addData(subAddRecord);
//											}
//											SC.say("สร้างรายการขายเสร็จสิ้น <br> " + "รหัสรายการขาย " + sale_id + "<br> สถานะของรายการขาย " + SaleOrderStatus.getDisplay(sale_status) + "<br><br> สร้างใบแจ้งหนี้โดยอัตโนมัติ เลขที่ "+ invoice_id + "<br> กำหนดชำระเงินวันที่ " + DateUtil.formatAsShortDate(due_date));
//											main.destroy();
//										}
//								}
//							});
//						}
//				}
//			});
//	}
}
