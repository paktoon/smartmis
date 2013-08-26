package com.smart.mis.client.function;

import com.smartgwt.client.widgets.Window;

public class FunctionWindow extends Window {
	
	public FunctionWindow(String name){
		setWidth100();
        setHeight100();
        setTitle(name);
        setShowMinimizeButton(false);
        setCanDragResize(false);
        setCanDragReposition(false);
	}
	
	public FunctionWindow(){
		setWidth100();
        setHeight100();
        setShowMinimizeButton(false);
        setCanDragResize(false);
        setCanDragReposition(false);
	}
}
