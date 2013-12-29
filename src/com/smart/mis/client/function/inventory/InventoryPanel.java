package com.smart.mis.client.function.inventory;

import com.smart.mis.client.MainPage;
import com.smart.mis.client.function.FunctionPanel;
import com.smart.mis.client.function.FunctionWindow;
import com.smart.mis.client.function.financial.receipt.ReceiptLayout;
import com.smart.mis.client.function.inventory.material.ViewMaterialLaylout;
import com.smart.mis.client.function.inventory.material.received.ReceivedPurchaseLayout;
import com.smart.mis.client.function.inventory.material.requisition.MaterialRequestLayout;
import com.smart.mis.client.function.inventory.material.returns.ReturnLayout;
import com.smart.mis.client.function.inventory.product.ViewProductLaylout;
import com.smart.mis.client.function.inventory.product.request.DeliveryOrderLayout;
import com.smart.mis.client.function.inventory.product.transfer.TransferLayout;
import com.smart.mis.client.function.inventory.report.ReportInventoryLayout;
import com.smart.mis.client.function.inventory.report.ReportReceiveLayout;
import com.smart.mis.client.function.inventory.report.ReportRequestLayout;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.layout.VLayout;

public class InventoryPanel extends FunctionPanel{
	
	private final FunctionWindow outProductWindow;
	private final FunctionWindow outMaterialWindow;
	private final FunctionWindow inProductWindow;
	private final FunctionWindow inMaterialWindow;
	private final FunctionWindow returnMaterialWindow;
	private final FunctionWindow allRamainWindow;
	private final FunctionWindow productRemainWindow;
	private final FunctionWindow materialRemainWindow;
	//private final FunctionWindow reportWindow;
	private final FunctionWindow SubInventoryReportWindow;
	private final FunctionWindow SubInProductMaterialReportWindow;
	private final FunctionWindow SubOutProductMaterialReportWindow;
	
	public InventoryPanel(MainPage main){
		super(main, "ระบบจัดการคลังสินค้าและวัตถุดิบ", 3);
		outProductWindow = createFuncWindow();
		outMaterialWindow = createFuncWindow();
		inProductWindow = createFuncWindow();
		inMaterialWindow = createFuncWindow();
		returnMaterialWindow = createFuncWindow();
		allRamainWindow = createFuncWindow();
		productRemainWindow = createFuncWindow();
		materialRemainWindow = createFuncWindow();
		
		//reportWindow = createFuncWindow();
		SubInventoryReportWindow = createFuncWindow();
		SubInProductMaterialReportWindow = createFuncWindow();
		SubOutProductMaterialReportWindow = createFuncWindow();
		
		prepareTransferWindow();
		prepareOutProductWindow();
		prepareInMaterialWindow();
		prepareReturnMaterialWindow();
		prepareRequestMaterialWindow();
		
		prepareProductRemainWindow();
		prepareMaterialRemainWindow();
		
		prepareInventoryWindow();
		prepareInReportWindow();
		prepareOutReportWindow();
	}

	@Override
	public void init() {
		this._main.getInventoryPanel().setAlign(Alignment.CENTER);
		this._main.getInventoryPanel().setMembers(getInitFunctionLabel());
	}

	@Override
	public void load(String nodeId, String name, String icon) {
		if (nodeId.equals("31")) {
			loadWindow(this._main.getInventoryPanel(), this.outProductWindow , name, icon);
		} else if (nodeId.equals("32")) {
			loadWindow(this._main.getInventoryPanel(), this.outMaterialWindow , name, icon);
		} else if (nodeId.equals("33")) {
			loadWindow(this._main.getInventoryPanel(), this.inProductWindow , name, icon);
		} else if (nodeId.equals("34")) {
			loadWindow(this._main.getInventoryPanel(), this.inMaterialWindow , name, icon);
		} else if (nodeId.equals("35")) {
			loadWindow(this._main.getInventoryPanel(), this.returnMaterialWindow , name, icon);
		} else if (nodeId.equals("36")) {
			//loadWindow(this._main.getInventoryPanel(), this.allRamainWindow , name, icon);
			//Do nothing
		} else if (nodeId.equals("361")) {
			loadWindow(this._main.getInventoryPanel(), this.productRemainWindow , name, icon);
		} else if (nodeId.equals("362")) {
			loadWindow(this._main.getInventoryPanel(), this.materialRemainWindow , name, icon);
		} else if (nodeId.equals("37")) {
			//loadWindow(this._main.getInventoryPanel(), this.reportWindow , name, icon);
			//Do nothing
		} else if (nodeId.equals("371")) {
			loadWindow(this._main.getInventoryPanel(), this.SubInventoryReportWindow , name, icon);
		} else if (nodeId.equals("372")) {
			loadWindow(this._main.getInventoryPanel(), this.SubOutProductMaterialReportWindow , name, icon);
		} else if (nodeId.equals("373")) {
			loadWindow(this._main.getInventoryPanel(), this.SubInProductMaterialReportWindow , name, icon);
		} else init();
	}
	
	private void prepareTransferWindow(){
		VLayout transferLayout = new TransferLayout(this._main.getCurrentUser());
		this.inProductWindow.addItem(transferLayout);
	}
	
	private void prepareOutProductWindow(){
		VLayout deliveryOrderLayout = new DeliveryOrderLayout(this._main.getCurrentUser());
		this.outProductWindow.addItem(deliveryOrderLayout);
	}
	
	private void prepareInMaterialWindow(){
		VLayout purhaseOrderLayout = new ReceivedPurchaseLayout(this._main.getCurrentUser());
		this.inMaterialWindow.addItem(purhaseOrderLayout);
	}
	
	private void prepareReturnMaterialWindow(){
		VLayout returnLayout = new ReturnLayout(this._main.getCurrentUser());
		this.returnMaterialWindow.addItem(returnLayout);
	}
	
	private void prepareRequestMaterialWindow(){
		VLayout requestLayout = new MaterialRequestLayout(this._main.getCurrentUser());
		this.outMaterialWindow.addItem(requestLayout);
	}
	
	private void prepareProductRemainWindow(){
		VLayout viewLayout = new ViewProductLaylout(this._main.getCurrentUser());
		this.productRemainWindow.addItem(viewLayout);
	}
	
	private void prepareMaterialRemainWindow(){
		VLayout viewLayout = new ViewMaterialLaylout(this._main.getCurrentUser());
		this.materialRemainWindow.addItem(viewLayout);
	}
	
	private void prepareInReportWindow() {
		VLayout report = new ReportReceiveLayout(this._main.getCurrentUser());
		this.SubInProductMaterialReportWindow.addItem(report);
	}
	
	private void prepareOutReportWindow() {
		VLayout report = new ReportRequestLayout(this._main.getCurrentUser());
		this.SubOutProductMaterialReportWindow.addItem(report);
	}
	
	private void prepareInventoryWindow() {
		VLayout report = new ReportInventoryLayout(this._main.getCurrentUser());
		this.SubInventoryReportWindow.addItem(report);
	}
}
