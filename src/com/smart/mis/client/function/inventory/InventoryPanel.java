package com.smart.mis.client.function.inventory;

import com.smart.mis.client.MainPage;
import com.smart.mis.client.function.FunctionPanel;
import com.smartgwt.client.types.Alignment;

public class InventoryPanel extends FunctionPanel{
	
	public InventoryPanel(MainPage main){
		super(main, "ระบบจัดการคลังสินค้าและวัตถุดิบ", 3);
	}

	@Override
	public void init() {
		this._main.getInventoryPanel().setAlign(Alignment.CENTER);
		this._main.getInventoryPanel().setMembers(getInitFunctionLabel());
	}

	@Override
	public void load(String nodeId, String name, String icon) {
		this._main.getInventoryPanel().setMembers(createFuncWindow(name, icon));
	}
}
