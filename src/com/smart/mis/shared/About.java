package com.smart.mis.shared;

import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Dialog;
import com.smartgwt.client.widgets.events.ButtonClickEvent;
import com.smartgwt.client.widgets.events.ButtonClickHandler;

public class About {
	
	public static void show() {
		  final Dialog dialog = new Dialog();
		  dialog.setTitle("เกี่ยวกับ ระบบสารสนเทศเพื่อการจัดการธุรกิจผลิตและจำหน่ายเครื่องประดับเงิน");
		  dialog.setMessage("To do");
		  dialog.setIcon("icons/logo.png");
		  dialog.setButtons(new Button("ปิด"));
		  dialog.setShowMinimizeButton(false);
		  dialog.setIsModal(true);
		  dialog.centerInPage();
		  //dialog.setShowModalMask(true);
		  dialog.addButtonClickHandler(new ButtonClickHandler() {
			@Override
			public void onButtonClick(ButtonClickEvent event) {
				dialog.hide();
			}
		  });
		  dialog.show();
	}
}
