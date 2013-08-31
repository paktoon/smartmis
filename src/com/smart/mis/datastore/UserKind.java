package com.smart.mis.datastore;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.smart.mis.shared.security.PermissionProfile;
import com.smart.mis.shared.security.User;

public class UserKind{
	
	public static String createUser(User euser, String pname, String creator) {
		Entity perm = PermissionKind.getPermEntity(pname);
		if (!hasUser(euser.getUserName()) && perm != null){
			try {
				Entity user = new Entity("USER", euser.getUserName());
				user.setProperty("pwd", euser.getPassWord());
				user.setProperty("title", euser.getTitle());
				user.setProperty("fname", euser.getFirstName());
				user.setProperty("lname", euser.getLastName());
				user.setProperty("email", euser.getEmail());
				user.setProperty("position", euser.getPosition());
				user.setProperty("status", euser.getStatus());
				user.setProperty("profile", pname);
				user.setProperty("uid", "UR" + (10000 + Util.getKey("USER").getId()));
				user.setProperty("creator", creator);
				user.setProperty("when", new SimpleDateFormat("dd-MM-yyy").format(Calendar.getInstance().getTime()));
				Util.persistEntity(user);
				return (String) getUserEntity(euser.getUserName()).getProperty("uid");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static boolean updateUser(User euser, String pname, String creator) {
		if (hasUser(euser.getUserName())){
			try {
				Entity user = getUserEntity(euser.getUserName());
				user.setProperty("pwd", euser.getPassWord());
				user.setProperty("title", euser.getTitle());
				user.setProperty("fname", euser.getFirstName());
				user.setProperty("lname", euser.getLastName());
				user.setProperty("email", euser.getEmail());
				user.setProperty("position", euser.getPosition());
				user.setProperty("status", euser.getStatus());
				user.setProperty("profile", pname);
				user.setProperty("creator", creator);
				user.setProperty("when", new SimpleDateFormat("dd-MM-yyy").format(Calendar.getInstance().getTime()));
				Util.persistEntity(user);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public static User loginUser(String username, String password) {
		Entity user = getUserEntity(username);
		if (user != null)
		{			
			String pwd = (String) user.getProperty("pwd");
			if (password.equals(pwd)){
				String name = user.getKey().getName();
				//Already get pwd
				String fname = (String) user.getProperty("fname");
				String lname = (String) user.getProperty("lname");
				String email = (String) user.getProperty("email");
				String position = (String) user.getProperty("position");
				PermissionProfile profile = PermissionKind.getPerm((String) user.getProperty("profile"));
				String title = (String) user.getProperty("title");
				return new User(name, pwd, fname, lname, email, position, profile, title, true);
			}
		} 
		return null;
	}
	
	public static boolean resetPassword(String username, String password) {
		Entity user = getUserEntity(username);
		if (user != null)
		{			
			user.setProperty("pwd", password);
			return Util.persistEntity(user);
		} 
		return false;
	}
	
	public static Entity getUserEntity(String name) {
	  	Key key = KeyFactory.createKey("USER",name);
	  	return Util.findEntity(key);
	}
	
	public static boolean hasUser(String name) {
		Entity perm = getUserEntity(name);
		if (perm == null){
			return false;
		} else return true;
	}
	
	public static boolean deleteUser(String username) {
		//To do - set status to false
		return true;
	}
	
	public static String writeJSON() {
		
		Iterable<Entity> userList = Util.listEntities("USER",null,null);
		StringBuilder sb = new StringBuilder();
		boolean firstSymbol = true;
		
		sb.append("[");
		for (Entity user : userList) {
			String uname = user.getKey().getName();
			String pwd = user.getProperty("pwd").toString();
			String title = user.getProperty("title").toString();
			String fname = user.getProperty("fname").toString();
			String lname = user.getProperty("lname").toString();
			String email = user.getProperty("email").toString();
			String position = user.getProperty("position").toString();
			String profile = user.getProperty("profile").toString();
			boolean status = (Boolean) user.getProperty("status");
			String uid = user.getProperty("uid").toString();
			String creator = (String) user.getProperty("creator");
			String when = (String) user.getProperty("when");
			
		    if (firstSymbol) {
			   firstSymbol = false;
			} else {
			   sb.append(",");
			}
		      
			sb.append("{");
			sb.append("\"uid\": \"");
			sb.append(uid);
			sb.append("\",");
			sb.append("\"uname\": \"");
			sb.append(uname);
			sb.append("\",");
			sb.append("\"pwd\": \"");
			sb.append(pwd);
			sb.append("\",");
			sb.append("\"title\": \"");
			sb.append(title);
			sb.append("\",");
			sb.append("\"creator\": \"");
			sb.append(creator);
			sb.append("\",");
			sb.append("\"when\": \"");
			sb.append(when);
			sb.append("\",");
			sb.append("\"fname\": \"");
			sb.append(fname);
			sb.append("\",");
			sb.append("\"lname\": \"");
			sb.append(lname);
			sb.append("\",");
			sb.append("\"email\": \"");
			sb.append(email);
			sb.append("\",");
			sb.append("\"position\": \"");
			sb.append(position);
			sb.append("\",");
			sb.append("\"pname\": \"");
			sb.append(profile);
			sb.append("\",");
			sb.append("\"status\": ");
			sb.append(status);
			sb.append("}");
		}
		sb.append("]");
		return sb.toString();
	}
}