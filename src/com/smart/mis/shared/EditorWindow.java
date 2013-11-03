package com.smart.mis.shared;

import com.smart.mis.shared.security.User;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public abstract class EditorWindow {
	
	public abstract void show(ListGridRecord record, boolean edit, User currentUser, int page);

}
