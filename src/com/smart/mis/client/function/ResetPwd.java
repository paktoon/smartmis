package com.smart.mis.client.function;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smart.mis.client.MainPage;
import com.smart.mis.client.function.security.SecurityService;
import com.smart.mis.client.function.security.SecurityServiceAsync;
import com.smart.mis.shared.FieldVerifier;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.validator.MatchesFieldValidator;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ResetPwd {

	private MainPage _main;
	private final SecurityServiceAsync securityService = GWT.create(SecurityService.class);
	
	public ResetPwd(MainPage main){
		this._main = main;
	}
	
	public User getUser(){
		return this._main.getCurrentUser();
	}
	
	public void show(){
		final VLayout resetVLayout = new VLayout();
		final HLayout contolHLayout = new HLayout();
		final HLayout resetPwdHLayout = new HLayout();
		
		resetVLayout.setLayoutMargin(10);
		resetVLayout.setMembersMargin(5);
		contolHLayout.setMembersMargin(5);
		contolHLayout.setAlign(Alignment.CENTER);
		resetPwdHLayout.setAutoWidth();
		resetPwdHLayout.setAutoHeight();
		
		final Window winModel = new Window();
		winModel.setTitle("เปลี่ยนแปลงรหัสผ่าน");
		//winModel.setAutoSize(true);
		winModel.setHeaderIcon("icons/256/key-icon.png");
		winModel.setWidth(320);
		winModel.setHeight(180);
		winModel.setShowMinimizeButton(false);
		winModel.setIsModal(true);
		winModel.setShowModalMask(true);
		winModel.setCanDragResize(false);
		winModel.setCanDragReposition(false);
		winModel.centerInPage();
		
		//Reset Password
		final DynamicForm resetForm = new DynamicForm();
		resetForm.setWidth(270);
		
		//check matching with Old password
		final PasswordItem oldPassword = new PasswordItem();  
		oldPassword.setName("oldPassword");
		oldPassword.setTitle("รหัสผ่านเดิม");  
		oldPassword.setRequired(true);
		oldPassword.setHint("*");
		RegExpValidator regExpOldValidator = new RegExpValidator();
		regExpOldValidator.setExpression(this._main.getCurrentUser().getPassWord()); 
		regExpOldValidator.setErrorMessage("รหัสผ่านเดิมไม่ถูกต้อง");
		oldPassword.setValidators(regExpOldValidator);
		
		//At least 4 char
		final PasswordItem newPassword = new PasswordItem(); 
		newPassword.setName("newPassword");
		newPassword.setTitle("รหัสผ่านใหม่");  
		newPassword.setRequired(true);
		newPassword.setHint("*");
		CustomValidator cv = new CustomValidator() {
			@Override
			protected boolean condition(Object value) {
				return FieldVerifier.isValidName((String) value);
			}
		};
		newPassword.setValidators(cv);
		
		//Match with new pass
		final PasswordItem confirmPassword = new PasswordItem();  
		confirmPassword.setName("confirmPassword");
		confirmPassword.setTitle("ยืนยันรหัสผ่าน");  
		confirmPassword.setRequired(true);
		confirmPassword.setHint("*");
		MatchesFieldValidator matchesValidator = new MatchesFieldValidator();
		matchesValidator.setOtherField("newPassword");  
        matchesValidator.setErrorMessage("รหัสผ่านใหม่ไม่ถูกต้อง");
        confirmPassword.setValidators(matchesValidator);
        
        resetForm.setRequiredMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
		resetForm.setFields(new FormItem[] {oldPassword, newPassword, confirmPassword});

		IButton confirmButton = new IButton("ยืนยัน");
		confirmButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	if (resetForm.validate()) {
	            	SC.confirm("ยืนยันการเปลี่ยนรหัสผ่าน", "ท่านต้องการเปลี่ยนแปลงรหัสผ่านหรือไม่?", new BooleanCallback() {
						@Override
						public void execute(Boolean value) {
							if (value) {
								final String resetPassword = newPassword.getValueAsString();
								securityService.resetPassword(_main.getCurrentUser().getUserName(), resetPassword, new AsyncCallback<Boolean>() {

									@Override
									public void onFailure(Throwable caught) {
										SC.say("ตั้งค่ารหัสผ่านใหม่ล้มเหลว");
									}

									@Override
									public void onSuccess(Boolean result) {
										_main.getCurrentUser().setPassword(resetPassword);
										SC.say("ตั้งค่ารหัสผ่านใหม่เสร็จสิ้น");
										winModel.destroy();
									}
									
								});
							}
							resetForm.clearValue("oldPassword");
							resetForm.clearValue("newPassword");
							resetForm.clearValue("confirmPassword");
						}
	            		
	            	});
            	}
            }  
        });  
		IButton cancelButton = new IButton("ยกเลิก");
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				winModel.destroy();
			}
		});
		
		//Layout
		resetPwdHLayout.setMembers(resetForm);
		contolHLayout.setMembers(confirmButton,cancelButton);
		resetVLayout.setMembers(resetPwdHLayout,contolHLayout);
		
		//Popup windows
		winModel.addItem(resetVLayout);
		winModel.show();
	}


}
