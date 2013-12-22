package com.smart.mis.client.function.purchasing;

import java.util.LinkedHashMap;

import com.smart.mis.client.MainPage;
import com.smart.mis.client.function.FunctionPanel;
import com.smart.mis.client.function.FunctionStack;
import com.smart.mis.client.function.FunctionWindow;
import com.smart.mis.client.function.purchasing.material.MaterialAdd;
import com.smart.mis.client.function.purchasing.material.MaterialDS;
import com.smart.mis.client.function.purchasing.material.MaterialDetailTabPane;
import com.smart.mis.client.function.purchasing.material.MaterialListGrid;
import com.smart.mis.client.function.purchasing.order.PurchaseOrderTabSet;
import com.smart.mis.client.function.purchasing.report.ReportOrderLayout;
import com.smart.mis.client.function.purchasing.report.ReportRequestLayout;
import com.smart.mis.client.function.purchasing.request.PurchaseRequestTabSet;
import com.smart.mis.client.function.purchasing.supplier.SupplierAdd;
import com.smart.mis.client.function.purchasing.supplier.SupplierDS;
import com.smart.mis.client.function.purchasing.supplier.SupplierData;
import com.smart.mis.client.function.purchasing.supplier.SupplierDetailTabPane;
import com.smart.mis.client.function.purchasing.supplier.SupplierListGrid;
import com.smart.mis.client.function.sale.customer.CustomerAdd;
import com.smart.mis.client.function.sale.customer.CustomerDS;
import com.smart.mis.client.function.sale.customer.CustomerDetailTabPane;
import com.smart.mis.client.function.sale.customer.CustomerListGrid;
import com.smart.mis.client.function.sale.order.SaleOrderTabSet;
import com.smart.mis.client.function.sale.quotation.QuotationTabSet;
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

public class PurchasingPanel extends FunctionPanel{
	
	private final FunctionWindow materialWindow;
	private final FunctionWindow supplierWindow;
	private final FunctionWindow purchaseRequestWindow;
	private final FunctionWindow purchaseOrderWindow;
	//private final FunctionWindow reportWindow;
	private final FunctionWindow subPurcharseRequestReportWindow;
	private final FunctionWindow subPurcharseOrderReportWindow;
	
	private final SupplierListGrid supplierGrid = new SupplierListGrid();
	private final MaterialListGrid materialGrid = new MaterialListGrid();
	
	public PurchasingPanel(MainPage main){
		super(main, "ระบบการจัดซื้อวัตถุดิบ", 4);
		materialWindow = createFuncWindow();
		supplierWindow = createFuncWindow();
		purchaseRequestWindow = createFuncWindow();
		purchaseOrderWindow = createFuncWindow();
		
		//reportWindow = createFuncWindow();
		subPurcharseRequestReportWindow = createFuncWindow();
		subPurcharseOrderReportWindow = createFuncWindow();
		
		prepareSupplierWindow();
		prepareMeterialWindow();
		prepareRequestWindow();
		prepareOrderWindow();
		
		prepareRequestReportWindow();
		prepareOrderReportWindow();
	}

	@Override
	public void init() {
		this._main.getPurchasingPanel().setAlign(Alignment.CENTER);
		this._main.getPurchasingPanel().setMembers(getInitFunctionLabel());
	}

	@Override
	public void load(String nodeId, String name, String icon) {
		if (nodeId.equals("41")) {
			loadWindow(this._main.getPurchasingPanel(), this.materialWindow , name, icon);
		} else if (nodeId.equals("42")) {
			loadWindow(this._main.getPurchasingPanel(), this.supplierWindow , name, icon);
		} else if (nodeId.equals("43")) {
			loadWindow(this._main.getPurchasingPanel(), this.purchaseRequestWindow , name, icon);
		} else if (nodeId.equals("44")) {
			loadWindow(this._main.getPurchasingPanel(), this.purchaseOrderWindow , name, icon);
		} else if (nodeId.equals("45")) {
			//loadWindow(this._main.getPurchasingPanel(), this.reportWindow , name, icon);
			//Do nothing
		} else if (nodeId.equals("451")) {
			loadWindow(this._main.getPurchasingPanel(), this.subPurcharseRequestReportWindow , name, icon);
		} else if (nodeId.equals("452")) {
			loadWindow(this._main.getPurchasingPanel(), this.subPurcharseOrderReportWindow , name, icon);
		} else init();
	}
	
