package com.smart.mis.client.chart;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.events.SelectHandler.SelectEvent;
import com.google.gwt.visualization.client.formatters.NumberFormat;
import com.google.gwt.visualization.client.visualizations.corechart.AxisOptions;
import com.google.gwt.visualization.client.visualizations.corechart.ColumnChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import com.smartgwt.client.widgets.Canvas;

public class CustomColumnChart {

	public void loadChart(final Canvas loader) {  
	    Runnable onLoadCallback = new Runnable() {
	        public void run() {
	   
	          ColumnChart chart = new ColumnChart(createTable(), createOptions());

	          chart.addSelectHandler(createSelectHandler(chart));
	          
	          Canvas temp = new Canvas();
	          temp.addChild(chart.asWidget());
	          temp.setHeight100();
	          temp.setWidth100();
	          loader.addChild(temp);
	        }
	      };

	      VisualizationUtils.loadVisualizationApi(onLoadCallback, ColumnChart.PACKAGE);
    } 
	
	  private Options createOptions() {
		    Options options = Options.create();
		    options.setWidth(550);
		    options.setHeight(240);
		    //options.setLegend("center");
		    options.setHAxisOptions(createAxisOption("ประเภทลูกค้า"));
		    options.setVAxisOptions(createAxisOption("ยอดขาย (บาท)"));
		    //options.set3D(true);
		    options.setTitle("รายงานแสดงสัดส่วนยอดขายแยกตามประเภทลูกค้า");
		    return options;
		  }
	  
	  private AxisOptions createAxisOption(String name){
		  AxisOptions options = AxisOptions.create();
		  options.setTitle(name);
		  return options;
	  }
	  
	  private SelectHandler createSelectHandler(final ColumnChart chart) {
		    return new SelectHandler() {
		      @Override
		      public void onSelect(SelectEvent event) {
		        String message = "";
		        
		        // May be multiple selections.
		        JsArray<Selection> selections = chart.getSelections();

		        for (int i = 0; i < selections.length(); i++) {
		          // add a new line for each selection
		          message += i == 0 ? "" : "\n";
		          
		          Selection selection = selections.get(i);

		          if (selection.isCell()) {
		            // isCell() returns true if a cell has been selected.
		            
		            // getRow() returns the row number of the selected cell.
		            int row = selection.getRow();
		            // getColumn() returns the column number of the selected cell.
		            int column = selection.getColumn();
		            message += "cell " + row + ":" + column + " selected";
		          } else if (selection.isRow()) {
		            // isRow() returns true if an entire row has been selected.
		            
		            // getRow() returns the row number of the selected row.
		            int row = selection.getRow();
		            message += "row " + row + " selected";
		          } else {
		            // unreachable
		            message += "Pie chart selections should be either row selections or cell selections.";
		            message += "  Other visualizations support column selections as well.";
		          }
		        }
		        
		        //Window.alert(message);
		      }
		    };
		  }

		  private AbstractDataTable createTable() {
		    DataTable data = DataTable.create();
		    data.addColumn(ColumnType.STRING, "ประเภทลูกค้า");
		    data.addColumn(ColumnType.NUMBER, "ยอดขาย (บาท)");
		    
		    data.addRows(2);
		    data.setValue(0, 0, "ลูกค้าทั่วไป");
		    data.setValue(0, 1, 152314.50);
		    data.setValue(1, 0, "ลูกค้าประจำ");
		    data.setValue(1, 1, 256241);
		    
		    // 2 Digit display for sales
		    NumberFormat.Options option = NumberFormat.Options.create();
		    option.setFractionDigits(2);
		    NumberFormat format = NumberFormat.create(option);
		    format.format(data, 1);
//		    google.visualization.arrayToDataTable({
//		    	{'Year', 'Sales', 'Expenses'},
//		    	{'2004',  1000,      400},
//		    	{'2005',  1170,      460},
//		    	{'2006',  660,       1120},
//		    	{'2007',  1030,      540}
//		    });
		    return data;
		  }

}
