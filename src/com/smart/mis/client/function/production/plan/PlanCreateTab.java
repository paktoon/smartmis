package com.smart.mis.client.function.production.plan;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.smart.mis.client.function.production.plan.product.PlanProductDS;
import com.smart.mis.client.function.production.plan.product.PlanProductData;
import com.smart.mis.client.function.production.plan.product.PlanProductDetails;
import com.smart.mis.client.function.production.product.ProductDS;
import com.smart.mis.client.function.sale.customer.CustomerDS;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.ListGridNumberField;
import com.smart.mis.shared.prodution.ProductType;
import com.smart.mis.shared.sale.Customer;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DateRange;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RelativeDate;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.RecordSummaryFunctionType;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SummaryFunctionType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SelectOtherItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
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
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class PlanCreateTab {
	
	Label userId;
	//DynamicForm customerForm, summaryForm, dateForm;
	DynamicForm productForm; 
	IButton addButton, delButton;
	ListGrid planListGrid;
	PlanProductDetails planProduct;
//	StaticTextItem netExclusive, tax, netInclusive;
//	ListGridSummaryField quoteItemCell_sum;
//	Customer client = new Customer();
	PlanProductDS planProductDS;
	
	public PlanCreateTab(){
		//For create tab
		planProductDS = new PlanProductDS();
		
		userId = new Label();

		createDynamicForm();
		//createSummaryField();
		
		addButton = new IButton("เพิ่มรายการสินค้า"); 
		delButton = new IButton("ลบรายการสินค้า"); 
		planListGrid = new ListGrid();
		planProduct = new PlanProductDetails();
		
		//quoteItemCell_sum = new ListGridSummaryField("sum_price", 100);
	}
	
	public Tab getCreateTab(final User currentUser){
		
		Tab createTab = new Tab("สร้างแผนการผลิต", "icons/16/icon_add_files.png");
		//TBD
		VLayout createLayout = new VLayout();
		createLayout.setWidth(750);
		createLayout.setHeight100();
		
		//******************Header
		HLayout headerLayout = new HLayout();
		headerLayout.setHeight(20);
		
		Label empty = new Label();
		empty.setWidth("*");
		headerLayout.addMember(empty);
		Label createDate = new Label();
		Date today = new Date();
		DateTimeFormat pattern = DateTimeFormat.getFormat("MM/dd/yyyy");
		createDate.setContents("วันที่สร้าง : " + pattern.format(today));
		createDate.setWidth("15%");
		createDate.setAlign(Alignment.RIGHT);
		headerLayout.addMember(createDate);
		createLayout.addMember(headerLayout);
		//******************End Header
		
//		//******************Customer
//		customerForm.setWidth100(); 
//		customerForm.setHeight(30);
//		customerForm.setMargin(5); 
//		customerForm.setNumCols(6); 
//		customerForm.setCellPadding(2);
//		customerForm.setAutoFocus(true);
//		customerForm.setSelectOnFocus(true);
//		//customerForm.setDataSource(CustomerDS.getInstance());
//		//customerForm.setUseAllDataSourceFields(false);
//		customerForm.setIsGroup(true);
//		customerForm.setGroupTitle("ข้อมูลลูกค้า");
//        
//		final StaticTextItem cid = new StaticTextItem("cid", "รหัสลูกค้า");
//		final SelectItem cus_name = new SelectItem("cus_name", "ชื่อลูกค้า");
//		cus_name.setOptionDataSource(CustomerDS.getInstance());
//		cus_name.setEmptyDisplayValue("--โปรดเลือกลูกค้า--");
//		cus_name.setPickListWidth(350);
//		cus_name.setWidth(240);
//		cus_name.setRequired(true);
//		cus_name.setHint("*");
//		ListGridField Field_1 = new ListGridField("cid", 80);  
//        ListGridField Field_2 = new ListGridField("cus_name", 200);  
//        ListGridField Field_3 = new ListGridField("cus_type", 70);
//        cus_name.setPickListFields(Field_1, Field_2, Field_3);
//        		
//		final StaticTextItem type = new StaticTextItem("cus_type", "ประเภทลูกค้า");
//		
//		final SelectItem paymentModel = new SelectItem("payment_model", "วิธีการชำระเงิน");
//		paymentModel.setValueMap("เงินสด", "แคชเชียร์เช็ค");
//		//paymentModel.setEmptyDisplayValue("--โปรดเลือกวิธีชำระเงิน--");
//		paymentModel.setDefaultValue("เงินสด");
//		final IntegerItem credit = new IntegerItem("credit","เครดิต");
//		credit.setRequired(true);
//		credit.setHint("วัน*");
//		credit.setWidth(100);
//		credit.setTextAlign(Alignment.LEFT);
//		credit.setDefaultValue(0);
//		paymentModel.disable();
//		credit.disable();
//        
//		cus_name.addChangedHandler(new ChangedHandler() {
//			@Override
//			public void onChanged(ChangedEvent event) {
//				Record selected = cus_name.getSelectedRecord();
//				if (selected != null) {
//					String customer_id = selected.getAttributeAsString("cid");
//					String customer_name = selected.getAttributeAsString("cus_name");
//					String customer_type = selected.getAttributeAsString("cus_type");
//					//Contact info
//					//String customer_address = selected.getAttributeAsString("address");
//					String customer_phone = selected.getAttributeAsString("cus_phone");
//					String contact_name = selected.getAttributeAsString("contact_name");
//					String contact_phone = selected.getAttributeAsString("contact_phone");
//					String contact_email = selected.getAttributeAsString("contact_email");
//					String zone = selected.getAttributeAsString("zone");
//					
//					client.setAttributes(customer_id, customer_name, customer_phone, contact_name, contact_phone, contact_email, customer_type, zone);
//					client.setPaymentModel(paymentModel.getValueAsString());
//					client.setCredit(credit.getValueAsInteger());
//					paymentModel.enable();
//					productForm.enable();
//					if(customer_type.equalsIgnoreCase("ลูกค้าประจำ")) {
//						credit.enable();
//					} else {
//						credit.setValue(0);
//						credit.disable();
//					}
//					cid.setValue(customer_id);
//					type.setValue(customer_type);
//				}
//			}
//        });
//		
//		paymentModel.addChangedHandler(new ChangedHandler() {
//			@Override
//			public void onChanged(ChangedEvent event) {
//				if (paymentModel.validate()) {
//					client.setPaymentModel(paymentModel.getValueAsString());
//				}
//			}
//        });
//		
//		credit.addChangedHandler(new ChangedHandler() {
//			@Override
//			public void onChanged(ChangedEvent event) {
//				if (credit.validate()) {
//					client.setCredit(credit.getValueAsInteger());
//				}
//			}
//        });
//		
//		customerForm.setFields(cid, cus_name, type, paymentModel, credit);
//		customerForm.setColWidths(100, 80, 80, 240, 100, 100);
//		createLayout.addMember(customerForm);
//		//******************End Customer
		
		//******************Product Header
		productForm.setWidth100(); 
		productForm.setMargin(5);  
		productForm.setNumCols(6);
		productForm.setCellPadding(2);
		productForm.setAutoFocus(false);  
        //productForm.setDataSource(ProductDS.getInstance());  
        //productForm.setUseAllDataSourceFields(false); 
		productForm.setIsGroup(true);
		productForm.setGroupTitle("เลือกสินค้าที่ต้องการผลิต");
        
        final StaticTextItem pid = new StaticTextItem("pid" , "รหัสสินค้า");
        final SelectItem pname = new SelectItem("name", "ชื่อสินค้า");
        pname.setOptionDataSource(ProductDS.getInstance());
        pname.setEmptyDisplayValue("--โปรดเลือกสินค้า--");
        pname.setPickListWidth(420);
        pname.setWidth(240);
        pname.setRequired(true);
		pname.setHint("*");
		ListGridField Field_M1 = new ListGridField("pid", 80);  
        ListGridField Field_M2 = new ListGridField("name", 200);
        ListGridField Field_M3 = new ListGridField("type", 70);
        ListGridField Field_M4 = new ListGridField("remain", 70);
        Field_M4.setCellFormatter(FieldFormatter.getNumberFormat());
        Field_M4.setAlign(Alignment.LEFT);
        pname.setPickListFields(Field_M1, Field_M2, Field_M3, Field_M4);
        
        final StaticTextItem ptype = new StaticTextItem("type", "ประเภทสินค้า");
        ptype.setValueMap(ProductType.getValueMap());
        
        final IntegerItem quantity = new IntegerItem("quantity", "จำนวน");
        quantity.setRequired(true);
        quantity.setHint("*");
        quantity.disable();
        quantity.setWidth(100);
        quantity.setTextAlign(Alignment.LEFT);
        
        final StaticTextItem pname_th = new StaticTextItem("name_th", "คำอธิบาย");
        final StaticTextItem pweight = new StaticTextItem("weight" , "น้ำหนัก");
		
        pname.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				Record selected = pname.getSelectedRecord();
				if (selected != null) {
					
					addButton.enable();
					String product_id = selected.getAttributeAsString("pid");
					String product_name = selected.getAttributeAsString("name");
					String product_name_th = selected.getAttributeAsString("name_th");
					//Contact info
					Double product_weight = selected.getAttributeAsDouble("weight");
					String product_type = selected.getAttributeAsString("type");
					String product_unit = selected.getAttributeAsString("unit");
					
					Double product_size = selected.getAttributeAsDouble("size");
					Double product_width = selected.getAttributeAsDouble("width");
					Double product_length = selected.getAttributeAsDouble("length");
					Double product_height = selected.getAttributeAsDouble("height");
					Double product_diameter = selected.getAttributeAsDouble("diameter");
					Double product_thinkness = selected.getAttributeAsDouble("thickness");
					
					quantity.setHint(product_unit + "*");
					pweight.setHint("กรัม ต่อ "+product_unit);
					//unit.setValue(product_unit + "*");
					quantity.enable();
					pname.setValue(product_name);
					pid.setValue(product_id);
					pname_th.setValue(product_name_th);
					ptype.setValue(product_type);
					pweight.setValue(product_weight);
				
					planProduct.save(product_id, product_name, product_weight, product_type, product_unit, product_size, product_width, product_length, product_height, product_diameter, product_thinkness);
				}
			}
        });
        productForm.setFields(pid, pname, pweight, ptype, pname_th , quantity);
        productForm.setColWidths(100, 80, 80, 240, 100, 100);
        //productForm.disable();
		createLayout.addMember(productForm);
		//******************End Product Header
		
		//******************Add button
		HLayout buttonLayout = new HLayout();
		buttonLayout.setMargin(10);
		buttonLayout.setMembersMargin(5);
		buttonLayout.setHeight(30);
		
		addButton.setIcon("[SKIN]actions/add.png");
		addButton.setWidth(120);
		addButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {
	            if (productForm.validate() && planProduct.check()) {
					ListGridRecord addProduct = planProduct.convertToRecord(quantity.getValueAsInteger());
					planProductDS.addData(addProduct, new DSCallback() {
						@Override
						public void execute(DSResponse dsResponse, Object data,
								DSRequest dsRequest) {
								if (dsResponse.getStatus() != 0) {
									SC.warn("การเพิ่มสินค้าล้มเหลว มีสินค้านี้อยู่แล้ว");
								} else { 
									planListGrid.fetchData();
					            	addButton.disable();
					            	productForm.reset();
					            	planProduct.clear();
					            	quantity.setHint("*");
					            	pweight.setHint("");
									
									//summaryPriceRecalculate();
								}
						}
					});
	            }  else {
	            	SC.warn("ข้อมูลสินค้าไม่ถูกต้อง กรุณาตรวจสอบรายการอีกครั้ง");
	            }
            }
        });
		addButton.disable();
		
		delButton.setIcon("icons/16/delete.png");
		delButton.setWidth(120);
		delButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {
            	if (planListGrid.getSelectedRecord() != null) {
            		//planListGrid.removeSelectedData();
            		planListGrid.removeSelectedData(new DSCallback() {
						@Override
						public void execute(DSResponse dsResponse, Object data,
								DSRequest dsRequest) {
								if (dsResponse.getStatus() != 0) {
									SC.warn("การลบสินค้าล้มเหลว");
								} else { 
									//summaryPriceRecalculate();
								}
						}
					}, null);
            	} else {
            		SC.warn("กรุณาเลือกรายการที่ต้องการลบ");
            	}
            }
        });
		
		buttonLayout.addMember(addButton);
		buttonLayout.addMember(delButton);
		buttonLayout.addMember(empty);
		//******************End Add
		
		//******************Quote List Grid
		HLayout itemLayout = new HLayout();
		itemLayout.setHeight(350);
		//itemLayout.setMargin(5);
		planListGrid.setWidth100();
		planListGrid.setHeight(400);
		planListGrid.setAlternateRecordStyles(true);  
		planListGrid.setShowAllRecords(true);  
		planListGrid.setAutoFetchData(true);  
		planListGrid.setSelectionType(SelectionStyle.SINGLE);
		planListGrid.setCanResizeFields(false);
		planListGrid.setShowGridSummary(true);
		planListGrid.setEditEvent(ListGridEditEvent.CLICK);  
		planListGrid.setListEndEditAction(RowEndEditAction.NEXT);
		planListGrid.setShowRowNumbers(true);
		
		//planListGrid.setCanRemoveRecords(true);
		//planListGrid.setWarnOnRemoval(true);
		//planListGrid.setWarnOnRemovalMessage("คุณต้องการลบ รายการสินค้า หรือไม่?");
		
		planListGrid.setDataSource(planProductDS);
		planListGrid.setUseAllDataSourceFields(false);
        
		ListGridField quoteItemCell_1 = new ListGridField("pid", 75);
        ListGridField quoteItemCell_2 = new ListGridField("name");  
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
        
