package com.smart.mis.client.chart.production;

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

public class ProductionColumnChart {

	public void loadChart(final Canvas loader, final ReportPanel panel) {  
	    Runnable onLoadCallback = new Runnable() {
	        public void run() {
	   
	          ColumnChart chart = new ColumnChart(createTable(), createOptions());

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
		    options.setIsStacked(true);
		    //options.setLegend("center");
		    //options.setLegend(createLegendOption());
		    //options.setLegend(LegendPosition.BOTTOM);
		    options.setHAxisOptions(createAxisOption("ประเภทสินค้า"));
		    options.setVAxisOptions(createAxisOption("ปริมาณที่ผลิต (ชิ้น)"));
		    ChartArea area = ChartArea.create();
		    area.setHeight("60%");
		    area.setWidth("80%");
		    options.setChartArea(area);
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
		        
		        //System.out.println(message);
		        //panel.updateInventoryProductGrid(row);
		        //Window.alert(message);
		      }
		    };
		  }

		  private AbstractDataTable createTable() {
		    DataTable data = DataTable.create();
		    data.addColumn(ColumnType.STRING, "ประเภทสินค้า"); //0
		    data.addColumn(ColumnType.NUMBER, "ปริมาณที่ผลิต (ชิ้น)"); //1
//		    addAnnotationColumn(data); //3
		    
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
		    
		    data.setValue(0, 1, 1000.0);
		    data.setValue(1, 1, 1200.0);
		    data.setValue(2, 1, 800.0);
		    data.setValue(3, 1, 1000.0);
		    data.setValue(4, 1, 1200.0);
		    data.setValue(5, 1, 800.0);
		    data.setValue(6, 1, 1000.0);
		    data.setValue(7, 1, 1200.0);
		    
//		    if (type.equalsIgnoreCase("cus_type")) {
//			    data.addRows(2);
//			    //row, column
//			    data.setValue(0, 0, "ลูกค้าทั่วไป");
//			    data.setValue(1, 0, "ลูกค้าประจำ");
//			    
//			    data.setValue(0, 1, 100000.0);
//			    data.setValue(1, 1, 300000.0);
//			    
//			    data.setValue(0, 2, 10000.0);
//			    data.setValue(1, 2, 80000.0);
//		    } else if (type.equalsIgnoreCase("bus_type")) {
//			    data.addRows(4);
//			    //row, column
//			    data.setValue(0, 0, "ค้าปลีกผ่านหน้าร้าน");
//			    data.setValue(1, 0, "ค้าปลีกผ่านเว็บไซต์");
//			    data.setValue(2, 0, "ค้าส่งผ่านหน้าร้าน");
//			    data.setValue(3, 0, "ค้าส่งผ่านเว็บไซต์");
//			    
//			    data.setValue(0, 1, 100000.0);
//			    data.setValue(1, 1, 120000.0);
//			    data.setValue(2, 1, 80000.0);
//			    data.setValue(3, 1, 100000.0);
//			    
//			    data.setValue(0, 2, 10000.0);
//			    data.setValue(1, 2, 23000.0);
//			    data.setValue(2, 2, 17000.0);
//			    data.setValue(3, 2, 9000.0);
//		    } else if (type.equalsIgnoreCase("cus_group")) {
//			    data.addRows(2);
//			    //row, column
//			    data.setValue(0, 0, "บุคคลทั่วไป");
//			    data.setValue(1, 0, "ร้านค้า");
//			    
//			    data.setValue(0, 1, 50000.0);
//			    data.setValue(1, 1, 350000.0);
//			    
//			    data.setValue(0, 2, 30000.0);
//			    data.setValue(1, 2, 60000.0);
//		    } else if (type.equalsIgnoreCase("zone")) { 
//		    	data.addRows(4);
//		    	
//		    	data.setValue(0, 0, "เอเซีย");
//			    data.setValue(1, 0, "ยุโรป");
//			    data.setValue(2, 0, "อเมริกาเหนือ");
//			    data.setValue(3, 0, "อเมริกาใต้");
//			    
//			    data.setValue(0, 1, 320000.0);
//			    data.setValue(1, 1, 20000.0);
//			    data.setValue(2, 1, 23000.0);
//			    data.setValue(3, 1, 37000.0);
//			    
//			    data.setValue(0, 2, 10000.0);
//			    data.setValue(1, 2, 30000.0);
//			    data.setValue(2, 2, 20000.0);
//			    data.setValue(3, 2, 30000.0);
//		    }
		    
//		    data.setValue(0, 3, "1100.00");
//		    data.setValue(1, 3, "1420.00");
//		    data.setValue(2, 3, "970.00");
//		    data.setValue(3, 3, "1090.00");
//		    data.setValue(4, 3, "1500.00");
//		    data.setValue(5, 3, "950.00");
//		    data.setValue(6, 3, "1120.00");
//		    data.setValue(7, 3, "1450.00");
		    
		    // 2 Digit display for remaining
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

//		private native void addToolTripColumn(DataTable data) /*-{
//		    data.addColumn({
//		        type : 'string',
//		        role : 'tooltip'
//		    });
//		}-*/;
		  
//		private native void addAnnotationColumn(DataTable data) /*-{
//		    data.addColumn({
//		        type : 'string',
//		        role : 'annotation'
//		    });
//		}-*/;
		
//		private native Options createOptionsWithAnnotationStyle() /*-{
//	      options = {
//	         annotation: {3: { style : 'line'} } // this assumes the column with index 3 is {type: "string", role: "annotation"}
//	      };
//	      return options;
//	   }-*/;
}
