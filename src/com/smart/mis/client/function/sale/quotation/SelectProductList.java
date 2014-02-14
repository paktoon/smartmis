package com.smart.mis.client.function.sale.quotation;

import java.util.ArrayList;

import com.google.gwt.i18n.client.NumberFormat;
import com.smart.mis.client.function.production.product.ProductDS;
import com.smart.mis.client.function.sale.quotation.product.QuoteProductData;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.FieldVerifier;
import com.smart.mis.shared.ValidatorFactory;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedEvent;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class SelectProductList {
	
	public void show(final ArrayList<String> selected, final ListGrid target, final DynamicForm summaryForm){
		
		final Window winModel = new Window();
		
		winModel.setTitle("เพิ่มรายการสินค้า");
		//winModel.setAutoSize(true);	
		winModel.setWidth(610);
		winModel.setHeight(310);
		winModel.setHeaderIcon("[SKIN]actions/add.png");
		winModel.setShowMinimizeButton(false);
		winModel.setIsModal(true);
		winModel.setShowModalMask(true);
		winModel.setCanDragResize(false);
		winModel.setCanDragReposition(false);
		winModel.centerInPage();
		
		VLayout outlineForm = new VLayout();
        outlineForm.setWidth100();
        outlineForm.setHeight100();
        outlineForm.setMargin(10);
        
        final ListGrid productGrid = new ListGrid();  
        productGrid.setWidth(570);  
        productGrid.setHeight(224);
        productGrid.setShowAllRecords(true);
        productGrid.setSelectionType(SelectionStyle.SIMPLE);  
        productGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX); 
        productGrid.setDataSource(new ProductDS());
        productGrid.setAutoFetchData(true);
        productGrid.setEditEvent(ListGridEditEvent.CLICK);
        productGrid.setListEndEditAction(RowEndEditAction.NONE);
        productGrid.setCanEdit(true);
        productGrid.setEditByCell(true);
        String regex = FieldVerifier.createNegativeRegex(selected);
	    final Criterion ci = new Criterion("pid", OperatorId.REGEXP, regex);
	    if (selected.size() != 0) {
	        productGrid.setCriteria(ci);
        }
        
        ListGridField pidField = new ListGridField("pid", 70);
        pidField.setCanEdit(false);
        ListGridField pnameField = new ListGridField("name", 150);
        pnameField.setCanEdit(false);
        //ListGridField pdescField = new ListGridField("desc", 150);
        ListGridField ppriceField = new ListGridField("price", 80);
        ppriceField.setCellFormatter(FieldFormatter.getPriceFormat());
        ppriceField.setCanEdit(false);
        //ListGridField ptypeField = new ListGridField("type", 80);
        ListGridField premainField = new ListGridField("remain", 80);
        premainField.setCellFormatter(FieldFormatter.getIntegerFormat());
        premainField.setCanEdit(false);
        ListGridField punitField = new ListGridField("unit", 50);
        punitField.setCanEdit(false);
        //ListGridField pinStockField = new ListGridField("inStock", 50);
        ListGridField pquoteAmount = new ListGridField("quote_amount", "จำนวน (ชิ้น)", 100);
        pquoteAmount.setType(ListGridFieldType.INTEGER);
        pquoteAmount.setCellFormatter(FieldFormatter.getIntegerFormat());
        pquoteAmount.setCanEdit(true);
        pquoteAmount.setRequired(true);
        pquoteAmount.setDefaultValue(0);
        pquoteAmount.setValidators(ValidatorFactory.integerRange(50, 5000));
        productGrid.setFields(pidField, pnameField, ppriceField, premainField, punitField, pquoteAmount); 
        
        HLayout control = new HLayout();
        control.setAlign(Alignment.CENTER);
        control.setMargin(5);
        control.setMembersMargin(5);
        final IButton addButton = new IButton("ยืนยันรายการ");
        addButton.setIcon("[SKIN]actions/add.png");
        addButton.setWidth(120);
        IButton clearButton = new IButton("ล้างรายการ");
        clearButton.setIcon("icons/16/delete.png");
        clearButton.setWidth(120);
        IButton cancelButton = new IButton("ปิด");
        cancelButton.setIcon("icons/16/close.png");
        cancelButton.setWidth(120);
        
        addButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {
            	for (ListGridRecord item : productGrid.getSelectedRecords()) {
            		if (item.getAttributeAsInt("quote_amount") == null || item.getAttributeAsInt("quote_amount") == 0) 
            		{
            			SC.warn("กรุณากรอกข้อมูลให้ครบถ้วนในรายการที่เลือก");
            			return;
            		}
            	}
            	SC.confirm("ยืนยันการทำรายการ", "ต้องการเพิ่มรายการสินค้าที่เลือก หรือไม่?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
							for (ListGridRecord item : productGrid.getSelectedRecords()) {
									String pid = item.getAttributeAsString("pid");
									String pname = item.getAttributeAsString("name");
									String ptype = item.getAttributeAsString("type");
									//String psize = item.getAttributeAsString("size");
									Double pweight = item.getAttributeAsDouble("weight");
									Integer pquote_amount = item.getAttributeAsInt("quote_amount");
									String punit = item.getAttributeAsString("unit");
									Double pprice = item.getAttributeAsDouble("price");
									ListGridRecord newRecord = QuoteProductData.createRecord(pid, pname, pweight, pprice ,ptype, punit, pquote_amount, true);
									//DS.addData(newRecord);
									target.addData(newRecord, new DSCallback() {
										@Override
										public void execute(DSResponse dsResponse, Object data,
												DSRequest dsRequest) {
													//quoteListGrid.fetchData();
									            	//addButton.disable();
									            	//productForm.reset();
									            	//quoteProduct.clear();
									            	//quantity.setHint("*");
													//pprice.setHint("");
													summaryPriceRecalculate(target.getRecords(), summaryForm);
										}
									});
									winModel.destroy();
							}
						}
					}
            	});
            }
        });
        control.addMember(addButton);
        
        clearButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {
            	productGrid.deselectAllRecords();
            	for (ListGridRecord item : productGrid.getRecords()) {
            		item.setAttribute("quote_amount", 0);
            	}
            	if (selected.size() != 0) {
            		productGrid.fetchData(ci);
            	}
            }
        });
        control.addMember(clearButton);
        
        cancelButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {
            	winModel.destroy();
            }
        });
        control.addMember(cancelButton);
        
        productGrid.addSelectionUpdatedHandler(new SelectionUpdatedHandler() {  
            public void onSelectionUpdated(SelectionUpdatedEvent event) {  
            	if (productGrid.getSelectedRecords().length != 0) addButton.enable();
            	else addButton.disable();  
            }  
        });
        
        outlineForm.addMember(productGrid);
        outlineForm.addMember(control);
        
        winModel.addItem(outlineForm);
        winModel.show();
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
}
