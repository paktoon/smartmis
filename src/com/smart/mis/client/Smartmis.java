package com.smart.mis.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smart.mis.client.function.security.SecurityService;
import com.smart.mis.client.function.security.SecurityServiceAsync;
import com.smart.mis.shared.FieldVerifier;
import com.smart.mis.shared.image.ImageUpload;
import com.smart.mis.shared.security.Function;
import com.smart.mis.shared.security.PermissionProfile;
import com.smart.mis.shared.security.Role;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Dialog;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.layout.VLayout; 

public class Smartmis implements EntryPoint {

	private DynamicForm loginForm;
	private TextItem userName;
	private PasswordItem passWord;
	private IButton loginButton;
	private Label errorLabel;
	private VLayout vLoginLayout;
	private int _retry = 3;
	private boolean isLogin = false;
	User _currentUser;
	PermissionProfile _permissionProfile;
	
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";
	
	private final SecurityServiceAsync securityService = GWT.create(SecurityService.class);
	
	private void loginToServer() {
						
			if (!FieldVerifier.isValidName(userName.getValueAsString()) || !FieldVerifier.isValidName(passWord.getValueAsString())) {
				errorLabel.setContents("ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง");
				errorLabel.setWrap(false);
	          	errorLabel.show();
	          	return;
			}
			 
	        SC.clearPrompt();
			Dialog dialogProperties = new Dialog();
			dialogProperties.setHeight(100);
			dialogProperties.setWidth(250);
			SC.showPrompt("กำลังตรวจสอบชื่อผู้ใช้และรหัสผ่าน", "<img src=\"images/ajax-loader.gif\"> Loading...", dialogProperties);
			
			securityService.loginToServer(userName.getValueAsString(), passWord.getValueAsString() , new AsyncCallback<User>() {
            
				public void onFailure(Throwable caught) {
				  //Retry
		          if (_retry != 0){
		          	loginToServer();
		            _retry--;
		          }
				  
		          SC.clearPrompt();
				  
				  String errorMessage = caught.getMessage();
				  if (errorMessage.equalsIgnoreCase("LOGIN FAILED"))
					  errorLabel.setContents("ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง");
				  else errorLabel.setContents(SERVER_ERROR + _retry);
				  
	          	  errorLabel.setWrap(false);
	          	  errorLabel.show();
	          	  _currentUser = null;
	          	  isLogin = false;
	            }
	
				@Override
				public void onSuccess(User result) {
					SC.clearPrompt();
					errorLabel.hide();
					vLoginLayout.destroy();
					
					isLogin = true;
					_currentUser = result;
					_permissionProfile = result.getProfile();
					// Login pass - load main page follow permission profile
					loadMainPage();
			} });
    }
	
	public void loadMainPage(){
		MainPage _main = new MainPage(this);
		_main.loadPage();
	}

	public void loadLoginPage() {
		
		vLoginLayout  = new VLayout();
		
		vLoginLayout.setWidth100();  
		vLoginLayout.setHeight100();  
		vLoginLayout.setDefaultLayoutAlign(Alignment.CENTER);
		//vLoginLayout.setBackgroundImage("background.jpg");
		
		VLayout loginWindow = new VLayout();
		loginWindow.setGroupTitle("ยินดีต้อนรับ");
		loginWindow.setIsGroup(true);
		loginWindow.setWidth(210);
		loginWindow.setHeight(100);
		loginWindow.setPadding(10);
		//loginWindow.setBorder("3px solid blue");
		loginWindow.setDefaultLayoutAlign(Alignment.CENTER);
				
		loginForm = new DynamicForm();
		userName = new TextItem("ชื่อผู้ใช้");
		userName.setTitleAlign(Alignment.LEFT);
		passWord = new PasswordItem("รหัสผ่าน");
		passWord.setTitleAlign(Alignment.LEFT);
		
		errorLabel = new Label();
		errorLabel.setWidth(200);
		errorLabel.setHeight(10);
		errorLabel.setAlign(Alignment.CENTER);
		errorLabel.setStyleName("serverResponseLabelError");
		
		loginForm.setWidth(200);
		loginButton = new IButton("เข้าสู่ระบบ");		
		
		loginForm.setFields(userName, passWord);
		loginWindow.addMembers(loginForm, loginButton, errorLabel);
		
		Canvas[] loginPage = {
			new Canvas() {{  
	            setHeight("40%");  
	            setWidth("40%");  
	        }}
			//,loginForm
			//,loginButton
			//,errorLabel
			,loginWindow
			,new Canvas() {{  
	            setHeight("*");  
	            setWidth("*");  
	        }}
        }; 
		
		vLoginLayout.addMembers(loginPage, 0);
		
		vLoginLayout.draw();
		userName.selectValue();
		errorLabel.hide();
		
	    // Create a Login handler
	    class LoginHandler implements ClickHandler, KeyPressHandler {

		@Override
		public void onClick(ClickEvent event) {
			_retry = 3;
			loginToServer();
		}

		@Override
		public void onKeyPress(KeyPressEvent event) {
			if (EventHandler.getKey().equalsIgnoreCase("Enter")){
				_retry = 3;
				loginToServer();}
			}
		}

	    // Add a handler to send the name to the server
	    LoginHandler loginHandler = new LoginHandler();
	    loginButton.addClickHandler(loginHandler);
	    userName.addKeyPressHandler(loginHandler);
	    passWord.addKeyPressHandler(loginHandler);
	}
	
