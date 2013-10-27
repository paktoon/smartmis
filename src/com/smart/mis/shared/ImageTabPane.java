package com.smart.mis.shared;

import com.smart.mis.shared.image.ImageUpload;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.tab.TabSet;

public class ImageTabPane extends TabSet {

	public String currentViewImgUrl = "/images/icons/photoNotFound.png";
	public String currentEditImgUrl = "/images/icons/photoNotFound.png";
	
	protected Img viewProductImage, editProductImage;
	
	protected ImageUpload upload;
	
	public void changeEditImg() {
		if (this.editProductImage != null) {
			this.editProductImage.setSrc(this.currentEditImgUrl);
		}
	}
	
	public void changeViewImg() {
		if (this.viewProductImage != null) {
			this.viewProductImage.setSrc(this.currentViewImgUrl);
		}
	}
	
	public void changeEditImg(String url) {
		if (this.editProductImage != null) {
			this.currentEditImgUrl = url;
			this.editProductImage.setSrc(this.currentEditImgUrl);
		}
	}
	
	public void changeViewImg(String url) {
		if (this.viewProductImage != null) {
			this.currentViewImgUrl = url;
			this.viewProductImage.setSrc(this.currentViewImgUrl);
		}
	}
	
	public void resetUrl() {
		this.currentViewImgUrl = "/images/icons/photoNotFound.png";
		this.currentEditImgUrl = "/images/icons/photoNotFound.png";
	}
}
