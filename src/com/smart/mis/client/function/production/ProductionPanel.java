package com.smart.mis.client.function.production;

import java.util.LinkedHashMap;

import com.smart.mis.client.MainPage;
import com.smart.mis.client.function.FunctionPanel;
import com.smart.mis.client.function.FunctionStack;
import com.smart.mis.client.function.FunctionWindow;
import com.smart.mis.client.function.production.order.ProductionOrderTabSet;
import com.smart.mis.client.function.production.plan.PlanTabSet;
import com.smart.mis.client.function.production.product.ProductAdd;
import com.smart.mis.client.function.production.product.ProductDS;
import com.smart.mis.client.function.production.product.ProductDetailTabPane;
import com.smart.mis.client.function.production.product.ProductListGrid;
import com.smart.mis.client.function.production.report.ReportProductionPlanLayout;
import com.smart.mis.client.function.production.report.ReportTransferLayout;
import com.smart.mis.client.function.production.smith.SmithAdd;
import com.smart.mis.client.function.production.smith.SmithDS;
import com.smart.mis.client.function.production.smith.SmithDetailTabPane;
import com.smart.mis.client.function.production.smith.SmithListGrid;
import com.smart.mis.client.function.purchasing.material.MaterialDS;
import com.smart.mis.client.function.sale.customer.CustomerAdd;
import com.smart.mis.client.function.sale.customer.CustomerDS;
import com.smart.mis.client.function.sale.customer.CustomerDetailTabPane;
import com.smart.mis.client.function.sale.customer.CustomerListGrid;
import com.smart.mis.client.function.sale.quotation.QuotationTabSet;
import com.smart.mis.client.function.sale.report.ReportDeliveryLayout;
import com.smart.mis.shared.security.Role;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.OperatorId;
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

public class ProductionPanel extends FunctionPanel{
	
	private final FunctionWindow smithWindow;
	private final FunctionWindow productWindow;
	private final FunctionWindow planWindow;
	private final FunctionWindow produceWindow;
	//private final FunctionWindow reportWindow;
	private final FunctionWindow SubPlanReportWindow;
	private final FunctionWindow SubTransferReportWindow;
	
	private final SmithListGrid smithGrid = new SmithListGrid();
	private final ProductListGrid productGrid = new ProductListGrid();
	
	public ProductionPanel(MainPage main){
		super(main, "ระบบจัดการงานผลิต", 2);
		smithWindow = createFuncWindow();
		productWindow = createFuncWindow();
		planWindow = createFuncWindow();
		produceWindow = createFuncWindow();
		//reportWindow = createFuncWindow();
		
		SubPlanReportWindow = createFuncWindow();
		SubTransferReportWindow = createFuncWindow();
		
		prepareSmithWindow();
		prepareProductWindow();
		preparePlanWindow();
		prepareOrderWindow();
		
		prepareProductionPlanReportWindow();
		prepareTransferReportWindow();
	}

	@Override
	public void init() {
		this._main.getProductionPanel().setAlign(Alignment.CENTER);
		this._main.getProductionPanel().setMembers(getInitFunctionLabel());
	}

	@Override
	public void load(String nodeId, String name, String icon) {
		if (nodeId.equals("21")) {
			loadWindow(this._main.getProductionPanel(), this.smithWindow , name, icon);
		} else if (nodeId.equals("22")) {
			loadWindow(this._main.getProductionPanel(), this.productWindow , name, icon);
		} else if (nodeId.equals("23")) {
			loadWindow(this._main.getProductionPanel(), this.planWindow , name, icon);
		} else if (nodeId.equals("24")) {
			loadWindow(this._main.getProductionPanel(), this.produceWindow , name, icon);
		} else if (nodeId.equals("25")) {
			//loadWindow(this._main.getProductionPanel(), this.reportWindow , name, icon);
			//Do nothing
		} else if (nodeId.equals("251")) {
			loadWindow(this._main.getProductionPanel(), this.SubPlanReportWindow , name, icon);
		} else if (nodeId.equals("252")) {
			loadWindow(this._main.getProductionPanel(), this.SubTransferReportWindow , name, icon);
		} else init();
	}
	
