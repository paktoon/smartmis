package com.smart.mis.client.function.sale;

import java.util.LinkedHashMap;

import com.smart.mis.client.MainPage;
import com.smart.mis.client.chart.CustomColumnChart;
import com.smart.mis.client.chart.CustomPieChart;
import com.smart.mis.client.function.FunctionPanel;
import com.smart.mis.client.function.FunctionStack;
import com.smart.mis.client.function.FunctionWindow;
import com.smart.mis.client.function.sale.customer.CustomerAdd;
import com.smart.mis.client.function.sale.customer.CustomerDS;
import com.smart.mis.client.function.sale.customer.CustomerDetailTabPane;
import com.smart.mis.client.function.sale.customer.CustomerListGrid;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.events.ItemChangedHandler;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class SalePanel extends FunctionPanel{
	
	private final FunctionWindow customerWindow;
	private final FunctionWindow quotationWindow;
	private final FunctionWindow saleOrderWindow;
	private final FunctionWindow invoiceWindow;
	private final FunctionWindow checkPOWindow;
	//private final FunctionWindow saleReportWindow;
	private final FunctionWindow SubQuotationReportWindow;
	private final FunctionWindow SubSaleReportWindow;
	private final FunctionWindow SubDeliveryReportWindow;
	private final FunctionWindow SubInvoiceReportWindow;
	
	private final CustomerListGrid customerGrid = new CustomerListGrid();

	public SalePanel(MainPage main){
		super(main, "ระบบจัดการงานขาย", 1);
		customerWindow = createFuncWindow();
		quotationWindow = createFuncWindow();
		saleOrderWindow = createFuncWindow();
		invoiceWindow = createFuncWindow();
		checkPOWindow = createFuncWindow();
		//saleReportWindow = createFuncWindow();
		
		SubQuotationReportWindow = createFuncWindow();
		SubSaleReportWindow = createFuncWindow();
		SubDeliveryReportWindow = createFuncWindow();
		SubInvoiceReportWindow = createFuncWindow();
		
		prepareCustomerWindow();
//		prepareQuotationWindow();
//		prepareSaleOrderWindow();
//		prepareCheckPOWindow();
//		prepareSaleReportWindow();
	}

	@Override
	public void init() {
		this._main.getSalePanel().setAlign(Alignment.CENTER);
		this._main.getSalePanel().setMembers(getInitFunctionLabel());
	}

	@Override
	public void load(String nodeId, String name, String icon) {
		if (nodeId.equals("11")) {
			loadWindow(this._main.getSalePanel(), this.customerWindow , name, icon);
		} else if (nodeId.equals("12")) {
			loadWindow(this._main.getSalePanel(), this.quotationWindow , name, icon);
		} else if (nodeId.equals("13")) {
			loadWindow(this._main.getSalePanel(), this.saleOrderWindow , name, icon);
		} else if (nodeId.equals("14")) {
			loadWindow(this._main.getSalePanel(), this.checkPOWindow , name, icon);
		} else if (nodeId.equals("16")) {
			loadWindow(this._main.getSalePanel(), this.invoiceWindow , name, icon);
		} else if (nodeId.equals("15")) {
			//loadWindow(this._main.getSalePanel(), this.saleReportWindow , name, icon);
			//Do nothing
		} else if (nodeId.equals("151")) {
			loadWindow(this._main.getSalePanel(), this.SubQuotationReportWindow , name, icon);
		} else if (nodeId.equals("152")) {
			LoadSaleReportWindow();
			loadWindow(this._main.getSalePanel(), this.SubSaleReportWindow , name, icon);
		} else if (nodeId.equals("153")) {
			loadWindow(this._main.getSalePanel(), this.SubDeliveryReportWindow , name, icon);
		} else if (nodeId.equals("154")) {
			loadWindow(this._main.getSalePanel(), this.SubInvoiceReportWindow , name, icon);
		} else init();
	}
	
	private void prepareCustomerWindow(){
		FunctionStack functionStack = new FunctionStack();
        SectionStackSection itemList = new SectionStackSection("รายการข้อมูลลูกค้า"); 
        itemList.setItems(customerGrid);
        itemList.setExpanded(true);
        
        SectionStackSection detailView = new SectionStackSection("จัดการข้อมูลลูกค้า");  
        final CustomerDetailTabPane customerTabPane = new CustomerDetailTabPane(CustomerDS.getInstance(), customerGrid);
        detailView.setItems(customerTabPane);
        detailView.setExpanded(true);
        customerGrid.addUpdateDetailHandler(customerTabPane);
        
        final CustomerAdd addFunc = new CustomerAdd(CustomerDS.getInstance(), customerGrid, customerTabPane, this._main.getCurrentUser().getUserName());
        ToolStripButton addButton = new ToolStripButton();  
        addButton.setHeight(18);  
        addButton.setWidth(120);
        addButton.setIcon("[SKIN]actions/add.png");  
        addButton.setTitle("เพิ่มข้อมูลลูกค้า");  
        addButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	addFunc.show();
            }  
        });  
        
        ToolStripButton refreshButton = new ToolStripButton();  
        refreshButton.setHeight(18);  
        refreshButton.setWidth(120);
        refreshButton.setIcon("[SKIN]actions/refresh.png");  
        refreshButton.setTitle("refresh");  
        refreshButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	customerTabPane.onRefresh();
            }  
        });
        
        //Search section
        String[] type = {"ลูกค้าประจำ", "ลูกค้าทั่วไป"};
	    final DynamicForm form = new DynamicForm();
	    //form.setWidth(340);
	    form.setNumCols(4);
	    form.setDataSource(CustomerDS.getInstance());
        TextItem filterText = new TextItem("cus_name", "ชื่อลูกค้า");
        filterText.setWrapTitle(false);
        //filterText.setWidth(120);
        filterText.setOperator(OperatorId.REGEXP);
        SelectItem filterItem = new SelectItem("cus_type","ประเภท");
        //filterItem.setWidth(120); 
        filterItem.setAllowEmptyValue(true);
        LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
        for (String item : type) {
        	valueMap.put(item, item);
        }
        filterItem.setValueMap(valueMap);
	    form.setItems(filterText, filterItem);
        form.addItemChangedHandler(new ItemChangedHandler() {  
            public void onItemChanged(ItemChangedEvent event) {  
            	customerGrid.fetchData(form.getValuesAsCriteria());  
            }  
        });
	    form.setColWidths(80, 120, 50, 120);
        //end form

        itemList.setControls(form, addButton, refreshButton);
       
        functionStack.setSections(itemList, detailView);
        
        VLayout functionLayout = new VLayout();
        functionLayout.setWidth100();
        functionLayout.setHeight100();
        functionLayout.setMembers(functionStack);
        this.customerWindow.addItem(functionLayout);
	}
	
	private void LoadSaleReportWindow() {
		for (Canvas removed : this.SubSaleReportWindow.getItems()) {
			this.SubSaleReportWindow.removeItem(removed);
		}
		CustomColumnChart chart = new CustomColumnChart();
		VLayout report = new VLayout();
		chart.loadChart(report);
		this.SubSaleReportWindow.addItem(report);
	}
}
