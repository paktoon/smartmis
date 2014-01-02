package com.smart.mis.client.function.purchasing.request;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.smart.mis.client.function.purchasing.material.MaterialDS;
import com.smart.mis.client.function.purchasing.request.material.RequestMaterialDS;
import com.smart.mis.client.function.purchasing.request.material.RequestMaterialData;
import com.smart.mis.client.function.purchasing.request.material.RequestMaterialDetails;
import com.smart.mis.client.function.purchasing.supplier.SupplierDS;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.ListGridNumberField;
import com.smart.mis.shared.ValidatorFactory;
import com.smart.mis.shared.prodution.ProductType;
import com.smart.mis.shared.purchasing.Supplier;
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
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.DoubleItem;
import com.smartgwt.client.widgets.form.fields.FloatItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
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

public class RequestCreateTab {
	
//	public static QuoteCreateTab instance;
	
	Label userId;
	DynamicForm supplierForm, summaryForm, conditionForm;
	DynamicForm materialForm; 
	IButton addButton, delButton;
	ListGrid requestListGrid;
	RequestMaterialDetails requestMaterial;
	StaticTextItem netExclusive, tax, netInclusive;
	ListGridSummaryField requestItemCell_sum;
	Supplier client = new Supplier();
	RequestMaterialDS requestDS;
	String material_list = null;
	
	MaterialDS tempDS = new MaterialDS("");
	
	public RequestCreateTab(){
		//For create tab
		requestDS = new RequestMaterialDS();
		
		userId = new Label();

		createDynamicForm();
		createSummaryField();
		
		addButton = new IButton("เพิ่มรายการวัตถุดิบ"); 
		delButton = new IButton("ลบรายการวัตถุดิบ"); 
		requestListGrid = new ListGrid();
		requestMaterial = new RequestMaterialDetails();
		
		requestItemCell_sum = new ListGridSummaryField("sum_price", 100);
	}
	
