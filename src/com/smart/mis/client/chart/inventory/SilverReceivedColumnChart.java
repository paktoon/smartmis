package com.smart.mis.client.chart.inventory;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.ChartArea;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.LegendPosition;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.events.SelectHandler.SelectEvent;
import com.google.gwt.visualization.client.formatters.NumberFormat;
import com.google.gwt.visualization.client.visualizations.corechart.AxisOptions;
import com.google.gwt.visualization.client.visualizations.corechart.ColumnChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import com.google.gwt.visualization.client.visualizations.corechart.TextStyle;
import com.smart.mis.client.function.FunctionPanel;
import com.smart.mis.client.function.report.ReportPanel;
import com.smartgwt.client.widgets.Canvas;

public class SilverReceivedColumnChart {

	public void loadChart(final Canvas loader, final ReportPanel panel, final Double[][] item) {  
	    Runnable onLoadCallback = new Runnable() {
	        public void run() {
	   
	          ColumnChart chart = new ColumnChart(createTable(item), createOptions());

	          chart.addSelectHandler(createSelectHandler(chart, panel));
	          
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
		    options.setHeight(350);
//		    options.setIsStacked(true);
		    options.setLegend(LegendPosition.NONE);
		    options.setHAxisOptions(createAxisOption("ประเภทแร่เงิน"));
		    options.setVAxisOptions(createAxisOption("ปริมาณแร่เงินที่รับ(กรัม)"));
		    options.setTitle("รายงานสรุปการรับแร่เงิน");
//		    ChartArea area = ChartArea.create();
//		    area.setHeight("80%");
//		    area.setWidth("80%");
//		    options.setChartArea(area);
		    return options;
		  }
	  
	  private AxisOptions createAxisOption(String name){
		  AxisOptions options = AxisOptions.create();
		  options.setTitle(name);
		  return options;
	  }
	  
	  private SelectHandler createSelectHandler(final ColumnChart chart, final ReportPanel panel) {
		    return new SelectHandler() {
		      @Override
		      public void onSelect(SelectEvent event) {
		        String message = "";
		        
		        // May be multiple selections.
		        JsArray<Selection> selections = chart.getSelections();

		        int row = -1;
		          
		        for (int i = 0; i < selections.length(); i++) {
		          // add a new line for each selection
		          message += i == 0 ? "" : "\n";
		          
		          Selection selection = selections.get(i);

		          if (selection.isCell()) {
		            // isCell() returns true if a cell has been selected.
		            
		            // getRow() returns the row number of the selected cell.
		            row = selection.getRow();
		            // getColumn() returns the column number of the selected cell.
		            int column = selection.getColumn();
		            message += "cell " + row + ":" + column + " selected";
		          } else if (selection.isRow()) {
		            // isRow() returns true if an entire row has been selected.
		            
		            // getRow() returns the row number of the selected row.
		            row = selection.getRow();
		            message += "row " + row + " selected";
		          } else {
		            // unreachable
		            message += "Column chart selections should be either row selections or cell selections.";
		            message += "  Other visualizations support column selections as well.";
		          }
		        }
		        
		        //System.out.println(message);
		        //panel.updateProductGrid(row);
		        //panel.updateInventoryMaterialGrid(1, 0);
		        //Window.alert(message);
		      }
		    };
		  }

		  private AbstractDataTable createTable(Double[][] item) {
		    DataTable data = DataTable.create();
		    data.addColumn(ColumnType.STRING, "ประเภทแร่เงิน"); //0
		    data.addColumn(ColumnType.NUMBER, "ปริมาณแร่เงินที่รับ(กรัม)"); //1
//		    data.addColumn(ColumnType.NUMBER, "ปริมาณแร่เงินที่ถูกจอง (กรัม)"); //2
//		    addAnnotationColumn(data); //3
		    
		    data.addRows(2);
		    //row, column
		    data.setValue(0, 0, "แร่เงิน 100%");
		    data.setValue(1, 0, "แร่เงิน 92.5%");
		    
		    data.setValue(0, 1, item[0][0]);
		    data.setValue(1, 1, item[0][1]);
		    
//		    data.setValue(0, 2, 100.0);
//		    data.setValue(1, 2, 220.0);
		    
		    // 2 Digit display for remaining
		    NumberFormat.Options option = NumberFormat.Options.create();
		    option.setFractionDigits(2);
		    NumberFormat format = NumberFormat.create(option);
		    format.format(data, 1);
		    //format.format(data, 2);
		    return data;
		  }
}
