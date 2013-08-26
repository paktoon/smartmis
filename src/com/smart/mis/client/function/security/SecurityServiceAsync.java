package com.smart.mis.client.function.security;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smart.mis.shared.security.PermissionProfile;
import com.smart.mis.shared.security.User;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface SecurityServiceAsync {
	void createUserOnServer(User user, String creator,
			AsyncCallback<Boolean> callback);
	void loginToServer(String userName, String passWord,
			AsyncCallback<User> callback);
	void resetPassword(String userName, String passWord,
			AsyncCallback<Boolean> callback);
	void createPermOnServer(PermissionProfile profile, String creator,
			AsyncCallback<Boolean> callback);
	void getPermissionFromServer(String name,
			AsyncCallback<PermissionProfile> callback);
}
