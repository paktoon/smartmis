package com.smart.mis.client.function.report.financial;

import com.smart.mis.client.function.production.product.ProductDS;
import com.smart.mis.client.function.sale.invoice.InvoiceDS;
import com.smart.mis.shared.FieldFormatter;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class CashReceiptListGrid extends ListGrid {

	@Override
	protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) { 
		if (getFieldName(colNum).equals("netInclusive")) {
			if (record.getAttributeAsString("status").equalsIgnoreCase("2_paid")) {
				return "font-weight:bold; color:#287fd6;";
			} else return "font-weight:bold; color:#d64949;"; 
		} else {  
            return super.getCellCSSText(record, rowNum, colNum);  
        } 
	}
	
	public CashReceiptListGrid(String groupBy) {
		setWidth(1000);  
		setHeight(300);  
        
        setAlternateRecordStyles(true);  
        setShowAllRecords(true);  
        setAutoFetchData(true);  
        setSelectionType(SelectionStyle.SINGLE);
        setCanResizeFields(false);
        setCanEdit(false);
        setDataSource(InvoiceDS.getInstance());
        setUseAllDataSourceFields(false);
        setMargin(10);
        //setCriteria(new Criterion("status", OperatorId.NOT_EQUAL, "4_canceled"));
        
        setGroupStartOpen(GroupStartOpen.ALL);
        setGroupByField(groupBy); // cus_type, bus_type, cus_group, zone
        
        ListGridField field_1 = new ListGridField("invoice_id", 120);
        ListGridField field_2_1 = new ListGridField("sale_id",120);
        ListGridField field_2_2 = new ListGridField("cus_name");

        //ListGridField field_3 = new ListGridField("size");
        ListGridField field_3_1 = new ListGridField("cus_type", 100);
        ListGridField field_3_2 = new ListGridField("bus_type", 150);
        ListGridField field_3_3 = new ListGridField("cus_group", 100);
        ListGridField field_3_4 = new ListGridField("zone", 100);
        
        //ListGridField field_4_1 = new ListGridField("payment_model", 100);
        ListGridField field_4 = new ListGridField("credit", 100);
        
        ListGridField Field_6_1 = new ListGridField("netInclusive", 120);
        ListGridField Field_6_2 = new ListGridField("created_date", 100);
        
        ListGridField Field_7 = new ListGridField("status", 120);
        
        //Cell Format
        Field_6_1.setAlign(Alignment.RIGHT);
        Field_6_1.setCellFormatter(FieldFormatter.getPriceFormat());
        
        setFields(field_1, field_2_1, field_2_2, field_3_1, field_3_2, field_3_3, field_3_4, field_4, Field_6_1, Field_6_2, Field_7);
        setHoverWidth(200);  
        setHoverHeight(20);
        hideFields("cus_type", "bus_type" , "cus_group", "zone");
        //fetchData();
	}
	
//	public void addUpdateDetailHandler(final ProductDetailTabPane itemDetailTabPane){
//        addRecordClickHandler(new RecordClickHandler() {  
//			@Override
//			public void onRecordClick(RecordClickEvent event) {
//				itemDetailTabPane.updateDetails();  
//			}  
//        });  
//  
//        addCellSavedHandler(new CellSavedHandler() {  
//			@Override
//			public void onCellSaved(CellSavedEvent event) {
//				itemDetailTabPane.updateDetails();  
//			}  
//        }); 
//		
//	}
}