//        ListGridField quoteItemCell_5 = new ListGridField("price", 90);
//        //quoteItemCell_5.setSummaryFunction(SummaryFunctionType.SUM);
//        quoteItemCell_5.setShowGridSummary(false);
//        quoteItemCell_5.setCellFormatter(FieldFormatter.getPriceFormat());
//        quoteItemCell_5.setAlign(Alignment.RIGHT);
        
        ListGridNumberField quoteItemCell_6 = new ListGridNumberField("plan_amount", 90);
        
        quoteItemCell_6.setCanEdit(true);
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
 
        planListGrid.addCellSavedHandler(new CellSavedHandler() {  
			@Override
			public void onCellSaved(CellSavedEvent event) {
				//summaryPriceRecalculate();
			}  
        });
        
        //planListGrid.setFields(quoteItemCell_1, quoteItemCell_2, quoteItemCell_3, quoteItemCell_4, quoteItemCell_5, quoteItemCell_6, quoteItemCell_sum);
        //planListGrid.setFields(quoteItemCell_1, quoteItemCell_2, quoteItemCell_4, quoteItemCell_6, quoteItemCell_3, quoteItemCell_5 , quoteItemCell_sum);
        planListGrid.setFields(quoteItemCell_1, quoteItemCell_2, quoteItemCell_7, quoteItemCell_4, quoteItemCell_6, quoteItemCell_3);
        
		itemLayout.addMember(planListGrid);
		
		//******************End Product Grid
		
		//******************Condition
