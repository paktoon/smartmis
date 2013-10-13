package com.smart.mis.client.function;

import com.smart.mis.client.MainPage;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public abstract class FunctionPanel {

	final protected MainPage _main;
	final public String _funcName;
	final protected int _type;
	
	protected FunctionPanel(MainPage main, String name, int type){
		this._main = main;
		this._funcName = name;
		this._type = type;
	}
	
	public abstract void init();
	
	public abstract void load(String nodeId, String name, String icon);
	
	public int getType(){
		return this._type;
	}
	
	protected Label getInitFunctionLabel(){
		Label test = new Label(this._funcName);
		test.setAlign(Alignment.CENTER);
		test.setWrap(false);
		test.setStyleName("initFunctionLabel");
		return test;
	}
	
	protected FunctionWindow createFuncWindow(String name, String icon){
		final FunctionWindow funcWindows = new FunctionWindow(this._funcName + " > " + name);
		funcWindows.setHeaderIcon(icon);
        funcWindows.addCloseClickHandler(new CloseClickHandler() {
            public void onCloseClick(CloseClickEvent event) {
            	init();
            }
        });
        return funcWindows;
	}
	
	protected FunctionWindow createFuncWindow(String name){
		final FunctionWindow funcWindows = new FunctionWindow(this._funcName + " > " + name);
        funcWindows.addCloseClickHandler(new CloseClickHandler() {
            public void onCloseClick(CloseClickEvent event) {
            	init();
            }
        });
        return funcWindows;
	}
	
	protected FunctionWindow createFuncWindow(){
		final FunctionWindow funcWindows = new FunctionWindow();
        funcWindows.addCloseClickHandler(new CloseClickHandler() {
            public void onCloseClick(CloseClickEvent event) {
            	init();
            }
        });
        return funcWindows;
	}
	
	protected void loadWindow (VLayout panel, FunctionWindow window, String name, String icon) {
		window.setTitle(this._funcName + " > " + name);
		window.setHeaderIcon(icon);
		panel.setMembers(window);
	}
}
