package com.smart.mis.client.function.report.production;

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
import com.smartgwt.client.widgets.grid.SummaryFunction;

public class ProductionReportListGrid extends ListGrid {

//	@Override
//	protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) { 
//		if (getFieldName(colNum).equals("remain")) {
//			if (record.getAttributeAsInt("remain") == 0) {
//				return "font-weight:bold; color:#d64949;";
//			} else {  
//                return super.getCellCSSText(record, rowNum, colNum);  
//            }
//		} else {  
//            return super.getCellCSSText(record, rowNum, colNum);  
//        } 
//	}
	
	public ProductionReportListGrid() {
		setWidth(950);
		setHeight(300);
        
        setAlternateRecordStyles(true);  
        setShowAllRecords(true);  
        setAutoFetchData(true);  
        setSelectionType(SelectionStyle.SINGLE);
        setCanResizeFields(false);
        setCanEdit(false);
        setDataSource(ProductionReportDS.getInstance());
        setUseAllDataSourceFields(false);
        setMargin(10);
        
        setGroupStartOpen(GroupStartOpen.ALL);
        setGroupByField("type");
        setShowGroupSummary(true); 
        
        ListGridField field_1 = new ListGridField("plan_id", 120);
        ListGridField field_2_1 = new ListGridField("pid",150);
        field_2_1.setShowGroupSummary(true);
        field_2_1.setSummaryFunction(new SummaryFunction() {  
            public Object getSummaryValue(Record[] records, ListGridField field) {
                return records.length + " รายการ";  
            }  
        });
        ListGridField field_2_2 = new ListGridField("name");
        ListGridField field_2_3 = new ListGridField("type"); // *
        
        ListGridField field_3_1 = new ListGridField("sent_weight",150);
        field_3_1.setShowGroupSummary(true);
        field_3_1.setSummaryFunction(SummaryFunctionType.SUM);
        ListGridField field_3_2 = new ListGridField("sent_amount", 150);
        field_3_2.setShowGroupSummary(true);
        field_3_2.setSummaryFunction(SummaryFunctionType.SUM);
        ListGridField field_3_3 = new ListGridField("unit", 50);
        
        ListGridField Field_4 = new ListGridField("produced_date", 100);
        
        //Cell Format
        field_3_1.setAlign(Alignment.RIGHT);
        field_3_1.setCellFormatter(FieldFormatter.getNumberFormat());
        field_3_2.setAlign(Alignment.RIGHT);
        field_3_2.setCellFormatter(FieldFormatter.getNumberFormat());
        
        setFields(field_1, field_2_1, field_2_2, field_2_3, field_3_1, field_3_2, field_3_3, Field_4);
        setHoverWidth(200);  
        setHoverHeight(20);
        hideFields("type");
        //fetchData();
	}
	
	public Double[][] createDataTable(Criterion criteria){
		ProductionReportDS.getInstance().refreshData();
	    Record[] ring = ProductionReportDS.getInstance().applyFilter(ProductionReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "ring")}));
	    Record[] toe_ring = ProductionReportDS.getInstance().applyFilter(ProductionReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "toe ring")}));
	    Record[] earring = ProductionReportDS.getInstance().applyFilter(ProductionReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "earring")}));
	    Record[] necklace = ProductionReportDS.getInstance().applyFilter(ProductionReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "necklace")}));
	    Record[] pendant = ProductionReportDS.getInstance().applyFilter(ProductionReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "pendant")}));
	    Record[] bracelet = ProductionReportDS.getInstance().applyFilter(ProductionReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "bracelet")}));
	    Record[] anklet = ProductionReportDS.getInstance().applyFilter(ProductionReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "anklet")}));
	    Record[] bangle = ProductionReportDS.getInstance().applyFilter(ProductionReportDS.getInstance().getCacheData(), new AdvancedCriteria(OperatorId.AND, new Criterion[] {criteria, new Criterion("type", OperatorId.EQUALS, "bangle")}));

	    return new Double[][] {
	    		{getProducedAmount(ring), getProducedAmount(toe_ring), getProducedAmount(earring), getProducedAmount(necklace), getProducedAmount(pendant), getProducedAmount(bracelet), getProducedAmount(anklet), getProducedAmount(bangle)}
	    };
	}
	
	public Double getProducedAmount(Record[] records) {
		Double sent_amount = 0.0;
		for (Record record : records) {
			sent_amount += record.getAttributeAsInt("sent_amount");
		}
		return sent_amount;
	}
	
}
