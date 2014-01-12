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

public class ProductReceivedColumnChart {

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
		    //Options options = createOptionsWithAnnotationStyle();
		    options.setWidth(1000);
		    options.setHeight(350);
		    //options.setIsStacked(true);
		    //options.setLegend("center");
		    //options.setLegend(createLegendOption());
		    options.setLegend(LegendPosition.NONE);
		    options.setHAxisOptions(createAxisOption("ประเภทสินค้า"));
		    options.setVAxisOptions(createAxisOption("ปริมาณสินค้าที่รับ (ชิ้น)"));
//		    ChartArea area = ChartArea.create();
//		    area.setHeight("70%");
//		    area.setWidth("80%");
//		    options.setChartArea(area);
//		    Options annotationOption = Options.create();
//		    Options annotationStyle = Options.create();
//		    annotationStyle.set("style", "line");
//		    annotationOption.set("3", annotationStyle);
//		    options.set("annotation", annotationOption);
		    
//		    Options groupWidthOption = Options.create();
//		    groupWidthOption.set("groupWidth", "50%");
		    
		    //options.set3D(true);
		    //options.setTitle("รายงานสรุปยอดสินค้าคงเหลือ");
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
		        
		        System.out.println(message);
		        //panel.updateInventoryProductGrid(row);
		        //Window.alert(message);
		      }
		    };
		  }

		  private AbstractDataTable createTable(Double[][] item) {
		    DataTable data = DataTable.create();
		    data.addColumn(ColumnType.STRING, "ประเภทสินค้า"); //0
		    data.addColumn(ColumnType.NUMBER, "ปริมาณสินค้าที่รับ(ชิ้น)"); //1
		    
		    data.addRows(8);
		    //row, column
		    data.setValue(0, 0, "แหวนนิ้วมือ");
		    data.setValue(1, 0, "แหวนนิ้วเท้า");
		    data.setValue(2, 0, "ต่างหู");
		    data.setValue(3, 0, "สร้อยคอ");
		    data.setValue(4, 0, "จี้");
		    data.setValue(5, 0, "กำไลข้อมือ");
		    data.setValue(6, 0, "กำไลข้อเท้า");
		    data.setValue(7, 0, "สร้อยข้อเท้าหรือข้อมือ");
		    
		    for (int i = 0 ; i <= 7 ; i ++) {
		    	data.setValue(i, 1, item[0][i]);
		    }
		    
		    // 2 Digit display for remaining
		    NumberFormat.Options option = NumberFormat.Options.create();
		    option.setFractionDigits(2);
		    NumberFormat format = NumberFormat.create(option);
		    format.format(data, 1);
		    return data;
		  }
}
