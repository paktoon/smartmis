package com.smart.mis.server.security;

import com.smart.mis.client.function.security.SecurityService;
import com.smart.mis.datastore.PermissionKind;
import com.smart.mis.datastore.UserKind;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.smart.mis.shared.FieldVerifier;
import com.smart.mis.shared.security.PermissionProfile;
import com.smart.mis.shared.security.User;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class SecurityServiceImpl extends RemoteServiceServlet implements
	SecurityService {

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}

	@Override
	public User loginToServer(String userName, String passWord)
			throws IllegalArgumentException {
		userName = escapeHtml(userName);
		passWord = escapeHtml(passWord); 
        if (!FieldVerifier.isValidName(userName) || !FieldVerifier.isValidName(passWord)) {
			throw new IllegalArgumentException(
					"LOGIN FAILED");
		}
        
        User userInstance = UserKind.loginUser(userName, passWord);
        
        if (userInstance == null)
        {
			throw new IllegalArgumentException("LOGIN FAILED");
        }
		return userInstance;
	}

	@Override
	public String createUserOnServer(User user, String pname, String creator)
			throws IllegalArgumentException {
		
		if (UserKind.hasUser(user.getUserName())){
			return null;
		}
		
		return UserKind.createUser(user, pname, creator);
	}
	
	@Override
	public String createPermOnServer(PermissionProfile profile, String creator)
			throws IllegalArgumentException {
		
		if (PermissionKind.hasPerm(profile.getName())){
			return null;
		}
		
		return PermissionKind.createPerm(profile, creator);
	}

	@Override
	public PermissionProfile getPermissionFromServer(String name)
			throws IllegalArgumentException {
		
		return PermissionKind.getPerm(name);
	}

	@Override
	public boolean resetPassword(String userName, String passWord) {
		return UserKind.resetPassword(userName, passWord);
	}

	@Override
	public boolean updateUserOnServer(User user, String pname, String updater)
			throws IllegalArgumentException {
		return UserKind.updateUser(user, pname, updater);
	}

	@Override
	public boolean updatePermOnServer(PermissionProfile profile, String updater)
			throws IllegalArgumentException {
		return PermissionKind.updatePerm(profile, updater);
	}
}