	public Tab getCreateTab(final User currentUser){
		
		Tab createTab = new Tab("จัดทำใบเสนอซื้อ", "icons/16/icon_add_files.png");
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
		
		//******************Condition
		conditionForm.setWidth100();
		conditionForm.setNumCols(6);
		conditionForm.setMargin(5);
		conditionForm.setIsGroup(true);
		conditionForm.setGroupTitle("ข้อกำหนดใบเสนอราคา");
				
		final TextItem quote_id = new TextItem();
		quote_id.setTitle("รหัสใบเสนอราคา");

		final SelectItem paymentModel = new SelectItem("payment_model", "วิธีการชำระเงิน");
		paymentModel.setValueMap("เงินสด", "แคชเชียร์เช็ค");
		paymentModel.setEmptyDisplayValue("--โปรดเลือกวิธีชำระเงิน--");
		
		final IntegerItem credit = new IntegerItem("credit","เครดิต");
		credit.setRequired(true);
		credit.setHint("วัน*");
		credit.setWidth(100);
		credit.setTextAlign(Alignment.LEFT);
		credit.setDefaultValue(0);
		
		final DateItem deliveryDate = new DateItem();
		deliveryDate.setName("deliveryDate");
		deliveryDate.setTitle("วันที่กำหนดส่งของ");
		deliveryDate.setUseTextField(true);
				
		final DateItem fromDate = new DateItem();
		fromDate.setName("fromDate");
		fromDate.setTitle("วันที่เริ่มข้อเสนอ");
		fromDate.setUseTextField(true);
				
		final DateItem toDate = new DateItem();
		toDate.setName("toDate");
		toDate.setTitle("วันที่สิ้นสุดข้อเสนอ");
		toDate.setUseTextField(true);
				
		quote_id.setRequired(true);
		quote_id.setHint("*");
		fromDate.setRequired(true);
		fromDate.setHint("*");
		toDate.setRequired(true);
		toDate.setHint("*");
		deliveryDate.setRequired(true);
		deliveryDate.setHint("*");
				
		conditionForm.setFields(quote_id, paymentModel, credit, deliveryDate, fromDate, toDate);
		conditionForm.setColWidths(125,125,125,125,125,125);
		createLayout.addMember(conditionForm);
		//******************End
				
		//******************Supplier
		supplierForm.setWidth100(); 
		supplierForm.setHeight(30);
		supplierForm.setMargin(5); 
		supplierForm.setNumCols(6); 
		supplierForm.setCellPadding(2);
		supplierForm.setAutoFocus(true);
		supplierForm.setSelectOnFocus(true);
		//supplierForm.setDataSource(SupplierDS.getInstance());
		//supplierForm.setUseAllDataSourceFields(false);
		supplierForm.setIsGroup(true);
		supplierForm.setGroupTitle("ข้อมูลผู้จำหน่าย");
        
		final StaticTextItem sid = new StaticTextItem("sid", "รหัสผู้จำหน่าย");
		final SelectItem sup_name = new SelectItem("sup_name", "ชื่อผู้จำหน่าย");
		sup_name.setOptionDataSource(SupplierDS.getInstance());
		sup_name.setEmptyDisplayValue("--โปรดเลือกผู้จำหน่าย--");
		sup_name.setPickListWidth(280);
		sup_name.setWidth(240);
		sup_name.setRequired(true);
		sup_name.setHint("*");
		ListGridField Field_1 = new ListGridField("sid", 80);  
        ListGridField Field_2 = new ListGridField("sup_name", 200);  
        //ListGridField Field_3 = new ListGridField("cus_type", 70);
        sup_name.setPickListFields(Field_1, Field_2);
        		
        final StaticTextItem sup_phone1 = new StaticTextItem("sup_phone1", "โทรศัทท์ 1");
        final StaticTextItem sup_phone2 = new StaticTextItem("sup_phone2", "โทรศัทท์ 2");
        final StaticTextItem sup_fax = new StaticTextItem("fax", "โทรสาร");
        final StaticTextItem sup_email = new StaticTextItem("email", "อีเมล");
        final StaticTextItem sup_address = new StaticTextItem("address", "ที่อยู่");
        sup_address.setColSpan(6);
        
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

					sid.setValue(supplier_id);
					sup_name.setValue(supplier_name);
					
					sup_phone1.setValue(supplier_phone1);
					sup_phone2.setValue(supplier_phone2);
					sup_email.setValue(supplier_email);
					sup_address.setValue(fullAddress);
					sup_fax.setValue(supplier_fax);
					
					materialForm.enable();
					System.out.println(material_list);
					//tempDS.fetchData(new Criterion("mid", OperatorId.REGEXP, material_list));
					tempDS.fetchData(new Criterion("mid", OperatorId.REGEXP, material_list), new DSCallback() {
						@Override
						public void execute(DSResponse dsResponse, Object data,
								DSRequest dsRequest) {
							dsResponse.setInvalidateCache(true);
							tempDS.updateCaches(dsResponse, dsRequest);
						}
			    	});
				}
			}
        });
		
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
		
		supplierForm.setFields(sid, sup_name, sup_email, sup_phone1, sup_phone2, sup_fax, sup_address);
		supplierForm.setColWidths(100, 80, 80, 200, 100, 140);
		createLayout.addMember(supplierForm);
		//******************End Supplier
		
		//******************Product Header
		materialForm.setWidth100(); 
		materialForm.setMargin(5);  
		materialForm.setNumCols(6);
		materialForm.setCellPadding(2);
		materialForm.setAutoFocus(false);  
        //materialForm.setDataSource(ProductDS.getInstance());  
        //materialForm.setUseAllDataSourceFields(false); 
		materialForm.setIsGroup(true);
		materialForm.setGroupTitle("เลือกวัตถุดิบ");
        
        final StaticTextItem mid = new StaticTextItem("mid" , "รหัสวัตถุดิบ");
        final SelectItem pname = new SelectItem("mat_name", "ชื่อวัตถุดิบ");
        //pname.setOptionDataSource(MaterialDS.getInstance());
        pname.setOptionDataSource(tempDS);
        pname.setEmptyDisplayValue("--โปรดเลือกวัตถุดิบ--");
        //pname.setOptionCriteria(new Criterion("type", OperatorId.REGEXP, material_list));
        pname.setPickListWidth(420);
        pname.setWidth(240);
        pname.setRequired(true);
		pname.setHint("*");
		ListGridField Field_M1 = new ListGridField("mid", 80);  
        ListGridField Field_M2 = new ListGridField("mat_name", 180);
        ListGridField Field_M3 = new ListGridField("type", 70);
        ListGridField Field_M4 = new ListGridField("remain", 90);
        Field_M4.setCellFormatter(FieldFormatter.getNumberFormat());
        Field_M4.setAlign(Alignment.LEFT);
        pname.setPickListFields(Field_M1, Field_M2, Field_M3, Field_M4);
        
        final StaticTextItem ptype = new StaticTextItem("type", "ประเภทวัตถุดิบ");
        ptype.setValueMap(ProductType.getValueMap());
        
        final DoubleItem quantity = new DoubleItem("quantity", "จำนวน");
        quantity.setRequired(true);
        quantity.setHint("*");
        quantity.disable();
        quantity.setWidth(100);
        quantity.setTextAlign(Alignment.LEFT);
        //quantity.setValidators(ValidatorFactory.integerRange(50, null));
        
        final StaticTextItem pname_th = new StaticTextItem("desc", "คำอธิบาย");
        
        final DoubleItem pprice = new DoubleItem("price" , "ราคา");
        pprice.setRequired(true);
        pprice.setHint("*");
        pprice.disable();
        pprice.setWidth(50);
        pprice.setTextAlign(Alignment.LEFT);
        
        pname.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				Record selected = pname.getSelectedRecord();
				if (selected != null) {
					
					addButton.enable();
					String material_id = selected.getAttributeAsString("mid");
					String material_name = selected.getAttributeAsString("mat_name");
					String material_desc = selected.getAttributeAsString("desc");
					//Contact info
					Double material_weight = selected.getAttributeAsDouble("weight");
					//Double material_price = selected.getAttributeAsDouble("price");
					String material_type = selected.getAttributeAsString("type");
					
					//Double material_remain = selected.getAttributeAsDouble("remain");
					String material_unit = selected.getAttributeAsString("unit");
					//Boolean material_inStock = selected.getAttributeAsBoolean("inStock");
					
					quantity.setHint(material_unit + "*");
					pprice.setHint("บาท ต่อ "+material_unit);
					//unit.setValue(material_unit + "*");
					quantity.enable();
					pprice.enable();
					pname.setValue(material_name);
					mid.setValue(material_id);
					pname_th.setValue(material_desc);
					ptype.setValue(material_type);
					//pprice.setValue(material_price);
				
					System.out.println(material_id + " " + material_name + " " + material_weight + " " + material_type + " " + material_unit);
					requestMaterial.save(material_id, material_name, material_weight, material_type, material_unit);
				}
			}
        });
        materialForm.setFields(mid, pname, pprice, ptype, pname_th , quantity);
        materialForm.setColWidths(100, 80, 80, 240, 100, 100);
        materialForm.disable();
		createLayout.addMember(materialForm);
		//******************End Product Header
		
		//******************Add button
		HLayout buttonLayout = new HLayout();
		buttonLayout.setMargin(10);
		buttonLayout.setMembersMargin(5);
		buttonLayout.setHeight(30);
		buttonLayout.setWidth100();
		
		addButton.setIcon("[SKIN]actions/add.png");
		addButton.setWidth(120);
		addButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {
	            if (materialForm.validate() && requestMaterial.check()) {
					ListGridRecord addProduct = requestMaterial.convertToRecord(quantity.getValueAsDouble(), pprice.getValueAsDouble());
					requestDS.addData(addProduct, new DSCallback() {
						@Override
						public void execute(DSResponse dsResponse, Object data,
								DSRequest dsRequest) {
								if (dsResponse.getStatus() != 0) {
									SC.warn("การเพิ่มสินค้าล้มเหลว มีสินค้านี้อยู่แล้ว");
								} else { 
									requestListGrid.fetchData();
					            	addButton.disable();
					            	materialForm.reset();
					            	requestMaterial.clear();
					            	quantity.setHint("*");
									pprice.setHint("");
									
									summaryPriceRecalculate();
								}
						}
					});
	            }  else {
	            	SC.warn("ข้อมูลวัตถุดิบไม่ถูกต้อง กรุณาตรวจสอบรายการอีกครั้ง");
	            }
            }
        });
		addButton.disable();
		
		delButton.setIcon("icons/16/delete.png");
		delButton.setWidth(120);
		delButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {
            	if (requestListGrid.getSelectedRecord() != null) {
            		//requestListGrid.removeSelectedData();
            		requestListGrid.removeSelectedData(new DSCallback() {
						@Override
						public void execute(DSResponse dsResponse, Object data,
								DSRequest dsRequest) {
								if (dsResponse.getStatus() != 0) {
									SC.warn("การลบสินค้าล้มเหลว");
								} else { 
									summaryPriceRecalculate();
								}
						}
					}, null);
            	} else {
            		SC.warn("กรุณาเลือกรายการที่ต้องการลบ");
            	}
            }
        });
		
		IButton saveButton = new IButton("saveRequest");
		saveButton.setTitle("บันทึกใบเสนอซื้อ");
		saveButton.setWidth(140);
		saveButton.setIcon("icons/16/save.png");
		
		IButton clearButton = new IButton("clearQuote");
		clearButton.setTitle("ล้างข้อมูลใบเสนอซื้อ");
		clearButton.setIcon("icons/16/trash-icon.png");
		clearButton.setWidth(140);
		
		buttonLayout.addMember(addButton);
		buttonLayout.addMember(delButton);
		buttonLayout.addMember(empty);
		buttonLayout.addMember(saveButton);
		buttonLayout.addMember(clearButton);
		createLayout.addMember(buttonLayout);
		//******************End Add
		
		//******************Quote List Grid
		HLayout itemLayout = new HLayout();
		itemLayout.setHeight(350);
		//itemLayout.setMargin(5);
		requestListGrid.setWidth100();
		requestListGrid.setHeight(350);
		requestListGrid.setAlternateRecordStyles(true);  
		requestListGrid.setShowAllRecords(true);  
		requestListGrid.setAutoFetchData(true);  
		requestListGrid.setSelectionType(SelectionStyle.SINGLE);
		requestListGrid.setCanResizeFields(false);
		requestListGrid.setShowGridSummary(true);
		requestListGrid.setEditEvent(ListGridEditEvent.CLICK);  
		requestListGrid.setListEndEditAction(RowEndEditAction.NEXT);
		requestListGrid.setShowRowNumbers(true);
		
		//requestListGrid.setCanRemoveRecords(true);
		//requestListGrid.setWarnOnRemoval(true);
		//requestListGrid.setWarnOnRemovalMessage("คุณต้องการลบ รายการสินค้า หรือไม่?");
		
		requestListGrid.setDataSource(requestDS);
		requestListGrid.setUseAllDataSourceFields(false);
        
		ListGridField requestItemCell_1 = new ListGridField("mid", 75);
        ListGridField requestItemCell_2 = new ListGridField("mat_name");  
        requestItemCell_2.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });  
        requestItemCell_2.setShowGridSummary(true);
		
        ListGridField requestItemCell_3 = new ListGridField("unit", 50);
        
        ListGridNumberField requestItemCell_4 = new ListGridNumberField("weight", 90);
        requestItemCell_4.setSummaryFunction(SummaryFunctionType.SUM);
        requestItemCell_4.setShowGridSummary(true);
        requestItemCell_4.setIncludeInRecordSummary(false);
        
        ListGridField requestItemCell_5 = new ListGridField("price", 90);
        //requestItemCell_5.setSummaryFunction(SummaryFunctionType.SUM);
        requestItemCell_5.setShowGridSummary(false);
        requestItemCell_5.setCellFormatter(FieldFormatter.getPriceFormat());
        requestItemCell_5.setAlign(Alignment.RIGHT);
        
        ListGridNumberField requestItemCell_6 = new ListGridNumberField("request_amount", 80);
        
        requestItemCell_6.setCanEdit(true);
        requestItemCell_6.setSummaryFunction(SummaryFunctionType.SUM);
        requestItemCell_6.setShowGridSummary(true);
        
        ListGridSummaryField requestItemCell_sum = new ListGridSummaryField("sum_price", 100);

        requestItemCell_sum.setRecordSummaryFunction(RecordSummaryFunctionType.MULTIPLIER);
        requestItemCell_sum.setSummaryFunction(SummaryFunctionType.SUM);
        requestItemCell_sum.setShowGridSummary(true);
        requestItemCell_sum.setCellFormatter(FieldFormatter.getPriceFormat());
        requestItemCell_sum.setAlign(Alignment.RIGHT);
 
        requestListGrid.addCellSavedHandler(new CellSavedHandler() {  
			@Override
			public void onCellSaved(CellSavedEvent event) {
				summaryPriceRecalculate();
			}  
        });
        
        //requestListGrid.setFields(requestItemCell_1, requestItemCell_2, requestItemCell_3, requestItemCell_4, requestItemCell_5, requestItemCell_6, requestItemCell_sum);
        requestListGrid.setFields(requestItemCell_1, requestItemCell_2, requestItemCell_6, requestItemCell_3, requestItemCell_4, requestItemCell_5 , requestItemCell_sum);
        
		itemLayout.addMember(requestListGrid);
		createLayout.addMember(itemLayout);
		//******************End Product Grid
		
		//******************Condition
		HLayout footerLayout = new HLayout();
		footerLayout.setHeight(100);
		
