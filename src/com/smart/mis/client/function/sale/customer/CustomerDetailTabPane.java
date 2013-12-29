package com.smart.mis.client.function.sale.customer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smart.mis.client.function.sale.order.SaleOrderDS;
import com.smart.mis.client.function.sale.order.SaleViewWindow;
import com.smart.mis.client.function.security.SecurityService;
import com.smart.mis.client.function.security.SecurityServiceAsync;
import com.smart.mis.client.function.security.permission.PermissionDS;
import com.smart.mis.shared.Country;
import com.smart.mis.shared.FieldFormatter;
import com.smart.mis.shared.FieldVerifier;
import com.smart.mis.shared.security.PermissionProfile;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DataSource;  
import com.smartgwt.client.data.Record;  
import com.smartgwt.client.types.Alignment;  
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;  
import com.smartgwt.client.widgets.form.DynamicForm;  
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
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
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
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
    private DynamicForm editorForm , addressForm; //, normalForm;
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
    	createHistoryGrid();
    	setHeight("70%");
    	
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
        editorForm.setWidth(400);
        editorForm.setHeight(200);
        editorForm.setMargin(5);  
        editorForm.setNumCols(2);  
        editorForm.setCellPadding(2);  
        editorForm.setAutoFocus(false);  
        editorForm.setDataSource(customerDS);  
        editorForm.setUseAllDataSourceFields(false); 
        editorForm.setIsGroup(true);
        editorForm.setGroupTitle("ข้อมูลลูกค้า");
        
        //editorForm
        StaticTextItem cid = new StaticTextItem("cid", "รหัสลูกค้า");
        cid.setRequired(true);
        
		TextItem cus_name = new TextItem("cus_name", "ชื่อลูกค้า");
		TextItem cus_phone = new TextItem("cus_phone", "หมายเลขโทรศัพท์ลูกค้า");
		TextItem contact_name = new TextItem("contact_name", "ชื่อผู้ติดต่อ");
		TextItem contact_phone = new TextItem("contact_phone", "หมายเลขโทรศัพท์ผู้ติดต่อ");
		TextItem contact_email = new TextItem("contact_email", "อีเมลผู้ติดต่อ");

		//TextAreaItem address = new TextAreaItem("address", "ที่อยู่");
		//address.setWidth(300);
		//address.setRowSpan(3);
		TextItem url = new TextItem("url");
		SelectItem cus_type = new SelectItem("cus_type");
		SelectItem bus_type = new SelectItem("bus_type");
		SelectItem cus_group = new SelectItem("cus_group");
		//SelectItem zone = new SelectItem("zone", "โซน");
		
		cus_name.setRequired(true);
		cus_phone.setRequired(true);
		contact_name.setRequired(true);
		cus_type.setRequired(true);
		bus_type.setRequired(true);
		cus_group.setRequired(true);
		cus_name.setHint("*");
		cus_phone.setHint("*");
		contact_name.setHint("*");
		cus_type.setHint("*");
		bus_type.setHint("*");
		cus_group.setHint("*");
		
		//Address Form
		addressForm = new DynamicForm();  
		addressForm.setWidth(300);  
		addressForm.setMargin(5);  
		addressForm.setNumCols(2);  
		addressForm.setCellPadding(2);  
		addressForm.setAutoFocus(false);  
		addressForm.setDataSource(customerDS);  
		addressForm.setUseAllDataSourceFields(false); 
		addressForm.setIsGroup(true);
		addressForm.setGroupTitle("ที่อยู่"); 
		
		TextItem address = new TextItem("address");
		TextItem street = new TextItem("street");
		TextItem city = new TextItem("city");
		TextItem state = new TextItem("state");
		SelectItem country = new SelectItem("country");
		country.setValueMap(Country.getValueMap());
		country.setImageURLPrefix("flags/24/");  
		country.setImageURLSuffix(".png");
		country.setValueIcons(Country.getValueIcons()); 
		IntegerItem postal = new IntegerItem("postal");
		
		address.setRequired(true);
		city.setRequired(true);
		state.setRequired(true);
		country.setRequired(true);
		postal.setRequired(true);
		
		address.setHint("*");
		city.setHint("*");
		state.setHint("*");
		country.setHint("*");
		postal.setHint("*");
		
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
        editorForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
        editorForm.setFields(cid, cus_name, cus_phone, contact_name, contact_phone, contact_email , url, cus_type, bus_type, cus_group);
        //editorForm.setFields(cid, cus_name, cus_phone, contact_name, contact_phone, contact_email , address, type, zone);
        editorForm.setColWidths(200	, 200); 
        
        addressForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
        addressForm.setFields(address, street, city, state, postal,country );
        addressForm.setColWidths(200, 200); 
        
        VLayout information = new VLayout();
        information.setMembersMargin(5);
        information.addMembers(addressForm);
        HLayout editor_control = new HLayout();
        editor_control.addMembers(saveButton, cancelButton);
        information.addMembers(editor_control);
        outlineForm.addMembers(editorForm, information);
        
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
                addressForm.editNewRecord();
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
        		addressForm.editRecord(selectedRecord);
        		
        		historyGrid.fetchData(new Criterion("cid", OperatorId.EQUALS, selectedRecord.getAttributeAsString("cid")));
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
    		addressForm.editRecord(selectedRecord);
    		
    		historyGrid.fetchData(new Criterion("cid", OperatorId.EQUALS, selectedRecord.getAttributeAsString("cid")));
        }
    }
    
    public void saveData(){
   	
    	if (editorForm.validate() && addressForm.validate()) {
    		
    		String cid = (String) editorForm.getValue("cid");
			String cus_name = (String) editorForm.getValue("cus_name");
			String cus_phone = (String) editorForm.getValue("cus_phone");
	    	String contact_name = (String) editorForm.getValue("contact_name");
	    	String contact_phone = (String) editorForm.getValue("contact_phone");
	    	String contact_email = (String) editorForm.getValue("contact_email");
	    	//String address = (String) editorForm.getValue("address");
	    	String url = (String) editorForm.getValue("url");
	    	String cus_type = (String) editorForm.getValue("cus_type");
	    	String bus_type = (String) editorForm.getValue("bus_type");
	    	String cus_group = (String) editorForm.getValue("cus_group");
	    	
    		String address = (String) addressForm.getValue("address");
    		String street = (String) addressForm.getValue("street");
			String city = (String) addressForm.getValue("city");
			String state = (String) addressForm.getValue("state");
	    	String country = (String) addressForm.getValue("country");
	    	Integer postal = (Integer) addressForm.getValue("postal");

	    	//System.out.println(country);
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
								cid,
								cus_name,
								cus_phone,
								contact_name,
								contact_phone,
								contact_email,
								address,
								street,
								city,
								state,
								postal,
								country,
								cus_type,
								bus_type,
								cus_group,
								url,
								Country.getContinient(country)
				    			);
						customerDataSource.updateData(updateRecord);
						SC.say("แก้ไขข้อมูลลูกค้าเรียบร้อยแล้ว");
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
    
    private void createHistoryGrid() {
    	
    	historyGrid = new ListGrid();
    	
    	historyGrid.setAutoFetchData(true);
    	historyGrid.setDataSource(SaleOrderDS.getInstance());
    	historyGrid.setUseAllDataSourceFields(false);
    	historyGrid.setCriteria(new Criterion("cid", OperatorId.EQUALS, ""));
    	
    	ListGridField sale_id = new ListGridField("sale_id" , 90);
		ListGridField status = new ListGridField("status",120);
		ListGridField payment_model = new ListGridField("payment_model", 120);
		ListGridField credit = new ListGridField("credit", 80);
		
		ListGridField total_amount = new ListGridField("total_weight", 150);
		total_amount.setCellFormatter(FieldFormatter.getNumberFormat());
		total_amount.setAlign(Alignment.RIGHT);
		ListGridField netInclusive = new ListGridField("netInclusive", 150);
		netInclusive.setCellFormatter(FieldFormatter.getPriceFormat());
		netInclusive.setAlign(Alignment.RIGHT);
		ListGridField created_date = new ListGridField("created_date","สั่งซื้อวันที่", 100);
		ListGridField created_by = new ListGridField("created_by","รับคำสั่งซื้อโดย");
		
		historyGrid.setFields(sale_id, status,created_date,created_by, payment_model, credit , total_amount, netInclusive);
		
		historyGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {

			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				// TODO Auto-generated method stub
				SaleViewWindow view = new SaleViewWindow();
				view.show(historyGrid.getSelectedRecord(), false, null, 0);
			}});
    }
}
