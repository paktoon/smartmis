package com.smart.mis.client.function.sale.quotation;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.smart.mis.client.function.production.product.ProductDS;
import com.smart.mis.client.function.sale.customer.CustomerDS;
import com.smart.mis.client.function.sale.quotation.product.QuoteProductDS;
import com.smart.mis.client.function.sale.quotation.product.QuoteProductData;
import com.smart.mis.client.function.sale.quotation.product.QuoteProductDetails;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.FromToValidate;
import com.smart.mis.shared.KeyGenerator;
import com.smart.mis.shared.ListGridNumberField;
import com.smart.mis.shared.ValidatorFactory;
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
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.DateRangeValidator;
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

public class QuoteCreateTab {
	
//	public static QuoteCreateTab instance;
	
	Label userId;
	DynamicForm customerForm, summaryForm, dateForm;
	DynamicForm productForm; 
	IButton addButton, delButton;
	ListGrid quoteListGrid;
	QuoteProductDetails quoteProduct;
	StaticTextItem netExclusive, tax, netInclusive;
	ListGridSummaryField quoteItemCell_sum;
	Customer client = new Customer();
	QuoteProductDS quoteDS;
	
	public QuoteCreateTab(){
		//For create tab
		quoteDS = new QuoteProductDS();
		
		userId = new Label();

		createDynamicForm();
		createSummaryField();
		
		addButton = new IButton("เพิ่มรายการสินค้า"); 
		delButton = new IButton("ลบรายการสินค้า"); 
		quoteListGrid = new ListGrid();
		quoteProduct = new QuoteProductDetails();
		
		quoteItemCell_sum = new ListGridSummaryField("sum_price", 100);
	}
	
