package com.smart.mis.client.function.inventory;

import com.smart.mis.client.MainPage;
import com.smart.mis.client.function.FunctionPanel;
import com.smart.mis.client.function.FunctionWindow;
import com.smartgwt.client.types.Alignment;

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
	private final FunctionWindow SubInOutProductReportWindow;
	private final FunctionWindow SubInOutMaterialReportWindow;
	
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
		SubInOutProductReportWindow = createFuncWindow();
		SubInOutMaterialReportWindow = createFuncWindow();
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
			loadWindow(this._main.getInventoryPanel(), this.allRamainWindow , name, icon);
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
			loadWindow(this._main.getInventoryPanel(), this.SubInOutProductReportWindow , name, icon);
		} else if (nodeId.equals("373")) {
			loadWindow(this._main.getInventoryPanel(), this.SubInOutMaterialReportWindow , name, icon);
		} else init();
	}
}