//		conditionForm.setWidth(300);
//		conditionForm.setNumCols(2);
//		conditionForm.setMargin(5);
//		conditionForm.setIsGroup(true);
//		conditionForm.setGroupTitle("ข้อกำหนดใบเสนอราคา");
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
//		conditionForm.setFields(fromDate, toDate, deliveryDate);
//		conditionForm.setColWidths(125,125 );
//		footerLayout.addMember(conditionForm);
//		//******************End
		
		//******************Summary
		summaryForm.setWidth(300);
		summaryForm.setNumCols(2);
		summaryForm.setMargin(5);
		summaryForm.setIsGroup(true);
		summaryForm.setGroupTitle("สรุปยอดรวม");
		summaryForm.setColWidths(100, 100);
//		StaticTextItem netExclusive = new StaticTextItem("ราคารวม");
//		StaticTextItem tax = new StaticTextItem("ภาษีมูลค่าเพิ่ม (7%)");
//		StaticTextItem netInclusive = new StaticTextItem("ราคาสุทธิ");
		summaryForm.setFields(netExclusive, tax, netInclusive);
		footerLayout.addMember(empty);
		footerLayout.addMember(summaryForm);
		//******************End Summary
		
		//******************Control
		VLayout controlLayout = new VLayout();
		controlLayout.setWidth("*");
		controlLayout.setMargin(10);
		controlLayout.setMembersMargin(5);
		
