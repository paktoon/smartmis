package com.smart.mis.client.chart;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import com.smartgwt.client.widgets.Canvas;
 
public class CustomPieChart {
	
	public void loadChart(final Canvas loader) {  
	    Runnable onLoadCallback = new Runnable() {
	        public void run() {
	   
	          // Create a pie chart visualization.
	          PieChart pie = new PieChart(createTable(), createOptions());

	          pie.addSelectHandler(createSelectHandler(pie));
	          
	          Canvas temp = new Canvas();
	          temp.addChild(pie.asWidget());
	          temp.setHeight100();
	          temp.setWidth100();
	          loader.addChild(temp);
	        }
	      };

	      VisualizationUtils.loadVisualizationApi(onLoadCallback, PieChart.PACKAGE);
    } 
	
	  private Options createOptions() {
		    Options options = Options.create();
		    options.setWidth(400);
		    options.setHeight(240);
		    //options.set3D(true);
		    options.setTitle("รายงานแสดงสัดส่วนยอดขายแยกตามประเภทลูกค้า");
		    return options;
		  }
	  
	  private SelectHandler createSelectHandler(final PieChart chart) {
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
		    data.addColumn(ColumnType.STRING, "จำนวน (ราย)");
		    data.addColumn(ColumnType.NUMBER, "สัดส่วนรายได้ (%)");
		    data.addRows(2);
		    data.setValue(0, 0, "ลูกค้าทั่วไป");
		    data.setValue(0, 1, 152314);
		    data.setValue(1, 0, "ลูกค้าประจำ");
		    data.setValue(1, 1, 256241);
		    return data;
		  }
}
