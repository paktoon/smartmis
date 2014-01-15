package com.smart.mis.client.function.report.financial;

import com.smart.mis.client.function.sale.invoice.InvoiceDS;
import com.smart.mis.shared.FieldFormatter;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SummaryFunctionType;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SummaryFunction;

public class CashReceiptListGrid extends ListGrid {

	@Override
	protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) { 
		if (getFieldName(colNum).equals("netInclusive") && record.getAttributeAsString("status") != null) {
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
        setShowGroupSummary(true);
        
        ListGridField field_1 = new ListGridField("invoice_id", 120);
        ListGridField field_2_1 = new ListGridField("sale_id",120);
        field_2_1.setShowGroupSummary(true);
        field_2_1.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });
        ListGridField field_2_2 = new ListGridField("cus_name");

        //ListGridField field_3 = new ListGridField("size");
        ListGridField field_3_1 = new ListGridField("cus_type", 100);
        ListGridField field_3_2 = new ListGridField("bus_type", 150);
        ListGridField field_3_3 = new ListGridField("cus_group", 100);
        ListGridField field_3_4 = new ListGridField("zone", 100);
        
        //ListGridField field_4_1 = new ListGridField("payment_model", 100);
        ListGridField field_4 = new ListGridField("credit", 100);
        field_4.setShowGroupSummary(false);
        
        ListGridField Field_6_1 = new ListGridField("netInclusive", 120);
        Field_6_1.setShowGroupSummary(true);
        Field_6_1.setSummaryFunction(SummaryFunctionType.SUM);
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
	
	public Double[][] createDataTable(Criterion criteria, String type){
	    InvoiceDS.getInstance().refreshData();
	    
	    if (type.equalsIgnoreCase("cus_type")) {
	    	Record[] cus_type_1_1 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("cus_type", OperatorId.EQUALS, "ลูกค้าทั่วไป") , new Criterion("status", OperatorId.EQUALS, "2_paid")}));
		    Record[] cus_type_1_2 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("cus_type", OperatorId.EQUALS, "ลูกค้าประจำ") , new Criterion("status", OperatorId.EQUALS, "2_paid")}));
		    Record[] cus_type_2_1 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("cus_type", OperatorId.EQUALS, "ลูกค้าทั่วไป") , new Criterion("status", OperatorId.NOT_EQUAL, "2_paid")}));
		    Record[] cus_type_2_2 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("cus_type", OperatorId.EQUALS, "ลูกค้าประจำ") , new Criterion("status", OperatorId.NOT_EQUAL, "2_paid")}));
		    
		    return new Double[][] {
		    		{getNetInclusive(cus_type_1_1), getNetInclusive(cus_type_1_2)},
		    		{getNetInclusive(cus_type_2_1), getNetInclusive(cus_type_2_2)}
		    };
	    } else if (type.equalsIgnoreCase("bus_type")) {
	    	Record[] bus_type_1_1 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("bus_type", OperatorId.EQUALS, "ค้าปลีกผ่านหน้าร้าน") , new Criterion("status", OperatorId.EQUALS, "2_paid")}));
		    Record[] bus_type_1_2 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("bus_type", OperatorId.EQUALS, "ค้าปลีกผ่านเว็บไซต์") , new Criterion("status", OperatorId.EQUALS, "2_paid")}));
		    Record[] bus_type_1_3 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("bus_type", OperatorId.EQUALS, "ค้าส่งผ่านหน้าร้าน") , new Criterion("status", OperatorId.EQUALS, "2_paid")}));
		    Record[] bus_type_1_4 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("bus_type", OperatorId.EQUALS, "ค้าส่งผ่านเว็บไซต์") , new Criterion("status", OperatorId.EQUALS, "2_paid")}));
		    
		    Record[] bus_type_2_1 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("bus_type", OperatorId.EQUALS, "ค้าปลีกผ่านหน้าร้าน") , new Criterion("status", OperatorId.NOT_EQUAL, "2_paid")}));
		    Record[] bus_type_2_2 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("bus_type", OperatorId.EQUALS, "ค้าปลีกผ่านเว็บไซต์") , new Criterion("status", OperatorId.NOT_EQUAL, "2_paid")}));
		    Record[] bus_type_2_3 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("bus_type", OperatorId.EQUALS, "ค้าส่งผ่านหน้าร้าน") , new Criterion("status", OperatorId.NOT_EQUAL, "2_paid")}));
		    Record[] bus_type_2_4 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("bus_type", OperatorId.EQUALS, "ค้าส่งผ่านเว็บไซต์") , new Criterion("status", OperatorId.NOT_EQUAL, "2_paid")}));
		    
		    return new Double[][] {
		    		{getNetInclusive(bus_type_1_1), getNetInclusive(bus_type_1_2), getNetInclusive(bus_type_1_3), getNetInclusive(bus_type_1_4)},
		    		{getNetInclusive(bus_type_2_1), getNetInclusive(bus_type_2_2), getNetInclusive(bus_type_2_3), getNetInclusive(bus_type_2_4)}
		    };
	    } else if (type.equalsIgnoreCase("cus_group")) {
	    	Record[] cus_group_1_1 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("cus_group", OperatorId.EQUALS, "บุคคลทั่วไป") , new Criterion("status", OperatorId.EQUALS, "2_paid")}));
		    Record[] cus_group_1_2 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("cus_group", OperatorId.EQUALS, "ร้านค้า") , new Criterion("status", OperatorId.EQUALS, "2_paid")}));
		    Record[] cus_group_2_1 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("cus_group", OperatorId.EQUALS, "บุคคลทั่วไป") , new Criterion("status", OperatorId.NOT_EQUAL, "2_paid")}));
		    Record[] cus_group_2_2 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("cus_group", OperatorId.EQUALS, "ร้านค้า") , new Criterion("status", OperatorId.NOT_EQUAL, "2_paid")}));
		    
		    return new Double[][] {
		    		{getNetInclusive(cus_group_1_1), getNetInclusive(cus_group_1_2)},
		    		{getNetInclusive(cus_group_2_1), getNetInclusive(cus_group_2_2)}
		    };
	    } else if (type.equalsIgnoreCase("zone")) {
	    	Record[] zone_1_1 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("zone", OperatorId.EQUALS, "เอเซีย") , new Criterion("status", OperatorId.EQUALS, "2_paid")}));
		    Record[] zone_1_2 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("zone", OperatorId.EQUALS, "ยุโรป") , new Criterion("status", OperatorId.EQUALS, "2_paid")}));
		    Record[] zone_1_3 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("zone", OperatorId.EQUALS, "อเมริกาเหนือ") , new Criterion("status", OperatorId.EQUALS, "2_paid")}));
		    Record[] zone_1_4 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("zone", OperatorId.EQUALS, "อเมริกาใต้") , new Criterion("status", OperatorId.EQUALS, "2_paid")}));
		    
		    Record[] zone_2_1 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("zone", OperatorId.EQUALS, "เอเซีย") , new Criterion("status", OperatorId.NOT_EQUAL, "2_paid")}));
		    Record[] zone_2_2 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("zone", OperatorId.EQUALS, "ยุโรป") , new Criterion("status", OperatorId.NOT_EQUAL, "2_paid")}));
		    Record[] zone_2_3 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("zone", OperatorId.EQUALS, "อเมริกาเหนือ") , new Criterion("status", OperatorId.NOT_EQUAL, "2_paid")}));
		    Record[] zone_2_4 = InvoiceDS.getInstance().applyFilter(InvoiceDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("zone", OperatorId.EQUALS, "อเมริกาใต้") , new Criterion("status", OperatorId.NOT_EQUAL, "2_paid")}));
		    
		    return new Double[][] {
		    		{getNetInclusive(zone_1_1), getNetInclusive(zone_1_2), getNetInclusive(zone_1_3), getNetInclusive(zone_1_4)},
		    		{getNetInclusive(zone_2_1), getNetInclusive(zone_2_2), getNetInclusive(zone_2_3), getNetInclusive(zone_2_4)}
		    };
	    } else return null;
	}
	
	public Double getNetInclusive(Record[] records) {
		Double netInclusive = 0.0;
		for (Record record : records) {
			netInclusive += record.getAttributeAsDouble("netInclusive");
		}
		return netInclusive;
	}
}