//		IButton saveButton = new IButton("saveRequest");
//		saveButton.setTitle("บันทึกใบเสนอซื้อ");
//		saveButton.setWidth(140);
//		saveButton.setHeight(40);
//		saveButton.setIcon("icons/16/save.png");
		saveButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				//quoteItem
				if (conditionForm.validate() && supplierForm.validate() ) {
					SC.confirm("ยืนยันการออกใบเสนอซื้อ", "ต้องการออกใบเสนอซื้อ หรือไม่?" , new BooleanCallback() {
						@Override
						public void execute(Boolean value) {
							if (value) {
								ListGridRecord[] all = requestListGrid.getRecords();
								
								if (all.length == 0) {
									SC.warn("กรูณาเลือกรายการวัตถุดิบอย่างน้อย 1 รายการ");
									return;
								}
								
								//SC.warn("numOfRecord : " + all.length);
								Double total_weight = 0.0;
								Double total_netExclusive = 0.0;
								Double total_amount = 0.0;
								final String request_id = "PR70" + Math.round((Math.random() * 100)) + Math.round((Math.random() * 100));
								final ArrayList<RequestMaterialDetails> materailList = new ArrayList<RequestMaterialDetails>();
								for (ListGridRecord item : all){
									total_weight += item.getAttributeAsDouble("weight");
									total_amount += item.getAttributeAsDouble("request_amount");
									total_netExclusive += item.getAttributeAsDouble("sum_price");
									
									String sub_request_id = "SPR80" + Math.round((Math.random() * 100)) + Math.round((Math.random() * 100));
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
									materailList.add(temp);
								}
								//String sid = client.sid;
								String quoteId = quote_id.getValueAsString();
								Date from = fromDate.getValueAsDate();
								Date to = toDate.getValueAsDate();
								Date delivery = deliveryDate.getValueAsDate();
								String payment = paymentModel.getValueAsString();
								Integer credit_value = credit.getValueAsInteger(); 
								String request_status = "2_waiting_for_approved";
								//xxxService.xxx(Callback quoteId);
								ListGridRecord newRecord = PurchaseRequestData.createRecord(request_id, client.sid, client.sup_name, quoteId, payment, credit_value, from, to, delivery, total_weight, total_amount, total_netExclusive, new Date(), null, currentUser.getFirstName() + " " + currentUser.getLastName(), null, "", request_status);
								// client; - sid
								// conditionForm; - from , to
								// SummaryForm; - netExclusive, tax, netInclusive
								// --> Quote Object + ItemQuote Object
								// Quote - quote id , sid , from, to , total_weight, total_amount, netExclusive, tax, netInclusive, created_date, modified_date, created_by, modified_by, status [waiting_for_approved, waiting_for_revised, approved, removed] --> to data store
								// ItemQuote - item quote id, quote id, mid, amount, total_price, status (0,1) --> to date store
								PurchaseRequestDS.getInstance().addData(newRecord, new DSCallback() {
									@Override
									public void execute(DSResponse dsResponse, Object data,
											DSRequest dsRequest) {
											if (dsResponse.getStatus() != 0) {
												SC.warn("การสร้างใบเสนอราคาล้มเหลว");
											} else { 
												for (RequestMaterialDetails item : materailList) {
													ListGridRecord subNewRecord = RequestMaterialData.createRecord(item);
													RequestMaterialDS.getInstance(request_id).addData(subNewRecord);
													//System.out.println("add data " + item.sub_quote_id);
												}
												SC.say("สร้างใบเสนอซื้อเสร็จสิ้น <br> " + "รหัสใบเสนอซื้อ " + request_id);
												clearAll();
											}
									}
			    				});
							}
						}
	            	});
				}
			}
		});