	private void prepareSmithWindow(){
		FunctionStack functionStack = new FunctionStack();
        SectionStackSection itemList = new SectionStackSection("รายการข้อมูลช่าง"); 
        itemList.setItems(smithGrid);
        itemList.setExpanded(true);
        
        SectionStackSection detailView = new SectionStackSection("จัดการข้อมูลช่าง");  
        final SmithDetailTabPane smithTabPane = new SmithDetailTabPane(SmithDS.getInstance(), smithGrid);
        detailView.setItems(smithTabPane);
        detailView.setExpanded(true);
        smithGrid.addUpdateDetailHandler(smithTabPane);
        
        final SmithAdd addFunc = new SmithAdd(SmithDS.getInstance(), smithGrid, smithTabPane, this._main.getCurrentUser().getUserName());
        ToolStripButton addButton = new ToolStripButton();  
        addButton.setHeight(18);  
        addButton.setWidth(120);
        addButton.setIcon("[SKIN]actions/add.png");  
        addButton.setTitle("เพิ่มข้อมูลช่าง");  
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
            	smithTabPane.onRefresh();
            }  
        });
        
        //Search section
        //String[] type = {"ลูกค้าประจำ", "ลูกค้าทั่วไป"};
	    final DynamicForm form = new DynamicForm();
	    //form.setWidth(340);
	    form.setNumCols(4);
	    form.setDataSource(SmithDS.getInstance());
        TextItem filterText1 = new TextItem("smid", "รหัสช่าง");
        filterText1.setWrapTitle(false);
        filterText1.setOperator(OperatorId.REGEXP);
        TextItem filterText2 = new TextItem("name", "ชื่อช่าง");
        filterText2.setWrapTitle(false);
        filterText2.setOperator(OperatorId.REGEXP);
        
	    form.setItems(filterText1, filterText2);
        form.addItemChangedHandler(new ItemChangedHandler() {  
            public void onItemChanged(ItemChangedEvent event) {  
            	smithGrid.fetchData(form.getValuesAsCriteria());  
            }  
        });
	    form.setColWidths(50, 120, 50, 120);
        //end form

        itemList.setControls(form, addButton, refreshButton);
       
        functionStack.setSections(itemList, detailView);
        
        VLayout functionLayout = new VLayout();
        functionLayout.setWidth100();
        functionLayout.setHeight100();
        functionLayout.setMembers(functionStack);
        this.smithWindow.addItem(functionLayout);
	}
	
	private void prepareProductWindow(){
		FunctionStack functionStack = new FunctionStack();
        SectionStackSection itemList = new SectionStackSection("รายการข้อมูลสินค้า"); 
        itemList.setItems(productGrid);
        itemList.setExpanded(true);
        
        SectionStackSection detailView = new SectionStackSection("จัดการข้อมูลสินค้า");  
        final ProductDetailTabPane productTabPane = new ProductDetailTabPane(ProductDS.getInstance(), productGrid);
        detailView.setItems(productTabPane);
        detailView.setExpanded(true);
        productGrid.addUpdateDetailHandler(productTabPane);
        
        final ProductAdd addFunc = new ProductAdd(ProductDS.getInstance(), productGrid, productTabPane, this._main.getCurrentUser().getUserName());
        ToolStripButton addButton = new ToolStripButton();  
        addButton.setHeight(18);  
        addButton.setWidth(120);
        addButton.setIcon("[SKIN]actions/add.png");  
        addButton.setTitle("เพิ่มข้อมูลสินค้า");  
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
            	productTabPane.onRefresh();
            }  
        });
        
        //Search section
        String[] type = {"ต่างหู", "จี้", "สร้อยคอ", "สร้อยข้อมือ", "สร้อยข้อเท้า", "กำไลข้อมือ", "กำไลข้อเท้า", "แหวนนิ้วมือ", "แหวนนิ้วเท้า"};
	    final DynamicForm form = new DynamicForm();
	    //form.setWidth(340);
        form.setNumCols(4);
	    form.setDataSource(ProductDS.getInstance());
        TextItem filterText = new TextItem("pid", "รหัสสินค้า");
        filterText.setWrapTitle(false);
        //filterText.setWidth(120);
        filterText.setOperator(OperatorId.REGEXP);
        SelectItem filterItem = new SelectItem("type","ประเภท");
        filterItem.setAllowEmptyValue(true);
        LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
        for (String item : type) {
        	valueMap.put(item, item);
        }
        
	    form.setItems(filterText, filterItem);
        form.addItemChangedHandler(new ItemChangedHandler() {  
            public void onItemChanged(ItemChangedEvent event) {  
            	productGrid.fetchData(form.getValuesAsCriteria());  
            }  
        });
	    form.setColWidths(50, 80, 50, 80);
        //end form

        itemList.setControls(form, addButton, refreshButton);
       
        functionStack.setSections(itemList, detailView);
        
        VLayout functionLayout = new VLayout();
        functionLayout.setWidth100();
        functionLayout.setHeight100();
        functionLayout.setMembers(functionStack);
        this.productWindow.addItem(functionLayout);
	}
	
	private void preparePlanWindow(){
		byte currentRole = this._main.getCurrentUser().getProfile().getRole();
		Boolean allow = checkPermFlag(currentRole, Role.ADMIN) || checkPermFlag(currentRole, Role.OWNER);
		PlanTabSet planTab = new PlanTabSet(allow, this._main.getCurrentUser());
		this.planWindow.addItem(planTab);
	}
	
	private void prepareOrderWindow(){
		ProductionOrderTabSet orderTab = new ProductionOrderTabSet( this._main.getCurrentUser());
		this.produceWindow.addItem(orderTab);
	}
	
	private void prepareProductionPlanReportWindow() {
		VLayout report = new ReportProductionPlanLayout(this._main.getCurrentUser());
		this.SubPlanReportWindow.addItem(report);
	}
	
	private void prepareTransferReportWindow() {
		VLayout report = new ReportTransferLayout(this._main.getCurrentUser());
		this.SubTransferReportWindow.addItem(report);
	}
	
	private boolean checkPermFlag(byte flag, byte checked){
		return (flag & checked) == checked;
	}
}