//		HLayout footerLayout = new HLayout();
//		footerLayout.setHeight(100);
//		dateForm.setWidth(300);
//		dateForm.setNumCols(2);
//		dateForm.setMargin(5);
//		dateForm.setIsGroup(true);
//		dateForm.setGroupTitle("ข้อกำหนดใบเสนอราคา");
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
//		DateRange dateRange = new DateRange();  
//        dateRange.setRelativeStartDate(RelativeDate.TODAY);
//        dateRange.setRelativeEndDate(new RelativeDate("+7d"));
//        fromDate.setDefaultChooserDate(dateRange.getStartDate());
//        fromDate.setDefaultValue(dateRange.getStartDate());
//        toDate.setDefaultChooserDate(dateRange.getEndDate());
//        toDate.setDefaultValue(dateRange.getEndDate());
//        dateRange.setRelativeEndDate(new RelativeDate("+1m"));
//        deliveryDate.setDefaultChooserDate(dateRange.getEndDate());
//        deliveryDate.setDefaultValue(dateRange.getEndDate());
//        
//        fromDate.setRequired(true);
//        fromDate.setHint("*");
//		toDate.setRequired(true);
//		toDate.setHint("*");
//		deliveryDate.setRequired(true);
//		deliveryDate.setHint("*");
//		
//		dateForm.setFields(fromDate, toDate, deliveryDate);
//		dateForm.setColWidths(125,125 );
//		footerLayout.addMember(dateForm);
//		//******************End
//		
//		//******************Summary
//		summaryForm.setWidth(300);
//		summaryForm.setNumCols(2);
//		summaryForm.setMargin(5);
//		summaryForm.setIsGroup(true);
//		summaryForm.setGroupTitle("สรุปยอดรวม");
//		summaryForm.setColWidths(100, 100);
////		StaticTextItem netExclusive = new StaticTextItem("ราคารวม");
////		StaticTextItem tax = new StaticTextItem("ภาษีมูลค่าเพิ่ม (7%)");
////		StaticTextItem netInclusive = new StaticTextItem("ราคาสุทธิ");
//		summaryForm.setFields(netExclusive, tax, netInclusive);
//		footerLayout.addMember(summaryForm);
//		//******************End Summary
		
		//******************Control
		VLayout controlLayout = new VLayout();
		controlLayout.setWidth("*");
		controlLayout.setMargin(10);
		controlLayout.setMembersMargin(5);
		
		IButton saveButton = new IButton("savePlan");
		saveButton.setTitle("บันทึกแผนการผลิต");
		saveButton.setWidth(140);
		//saveButton.setHeight(40);
		saveButton.setIcon("icons/16/save.png");
		saveButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				//quoteItem
				//if (customerForm.validate() && dateForm.validate()) {
					SC.confirm("ยืนยันการสร้างแผนการผลิต", "ต้องการสร้างแผนการผลิต หรือไม่?" , new BooleanCallback() {
						@Override
						public void execute(Boolean value) {
							if (value) {
								
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
						        selectOtherItem.setEmptyDisplayValue("---โปรดระบุเหตุผล---");
						        selectOtherItem.setTitle("เหตุผลในการผลิตสินค้า");  
						        selectOtherItem.setValueMap("สินค้ามีปริมาณต่ำกว่าที่ควรจะเป็น", "สินค้าขายดี", "สินค้าขาดตลาด");  
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
											createProductionPlan(currentUser.getFirstName() + " " + currentUser.getLastName(), value);
											confirm.destroy();
						            	} else {
						            		SC.warn("กรูณาใส่เหตุผล");
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
				//}
			}
		});
		IButton clearButton = new IButton("clearPlan");
		clearButton.setTitle("ล้างข้อมูลแผนการผลิต");
		clearButton.setIcon("icons/16/trash-icon.png");
		clearButton.setWidth(140);
		//clearButton.setHeight(40);
		clearButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				SC.confirm("ยืนยันการทำรายการ", "ต้องการล้างข้อมูลแผนการผลิตทั้งหมด หรือไม่?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
							clearAll();
						}
					}
            	});
			}
			
		});
		
