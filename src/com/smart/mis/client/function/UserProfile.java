package com.smart.mis.client.function;

import com.smart.mis.client.MainPage;
import com.smart.mis.shared.security.User;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.viewer.DetailViewer;
import com.smartgwt.client.widgets.viewer.DetailViewerField;
import com.smartgwt.client.widgets.viewer.DetailViewerRecord;

public class UserProfile {

	private MainPage _main;
	
	public UserProfile(MainPage main){
		this._main = main;
	}
	
	public User getUser(){
		return this._main.getCurrentUser();
	}
	
	public void show(){
		final VLayout userVLayout = new VLayout();
		final HLayout contolHLayout = new HLayout();
		
		userVLayout.setLayoutMargin(10);
		userVLayout.setMembersMargin(5);
		userVLayout.setHeight100();
		userVLayout.setWidth100();
		contolHLayout.setMembersMargin(5);
		contolHLayout.setAlign(Alignment.CENTER);

		final Window winModel = new Window();
		winModel.setTitle("ข้อมูลส่วนตัว");
		//winModel.setAutoSize(true);
		winModel.setHeaderIcon("icons/16/person.png");
		winModel.setWidth(250);
		winModel.setHeight(210);
		winModel.setShowMinimizeButton(false);
		winModel.setIsModal(true);
		winModel.setShowModalMask(true);
		winModel.setCanDragResize(false);
		winModel.setCanDragReposition(false);
		winModel.centerInPage();
			
		//Close Button
		IButton close = new IButton("ปิด");
		close.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				winModel.destroy();
			}
		});
		close.setAlign(Alignment.CENTER);
		//User detail
		DetailViewer userViewer = new DetailViewer();
		userViewer.setWidth100(); 
		userViewer.setHeight100();
		userViewer.setAlign(Alignment.LEFT);
		userViewer.setFields(new DetailViewerField("ชื่อผู้ใช้"), 
				new DetailViewerField("คำนำหน้าชื่อ"),
				new DetailViewerField("ชื่อ"), 
				new DetailViewerField("นามสกุล"), 
				new DetailViewerField("อีเมล"), 
				new DetailViewerField("ตำแหน่ง"));
		
		DetailViewerRecord userRecord = new DetailViewerRecord();
		userRecord.setAttribute("ชื่อผู้ใช้", this._main.getCurrentUser().getUserName());
		userRecord.setAttribute("คำนำหน้าชื่อ", this._main.getCurrentUser().getTitle());
		userRecord.setAttribute("ชื่อ", this._main.getCurrentUser().getFirstName());
		userRecord.setAttribute("นามสกุล", this._main.getCurrentUser().getLastName());
		userRecord.setAttribute("อีเมล", this._main.getCurrentUser().getEmail());
		userRecord.setAttribute("ตำแหน่ง", this._main.getCurrentUser().getPosition());
		//userRecord.setAttribute("สิทธิการใช้งาน", this._main.getCurrentUser().getProfile().getName());
				
		userViewer.setData(new DetailViewerRecord[]{
				userRecord
		});

		contolHLayout.setMembers(close);
		userVLayout.setMembers(userViewer,contolHLayout);
		
		//Popup windows
		winModel.addItem(userVLayout);
		winModel.show();
	}
}
