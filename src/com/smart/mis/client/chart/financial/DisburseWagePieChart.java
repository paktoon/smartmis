package com.smart.mis.client.chart.financial;

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
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;
import com.google.gwt.visualization.client.visualizations.corechart.TextStyle;
import com.smart.mis.client.function.FunctionPanel;
import com.smart.mis.client.function.report.ReportPanel;
import com.smartgwt.client.widgets.Canvas;

public class DisburseWagePieChart {

	public void loadChart(final Canvas loader, final ReportPanel panel) {  
	    Runnable onLoadCallback = new Runnable() {
	        public void run() {
	   
	          PieChart chart = new PieChart(createTable(), createOptions());

	          chart.addSelectHandler(createSelectHandler(chart, panel));
	          
	          Canvas temp = new Canvas();
	          temp.addChild(chart.asWidget());
	          temp.setHeight100();
	          temp.setWidth100();
	          loader.addChild(temp);
	        }
	      };

	      VisualizationUtils.loadVisualizationApi(onLoadCallback, PieChart.PACKAGE);
    } 
	
	  private Options createOptions() {
		    Options options = Options.create();
		    options.setWidth(500);
		    options.setHeight(350);
//		    options.setIsStacked(true);
		    options.setLegend(LegendPosition.RIGHT);
//		    options.setHAxisOptions(createAxisOption("ประเภทวัตถุดิบ"));
//		    options.setVAxisOptions(createAxisOption("ปริมาณวัตถุดิบที่เบิกจ่าย (หน่วย)"));
//		    options.setTitle("รายงานสรุปสัดส่วนค่าวัตถุดิบแยกตามประเภท");
		    ChartArea area = ChartArea.create();
		    area.setHeight("90%");
		    area.setWidth("90%");
		    options.setChartArea(area);
		    return options;
		  }
	  
//	  private AxisOptions createAxisOption(String name){
//		  AxisOptions options = AxisOptions.create();
//		  options.setTitle(name);
//		  return options;
//	  }
	  
	  private SelectHandler createSelectHandler(final PieChart chart, final ReportPanel panel) {
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
		    data.addColumn(ColumnType.STRING, "ประเภทงาน"); //0
		    data.addColumn(ColumnType.NUMBER, "สัดส่วนค่าจ้างผลิต (บาท)"); //1
//		    data.addColumn(ColumnType.NUMBER, "ปริมาณวัตถุดิบที่ถูกจอง (หน่วย)"); //2
//		    addAnnotationColumn(data); //3
		    
		    data.addRows(3);
		    //row, column
		    data.setValue(0, 0, "หล่อขึ้นรูป");
		    data.setValue(0, 1, 100000.0);
		    
		    data.setValue(1, 0, "แต่งและฝังพลอยประดับ");
		    data.setValue(1, 1, 120000.0);
		    
		    data.setValue(2, 0, "ขัดและติดพลอยแมกกาไซต์");
		    data.setValue(2, 1, 80000.0);
		    
//		    data.setValue(0, 2, 100.0);
//		    data.setValue(1, 2, 220.0);
//		    data.setValue(2, 2, 170.0);
		    
		    // 2 Digit display for remaining
		    NumberFormat.Options option = NumberFormat.Options.create();
		    option.setFractionDigits(2);
		    NumberFormat format = NumberFormat.create(option);
		    format.format(data, 1);
		    //format.format(data, 2);
		    return data;
		  }
}
