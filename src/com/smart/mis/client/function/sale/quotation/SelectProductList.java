package com.smart.mis.client.function.sale.quotation;

import java.util.ArrayList;

import com.smart.mis.client.function.production.product.ProductDS;
import com.smart.mis.client.function.sale.quotation.product.QuoteProductData;
import com.smart.mis.shared.FieldFormatter;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedEvent;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class SelectProductList {

	QuoteReviseTab main;
	
	public SelectProductList(QuoteReviseTab main){
		this.main = main;
	}
	
	public void show(ArrayList<String> selected, final DataSource DS){
		
		final Window winModel = new Window();
		
		winModel.setTitle("เพิ่มรายการสินค้า");
		//winModel.setAutoSize(true);	
		winModel.setWidth(650);
		winModel.setHeight(350);
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
        productGrid.setWidth(500);  
        productGrid.setHeight(224);
        productGrid.setShowAllRecords(true);
        productGrid.setSelectionType(SelectionStyle.SIMPLE);  
        productGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX); 
        productGrid.setDataSource(ProductDS.getInstance());
        productGrid.setAutoFetchData(true);
        
        ListGridField pidField = new ListGridField("pid", 70);
        ListGridField pnameField = new ListGridField("name", 150);
        ListGridField pdescField = new ListGridField("desc", 150);
        ListGridField ppriceField = new ListGridField("price", 80);
        ppriceField.setCellFormatter(FieldFormatter.getPriceFormat());
        ListGridField ptypeField = new ListGridField("type", 80);
        ListGridField premainField = new ListGridField("remain", 80);
        premainField.setCellFormatter(FieldFormatter.getNumberFormat());
        ListGridField punitField = new ListGridField("unit", 50);
        ListGridField pinStockField = new ListGridField("inStock", 50);
        ListGridField pquoteAmount = new ListGridField("quote_amount", 80);
        pquoteAmount.setCellFormatter(FieldFormatter.getNumberFormat());
        pquoteAmount.setCanEdit(true);
        pquoteAmount.setRequired(true);
        pquoteAmount.setDefaultValue("0.00");
        //productGrid.setFields(pidField, pnameField); 
        
        
        HLayout control = new HLayout();
        control.setAlign(Alignment.CENTER);
        final IButton addButton = new IButton("ยืนยันรายการ");
        addButton.setWidth(120);
        IButton clearButton = new IButton("ล้างรายการ");
        addButton.setWidth(120);
        IButton cancelButton = new IButton("ยกเลิก");
        cancelButton.setWidth(120);
        
        addButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {
            	SC.confirm("ยืนยันการทำรายการ", "ต้องการเพิ่มรายการสินค้าที่เลือก หรือไม่?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
							for (ListGridRecord item : productGrid.getSelectedRecords()) {
									String pid = item.getAttributeAsString("pid");
									String pname = item.getAttributeAsString("name");
									String ptype = item.getAttributeAsString("type");
									String psize = item.getAttributeAsString("size");
									Double pweight = item.getAttributeAsDouble("weight");
									Integer pquote_amount = item.getAttributeAsInt("quote_amount");
									String punit = item.getAttributeAsString("unit");
									Double pprice = item.getAttributeAsDouble("price");
									ListGridRecord newRecord = QuoteProductData.createRecord(pid, pname, psize, pweight, pprice ,punit,ptype,pquote_amount);
									DS.addData(newRecord);
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
            	productGrid.fetchData();
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
}
