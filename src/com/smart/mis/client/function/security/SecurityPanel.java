package com.smart.mis.client.function.security;

import java.util.LinkedHashMap;

import com.smart.mis.client.MainPage;
import com.smart.mis.client.function.FunctionPanel;
import com.smart.mis.client.function.FunctionStack;
import com.smart.mis.client.function.FunctionWindow;
import com.smart.mis.client.function.security.permission.PermissionAdd;
import com.smart.mis.client.function.security.permission.PermissionDS;
import com.smart.mis.client.function.security.permission.PermissionDetailTabPane;
import com.smart.mis.client.function.security.permission.PermissionListGrid;
import com.smart.mis.client.function.security.user.UserAdd;
import com.smart.mis.client.function.security.user.UserDS;
import com.smart.mis.client.function.security.user.UserDetailTabPane;
import com.smart.mis.client.function.security.user.UserListGrid;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.events.ClickEvent;  
import com.smartgwt.client.widgets.events.ClickHandler; 
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.events.ItemChangedHandler;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

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
		if (nodeId.equals("71")) {
			loadWindow(this._main.getSecurityPanel(), this.permissionWindow , name, icon);
		} else if (nodeId.equals("72")) {
			loadWindow(this._main.getSecurityPanel(), this.userWindow , name, icon);
		} else init();
	}
	
	private void preparePermissionWindow(){
		final FunctionStack functionStack = new FunctionStack();
		
        SectionStackSection itemList = new SectionStackSection("รายการสิทธิการใช้งาน");  
        itemList.setItems(permissionGrid);
        itemList.setExpanded(true);
        
        SectionStackSection detailView = new SectionStackSection("จัดการข้อมูลสิทธิการใช้งาน");  
        final PermissionDetailTabPane permissionTabPane = new PermissionDetailTabPane(PermissionDS.getInstance(), permissionGrid, this._main.getCurrentUser().getUserName());
        detailView.setItems(permissionTabPane);
        detailView.setExpanded(true);
        permissionGrid.addUpdateDetailHandler(permissionTabPane);
        
        final PermissionAdd addFunc = new PermissionAdd(PermissionDS.getInstance(), permissionGrid, permissionTabPane, this._main.getCurrentUser().getUserName());
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
        
        ToolStripButton refreshButton = new ToolStripButton();  
        refreshButton.setHeight(18);  
        refreshButton.setWidth(120);
        refreshButton.setIcon("[SKIN]actions/refresh.png");  
        refreshButton.setTitle("รีเฟรช");  
        refreshButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	permissionTabPane.onRefresh();
            }  
        });
        
        //Search section
        String[] status = {"Active", "Inactive"};
	    final DynamicForm form = new DynamicForm();
	    form.setWidth(200);
	    form.setNumCols(4);
	    form.setDataSource(PermissionDS.getInstance());
        TextItem pid = new TextItem("pid", "รหัส");
        pid.setWidth(80);
        pid.setOperator(OperatorId.REGEXP);
        SelectItem filterItem = new SelectItem("status","สถานะ");
        filterItem.setWidth(80); 
        filterItem.setAllowEmptyValue(true);
        LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
        for (String item : status) {
        	valueMap.put(item, item);
        }
        filterItem.setValueMap(valueMap);
	    form.setItems(pid, filterItem);
        form.addItemChangedHandler(new ItemChangedHandler() {  
            public void onItemChanged(ItemChangedEvent event) {  
            	permissionGrid.fetchData(form.getValuesAsCriteria());  
            }  
        });
        //end form
        
        itemList.setControls(form, addButton, refreshButton);
       
        functionStack.setSections(itemList, detailView);
        
        VLayout functionLayout = new VLayout();
        functionLayout.setWidth100();
        functionLayout.setHeight100();
        functionLayout.setMembers(functionStack);
        this.permissionWindow.addItem(functionLayout);
	}
	
	private void prepareUserWindow(){
		FunctionStack functionStack = new FunctionStack();
		
        SectionStackSection itemList = new SectionStackSection("รายการข้อมูลผู้ใช้ระบบ"); 
        itemList.setItems(userGrid);
        itemList.setExpanded(true);
		
        SectionStackSection detailView = new SectionStackSection("จัดการข้อมูลผู้ใช้ระบบ");  
        final UserDetailTabPane userTabPane = new UserDetailTabPane(UserDS.getInstance(), userGrid, this._main.getCurrentUser().getUserName());
        detailView.setItems(userTabPane);
        detailView.setExpanded(true);
        userGrid.addUpdateDetailHandler(userTabPane);
        
        final UserAdd addFunc = new UserAdd(UserDS.getInstance(), userGrid, userTabPane, this._main.getCurrentUser().getUserName());
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
        
        ToolStripButton refreshButton = new ToolStripButton();  
        refreshButton.setHeight(18);  
        refreshButton.setWidth(120);
        refreshButton.setIcon("[SKIN]actions/refresh.png");  
        refreshButton.setTitle("รีเฟรช");  
        refreshButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	userTabPane.onRefresh();
            }  
        });
        
      //Search section
	    final DynamicForm form = new DynamicForm();
	    form.setWidth(270);
	    form.setNumCols(4);
	    form.setDataSource(UserDS.getInstance());
        TextItem uid = new TextItem("uid", "รหัส");
        TextItem name = new TextItem("uname", "ชื่อผู้ใช้");
        uid.setWidth(80);
        uid.setHeight(19);
        uid.setOperator(OperatorId.REGEXP);
        name.setWidth(80);
        name.setHeight(19);
        name.setOperator(OperatorId.REGEXP);
	    form.setItems(uid, name);
        form.addItemChangedHandler(new ItemChangedHandler() {  
            public void onItemChanged(ItemChangedEvent event) {  
            	userGrid.fetchData(form.getValuesAsCriteria());  
            }  
        });
        //end form
        
        itemList.setControls(form, addButton, refreshButton);
        
        functionStack.setSections(itemList, detailView);
        VLayout functionLayout = new VLayout();
        functionLayout.setWidth100();
        functionLayout.setHeight100();
        functionLayout.setMembers(functionStack);
        this.userWindow.addItem(functionStack);
	}
}