	public void onModuleLoad() {
		        	 
		//For test only
		AsyncCallback<String> callback = new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(String result) {
//				if (result)
//					System.out.println("Create successful");				
			}
			
		};
		
		//Need to be revise
		securityService.createPermOnServer(new PermissionProfile("ADMIN", Function.ALL, Role.ADMIN, true), "admin", callback);
		securityService.createPermOnServer(new PermissionProfile("SALE", Function.SALE, Role.STAFF, true), "admin",callback);
		securityService.createPermOnServer(new PermissionProfile("PRODUCTION", Function.PRODUCTION, Role.STAFF, true), "admin",callback);
		securityService.createPermOnServer(new PermissionProfile("INVENTORY", Function.INVENTORY, Role.STAFF, true), "admin",callback);
		securityService.createPermOnServer(new PermissionProfile("PURCHASING", Function.PURCHASING, Role.STAFF, true), "admin",callback);
		securityService.createPermOnServer(new PermissionProfile("FINANCIAL", Function.FINANCIAL, Role.STAFF, true), "admin",callback);
		securityService.createPermOnServer(new PermissionProfile("OWNER", Function.ALL, Role.OWNER, true), "admin",callback);
		
		securityService.createUserOnServer(new User("admin", "test", "ภักดิ์ทูล" , "ใจทอง", "admin@projectadmin.com" , "administrator" , "นาย", true), "ADMIN", "admin", callback);
		securityService.createUserOnServer(new User("sale", "test", "สมบัติ" , "ยอดขาย", "somsee@richsilver.com" , "sale person", "นาง", true),"SALE", "admin",callback);
		securityService.createUserOnServer(new User("sale1", "test", "สมศรี" , "ยอดขาย", "somsee@richsilver.com" , "sale person", "นาง", true),"SALE", "admin",callback);
		securityService.createUserOnServer(new User("sale2", "test", "สมใจ" , "ยอดขาย", "somsee@richsilver.com" , "sale person", "นาง", true),"SALE", "admin",callback);
		securityService.createUserOnServer(new User("sale3", "test", "สมมุติ" , "ยอดขาย", "somsee@richsilver.com" , "sale person", "นาง", true),"SALE", "admin",callback);
		securityService.createUserOnServer(new User("production", "test", "สมใจ" , "ผลิตเก่ง", "somjai@richsilver.com" , "production staff", "นางสาว", true),"PRODUCTION", "admin",callback);
		securityService.createUserOnServer(new User("inventory", "test", "สมศักดิ์" , "ดูแลคลัง", "somsak@richsilver.com" , "inventory staff", "นาย", true),"INVENTORY", "admin",callback);
		securityService.createUserOnServer(new User("purchasing", "test", "สมหมาย" , "ซื้อของ", "sommai@richsilver.com" , "purchasing staff", "นาย", true),"PURCHASING", "admin",callback);
		securityService.createUserOnServer(new User("financial", "test", "สมปรีดี" , "เงินสด", "sompredee@richsilver.com" , "accouting staff", "นาง", true),"FINANCIAL", "admin",callback);
		securityService.createUserOnServer(new User("owner", "test", "ดาว" , "เจ้าของ", "dow@richsilver.com" , "business owner", "นางสาว", true),"OWNER", "admin",callback);

	    if (!isLogin) {
	    	// To do should implement cookies
	    	loadLoginPage();
	    }
	    //todo else
	}
}
