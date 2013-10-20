package com.smart.mis.client.function.sale.customer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smart.mis.client.function.security.SecurityService;
import com.smart.mis.client.function.security.SecurityServiceAsync;
import com.smart.mis.client.function.security.permission.PermissionDS;
import com.smart.mis.shared.FieldVerifier;
import com.smart.mis.shared.security.PermissionProfile;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;  
import com.smartgwt.client.data.Record;  
import com.smartgwt.client.types.Alignment;  
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;  
import com.smartgwt.client.widgets.form.DynamicForm;  
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.MatchesFieldValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;  
import com.smartgwt.client.widgets.tab.TabSet;  
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;  
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;  
import com.smartgwt.client.widgets.viewer.DetailViewer;  

public class CustomerDetailTabPane extends TabSet {
    private DetailViewer itemViewer;  
    private DynamicForm editorForm; //, normalForm;
    private ListGrid historyGrid;
    private Label editorLabel;  
    private CustomerListGrid customerListGrid; 
    private CustomerDS customerDataSource;
    //private PermissionDS permissionDataSource;
    private HLayout outlineForm;
    private IButton saveButton, cancelButton;
//    private final SecurityServiceAsync securityService = GWT.create(SecurityService.class);
//    private SelectItem profile;
    
    public CustomerDetailTabPane(CustomerDS customerDS , CustomerListGrid customerGrid){
    	this.customerListGrid = customerGrid;
    	this.customerDataSource = customerDS;
    	
    	itemViewer = new DetailViewer();  
        itemViewer.setDataSource(customerDS);  
        itemViewer.setWidth(600);  
        itemViewer.setMargin(5);  
        itemViewer.setEmptyMessage("Select an item to view its details");
        
        editorLabel = new Label();  
        editorLabel.setWidth100();  
        editorLabel.setHeight100();  
        editorLabel.setAlign(Alignment.CENTER);  
        editorLabel.setContents("Select a record to edit its details");  

        outlineForm = new HLayout();
        outlineForm.setWidth100();
        outlineForm.setHeight100();
        outlineForm.setMargin(5);
        
        editorForm = new DynamicForm();  
        editorForm.setWidth(450);  
        editorForm.setMargin(5);  
        editorForm.setNumCols(2);  
        editorForm.setCellPadding(2);  
        editorForm.setAutoFocus(false);  
        editorForm.setDataSource(customerDS);  
        editorForm.setUseAllDataSourceFields(false); 
        editorForm.setIsGroup(true);
        editorForm.setGroupTitle("ข้อมูลลูกค้า");
        
        historyGrid = new ListGrid();
//        normalForm = new DynamicForm(); 
//        normalForm.setAutoFocus(false);
//        normalForm.setCellPadding(2);
//        normalForm.setMargin(5); 
//        normalForm.setNumCols(2); 
//        normalForm.setWidth(270); 
//        normalForm.setDataSource(customerDS);  
//        normalForm.setUseAllDataSourceFields(false); 
//        normalForm.setIsGroup(true);
//        normalForm.setGroupTitle("ข้อมูลทั่วไป");
        
        //editorForm
        StaticTextItem cid = new StaticTextItem("cid", "รหัสลูกค้า");
        cid.setRequired(true);
//        profile = new SelectItem("name", "สิทธิการใช้งาน");
//        //Setup for fetch update data on Data Source
//        profile.setValueField("name");
//        profile.setDisplayField("name");
//        profile.setOptionDataSource(this.permissionDataSource);
//        profile.setFetchMissingValues(true);
//        profile.setAlwaysFetchMissingValues(true);
//        profile.setRequired(true);
//        profile.setPickListCriteria(new Criteria("status", "Active"));
        //End setup
//        CheckboxItem status = new CheckboxItem("status", "สถานะ");
//        PasswordItem pwd = new PasswordItem("pwd", "รหัสผ่าน");
//		CustomValidator cv = new CustomValidator() {
//			@Override
//			protected boolean condition(Object value) {
//				return FieldVerifier.isValidName((String) value);
//			}
//		};
//		pwd.setValidators(cv);
//		PasswordItem npwd = new PasswordItem("npwd", "ยืนยันรหัสผ่าน");
//		MatchesFieldValidator matchesValidator = new MatchesFieldValidator();
//		matchesValidator.setOtherField("pwd");
//		matchesValidator.setErrorMessage("ยืนยันรหัสผ่านไม่ถูกต้อง");
//		npwd.setValidators(matchesValidator);
//		
//		pwd.setRequired(true);
//		npwd.setRequired(true);
//		pwd.setHint("*");
//		npwd.setHint("*");
		
        //normalForm
//		SelectItem title = new SelectItem("title", "คำนำหน้าชื่อ");
//		title.setWidth(75);
		TextItem cus_name = new TextItem("cus_name", "ชื่อลูกค้า");
		TextItem cus_phone = new TextItem("cus_phone", "หมายเลขโทรศัพท์ลูกค้า");
		TextItem contact_name = new TextItem("contact_name", "ชื่อผู้ติดต่อ");
		TextItem contact_phone = new TextItem("contact_phone", "หมายเลขโทรศัพท์ผู้ติดต่อ");
		TextItem contact_email = new TextItem("contact_email", "อีเมลผู้ติดต่อ");

		TextAreaItem address = new TextAreaItem("address", "ที่อยู่");
		address.setWidth(300);
		address.setRowSpan(3);
		
		SelectItem type = new SelectItem("cus_type", "ประเภทลูกค้า");
		
		cus_name.setRequired(true);
		cus_phone.setRequired(true);
		contact_name.setRequired(true);
		address.setRequired(true);
		type.setRequired(true);
		cus_name.setHint("*");
		cus_phone.setHint("*");
		contact_name.setHint("*");
		address.setHint("*");
		type.setHint("*");
		
        saveButton = new IButton("บันทึก");  
        saveButton.setAlign(Alignment.CENTER);  
        saveButton.setMargin(10);
        saveButton.setWidth(150);  
        saveButton.setHeight(50);
        saveButton.setIcon("icons/16/save.png");
        saveButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	SC.confirm("ยืนยันการแก้ไขข้อมูลลูกค้า", "ท่านต้องการแก้ไขข้อมูลลูกค้า " + (String) editorForm.getValue("cid") + " หรือไม่ ?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
			            	saveData();
						}
					}
            		
            	});
            }  
        }); 
        
        cancelButton = new IButton("ยกเลิก");  
        cancelButton.setAlign(Alignment.CENTER);  
        cancelButton.setMargin(10);
        cancelButton.setWidth(150);  
        cancelButton.setHeight(50);
        cancelButton.setIcon("icons/16/close.png");
        cancelButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	SC.confirm("ยกเลิกการแก้ไขข้อมูลลูกค้า", "ท่านต้องการ ยกเลิกการแก้ไขข้อมูลลูกค้า หรือไม่ ?" , new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
							updateDetails();
							selectTab(0); // back to detail tab
						}
					}
            		
            	});
            }  
        });

        saveButton.setDisabled(true);
        cancelButton.setDisabled(true);
        
        cus_name.setWidth(250);
        cus_phone.setWidth(250);
        contact_name.setWidth(250);
        contact_phone.setWidth(250);
        contact_email.setWidth(250);
        editorForm.setFields(cid, cus_name, cus_phone, contact_name, contact_phone, contact_email , address, type);
        editorForm.setColWidths(200	, 300); 
        VLayout editor_control = new VLayout();
        editor_control.addMembers(saveButton, cancelButton);
        outlineForm.addMembers(editorForm, editor_control);
        
        Tab viewTab = new Tab("ข้อมูลลูกค้า");  
        viewTab.setIcon("icons/16/application_form.png");  
        viewTab.setWidth(70);  
        viewTab.setPane(itemViewer);
  
        Tab historyTab = new Tab("ประวัติการจัดซื้อ");
        historyTab.setIcon("icons/16/application_form.png");  
        historyTab.setWidth(70);
        historyTab.setPane(historyGrid); 
        
        Tab editTab = new Tab("แก้ไข");  
        editTab.setIcon("icons/16/icon_edit.png");  
        editTab.setWidth(70);  
        editTab.setPane(outlineForm);
        
        setTabs(viewTab, historyTab, editTab);  
        
        addTabSelectedHandler(new TabSelectedHandler() {  
            public void onTabSelected(TabSelectedEvent event) {  
                updateDetails();  
            }  
        });
    }
    
    public void clearDetails(Record selectedRecord) {  
        int selectedTab = getSelectedTabNumber();  
        if (selectedTab == 0) {  
            //view tab : show empty message  
            itemViewer.setData((Record[]) null);  
        } else {  
            // edit tab : show new record editor, or empty message  
            if (selectedRecord != null) {  
                updateTab(1, outlineForm);
                editorForm.editNewRecord();
            } else {  
                updateTab(1, editorLabel);  
            }  
        }  
    }  
  
    public void updateDetails() {  
        Record selectedRecord  = customerListGrid.getSelectedRecord();  
        int selectedTab = getSelectedTabNumber();  
        if (selectedTab == 0) {  
            //view tab : show empty message  
            itemViewer.setData(new Record[]{selectedRecord});
        } else {  
            // edit tab : show record editor  
        	if (selectedRecord != null) {
        		saveButton.setDisabled(false);
        		cancelButton.setDisabled(false);
//        		profile.invalidateDisplayValueCache();
        		editorForm.editRecord(selectedRecord);
        	}
        }  
    }  
    
    public void updateDetails(Record selectedRecord) {
    	int selectedTab = getSelectedTabNumber();  
        if (selectedTab == 0) {  
            //view tab : show empty message  
            itemViewer.setData(new Record[]{selectedRecord});  
        } else { 
        	saveButton.setDisabled(false);
        	cancelButton.setDisabled(false);
//        	profile.invalidateDisplayValueCache();
    		editorForm.editRecord(selectedRecord);
        }
    }
    
    public void saveData(){
   	
    	if (editorForm.validate()) {
    		
    		String cid = (String) editorForm.getValue("cid");
			String cus_name = (String) editorForm.getValue("cus_name");
			String cus_phone = (String) editorForm.getValue("cus_phone");
	    	String contact_name = (String) editorForm.getValue("contact_name");
	    	String contact_phone = (String) editorForm.getValue("contact_phone");
	    	String contact_email = (String) editorForm.getValue("contact_email");
	    	String address = (String) editorForm.getValue("address");
	    	String type = (String) editorForm.getValue("cus_type");
	    	
//	    	User updatedUser = new User(uname, pwd, fname, lname, email, position, title, status);
//	    	securityService.updateUserOnServer(updatedUser, pname, this.user, new AsyncCallback<Boolean>() {
//				@Override
//				public void onFailure(Throwable caught) {
//					SC.warn("Updating permission Fails - please contact administrator");
//				}
//				@Override
//				public void onSuccess(Boolean result) {
//					if (result)
//					{
//						//System.out.println("*** Update result => " + result);
						Record updateRecord = CustomerData.createRecord(
								(String) editorForm.getValue("cid"),
				    			(String) editorForm.getValue("cus_name"),
				    			(String) editorForm.getValue("cus_phone"),
				    			(String) editorForm.getValue("contact_name"),
				    	    	(String) editorForm.getValue("contact_phone"),
				    	    	(String) editorForm.getValue("contact_email"),
				    	    	(String) editorForm.getValue("address"),
				    	    	(String) editorForm.getValue("cus_type")
				    			);
						customerDataSource.updateData(updateRecord);
						SC.warn("แก้ไขข้อมูลลูกค้าเรียบร้อยแล้ว");
//					} else {
//						SC.warn("Updating user Fails - please contact administrator");
//					}
//				}
//			});
    	} else {
    		SC.warn("ข้อมูลลูกค้าไม่ถูกต้อง");
    	}
    }
    
    public void onRefresh() {
    	customerDataSource.refreshData();
    	//permissionDataSource.refreshData();
    	customerListGrid.invalidateCache();
//    	profile.invalidateDisplayValueCache();
    }
}