	private void prepareMeterialWindow(){
		FunctionStack functionStack = new FunctionStack();
        SectionStackSection itemList = new SectionStackSection("รายการข้อมูลวัตถุดิบ"); 
        itemList.setItems(materialGrid);
        itemList.setExpanded(true);
        
        SectionStackSection detailView = new SectionStackSection("จัดการข้อมูลวัตถุดิบ");  
        final MaterialDetailTabPane tabPane = new MaterialDetailTabPane(MaterialDS.getInstance(), materialGrid);
        detailView.setItems(tabPane);
        detailView.setExpanded(true);
        materialGrid.addUpdateDetailHandler(tabPane);
        
        final MaterialAdd addFunc = new MaterialAdd(MaterialDS.getInstance(), materialGrid, tabPane, this._main.getCurrentUser().getUserName());
        ToolStripButton addButton = new ToolStripButton();  
        addButton.setHeight(18);  
        addButton.setWidth(120);
        addButton.setIcon("[SKIN]actions/add.png");  
        addButton.setTitle("เพิ่มข้อมูลวัตถุดิบ");  
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
            	tabPane.onRefresh();
            }  
        });
        
        //Search section
        String[] type = {"แร่เงิน", "แมกกาไซต์", "พลอยประดับ"};
	    final DynamicForm form = new DynamicForm();
	    //form.setWidth(340);
	    form.setNumCols(4);
	    form.setDataSource(MaterialDS.getInstance());
        TextItem filterText = new TextItem("mat_name", "ชื่อวัตถุดิบ");
        filterText.setWrapTitle(false);
        //filterText.setWidth(120);
        filterText.setOperator(OperatorId.REGEXP);
        SelectItem filterItem = new SelectItem("type","ชนิด");
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
            	materialGrid.fetchData(form.getValuesAsCriteria());  
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
        this.materialWindow.addItem(functionLayout);
	}
	
	private void prepareSupplierWindow(){
		FunctionStack functionStack = new FunctionStack();
        SectionStackSection itemList = new SectionStackSection("รายการข้อมูลผู้จำหน่าย"); 
        itemList.setItems(supplierGrid);
        itemList.setExpanded(true);
        
        SectionStackSection detailView = new SectionStackSection("จัดการข้อมูลผู้จำหน่าย");  
        final SupplierDetailTabPane tabPane = new SupplierDetailTabPane(SupplierDS.getInstance(), supplierGrid);
        detailView.setItems(tabPane);
        detailView.setExpanded(true);
        supplierGrid.addUpdateDetailHandler(tabPane);
        
        final SupplierAdd addFunc = new SupplierAdd(SupplierDS.getInstance(), supplierGrid, tabPane, this._main.getCurrentUser().getUserName());
        ToolStripButton addButton = new ToolStripButton();  
        addButton.setHeight(18);  
        addButton.setWidth(120);
        addButton.setIcon("[SKIN]actions/add.png");  
        addButton.setTitle("เพิ่มข้อมูลผู้จำหน่าย");  
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
            	tabPane.onRefresh();
            }  
        });
        
        //Search section
	    final DynamicForm form = new DynamicForm();
	    form.setNumCols(2);
	    form.setDataSource(SupplierDS.getInstance());
        TextItem filterText = new TextItem("sup_name", "ชื่อผู้จำหน่าย");
        filterText.setWrapTitle(false);
        filterText.setOperator(OperatorId.REGEXP);
	    form.setItems(filterText);
        form.addItemChangedHandler(new ItemChangedHandler() {  
            public void onItemChanged(ItemChangedEvent event) {  
            	supplierGrid.fetchData(form.getValuesAsCriteria());  
            }  
        });
	    form.setColWidths(80, 120);
        //end form

        itemList.setControls(form, addButton, refreshButton);
       
        functionStack.setSections(itemList, detailView);
        
        VLayout functionLayout = new VLayout();
        functionLayout.setWidth100();
        functionLayout.setHeight100();
        functionLayout.setMembers(functionStack);
        this.supplierWindow.addItem(functionLayout);
	}
	
	private void prepareRequestWindow(){
		byte currentRole = this._main.getCurrentUser().getProfile().getRole();
		Boolean allow = checkPermFlag(currentRole, Role.ADMIN) || checkPermFlag(currentRole, Role.OWNER);
		PurchaseRequestTabSet requestTab = new PurchaseRequestTabSet(allow, this._main.getCurrentUser());
		this.purchaseRequestWindow.addItem(requestTab);
	}
	
	private void prepareOrderWindow(){
		PurchaseOrderTabSet orderTab = new PurchaseOrderTabSet(this._main.getCurrentUser());
		this.purchaseOrderWindow.addItem(orderTab);
	}
	
	private void prepareRequestReportWindow() {
		VLayout report = new ReportRequestLayout(this._main.getCurrentUser());
		this.subPurcharseRequestReportWindow.addItem(report);
	}
	
	private void prepareOrderReportWindow() {
		VLayout report = new ReportOrderLayout(this._main.getCurrentUser());
		this.subPurcharseOrderReportWindow.addItem(report);
	}
	
	private boolean checkPermFlag(byte flag, byte checked){
		return (flag & checked) == checked;
	}
}
