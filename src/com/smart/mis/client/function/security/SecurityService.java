package com.smart.mis.client.function.security;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.smart.mis.shared.security.PermissionProfile;
import com.smart.mis.shared.security.User;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("security")
public interface SecurityService extends RemoteService {
	User loginToServer(String userName,String passWord) throws IllegalArgumentException;
	PermissionProfile getPermissionFromServer(String name) throws IllegalArgumentException;
	String createUserOnServer(User user, String pname, String creator) throws IllegalArgumentException;
	boolean updateUserOnServer(User user, String pname, String updater);
	String createPermOnServer(PermissionProfile profile, String creator);
	boolean updatePermOnServer(PermissionProfile profile, String updater) throws IllegalArgumentException;
	boolean resetPassword(String userName, String passWord);
}
