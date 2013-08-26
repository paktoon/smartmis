package com.smart.mis.client.function.security;

import com.smart.mis.client.MainPage;
import com.smart.mis.client.function.FunctionPanel;
import com.smart.mis.client.function.FunctionStack;
import com.smart.mis.client.function.FunctionWindow;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.events.ClickEvent;  
import com.smartgwt.client.widgets.events.ClickHandler; 

public class SecurityPanel extends FunctionPanel{

	private final FunctionWindow permissionWindow;
	private final FunctionWindow userWindow;
	private final PermissionListGrid permissionGrid = new PermissionListGrid(); 
	private final UserListGrid userGrid = new UserListGrid(); 

	public SecurityPanel(MainPage main){
		super(main, "ระบบรักษาความปลอดภัย", 7);
		permissionWindow = createFuncWindow();
		preparePermissionWindow();
		userWindow = createFuncWindow();
		prepareUserWindow();
	}

	@Override
	public void init() {
		this._main.getSecurityPanel().setAlign(Alignment.CENTER);
		this._main.getSecurityPanel().setMembers(getInitFunctionLabel());
	}

	@Override
	public void load(String nodeId, String name, String icon) {
		//Add tab to window 
		//End tab
		//this._main.getSecurityPanel().setMembers(funcWindows);
		if (nodeId.equals("71")) {
			loadPermissionWindow(name, icon);
		} else if (nodeId.equals("72")) {
			loadUserWindow(name, icon);
		} else init();
	}
	
	private void loadPermissionWindow(String name, String icon) {
		this.permissionWindow.setTitle(this._funcName + " > " + name);
		this.permissionWindow.setHeaderIcon(icon);
		this._main.getSecurityPanel().setMembers(this.permissionWindow);
		//PermissionDS.getInstance().fetchData();
	}
	
	private void loadUserWindow(String name, String icon) {
		this.userWindow.setTitle(this._funcName + " > " + name);
		this.userWindow.setHeaderIcon(icon);
		this._main.getSecurityPanel().setMembers(this.userWindow);
		//UserDS.getInstance().fetchData();
	}
	
	private void preparePermissionWindow(){
		FunctionStack functionStack = new FunctionStack();
//		SectionStackSection findSection = new SectionStackSection("ค้นหาสิทธิการใช้งาน");
//		//TBD findSection.setItems(searchForm);
//		findSection.setExpanded(true);
		
        SectionStackSection itemList = new SectionStackSection("รายการสิทธิการใช้งาน");  
        itemList.setItems(permissionGrid);
        itemList.setExpanded(true);
        
        SectionStackSection detailView = new SectionStackSection("จัดการข้อมูลสิทธิการใช้งาน");  
        PermissionDetailTabPane permissionTabPane = new PermissionDetailTabPane(PermissionDS.getInstance(), permissionGrid);
        detailView.setItems(permissionTabPane);
        detailView.setExpanded(true);
        permissionGrid.addUpdateDetailHandler(permissionTabPane);
        
        final PermissionAdd addFunc = new PermissionAdd(PermissionDS.getInstance(), permissionGrid, permissionTabPane);
        ToolStripButton addButton = new ToolStripButton();  
        addButton.setHeight(18);  
        addButton.setWidth(120);
        addButton.setIcon("[SKIN]actions/add.png");  
        addButton.setTitle("เพิ่มสิทธิการใช้งาน");  
        addButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	addFunc.show();
            }  
        });  
        
        itemList.setControls(addButton);
        
        functionStack.setSections(itemList, detailView);
        VLayout functionLayout = new VLayout();
        functionLayout.setWidth100();
        functionLayout.setHeight100();
        functionLayout.setMembers(functionStack);
        this.permissionWindow.addItem(functionLayout);
	}
	
	private void prepareUserWindow(){
		FunctionStack functionStack = new FunctionStack();
//		SectionStackSection findSection = new SectionStackSection("ค้นหาข้อมูลผู้ใช้ระบบ");
//		//TBD findSection.setItems(searchForm);
//		findSection.setExpanded(true);
		
        SectionStackSection itemList = new SectionStackSection("รายการข้อมูลผู้ใช้ระบบ"); 
        itemList.setItems(userGrid);
        itemList.setExpanded(true);
        itemList.setExpanded(true);
		
        SectionStackSection detailView = new SectionStackSection("จัดการข้อมูลผู้ใช้ระบบ");  
        UserDetailTabPane userTabPane = new UserDetailTabPane(UserDS.getInstance(), userGrid);
        detailView.setItems(userTabPane);
        detailView.setExpanded(true);
        userGrid.addUpdateDetailHandler(userTabPane);
        
        final UserAdd addFunc = new UserAdd(UserDS.getInstance(), userGrid, userTabPane);
        ToolStripButton addButton = new ToolStripButton();  
        addButton.setHeight(18);  
        addButton.setWidth(120);
        addButton.setIcon("[SKIN]actions/add.png");  
        addButton.setTitle("เพิ่มผู้ใช้ระบบ");  
        addButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	addFunc.show();
            }  
        });  
        
        itemList.setControls(addButton);
        
        functionStack.setSections(itemList, detailView);
        VLayout functionLayout = new VLayout();
        functionLayout.setWidth100();
        functionLayout.setHeight100();
        functionLayout.setMembers(functionStack);
        this.userWindow.addItem(functionStack);
	}
}