	public Tab getCreateTab(final User currentUser){
		
		Tab createTab = new Tab("ออกใบเสนอราคา", "icons/16/icon_add_files.png");
		//TBD
		VLayout createLayout = new VLayout();
		createLayout.setWidth(750);
		createLayout.setHeight100();
		
		//******************Header
		HLayout headerLayout = new HLayout();
		headerLayout.setHeight(20);
		
//		userId.setContents("สร้างโดย : " + currentUser.getFirstName() + " " + currentUser.getLastName());
//		userId.setWidth("25%");
//		userId.setAlign(Alignment.LEFT);
//		headerLayout.addMember(userId);
		
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
		
		//******************Customer
		customerForm.setWidth100(); 
		customerForm.setHeight(30);
		customerForm.setMargin(5); 
		customerForm.setNumCols(6); 
		customerForm.setCellPadding(2);
		customerForm.setAutoFocus(true);
		customerForm.setSelectOnFocus(true);
		//customerForm.setDataSource(CustomerDS.getInstance());
		//customerForm.setUseAllDataSourceFields(false);
		customerForm.setIsGroup(true);
		customerForm.setGroupTitle("ข้อมูลลูกค้า");
        
		final StaticTextItem cid = new StaticTextItem("cid", "รหัสลูกค้า");
		final SelectItem cus_name = new SelectItem("cus_name", "ชื่อลูกค้า");
		cus_name.setOptionDataSource(CustomerDS.getInstance());
		cus_name.setEmptyDisplayValue("--โปรดเลือกลูกค้า--");
		cus_name.setPickListWidth(350);
		cus_name.setWidth(240);
		cus_name.setRequired(true);
		cus_name.setHint("*");
		ListGridField Field_1 = new ListGridField("cid", 80);  
        ListGridField Field_2 = new ListGridField("cus_name", 200);  
        ListGridField Field_3 = new ListGridField("cus_type", 70);
        cus_name.setPickListFields(Field_1, Field_2, Field_3);
        		
		final StaticTextItem type = new StaticTextItem("cus_type", "ประเภทลูกค้า");
		
		final SelectItem paymentModel = new SelectItem("payment_model", "วิธีการชำระเงิน");
		paymentModel.setValueMap("เงินสด", "แคชเชียร์เช็ค");
		//paymentModel.setEmptyDisplayValue("--โปรดเลือกวิธีชำระเงิน--");
		paymentModel.setDefaultValue("เงินสด");
		final IntegerItem credit = new IntegerItem("credit","เครดิต");
		credit.setRequired(true);
		credit.setHint("วัน*");
		credit.setWidth(100);
		credit.setTextAlign(Alignment.LEFT);
		credit.setDefaultValue(0);

		credit.setValidators(ValidatorFactory.integerRange(0, 30));
		paymentModel.disable();
		credit.disable();
        
		cus_name.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				Record selected = cus_name.getSelectedRecord();
				if (selected != null) {
					String customer_id = selected.getAttributeAsString("cid");
					String customer_name = selected.getAttributeAsString("cus_name");
					String customer_type = selected.getAttributeAsString("cus_type");
					//Contact info
					//String customer_address = selected.getAttributeAsString("address");
					String customer_phone = selected.getAttributeAsString("cus_phone");
					String contact_name = selected.getAttributeAsString("contact_name");
					String contact_phone = selected.getAttributeAsString("contact_phone");
					String contact_email = selected.getAttributeAsString("contact_email");
					
					//For report
					String bus_type = selected.getAttributeAsString("bus_type");
					String cus_group = selected.getAttributeAsString("cus_group");
					String zone = selected.getAttributeAsString("zone");
					
					client.setAttributes(customer_id, customer_name, customer_phone, contact_name, contact_phone, contact_email, customer_type, bus_type, cus_group, zone);
					client.setPaymentModel(paymentModel.getValueAsString());
					client.setCredit(credit.getValueAsInteger());
					paymentModel.enable();
					productForm.enable();
					if(customer_type.equalsIgnoreCase("ลูกค้าประจำ")) {
						credit.enable();
					} else {
						credit.setValue(0);
						credit.disable();
					}
					cid.setValue(customer_id);
					type.setValue(customer_type);
				}
			}
        });
		
		paymentModel.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				if (paymentModel.validate()) {
					client.setPaymentModel(paymentModel.getValueAsString());
				}
			}
        });
		
		credit.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				if (credit.validate()) {
					client.setCredit(credit.getValueAsInteger());
				}
			}
        });
		
		customerForm.setFields(cid, cus_name, type, paymentModel, credit);
		customerForm.setColWidths(100, 80, 80, 240, 100, 100);
		createLayout.addMember(customerForm);
		//******************End Customer
		
		//******************Product Header
		productForm.setWidth100(); 
		productForm.setMargin(5);  
		productForm.setNumCols(6);
		productForm.setCellPadding(2);
		productForm.setAutoFocus(false);  
        //productForm.setDataSource(ProductDS.getInstance());  
        //productForm.setUseAllDataSourceFields(false); 
		productForm.setIsGroup(true);
		productForm.setGroupTitle("เลือกสินค้า");
        
        final StaticTextItem pid = new StaticTextItem("pid" , "รหัสสินค้า");
        final SelectItem pname = new SelectItem("name", "ชื่อสินค้า");
        pname.setOptionDataSource(ProductDS.getInstance());
        pname.setEmptyDisplayValue("--โปรดเลือกสินค้า--");
        pname.setPickListWidth(420);
        pname.setWidth(240);
        pname.setRequired(true);
		pname.setHint("*");
		ListGridField Field_M1 = new ListGridField("pid", 80);  
        ListGridField Field_M2 = new ListGridField("name", 190);
        ListGridField Field_M3 = new ListGridField("type", 80);
        ListGridField Field_M4 = new ListGridField("remain", 70);
        Field_M4.setCellFormatter(FieldFormatter.getIntegerFormat());
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
        quantity.setValidators(ValidatorFactory.integerRange(50, 5000));
        
        final StaticTextItem pname_th = new StaticTextItem("name_th", "คำอธิบาย");
        final StaticTextItem pprice = new StaticTextItem("pprice" , "ราคา");
		
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
					Double product_price = selected.getAttributeAsDouble("price");
					String product_type = selected.getAttributeAsString("type");
					
					//Double product_remain = selected.getAttributeAsDouble("remain");
					String product_unit = selected.getAttributeAsString("unit");
					//Boolean product_inStock = selected.getAttributeAsBoolean("inStock");
					
					quantity.setHint(product_unit + "*");
					pprice.setHint("บาท ต่อ "+product_unit);
					//unit.setValue(product_unit + "*");
					quantity.enable();
					pname.setValue(product_name);
					pid.setValue(product_id);
					pname_th.setValue(product_name_th);
					ptype.setValue(product_type);
					pprice.setValue(product_price);
				
					quoteProduct.save(product_id, product_name, product_weight, product_price, product_type, product_unit);
				}
			}
        });
        productForm.setFields(pid, pname, pprice, ptype, pname_th , quantity);
        productForm.setColWidths(100, 80, 80, 240, 100, 100);
        productForm.disable();
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
	            if (productForm.validate() && quoteProduct.check()) {
					ListGridRecord addProduct = quoteProduct.convertToRecord(quantity.getValueAsInteger());
					quoteDS.addData(addProduct, new DSCallback() {
						@Override
						public void execute(DSResponse dsResponse, Object data,
								DSRequest dsRequest) {
								if (dsResponse.getStatus() != 0) {
									SC.warn("การเพิ่มสินค้าล้มเหลว มีสินค้านี้อยู่แล้ว");
								} else { 
									quoteListGrid.fetchData();
					            	addButton.disable();
					            	productForm.reset();
					            	quoteProduct.clear();
					            	quantity.setHint("*");
									pprice.setHint("");
									
									summaryPriceRecalculate();
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
            	if (quoteListGrid.getSelectedRecord() != null) {
            		//quoteListGrid.removeSelectedData();
            		quoteListGrid.removeSelectedData(new DSCallback() {
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
		
		buttonLayout.addMember(addButton);
		buttonLayout.addMember(delButton);
		createLayout.addMember(buttonLayout);
		//******************End Add
		
		//******************Quote List Grid
		HLayout itemLayout = new HLayout();
		itemLayout.setHeight(350);
		//itemLayout.setMargin(5);
		quoteListGrid.setWidth100();
		quoteListGrid.setHeight(350);
		quoteListGrid.setAlternateRecordStyles(true);  
		quoteListGrid.setShowAllRecords(true);  
		quoteListGrid.setAutoFetchData(true);  
		quoteListGrid.setSelectionType(SelectionStyle.SINGLE);
		quoteListGrid.setCanResizeFields(false);
		quoteListGrid.setShowGridSummary(true);
		quoteListGrid.setEditEvent(ListGridEditEvent.CLICK);  
		quoteListGrid.setListEndEditAction(RowEndEditAction.NONE);
		quoteListGrid.setShowRowNumbers(true);
		
		//quoteListGrid.setCanRemoveRecords(true);
		//quoteListGrid.setWarnOnRemoval(true);
		//quoteListGrid.setWarnOnRemovalMessage("คุณต้องการลบ รายการสินค้า หรือไม่?");
		
		quoteListGrid.setDataSource(quoteDS);
		quoteListGrid.setUseAllDataSourceFields(false);
        
		ListGridField quoteItemCell_1 = new ListGridField("pid", 75);
        ListGridField quoteItemCell_2 = new ListGridField("name");  
        quoteItemCell_2.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });  
        quoteItemCell_2.setShowGridSummary(true);
		
        ListGridField quoteItemCell_3 = new ListGridField("unit", 50);
        
        ListGridNumberField quoteItemCell_4 = new ListGridNumberField("weight", 110);
        quoteItemCell_4.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_4.setShowGridSummary(true);
        quoteItemCell_4.setIncludeInRecordSummary(false);
        
        ListGridField quoteItemCell_5 = new ListGridField("price", 90);
        //quoteItemCell_5.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_5.setShowGridSummary(false);
        quoteItemCell_5.setCellFormatter(FieldFormatter.getPriceFormat());
        quoteItemCell_5.setAlign(Alignment.RIGHT);
        
        ListGridNumberField quoteItemCell_6 = new ListGridNumberField("quote_amount", 80);
        quoteItemCell_6.setValidators(ValidatorFactory.integerRange(50, 5000));
        quoteItemCell_6.setCellFormatter(FieldFormatter.getIntegerFormat());
        //quoteItemCell_6.setCanEdit(true);
        quoteItemCell_6.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_6.setShowGridSummary(true);
        
        ListGridSummaryField quoteItemCell_sum = new ListGridSummaryField("sum_price", 100);

        quoteItemCell_sum.setRecordSummaryFunction(RecordSummaryFunctionType.MULTIPLIER);
        quoteItemCell_sum.setSummaryFunction(SummaryFunctionType.SUM);
        quoteItemCell_sum.setShowGridSummary(true);
        quoteItemCell_sum.setCellFormatter(FieldFormatter.getPriceFormat());
        quoteItemCell_sum.setAlign(Alignment.RIGHT);
 
        quoteListGrid.addCellSavedHandler(new CellSavedHandler() {  
			@Override
			public void onCellSaved(CellSavedEvent event) {
				summaryPriceRecalculate();
			}  
        });
        
        //quoteListGrid.setFields(quoteItemCell_1, quoteItemCell_2, quoteItemCell_3, quoteItemCell_4, quoteItemCell_5, quoteItemCell_6, quoteItemCell_sum);
        quoteListGrid.setFields(quoteItemCell_1, quoteItemCell_2, quoteItemCell_6, quoteItemCell_3, quoteItemCell_4, quoteItemCell_5 , quoteItemCell_sum);
        
		itemLayout.addMember(quoteListGrid);
		createLayout.addMember(itemLayout);
		//******************End Product Grid
		
		//******************Condition
		HLayout footerLayout = new HLayout();
		footerLayout.setHeight(100);
		
		dateForm.setWidth(300);
		dateForm.setNumCols(2);
		dateForm.setMargin(5);
		dateForm.setIsGroup(true);
		dateForm.setGroupTitle("ข้อกำหนดใบเสนอราคา");
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
		
		DateRange dateRange = new DateRange();  
        dateRange.setRelativeStartDate(RelativeDate.TODAY);
        dateRange.setRelativeEndDate(new RelativeDate("+7d"));
        fromDate.setDefaultChooserDate(dateRange.getStartDate());
        fromDate.setDefaultValue(dateRange.getStartDate());
        toDate.setDefaultChooserDate(dateRange.getEndDate());
        toDate.setDefaultValue(dateRange.getEndDate());
        //dateRange.setRelativeEndDate(new RelativeDate("+1m"));
        dateRange.setRelativeEndDate(new RelativeDate("+30d"));
        deliveryDate.setDefaultChooserDate(dateRange.getEndDate());
        deliveryDate.setDefaultValue(dateRange.getEndDate());
        
        fromDate.setRequired(true);
        fromDate.setHint("*");
		toDate.setRequired(true);
		toDate.setHint("*");
		deliveryDate.setRequired(true);
		deliveryDate.setHint("*");
		
		fromDate.addChangeHandler( new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				// TODO Auto-generated method stub
					try {
						Date from = (Date) event.getValue();
						//if (!from.before(toDate.getValueAsDate()) || !from.before(deliveryDate.getValueAsDate())) {
						if (from.after(toDate.getValueAsDate()) || from.after(deliveryDate.getValueAsDate()) || from.before(new Date())) {
								//SC.warn("วันที่เลือกไม่ถูกต้อง กรุณาเลือกใหม่อีกครั้ง");
								SC.warn("วันที่เลือกไม่ถูกต้อง กรุณาเลือกใหม่อีกครั้ง");
								fromDate.setValue(fromDate.getValueAsDate());
							}
					} catch (Exception e) {
						fromDate.setValue(fromDate.getValueAsDate());
						SC.warn("รูปแบบวันที่ไม่ถูกต้อง กรุณากรอกด้วยรูปแบบ เดือน/วันที่/ปี เช่น 01/01/2014");
					}
				}
			});
        
		toDate.addChangeHandler( new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				// TODO Auto-generated method stub
					try {
						Date to = (Date) event.getValue();
						
						//if (!to.after(fromDate.getValueAsDate()) || !to.after(new Date())) {
						if (to.before(fromDate.getValueAsDate()) || to.before(new Date())) {
								SC.warn("วันที่เลือกไม่ถูกต้อง กรุณาเลือกใหม่อีกครั้ง");
								toDate.setValue(toDate.getValueAsDate());
							}
					} catch (Exception e) {
						toDate.setValue(toDate.getValueAsDate());
						SC.warn("รูปแบบวันที่ไม่ถูกต้อง กรุณากรอกด้วยรูปแบบ เดือน/วันที่/ปี เช่น 01/01/2014");
					}
				} 
		});
		
		deliveryDate.addChangeHandler( new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				// TODO Auto-generated method stub
					try {
						Date delivery = (Date) event.getValue();
						//if (!delivery.after(fromDate.getValueAsDate())  || !delivery.after(new Date())) {
						if (delivery.before(fromDate.getValueAsDate())  || delivery.before(new Date())) {
								//SC.warn("วันที่เลือกไม่ถูกต้อง กรุณาเลือกใหม่อีกครั้ง");
								SC.warn("วันที่เลือกไม่ถูกต้อง กรุณาเลือกใหม่อีกครั้ง");
								deliveryDate.setValue(deliveryDate.getValueAsDate());
							}
					} catch (Exception e) {
						deliveryDate.setValue(deliveryDate.getValueAsDate());
						SC.warn("รูปแบบวันที่ไม่ถูกต้อง กรุณากรอกด้วยรูปแบบ เดือน/วันที่/ปี เช่น 01/01/2014");
					}
				}
			});
		
		dateForm.setFields(fromDate, toDate, deliveryDate);
		dateForm.setColWidths(125,125 );
		footerLayout.addMember(dateForm);
		//******************End
		
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
		footerLayout.addMember(summaryForm);
		//******************End Summary
		
		//******************Control
		VLayout controlLayout = new VLayout();
		controlLayout.setWidth("*");
		controlLayout.setMargin(10);
		controlLayout.setMembersMargin(5);
		
		IButton saveButton = new IButton("saveQuote");
		saveButton.setTitle("บันทึกใบเสนอราคา");
		saveButton.setWidth(140);
		saveButton.setHeight(40);
		saveButton.setIcon("icons/16/save.png");
		saveButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				//quoteItem
				if (quoteListGrid.hasErrors()) {
					SC.warn("ข้อมูลจำนวนสินค้าไม่ถูกต้อง");
					return;
				}
				
				if (customerForm.validate() && dateForm.validate()) {
					SC.confirm("ยืนยันการออกใบเสนอราคา", "ต้องการออกใบเสนอราคา หรือไม่?" , new BooleanCallback() {
						@Override
						public void execute(Boolean value) {
							if (value) {
								ListGridRecord[] all = quoteListGrid.getRecords();
								
								if (all.length == 0) {
									SC.warn("กรูณาเลือกรายการสินค้าอย่างน้อย 1 รายการ");
									return;
								}
								
								//SC.warn("numOfRecord : " + all.length);
								Double total_weight = 0.0;
								Double total_netExclusive = 0.0;
								Integer total_amount = 0;
								final String quote_id = "QA" + KeyGenerator.genKey() + Math.round((Math.random() * 100)) + Math.round((Math.random() * 100));
								final ArrayList<QuoteProductDetails> productList = new ArrayList<QuoteProductDetails>();
								for (ListGridRecord item : all){
									total_weight += item.getAttributeAsDouble("weight");
									total_amount += item.getAttributeAsInt("quote_amount");
									total_netExclusive += item.getAttributeAsDouble("sum_price");
									
									String sub_quote_id = "QS" + KeyGenerator.genKey() + Math.round((Math.random() * 100)) + Math.round((Math.random() * 100));
									String pid = item.getAttributeAsString("pid");
									String pname = item.getAttributeAsString("name");
									String ptype = item.getAttributeAsString("type");
									Double pweight = item.getAttributeAsDouble("weight");
									Integer pquote_amount = item.getAttributeAsInt("quote_amount");
									String punit = item.getAttributeAsString("unit");
									Double pprice = item.getAttributeAsDouble("price");
									QuoteProductDetails temp = new QuoteProductDetails();
									temp.save(pid, pname, pweight, pprice, ptype, punit);
									temp.setID(sub_quote_id, quote_id);
									temp.setQuantity(pquote_amount);
									productList.add(temp);
								}
								//String cid = client.cid;
								Date from = fromDate.getValueAsDate();
								Date to = toDate.getValueAsDate();
								Date delivery = deliveryDate.getValueAsDate();
								String quote_status = "2_waiting_for_approved";
								//xxxService.xxx(Callback quoteId);
								ListGridRecord newRecord = QuotationData.createRecord(quote_id, client.cid, client.cus_name, client.payment_model, client.credit, client.cus_type, client.bus_type, client.cus_group, client.zone, from, to, delivery, total_weight, total_amount, total_netExclusive, new Date(), null, currentUser.getFirstName() + " " + currentUser.getLastName(), null, "", quote_status);
								// client; - cid
								// DateForm; - from , to
								// SummaryForm; - netExclusive, tax, netInclusive
								// --> Quote Object + ItemQuote Object
								// Quote - quote id , cid , from, to , total_weight, total_amount, netExclusive, tax, netInclusive, created_date, modified_date, created_by, modified_by, status [waiting_for_approved, waiting_for_revised, approved, removed] --> to data store
								// ItemQuote - item quote id, quote id, pid, amount, total_price, status (0,1) --> to date store
								QuotationDS.getInstance().addData(newRecord, new DSCallback() {
									@Override
									public void execute(DSResponse dsResponse, Object data,
											DSRequest dsRequest) {
											if (dsResponse.getStatus() != 0) {
												SC.warn("การสร้างใบเสนอราคาล้มเหลว");
											} else { 
												for (QuoteProductDetails item : productList) {
													ListGridRecord subNewRecord = QuoteProductData.createRecord(item);
													QuoteProductDS.getInstance(quote_id).addData(subNewRecord);
													//System.out.println("add data " + item.sub_quote_id);
												}
												SC.say("สร้างใบเสนอราคาเสร็จสิ้น <br> " + "รหัสใบเสนอราคา " + quote_id);
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
		IButton clearButton = new IButton("clearQuote");
		clearButton.setTitle("ล้างข้อมูลใบเสนอราคา");
		clearButton.setIcon("icons/16/trash-icon.png");
		clearButton.setWidth(140);
		clearButton.setHeight(40);
		clearButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				SC.confirm("ยืนยันการทำรายการ", "ต้องการล้างข้อมูลใบเสนอราคาทั้งหมด หรือไม่?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
							clearAll();
						}
					}
            	});
			}
			
		});
		
		controlLayout.addMember(saveButton);
		controlLayout.addMember(clearButton);
		footerLayout.addMember(controlLayout);
		//******************End Control
		
		createLayout.addMember(footerLayout);
		
		createTab.setPane(createLayout);
		
		return createTab;
	}
	
	private void summaryPriceRecalculate(){
		ListGridRecord[] all = quoteListGrid.getRecords();
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
		customerForm = new DynamicForm();
		productForm = new DynamicForm(); 
		summaryForm = new DynamicForm();
		dateForm = new DynamicForm();
		
		customerForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
		productForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
		dateForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
	}
	
	private void clearAll() {
		
		for (ListGridRecord n : quoteListGrid.getRecords()) {
			quoteListGrid.removeData(n);
		}
		
		quoteProduct.clear();
		customerForm.reset();
		productForm.reset();
		summaryForm.reset();
		dateForm.reset();
		
		addButton.disable();
	}
}