//		IButton clearButton = new IButton("clearQuote");
//		clearButton.setTitle("ล้างข้อมูลใบเสนอซื้อ");
//		clearButton.setIcon("icons/16/trash-icon.png");
//		clearButton.setWidth(140);
//		clearButton.setHeight(40);
		clearButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				SC.confirm("ยืนยันการทำรายการ", "ต้องการล้างข้อมูลใบเสนอซื้อทั้งหมด หรือไม่?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
							clearAll();
						}
					}
            	});
			}
			
		});
		
		//controlLayout.addMember(saveButton);
		//controlLayout.addMember(clearButton);
		//footerLayout.addMember(controlLayout);
		//******************End Control
		
		createLayout.addMember(footerLayout);
		
		createTab.setPane(createLayout);
		
		return createTab;
	}
	
	private void summaryPriceRecalculate(){
		ListGridRecord[] all = requestListGrid.getRecords();
		Double sum_price = 0.0;
		for (ListGridRecord record : all) {
			sum_price += record.getAttributeAsDouble("sum_price");
		}
		NumberFormat nf = NumberFormat.getFormat("#,##0.00");
		netExclusive.setValue(nf.format(sum_price));
		tax.setValue(nf.format(sum_price * 0.07));
		netInclusive.setValue(nf.format(sum_price * 1.07));
	}
	
	private void createSummaryField() {
		netExclusive = new StaticTextItem("netExclusive");
		tax = new StaticTextItem("tax");
		netInclusive = new StaticTextItem("netInclusive");
		
		netExclusive.setWidth(100);
		tax.setWidth(100);
		netInclusive.setWidth(100);
		
		netExclusive.setTitle("ราคารวม");
		tax.setTitle("ภาษีมูลค่าเพิ่ม (7%)");
		netInclusive.setTitle("ราคาสุทธิ");
		
		netExclusive.setDefaultValue("0.00");
		tax.setDefaultValue("0.00");
		netInclusive.setDefaultValue("0.00");
		
		netExclusive.setHint("บาท");
		tax.setHint("บาท");
		netInclusive.setHint("บาท");
		
		netExclusive.setTextAlign(Alignment.RIGHT);
		tax.setTextAlign(Alignment.RIGHT);
		netInclusive.setTextAlign(Alignment.RIGHT);
	}
	
	private void createDynamicForm() {
		supplierForm = new DynamicForm();
		materialForm = new DynamicForm(); 
		summaryForm = new DynamicForm();
		conditionForm = new DynamicForm();
		
		supplierForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
		materialForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
		conditionForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
	}
	
	private void clearAll() {
		
		for (ListGridRecord n : requestListGrid.getRecords()) {
			requestListGrid.removeData(n);
		}
		
		requestMaterial.clear();
		supplierForm.reset();
		materialForm.reset();
		summaryForm.reset();
		conditionForm.reset();
		
		addButton.disable();
		materialForm.disable();
	}
}