//		controlLayout.addMember(saveButton);
//		controlLayout.addMember(clearButton);
//		footerLayout.addMember(controlLayout);		
		buttonLayout.addMember(saveButton);
		buttonLayout.addMember(clearButton);
		//******************End Control
		
		//createLayout.addMember(footerLayout);
		createLayout.addMember(buttonLayout);
		createLayout.addMember(itemLayout);
		
		createTab.setPane(createLayout);
		
		return createTab;
	}
	
//	private void summaryPriceRecalculate(){
//		ListGridRecord[] all = planListGrid.getRecords();
//		Double sum_price = 0.0;
//		for (ListGridRecord record : all) {
//			sum_price += record.getAttributeAsDouble("sum_price");
//		}
//		NumberFormat nf = NumberFormat.getFormat("#,##0.00");
//		netExclusive.setValue(nf.format(sum_price));
//		tax.setValue(nf.format(sum_price * 0.07));
//		netInclusive.setValue(nf.format(sum_price * 1.07));
//	}
	
//	private void createSummaryField() {
//		netExclusive = new StaticTextItem("netExclusive");
//		tax = new StaticTextItem("tax");
//		netInclusive = new StaticTextItem("netInclusive");
//		
//		netExclusive.setWidth(100);
//		tax.setWidth(100);
//		netInclusive.setWidth(100);
//		
//		netExclusive.setTitle("ราคารวม");
//		tax.setTitle("ภาษีมูลค่าเพิ่ม (7%)");
//		netInclusive.setTitle("ราคาสุทธิ");
//		
//		netExclusive.setDefaultValue("0.00");
//		tax.setDefaultValue("0.00");
//		netInclusive.setDefaultValue("0.00");
//		
//		netExclusive.setHint("บาท");
//		tax.setHint("บาท");
//		netInclusive.setHint("บาท");
//		
//		netExclusive.setTextAlign(Alignment.RIGHT);
//		tax.setTextAlign(Alignment.RIGHT);
//		netInclusive.setTextAlign(Alignment.RIGHT);
//	}
	
	private void createDynamicForm() {
		//customerForm = new DynamicForm();
		productForm = new DynamicForm(); 
		//summaryForm = new DynamicForm();
		//dateForm = new DynamicForm();
		
		//customerForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
		productForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
		//dateForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
	}
	
	private void clearAll() {
		
		for (ListGridRecord n : planListGrid.getRecords()) {
			planListGrid.removeData(n);
		}
		
		planProduct.clear();
		//customerForm.reset();
		productForm.reset();
		//summaryForm.reset();
		//dateForm.reset();
		
		addButton.disable();
	}
	
	private void createProductionPlan(String user, String reason) {
		ListGridRecord[] all = planListGrid.getRecords();
		
		if (all.length == 0) {
			SC.warn("กรูณาเลือกรายการสินค้าอย่างน้อย 1 รายการ");
			return;
		}
		
		//SC.warn("numOfRecord : " + all.length);
		Double total_weight = 0.0;
		//Double total_netExclusive = 0.0;
		Integer total_amount = 0;
		final String plan_id = "PL70" + Math.round((Math.random() * 100));
		final ArrayList<PlanProductDetails> productList = new ArrayList<PlanProductDetails>();
		for (ListGridRecord item : all){
			total_weight += item.getAttributeAsDouble("weight");
			total_amount += item.getAttributeAsInt("plan_amount");
			//total_netExclusive += item.getAttributeAsDouble("sum_price");
			
			String sub_plan_id = "SP80" + Math.round((Math.random() * 100));
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
		//String cid = client.cid;
//		Date from = fromDate.getValueAsDate();
//		Date to = toDate.getValueAsDate();
		Date delivery = null;
		String quote_status = "2_waiting_for_approved";
		//xxxService.xxx(Callback quoteId);
		ListGridRecord newRecord = PlanData.createRecord(plan_id, null, delivery, total_weight, total_amount, new Date(), null, user, null, "", quote_status, reason);
		// client; - cid
		// DateForm; - from , to
		// SummaryForm; - netExclusive, tax, netInclusive
		// --> Quote Object + ItemQuote Object
		// Quote - quote id , cid , from, to , total_weight, total_amount, netExclusive, tax, netInclusive, created_date, modified_date, created_by, modified_by, status [waiting_for_approved, waiting_for_revised, approved, removed] --> to data store
		// ItemQuote - item quote id, quote id, pid, amount, total_price, status (0,1) --> to date store
		PlanDS.getInstance().addData(newRecord, new DSCallback() {
			@Override
			public void execute(DSResponse dsResponse, Object data,
					DSRequest dsRequest) {
					if (dsResponse.getStatus() != 0) {
						SC.warn("การสร้างแผนการผลิตล้มเหลว");
					} else { 
						for (PlanProductDetails item : productList) {
							ListGridRecord subNewRecord = PlanProductData.createRecord(item);
							PlanProductDS.getInstance(plan_id).addData(subNewRecord);
							//System.out.println("add data " + item.sub_plan_id);
						}
						SC.say("สร้างแผนการผลิตเสร็จสิ้น <br> " + "รหัสแผนการผลิต " + plan_id);
						clearAll();
					}
			}
		});
	}
}
