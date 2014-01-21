package com.smart.mis.client.cube;

import com.google.gwt.i18n.client.NumberFormat;
import com.smartgwt.client.types.AutoSelectionModel;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.cube.CubeGrid;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SaleCube {

	public static CubeGrid createCubeGrid(String time, String customer, String sale, String product, String yearSelected) {
		CubeGrid cubeGrid = new CubeGrid();  
        //in order to enable charting, the Drawing module must be present  
        if(SC.hasDrawing()) {  
            cubeGrid.setEnableCharting(true);
        }  
       
		// Time : yearly, quaterly (fit in 1 year), monthly (fit in 1 year)
		// Customer : Type, name (fit in type : todo), zone (fit in type)
		// Sale : name
		// Product : Type, name (fit in type : todo)
        
        //cubeGrid.setFacets(SaleVolumnFacets.getSaleVolumnFacets());
        //cubeGrid.setData(SaleVolumnData.getData());
        cubeGrid.setValueProperty("value");
        cubeGrid.setValueTitle("Sales");
        //cubeGrid.setColumnFacets("time", "metric");
        
        if (time.equalsIgnoreCase("yearly")) {
        	//cubeGrid.setColumnFacets("year", "quarter", "metric");
            cubeGrid.setData(SaleVolumnData.getYearlyData());
        	cubeGrid.setColumnFacets("year", "metric");
        } else if (time.equalsIgnoreCase("quarterly")) { 
        	//todo
        	//cubeGrid.setColumnFacets("quarter", "month", "metric");
        	cubeGrid.setData(SaleVolumnData.getQuarterlyData(yearSelected));
        	cubeGrid.setColumnFacets("year", "quarter", "metric");
        } else if (time.equalsIgnoreCase("monthly")) { 
        	//todo
        	//cubeGrid.setColumnFacets("quarter", "month", "metric");
        	cubeGrid.setData(SaleVolumnData.getMonthlyData(yearSelected));
        	cubeGrid.setColumnFacets("year", "month", "metric");
        }

    	cubeGrid.setRowFacets("customer", "product");
        
        cubeGrid.setWidth100();  
        cubeGrid.setHeight100();  
        cubeGrid.setHideEmptyFacetValues(true);  
        cubeGrid.setShowCellContextMenus(true);  
        cubeGrid.setCanCollapseFacets(true);
        cubeGrid.setCanMinimizeFacets(true); 
        cubeGrid.setAutoSelectValues(AutoSelectionModel.BOTH);
        cubeGrid.setRowHeaderGridMode(true);
  
        final NumberFormat numberFormat_1 = NumberFormat.getFormat("0,000.00");  
        final NumberFormat numberFormat_2 = NumberFormat.getFormat("000.00");  
        
        cubeGrid.setCellFormatter(new CellFormatter() {  
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {  
                if (value == null) return null;  
                try {  
                	if (((Number) value).longValue() > 999) {
                		return numberFormat_1.format(((Number) value).longValue());  
                	} else return numberFormat_2.format(((Number) value).longValue());
                } catch (Exception e) {  
                    return value.toString();  
                }  
            }  
        });   
        
        return cubeGrid;
	}
}
